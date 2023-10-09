package com.data.domain;

import java.util.List;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private List<OVChipkaart> ovchipkaarten;

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product(int product_nummer, String naam, String beschrijving, double prijs, List<OVChipkaart> ovchipkaarten) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.ovchipkaarten = ovchipkaarten;
    }

    public List<OVChipkaart> getOvchipkaarten() {
        return ovchipkaarten;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setOvchipkaarten(List<OVChipkaart> ovchipkaarten) {
        this.ovchipkaarten = ovchipkaarten;
    }
    
    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product: ");
        sb.append(this.product_nummer);
        sb.append(", ");
        sb.append(this.naam);
        sb.append(", ");
        sb.append(this.beschrijving);
        sb.append(", ");
        sb.append(this.prijs);
        return sb.toString();
    }

}
