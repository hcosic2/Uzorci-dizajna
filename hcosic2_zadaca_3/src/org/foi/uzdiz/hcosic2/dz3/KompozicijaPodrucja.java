/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hrvoje
 */
public class KompozicijaPodrucja implements Podrucja{
    
    public int id;
    public String oznakaPodrucja;
    public String nazivPodrucja;
    public float ukupnoStaklo;
    public float ukupnoPapir;
    public float ukupnoMetal;
    public float ukupnoBio;
    public float ukupnoMjesano;
    public String dijeloviPodrucja;
    public List<String> listaPodpodrucjaUlice = new ArrayList();
    public List<String> listaPodpodrucjaPodrucja = new ArrayList();
    public List<Ulica> listaUlica = new ArrayList();
    public List<KompozicijaPodrucja> listaPodrucja = new ArrayList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOznakaPodrucja() {
        return oznakaPodrucja;
    }

    public void setOznakaPodrucja(String oznakaPodrucja) {
        this.oznakaPodrucja = oznakaPodrucja;
    }

    public String getNazivPodrucja() {
        return nazivPodrucja;
    }

    public String getDijeloviPodrucja() {
        return dijeloviPodrucja;
    }

    public void setDijeloviPodrucja(String dijeloviPodrucja) {
        this.dijeloviPodrucja = dijeloviPodrucja;
    }

    public List<Ulica> getListaUlica() {
        return listaUlica;
    }

    public void setListaUlica(List<Ulica> listaUlica) {
        this.listaUlica = listaUlica;
    }

    public List<KompozicijaPodrucja> getListaPodrucja() {
        return listaPodrucja;
    }

    public void setListaPodrucja(List<KompozicijaPodrucja> listaPodrucja) {
        this.listaPodrucja = listaPodrucja;
    }
    

    public void setNazivPodrucja(String nazivPodrucja) {
        this.nazivPodrucja = nazivPodrucja;
    }

    public float getUkupnoStaklo() {
        return ukupnoStaklo;
    }

    public void setUkupnoStaklo(float ukupnoStaklo) {
        this.ukupnoStaklo = ukupnoStaklo;
    }

    public float getUkupnoPapir() {
        return ukupnoPapir;
    }

    public void setUkupnoPapir(float ukupnoPapir) {
        this.ukupnoPapir = ukupnoPapir;
    }

    public float getUkupnoMetal() {
        return ukupnoMetal;
    }

    public void setUkupnoMetal(float ukupnoMetal) {
        this.ukupnoMetal = ukupnoMetal;
    }

    public float getUkupnoBio() {
        return ukupnoBio;
    }

    public void setUkupnoBio(float ukupnoBio) {
        this.ukupnoBio = ukupnoBio;
    }

    public float getUkupnoMjesano() {
        return ukupnoMjesano;
    }

    public void setUkupnoMjesano(float ukupnoMjesano) {
        this.ukupnoMjesano = ukupnoMjesano;
    }

    public List<String> getListaPodpodrucjaUlice() {
        return listaPodpodrucjaUlice;
    }

    public void setListaPodpodrucjaUlice(List<String> listaPodpodrucjaUlice) {
        this.listaPodpodrucjaUlice = listaPodpodrucjaUlice;
    }

    public List<String> getListaPodpodrucjaPodrucja() {
        return listaPodpodrucjaPodrucja;
    }

    public void setListaPodpodrucjaPodrucja(List<String> listaPodpodrucjaPodrucja) {
        this.listaPodpodrucjaPodrucja = listaPodpodrucjaPodrucja;
    }

    public void dodajPodpodrucja(String podpodrucje, Object object) {
        if(podpodrucje.equals("p")){
            listaPodrucja.add((KompozicijaPodrucja)object);
        } else if(podpodrucje.equals("u")){
            listaUlica.add((Ulica)object);
        }
    }

    @Override
    public void ispis() {
        MainProgram.listaIspisPodataka.add("Dodano je podpodrucje za podrucje");
    }
    
    
}
