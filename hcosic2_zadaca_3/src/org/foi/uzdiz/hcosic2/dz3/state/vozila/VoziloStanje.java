/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3.state.vozila;

/**
 *
 * @author Hrvoje
 */
public interface VoziloStanje {
    
    public void parking(Vozilo vozilo);
    
    public void prikupljaOtpad(Vozilo vozilo);
    
    public void kvar(Vozilo vozilo);
    
    public void kontrola(Vozilo vozilo);
    
    public void prazniSmece(Vozilo vozilo);
    
}
