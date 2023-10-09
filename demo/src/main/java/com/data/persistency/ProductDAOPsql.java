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
    public boolean save(Product product) throws SQLException{
        int product_nummer = product.getProduct_nummer();
        String naam = product.getNaam();
        String beschrijving = product.getBeschrijving();
        double prijs = product.getPrijs();

        try {
            PreparedStatement myStmt = conn.prepareStatement("UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?");
            myStmt.setString(1, naam);
            myStmt.setString(2, beschrijving);
            myStmt.setDouble(3, prijs);
            myStmt.setInt(4, product_nummer);
            myStmt.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Product product) throws SQLException{
        try{
            PreparedStatement myStmt = conn.prepareStatement("UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?");
            myStmt.setString(1, product.getNaam());
            myStmt.setString(2, product.getBeschrijving());
            myStmt.setDouble(3, product.getPrijs());
            myStmt.setInt(4, product.getProduct_nummer());
            myStmt.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean delete(Product product) throws SQLException{
        try{
            PreparedStatement myStmt = conn.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
            myStmt.setInt(1, product.getProduct_nummer());
            myStmt.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
            return false;
        }
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
        List<Product> producten = new ArrayList<Product>();
        PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM ov_chipkaart_product WHERE kaart_nummer = ?");
        myStmt.setInt(1, ovchipkaart.getKaart_nummer());
        try (ResultSet rs = myStmt.executeQuery()) {
            while (rs.next()) {
                rs.getInt("product_nummer");
                rs.getString("naam");
                rs.getString("beschrijving");
                rs.getDouble("prijs");
                producten.add(new Product(rs.getInt("product_nummer"), rs.getString("naam"), rs.getString("beschrijving"), rs.getDouble("prijs")));
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
