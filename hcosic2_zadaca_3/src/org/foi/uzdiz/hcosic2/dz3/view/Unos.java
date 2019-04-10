/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.foi.uzdiz.hcosic2.dz3.ANSIVT100.IspisAnsiVt100;
import org.foi.uzdiz.hcosic2.dz3.kontroler.Kontroler;

/**
 *
 * @author Hrvoje
 */
public class Unos {

    private static int brojRedovaGore;
    private static int brojRedovaDolje;
    public boolean radi = true;
    List<String> listaZaIspis = new ArrayList<>();

    public Unos(int brojRedovaGore, int brojRedovaDolje) {
        this.brojRedovaGore = brojRedovaGore;
        this.brojRedovaDolje = brojRedovaDolje;
    }

    public void unesiKomandu() {
        while (radi) {
            int koraci = brojRedovaGore + 2;
            brisiUnos();
            for (int i = 0; i <= 80; i++) {
                IspisAnsiVt100.getInstanca().prikazi(brojRedovaGore + 1, i, 31, "-");
            }
            listaZaIspis.add("Za nastavak ispisa unesite n/N, a za unos komande unesite k/K: ");

            if (brojRedovaDolje >= listaZaIspis.size()) {
                for (int i = 0; i < listaZaIspis.size(); i++) {
                    IspisAnsiVt100.getInstanca().prikazi(koraci++, 1, 31, listaZaIspis.get(i));
                }
            } else {
                listaZaIspis.remove(0);
                for (int i = 0; i < listaZaIspis.size(); i++) {
                    IspisAnsiVt100.getInstanca().prikazi(koraci++, 1, 31, listaZaIspis.get(i));
                }
            }
            Scanner scanner = new Scanner(System.in);
            String upisanaKomanda = scanner.nextLine();
            listaZaIspis.remove(listaZaIspis.size() - 1);
            listaZaIspis.add("Za nastavak ispisa unesite n/N, a za unos komande unesite k/K: " + upisanaKomanda);
            if (upisanaKomanda.equals("n") || upisanaKomanda.equals("N")) {
                Kontroler.getInstanca().ispisiEkranZaIspis();
            } else if (upisanaKomanda.equals("k") || upisanaKomanda.equals("K")) {
                brisiUnos();
                koraci = brojRedovaGore + 2;
                for (int i = 0; i <= 80; i++) {
                    IspisAnsiVt100.getInstanca().prikazi(brojRedovaGore + 1, i, 31, "-");
                }
                listaZaIspis.add("Unesite Komandu: ");
                if (brojRedovaDolje >= listaZaIspis.size()) {
                    for (int i = 0; i < listaZaIspis.size(); i++) {
                        IspisAnsiVt100.getInstanca().prikazi(koraci++, 1, 31, listaZaIspis.get(i));
                    }
                } else {
                    listaZaIspis.remove(0);
                    for (int i = 0; i < listaZaIspis.size(); i++) {
                        IspisAnsiVt100.getInstanca().prikazi(koraci++, 1, 31, listaZaIspis.get(i));
                    }
                }
                String komanda;
                Scanner scanner2 = new Scanner(System.in, "CP1250");
                komanda = scanner2.nextLine();
                listaZaIspis.remove(listaZaIspis.size() - 1);
                listaZaIspis.add("Unesite Komandu: " + komanda);
                try {
                    Kontroler.getInstanca().izvrsiKomandu(komanda);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }

    public void brisiUnos() {
        System.out.print("\033[" + (brojRedovaGore + 2) + ";0H");
        System.out.print("\033[J");
        System.out.print("\033[" + (brojRedovaGore + 2) + ";0H");
    }
}
