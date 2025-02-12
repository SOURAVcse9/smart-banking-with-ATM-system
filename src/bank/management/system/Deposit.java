package bank.management.system;


import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Deposit extends JFrame implements ActionListener {
    private String pin;
    private JTextField depositField;
    private JButton submitButton, cancelButton;

    public Deposit(String pin) {
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
        JLabel instructionLabel = new JLabel("ENTER THE AMOUNT YOU WANT TO DEPOSIT");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("System", Font.BOLD, 18));
        instructionLabel.setBounds(430, 180, 600, 30);
        background.add(instructionLabel);

        // Deposit Field
        depositField = new JTextField();
        depositField.setBackground(Color.GRAY);
        depositField.setForeground(Color.WHITE);
        depositField.setFont(new Font("Raleway", Font.BOLD, 30));
        depositField.setBounds(520, 240, 250, 40);
        background.add(depositField);

        // Submit Button
        submitButton = new JButton("DEPOSIT");
        submitButton.setBackground(Color.BLUE);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        submitButton.setBounds(700, 370, 130, 35);
        submitButton.addActionListener(this);
        background.add(submitButton);

        // Cancel Button
        cancelButton = new JButton("BACK");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 20));
        cancelButton.setBounds(700, 410, 130, 35);
        cancelButton.addActionListener(this);
        background.add(cancelButton);

        // Frame Properties
        setTitle("ATM Simulator");
        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String depositAmount = depositField.getText();
    
            if (depositAmount == null || depositAmount.trim().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Amount cannot be empty!");
                return;
            }
    
            try {
                double amount = Double.parseDouble(depositAmount);
    
                if (amount <= 0) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Amount must be greater than zero!");
                    return;
                }
    
                // Fetch account_id using the pin
                Connn con = new Connn();
                String accountQuery = "SELECT accounts.account_id, accounts.balance FROM accounts " +
                                      "INNER JOIN login ON accounts.card_no = login.card_no " +
                                      "WHERE login.pin_no = ?";
                PreparedStatement psAccount = con.c.prepareStatement(accountQuery);
                psAccount.setString(1, pin);
                ResultSet rs = psAccount.executeQuery();
    
                if (rs.next()) {
                    int accountId = rs.getInt("account_id");
                    double currentBalance = rs.getDouble("balance");
    
                    
                    double newBalance = currentBalance + amount;
    
                    // Update the balance in the accounts table
                    String updateBalanceQuery = "UPDATE accounts SET balance = ? WHERE account_id = ?";
                    PreparedStatement psUpdateBalance = con.c.prepareStatement(updateBalanceQuery);
                    psUpdateBalance.setDouble(1, newBalance);
                    psUpdateBalance.setInt(2, accountId);
                    psUpdateBalance.executeUpdate();
    
                    // Insert deposit transaction into the transactions table
                    String transactionQuery = "INSERT INTO transactions (account_id, transaction_type, amount, description) " +
                                              "VALUES (?, 'Deposit', ?, 'Cash deposit')";
                    PreparedStatement psTransaction = con.c.prepareStatement(transactionQuery);
                    psTransaction.setInt(1, accountId);
                    psTransaction.setDouble(2, amount);
                    psTransaction.executeUpdate();
    
                    javax.swing.JOptionPane.showMessageDialog(null, "Tk. " + amount + " Deposited Successfully");
                    setVisible(false);
                    new Main_class(pin);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Account not found!");
                }
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Invalid Amount!");
            } catch (Exception ex) {
                ex.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
            }
        } else if (e.getSource() == cancelButton) {
            setVisible(false);
            new Main_class(pin);
        }
    }
    
    
    
    public static void main(String[] args) {
        new Deposit("");
    }
}
