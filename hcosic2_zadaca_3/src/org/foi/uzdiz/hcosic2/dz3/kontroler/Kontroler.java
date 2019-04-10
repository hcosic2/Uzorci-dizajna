/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3.kontroler;

import org.foi.uzdiz.hcosic2.dz3.MainProgram;
import org.foi.uzdiz.hcosic2.dz3.model.Model;
import org.foi.uzdiz.hcosic2.dz3.view.Pogled;

/**
 *
 * @author Hrvoje
 */
public class Kontroler {
    
    private static Kontroler instanca = null;
    private Pogled pogled;
    private Model model;
    
    
    public static void izvrsiKomandu(String komanda){
        MainProgram.obradiUnesenuKomandu(komanda);
    }
    
    public static Kontroler getInstanca(){
        if(instanca == null) instanca = new Kontroler();
        return instanca;
    }
    
    public void ispisiEkranZaUnos(){
        this.pogled.getUnos().unesiKomandu();
    }
    
    public void ispisiEkranZaIspis(){
        this.pogled.getIspis().ispisiPodatke();
    }

    public Pogled getPogled() {
        return pogled;
    }

    public void setPogled(Pogled pogled) {
        this.pogled = pogled;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }  
}
