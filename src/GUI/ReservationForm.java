package GUI;

import entities.Reservation;
import service.ReservationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReservationForm extends JInternalFrame {

    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private ReservationService reservationService;

    public ReservationForm() {
        reservationService = new ReservationService();

        // Initialisation de la fenêtre
        setTitle("Liste des Réservations");
        setSize(800, 600);
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);

        // Création des composants
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Date Début", "Date Fin", "Chambre ID", "Client ID"},
                0
        );
        reservationTable = new JTable(tableModel);

        // Ajout de la table dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(reservationTable);

        // Bouton pour rafraîchir les données
        JButton refreshButton = new JButton("Rafraîchir");
        refreshButton.addActionListener(e -> loadReservations());

        // Mise en page
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        // Ajouter le panneau à la fenêtre
        add(panel);

        // Charger les données initiales
        loadReservations();
    }

    private void loadReservations() {
        // Effacer les anciennes données
        tableModel.setRowCount(0);

        // Charger les nouvelles données
        List<Reservation> reservations = reservationService.findAll();
        for (Reservation reservation : reservations) {
            tableModel.addRow(new Object[]{
                    reservation.getId(),
                    reservation.getDatedebut(), 
                    reservation.getDatefin(),
                    reservation.getChambre().getId(),
                    reservation.getClient().getId()
            });
        }
    }
}
