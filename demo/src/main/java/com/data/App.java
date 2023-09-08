package com.data;

import java.sql.*;
import java.util.List;

import com.data.domein.Reiziger;
import com.data.domein.ReizigerDAO;
import com.data.domein.ReizigerDAOPsql;

public class App {
    public static void main(String[] args) {
        try {
            testReizigerDAO(new ReizigerDAOPsql());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
        /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        // Update de reiziger
        System.out.println("[Test] ReizigerDAO.update() geeft de volgende reizigers:");
        sietske.setAchternaam("Boers");
        rdao.update(sietske);
        reizigers = rdao.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        // Delete de reiziger
        System.out.println("[Test] ReizigerDAO.delete() geeft de volgende reizigers:");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        // Find by ID
        System.out.println("[Test] ReizigerDAO.findById() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(rdao.findById(r.getReiziger_id()));
        }

        // Find by GBdatum
        System.out.println("[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(rdao.findByGbdatum(r.getGeboortedatum().toString()));
        }
    }
    
}

