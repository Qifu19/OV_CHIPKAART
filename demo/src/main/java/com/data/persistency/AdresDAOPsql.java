package com.data.persistency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.data.domain.Adres;
import com.data.domain.Reiziger;
import com.data.persistency.interfaces.AdresDAO;
import com.data.persistency.interfaces.ReizigerDAO;

public class AdresDAOPsql implements AdresDAO{
    private Connection conn;
    private ReizigerDAO rdao;

    public AdresDAOPsql() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OV_Chipkaart", "postgres", "Qianfu19#2004");
    }

    public ReizigerDAO getRdao() {
        return rdao;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    @Override
    public boolean save(Adres adres) {
        int adres_id = adres.getAdres_id();
        String postcode = adres.getPostcode();
        String huisnummer = adres.getHuisnummer();
        String straat = adres.getStraat();
        String woonplaats = adres.getWoonplaats();
        int reiziger_id = adres.getReiziger_id();
        
        try {
            PreparedStatement myStmt = conn.prepareStatement("INSERT INTO adres VALUES (?, ?, ?, ?, ?, ?)");
            myStmt.setInt(1, adres_id);
            myStmt.setString(2, postcode);
            myStmt.setString(3, huisnummer);
            myStmt.setString(4, straat);
            myStmt.setString(5, woonplaats);
            myStmt.setInt(6, reiziger_id);
            myStmt.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
            return false;
        }
        
    }

    @Override
    public boolean update(Adres adres) {
        try {
            PreparedStatement myStmt = conn.prepareStatement("UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?");
            myStmt.setString(1, adres.getPostcode());
            myStmt.setString(2, adres.getHuisnummer());
            myStmt.setString(3, adres.getStraat());
            myStmt.setString(4, adres.getWoonplaats());
            myStmt.setInt(5, adres.getReiziger_id());
            myStmt.setInt(6, adres.getAdres_id());
            myStmt.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean delete(Adres adres) {
        try {
            PreparedStatement myStmt = conn.prepareStatement("DELETE FROM adres WHERE adres_id = ?");
            myStmt.setInt(1, adres.getAdres_id());
            myStmt.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM adres WHERE reiziger_id = ?");
            myStmt.setInt(1, reiziger.getReiziger_id());
            ResultSet myRs = myStmt.executeQuery();
            myRs.next();
            Adres adres = new Adres(myRs.getInt("adres_id"), myRs.getString("postcode"), myRs.getString("huisnummer"), myRs.getString("straat"), myRs.getString("woonplaats"), myRs.getInt("reiziger_id"));
            myStmt.close();
            myRs.close();
            return adres;
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        List<Adres> adressen = new ArrayList<Adres>();
        try(ResultSet myRs = conn.prepareStatement("SELECT * FROM adres").executeQuery()) {
            while (myRs.next()) {
                Adres adres = new Adres(myRs.getInt("adres_id"), myRs.getString("postcode"), myRs.getString("huisnummer"), myRs.getString("straat"), myRs.getString("woonplaats"), myRs.getInt("reiziger_id"));
                adressen.add(adres);
            }
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
        }
        return adressen;
    }
}
