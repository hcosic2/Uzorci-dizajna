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
public class VrstaSpremnik {
    
    public String naziv;
    public int vrsta;
    public int brojMalih;
    public int brojSrednjih;
    public int brojVelikih;
    public int nosivost;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getVrsta() {
        return vrsta;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }

    public int getBrojMalih() {
        return brojMalih;
    }

    public void setBrojMalih(int brojMalih) {
        this.brojMalih = brojMalih;
    }

    public int getBrojSrednjih() {
        return brojSrednjih;
    }

    public void setBrojSrednjih(int brojSrednjih) {
        this.brojSrednjih = brojSrednjih;
    }

    public int getBrojVelikih() {
        return brojVelikih;
    }

    public void setBrojVelikih(int brojVelikih) {
        this.brojVelikih = brojVelikih;
    }

    public int getNosivost() {
        return nosivost;
    }

    public void setNosivost(int nosivost) {
        this.nosivost = nosivost;
    }
    
    private VrstaSpremnik(VrstaSpremnikBuilder builder){
        this.naziv = builder.naziv; 
        this.vrsta = builder.vrsta; 
        this.brojMalih = builder.brojMalih; 
        this.brojSrednjih = builder.brojSrednjih; 
        this.brojVelikih = builder.brojVelikih; 
        this.nosivost = builder.nosivost; 
    
    }
    
    public static class VrstaSpremnikBuilder{
        private String naziv;
        private int vrsta;
        private int brojMalih;
        private int brojSrednjih;
        private int brojVelikih;
        private int nosivost;
        
        public VrstaSpremnikBuilder(String naziv, int vrsta, int brojMalih, int brojSrednjih, int brojVelikih, int nosivost){
            this.naziv=naziv;
            this.vrsta=vrsta;
            this.brojMalih=brojMalih;
            this.brojSrednjih=brojSrednjih;
            this.brojVelikih=brojVelikih;
            this.nosivost=nosivost;
        }
        
        public VrstaSpremnik build(){
            return new VrstaSpremnik(this);
        }
    }    
}
