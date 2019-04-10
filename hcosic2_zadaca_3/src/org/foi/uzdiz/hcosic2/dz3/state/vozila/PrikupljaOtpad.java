/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3.state.vozila;

import static org.foi.uzdiz.hcosic2.dz3.MainProgram.listaIspisPodataka;

/**
 *
 * @author Hrvoje
 */
public class PrikupljaOtpad implements VoziloStanje{

    @Override
    public void parking(Vozilo vozilo) {
        vozilo.setStanje(new Parking());
        listaIspisPodataka.add(vozilo.naziv +" je na parkingu");
    }

    @Override
    public void prikupljaOtpad(Vozilo vozilo) {
        vozilo.setStanje(new PrikupljaOtpad());
        listaIspisPodataka.add(vozilo.naziv +" je spremno za prikupljanje otpada");
    }

    @Override
    public void kvar(Vozilo vozilo) {
        vozilo.setStanje(new Kvar());
        listaIspisPodataka.add(vozilo.naziv +" je u kvaru");
    }

    @Override
    public void kontrola(Vozilo vozilo) {
        vozilo.setStanje(new Kontrola());
        listaIspisPodataka.add(vozilo.naziv +" je poslano na kontrolu");
    }

    @Override
    public void prazniSmece(Vozilo vozilo) {
        vozilo.setStanje(new PrazniSmece());
        listaIspisPodataka.add(vozilo.naziv +" ide na zbrinjavanje otpada");
    }
    
}
