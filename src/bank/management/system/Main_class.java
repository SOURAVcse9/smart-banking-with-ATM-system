package bank.management.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main_class extends JFrame implements ActionListener {
    JButton b1, b2, b3, b4, b5, b6, b7, b8;
    String pin;

    public Main_class(String pin) {
        this.pin = pin;

        // Create all the components and add them to the frame
        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 1550, 830);
        background.setLayout(null);
        add(background);

        // Instruction Label
        JLabel instructionLabel = new JLabel("Welcome to GS Bank ATM");
        instructionLabel.setForeground(java.awt.Color.WHITE);
        instructionLabel.setBounds(440, 140, 600, 50);
        instructionLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));
        background.add(instructionLabel);

        // Add the rest of the components here
        JLabel l1 = new JLabel("Please Insert Your Card");
        l1.setBounds(980, 120, 600, 30);
        l1.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        l1.setForeground(java.awt.Color.green);
        background.add(l1);

        JLabel l2 = new JLabel("Select Your Transaction");
        l2.setBounds(500, 180, 500, 30);
        l2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        l2.setForeground(java.awt.Color.MAGENTA);
        background.add(l2);

        // deposit
        b1 = new JButton("DEPOSIT");
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Arial", Font.BOLD, 20));
        b1.setBackground(new Color(65, 125, 128));
        b1.setBounds(405, 272, 150, 35);
        b1.addActionListener(this);
        background.add(b1);

        // withdraw
        b2 = new JButton("WITHDRAW");
        b2.setForeground(Color.WHITE);
        b2.setFont(new Font("Arial", Font.BOLD, 20));
        b2.setBackground(new Color(65, 125, 128));
        b2.setBounds(673, 272, 180, 35);
        b2.addActionListener(this);
        background.add(b2);

        // fast cash
        b3 = new JButton("FAST CASH");
        b3.setForeground(Color.WHITE);
        b3.setFont(new Font("Arial", Font.BOLD, 20));
        b3.setBackground(new Color(65, 125, 128));
        b3.setBounds(405, 316, 150, 35);
        b3.addActionListener(this);
        background.add(b3);

        // mini statement
        b4 = new JButton("MINI STATEMENT");
        b4.setForeground(Color.WHITE);
        b4.setFont(new Font("Arial", Font.BOLD, 15));
        b4.setBackground(new Color(65, 125, 128));
        b4.setBounds(673, 316, 180, 35);
        b4.addActionListener(this);
        background.add(b4);

        // pin change
        b5 = new JButton("PIN CHANGE");
        b5.setForeground(Color.WHITE);
        b5.setFont(new Font("Arial", Font.BOLD, 18));
        b5.setBackground(new Color(65, 125, 128));
        b5.setBounds(405, 359, 150, 35);
        b5.addActionListener(this);
        background.add(b5);

        // balance enquiry
        b6 = new JButton("BALANCE ENQUIRY");
        b6.setForeground(Color.WHITE);
        b6.setFont(new Font("Arial", Font.BOLD, 15));
        b6.setBackground(new Color(65, 125, 128));
        b6.setBounds(673, 359, 180, 35);
        b6.addActionListener(this);
        background.add(b6);

        // exit
        b7 = new JButton("EXIT");
        b7.setForeground(Color.WHITE);
        b7.setFont(new Font("Arial", Font.BOLD, 22));
        b7.setBackground(new Color(255, 0, 0));
        b7.setBounds(673, 405, 180, 35);
        b7.addActionListener(this);
        background.add(b7);

        // money transfer
        b8 = new JButton("MONEY TRANSFER");
        b8.setForeground(Color.WHITE);
        b8.setFont(new Font("Arial", Font.BOLD, 12));
        b8.setBackground(new Color(65, 125, 128));
        b8.setBounds(405, 405, 150, 35);
        b8.addActionListener(this);
        background.add(b8);

        setSize(1550, 1080);
        setTitle("ATM SYSTEM");
        setLocation(0, 0);
        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            new Deposit(pin);
            setVisible(false);
        } else if (e.getSource() == b7) {
            System.exit(0);
        } else if (e.getSource() == b2) {
            new Withdraw(pin);
            setVisible(false);

        } else if (e.getSource() == b6) {
            new BalanceEnquriy(pin);
            setVisible(false);
        } else if (e.getSource() == b3) {
            new FastCash(pin);
            setVisible(false);
        } else if (e.getSource() == b5) {
            new PinChange(pin);
            setVisible(false);
        } else if (e.getSource() == b4) {
            new MiniStatement(pin);
            // setVisible(false);
        } else if (e.getSource() == b8) {
            new Transfer(pin);
            setVisible(false);
        }

    }

    public static void main(String[] args) {
        new Main_class("");
    }
}
