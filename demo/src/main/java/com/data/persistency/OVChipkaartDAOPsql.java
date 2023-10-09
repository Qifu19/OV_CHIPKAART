package com.data.persistency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.data.domain.OVChipkaart;
import com.data.domain.Product;
import com.data.domain.Reiziger;
import com.data.persistency.interfaces.OVChipkaartDAO;
import com.data.persistency.interfaces.ReizigerDAO;


public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    Connection conn;
    private ReizigerDAO rdao;

    public OVChipkaartDAOPsql() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OV_Chipkaart", "postgres", "Qianfu19#2004");
    }

    public ReizigerDAO getRdao() {
        return rdao;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    @Override
    public void save(OVChipkaart ovchipkaart) throws SQLException {
        int kaart_nummer = ovchipkaart.getKaart_nummer();
        Date geldig_tot = ovchipkaart.getGeldig_tot();
        int klasse = ovchipkaart.getKlasse();
        double saldo = ovchipkaart.getSaldo();
        int reiziger_id = ovchipkaart.getReiziger_id();

        PreparedStatement myStmt = conn.prepareStatement("INSERT INTO ov_chipkaart VALUES (?, ?, ?, ?, ?)");
        myStmt.setInt(1, kaart_nummer);
        myStmt.setDate(2, java.sql.Date.valueOf(geldig_tot.toString()));
        myStmt.setInt(3, klasse);
        myStmt.setDouble(4, saldo);
        myStmt.setInt(5, reiziger_id);
        myStmt.executeUpdate();
        myStmt.close();
    }

    @Override
    public boolean update(OVChipkaart ovchipkaart) throws SQLException{
        PreparedStatement myStmt = conn.prepareStatement("UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?");
        myStmt.setDate(1, java.sql.Date.valueOf(ovchipkaart.getGeldig_tot().toString()));
        myStmt.setInt(2, ovchipkaart.getKlasse());
        myStmt.setDouble(3, ovchipkaart.getSaldo());
        myStmt.setInt(4, ovchipkaart.getReiziger_id());
        myStmt.setInt(5, ovchipkaart.getKaart_nummer());
        myStmt.executeUpdate();
        myStmt.close();
        return true;
    }
    
    @Override
    public boolean delete(OVChipkaart ovchipkaart) throws SQLException{
        PreparedStatement myStmt = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
        myStmt.setInt(1, ovchipkaart.getKaart_nummer());
        myStmt.executeUpdate();
        myStmt.close();
        return true;
    }

    @Override
    public OVChipkaart findById(int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?");
        preparedStatement.setInt(1, id);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                OVChipkaart ovchipkaart = new OVChipkaart(resultSet.getInt("kaart_nummer"), resultSet.getDate("geldig_tot"), resultSet.getInt("klasse"), resultSet.getDouble("saldo"), resultSet.getInt("reiziger_id"));
                ovchipkaart.setReiziger(rdao.findById(resultSet.getInt("reiziger_id")));
                return ovchipkaart;
            }
        }
        return null;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?");
        preparedStatement.setInt(1, reiziger.getReiziger_id());

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            List<OVChipkaart> ovchipkaarten = new ArrayList<OVChipkaart>();
            while (resultSet.next()) {
                OVChipkaart ovchipkaart = new OVChipkaart(resultSet.getInt("kaart_nummer"), resultSet.getDate("geldig_tot"), resultSet.getInt("klasse"), resultSet.getDouble("saldo"), resultSet.getInt("reiziger_id"));
                ovchipkaart.setReiziger(rdao.findById(resultSet.getInt("reiziger_id")));
                ovchipkaarten.add(ovchipkaart);
            }
            return ovchipkaarten;
        }
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        List<OVChipkaart> ovchipkaarten = new ArrayList<OVChipkaart>();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM ov_chipkaart");

        try (ResultSet result = preparedStatement.executeQuery()){ 
            while(result.next()) {
                OVChipkaart ovchipkaart = new OVChipkaart(result.getInt("kaart_nummer"), result.getDate("geldig_tot"), result.getInt("klasse"), result.getDouble("saldo"), result.getInt("reiziger_id"));
                ovchipkaart.setReiziger(rdao.findById(result.getInt("reiziger_id")));
                ovchipkaarten.add(ovchipkaart);
            }
        return ovchipkaarten;
        }
    }

    @Override
    public List<OVChipkaart> findByProduct(Product product) throws SQLException {
        if (product == null) {
            return null;
        }
        List<OVChipkaart> ovchipkaarten = new ArrayList<OVChipkaart>();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM ov_chipkaart_product WHERE product_nummer = ?");
        preparedStatement.setInt(1, product.getProduct_nummer());

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                OVChipkaart ovchipkaart = findById(resultSet.getInt("kaart_nummer"));
                ovchipkaarten.add(ovchipkaart);
            }
            return ovchipkaarten;
        }
    }

}
