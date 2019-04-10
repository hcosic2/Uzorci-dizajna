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
public class KomandaVozila extends Komanda {
    
    public String vozila;

    public KomandaVozila() {
    }

    public KomandaVozila(KomandaVozila target) {
        super(target);
        if (target != null) {
            this.vozila = target.vozila;
        }
    }

    @Override
    public Komanda clone() {
        return new KomandaVozila(this);
    }

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof KomandaVozila) || !super.equals(object2)) {
            return false;
        }
        KomandaVozila komanda2 = (KomandaVozila) object2;
        return komanda2.vozila == vozila;

    }

    public String getVozila() {
        return vozila;
    }

    public void setVozila(String vozila) {
        this.vozila = vozila;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKomanda() {
        return komanda;
    }

    public void setKomanda(String komanda) {
        this.komanda = komanda;
    }
    
    

}
