package bank.management.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MiniStatement extends JFrame implements ActionListener {
    JButton exitButton;
    String pin;

    public MiniStatement(String pin) {
        this.pin = pin;

        // Frame setup
        getContentPane().setBackground(new Color(255, 204, 204));
        setSize(450, 700);
        setLocation(20, 20);
        setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("GS Bank Mini Statement");
        titleLabel.setFont(new Font("System", Font.BOLD, 18));
        titleLabel.setBounds(100, 20, 300, 20);
        add(titleLabel);

        // Card number label
        JLabel cardLabel = new JLabel();
        cardLabel.setBounds(20, 80, 300, 20);
        add(cardLabel);

        // Balance label
        JLabel balanceLabel = new JLabel();
        balanceLabel.setBounds(20, 550, 400, 20);
        add(balanceLabel);

        // Text area for transactions
        JTextArea transactionText = new JTextArea();
        transactionText.setEditable(false);
        transactionText.setFont(new Font("Monospaced", Font.PLAIN, 12));
        transactionText.setBackground(new Color(255, 204, 204));
        JScrollPane scrollPane = new JScrollPane(transactionText);
        scrollPane.setBounds(20, 120, 400, 400);
        add(scrollPane);

        try {
            Connn connn = new Connn(); // Using your database connection class
            Connection conn = connn.c;

            // Step 1: Fetch card number and balance
            String queryCard = """
                    SELECT login.card_no, accounts.balance
                    FROM login 
                    JOIN accounts ON login.card_no = accounts.card_no
                    WHERE login.pin_no = ? LIMIT 1
                    """;
            PreparedStatement stmtCard = conn.prepareStatement(queryCard);
            stmtCard.setString(1, pin);
            ResultSet rsCard = stmtCard.executeQuery();

            String cardNo = "";
            double balance = 0.0;
            if (rsCard.next()) {
                cardNo = rsCard.getString("card_no");
                balance = rsCard.getDouble("balance");
                cardLabel.setText("Card Number: " + cardNo.substring(0, 4) + "XXXXXXXX" + cardNo.substring(12));
            }
            rsCard.close();
            stmtCard.close();

            // Step 2: Fetch transactions for the account
            String queryTransactions = """
                    SELECT date, transaction_type, amount, description
                    FROM transactions
                    WHERE account_id = (
                        SELECT account_id FROM accounts WHERE card_no = ?
                    )
                    ORDER BY date ASC
                    """;
            PreparedStatement stmtTrans = conn.prepareStatement(queryTransactions);
            stmtTrans.setString(1, cardNo);
            ResultSet rsTrans = stmtTrans.executeQuery();

            StringBuilder transactions = new StringBuilder();
            while (rsTrans.next()) {
                String date = rsTrans.getString("date");
                String type = rsTrans.getString("transaction_type");
                double amount = rsTrans.getDouble("amount");
                String description = rsTrans.getString("description") != null ? rsTrans.getString("description") : "";

                transactions.append(date).append("   ")
                        .append(type).append("   Tk. ").append(amount)
                        .append("   ").append(description).append("\n");
            }
            rsTrans.close();
            stmtTrans.close();

            // Update text area and balance label
            transactionText.setText(transactions.length() > 0 ? transactions.toString() : "No transactions found.");
            balanceLabel.setText("Your Total Balance is Tk. " + balance);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.setBounds(300, 600, 100, 25);
        exitButton.addActionListener(this);
        exitButton.setBackground(Color.CYAN);
        exitButton.setForeground(Color.BLACK);
        add(exitButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }

    public static void main(String[] args) {
        new MiniStatement(""); // Example PIN for testing
    }
}
