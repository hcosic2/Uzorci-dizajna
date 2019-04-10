/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3.model;

import org.foi.uzdiz.hcosic2.dz3.MainProgram;

/**
 *
 * @author Hrvoje
 */
public class Model {
    
    public void dohvatiPodatke(){
        
        MainProgram.kreirajVrsteSpremnika();
        MainProgram.kreirajUlice();
        MainProgram.kreirajKorisnike();
        MainProgram.kreirajVozilaIVozace();
        MainProgram.kreirajPodrucja();
        MainProgram.kreirajKomandeDispecera();
        MainProgram.rasporediSpremnike();
    }
    
    public void dodijeliKorisnkuSpremnike(){
        MainProgram.dodijeliKorisnikuSpreminke();
        MainProgram.napuniSpremnik();
    }
    
    public void zbrojiSmecePoUlicamaIPorducijima(){
        MainProgram.smecePoUlicama();
        MainProgram.smecePoPodrucjuUlice();
        MainProgram.smecePoPodcrucju();
    }
    
    public void odrediIshodisnaPodrucja(){
        MainProgram.odrediIshodisnaPodrucja();
    }
    
    public void obradiKomandeDispecera(){
        MainProgram.obradiKomandeDispecera();
    }
}
