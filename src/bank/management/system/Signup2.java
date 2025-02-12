package bank.management.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Signup2 extends JFrame implements ActionListener {

    String formno;
    JComboBox<String> accountTypeComboBox, occupationComboBox;
    JTextField incomeField, nidField, passportField;
    JButton nextButton;

    // Constructor
     public Signup2(String formno) {
        super("APPLICATION FORM - Page 2: Additional Details");
        this.formno = formno;

        // Set layout and background
        setLayout(null);
        setSize(700, 700);
        setLocation(400, 80);
        getContentPane().setBackground(new Color(252, 208, 76));

        // Bank logo
        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setBounds(150, 5, 100, 100);
        add(logoLabel);

        // Form number label
        JLabel formNoLabel = new JLabel("Form No:");
        formNoLabel.setBounds(300, 30, 100, 40);
        formNoLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        add(formNoLabel);

        JLabel formNoValueLabel = new JLabel(formno);
        formNoValueLabel.setBounds(400, 30, 200, 40);
        formNoValueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(formNoValueLabel);

        // Page heading
        JLabel headingLabel = new JLabel("Page 2: Additional Details");
        headingLabel.setBounds(300, 80, 400, 40);
        headingLabel.setFont(new Font("Raleway", Font.BOLD, 22));
        add(headingLabel);

        // Account type
        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setBounds(100, 140, 200, 30);
        accountTypeLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        add(accountTypeLabel);

        String[] accountTypes = {"Saving", "Current", "Fixed Deposit", "Recurring Deposit"};
        accountTypeComboBox = new JComboBox<>(accountTypes);
        accountTypeComboBox.setBounds(300, 140, 250, 30);
        accountTypeComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        accountTypeComboBox.setBackground(Color.GRAY);
        add(accountTypeComboBox);

        // Occupation
        JLabel occupationLabel = new JLabel("Occupation:");
        occupationLabel.setBounds(100, 200, 200, 30);
        occupationLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        add(occupationLabel);

        String[] occupations = {"Salaried", "Self-Employed", "Business", "Student", "Retired", "Others"};
        occupationComboBox = new JComboBox<>(occupations);
        occupationComboBox.setBounds(300, 200, 250, 30);
        occupationComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        occupationComboBox.setBackground(Color.GRAY);
        add(occupationComboBox);

        // Income
        JLabel incomeLabel = new JLabel("Income:");
        incomeLabel.setBounds(100, 260, 200, 30);
        incomeLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        add(incomeLabel);

        incomeField = new JTextField();
        incomeField.setBounds(300, 260, 250, 30);
        incomeField.setFont(new Font("Arial", Font.BOLD, 16));
        add(incomeField);

        // NID
        JLabel nidLabel = new JLabel("NID Number:");
        nidLabel.setBounds(100, 320, 200, 30);
        nidLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        add(nidLabel);

        nidField = new JTextField();
        nidField.setBounds(300, 320, 250, 30);
        nidField.setFont(new Font("Arial", Font.BOLD, 16));
        add(nidField);

        // Passport
        JLabel passportLabel = new JLabel("Passport Number:");
        passportLabel.setBounds(100, 380, 200, 30);
        passportLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        add(passportLabel);

        passportField = new JTextField();
        passportField.setBounds(300, 380, 250, 30);
        passportField.setFont(new Font("Arial", Font.BOLD, 16));
        add(passportField);

        // Next button
        nextButton = new JButton("Next");
        nextButton.setBounds(350, 440, 100, 30);
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(this);
        add(nextButton);

        setVisible(true);
    }

    @Override
public void actionPerformed(ActionEvent e) {
    String accountType = (String) accountTypeComboBox.getSelectedItem();
    String occupation = (String) occupationComboBox.getSelectedItem();
    String income = incomeField.getText();
    String nid = nidField.getText();
    String passport = passportField.getText();

    if (income.isEmpty() || nid.isEmpty() || passport.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill all required fields.");
    } else {
        try {
            Connn connn = new Connn();
            String query = "INSERT INTO signupTwo (form_no, account_type, occupation, income, nid_no, passport_no) VALUES ('" +
                           formno + "', '" + accountType + "', '" + occupation + "', '" + income + "', '" +
                           nid + "', '" + passport + "')";
            connn.s.executeUpdate(query);

            // Ensure Signup3 class is defined and imported
            new Signup3(formno);
            setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
}


    public static void main(String[] args) {
        new Signup2("");
    }
}
