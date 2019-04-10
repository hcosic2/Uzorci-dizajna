/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3;

import java.util.List;
import org.foi.uzdiz.hcosic2.dz3.state.vozila.Vozilo;

/**
 *
 * @author Hrvoje
 */
public class VozilaProxy implements Vozila{
    
    private boolean admin;
    private Vozila vozila;

    public VozilaProxy(String korisnik) {
        if("dispecer".equals(korisnik)){
            admin = true;
        }
        vozila = new VozilaImpl();
    }
    
    @Override
    public void odrediVrstuOtpada(List<Vozilo> listaVozila) {
        if(admin){
            vozila.odrediVrstuOtpada(listaVozila);
        } else{
            MainProgram.listaIspisPodataka.add("Korisnik nije administrator");
        }
    }
    
    
}
