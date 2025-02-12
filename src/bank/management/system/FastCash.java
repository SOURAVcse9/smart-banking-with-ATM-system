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
import javax.swing.JOptionPane;

public class FastCash extends JFrame implements ActionListener {
    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    public FastCash(String pin) {
        this.pin = pin;

        // Setting up the background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 1550, 830);
        background.setLayout(null);
        add(background);

        // Header Labels
        JLabel l1 = new JLabel("Please Insert Your Card");
        l1.setBounds(980, 120, 600, 30);
        l1.setFont(new Font("Arial", Font.BOLD, 20));
        l1.setForeground(Color.GREEN);
        background.add(l1);

        JLabel l2 = new JLabel("SELECT WITHDRAWAL AMOUNT");
        l2.setBounds(500, 180, 500, 30);
        l2.setFont(new Font("Arial", Font.BOLD, 20));
        l2.setForeground(Color.MAGENTA);
        background.add(l2);

        // Buttons for fast cash options
        b1 = createButton("Tk. 100", 405, 272, background);
        b2 = createButton("Tk. 500", 673, 272, background);
        b3 = createButton("Tk. 1000", 405, 316, background);
        b4 = createButton("Tk. 2000", 673, 316, background);
        b5 = createButton("Tk. 5000", 405, 359, background);
        b6 = createButton("Tk. 10000", 673, 359, background);

        // Back button
        b7 = new JButton("BACK");
        b7.setForeground(Color.WHITE);
        b7.setFont(new Font("Arial", Font.BOLD, 22));
        b7.setBackground(Color.RED);
        b7.setBounds(673, 405, 180, 35);
        b7.addActionListener(this);
        background.add(b7);

        // JFrame settings
        setSize(1550, 1080);
        setTitle("ATM SYSTEM");
        setLocation(0, 0);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Helper method to create buttons
    private JButton createButton(String text, int x, int y, JLabel background) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(65, 125, 128));
        button.setBounds(x, y, 150, 35);
        button.addActionListener(this);
        background.add(button);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b7) {
            setVisible(false);
            new Main_class(pin);
        } else {
            String amountText = ((JButton) e.getSource()).getText().substring(4).trim();
            double amount = Double.parseDouble(amountText);
            Connn c1 = new Connn();

            try {
                ResultSet rsCard = c1.s.executeQuery("SELECT card_no FROM login WHERE pin_no = '" + pin + "'");
                String cardNo = "";
                if (rsCard.next()) {
                    cardNo = rsCard.getString("card_no");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid PIN. Please try again.");
                    return;
                }
                ResultSet rsBalance = c1.s
                        .executeQuery("SELECT balance FROM accounts WHERE card_no = '" + cardNo + "'");
                double balance = 0;
                if (rsBalance.next()) {
                    balance = rsBalance.getDouble("balance");
                }

                if (balance < amount) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                } else {
                    double newBalance = balance - amount;
                    c1.s.executeUpdate(
                            "UPDATE accounts SET balance = " + newBalance + " WHERE card_no = '" + cardNo + "'");
                    c1.s.executeUpdate(
                            "INSERT INTO transactions (account_id, transaction_type, amount, description) " +
                                    "VALUES ((SELECT account_id FROM accounts WHERE card_no = '" + cardNo
                                    + "'), 'Withdraw', " +
                                    amount + ", 'Fast Cash Withdrawal')"
                                    );
                    JOptionPane.showMessageDialog(null, "Tk. " + amount + " Withdrawn Successfully");
                }

                rsCard.close();
                rsBalance.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            setVisible(false);
            new Main_class(pin);
        }
    }

    public static void main(String[] args) {
        new FastCash(""); // Example PIN for testing
    }
}
