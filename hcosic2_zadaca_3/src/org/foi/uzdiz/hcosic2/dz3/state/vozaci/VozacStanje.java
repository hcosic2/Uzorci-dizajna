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
public interface VozacStanje {
    
    public void vozi(Vozaci vozac);

    public void godisnji(Vozaci vozac);
    
    public void bolovanje(Vozaci vozac);
    
    public void otkaz(Vozaci vozac);
    
    public void novi(Vozaci vozac);

    
}
