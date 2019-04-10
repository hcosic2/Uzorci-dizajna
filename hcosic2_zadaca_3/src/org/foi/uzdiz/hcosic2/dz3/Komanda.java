/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3;

import java.util.Objects;

/**
 *
 * @author Hrvoje
 */
public abstract class Komanda {
    
    public int id;
    public String komanda;

    public Komanda() {
    }
    
    public Komanda(Komanda target){
        if(target != null){
            this.id = target.id;
            this.komanda = target.komanda;
        }
    }
    public abstract Komanda clone();

    @Override
    public boolean equals(Object object2) {
        if(!(object2 instanceof Komanda))return false;
        Komanda komanda2 = (Komanda) object2;
        return komanda2.id == id && komanda2.komanda == komanda;
    }
    
    
    
}
