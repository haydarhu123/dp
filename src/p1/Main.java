package p1;

import java.sql.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
       String url= "jdbc:postgresql://localhost/ovchip?user=postgres&password=Shahabi1999!";

       try{
           Connection cnctn = DriverManager.getConnection(url);

           Statement stamnt = cnctn.createStatement();
           String query = "SELECT voorletters,tussenvoegsel, achternaam, geboortedatum FROM reiziger ";

           ResultSet rsltst = stamnt.executeQuery(query);

           String voorletters;
           String tussenvoegsel;
           String achternaam;
           Date geboortedatum;

           while(rsltst.next()){
               voorletters = rsltst.getString("voorletters");
               tussenvoegsel = rsltst.getString("tussenvoegsel");
               achternaam = rsltst.getString("achternaam");
               geboortedatum = rsltst.getDate("geboortedatum");

               System.out.println("Alle reizigers: "+ " "+ voorletters + ". "+tussenvoegsel+ " "+ achternaam+"(" +geboortedatum+")");
           }


           rsltst.close();
           stamnt.close();
           cnctn.close();


       }
       catch (SQLException sqlException) {
           System.out.println(sqlException);
           System.err.println("De opgevraagde reizigers kunnen niet getoond worden, Probeer later opnieuw!");
       }
    }
}
