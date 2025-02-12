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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
    JLabel label1, label2, label3;
    JTextField text2;
    JPasswordField text3;
    String formno;
    JButton b1, b2, b3,b4;

    Login(String formno) {
        super("Bank Management System");
        this.formno = formno;

        // bank image loader
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 10, 100, 100);
        add(image);

        // card image loader
        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icon/card.png"));
        Image ii2 = ii1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel iimage = new JLabel(ii3);
        iimage.setBounds(630, 350, 100, 100);
        add(iimage);

        // login form labels
        label1 = new JLabel("WELCOME TO BANK");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Osward", Font.BOLD, 38));
        label1.setBounds(230, 125, 450, 40);
        add(label1);

        // lebel 2
        label2 = new JLabel("Card No:");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Raleway", Font.BOLD, 28));
        label2.setBounds(150, 190, 375, 30);
        add(label2);

        // text 2
        text2 = new JTextField(15);
        text2.setBounds(300, 190, 230, 30);
        text2.setFont(new Font("Arial", Font.BOLD, 16));
        add(text2);

        // lebel 3
        label3 = new JLabel("PIN:");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("Raleway", Font.BOLD, 28));
        label3.setBounds(150, 250, 375, 30);
        add(label3);

        // text 3//password field
        text3 = new JPasswordField(15);
        text3.setBounds(300, 250, 230, 30);
        text3.setFont(new Font("Arial", Font.BOLD, 16));
        add(text3);

        // login button
        b1 = new JButton("LOG IN");
        b1.setFont(new Font("Arial", Font.BOLD, 16));
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setBounds(300, 300, 100, 30);
        b1.addActionListener(this);
        add(b1);

        // clear button
        b2 = new JButton("CLEAR");
        b2.setFont(new Font("Arial", Font.BOLD, 16));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.setBounds(430, 300, 100, 30);
        b2.addActionListener(this);
        add(b2);

        // signup button
        b3 = new JButton("SIGN UP");
        b3.setFont(new Font("Arial", Font.BOLD, 16));
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);
        b3.setBounds(300, 350, 230, 30);
        b3.addActionListener(this);
        add(b3);

        //forgot password button
        b4 = new JButton("FORGOT PIN");
        b4.setFont(new Font("Arial", Font.BOLD, 16));
        b4.setBackground(Color.BLACK);
        b4.setForeground(Color.WHITE);
        b4.setBounds(300, 400, 230, 30);
        b4.addActionListener(this);
        add(b4);

        // background image loader
        ImageIcon iii1 = new ImageIcon(ClassLoader.getSystemResource("icon/backbg.png"));
        Image iii2 = iii1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon iii3 = new ImageIcon(iii2);
        JLabel iiimage = new JLabel(iii3);
        iiimage.setBounds(0, 0, 850, 480);
        add(iiimage);

        // login form framework
        setLayout(null);
        setSize(850, 480);
        setLocation(400, 200);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == b1) {
                Connn c1 = new Connn();
                String cardno = text2.getText();
                String pin = new String(text3.getPassword());
                String q = "select * from login where card_no='" + cardno + "' and pin_no='" + pin + "'";
                ResultSet rs = c1.s.executeQuery(q);
                if (rs.next()) {
                    setVisible(false);
                    new Main_class(pin);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Invalid Card Number or PIN");
                }
            } else if (e.getSource() == b2) {
                text2.setText("");
                text3.setText("");
            } else if (e.getSource() == b3) {
                setVisible(false);
                new Signup();
            }
            else if (e.getSource() == b4) {
                setVisible(false);
                new ForgotPin();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Login("");

    }

}
