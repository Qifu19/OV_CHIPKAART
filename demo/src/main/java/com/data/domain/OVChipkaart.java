package com.data.domain;

import java.sql.Date;
import java.util.List;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private double saldo;
    private int reiziger_id;
    private Reiziger reiziger;
    private List<Product> producten;

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, double saldo, int reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, double saldo, int reiziger_id, Reiziger reiziger) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
        this.reiziger = reiziger;
    }

    public OVChipkaart(int kaart_nummer, java.sql.Date geldig_tot, int klasse, double saldo, int reiziger_id, Reiziger reiziger, List<Product> producten) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
        this.reiziger = reiziger;
        this.producten = producten;
    }

    public List<Product> getProducten() {
        return producten;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Kaartnummer: " + kaart_nummer + "\n");
        sb.append("Geldig tot: " + geldig_tot + "\n");
        sb.append("Klasse: " + klasse + "\n");
        sb.append("Saldo: " + saldo + "\n");
        sb.append("Reiziger ID: " + reiziger_id + "\n");
        return sb.toString();
    }
}
