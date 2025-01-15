package GUI;

import entities.Categorie;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import service.CategorieService;

public class CategorieForm extends javax.swing.JInternalFrame {

    private CategorieService service;
    private DefaultTableModel tableModel;

    public CategorieForm() {
        initComponents();
        configureFrame();
        service = new CategorieService();
        setupTable();
        loadCategorieData();
    }

    private void configureFrame() {
        setResizable(true);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
    }

    private void setupTable() {
        tableModel = (DefaultTableModel) jTable1.getModel();
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = jTable1.getSelectedRow();
                if (row != -1) {
                    jTextField1.setText((String) tableModel.getValueAt(row, 1)); // code
                    jTextField2.setText((String) tableModel.getValueAt(row, 2)); // libelle
                }
            }
        });

        jButton1.addActionListener(e -> insertOrUpdateCategorie(true));
        jButton2.addActionListener(e -> insertOrUpdateCategorie(false));
        jButton3.addActionListener(e -> deleteCategorie());
    }

    private void loadCategorieData() {
        tableModel.setRowCount(0);
        List<Categorie> categories = service.findAll();
        categories.forEach(categorie -> {
            Object[] row = {categorie.getId(), categorie.getCode(), categorie.getLibelle()};
            tableModel.addRow(row);
        });
    }

    private void insertOrUpdateCategorie(boolean isInsert) {
        String code = jTextField1.getText().trim();
        String libelle = jTextField2.getText().trim();

        if (code.isEmpty() || libelle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        Categorie categorie = isInsert ? new Categorie(code, libelle) : getSelectedCategorie(code, libelle);
        
        boolean success = isInsert ? service.create(categorie) : service.update(categorie);
        String message = isInsert ? "added" : "updated";

        if (success) {
            clearFields();
            loadCategorieData();
            JOptionPane.showMessageDialog(this, "Category " + message + " successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Error " + message + " category!");
        }
    }

    private Categorie getSelectedCategorie(String code, String libelle) {
        int row = jTable1.getSelectedRow();
        int id = (int) tableModel.getValueAt(row, 0);
        return new Categorie(id, code, libelle);
    }

    private void deleteCategorie() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this category?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            Categorie categorie = new Categorie(id);

            if (service.delete(categorie)) {
                clearFields();
                loadCategorieData();
                JOptionPane.showMessageDialog(this, "Category deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting category!");
            }
        }
    }

    private void clearFields() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTable1.clearSelection();
    }

    // Generated GUI code...
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setTitle("Categorie Form");

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Categorie"));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("code");
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("libelle");
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Ajouter");
        jButton1.setBackground(new java.awt.Color(169, 169, 169));
        jButton1.setForeground(new java.awt.Color(0, 0, 0));

        jButton2.setText("Modifier");
        jButton2.setBackground(new java.awt.Color(169, 169, 169));
        jButton2.setForeground(new java.awt.Color(0, 0, 0));

        jButton3.setText("Supprimer");
        jButton3.setBackground(new java.awt.Color(169, 169, 169));
        jButton3.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGap(97, 97, 97))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(jButton1)
                .addGap(140, 140, 140)
                .addComponent(jButton2)
                .addGap(121, 121, 121)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(26, 26, 26))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("List"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "id", "code", "libelle"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
