package com.data;

import java.sql.*;
import java.util.List;

import com.data.domain.Adres;
import com.data.domain.Reiziger;
import com.data.persistency.AdresDAOPsql;
import com.data.persistency.ReizigerDAO;
import com.data.persistency.ReizigerDAOPsql;

public class App {
    public static void main(String[] args) {
        try {
            ReizigerDAOPsql rdao = new ReizigerDAOPsql();
            AdresDAOPsql adao = new AdresDAOPsql();
            rdao.setAdao(adao);
            adao.setRdao(rdao);
            testReizigerDAO(rdao);
            testAdresDAO(adao);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
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
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum), null);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        // Update de reiziger
        System.out.println("[Test] ReizigerDAO.update() geeft de volgende reizigers:");
        sietske.setAchternaam("Boersma");
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
        // Find an specific ID
        if (rdao.findById(3) == null) {
            System.out.println("Reiziger met ID 3 bestaat niet");
        } else {
            System.out.println(rdao.findById(3));
        }

        // Find by GBdatum
        System.out.println("[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(rdao.findByGbdatum(r.getGeboortedatum().toString()));
        }
    }
    
    private static void testAdresDAO(AdresDAOPsql adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuw adres aan en persisteer deze in de database
        Adres adres = new Adres(6, "1234AB", "1", "Straat", "Woonplaats", 1);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(adres);
        adressen = adao.findAll();
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println(adressen.size() + " adressen\n");
        
        // Update het adres
        System.out.println("[Test] AdresDAO.update() geeft de volgende adressen:");
        adres.setHuisnummer("2");
        adao.update(adres);
        adressen = adao.findAll();
        for (Adres a : adressen) {
            System.out.println(a);
        }

        // Delete het adres
        System.out.println("[Test] AdresDAO.delete() geeft de volgende adressen:");
        adao.delete(adres);
        adressen = adao.findAll();
        for (Adres a : adressen) {
            System.out.println(a);
        }

        // Find by reiziger
        System.out.println("[Test] AdresDAO.findByReiziger() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(adao.findByReiziger(adao.getRdao().findById(a.getReiziger_id())));
        }
        
    }


}

