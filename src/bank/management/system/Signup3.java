package bank.management.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Signup3 extends JFrame implements ActionListener {
    JCheckBox c1, c2, c3, c4, c5, c6, c7;
    JButton cancelButton, submitButton;
    String formno;
    JComboBox<String> accountTypeComboBox;

    public Signup3(String formno) {
        this.formno = formno;

        // Bank image loader
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(100, 5, 80, 80);
        add(image);

        // Form header
        JLabel label7 = new JLabel("Form No :");
        label7.setBounds(220, 20, 100, 40);
        label7.setFont(new Font("Raleway", Font.BOLD, 20));
        add(label7);

        JLabel labelform = new JLabel(formno);
        labelform.setBounds(320, 20, 200, 40);
        labelform.setFont(new Font("Raleway", Font.BOLD, 20));
        add(labelform);

        JLabel label1 = new JLabel("Page 2 : Secure Details");
        label1.setBounds(220, 50, 400, 40);
        label1.setFont(new Font("Raleway", Font.BOLD, 22));
        add(label1);

        // Account type
        JLabel label11 = new JLabel("Select Your Account Type :");
        label11.setBounds(100, 100, 300, 30);
        label11.setFont(new Font("Raleway", Font.BOLD, 20));
        add(label11);

        accountTypeComboBox = new JComboBox<>(
                new String[] { "Saving", "Current", "Fixed Deposit", "Recurring Deposit" });
        accountTypeComboBox.setBounds(370, 100, 230, 30);
        accountTypeComboBox.setFont(new Font("Arial", Font.BOLD, 18));
        accountTypeComboBox.setBackground(Color.GRAY);
        add(accountTypeComboBox);

        // Card number
        JLabel label2 = new JLabel("Card Number :");
        label2.setBounds(100, 300, 200, 30);
        label2.setFont(new Font("Raleway", Font.BOLD, 20));
        add(label2);

        JLabel label3 = new JLabel("(your 16-digit Card number)");
        label3.setBounds(100, 330, 300, 20);
        label3.setFont(new Font("Raleway", Font.PLAIN, 15));
        add(label3);

        JLabel label4 = new JLabel("XXXX-XXXX-XXXX-4184");
        label4.setBounds(370, 300, 300, 40);
        label4.setFont(new Font("Raleway", Font.BOLD, 20));
        add(label4);

        JLabel label9 = new JLabel("(It would appear on ATM Card/Cheque Book and Statement)");
        label9.setBounds(370, 330, 600, 20);
        label9.setFont(new Font("Raleway", Font.PLAIN, 15));
        add(label9);

        // PIN
        JLabel label5 = new JLabel("PIN :");
        label5.setBounds(100, 360, 100, 30);
        label5.setFont(new Font("Raleway", Font.BOLD, 20));
        add(label5);

        JLabel label6 = new JLabel("(4-digit password)");
        label6.setBounds(100, 390, 200, 20);
        label6.setFont(new Font("Raleway", Font.PLAIN, 15));
        add(label6);

        JLabel label8 = new JLabel("XXXX");
        label8.setBounds(370, 360, 100, 40);
        label8.setFont(new Font("Raleway", Font.BOLD, 20));
        add(label8);

        // Checkboxes for services
        JLabel label10 = new JLabel("Select You Want Services :");
        label10.setBounds(100, 150, 300, 30);
        label10.setFont(new Font("Raleway", Font.BOLD, 20));
        add(label10);

        c1 = new JCheckBox("ATM CARD");
        c1.setBackground(new Color(204, 204, 255));
        c1.setBounds(100, 190, 200, 25);
        c1.setFont(new Font("Raleway", Font.BOLD, 18));
        add(c1);

        c2 = new JCheckBox("Internet Banking");
        c2.setBackground(new Color(204, 204, 255));
        c2.setBounds(355, 190, 200, 25);
        c2.setFont(new Font("Raleway", Font.BOLD, 20));
        add(c2);

        c3 = new JCheckBox("Mobile Banking");
        c3.setBackground(new Color(204, 204, 255));
        c3.setBounds(100, 220, 200, 25);
        c3.setFont(new Font("Raleway", Font.BOLD, 18));
        add(c3);

        c4 = new JCheckBox("POS/Online Shopping");
        c4.setBackground(new Color(204, 204, 255));
        c4.setBounds(355, 220, 300, 25);
        c4.setFont(new Font("Raleway", Font.BOLD, 18));
        add(c4);

        c5 = new JCheckBox("PayBill");
        c5.setBackground(new Color(204, 204, 255));
        c5.setBounds(100, 250, 200, 25);
        c5.setFont(new Font("Raleway", Font.BOLD, 18));
        add(c5);

        c6 = new JCheckBox("Mobile Wallet");
        c6.setBackground(new Color(204, 204, 255));
        c6.setBounds(355, 250, 200, 25);
        c6.setFont(new Font("Raleway", Font.BOLD, 18));
        add(c6);

        c7 = new JCheckBox("Above entered details are correct.", true);
        c7.setBackground(new Color(204, 204, 255));
        c7.setBounds(200, 430, 400, 30);
        c7.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
        c7.setForeground(new Color(0, 0, 139));
        add(c7);

        // Buttons
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(200, 480, 100, 30);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.setBackground(new Color(102, 178, 255));
        cancelButton.setForeground(Color.DARK_GRAY);
        cancelButton.addActionListener(this);
        add(cancelButton);

        submitButton = new JButton("Submit");
        submitButton.setBounds(400, 480, 100, 30);
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setBackground(new Color(102, 178, 255));
        submitButton.setForeground(Color.DARK_GRAY);
        submitButton.addActionListener(this);
        add(submitButton);

        getContentPane().setBackground(new java.awt.Color(204, 204, 255));
        setSize(800, 700);
        setTitle("Banking System Signup Page");
        setLocation(400, 100);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            try {
                Random ran = new Random();
                long first7 = (Math.abs(ran.nextLong()) % 90000000L) + 5040936000000000L;
                String cardno = String.valueOf(first7);

                int first3 = ran.nextInt(9000) + 1000;
                String pin = String.valueOf(first3);

                // Collect selected services
                List<String> selectedServices = new ArrayList<>();
                if (c1.isSelected())
                    selectedServices.add("ATM Card");
                if (c2.isSelected())
                    selectedServices.add("Internet Banking");
                if (c3.isSelected())
                    selectedServices.add("Mobile Banking");
                if (c4.isSelected())
                    selectedServices.add("POS/Online Shopping");
                if (c5.isSelected())
                    selectedServices.add("Pay Bill");
                if (c6.isSelected())
                    selectedServices.add("Mobile Wallet");

                if (selectedServices.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select at least one service.");
                    return;
                }

                String userId = null;

                Connn conn = new Connn();
                String fetchUserIdQuery = "SELECT user_id FROM users WHERE form_no = '" + formno + "'";
                var rs = conn.s.executeQuery(fetchUserIdQuery);
                if (rs.next()) {
                    userId = rs.getString("user_id");
                } else {
                    JOptionPane.showMessageDialog(null, "User not found. Please try again.");
                    return;
                }

                // Insert data into the accounts table
                String accountType = (String) accountTypeComboBox.getSelectedItem();
                String accountsQuery = "INSERT INTO accounts (user_id, card_no, account_type, balance, created_at) VALUES (?, ?, ?, ?, NOW())";
                PreparedStatement psAccounts = conn.c.prepareStatement(accountsQuery);
                psAccounts.setString(1, userId);
                psAccounts.setString(2, cardno);
                psAccounts.setString(3, accountType);
                psAccounts.setDouble(4, 0.0); // Initial balance
                psAccounts.executeUpdate();

                // Insert data into the login table
                String loginQuery = "INSERT INTO login (user_id, card_no, pin_no, last_login) VALUES (?, ?, ?, NOW())";
                PreparedStatement psLogin = conn.c.prepareStatement(loginQuery);
                psLogin.setString(1, userId);
                psLogin.setString(2, cardno);
                psLogin.setString(3, pin);
                psLogin.executeUpdate();

                // Insert selected services
                String servicesQuery = "INSERT INTO services (user_id, service_name) VALUES (?, ?)";
                PreparedStatement psServices = conn.c.prepareStatement(servicesQuery);
                for (String service : selectedServices) {
                    psServices.setString(1, userId);
                    psServices.setString(2, service);
                    psServices.executeUpdate();
                }

                // Show success message
                JOptionPane.showMessageDialog(
                        null,
                        "Registration Successful!" +
                                "\nCard Number: " + cardno +
                                "\nPIN: " + pin);
                new Deposit(pin);
                setVisible(false);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == cancelButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Signup3("");
    }
}



