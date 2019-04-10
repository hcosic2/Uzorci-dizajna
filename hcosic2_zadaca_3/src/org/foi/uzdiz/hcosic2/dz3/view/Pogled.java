/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3.view;

import java.util.List;

/**
 *
 * @author Hrvoje
 */
public class Pogled {
    
    private Ispis ispis;
    private Unos unos;

    public Pogled(Ispis ispis, Unos unos) {
        this.ispis = ispis;
        this.unos = unos;
    }

    public Ispis getIspis() {
        return ispis;
    }

    public void setIspis(Ispis ispis) {
        this.ispis = ispis;
    }

    public Unos getUnos() {
        return unos;
    }

    public void setUnos(Unos unos) {
        this.unos = unos;
    }
}
