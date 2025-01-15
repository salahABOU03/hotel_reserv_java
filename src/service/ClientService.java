package service;

import connexion.Connexion;
import dao.IDAO;
import entities.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class ClientService implements IDAO<Client>
{

    @Override
    public boolean create(Client o) {
        String query = "insert into client values (null, ?, ?, ?, ?)";
        PreparedStatement ps ;
        try {

            ps = Connexion.getCnx().prepareStatement(query);

            ps.setString(1, o.getNom());
            ps.setString(2, o.getPrenom());
            ps.setString(3, o.getTelephone());
            ps.setString(4, o.getEmail());


            int ligne = ps.executeUpdate();

            if (ligne == 1){
                System.out.println(ligne+" ligne a été inséré");
                return true;
            }

        } catch (SQLException e) {

            System.out.println(e);        }

        return false;
    }

    @Override
    public boolean update(Client o) {
        String query = "UPDATE client SET nom = ?, prenom = ?, telephone = ?, email = ? WHERE id = ?";
        PreparedStatement ps ;
        try {
            ps = Connexion.getCnx().prepareStatement(query);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getPrenom());
            ps.setString(3, o.getTelephone());
            ps.setString(4, o.getEmail());
            ps.setInt(5, o.getId());

            int ligne = ps.executeUpdate();
            if (ligne == 1){
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
                }
       return false;
        }




    @Override
    public boolean delete(Client o) {

        String query = "DELETE FROM client WHERE id = ?";
        PreparedStatement ps;
        try {
            ps = Connexion.getCnx().prepareStatement(query);
            ps.setInt(1,o.getId());

            int ligne = ps.executeUpdate();
            if(ligne == 1) {
            return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public Client findById(int id) {
        String query = "SELECT * FROM client WHERE id = ?";
        PreparedStatement ps;
        ResultSet rs ;
        try {
            ps = Connexion.getCnx().prepareStatement(query);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {

              return new Client(  rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("email"));


            }
        } catch (SQLException e) {
            System.out.println(e);
        }


        return null;
    }

    @Override
    public List<Client> findAll() {
        String query = "SELECT * FROM client";
        PreparedStatement ps ;
        ResultSet rs ;
        List<Client> clients = new ArrayList<>();
        try {
            ps = Connexion.getCnx().prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("email")
                ));
            }
          
        } catch (SQLException e) {
            System.out.println(e);
        }


        return clients;
    }


}
