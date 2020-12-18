package p5;

import p4.OVChipkaart;
import p4.OVChipkaartDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAOP {

    private Connection conn;
    private OVChipkaartDAO ovckdao;


    public ProductDAOPsql(Connection connection) {
        this.conn = connection;
    }

    @Override
    public boolean save(Product product) {
        try{
            String query = "INSERT INTO product (productnummer , naam , beschrijving , prijs) VALUES (?,?,?,?)";

            PreparedStatement prepstmt = conn.prepareStatement(query);

            int productnummer = product.getProductnummer();
            String naam = product.getNaam();
            String beschrijving = product.getBeschrijving();
            Double prijs = product.getPrijs();

            prepstmt.setInt(1, product.getProductnummer());
            prepstmt.setString(2, product.getNaam());
            prepstmt.setString(3, product.getBeschrijving());
            prepstmt.setDouble(4, product.getPrijs());

            String query1 = "INSERT INTO ov_chipkaart_product (kaartnummer , productnummer , status ) VALUES (?,?,?)";

            String status = "status van het product";

            PreparedStatement prepstatmt = conn.prepareStatement(query1);
            for (int nummer=0; nummer < product.getOvchipkaart().size(); nummer++  )

            prepstatmt.setInt(1, product.getOvchipkaart().get(nummer).getKaartnummer());
            prepstmt.setInt(2, product.getProductnummer());
            prepstatmt.setString(3, status);

            prepstmt.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean delete(Product product) {
        try {
            String query = "DELETE FROM product WHERE productnummer = ?";

            PreparedStatement prpstmt = conn.prepareStatement(query);

            prpstmt.setInt(1, product.getProductnummer());

            prpstmt.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        try{
            String query = "UPDATE product SET  naam=? , beschrijving=? , prijs = ? WHERE productnummer =?";

            PreparedStatement prepstmt = conn.prepareStatement(query);

            prepstmt.setString(1, product.getNaam());
            prepstmt.setString(2, product.getBeschrijving());
            prepstmt.setDouble(3, product.getPrijs());
            prepstmt.setInt(4, product.getProductnummer());

            String query1 = "DELETE FROM product WHERE productnummer = ?";

            PreparedStatement prpstmt = conn.prepareStatement(query1);

            prpstmt.setInt(1, product.getProductnummer());

            String query2 = "INSERT INTO ov_chipkaart_product (kaartnummer , productnummer , status ) VALUES (?,?,?)";
            PreparedStatement prepstatmt = conn.prepareStatement(query2);

            String status = "status van het product";

            for (int nummer=0; nummer < product.getOvchipkaart().size(); nummer++)

                prepstatmt.setInt(1, product.getOvchipkaart().get(nummer).getKaartnummer());
                prepstmt.setInt(2, product.getProductnummer());
                prepstatmt.setString(3, status);

                prepstmt.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

        }
        return false;
    }

    @Override
    public Product findByOVChipkaart(OVChipkaart ovchipkaart) {
        try {
            PreparedStatement prepstmt = conn.prepareStatement("SELECT * FROM product JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer WHERE ov_chipkaart_product.kaart_nummer = ?");
            prepstmt.setInt(1,ovchipkaart.getKaartnummer());

            ResultSet rsltst = prepstmt.executeQuery();
            while (rsltst.next()) {
                int productnummer = rsltst.getInt("product_nummer");
                String naam = rsltst.getString("naam");
                String beschrijving = rsltst.getString("beschrijving");
                Double prijs = rsltst.getDouble("prijs");

                System.out.println(productnummer);
                System.out.println(naam);
                System.out.println(beschrijving);
                System.out.println(prijs);
            }
        }
        catch (SQLException sqlException){
            System.err.println("De gegevens van deze geboortedatum kunnen niet opgehaald worden, probeer het later opnieuw!");
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws SQLException {

        Statement stmt = conn.createStatement();
        ResultSet rsltst = stmt.executeQuery( "SELECT* FROM product");

        ArrayList<Product> aantalproducten = new ArrayList<Product>();

        while (rsltst.next()) {

            int productnummer = rsltst.getInt("product_nummer");
            String naam = rsltst.getString("naam");
            String beschrijving = rsltst.getString("beschrijving");
            Double prijs = rsltst.getDouble("prijs");


            Product product = new Product(productnummer, naam, beschrijving, prijs);
            aantalproducten.add(product);
        }
        return aantalproducten;
    }
}
