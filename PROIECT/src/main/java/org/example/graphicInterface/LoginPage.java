package org.example.graphicInterface;

import org.example.JsonInput;
import org.example.player.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginPage extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private ArrayList<Account> accounts;
    private JLabel errorLabel;

    public LoginPage() {
        // incarcarea conturilor
        accounts = JsonInput.deserializeAccounts();

        setTitle("League of Warriors - Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // titlul jocului
        JLabel gameTitleLabel = new JLabel("    ~   L E A G U E    O F    W A R R I O R S   ~", SwingConstants.CENTER);
        gameTitleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        gameTitleLabel.setForeground(Color.WHITE);
        gameTitleLabel.setPreferredSize(new Dimension(1500, 200));
        gameTitleLabel.setBackground(new Color(58, 0, 0));
        gameTitleLabel.setOpaque(true);
        add(gameTitleLabel, BorderLayout.NORTH);

        // fundal
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(58, 0, 0));
        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));
        add(backgroundPanel);

        // chenarul de login
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(108, 0, 0, 255));
        loginPanel.setPreferredSize(new Dimension(300, 400));
        loginPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        loginPanel.setLayout(null);
        backgroundPanel.add(loginPanel);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 40, 300, 40);
        loginPanel.add(titleLabel);

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(new Font("Serif", Font.BOLD, 16));
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(50, 100, 200, 20);
        loginPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(50, 130, 200, 30);
        loginPanel.add(emailField);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Serif", Font.BOLD, 16));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 180, 200, 20);
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 210, 200, 30);
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Serif", Font.BOLD, 16));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(58, 0, 0, 255));
        loginButton.setBounds(50, 300, 200, 50);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyLogin();
            }
        });
        loginPanel.add(loginButton);

        // erori
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(0, 360, 300, 30);
        loginPanel.add(errorLabel);

        setVisible(true);
    }

    private void verifyLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        for (Account account : accounts) {
            if (account.getInformation().getEmail().equals(email) &&
                    account.getInformation().getPassword().equals(password)) {
                errorLabel.setText("Login successful!");
                account.setPhotos();
                new CharacterSelectionPage(account);
                dispose();
                return;
            }
        }
        errorLabel.setText("Invalid email or password.");
    }
}
