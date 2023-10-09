package com.data;

import java.sql.*;
import java.util.List;

import com.data.domain.Adres;
import com.data.domain.OVChipkaart;
import com.data.domain.Product;
import com.data.domain.Reiziger;
import com.data.persistency.AdresDAOPsql;
import com.data.persistency.OVChipkaartDAOPsql;
import com.data.persistency.ProductDAOPsql;
import com.data.persistency.interfaces.OVChipkaartDAO;
import com.data.persistency.interfaces.ReizigerDAO;
import com.data.persistency.ReizigerDAOPsql;

public class App {
    public static void main(String[] args) throws SQLException{
        ReizigerDAOPsql rdao = new ReizigerDAOPsql();
        AdresDAOPsql adao = new AdresDAOPsql();
        OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql();
        ProductDAOPsql pdao = new ProductDAOPsql();
        rdao.setAdao(adao);
        adao.setRdao(rdao);
        odao.setRdao(rdao);
        pdao.setOdao(odao);
        testReizigerDAO(rdao);
        testAdresDAO(adao);
        testOVChipkaartDAO(odao);
        testProductDAO(pdao);
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
        System.out.println(reizigers.size() + " reizigers\n");

        // Delete de reiziger
        System.out.println("[Test] ReizigerDAO.delete() geeft de volgende reizigers:");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println(reizigers.size() + " reizigers\n");

        // Find by ID
        System.out.println("[Test] ReizigerDAO.findById() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(rdao.findById(r.getReiziger_id()));
        }
        System.out.println("\n");

        // Find an specific ID
        if (rdao.findById(3) == null) {
            System.out.println("Reiziger met ID 3 bestaat niet");
        } else {
            System.out.println(rdao.findById(3));
        }
        System.out.println("\n");

        // Find by GBdatum
        System.out.println("[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(rdao.findByGbdatum(r.getGeboortedatum().toString()));
        }
        System.out.println("\n");
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
        System.out.println("\n");

        // Delete het adres
        System.out.println("[Test] AdresDAO.delete() geeft de volgende adressen:");
        adao.delete(adres);
        adressen = adao.findAll();
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println("\n");

        // Find by reiziger
        System.out.println("[Test] AdresDAO.findByReiziger() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(adao.findByReiziger(adao.getRdao().findById(a.getReiziger_id())));
        }
        System.out.println(adressen.size() + " adressen\n");

    }
        private static void testOVChipkaartDAO(OVChipkaartDAOPsql odao) throws SQLException {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Haal alle OVChipkaarten op uit de database
        List<OVChipkaart> ovchipkaarten = odao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende OVChipkaarten:");
        for (OVChipkaart o : ovchipkaarten) {
            System.out.println(o);
        }
        System.out.println();
        
        // Maak een nieuwe OVChipkaart aan en persisteer deze in de database
        OVChipkaart ovchipkaart = new OVChipkaart(6, java.sql.Date.valueOf("2022-01-01"), 1, 1, 1);
        System.out.print("[Test] Eerst " + ovchipkaarten.size() + " OVChipkaarten, na OVChipkaartDAO.save() ");
        odao.save(ovchipkaart);
        ovchipkaarten = odao.findAll();
        for (OVChipkaart o : ovchipkaarten) {
            System.out.println(o);
        }
        System.out.println(ovchipkaarten.size() + " OVChipkaarten\n");

        // Update de OVChipkaart
        System.out.println("[Test] OVChipkaartDAO.update() geeft de volgende OVChipkaarten:");
        ovchipkaart.setKlasse(2);
        odao.update(ovchipkaart);
        ovchipkaarten = odao.findAll();
        for (OVChipkaart o : ovchipkaarten) {
            System.out.println(o);
        }
        System.out.println(ovchipkaarten.size() + " OVChipkaarten\n");

        // Delete de OVChipkaart
        System.out.println("[Test] OVChipkaartDAO.delete() geeft de volgende OVChipkaarten:");
        odao.delete(ovchipkaart);
        ovchipkaarten = odao.findAll();
        for (OVChipkaart o : ovchipkaarten) {
            System.out.println(o);
        }
        System.out.println(ovchipkaarten.size() + " OVChipkaarten\n");

        // Find by reiziger
        System.out.println("[Test] OVChipkaartDAO.findByReiziger() geeft de volgende OVChipkaarten:");
        for (OVChipkaart o : ovchipkaarten) {
            System.out.println(odao.findByReiziger(odao.getRdao().findById(o.getReiziger_id())));
        }
        System.out.println(ovchipkaarten.size() + " OVChipkaarten\n");

        // Find by ID
        System.out.println("[Test] OVChipkaartDAO.findById() geeft de volgende OVChipkaarten:");
        for (OVChipkaart o : ovchipkaarten) {
            System.out.println(odao.findById(o.getKaart_nummer()));
        }
        System.out.println(ovchipkaarten.size() + " OVChipkaarten\n");
    }

