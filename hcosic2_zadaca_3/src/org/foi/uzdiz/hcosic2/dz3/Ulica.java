/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3;

/**
 *
 * @author Hrvoje
 */
public class Ulica implements Podrucja{
    
    public int id;
    public String oznakaUlice;
    public String naziv;
    public int brojMjesta;
    public int udioMali;
    public int udioSrednji;
    public int udioVeliki;
    public int brojMali;
    public int brojSrednji;
    public int brojVeliki;
    public float ukupnoStaklo;
    public float ukupnoPapir;
    public float ukupnoMetal;
    public float ukupnoBio;
    public float ukupnoMjesano;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojMjesta() {
        return brojMjesta;
    }

    public void setBrojMjesta(int brojMjesta) {
        this.brojMjesta = brojMjesta;
    }
    
    
    public int getUdioMali() {
        return udioMali;
    }

    public void setUdioMali(int udioMali) {
        this.udioMali = udioMali;
    }

    public int getUdioSrednji() {
        return udioSrednji;
    }

    public void setUdioSrednji(int udioSrednji) {
        this.udioSrednji = udioSrednji;
    }

    public int getUdioVeliki() {
        return udioVeliki;
    }

    public void setUdioVeliki(int udioVeliki) {
        this.udioVeliki = udioVeliki;
    }

    public int getBrojMali() {
        return brojMali;
    }

    public void setBrojMali(int brojMali) {
        this.brojMali = brojMali;
    }

    public int getBrojSrednji() {
        return brojSrednji;
    }

    public void setBrojSrednji(int brojSrednji) {
        this.brojSrednji = brojSrednji;
    }

    public int getBrojVeliki() {
        return brojVeliki;
    }

    public void setBrojVeliki(int brojVeliki) {
        this.brojVeliki = brojVeliki;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getOznakaUlice() {
        return oznakaUlice;
    }

    public void setOznakaUlice(String oznakaUlice) {
        this.oznakaUlice = oznakaUlice;
    }

    @Override
    public void ispis() {
        MainProgram.listaIspisPodataka.add("Dodana je ulica za određeno podrucje");
    }
    
    
}
