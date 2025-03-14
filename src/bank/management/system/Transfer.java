package bank.management.system;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        String amountText = textAmount.getText();
        
        if (recipientCard.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter recipient card and amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ✅ Ensure proper initialization of Conn object
        try {
            Connn conn = new Connn(); // ✅ Fix: Create Conn object inside try
            conn.c.setAutoCommit(false); // Start transaction
            
            // Fetch sender details
            String fetchSenderQuery = """
                SELECT u.user_id, a.balance, a.account_id, a.card_no
                FROM users u
                JOIN accounts a ON u.user_id = a.user_id
                JOIN login l ON a.card_no = l.card_no
                WHERE l.pin_no = ?
            """;
            PreparedStatement psFetchSender = conn.c.prepareStatement(fetchSenderQuery);
            psFetchSender.setString(1, pin);
            ResultSet rsSender = psFetchSender.executeQuery();

            if (!rsSender.next()) {
                JOptionPane.showMessageDialog(this, "Invalid PIN. Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int senderUserId = rsSender.getInt("user_id");
            int senderAccountId = rsSender.getInt("account_id");
            double senderBalance = rsSender.getDouble("balance");
            String senderCard = rsSender.getString("card_no");

            if (senderBalance < amount) {
                JOptionPane.showMessageDialog(this, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Fetch recipient details
            String fetchRecipientQuery = """
                SELECT u.user_id, a.account_id 
                FROM users u
                JOIN accounts a ON u.user_id = a.user_id
                WHERE a.card_no = ?
            """;
            PreparedStatement psFetchRecipient = conn.c.prepareStatement(fetchRecipientQuery);
            psFetchRecipient.setString(1, recipientCard);
            ResultSet rsRecipient = psFetchRecipient.executeQuery();

            if (!rsRecipient.next()) {
                JOptionPane.showMessageDialog(this, "Recipient account not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int recipientUserId = rsRecipient.getInt("user_id");
            int recipientAccountId = rsRecipient.getInt("account_id");

            // Deduct money from sender
            String deductMoneyQuery = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            PreparedStatement psDeductMoney = conn.c.prepareStatement(deductMoneyQuery);
            psDeductMoney.setDouble(1, amount);
            psDeductMoney.setInt(2, senderAccountId);
            psDeductMoney.executeUpdate();

            // Add money to recipient
            String addMoneyQuery = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            PreparedStatement psAddMoney = conn.c.prepareStatement(addMoneyQuery);
            psAddMoney.setDouble(1, amount);
            psAddMoney.setInt(2, recipientAccountId);
            psAddMoney.executeUpdate();

            // Log Transaction
            String logTransactionQuery = "INSERT INTO transactions (account_id, transaction_type, amount) VALUES (?, 'Transfer', ?)";
            PreparedStatement psLogTransaction = conn.c.prepareStatement(logTransactionQuery, Statement.RETURN_GENERATED_KEYS);
            psLogTransaction.setInt(1, senderAccountId);
            psLogTransaction.setDouble(2, amount);
            psLogTransaction.executeUpdate();

            // Get transaction ID
            ResultSet rsTransaction = psLogTransaction.getGeneratedKeys();
            int transactionId = -1;
            if (rsTransaction.next()) {
                transactionId = rsTransaction.getInt(1);
            }

            // Log fund transfer
            String logFundTransferQuery = "INSERT INTO fundTransfers (sender_id, receiver_id, transaction_id, transfer_amount) VALUES (?, ?, ?, ?)";
            PreparedStatement psLogFundTransfer = conn.c.prepareStatement(logFundTransferQuery);
            psLogFundTransfer.setInt(1, senderUserId);
            psLogFundTransfer.setInt(2, recipientUserId);
            psLogFundTransfer.setInt(3, transactionId);
            psLogFundTransfer.setDouble(4, amount);
            psLogFundTransfer.executeUpdate();

            conn.c.commit(); // Commit transaction
            JOptionPane.showMessageDialog(this, "Transfer Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            textRecipient.setText("");
            textAmount.setText("");

        } catch (SQLException e) {
            try {
                Connn conn = new Connn(); // ✅ Fix: Ensure rollback has a connection instance
                conn.c.rollback(); // Rollback on error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Transfer Failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                Connn conn = new Connn(); // ✅ Fix: Ensure setAutoCommit(true) has a connection instance
                conn.c.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
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
