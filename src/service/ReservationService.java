package service;

import connexion.Connexion;
import dao.IDAO;
import entities.Client;
import entities.Reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReservationService implements IDAO<Reservation> {

    @Override
    public boolean create(Reservation o) {
        String query = "INSERT INTO reservation VALUES (null, ?, ?, ?, ?)";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setDate(1, new Date(o.getDatedebut().getTime()));
            ps.setDate(2, new Date(o.getDatefin().getTime()));
            ps.setInt(3, o.getChambre().getId());
            ps.setInt(4, o.getClient().getId());

            int ligne = ps.executeUpdate();
            return ligne == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean update(Reservation o) {
        String query = "UPDATE reservation SET datedebut = ?, datefin = ?, chambre_id = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setDate(1, new Date(o.getDatedebut().getTime()));
            ps.setDate(2, new Date(o.getDatefin().getTime()));
            ps.setInt(3, o.getChambre().getId());
            ps.setInt(4, o.getClient().getId());
            ps.setInt(5, o.getId());

            int ligne = ps.executeUpdate();
            return ligne == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean delete(Reservation o) {
        String query = "DELETE FROM reservation WHERE id = ?";
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
    public Reservation findById(int id) {
        String query = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Reservation(
                        rs.getInt("id"),
                        rs.getDate("datedebut"),
                        rs.getDate("datefin"),
                        new ChambreService().findById(rs.getInt("chambre_id")),
                        new ClientService().findById(rs.getInt("client_id"))
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        String query = "SELECT * FROM reservation";
        List<Reservation> reservations = new ArrayList<>();
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Client client = new ClientService().findById(rs.getInt("client_id"));
                if (client != null) {
                    reservations.add(new Reservation(
                            rs.getInt("id"),
                            rs.getDate("datedebut"),
                            rs.getDate("datefin"),
                            new ChambreService().findById(rs.getInt("chambre_id")),
                            client
                    ));
                } else {
                    System.out.println("Reservation with null client skipped: " + rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return reservations;
    }
    
}