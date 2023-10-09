package com.data.persistency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.data.domain.OVChipkaart;
import com.data.domain.Product;
import com.data.persistency.interfaces.OVChipkaartDAO;
import com.data.persistency.interfaces.ProductDAO;

public class ProductDAOPsql implements ProductDAO{
    Connection conn;
    private OVChipkaartDAO Odao;

    public ProductDAOPsql() throws SQLException{
        this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OV_Chipkaart", "postgres", "Qianfu19#2004");
    }

    public OVChipkaartDAO getOdao() {
        return Odao;
    }

    public void setOdao(OVChipkaartDAO odao) {
        Odao = odao;
    }

    @Override
    public void save(Product product) throws SQLException {
        int product_nummer = product.getProduct_nummer();
        String naam = product.getNaam();
        String beschrijving = product.getBeschrijving();
        double prijs = product.getPrijs();

        
        PreparedStatement myStmt = conn.prepareStatement("UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?");
        myStmt.setString(1, naam);
        myStmt.setString(2, beschrijving);
        myStmt.setDouble(3, prijs);
        myStmt.setInt(4, product_nummer);
        myStmt.executeUpdate();
        myStmt.close();

        for (OVChipkaart ovchipkaart : product.getOvchipkaarten()) {
            PreparedStatement delete = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
            delete.setInt(1, product_nummer);
            delete.executeUpdate();
            delete.close();
            PreparedStatement myStmt2 = conn.prepareStatement("INSERT INTO ov_chipkaart_product VALUES (?, ?)");
            myStmt2.setInt(1, ovchipkaart.getKaart_nummer());
            myStmt2.setInt(2, product_nummer);
            myStmt2.executeUpdate();
            myStmt2.close();
        }
    }

    @Override
    public boolean update(Product product) throws SQLException{
            PreparedStatement myStmt = conn.prepareStatement("UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?");
            myStmt.setString(1, product.getNaam());
            myStmt.setString(2, product.getBeschrijving());
            myStmt.setDouble(3, product.getPrijs());
            myStmt.setInt(4, product.getProduct_nummer());
            myStmt.executeUpdate();
            myStmt.close();

            for(OVChipkaart ovchipkaart : product.getOvchipkaarten()){
                PreparedStatement delete = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
                delete.setInt(1, product.getProduct_nummer());
                delete.executeUpdate();
                delete.close();
                PreparedStatement myStmt2 = conn.prepareStatement("INSERT INTO ov_chipkaart_product VALUES (?, ?)");
                myStmt2.setInt(1, ovchipkaart.getKaart_nummer());
                myStmt2.setInt(2, product.getProduct_nummer());
                myStmt2.executeUpdate();
                myStmt2.close();
            }
            return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException{
        PreparedStatement myStmt = conn.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
        myStmt.setInt(1, product.getProduct_nummer());
        myStmt.executeUpdate();
        myStmt.close();

        for (OVChipkaart ovchipkaart : product.getOvchipkaarten()) {
            PreparedStatement myStmt2 = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ? AND product_nummer = ?");
            myStmt2.setInt(1, ovchipkaart.getKaart_nummer());
            myStmt2.setInt(2, product.getProduct_nummer());
            myStmt2.executeUpdate();
            myStmt2.close();
        }
        return true;
    }

    @Override
    public Product findById(int id) throws SQLException {
        PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM product WHERE product_nummer = ?");
        myStmt.setInt(1, id);
        try (ResultSet rs = myStmt.executeQuery()) {
            if (rs.next()) {
                int product_nummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");
                return new Product(product_nummer, naam, beschrijving, prijs);
            }
        return null;
        }
    }
    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovchipkaart) throws SQLException {
        if (ovchipkaart == null) {
            return null;
        }
        List<Product> producten = new ArrayList<Product>();
        PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM product INNER JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer WHERE ov_chipkaart_product.kaart_nummer = ?");
        myStmt.setInt(1, ovchipkaart.getKaart_nummer());

        try (ResultSet rs = myStmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(rs.getInt("product_nummer"), rs.getString("naam"), rs.getString("beschrijving"), rs.getDouble("prijs"));

                producten.add(product);
            }
            return producten;
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
       List<Product> producten = new ArrayList<Product>();
         PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM product");
            try (ResultSet rs = myStmt.executeQuery()) {
                while (rs.next()) {
                    int product_nummer = rs.getInt("product_nummer");
                    String naam = rs.getString("naam");
                    String beschrijving = rs.getString("beschrijving");
                    double prijs = rs.getDouble("prijs");
                    producten.add(new Product(product_nummer, naam, beschrijving, prijs));
                }
            }
            return producten;
    }
}