        private static void testProductDAO(ProductDAOPsql pdao) throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------");

        // Haal alle producten op uit de database
        List<Product> producten = pdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product p : producten) {
            System.out.println(p);
        }
        System.out.println();

        // Maak een nieuw product aan en persisteer deze in de database
        Product product = new Product(6, "Test", "Test", 1.00);
        System.out.print("[Test] Eerst " + producten.size() + " producten, na ProductDAO.save() ");
        pdao.save(product);
        producten = pdao.findAll();
        for (Product p : producten) {
            System.out.println(p);
        }
        System.out.println(producten.size() + " producten\n");

        // OVChipkaart ovChipkaart = new OVChipkaart(6, java.sql.Date.valueOf("2022-01-01"), 1, 1, 1);
        // ovChipkaart.getProducten().add(product);
        // product.getOvchipkaarten().add(ovChipkaart);
        // System.out.println(ovChipkaart + "dit is een test");
    

        // Update het product
        System.out.println("[Test] ProductDAO.update() geeft de volgende producten:");
        product.setPrijs(2.00);
        pdao.update(product);
        producten = pdao.findAll();
        for (Product p : producten) {
            System.out.println(p);
        }
        System.out.println("\n");

        // Delete het product
        System.out.println("[Test] ProductDAO.delete() geeft de volgende producten:");
        pdao.delete(product);
        producten = pdao.findAll();
        for (Product p : producten) {
            System.out.println(p);
        }
        System.out.println("\n");

        // Find by OVChipkaart
        System.out.println("[Test] ProductDAO.findByOVChipkaart() geeft de volgende producten:");
        OVChipkaartDAO odao =  pdao.getOdao();
        int ovchipkaart_id = odao.findAll().size() + 1;
        OVChipkaart ovChipkaart = new OVChipkaart(ovchipkaart_id, java.sql.Date.valueOf("2022-01-01"), 1, 1, 1);
        Product p = producten.get(0);
            ovChipkaart.getProducten().add(p);
            p.getOvchipkaarten().add(ovChipkaart);
            odao.save(ovChipkaart);
            pdao.save(p);
            System.out.println(pdao.findByOVChipkaart(ovChipkaart));
        System.out.println("\n");

        // for (Product p : producten) {
        //     OVChipkaartDAO odao =  pdao.getOdao();
        //     // ovChipkaart = odao.findById(p.getProduct_nummer());
        //     ovChipkaart.getProducten().add(p);
        //     p.getOvchipkaarten().add(ovChipkaart);
        //     odao.save(ovChipkaart);
        //     System.out.println(pdao.findByOVChipkaart(ovChipkaart));
        // }

        // Find by ID
        System.out.println("[Test] ProductDAO.findById() geeft de volgende producten:");
        for (Product pr : producten) {
            System.out.println(pdao.findById(pr.getProduct_nummer()));
        }
        System.out.println(producten.size() + " producten\n");

    }
}

