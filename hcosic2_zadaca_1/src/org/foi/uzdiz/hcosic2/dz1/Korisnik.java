/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz1;

/**
 *
 * @author Hrvoje
 */
public class Korisnik {

    private int id;
    private int ulica;
    private String kategorija;
    private float kolicinaStaklo;
    private float kolicinaPapir;
    private float kolicinaMetal;
    private float kolicinaBio;
    private float kolicinaMjesano;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUlica() {
        return ulica;
    }

    public void setUlica(int ulica) {
        this.ulica = ulica;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public float getKolicinaStaklo() {
        return kolicinaStaklo;
    }

    public void setKolicinaStaklo(float kolicinaStaklo) {
        this.kolicinaStaklo = kolicinaStaklo;
    }

    public float getKolicinaPapir() {
        return kolicinaPapir;
    }

    public void setKolicinaPapir(float kolicinaPapir) {
        this.kolicinaPapir = kolicinaPapir;
    }

    public float getKolicinaMetal() {
        return kolicinaMetal;
    }

    public void setKolicinaMetal(float kolicinaMetal) {
        this.kolicinaMetal = kolicinaMetal;
    }

    public float getKolicinaMjesano() {
        return kolicinaMjesano;
    }

    public void setKolicinaMjesano(float kolicinaMjesano) {
        this.kolicinaMjesano = kolicinaMjesano;
    }

    public float getKolicinaBio() {
        return kolicinaBio;
    }

    public void setKolicinaBio(float kolicinaBio) {
        this.kolicinaBio = kolicinaBio;
    }

}
