/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz2;

import java.util.List;

/**
 *
 * @author Hrvoje
 */
public class VozilaImpl implements Vozila {

    @Override
    public void odrediVrstuOtpada(List<Vozilo> listaVozila) {
        for (Vozilo vozilo : listaVozila) {
            if (vozilo.getVrsta() == 0) {
                vozilo.setNazivOtpada("staklo");
            } else if (vozilo.getVrsta() == 1) {
                vozilo.setNazivOtpada("papir");
            } else if (vozilo.getVrsta() == 2) {
                vozilo.setNazivOtpada("metal");
            } else if (vozilo.getVrsta() == 3) {
                vozilo.setNazivOtpada("bio");
            } else if (vozilo.getVrsta() == 4) {
                vozilo.setNazivOtpada("mjesano");
            }
        }
    }

}
