/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3.state.vozaci;

import static org.foi.uzdiz.hcosic2.dz3.MainProgram.listaIspisPodataka;

/**
 *
 * @author Hrvoje
 */
public class Otkaz implements VozacStanje{

    @Override
    public void vozi(Vozaci vozac) {
         listaIspisPodataka.add(vozac.ime +" je dobio otkaz i ne moze voziti vozilo");
    }

    @Override
    public void godisnji(Vozaci vozac) {
         listaIspisPodataka.add(vozac.ime +" je dobio otkaz i ne moze ici na godisnji jer nije zaposlen");
    }

    @Override
    public void bolovanje(Vozaci vozac) {
         listaIspisPodataka.add(vozac.ime +" je dobio otkaz i ne moze ici na bolovanje jer nije zaposlen");
    }

    @Override
    public void otkaz(Vozaci vozac) {
         listaIspisPodataka.add(vozac.ime +" je vec dobio otkaz");
    }

    @Override
    public void novi(Vozaci vozac) {
         listaIspisPodataka.add(vozac.ime +" je dobio otkaz i ne moze biti ponovo zaposlen");
    }

}