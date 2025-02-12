package bank.management.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Withdraw extends JFrame implements ActionListener {

    private String pin;
    private JTextField withdrawField;
    private JButton submitButton, cancelButton;

    public Withdraw(String pin) {
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
        JLabel instructionLabel = new JLabel("ENTER THE AMOUNT YOU WANT TO WITHDRAW");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("System", Font.BOLD, 18));
        instructionLabel.setBounds(410, 180, 600, 30);
        background.add(instructionLabel);

        // Withdraw Field
        withdrawField = new JTextField();
        withdrawField.setBackground(Color.GRAY);
        withdrawField.setForeground(Color.WHITE);
        withdrawField.setFont(new Font("Raleway", Font.BOLD, 30));
        withdrawField.setBounds(520, 240, 250, 40);
        background.add(withdrawField);

        // Submit Button
        submitButton = new JButton("WITHDRAW");
        submitButton.setBackground(Color.BLUE);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        submitButton.setBounds(700, 370, 150, 35);
        submitButton.addActionListener(this);
        background.add(submitButton);

        // Cancel Button
        cancelButton = new JButton("BACK");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 20));
        cancelButton.setBounds(700, 410, 150, 35);
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
            String withdrawAmountStr = withdrawField.getText();
    
            if (withdrawAmountStr == null || withdrawAmountStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Amount cannot be empty!");
                return;
            }
    
            try {
                double withdrawAmount = Double.parseDouble(withdrawAmountStr);
    
                if (withdrawAmount <= 0) {
                    JOptionPane.showMessageDialog(null, "Amount must be greater than zero!");
                    return;
                }
    
                Connn c1 = new Connn();
    
                // Fetch user_id and card_no from the login table using the pin_no (provided pin)
                ResultSet rsLogin = c1.s.executeQuery("SELECT user_id, card_no FROM login WHERE pin_no = '" + pin + "'");
                if (rsLogin.next()) {
                    String cardNo = rsLogin.getString("card_no");
                    int userId = rsLogin.getInt("user_id");
    
                    // Fetch account details from the accounts table using card_no
                    ResultSet rsAccount = c1.s.executeQuery("SELECT account_id, balance FROM accounts WHERE card_no = '" + cardNo + "'");
                    if (rsAccount.next()) {
                        int accountId = rsAccount.getInt("account_id");
                        double balance = rsAccount.getDouble("balance");
    
                        if (balance < withdrawAmount) {
                            JOptionPane.showMessageDialog(null, "Insufficient Balance");
                            return;
                        }
    
                        double newBalance = balance - withdrawAmount;
    
                        // Update the balance in the accounts table
                        c1.s.executeUpdate("UPDATE accounts SET balance = " + newBalance + " WHERE account_id = " + accountId);
    
                        // Log the transaction in the transactions table
                        c1.s.executeUpdate(
                                "INSERT INTO transactions (account_id, transaction_type, amount, description) " +
                                "VALUES (" + accountId + ", 'Withdraw', " + withdrawAmount + ", 'ATM Withdrawal')");
    
                        // Play success sound
                        playSound("src/success.wav");
                        JOptionPane.showMessageDialog(null, "Tk. " + withdrawAmount + " Withdrawn Successfully");
    
                        setVisible(false);
                        new Main_class(pin);
                    } else {
                        JOptionPane.showMessageDialog(null, "Account not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid PIN.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Amount!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
            }
        } else if (e.getSource() == cancelButton) {
            setVisible(false);
            new Main_class(pin);
        }
    }
    
    private void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            if (!soundFile.exists()) {
                throw new RuntimeException("Audio file not found: " + soundFilePath);
            }

            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unable to play audio: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Withdraw("");
    }
}
