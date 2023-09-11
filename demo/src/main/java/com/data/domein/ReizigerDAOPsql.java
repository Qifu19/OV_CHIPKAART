package com.data.domein;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            Statement myStmt = conn.createStatement();
            ResultSet myRs = myStmt.executeQuery("INSERT INTO reiziger VALUES (" + reiziger_id + ", ' " + voorletters + "', '" + tussenvoegsel + "', '" + achternaam + "', '" + geboortedatum + "')");
            myRs.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(Reiziger reiziger) {
        try {
            Statement myStmt = conn.createStatement();
            ResultSet myRs = myStmt.executeQuery("UPDATE reiziger SET achternaam = ' " + reiziger.getAchternaam() + "', tussenvoegsel = '" + reiziger.getTussenvoegsel() + "', voorletters = '" + reiziger.getVoorletters() + "', geboortedatum = '" + reiziger.getGeboortedatum() + "' WHERE reiziger_id = " + reiziger.getReiziger_id());
            myRs.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(Reiziger reiziger) {
        try {
            Statement myStmt = conn.createStatement();
            ResultSet myRs = myStmt.executeQuery("DELETE FROM reiziger WHERE reiziger_id = " + reiziger.getReiziger_id());
            myRs.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Reiziger findById(int id) {
        try {
            Statement myStmt = conn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM reiziger WHERE reiziger_id = " + id);
            while (myRs.next()) {
                Reiziger reiziger = new Reiziger(Integer.parseInt(myRs.getString("reiziger_id")), myRs.getString("voorletters"), myRs.getString("tussenvoegsel"), myRs.getString("achternaam"), java.sql.Date.valueOf(myRs.getString("geboortedatum")));
                return reiziger;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Reiziger> findByGbdatum(String datum) {
        List<Reiziger> reizigers = new ArrayList<Reiziger>();
        try {
            Statement myStmt = conn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM reiziger WHERE geboortedatum = '" + datum + "'");
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
            Statement myStmt = conn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM reiziger");
            while (myRs.next()) {
                Reiziger reiziger = new Reiziger(Integer.parseInt(myRs.getString("reiziger_id")), myRs.getString("voorletters"), myRs.getString("tussenvoegsel"), myRs.getString("achternaam"), java.sql.Date.valueOf(myRs.getString("geboortedatum")));
                reizigers.add(reiziger);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return reizigers;
    }
    public static void main(String[] args) {
        try {
            // get an connection
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OV_Chipkaart", "postgres", "Qianfu19#2004");
            // create a statement
            Statement myStmt = myConn.createStatement();
            // execute sql query
            ResultSet myRs = myStmt.executeQuery("select * from reiziger");
            // process the result set, print all reizigers
            while (myRs.next()) {
                if(myRs.getString("tussenvoegsel") == null) {
                    System.out.println("#" + myRs.getString("reiziger_id") + 
                    ": " + myRs.getString("voorletters") + 
                    ". " + myRs.getString("achternaam") + 
                    " (" + myRs.getString("geboortedatum") + ")");
                }
                else
                System.out.println("#" + myRs.getString("reiziger_id") + 
                ": " + myRs.getString("voorletters") + 
                ". " + myRs.getString("tussenvoegsel") + 
                " " + myRs.getString("achternaam") + 
                " (" + myRs.getString("geboortedatum") + ")");
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

