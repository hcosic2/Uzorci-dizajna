/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hrvoje
 */
public class MainProgram {

    private static List<String> listaPodatakaSpremnici = new ArrayList();
    private static List<String> listaPodatakaUlice = new ArrayList();
    private static List<String> listaPodatakaVozila = new ArrayList();
    private static List<VrstaSpremnik> listaVrstaSpremnika = new ArrayList();
    private static List<Ulica> listaUlica = new ArrayList();
    private static List<Korisnik> listaKorisnika = new ArrayList();
    private static List<Spremnik> listaSpremnika = new ArrayList();
    private static List<Vozilo> listaVozila = new ArrayList();
    private static Matcher matcher;
    private static Matcher matcher2;
    private static DohvatiParametre parametri;
    private static int decimale;
    private static int sjemeGeneratora;
    private static Random randomBroj = new Random(sjemeGeneratora);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String podaci = args[0];
        parametri = DohvatiParametre.getInstance(podaci);
        parametri.preuzmiParametre(podaci);
        sjemeGeneratora = Integer.parseInt(parametri.dajParametar("sjemeGeneratora"));
        String datotekaSpremnici = parametri.dajParametar("spremnici");
        String datotekaUlice = parametri.dajParametar("ulice");
        String datotekaVozila = parametri.dajParametar("vozila");
        int ispis = Integer.parseInt(parametri.dajParametar("ispis"));
        decimale = Integer.parseInt(parametri.dajParametar("brojDecimala"));

