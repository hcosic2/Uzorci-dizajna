/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz2;

/**
 *
 * @author Hrvoje
 */
public class Vozilo {

    public int id;
    public String oznakaVozila;
    public String naziv;
    public int tip;
    public int vrsta;
    public int nosivost;
    public String vozaci;
    public float popunjenost;
    public int brojPokupljenihSpremnika = 0;
    public float kolicinaPokupljenogOtpada;
    public int brojZbrinjavanja;
    public int brojCiklusa;
    public String nazivOtpada;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTip() {
        return tip;
    }

    public int getBrojCiklusa() {
        return brojCiklusa;
    }

    public String getNazivOtpada() {
        return nazivOtpada;
    }

    public void setNazivOtpada(String nazivOtpada) {
        this.nazivOtpada = nazivOtpada;
    }
    
    

    public void setBrojCiklusa(int brojCiklusa) {
        this.brojCiklusa = brojCiklusa;
    }
    

    public void setTip(int tip) {
        this.tip = tip;
    }

    public int getVrsta() {
        return vrsta;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }

    public int getNosivost() {
        return nosivost;
    }

    public void setNosivost(int nosivost) {
        this.nosivost = nosivost;
    }

    public String getVozaci() {
        return vozaci;
    }

    public void setVozaci(String vozaci) {
        this.vozaci = vozaci;
    }

    public String getOznakaVozila() {
        return oznakaVozila;
    }

    public void setOznakaVozila(String oznakaVozila) {
        this.oznakaVozila = oznakaVozila;
    }

    public float getPopunjenost() {
        return popunjenost;
    }

    public void setPopunjenost(float popunjenost) {
        this.popunjenost = popunjenost;
    }

    public int getBrojPokupljenihSpremnika() {
        return brojPokupljenihSpremnika;
    }

    public void setBrojPokupljenihSpremnika(int brojPokupljenihSpremnika) {
        this.brojPokupljenihSpremnika = brojPokupljenihSpremnika;
    }

    public float getKolicinaPokupljenogOtpada() {
        return kolicinaPokupljenogOtpada;
    }

    public void setKolicinaPokupljenogOtpada(float kolicinaPokupljenogOtpada) {
        this.kolicinaPokupljenogOtpada = kolicinaPokupljenogOtpada;
    }

    public int getBrojZbrinjavanja() {
        return brojZbrinjavanja;
    }

    public void setBrojZbrinjavanja(int brojZbrinjavanja) {
        this.brojZbrinjavanja = brojZbrinjavanja;
    }
    
    
    
    
    

    private Vozilo(VoziloBuilder builder) {
        this.id = builder.id;
        this.oznakaVozila = builder.oznakaVozila;
        this.naziv = builder.naziv;
        this.tip = builder.tip;
        this.vrsta = builder.vrsta;
        this.nosivost = builder.nosivost;
        this.vozaci = builder.vozaci;
    }

    public static class VoziloBuilder {

        private int id;
        private String oznakaVozila;
        private String naziv;
        private int tip;
        private int vrsta;
        private int nosivost;
        private String vozaci;

        public VoziloBuilder(int id, String naziv, int tip, int vrsta, int nosivost, String vozaci, String oznakaVozila) {
            this.id = id;
            this.naziv = naziv;
            this.tip = tip;
            this.vrsta = vrsta;
            this.nosivost = nosivost;
            this.vozaci = vozaci;
            this.oznakaVozila = oznakaVozila;
        }
        
        public Vozilo build(){
            return new Vozilo(this);
        }
    }

}
