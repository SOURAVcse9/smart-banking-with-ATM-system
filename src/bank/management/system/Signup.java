package bank.management.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Signup extends JFrame implements ActionListener {
    JComboBox<String> occupationComboBox;
    JRadioButton r1, r2, r3;
    JButton next, back;
    Random random = new Random();
    long first4 = (random.nextLong() % 9000L) + 1000L;
    String first = " " + Math.abs(first4);

    JLabel label1, label2, label3, labelName, labelFname, DOB, gender, email, address, phone, nid, passport;
    JTextField textName, textFname, textEmail, textAdd, textPhone, textNid, textPassport;
    JComboBox<String> dayCombo, monthCombo, yearCombo;

    Signup() {
        super("APPLICATION FORM");

        // Bank image loader
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(25, 10, 100, 100);
        add(image);

        // Signup form labels
        label1 = new JLabel("APPLICATION FORM NO:" + first);
        label1.setBounds(160, 10, 600, 40);
        label1.setFont(new Font("Osward", Font.BOLD, 38));
        add(label1);

        label2 = new JLabel("Page 1");
        label2.setBounds(400, 55, 600, 30);
        label2.setFont(new Font("Raleway", Font.BOLD, 25));
        add(label2);

        label3 = new JLabel("Personal Details");
        label3.setBounds(350, 86, 600, 30);
        label3.setFont(new Font("Raleway", Font.BOLD, 25));
        add(label3);

        labelName = new JLabel("Name :");
        labelName.setBounds(100, 120, 100, 30);
        labelName.setFont(new Font("Raleway", Font.BOLD, 20));
        add(labelName);

        textName = new JTextField();
        textName.setBounds(280, 120, 400, 30);
        textName.setFont(new Font("Arial", Font.BOLD, 16));
        add(textName);

        labelFname = new JLabel("Father's Name :");
        labelFname.setBounds(100, 170, 200, 30);
        labelFname.setFont(new Font("Raleway", Font.BOLD, 20));
        add(labelFname);

        textFname = new JTextField();
        textFname.setBounds(280, 170, 400, 30);
        textFname.setFont(new Font("Arial", Font.BOLD, 16));
        add(textFname);

        DOB = new JLabel("Date of Birth : ");
        DOB.setBounds(100, 220, 200, 30);
        DOB.setFont(new Font("Raleway", Font.BOLD, 20));
        add(DOB);

        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        dayCombo = new JComboBox<>(days);
        dayCombo.setBounds(280, 220, 100, 30);
        add(dayCombo);

        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        monthCombo = new JComboBox<>(months);
        monthCombo.setBounds(390, 220, 150, 30);
        add(monthCombo);

        String[] years = new String[100];
        for (int i = 1920; i <= 2019; i++) {
            years[i - 1920] = String.valueOf(i);
        }
        yearCombo = new JComboBox<>(years);
        yearCombo.setBounds(550, 220, 130, 30);
        add(yearCombo);

        gender = new JLabel("Gender :");
        gender.setBounds(100, 280, 100, 30);
        gender.setFont(new Font("Raleway", Font.BOLD, 20));
        add(gender);

        r1 = new JRadioButton("Male");
        r1.setBounds(280, 280, 80, 30);
        r1.setFont(new Font("Arial", Font.BOLD, 18));
        r1.setBackground(new Color(222, 255, 228));
        add(r1);

        r2 = new JRadioButton("Female");
        r2.setBounds(390, 280, 100, 30);
        r2.setFont(new Font("Arial", Font.BOLD, 18));
        r2.setBackground(new Color(222, 255, 228));
        add(r2);

        r3 = new JRadioButton("Other");
        r3.setBounds(550, 280, 100, 30);
        r3.setFont(new Font("Arial", Font.BOLD, 18));
        r3.setBackground(new Color(222, 255, 228));
        add(r3);

        ButtonGroup group = new ButtonGroup();
        group.add(r1);
        group.add(r2);
        group.add(r3);

        email = new JLabel("Email : ");
        email.setBounds(100, 330, 200, 30);
        email.setFont(new Font("Raleway", Font.BOLD, 20));
        add(email);

        textEmail = new JTextField();
        textEmail.setBounds(280, 330, 400, 30);
        textEmail.setFont(new Font("Arial", Font.BOLD, 16));
        add(textEmail);

        phone = new JLabel("Phone : ");
        phone.setBounds(100, 380, 100, 30);
        phone.setFont(new Font("Raleway", Font.BOLD, 20));
        add(phone);

        textPhone = new JTextField();
        textPhone.setBounds(280, 380, 400, 30);
        textPhone.setFont(new Font("Arial", Font.BOLD, 16));
        add(textPhone);

        address = new JLabel("Address : ");
        address.setBounds(100, 430, 200, 30);
        address.setFont(new Font("Raleway", Font.BOLD, 20));
        add(address);

        textAdd = new JTextField();
        textAdd.setBounds(280, 430, 400, 30);
        textAdd.setFont(new Font("Arial", Font.BOLD, 16));
        add(textAdd);

        nid = new JLabel("NID : ");
        nid.setBounds(100, 480, 100, 30);
        nid.setFont(new Font("Raleway", Font.BOLD, 20));
        add(nid);

        textNid = new JTextField();
        textNid.setBounds(280, 480, 400, 30);
        textNid.setFont(new Font("Arial", Font.BOLD, 16));
        add(textNid);

        passport = new JLabel("Passport : ");
        passport.setBounds(100, 530, 150, 30);
        passport.setFont(new Font("Raleway", Font.BOLD, 20));
        add(passport);

        textPassport = new JTextField();
        textPassport.setBounds(280, 530, 400, 30);
        textPassport.setFont(new Font("Arial", Font.BOLD, 16));
        add(textPassport);

        // Occupation
        JLabel occupationLabel = new JLabel("Occupation:");
        occupationLabel.setBounds(100, 580, 150, 30);
        occupationLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        add(occupationLabel);

        String[] occupations = { "Salaried", "Self-Employed", "Business", "Student", "Retired", "Others" };
        occupationComboBox = new JComboBox<>(occupations);
        occupationComboBox.setBounds(280, 580, 400, 30);
        occupationComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        occupationComboBox.setBackground(Color.GRAY);
        add(occupationComboBox);

        // pin = new JLabel("PIN : ");
        // pin.setBounds(100, 600, 100, 30);
        // pin.setFont(new Font("Raleway", Font.BOLD, 20));
        // add(pin);

        // textPin = new JTextField();
        // textPin.setBounds(280, 600, 400, 30);
        // textPin.setFont(new Font("Arial", Font.BOLD, 16));
        // add(textPin);

        next = new JButton("Next");
        next.setBounds(580, 650, 100, 30);
        next.setFont(new Font("Raleway", Font.BOLD, 18));
        next.setBackground(new Color(255, 153, 0));
        next.setForeground(Color.white);
        next.addActionListener(this);
        add(next);

        back = new JButton("Back");
        back.setBounds(280, 650, 100, 30);
        back.setFont(new Font("Raleway", Font.BOLD, 18));
        back.setBackground(new Color(255, 153, 0));
        back.setForeground(Color.white);
        back.addActionListener(this);
        add(back);

        getContentPane().setBackground(new Color(222, 255, 228));
        setLayout(null);
        setSize(850, 830);
        setLocation(360, 0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String formno = first;
        String name = textName.getText();
        String fname = textFname.getText();
        String day = (String) dayCombo.getSelectedItem();
        String month = (String) monthCombo.getSelectedItem();
        String year = (String) yearCombo.getSelectedItem();

        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        int monthIndex = Arrays.asList(months).indexOf(month) + 1;
        String monthFormatted = (monthIndex < 10 ? "0" : "") + monthIndex;
        String dob = year + "-" + monthFormatted + "-" + (day.length() == 1 ? "0" + day : day);

        String gender = null;
        if (r1.isSelected()) {
            gender = "Male";
        } else if (r2.isSelected()) {
            gender = "Female";
        } else if (r3.isSelected()) {
            gender = "Others";
        }

        String email = textEmail.getText();
        String address = textAdd.getText();
        String phone = textPhone.getText();
        String nid = textNid.getText();
        String passport = textPassport.getText();
        String occupation = (String) occupationComboBox.getSelectedItem();

        // String pin = textPin.getText();

        try {
            if (name.equals("") || fname.equals("") || address.equals("") || phone.equals("") || nid.equals("")
                    || occupation.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the required fields");
            } else {
                // Database connection setup
                // Connn c1 = new Connn();

                /*
                 * / SQL query for inserting data into signup table
                 * String query =
                 * "INSERT INTO signup (form_no, name, father_name, DOB, gender, email, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                 * ;
                 * 
                 * PreparedStatement pstmt = c1.c.prepareStatement(query);
                 * pstmt.setString(1, formno);
                 * pstmt.setString(2, name);
                 * pstmt.setString(3, fname);
                 * pstmt.setString(4, dob);
                 * pstmt.setString(5, gender);
                 * pstmt.setString(6, email);
                 * pstmt.setString(7, address);
                 * pstmt.setString(8, phone);
                 * // pstmt.setString(9, pin);
                 * pstmt.executeUpdate();
                 */
                Connn c1 = new Connn();

                String query = "INSERT INTO users (form_no,name,fathername,dob,gender,email,phone,address,nid_number,passport_number,occupation) VALUES ('"
                        +
                        formno + "', '" + name + "', '" + fname + "', '" + dob + "', '" + gender + "', '" + email
                        + "' , '" + phone + "', '" + address + "', '" + nid + "', '" + passport + "', '" + occupation
                        + "')";
                c1.s.executeUpdate(query);

                // pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Form Submitted Successfully");
                
                new Signup3(formno);
                setVisible(false);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in submission: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
