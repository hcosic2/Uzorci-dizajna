/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3.view;

import java.util.List;
import java.util.Scanner;
import org.foi.uzdiz.hcosic2.dz3.ANSIVT100.IspisAnsiVt100;

/**
 *
 * @author Hrvoje
 */
public class Ispis {

    private static int brojRedovaGore;
    private static List<String> listaPodatakIspis;

    public Ispis(int brojRedovaGore, List<String> listaPodatakaIspis) {
        this.brojRedovaGore = brojRedovaGore;
        this.listaPodatakIspis = listaPodatakaIspis;

        //ispisiPodatke();
    }

    public void ispisiPodatke() {
        IspisAnsiVt100.getInstanca().brisiEkran();
        int koraci;
        if (listaPodatakIspis.isEmpty()) {
            IspisAnsiVt100.getInstanca().prikazi(1, 1, 35, "Svi podaci su ispisani");
        } else {
            if (brojRedovaGore >= listaPodatakIspis.size()) {
                koraci = listaPodatakIspis.size();
            } else {
                koraci = brojRedovaGore;
            }
            for (int i = 0; i < koraci; i++) {
                IspisAnsiVt100.getInstanca().prikazi(i + 1, 1, 35, listaPodatakIspis.get(0));
                listaPodatakIspis.remove(0);
            }
        }
    }

}
