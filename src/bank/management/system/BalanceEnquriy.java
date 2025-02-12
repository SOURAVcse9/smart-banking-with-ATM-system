package bank.management.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BalanceEnquriy extends JFrame implements ActionListener {
    JLabel background, instructionLabel, balanceLabel;
    JButton cancelButton;
    private String pin;

    public BalanceEnquriy(String pin) {
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
        instructionLabel = new JLabel("Your Current Account Balance is: ");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("System", Font.BOLD, 22));
        instructionLabel.setBounds(440, 200, 600, 30);
        background.add(instructionLabel);

        // Balance Label
        balanceLabel = new JLabel("TK. 0.00");
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("System", Font.BOLD, 25));
        balanceLabel.setBounds(500, 250, 600, 30);
        background.add(balanceLabel);

        // Cancel Button
        cancelButton = new JButton("BACK");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 20));
        cancelButton.setBounds(700, 410, 150, 35);
        cancelButton.addActionListener(this);
        background.add(cancelButton);

        // Fetch balance from database
        double balance = 0.0;
        try {
            Connn c = new Connn();
            
            ResultSet rsAccount = c.s.executeQuery(
                "SELECT accounts.account_id FROM accounts  INNER JOIN login  ON accounts.card_no = login.card_no WHERE login.pin_no = '" + pin + "'"
            );
            if (rsAccount.next()) {
                int accountId = rsAccount.getInt("account_id");

                
                ResultSet rsTransactions = c.s.executeQuery(
                    "SELECT * FROM transactions WHERE account_id = " + accountId);
                while (rsTransactions.next()) {
                    String transactionType = rsTransactions.getString("transaction_type");
                    double amount = rsTransactions.getDouble("amount");

                    if (transactionType.equalsIgnoreCase("Deposit")|| transactionType.equalsIgnoreCase("Transfer In")) {
                        balance += amount;
                        
                    } else if (transactionType.equalsIgnoreCase("Withdraw") || transactionType.equalsIgnoreCase("Transfer Out")) {
                        balance -= amount;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        balanceLabel.setText("Tk. " + String.format("%.2f", balance));

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
        if (e.getSource() == cancelButton) {
            setVisible(false);
            new Main_class(pin);
        }
    }

    public static void main(String[] args) {
        new BalanceEnquriy("");
    }
}
