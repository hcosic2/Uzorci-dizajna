/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hrvoje
 */
public class Spremnik {

    public int id;
    public String vrstaSpremnik;
    public int ulica;
    public Float ukupnoSmeca;
    public String vrstaKorisnik;
    public List<Korisnik> korisnici = new ArrayList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVrstaSpremnik() {
        return vrstaSpremnik;
    }

    public String getVrstaKorisnik() {
        return vrstaKorisnik;
    }

    public void setVrstaKorisnik(String vrstaKorisnik) {
        this.vrstaKorisnik = vrstaKorisnik;
    }

    
    public void setVrstaSpremnik(String vrstaSpremnik) {
        this.vrstaSpremnik = vrstaSpremnik;
    }

    public int getUlica() {
        return ulica;
    }

    public void setUlica(int ulica) {
        this.ulica = ulica;
    }

    private Spremnik(SpremnikBuilder builder) {
        this.id = builder.id;
        this.vrstaSpremnik = builder.vrstaSpremnik;
        this.ulica = builder.ulica;
        this.korisnici = builder.korisnici;
        this.vrstaKorisnik = builder.vrstaKorisnik;

    }

    public static class SpremnikBuilder {

        private int id;
        private String vrstaSpremnik;
        private int ulica;
        private String vrstaKorisnik;
        private List<Korisnik> korisnici = new ArrayList();

        public SpremnikBuilder(int id, String vrstaSpremnik, int ulica, String vrstaKorisnik, List korisnici) {
            this.id = id;
            this.vrstaSpremnik = vrstaSpremnik;
            this.ulica = ulica;
            this.vrstaKorisnik = vrstaKorisnik;
            this.korisnici = korisnici;
        }

        public Spremnik build() {
            return new Spremnik(this);
        }

    }

    public Float getUkupnoSmeca() {
        return ukupnoSmeca;
    }

    public void setUkupnoSmeca(Float ukupnoSmeca) {
        this.ukupnoSmeca = ukupnoSmeca;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    
    
}