        listaPodatakaSpremnici = dohvatiPodatkeSpremnici(datotekaSpremnici);
        kreirajVrsteSpremnika();
        listaPodatakaUlice = dohvatiPodatkeUlice(datotekaUlice);
        kreirajUlice();
        kreirajKorisnike();
        rasporediSpremnike();
        listaPodatakaVozila = dohvatiPodatkeVozila(datotekaVozila);
        kreirajVozila();
        if (ispis == 0) {
            ispisiVrsteSpremnika();
            ispisiUlice();
            ispisiVozila();
            ispisiKorisnike();
            ispisiSpremnike();
        }
    }

    public static int randomInt(int granicaOd, int granicaDo, int sjemeGeneratora) {
        int broj = randomBroj.nextInt(granicaDo - granicaOd) + granicaOd;
        return broj;
    }

    public static long randomLong(long granicaOd, long granicaDo, int sjemeGeneratora) {
        long broj = granicaOd + ((long) (randomBroj.nextDouble() * (granicaDo - granicaOd)));
        return broj;
    }

    public static float randomFloat(float granicaOd, float granicaDo, int sjemeGeneratora, int brojDecimala) {
        String rezultat = "";
        float broj = granicaOd + randomBroj.nextFloat() * (granicaDo - granicaOd);
        DecimalFormat decimal = new DecimalFormat();
        decimal.setMaximumFractionDigits(brojDecimala);
        String[] deicmalniBroj = decimal.format(broj).split(",");
        try {
            rezultat = deicmalniBroj[0] + "." + deicmalniBroj[1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            rezultat = deicmalniBroj[0] + "." + randomInt(1, 10, sjemeGeneratora);
        }
        return Float.parseFloat(rezultat);
    }

    public static List dohvatiPodatkeSpremnici(String nazivDatoteke) {
        List<String> lista = new ArrayList();
        try {
            Scanner inputStream = new Scanner(new File("./" + nazivDatoteke));
            while (inputStream.hasNext()) {
                lista.add(inputStream.next());

            }
            inputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public static void kreirajVrsteSpremnika() {
        String regex = "(staklo|papir|metal|bio|mješano);([0-1]);([0-9]*);([0-9]*);([0-9]*);([0-9]*)$";
        for (int i = 0; i < listaPodatakaSpremnici.size(); i++) {
            Pattern uzorak = Pattern.compile(regex);
            matcher = uzorak.matcher(listaPodatakaSpremnici.get(i));
            if (matcher.matches()) {
                String zapis = listaPodatakaSpremnici.get(i);
                String[] dijeloviZapisa = zapis.split(";");

                VrstaSpremnik spremnik = new VrstaSpremnik.VrstaSpremnikBuilder(dijeloviZapisa[0],
                        Integer.parseInt(dijeloviZapisa[1]), Integer.parseInt(dijeloviZapisa[2]),
                        Integer.parseInt(dijeloviZapisa[3]), Integer.parseInt(dijeloviZapisa[4]), Integer.parseInt(dijeloviZapisa[5]))
                        .build();
                listaVrstaSpremnika.add(spremnik);
            } else {
                System.out.println("Krivo unesena vrsta spremnika!");
            }

        }
    }

    public static List dohvatiPodatkeUlice(String nazivDatoteke) {
        List<String> lista = new ArrayList();
        try {
            Scanner inputStream = new Scanner(new File("./" + nazivDatoteke));
            inputStream.useDelimiter("\n");
            while (inputStream.hasNext()) {
                String rezultat = inputStream.next();

                lista.add(rezultat.substring(0, rezultat.length() - 1));

            }
            inputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public static void kreirajUlice() {
        String regex = "([^\\ž]+);([0-9]*);([0-9]*);([0-9]*);([0-9]*)$";
        int id = 1;
        for (int i = 0; i < listaPodatakaUlice.size(); i++) {
            Pattern uzorak = Pattern.compile(regex);
            matcher2 = uzorak.matcher(listaPodatakaUlice.get(i));
            if (matcher2.matches()) {
                String zapis = listaPodatakaUlice.get(i);
                String[] dijeloviZapisa = zapis.split(";");
                Ulica ulica = new Ulica();
                ulica.setId(id++);
                ulica.setNaziv(dijeloviZapisa[0]);
                ulica.setBrojMjesta(Integer.parseInt(dijeloviZapisa[1]));
                ulica.setUdioMali(Integer.parseInt(dijeloviZapisa[2]));
                ulica.setUdioSrednji(Integer.parseInt(dijeloviZapisa[3]));
                ulica.setUdioVeliki(Integer.parseInt(dijeloviZapisa[4]));
                float brojMali = (float) (Integer.parseInt(dijeloviZapisa[1])) / 100 * (Integer.parseInt(dijeloviZapisa[2]));
                float brojSrednji = (float) (Integer.parseInt(dijeloviZapisa[1])) / 100 * (Integer.parseInt(dijeloviZapisa[3]));
                if (brojMali >= (float) ((int) brojMali + 0.5)) {
                    brojMali++;
                    ulica.setBrojMali((int) brojMali);
                } else {
                    ulica.setBrojMali((int) brojMali);
                }
                if (brojSrednji >= (float) ((int) brojSrednji + 0.5)) {
                    brojSrednji++;
                    ulica.setBrojSrednji((int) brojSrednji);
                } else {
                    ulica.setBrojSrednji((int) brojSrednji);
                }
                ulica.setBrojVeliki(Integer.parseInt(dijeloviZapisa[1]) - ((int) brojMali + (int) brojSrednji));
                listaUlica.add(ulica);
            } else {
                System.out.println("Krivo unesena ulica!");
            }
        }
    }

    public static void kreirajKorisnike() {
        int id = 1;
        for (int i = 0; i < listaUlica.size(); i++) {
            int brojMali = 1;
            int brojSrednji = 1;
            int brojVeliki = 1;
            for (int e = 0; e < listaUlica.get(i).brojMjesta; e++) {
                Korisnik korisnik = new Korisnik();
                korisnik.setId(id++);
                korisnik.setUlica(listaUlica.get(i).getId());
                if (listaUlica.get(i).getBrojMali() != 0 && brojMali <= listaUlica.get(i).getBrojMali()) {
                    brojMali++;
                    korisnik.setKategorija("mali");
                    int maliMin = Integer.parseInt(parametri.dajParametar("maliMin"));
                    int gornjaGranicaStaklo = Integer.parseInt(parametri.dajParametar("maliStaklo"));
                    int gornjaGranicaPapir = Integer.parseInt(parametri.dajParametar("maliPapir"));
                    int gornjaGranicaMetal = Integer.parseInt(parametri.dajParametar("maliMetal"));
                    int gornjaGranicaBio = Integer.parseInt(parametri.dajParametar("maliBio"));
                    int gornjaGranicaMjesano = Integer.parseInt(parametri.dajParametar("maliMjesano"));
                    korisnik.setKolicinaStaklo(randomFloat((float) gornjaGranicaStaklo / 100 * maliMin, (float) gornjaGranicaStaklo, sjemeGeneratora, decimale));
                    korisnik.setKolicinaPapir(randomFloat((float) gornjaGranicaPapir / 100 * maliMin, (float) gornjaGranicaPapir, sjemeGeneratora, decimale));
                    korisnik.setKolicinaMetal(randomFloat((float) gornjaGranicaMetal / 100 * maliMin, (float) gornjaGranicaMetal, sjemeGeneratora, decimale));
                    korisnik.setKolicinaBio(randomFloat((float) gornjaGranicaBio / 100 * maliMin, (float) gornjaGranicaBio, sjemeGeneratora, decimale));
                    korisnik.setKolicinaMjesano(randomFloat((float) gornjaGranicaMjesano / 100 * maliMin, (float) gornjaGranicaMjesano, sjemeGeneratora, decimale));
                } else if (listaUlica.get(i).getBrojSrednji() != 0 && brojSrednji <= listaUlica.get(i).getBrojSrednji()) {
                    brojSrednji++;
                    korisnik.setKategorija("srednji");
                    int srednjiMin = Integer.parseInt(parametri.dajParametar("srednjiMin"));
                    int gornjaGranicaStaklo = Integer.parseInt(parametri.dajParametar("srednjiStaklo"));
                    int gornjaGranicaPapir = Integer.parseInt(parametri.dajParametar("srednjiPapir"));
                    int gornjaGranicaMetal = Integer.parseInt(parametri.dajParametar("srednjiMetal"));
                    int gornjaGranicaBio = Integer.parseInt(parametri.dajParametar("srednjiBio"));
                    int gornjaGranicaMjesano = Integer.parseInt(parametri.dajParametar("srednjiMjesano"));
                    korisnik.setKolicinaStaklo(randomFloat((float) gornjaGranicaStaklo / 100 * srednjiMin, (float) gornjaGranicaStaklo, sjemeGeneratora, decimale));
                    korisnik.setKolicinaPapir(randomFloat((float) gornjaGranicaPapir / 100 * srednjiMin, (float) gornjaGranicaPapir, sjemeGeneratora, decimale));
                    korisnik.setKolicinaMetal(randomFloat((float) gornjaGranicaMetal / 100 * srednjiMin, (float) gornjaGranicaMetal, sjemeGeneratora, decimale));
                    korisnik.setKolicinaBio(randomFloat((float) gornjaGranicaBio / 100 * srednjiMin, (float) gornjaGranicaBio, sjemeGeneratora, decimale));
                    korisnik.setKolicinaMjesano(randomFloat((float) gornjaGranicaMjesano / 100 * srednjiMin, (float) gornjaGranicaMjesano, sjemeGeneratora, decimale));
                } else if (listaUlica.get(i).getBrojVeliki() != 0 && brojVeliki <= listaUlica.get(i).getBrojVeliki()) {
                    brojVeliki++;
                    korisnik.setKategorija("veliki");
                    int velikiMin = Integer.parseInt(parametri.dajParametar("velikiMin"));
                    int gornjaGranicaStaklo = Integer.parseInt(parametri.dajParametar("velikiStaklo"));
                    int gornjaGranicaPapir = Integer.parseInt(parametri.dajParametar("velikiPapir"));
                    int gornjaGranicaMetal = Integer.parseInt(parametri.dajParametar("velikiMetal"));
                    int gornjaGranicaBio = Integer.parseInt(parametri.dajParametar("velikiBio"));
                    int gornjaGranicaMjesano = Integer.parseInt(parametri.dajParametar("velikiMjesano"));
                    korisnik.setKolicinaStaklo(randomFloat((float) gornjaGranicaStaklo / 100 * velikiMin, (float) gornjaGranicaStaklo, sjemeGeneratora, decimale));
                    korisnik.setKolicinaPapir(randomFloat((float) gornjaGranicaPapir / 100 * velikiMin, (float) gornjaGranicaPapir, sjemeGeneratora, decimale));
                    korisnik.setKolicinaMetal(randomFloat((float) gornjaGranicaMetal / 100 * velikiMin, (float) gornjaGranicaMetal, sjemeGeneratora, decimale));
                    korisnik.setKolicinaBio(randomFloat((float) gornjaGranicaBio / 100 * velikiMin, (float) gornjaGranicaBio, sjemeGeneratora, decimale));
                    korisnik.setKolicinaMjesano(randomFloat((float) gornjaGranicaMjesano / 100 * velikiMin, (float) gornjaGranicaMjesano, sjemeGeneratora, decimale));
                }
                listaKorisnika.add(korisnik);
            }
        }
    }

    public static void rasporediSpremnike() {
        int id = 1;
        for (int i = 0; i < listaVrstaSpremnika.size(); i++) {
            for (int e = 0; e < listaUlica.size(); e++) {
                boolean pretovar = true;
                while (pretovar) {
                    pretovar = false;
                    int brojMalih = 0;
                    int brojSrednjih = 0;
                    int brojVelikih = 0;
                    for (int a = 0; a < listaSpremnika.size(); a++) {
                        if (listaSpremnika.get(a).getVrstaKorisnik().equals("mali")
                                && listaSpremnika.get(a).getVrstaSpremnik().equals(listaVrstaSpremnika.get(i).getNaziv())
                                && listaSpremnika.get(a).getUlica() == listaUlica.get(e).getId()) {
                            brojMalih += listaVrstaSpremnika.get(i).getBrojMalih();
                        }
                        if (listaSpremnika.get(a).getVrstaKorisnik().equals("srednji")
                                && listaSpremnika.get(a).getVrstaSpremnik().equals(listaVrstaSpremnika.get(i).getNaziv())
                                && listaSpremnika.get(a).getUlica() == listaUlica.get(e).getId()) {
                            brojSrednjih += listaVrstaSpremnika.get(i).getBrojSrednjih();
                        }
                        if (listaSpremnika.get(a).getVrstaKorisnik().equals("veliki")
                                && listaSpremnika.get(a).getVrstaSpremnik().equals(listaVrstaSpremnika.get(i).getNaziv())
                                && listaSpremnika.get(a).getUlica() == listaUlica.get(e).getId()) {
                            brojVelikih += listaVrstaSpremnika.get(i).getBrojVelikih();
                        }
                    }
                    if (brojMalih < listaUlica.get(e).getBrojMali() && listaVrstaSpremnika.get(i).getBrojMalih() > 0) {
                        Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(i).getNaziv(), listaUlica.get(e).getId(), "mali")
                                .build();
                        listaSpremnika.add(spremnik);
                        pretovar = true;
                    }
                    if (brojSrednjih < listaUlica.get(e).getBrojSrednji() && listaVrstaSpremnika.get(i).getBrojSrednjih() > 0) {
                        Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(i).getNaziv(), listaUlica.get(e).getId(), "srednji")
                                .build();
                        listaSpremnika.add(spremnik);
                        pretovar = true;
                    }
                    if (brojVelikih < listaUlica.get(e).getBrojVeliki() && listaVrstaSpremnika.get(i).getBrojVelikih() > 0) {
                        Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(i).getNaziv(), listaUlica.get(e).getId(), "veliki")
                                .build();
                        listaSpremnika.add(spremnik);
                        pretovar = true;
                    }
                }
            }
        }
    }

    public static List dohvatiPodatkeVozila(String nazivDatoteke) {
        List<String> lista = new ArrayList();
        try {
            Scanner inputStream = new Scanner(new File("./" + nazivDatoteke));
            inputStream.useDelimiter("\n");
            while (inputStream.hasNext()) {
                lista.add(inputStream.next());

            }
            inputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public static void kreirajVozila() {
        String regex = "([^\\ž]+);([0-1]);([0-4]);([0-9]*);([^\\ž]+).*$";
        int id = 1;
        for (int i = 0; i < listaPodatakaVozila.size(); i++) {
            Pattern uzorak = Pattern.compile(regex);
            Matcher matcher = uzorak.matcher(listaPodatakaVozila.get(i));
            if (matcher.matches()) {
                String zapis = listaPodatakaVozila.get(i);
                String[] dijeloviZapisa = zapis.split(";");
                Vozilo vozilo = new Vozilo.VoziloBuilder(id++, dijeloviZapisa[0], Integer.parseInt(dijeloviZapisa[1]),
                        Integer.parseInt(dijeloviZapisa[2]), Integer.parseInt(dijeloviZapisa[3]), dijeloviZapisa[4])
                        .build();
                listaVozila.add(vozilo);
            } else {
                System.out.println("Krivo uneseo vozilo!");
            }
        }
    }

    public static void ispisiVrsteSpremnika() {
        System.out.println("\n|Vrste spremnika:");
        for (int i = 0; i < listaVrstaSpremnika.size(); i++) {
            System.out.println("|Naziv: " + listaVrstaSpremnika.get(i).getNaziv() + " |Vrsta: " + listaVrstaSpremnika.get(i).getVrsta()
                    + " |1 na broj malih: " + listaVrstaSpremnika.get(i).getBrojMalih() + " |1 na broj srednjih: "
                    + listaVrstaSpremnika.get(i).getBrojSrednjih() + " |1 na broj velikih: " + listaVrstaSpremnika.get(i).getBrojVelikih()
                    + " |Nosivost: " + listaVrstaSpremnika.get(i).getNosivost());
        }
    }

    public static void ispisiUlice() {
        System.out.println("\n|Ulice:");
        for (int i = 0; i < listaUlica.size(); i++) {
            System.out.println("|Id: " + listaUlica.get(i).getId() + " |Naziv: " + listaUlica.get(i).getNaziv()
                    + " |Broj mjesta: " + listaUlica.get(i).getBrojMjesta() + " |Udio malih: " + listaUlica.get(i).getUdioMali()
                    + " |Udio srednji: " + listaUlica.get(i).getUdioSrednji() + " |Udio veliki: " + listaUlica.get(i).getUdioVeliki()
                    + " |Broj malih: " + listaUlica.get(i).getBrojMali() + " |Broj srednjih: " + listaUlica.get(i).getBrojSrednji()
                    + " |Broj velikih: " + listaUlica.get(i).getBrojVeliki());
        }
    }

    public static void ispisiVozila() {
        System.out.println("\n|Vozila:");
        for (int i = 0; i < listaVozila.size(); i++) {
            System.out.println("|Id: " + listaVozila.get(i).getId() + " |Naziv: " + listaVozila.get(i).getNaziv()
                    + " |Vrsta: " + listaVozila.get(i).getVrsta() + " |Nosivost: " + listaVozila.get(i).getNosivost()
                    + " |Vozači: " + listaVozila.get(i).getVozaci());
        }
    }

    public static void ispisiKorisnike() {
        System.out.println("\n|Korisnici:");
        for (int i = 0; i < listaKorisnika.size(); i++) {
            System.out.println("|Id: " + listaKorisnika.get(i).getId() + " |Ulica: " + listaKorisnika.get(i).getUlica()
                    + " |Kategorija: " + listaKorisnika.get(i).getKategorija() + " |Staklo: " + listaKorisnika.get(i).getKolicinaStaklo() + "kg"
                    + " |Papir: " + listaKorisnika.get(i).getKolicinaPapir() + "kg" + " |Metal: " + listaKorisnika.get(i).getKolicinaMetal() + "kg"
                    + " |Bio: " + listaKorisnika.get(i).getKolicinaBio() + "kg" + " |Mješano: " + listaKorisnika.get(i).getKolicinaMjesano() + "kg");
        }
    }

    public static void ispisiSpremnike() {
        System.out.println("\n|Spremnici:");
        for (int i = 0; i < listaSpremnika.size(); i++) {
            System.out.println("|Id: " + listaSpremnika.get(i).getId() + " |Vrsta spremnika: " + listaSpremnika.get(i).getVrstaSpremnik()
                    + " |Ulica: " + listaSpremnika.get(i).getUlica() + " |Vrsta korisnika: " + listaSpremnika.get(i).getVrstaKorisnik());
        }
    }
}
