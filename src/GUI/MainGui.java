package GUI;

import javax.swing.*;
import java.awt.*;

public class MainGui extends javax.swing.JFrame {

    private JDesktopPane desktopPane;
    private JPanel sidePanel;

    public MainGui() {
        initComponents();
        setTitle("Gestion d'Hôtel - Moderne");
        setLocationRelativeTo(null);
        setSize(1000, 700);
    }

    private void initComponents() {
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(245, 245, 245));

        sidePanel = new JPanel();
        sidePanel.setBackground(new Color(30, 30, 30));
        sidePanel.setLayout(new GridLayout(4, 1, 20, 20));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton clientsButton = createSideButton("Clients", "icons/client.png");
        clientsButton.addActionListener(evt -> openClientForm());

        JButton roomsButton = createSideButton("Chambres", "icons/room.png");
        roomsButton.addActionListener(evt -> openRoomForm());

        JButton categoriesButton = createSideButton("Catégories", "icons/category.png");
        categoriesButton.addActionListener(evt -> openCategoryForm());

        JButton reservationsButton = createSideButton("Réservations", "icons/reservation.png");
        reservationsButton.addActionListener(evt -> openReservationForm());

        sidePanel.add(clientsButton);
        sidePanel.add(roomsButton);
        sidePanel.add(categoriesButton);
        sidePanel.add(reservationsButton);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(60, 60, 60));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));

        JLabel headerLabel = new JLabel("Gestion d'Hôtel");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(sidePanel, BorderLayout.WEST);
        getContentPane().add(desktopPane, BorderLayout.CENTER);
    }

    private JButton createSideButton(String text, String iconPath) {
        JButton button = new JButton(text, new ImageIcon(iconPath));
        button.setFocusPainted(false);
        button.setBackground(new Color(40, 40, 40));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
        // Dégradé de fond pour un effet plus moderne
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                ButtonModel model = ((JButton)c).getModel();
                Graphics2D g2d = (Graphics2D) g;
                Color startColor = new Color(60, 60, 60);
                Color endColor = new Color(40, 40, 40);
                if (model.isRollover()) {
                    startColor = new Color(50, 50, 50);
                    endColor = new Color(30, 30, 30);
                }
                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, c.getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
    
                super.paint(g, c);
            }
        });
    
        // Effet de survol (hover)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50)); // changement de fond
            }
    
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(40, 40, 40)); // fond par défaut
            }
        });
    
        return button;
    }
    

    private void openClientForm() {
        ClientForm cf = new ClientForm();
        desktopPane.add(cf);
        cf.setVisible(true);
    }

    private void openRoomForm() {
        ChambreForm chf = new ChambreForm();
        desktopPane.add(chf);
        chf.setVisible(true);
    }

    private void openCategoryForm() {
        CategorieForm catf = new CategorieForm();
        desktopPane.add(catf);
        catf.setVisible(true);
    }

    private void openReservationForm() {
        ReservationForm rf = new ReservationForm();
        if(rf!=null){System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");}
        
        desktopPane.add(rf); // Ajouter le JInternalFrame au JDesktopPane
        rf.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainGui().setVisible(true);
        });
    }
}
