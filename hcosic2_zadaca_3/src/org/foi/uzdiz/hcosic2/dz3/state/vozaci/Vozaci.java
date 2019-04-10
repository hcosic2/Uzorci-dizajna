/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3.state.vozaci;

/**
 *
 * @author Hrvoje
 */
public class Vozaci {
    
    public int id;
    public String ime;
    VozacStanje stanje;

    public Vozaci() {
        stanje = new Vozi();
    }
    
    public void setStanje(VozacStanje stanje) {
        this.stanje = stanje;
    }

    public VozacStanje getStanje() {
        return stanje;
    }
    
    public void vozi(){
        this.stanje.vozi(this);
    }
    
    public void godisnji(){
        this.stanje.godisnji(this);
    }
 
    public void bolovanje(){
        this.stanje.bolovanje(this);
    }
    
    public void otkaz(){
        this.stanje.otkaz(this);
    }
    
    public void novi(){
        this.stanje.novi(this);
    }
}
