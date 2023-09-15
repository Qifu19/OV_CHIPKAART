package com.data.persistency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.data.domain.Reiziger;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;

    public ReizigerDAOPsql() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OV_Chipkaart", "postgres", "Qianfu19#2004");
    }

    public boolean save(Reiziger reiziger) {
        String achternaam = reiziger.getAchternaam();
        String tussenvoegsel = reiziger.getTussenvoegsel();
        String voorletters = reiziger.getVoorletters();
        String geboortedatum = reiziger.getGeboortedatum().toString();
        String reiziger_id = Integer.toString(reiziger.getReiziger_id());

        try {
            PreparedStatement myStmt = conn.prepareStatement("INSERT INTO reiziger VALUES (?, ?, ?, ?, ?)");
            myStmt.setInt(1, Integer.parseInt(reiziger_id));
            myStmt.setString(2, voorletters);
            myStmt.setString(3, tussenvoegsel);
            myStmt.setString(4, achternaam);
            myStmt.setDate(5, java.sql.Date.valueOf(geboortedatum));
            myStmt.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Reiziger reiziger) {
        try {
            PreparedStatement myStmt = conn.prepareStatement("UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");
            myStmt.setString(1, reiziger.getVoorletters());
            myStmt.setString(2, reiziger.getTussenvoegsel());
            myStmt.setString(3, reiziger.getAchternaam());
            myStmt.setDate(4, java.sql.Date.valueOf(reiziger.getGeboortedatum().toString()));
            myStmt.setInt(5, reiziger.getReiziger_id());
            myStmt.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(Reiziger reiziger) {
        try {
            PreparedStatement myStmt = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");
            myStmt.setInt(1, reiziger.getReiziger_id());
            myStmt.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
            return false;
        }
    }

    public Reiziger findById(int id) {
        try {
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id = ?");
            myStmt.setInt(1, id);
            ResultSet myRs = myStmt.executeQuery();
            while (myRs.next()) {
                Reiziger reiziger = new Reiziger(Integer.parseInt(myRs.getString("reiziger_id")), myRs.getString("voorletters"), myRs.getString("tussenvoegsel"), myRs.getString("achternaam"), java.sql.Date.valueOf(myRs.getString("geboortedatum")));
                return reiziger;
            }
        } catch (SQLException e) {
            System.err.println("SQLExeption: " + e.getMessage());
        }
        return null;
    }
    // public Reiziger findById(int id) {
    //     try {
    //         Statement myStmt = conn.createStatement();
    //         ResultSet myRs = myStmt.executeQuery("SELECT * FROM reiziger WHERE reiziger_id = " + id);
    //         while (myRs.next()) {
    //             Reiziger reiziger = new Reiziger(Integer.parseInt(myRs.getString("reiziger_id")), myRs.getString("voorletters"), myRs.getString("tussenvoegsel"), myRs.getString("achternaam"), java.sql.Date.valueOf(myRs.getString("geboortedatum")));
    //             return reiziger;
    //         }
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //     }
    //     return null;
    // }

    public List<Reiziger> findByGbdatum(String datum) {
        List<Reiziger> reizigers = new ArrayList<Reiziger>();
        try {
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?");
            myStmt.setDate(1, java.sql.Date.valueOf(datum));
            ResultSet myRs = myStmt.executeQuery();
            while (myRs.next()) {
                Reiziger reiziger = new Reiziger(Integer.parseInt(myRs.getString("reiziger_id")), myRs.getString("voorletters"), myRs.getString("tussenvoegsel"), myRs.getString("achternaam"), java.sql.Date.valueOf(myRs.getString("geboortedatum")));
                reizigers.add(reiziger);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return reizigers;
    }

    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<Reiziger>();
    
        try {
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM reiziger");
            ResultSet myRs = myStmt.executeQuery();
            while (myRs.next()) {
                Reiziger reiziger = new Reiziger(Integer.parseInt(myRs.getString("reiziger_id")), myRs.getString("voorletters"), myRs.getString("tussenvoegsel"), myRs.getString("achternaam"), java.sql.Date.valueOf(myRs.getString("geboortedatum")));
                reizigers.add(reiziger);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return reizigers;
    }
}

