package entities;

import java.util.Date;

public class Reservation {
    private static int count;
    private int id;
    private Date datedebut;
    private Date datefin;
    private Chambre chambre;
    private Client client;
    public Reservation(Date datedebut, Date datefin, Chambre chambre, Client client) {
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.chambre = chambre;
        this.client = client;
       this.id=++count;
    }

    public Reservation(int id) {
        this.id = id;
    }

    public Reservation(int id, Date datedebut, Date datefin, Chambre chambre, Client client) {
        this.id = id;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.chambre = chambre;
        this.client = client;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", datedebut=" + datedebut +
                ", datefin=" + datefin +
                ", chambre_ID=" + chambre.getId() +
                ", client_ID=" + client.getId() +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
