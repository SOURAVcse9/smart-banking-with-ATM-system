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

public class BalanceEnquiry extends JFrame implements ActionListener {
    private JLabel background, instructionLabel, balanceLabel;
    private JButton backButton;
    private String pin;

    public BalanceEnquiry(String pin) {
        this.pin = pin;

        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        background = new JLabel(i3);
        background.setBounds(0, 0, 1550, 830);
        background.setLayout(null);
        add(background);

        // Instruction Label
        instructionLabel = new JLabel("Your Current Account Balance is:");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("System", Font.BOLD, 22));
        instructionLabel.setBounds(440, 200, 600, 30);
        background.add(instructionLabel);

        // Balance Label
        balanceLabel = new JLabel("Tk. 0.00");
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("System", Font.BOLD, 25));
        balanceLabel.setBounds(500, 250, 600, 30);
        background.add(balanceLabel);

        // Back Button
        backButton = new JButton("BACK");
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBounds(700, 410, 150, 35);
        backButton.addActionListener(this);
        background.add(backButton);

        // Fetch and Display Balance
        fetchBalance();

        // Frame Properties
        setTitle("ATM Simulator");
        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fetchBalance() {
        double balance = 0.0;

        try {
            Connn conn = new Connn();
            
            // ✅ Use PreparedStatement for security
            String queryAccount = "SELECT accounts.account_id FROM accounts "
                    + "INNER JOIN login ON accounts.card_no = login.card_no "
                    + "WHERE login.pin_no = ?";
            
            PreparedStatement psAccount = conn.c.prepareStatement(queryAccount);
            psAccount.setString(1, pin);
            ResultSet rsAccount = psAccount.executeQuery();

            if (rsAccount.next()) {
                int accountId = rsAccount.getInt("account_id");

                // Fetch transactions related to the account
                String queryTransactions = "SELECT transaction_type, amount FROM transactions WHERE account_id = ?";
                PreparedStatement psTransactions = conn.c.prepareStatement(queryTransactions);
                psTransactions.setInt(1, accountId);
                ResultSet rsTransactions = psTransactions.executeQuery();

                while (rsTransactions.next()) {
                    String transactionType = rsTransactions.getString("transaction_type");
                    double amount = rsTransactions.getDouble("amount");

                    if (transactionType.equalsIgnoreCase("Deposit") || transactionType.equalsIgnoreCase("Transfer In")) {
                        balance += amount;
                    } else if (transactionType.equalsIgnoreCase("Withdraw") || transactionType.equalsIgnoreCase("Transfer Out")) {
                        balance -= amount;
                    }
                }

                // Close resources
                rsTransactions.close();
                psTransactions.close();
            }

            rsAccount.close();
            psAccount.close();
            conn.c.close(); // Close connection

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Update Balance Label
        balanceLabel.setText("Tk. " + String.format("%.2f", balance));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            new Main_class(pin);
        }
    }

    public static void main(String[] args) {
        new BalanceEnquiry("");
    }
}
