package bank.management.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import static java.awt.Image.SCALE_DEFAULT;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class PinChange extends JFrame implements ActionListener {

    JButton b1, b2;
    JPasswordField p1, p2;
    String pin;

    public PinChange(String pin) {
        this.pin = pin;

        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 1550, 830);
        background.setLayout(null);
        add(background);

        // Instruction Label
        JLabel instructionLabel = new JLabel("CHANGE YOUR PIN");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("System", Font.BOLD, 25));
        instructionLabel.setBounds(540, 170, 600, 30);
        background.add(instructionLabel);

        // New Pin Label
        JLabel newPin = new JLabel("NEW PIN:");
        newPin.setForeground(Color.WHITE);
        newPin.setFont(new Font("System", Font.BOLD, 20));
        newPin.setBounds(430, 240, 150, 30);
        background.add(newPin);

        // New Pin Field
        p1 = new JPasswordField();
        p1.setBackground(Color.GRAY);
        p1.setForeground(Color.WHITE);
        p1.setFont(new Font("Raleway", Font.BOLD, 30));
        p1.setBounds(580, 240, 250, 40);
        background.add(p1);

        // Confirm Pin Label
        JLabel confirmPin = new JLabel("CONFIRM PIN:");
        confirmPin.setForeground(Color.WHITE);
        confirmPin.setFont(new Font("System", Font.BOLD, 20));
        confirmPin.setBounds(430, 300, 150, 30);
        background.add(confirmPin);

        // Confirm Pin Field
        p2 = new JPasswordField();
        p2.setBackground(Color.GRAY);
        p2.setForeground(Color.WHITE);
        p2.setFont(new Font("Raleway", Font.BOLD, 30));
        p2.setBounds(580, 300, 250, 40);
        background.add(p2);

        // Submit Button
        b1 = new JButton("CHANGE");
        b1.setBackground(Color.BLUE);
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Arial", Font.BOLD, 20));
        b1.setBounds(700, 370, 130, 35);
        b1.addActionListener(this);
        background.add(b1);

        // Cancel Button
        b2 = new JButton("BACK");
        b2.setBackground(Color.RED);
        b2.setForeground(Color.WHITE);
        b2.setFont(new Font("Arial", Font.BOLD, 20));
        b2.setBounds(700, 410, 130, 35);
        b2.addActionListener(this);
        background.add(b2);

        // Frame Properties
        setTitle("ATM Simulator");
        setLayout(null);
        setSize(1550, 830);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String pin1 = new String(p1.getPassword());
            String pin2 = new String(p2.getPassword());

            if (e.getSource() == b1) { 
                if (pin1.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter New PIN", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (pin2.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Confirm PIN", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!pin1.equals(pin2)) {
                    JOptionPane.showMessageDialog(null, "PINs do not match", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Connn c1 = new Connn();

                String q2 = "UPDATE login SET pin_no='" + pin1 + "' WHERE pin_no='" + pin + "'";
                c1.s.executeUpdate(q2);

                JOptionPane.showMessageDialog(null, "PIN changed successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                setVisible(false);
                new Main_class(pin1);

            } else if (e.getSource() == b2) { 
                new Main_class(pin);
                setVisible(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new PinChange("");
    }
}
