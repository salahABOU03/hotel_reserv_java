package GUI;

import service.CategorieService;
import service.ChambreService;
import entities.Categorie;
import entities.Chambre;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;

public class ChambreForm extends javax.swing.JInternalFrame {

    private ChambreService service;
    private CategorieService categorieService;
    private DefaultTableModel tableModel;

    public ChambreForm() {
        initComponents();
        setResizable(true);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);

        service = new ChambreService();
        categorieService = new CategorieService();
        setupTable();
        loadCategories();
        loadChambreData();
    }

    private void setupTable() {
        tableModel = (DefaultTableModel) jTable1.getModel();

        // Add selection listener to table
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = jTable1.getSelectedRow();
                if (row != -1) {
                    jTextField1.setText((String) tableModel.getValueAt(row, 1)); // telephone
                    String categorieName = (String) tableModel.getValueAt(row, 2);
                    for (int i = 0; i < jComboBox1.getItemCount(); i++) {
                        Categorie cat = (Categorie) jComboBox1.getItemAt(i);
                        if (cat.getLibelle().equals(categorieName)) {
                            jComboBox1.setSelectedIndex(i);
                            break;
                        }
                    }

                    // Afficher l'image de la chambre dans le label
                    byte[] imageBytes = (byte[]) tableModel.getValueAt(row, 3);
                    if (imageBytes != null) {
                        ImageIcon imageIcon = new ImageIcon(imageBytes);
                        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        jLabelImage.setIcon(new ImageIcon(image));
                    }
                }
            }
        });

        // Add action listeners to buttons
        jButton1.addActionListener(e -> insertChambre()); // Insert button
        jButton2.addActionListener(e -> updateChambre()); // Update button
        jButton3.addActionListener(e -> deleteChambre()); // Delete button
    }

    private void loadCategories() {
        List<Categorie> categories = categorieService.findAll();
        for (Categorie categorie : categories) {
            jComboBox1.addItem(categorie);
        }
    }

    private void loadChambreData() {
        tableModel.setRowCount(0);
        List<Chambre> chambres = service.findAll();

        for (Chambre chambre : chambres) {
            Object[] row = {
                chambre.getId(),
                chambre.getTelephone(),
                chambre.getCategorie().getLibelle(),
                chambre.getImage() // Ajouter l'image en tant que byte[]
            };
            tableModel.addRow(row);
        }
    }

    private void insertChambre() {
        Categorie selectedCategorie = (Categorie) jComboBox1.getSelectedItem();
        if (selectedCategorie == null) {
            JOptionPane.showMessageDialog(this, "Please select a category!");
            return;
        }

        byte[] image = loadImage(); // Get the image byte array

        Chambre chambre = new Chambre(
            jTextField1.getText(), // telephone
            selectedCategorie,     // categorie
            image                  // image
        );

        if (service.create(chambre)) {
            clearFields();
            loadChambreData();
            JOptionPane.showMessageDialog(this, "Chambre added successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Error adding chambre!");
        }
    }

    private void updateChambre() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a chambre to update!");
            return;
        }

        Categorie selectedCategorie = (Categorie) jComboBox1.getSelectedItem();
        if (selectedCategorie == null) {
            JOptionPane.showMessageDialog(this, "Please select a category!");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        byte[] image = loadImage(); // Get the image byte array

        Chambre chambre = new Chambre(
            id,
            jTextField1.getText(), // telephone
            selectedCategorie,     // categorie
            image                  // image
        );

        if (service.update(chambre)) {
            clearFields();
            loadChambreData();
            JOptionPane.showMessageDialog(this, "Chambre updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Error updating chambre!");
        }
    }

    private void deleteChambre() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a chambre to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this chambre?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            Chambre chambre = new Chambre(id);

            if (service.delete(chambre)) {
                clearFields();
                loadChambreData();
                JOptionPane.showMessageDialog(this, "Chambre deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting chambre!");
            }
        }
    }

    private void clearFields() {
        jTextField1.setText("");
        jComboBox1.setSelectedIndex(-1);
        jLabelImage.setIcon(null); // Clear the image label
    }

    // Helper method to load image from file
    private byte[] loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try (InputStream inputStream = fileChooser.getSelectedFile().toURI().toURL().openStream();
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, length);
                }
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading image!");
            }
        }
        return null; // Return null if no image is selected
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelImage = new javax.swing.JLabel(); // Label for displaying the image
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setTitle("Chambre Form");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255)); // White background with a gradient
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Chambre"));

        jButton1.setText("Ajouter");
        jButton1.setBackground(new java.awt.Color(0, 0, 0)); // Black buttons
        jButton1.setForeground(new java.awt.Color(255, 255, 255)); // White text

        jButton2.setText("Modifier");
        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));

        jButton3.setText("Supprimer");
        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<Categorie>());

        jLabel1.setText("Telephone");
        jLabel1.setForeground(new java.awt.Color(0, 0, 0)); // Black text

        jLabel2.setText("Categorie");
        jLabel2.setForeground(new java.awt.Color(0, 0, 0)); // Black text

        jLabelImage.setText(""); // Label to display image

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addComponent(jComboBox1, 0, 642, Short.MAX_VALUE))
                .addGap(78, 78, 78))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(jButton1)
                .addGap(109, 109, 109)
                .addComponent(jButton2)
                .addGap(99, 99, 99)
                .addComponent(jButton3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(30, 30, 30)
                .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255)); // White background
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("List"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "telephone", "categorie", "image"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        jTable1.setRowHeight(100); // Ajustez la hauteur des lignes pour correspondre à la taille des images

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof byte[]) {
            byte[] imageBytes = (byte[]) value;
            if (imageBytes != null) {
                ImageIcon icon = new ImageIcon(imageBytes);
                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                JLabel label = new JLabel(new ImageIcon(img));
                return label;
            }
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
});

        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<Categorie> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel jLabelImage; // Ajoutez cette ligne pour le label d'image

    // End of variables declaration//GEN-END:variables
}
