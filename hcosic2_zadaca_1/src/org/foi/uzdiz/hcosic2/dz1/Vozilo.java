/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz1;

/**
 *
 * @author Hrvoje
 */
public class Vozilo {

    public int id;
    public String naziv;
    public int tip;
    public int vrsta;
    public int nosivost;
    public String vozaci;

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

    private Vozilo(VoziloBuilder builder) {
        this.id = builder.id;
        this.naziv = builder.naziv;
        this.tip = builder.tip;
        this.vrsta = builder.vrsta;
        this.nosivost = builder.nosivost;
        this.vozaci = builder.vozaci;
    }

    public static class VoziloBuilder {

        private int id;
        private String naziv;
        private int tip;
        private int vrsta;
        private int nosivost;
        private String vozaci;

        public VoziloBuilder(int id, String naziv, int tip, int vrsta, int nosivost, String vozaci) {
            this.id = id;
            this.naziv = naziv;
            this.tip = tip;
            this.vrsta = vrsta;
            this.nosivost = nosivost;
            this.vozaci = vozaci;
        }
        
        public Vozilo build(){
            return new Vozilo(this);
        }
    }

}
