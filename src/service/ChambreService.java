package service;

import connexion.Connexion;
import dao.IDAO;
import entities.Chambre;
import entities.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ChambreService implements IDAO<Chambre> {

    @Override
    public boolean create(Chambre o) {
        String query = "INSERT INTO chambre VALUES (null, ?, ?)";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setString(1, o.getTelephone());
            ps.setInt(2, o.getCategorie().getId());

            int ligne = ps.executeUpdate();
            return ligne == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean update(Chambre o) {
        String query = "UPDATE chambre SET telephone = ?, categorie_id = ? WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setString(1, o.getTelephone());
            ps.setInt(2, o.getCategorie().getId());
            ps.setInt(3, o.getId());

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
                return new Chambre(
                        rs.getInt("id"),
                        rs.getString("telephone"),
                        new CategorieService().findById(rs.getInt("categorie_id"))
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
                chambres.add(new Chambre(
                        rs.getInt("id"),
                        rs.getString("telephone"),
                        new CategorieService().findById(rs.getInt("categorie_id"))
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return chambres;
    }
 
}