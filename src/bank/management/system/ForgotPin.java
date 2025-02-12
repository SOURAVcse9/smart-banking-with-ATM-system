package bank.management.system;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ForgotPin extends JFrame implements ActionListener {
    JTextField cardField, emailField, pinField, confirmPinField;
    JButton resetButton, backButton;

    public ForgotPin() {
        // Frame settings
        setTitle("Forgot PIN");
        setLayout(null);
        setSize(480, 400);
        setLocation(400, 200);

        JLabel instructionLabel = new JLabel("CARD No: ");
        instructionLabel.setForeground(Color.BLACK);
        instructionLabel.setFont(new Font("System", Font.BOLD, 20));
        instructionLabel.setBounds(50, 50, 150, 25);
        add(instructionLabel);

        cardField = new JTextField();
        cardField.setBounds(200, 50, 200, 30);
        cardField.setFont(new Font("Raleway", Font.BOLD, 16));
        add(cardField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 100, 100, 30);
        emailLabel.setFont(new Font("System", Font.BOLD, 20));
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(200, 100, 200, 30);
        emailField.setFont(new Font("Raleway", Font.BOLD, 16));
        add(emailField);

        JLabel pinLabel = new JLabel("New PIN:");
        pinLabel.setBounds(50, 150, 100, 30);
        pinLabel.setFont(new Font("System", Font.BOLD, 20));
        add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(200, 150, 200, 30);
        pinField.setFont(new Font("Raleway", Font.BOLD, 16));
        add(pinField);
        
        JLabel confirmPinLabel = new JLabel("Confirm PIN:");
        confirmPinLabel.setBounds(50, 200, 150, 30);
        confirmPinLabel.setFont(new Font("System", Font.BOLD, 20));
        add(confirmPinLabel);
        
        confirmPinField = new JPasswordField();
        confirmPinField.setBounds(200, 200, 200, 30);
        confirmPinField.setFont(new Font("Raleway", Font.BOLD, 16));
        add(confirmPinField);

        resetButton = new JButton("Reset");
        resetButton.setBounds(280, 270, 120, 30);
        resetButton.setFont(new Font("Raleway", Font.BOLD, 20));
        resetButton.addActionListener(this);
        add(resetButton);
        

        backButton = new JButton("Back");
        backButton.setBounds(50, 270, 120, 30);
        backButton.setFont(new Font("Raleway", Font.BOLD, 20));
        backButton.addActionListener(this);
        add(backButton);

        // Frame visibility
        getContentPane().setBackground(Color.CYAN);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            String cardNo = cardField.getText();
            String email = emailField.getText();
            String pin = pinField.getText();
            String confirmPin = confirmPinField.getText();

            if (cardNo.equals("") || email.equals("") || pin.equals("") || confirmPin.equals("")) {
                JOptionPane.showMessageDialog(null, "All fields are required");
                return;
            }

            if (!pin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(null, "PINs do not match");
                return;
            }

            try {
                Connn c = new Connn();
                String query = "SELECT email FROM users INNER JOIN login ON users.user_id = login.user_id WHERE card_no = ? AND email = ?";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setString(1, cardNo);
                ps.setString(2, email);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String updateQuery = "UPDATE login SET pin_no = ? WHERE card_no = ?";
                    PreparedStatement updatePs = c.c.prepareStatement(updateQuery);
                    updatePs.setString(1, pin);
                    updatePs.setString(2, cardNo);
                    updatePs.executeUpdate();

                    JOptionPane.showMessageDialog(null, "PIN reset successfully");
                    setVisible(false);
                    new Login(""); // Navigate to login
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid card number or email");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else if (e.getSource() == backButton) {
            setVisible(false);
            new Login("");
        }
    }

    public static void main(String[] args) {
        new ForgotPin();
    }
}


