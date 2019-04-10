/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3.state.vozaci;

import org.foi.uzdiz.hcosic2.dz3.MainProgram;
import static org.foi.uzdiz.hcosic2.dz3.MainProgram.listaIspisPodataka;

/**
 *
 * @author Hrvoje
 */
public class Vozi implements VozacStanje {

    @Override
    public void vozi(Vozaci vozac) {
        vozac.setStanje(new Vozi());
         listaIspisPodataka.add(vozac.ime + " trenutno vozi");
    }

    @Override
    public void godisnji(Vozaci vozac) {
        vozac.setStanje(new Godisnji());
         listaIspisPodataka.add(vozac.ime +" je na godisnjem odmoru");
    }

    @Override
    public void bolovanje(Vozaci vozac) {
        vozac.setStanje(new Bolovanje());
         listaIspisPodataka.add(vozac.ime +" je trenutno na bolovanju");
    }

    @Override
    public void otkaz(Vozaci vozac) {
        vozac.setStanje(new Otkaz());
         listaIspisPodataka.add(vozac.ime +" je dobio otkaz");
    }

    @Override
    public void novi(Vozaci vozac) {
        vozac.setStanje(new Novi());
         listaIspisPodataka.add(vozac.ime +" je novozaposleni vozac");
    }

}
