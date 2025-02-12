package bank.management.system;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Transfer extends JFrame implements ActionListener {
    JLabel labelRecipient, labelAmount;
    JTextField textRecipient, textAmount;
    JButton bTransfer, bBack;
    String pin;

    Transfer(String pin) {
        this.pin = pin;

        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 1550, 830);
        background.setLayout(null);
        add(background);

        // Instruction Label
        JLabel instructionLabel = new JLabel("TRANSFER MONEY TO ANOTHER ACCOUNT");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("system", Font.BOLD, 20));
        instructionLabel.setBounds(410, 150, 600, 30);
        background.add(instructionLabel);

        // Recipient Label
        labelRecipient = new JLabel("Recipient Account Number :");
        labelRecipient.setForeground(Color.WHITE);
        labelRecipient.setFont(new Font("System", Font.BOLD, 20));
        labelRecipient.setBounds(500, 200, 300, 30);
        background.add(labelRecipient);

        // Recipient Account Field
        textRecipient = new JTextField();
        textRecipient.setBackground(Color.GRAY);
        textRecipient.setForeground(Color.WHITE);
        textRecipient.setFont(new Font("Raleway", Font.PLAIN, 30));
        textRecipient.setBounds(430, 240, 400, 35);
        background.add(textRecipient);

        // Amount Label
        labelAmount = new JLabel("Amount :");
        labelAmount.setForeground(Color.WHITE);
        labelAmount.setFont(new Font("System", Font.BOLD, 20));
        labelAmount.setBounds(590, 280, 250, 30);
        background.add(labelAmount);

        // Amount Field
        textAmount = new JTextField();
        textAmount.setBackground(Color.GRAY);
        textAmount.setForeground(Color.WHITE);
        textAmount.setFont(new Font("Raleway", Font.BOLD, 30));
        textAmount.setBounds(500, 317, 250, 35);
        background.add(textAmount);

        // Submit Button
        bTransfer = new JButton("TRANSFER");
        bTransfer.setBackground(Color.BLUE);
        bTransfer.setForeground(Color.WHITE);
        bTransfer.setFont(new Font("Arial", Font.BOLD, 20));
        bTransfer.setBounds(700, 370, 150, 30);
        bTransfer.addActionListener(this);
        background.add(bTransfer);

        // Cancel Button
        bBack = new JButton("BACK");
        bBack.setBackground(Color.RED);
        bBack.setForeground(Color.WHITE);
        bBack.setFont(new Font("Arial", Font.BOLD, 20));
        bBack.setBounds(700, 410, 150, 30);
        bBack.addActionListener(this);
        background.add(bBack);

        // Frame Properties
        setTitle("Money Transfer");
        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    
    @Override
public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == bTransfer) {
        String recipientCard = textRecipient.getText();
        String amountStr = textAmount.getText();

        if (recipientCard.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connn conn = null;
        try {
            double transferAmount = Double.parseDouble(amountStr);

            if (transferAmount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            conn = new Connn();
            conn.c.setAutoCommit(false); 

            // Get sender's account info using the PIN
            String fetchSenderQuery = """
                SELECT a.account_id, a.balance, a.card_no
                FROM accounts a
                JOIN login l ON a.card_no = l.card_no
                WHERE l.pin_no = ?
            """;
            PreparedStatement psFetchSender = conn.c.prepareStatement(fetchSenderQuery);
            psFetchSender.setString(1, pin); // Ensure 'pin' is passed to this method
            ResultSet rsSender = psFetchSender.executeQuery();

            if (!rsSender.next()) {
                JOptionPane.showMessageDialog(this, "Invalid PIN. Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int senderAccountId = rsSender.getInt("account_id");
            double senderBalance = rsSender.getDouble("balance");
            String senderCard = rsSender.getString("card_no");

            if (transferAmount > senderBalance) {
                JOptionPane.showMessageDialog(this, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get recipient's account info
            String fetchRecipientQuery = "SELECT account_id FROM accounts WHERE card_no = ?";
            PreparedStatement psFetchRecipient = conn.c.prepareStatement(fetchRecipientQuery);
            psFetchRecipient.setString(1, recipientCard);
            ResultSet rsRecipient = psFetchRecipient.executeQuery();

            if (!rsRecipient.next()) {
                JOptionPane.showMessageDialog(this, "Recipient account not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int recipientAccountId = rsRecipient.getInt("account_id");

            String updateSenderBalance = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            PreparedStatement psUpdateSender = conn.c.prepareStatement(updateSenderBalance);
            psUpdateSender.setDouble(1, transferAmount);
            psUpdateSender.setInt(2, senderAccountId);
            psUpdateSender.executeUpdate();

            // Add amount to recipient
            String updateRecipientBalance = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            PreparedStatement psUpdateRecipient = conn.c.prepareStatement(updateRecipientBalance);
            psUpdateRecipient.setDouble(1, transferAmount);
            psUpdateRecipient.setInt(2, recipientAccountId);
            psUpdateRecipient.executeUpdate();

            // Log the transaction for sender
            String logSenderTransaction = """
                INSERT INTO transactions (account_id, transaction_type, amount, description)
                VALUES (?, ?, ?, ?)
            """;
            PreparedStatement psLogSender = conn.c.prepareStatement(logSenderTransaction);
            psLogSender.setInt(1, senderAccountId);
            psLogSender.setString(2, "Transfer Out");
            psLogSender.setDouble(3, transferAmount);
            psLogSender.setString(4, "Transferred to card: " + recipientCard);
            psLogSender.executeUpdate();

            // Log the transaction for recipient
            String logRecipientTransaction = """
                INSERT INTO transactions (account_id, transaction_type, amount, description)
                VALUES (?, ?, ?, ?)
            """;
            PreparedStatement psLogRecipient = conn.c.prepareStatement(logRecipientTransaction);
            psLogRecipient.setInt(1, recipientAccountId);
            psLogRecipient.setString(2, "Transfer In");
            psLogRecipient.setDouble(3, transferAmount);
            psLogRecipient.setString(4, "Received from card: " + senderCard);
            psLogRecipient.executeUpdate();

            conn.c.commit(); 
            conn.c.setAutoCommit(true);

            JOptionPane.showMessageDialog(this, "Transfer Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            new Main_class(pin).setVisible(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.c.rollback(); 
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (conn != null) {
                    conn.c.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    } else if (ae.getSource() == bBack) {
        setVisible(false);
        new Main_class(pin).setVisible(true);
    }
}

    public static void main(String[] args) {
        new Transfer("");  
    }
}
