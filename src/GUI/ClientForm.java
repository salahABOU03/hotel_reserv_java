package GUI;

import entities.Client;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import service.ClientService;

public class ClientForm extends javax.swing.JInternalFrame {

    private ClientService service;
    private DefaultTableModel tableModel;

    public ClientForm() {
        initComponents();
        setResizable(true);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        service = new ClientService();
        setupTable();
        loadClientData();
    }

    private void setupTable() {
        tableModel = (DefaultTableModel) jTable1.getModel();

        // Add selection listener to table
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = jTable1.getSelectedRow();
                if (row != -1) {
                    jTextField1.setText((String) tableModel.getValueAt(row, 1)); // nom
                    jTextField2.setText((String) tableModel.getValueAt(row, 2)); // prenom
                    jTextField3.setText((String) tableModel.getValueAt(row, 3)); // telephone
                    jTextField4.setText((String) tableModel.getValueAt(row, 4)); // email
                }
            }
        });

        // Add action listeners to buttons
        jButton1.addActionListener(e -> insertClient()); // Insert button
        jButton2.addActionListener(e -> updateClient()); // Update button
        jButton3.addActionListener(e -> deleteClient()); // Delete button
    }

    private void loadClientData() {
        List<Client> clients = service.findAll();

        for (Client client : clients) {
            Object[] row = {
                client.getId(),
                client.getNom(),
                client.getPrenom(),
                client.getTelephone(),
                client.getEmail()
            };
            tableModel.addRow(row);
        }
    }

    private void insertClient() {
        Client client = new Client(
            jTextField1.getText(), // nom
            jTextField2.getText(), // prenom
            jTextField3.getText(), // telephone
            jTextField4.getText()  // email
        );

        if (service.create(client)) {
            clearFields();
            loadClientData();
            JOptionPane.showMessageDialog(this, "Client added successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Error adding client!");
        }
    }

    private void updateClient() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a client to update!");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        Client client = new Client(
            id,
            jTextField1.getText(), // nom
            jTextField2.getText(), // prenom
            jTextField3.getText(), // telephone
            jTextField4.getText()  // email
        );

        if (service.update(client)) {
            clearFields();
            loadClientData();
            JOptionPane.showMessageDialog(this, "Client updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Error updating client!");
        }
    }

    private void deleteClient() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a client to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this client?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            Client client = new Client(id);

            if (service.delete(client)) {
                clearFields();
                loadClientData();
                JOptionPane.showMessageDialog(this, "Client deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting client!");
            }
        }
    }

    private void clearFields() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255)); // Blanc
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Client"));

        jTextField1.setBackground(new java.awt.Color(240, 240, 240)); // Gris clair
        jTextField2.setBackground(new java.awt.Color(240, 240, 240)); 
        jTextField3.setBackground(new java.awt.Color(240, 240, 240)); 
        jTextField4.setBackground(new java.awt.Color(240, 240, 240)); 

        jButton1.setText("Ajouter");
        jButton1.setBackground(new java.awt.Color(0, 0, 0)); // Noir
        jButton1.setForeground(new java.awt.Color(255, 255, 255)); // Blanc
        jButton1.addActionListener(evt -> insertClient());

        jButton2.setText("Modifier");
        jButton2.setBackground(new java.awt.Color(0, 0, 0)); // Noir
        jButton2.setForeground(new java.awt.Color(255, 255, 255)); // Blanc

        jButton3.setText("Supprimer");
        jButton3.setBackground(new java.awt.Color(0, 0, 0)); // Noir
        jButton3.setForeground(new java.awt.Color(255, 255, 255)); // Blanc

        jLabel1.setText("Nom");
        jLabel2.setText("Prenom");
        jLabel3.setText("Telephone");
        jLabel4.setText("Email");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jTextField4))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2)
                            .addComponent(jTextField1))))
                .addGap(84, 84, 84))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(jButton1)
                .addGap(86, 86, 86)
                .addComponent(jButton2)
                .addGap(85, 85, 85)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(31, 31, 31))
        );

        tablePanel.setBackground(new java.awt.Color(0, 0, 0)); // Noir
        tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("List"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"id", "nom", "prenom", "telephone", "email"}
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
                .addContainerGap())
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel tablePanel; 
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration
}
