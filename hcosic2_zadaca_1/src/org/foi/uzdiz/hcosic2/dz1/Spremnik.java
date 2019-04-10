/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz1;

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
    public String vrstaKorisnik;

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
        this.vrstaKorisnik = builder.vrstaKorisnik;

    }

    public static class SpremnikBuilder {

        private int id;
        private String vrstaSpremnik;
        private int ulica;
        private String vrstaKorisnik;

        public SpremnikBuilder(int id, String vrstaSpremnik, int ulica, String vrstaKorisnik) {
            this.id = id;
            this.vrstaSpremnik = vrstaSpremnik;
            this.ulica = ulica;
            this.vrstaKorisnik = vrstaKorisnik;
        }

        public Spremnik build() {
            return new Spremnik(this);
        }

    }

}
