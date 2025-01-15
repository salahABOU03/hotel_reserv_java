/*package presentation;


import entities.Categorie;
import entities.Chambre;
import entities.Client;
import entities.Reservation;
import service.CategorieService;
import service.ChambreService;
import service.ClientService;
import service.ReservationService;

import java.util.Date;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        // Test ClientService
        ClientService clientService = new ClientService();
        Client clt1= new Client("youssef", "zaia", "0600140407", "youssef@gmail.com");
        Client clt2= new Client("John", "Doe", "0654321987", "john.doe@gmail.com");


       //clientService.create(clt1);
      //  clientService.create(clt2);

        System.out.println("List des Clients:");
        System.out.println(clientService.findAll());
        System.out.println("\n\n");
        
        
        // Test CategorieService
        CategorieService categorieService = new CategorieService();
        Categorie cat1= new Categorie("CAT01", "Electronics");
        Categorie cat2= new Categorie("CAT02", "Books");


 //   categorieService.create(cat1);
       // categorieService.create(cat2);

        System.out.println("List des Categories:");
        System.out.println(categorieService.findAll());
        System.out.println("\n\n");

        // Test ChambreService

        List<Categorie> categories = categorieService.findAll();
        Categorie dbCat1 = categories.get(0);
        Categorie dbCat2 = categories.get(1);



        ChambreService chambreService = new ChambreService();
        Chambre chambre1= new Chambre("0668109988", dbCat1);
        Chambre chambre2= new Chambre("0654321987", dbCat2);

     // chambreService.create(chambre1);
       // chambreService.create(chambre2);


        System.out.println("List des Chambres:");
        System.out.println(chambreService.findAll());
        System.out.println("\n\n");


        // Test ReservationService

        List<Chambre> chambres = chambreService.findAll();
        Chambre dbChambre1 = chambres.get(0);

        List<Client> clients = clientService.findAll();
        Client dbClient1 = clients.get(0);

        ReservationService reservationService = new ReservationService();
        Reservation res1=new Reservation(new Date("01/25/2021"), new Date("01/29/2021"), dbChambre1,dbClient1);


     //   reservationService.create(res1);


        System.out.println("List des Reservations:");
        System.out.println(reservationService.findAll());
    }
}*/

