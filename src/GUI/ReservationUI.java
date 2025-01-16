package GUI ; 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import entities.Chambre;
import entities.Categorie;
import entities.Client;
import entities.Reservation;
import service.ChambreService;
import service.ClientService;
import service.ReservationService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ReservationUI extends JFrame {

    private JPanel contentPane;
    private JList<Chambre> chambreList;
    private JTextField clientNameField;
    private JTextField clientPhoneField;
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;
    private JButton reserveButton;

    private final ReservationService reservationService = new ReservationService();
    private final ChambreService chambreService = new ChambreService();
    private final ClientService clientService = new ClientService();

    public ReservationUI() {
        setTitle("Reservation Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        // Main content panel
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.DARK_GRAY);
        JLabel headerLabel = new JLabel("Reservation Form", SwingConstants.CENTER);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        contentPane.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.BLACK);
        formPanel.setLayout(new BorderLayout(10, 10));

        // Chambre list panel
        JPanel chambrePanel = new JPanel();
        chambrePanel.setLayout(new BorderLayout());
        chambrePanel.setBackground(Color.BLACK);
        chambrePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Available Rooms", 0, 0, new Font("Arial", Font.BOLD, 16), Color.WHITE));

        DefaultListModel<Chambre> chambreListModel = new DefaultListModel<>();
        List<Chambre> chambres = chambreService.findAll();
        for (Chambre chambre : chambres) {
            chambreListModel.addElement(chambre);
        }

        chambreList = new JList<>(chambreListModel);
        chambreList.setCellRenderer(new ChambreRenderer());
        chambrePanel.add(new JScrollPane(chambreList), BorderLayout.CENTER);

        formPanel.add(chambrePanel, BorderLayout.WEST);

        // Client and date panel
        JPanel clientDatePanel = new JPanel();
        clientDatePanel.setLayout(new GridLayout(4, 2, 10, 10));
        clientDatePanel.setBackground(Color.BLACK);

        JLabel clientNameLabel = new JLabel("Client Name:");
        clientNameLabel.setForeground(Color.WHITE);
        clientNameField = new JTextField();

        JLabel clientPhoneLabel = new JLabel("Client Phone:");
        clientPhoneLabel.setForeground(Color.WHITE);
        clientPhoneField = new JTextField();

        JLabel startDateLabel = new JLabel("Start Date:");
        startDateLabel.setForeground(Color.WHITE);
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd"));

        JLabel endDateLabel = new JLabel("End Date:");
        endDateLabel.setForeground(Color.WHITE);
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd"));

        clientDatePanel.add(clientNameLabel);
        clientDatePanel.add(clientNameField);
        clientDatePanel.add(clientPhoneLabel);
        clientDatePanel.add(clientPhoneField);
        clientDatePanel.add(startDateLabel);
        clientDatePanel.add(startDateSpinner);
        clientDatePanel.add(endDateLabel);
        clientDatePanel.add(endDateSpinner);

        formPanel.add(clientDatePanel, BorderLayout.CENTER);

        contentPane.add(formPanel, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.DARK_GRAY);
        reserveButton = new JButton("Reserve");
        reserveButton.setBackground(Color.WHITE);
        reserveButton.setForeground(Color.BLACK);
        reserveButton.setFont(new Font("Arial", Font.BOLD, 16));

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeReservation();
            }
        });

        footerPanel.add(reserveButton);
        contentPane.add(footerPanel, BorderLayout.SOUTH);
    }

    private void makeReservation() {
        try {
            Chambre selectedChambre = chambreList.getSelectedValue();
            if (selectedChambre == null) {
                JOptionPane.showMessageDialog(this, "Please select a room!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String clientName = clientNameField.getText().trim();
            String clientPhone = clientPhoneField.getText().trim();
            if (clientName.isEmpty() || clientPhone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please provide client name and phone!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date startDate = (Date) startDateSpinner.getValue();
            Date endDate = (Date) endDateSpinner.getValue();

            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(this, "Start date cannot be after end date!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Client client = new Client(clientName, ".", clientPhone, ".");
            clientService.create(client);
            Reservation reservation = new Reservation(0, startDate, endDate, selectedChambre, client);
            boolean success = reservationService.create(reservation);

            if (success) {
                sendEmail(clientName, "Reservation Confirmation", "Your reservation is confirmed for room " + selectedChambre.getId());
                JOptionPane.showMessageDialog(this, "Reservation successful! A confirmation email has been sent.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create reservation.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendEmail(String recipientEmail, String subject, String content) throws MessagingException {
        String host = "smtp.gmail.com"; // Replace with your SMTP server
        String from = "se55233@gmail.com";
        String password = "oahv nefa aedx xbov";

        // Configure session properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        // Create the message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject(subject);
        message.setText(content);

        // Send the message
        Transport.send(message);
        System.out.println("Email sent successfully!");
    }

    private static class ChambreRenderer extends JPanel implements ListCellRenderer<Chambre> {

        private JLabel imageLabel;
        private JLabel infoLabel;

        public ChambreRenderer() {
            setLayout(new BorderLayout(10, 10));
            setBackground(Color.BLACK);
            imageLabel = new JLabel();
            infoLabel = new JLabel();
            infoLabel.setForeground(Color.WHITE);
            infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            add(imageLabel, BorderLayout.WEST);
            add(infoLabel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Chambre> list, Chambre chambre, int index, boolean isSelected, boolean cellHasFocus) {
            infoLabel.setText("Room " + chambre.getId() + " - " + chambre.getCategorie() + " - Phone: " + chambre.getTelephone());

            if (chambre.getImage() != null) {
                ImageIcon icon = new ImageIcon(new ImageIcon(chambre.getImage()).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
            } else {
                imageLabel.setIcon(null);
            }

            setBackground(isSelected ? Color.GRAY : Color.BLACK);
            return this;
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ReservationUI frame = new ReservationUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
