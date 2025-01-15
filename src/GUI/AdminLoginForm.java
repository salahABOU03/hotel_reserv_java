package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AdminLoginForm() {
        // Initialisation de la fenêtre
        setTitle("Connexion Admin");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création des composants
        JLabel usernameLabel = new JLabel("Nom d'utilisateur :");
        JLabel passwordLabel = new JLabel("Mot de passe :");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Se connecter");
        loginButton.setBackground(new Color(50, 50, 50));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.addActionListener(new LoginActionListener());

        // Mise en page avec un design moderne
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        usernameField.setBackground(new Color(50, 50, 50));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        // Ajouter le panneau à la fenêtre
        add(panel);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
    
            // Vérification des identifiants (simple exemple)
            if (username.equals("admin") && password.equals("admin123")) {
              //  JOptionPane.showMessageDialog(AdminLoginForm.this, "Connexion réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                
                // Ouvrir MainGui après connexion réussie
                SwingUtilities.invokeLater(() -> {
                    MainGui mainGui = new MainGui();
                    mainGui.setVisible(true);
                });
    
                // Fermer la fenêtre de connexion
                dispose();
            } else {
                JOptionPane.showMessageDialog(AdminLoginForm.this, "Nom d'utilisateur ou mot de passe incorrect !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminLoginForm loginForm = new AdminLoginForm();
            loginForm.setVisible(true);
        });
    }
}
