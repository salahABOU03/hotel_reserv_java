package service;

import connexion.Connexion;
import dao.IDAO;
import entities.Chambre;
import entities.Categorie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class ChambreService implements IDAO<Chambre> {

    @Override
    public boolean create(Chambre o) {
        String query = "INSERT INTO chambre (telephone, categorie_id, image) VALUES (?, ?, ?)";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setString(1, o.getTelephone());
            ps.setInt(2, o.getCategorie().getId());

            // Si l'image n'est pas nulle, on la convertit en Blob pour l'insérer
            if (o.getImage() != null) {
                ps.setBlob(3, new javax.sql.rowset.serial.SerialBlob(o.getImage()));
            } else {
                ps.setNull(3, java.sql.Types.BLOB);
            }

            int ligne = ps.executeUpdate();
            return ligne == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean update(Chambre o) {
        String query = "UPDATE chambre SET telephone = ?, categorie_id = ?, image = ? WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setString(1, o.getTelephone());
            ps.setInt(2, o.getCategorie().getId());

            // Mise à jour de l'image
            if (o.getImage() != null) {
                ps.setBlob(3, new javax.sql.rowset.serial.SerialBlob(o.getImage()));
            } else {
                ps.setNull(3, java.sql.Types.BLOB);
            }

            ps.setInt(4, o.getId());

            int ligne = ps.executeUpdate();
            return ligne == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean delete(Chambre o) {
        String query = "DELETE FROM chambre WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, o.getId());

            int ligne = ps.executeUpdate();
            return ligne == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public Chambre findById(int id) {
        String query = "SELECT * FROM chambre WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Blob imageBlob = rs.getBlob("image");
                byte[] image = null;
                if (imageBlob != null) {
                    image = imageBlob.getBytes(1, (int) imageBlob.length());
                }

                return new Chambre(
                        rs.getInt("id"),
                        rs.getString("telephone"),
                        new CategorieService().findById(rs.getInt("categorie_id")),
                        image
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Chambre> findAll() {
        String query = "SELECT * FROM chambre";
        List<Chambre> chambres = new ArrayList<>();
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blob imageBlob = rs.getBlob("image");
                byte[] image = null;
                if (imageBlob != null) {
                    image = imageBlob.getBytes(1, (int) imageBlob.length());
                }

                chambres.add(new Chambre(
                        rs.getInt("id"),
                        rs.getString("telephone"),
                        new CategorieService().findById(rs.getInt("categorie_id")),
                        image
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return chambres;
    }
}
