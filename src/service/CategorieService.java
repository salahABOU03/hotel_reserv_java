package service;

import connexion.Connexion;
import dao.IDAO;
import entities.Categorie;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CategorieService implements IDAO<Categorie> {

    @Override
    public boolean create(Categorie o) {
        String query = "INSERT INTO categorie VALUES (null, ?, ?)";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setString(1, o.getCode());
            ps.setString(2, o.getLibelle());

            int ligne = ps.executeUpdate();
            if (ligne == 1) {
                System.out.println(ligne + " ligne a été insérée");
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean update(Categorie o) {
        String query = "UPDATE categorie SET code = ?, libelle = ? WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setString(1, o.getCode());
            ps.setString(2, o.getLibelle());
            ps.setInt(3, o.getId());

            int ligne = ps.executeUpdate();
            if (ligne == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean delete(Categorie o) {
        String query = "DELETE FROM categorie WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, o.getId());

            int ligne = ps.executeUpdate();
            if (ligne == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }


@Override
public Categorie findById(int id) {
    String query = "SELECT * FROM categorie WHERE id = ?";
    try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Categorie(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("libelle")
                );
            }
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return null;
}

@Override
public List<Categorie> findAll() {
    String query = "SELECT * FROM categorie";
    List<Categorie> categories = new ArrayList<>();
    try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            categories.add(new Categorie(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getString("libelle")
            ));
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return categories;
}
}
