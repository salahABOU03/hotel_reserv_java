package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connexion {
    private static final String url="jdbc:mysql://localhost:3306/Gestionutilisateur";
    private static final String user="root";
    private static final String password="";
    private static Connection cnx=null;
   static {
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           
            cnx = DriverManager.getConnection(url, user ,password);
           

       }
       catch (ClassNotFoundException e) {
           System.out.println("classnotfound");
       }
       catch (SQLException e) {
           System.out.println("sql exception");
       }

   }
   public static Connection getCnx() {
       return cnx;
   }
}
