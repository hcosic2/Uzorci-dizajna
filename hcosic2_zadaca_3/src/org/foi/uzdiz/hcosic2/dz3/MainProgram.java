/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.hcosic2.dz3.ANSIVT100.IspisAnsiVt100;
import org.foi.uzdiz.hcosic2.dz3.kontroler.Kontroler;
import org.foi.uzdiz.hcosic2.dz3.model.Model;
import org.foi.uzdiz.hcosic2.dz3.state.vozaci.Bolovanje;
import org.foi.uzdiz.hcosic2.dz3.state.vozaci.Godisnji;
import org.foi.uzdiz.hcosic2.dz3.state.vozaci.Novi;
import org.foi.uzdiz.hcosic2.dz3.state.vozaci.Otkaz;
import org.foi.uzdiz.hcosic2.dz3.state.vozaci.VozacStanje;
import org.foi.uzdiz.hcosic2.dz3.state.vozaci.Vozaci;
import org.foi.uzdiz.hcosic2.dz3.state.vozaci.Vozi;
import org.foi.uzdiz.hcosic2.dz3.state.vozila.Kontrola;
import org.foi.uzdiz.hcosic2.dz3.state.vozila.Kvar;
import org.foi.uzdiz.hcosic2.dz3.state.vozila.PrikupljaOtpad;
import org.foi.uzdiz.hcosic2.dz3.state.vozila.Vozilo;
import org.foi.uzdiz.hcosic2.dz3.view.Ispis;
import org.foi.uzdiz.hcosic2.dz3.view.Pogled;
import org.foi.uzdiz.hcosic2.dz3.view.Unos;

/**
 *
 * @author Hrvoje
 */
public class MainProgram {

    private static List<String> listaPodatakaSpremnici = new ArrayList();
    private static List<String> listaPodatakaUlice = new ArrayList();
    private static List<String> listaPodatakaVozila = new ArrayList();
    private static List<String> listaPodatakaPodrucja = new ArrayList();
    private static List<String> listaPodatakaDispecer = new ArrayList();
    private static List<VrstaSpremnik> listaVrstaSpremnika = new ArrayList();
    private static List<Ulica> listaUlica = new ArrayList();
    private static List<Korisnik> listaKorisnika = new ArrayList();
    private static List<Spremnik> listaSpremnika = new ArrayList();
    private static List<Vozilo> listaVozila = new ArrayList();
    private static List<KompozicijaPodrucja> listaPodrucja = new ArrayList();
    private static List<KomandaVozila> listaKomandi = new ArrayList();
    public static List<String> listaIspisPodataka = new ArrayList();
    private static List<Vozaci> listaVozaca = new ArrayList<>();
    private static List<KompozicijaPodrucja> listaIshodistaSustava = new ArrayList();
    private static Matcher matcher;
    private static Matcher matcher2;
    private static DohvatiParametre parametri;
    private static int decimale;
    private static int sjemeGeneratora;
    private static Random randomBroj = new Random(sjemeGeneratora);
    private static int ciklus = 1;
    private static int maxBrojCiklusa = 0;
    private static int brojCiklusa = 1;
    private static int brojRadnihCiklusa = 0;
    private static int brojRedovaGore = 0;
    private static int brojRedovaDolje = 0;
    private static Unos unos;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        String podaci = args[0];
        parametri = DohvatiParametre.getInstance(podaci);
        parametri.preuzmiParametre(podaci);

        if (args.length == 5) {
            if (args[1].equals("--brg")) {
                brojRedovaGore = Integer.parseInt(args[2]);
            }
            if (args[3].equals("--brd")) {
                brojRedovaDolje = Integer.parseInt(args[4]);
            }
            if (args[1].equals("--brd")) {
                brojRedovaDolje = Integer.parseInt(args[2]);
            }
            if (args[3].equals("--brg")) {
                brojRedovaGore = Integer.parseInt(args[4]);
            }
        }

        sjemeGeneratora = Integer.parseInt(parametri.dajParametar("sjemeGeneratora"));
        String datotekaSpremnici = parametri.dajParametar("spremnici");
        String datotekaUlice = parametri.dajParametar("ulice");
        String datotekaVozila = parametri.dajParametar("vozila");
        String datotekaPodrucja = parametri.dajParametar("područja");
        String datotekaDispecer = parametri.dajParametar("dispečer");
        String datotekaIzlaz = parametri.dajParametar("izlaz");
        int ispis = Integer.parseInt(parametri.dajParametar("ispis"));
        brojRadnihCiklusa = Integer.parseInt(parametri.dajParametar("brojRadnihCiklusaZaOdvoz"));
        decimale = Integer.parseInt(parametri.dajParametar("brojDecimala"));
        listaPodatakaSpremnici = dohvatiPodatkeSpremnici(datotekaSpremnici);
        //kreirajVrsteSpremnika();
        listaPodatakaUlice = dohvatiPodatkeUlice(datotekaUlice);
        listaPodatakaPodrucja = dohvatiPodatkePodrucja(datotekaPodrucja);
        listaPodatakaDispecer = dohvatiPodatkeDispecera(datotekaDispecer);
        //kreirajUlice();
        //kreirajKorisnike();
        //rasporediSpremnike();
        listaPodatakaVozila = dohvatiPodatkeVozila(datotekaVozila);
        //kreirajVozila();
        //kreirajPodrucja();
        //kreirajKomandeDispecera();

        Kontroler.getInstanca().setModel(new Model());

        Kontroler.getInstanca().getModel().dohvatiPodatke();
        Kontroler.getInstanca().getModel().dodijeliKorisnkuSpremnike();
        Kontroler.getInstanca().getModel().zbrojiSmecePoUlicamaIPorducijima();
        Kontroler.getInstanca().getModel().odrediIshodisnaPodrucja();

        if (ispis == 0) {
            ispisiSpremnike();
            ispisiSmeceUlice();
        }
        ispisSmecePodrucja();
        ispisiKorisnike();

        Kontroler.getInstanca().getModel().obradiKomandeDispecera();

        //dodijeliKorisnikuSpreminke();
        //napuniSpremnik();
        //smecePoUlicama();
        //smecePoPodrucjuUlice();
        //smecePoPodcrucju();
        //odrediIshodisnaPodrucja();
        //obradiKomandeDispecera();
        isprazniVozilaKrajDana();
        ispisStatistickePodatke();

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(datotekaIzlaz));
            for (int i = 0; i < listaIspisPodataka.size(); i++) {
                bw.write(listaIspisPodataka.get(i));
                bw.newLine();
            }

        } catch (IOException e) {
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
            }
        }

        unos = new Unos(brojRedovaGore, brojRedovaDolje);
        Ispis ispisi = new Ispis(brojRedovaGore, listaIspisPodataka);
        Pogled pogled = new Pogled(ispisi, unos);

        Kontroler.getInstanca().setPogled(pogled);
        Kontroler.getInstanca().ispisiEkranZaIspis();
        Kontroler.getInstanca().ispisiEkranZaUnos();

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

    public static List dohvatiPodatkeSpremnici(String nazivDatoteke) throws UnsupportedEncodingException, IOException {
        List<String> lista = new ArrayList();
        try {
            String glavnaPutanja = DohvatiParametre.getPutanja();
            String[] rastavi = glavnaPutanja.split("\\\\");
            String putanja = "";
            for (int i = 0; i < rastavi.length; i++) {
                rastavi[rastavi.length - 1] = nazivDatoteke;
                putanja += rastavi[i] + "/";
            }
            File file = new File(putanja.substring(0, putanja.length() - 1));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF8"));
            String redTeksta;
            while ((redTeksta = in.readLine()) != null) {
                lista.add(redTeksta);
            }
            in.close();
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
            } else if (!listaPodatakaSpremnici.get(i).equals("﻿naziv;vrsta: 0 - kanta, 1 - kontejner;1 na broj malih;1 na broj srednjih;1 na broj velikih;nosivost (kg)")) {
                String greska = ("Krivo unesena vrsta spremnika!");
                listaIspisPodataka.add(greska);
            }

        }
    }

    public static List dohvatiPodatkeUlice(String nazivDatoteke) throws IOException {
        List<String> lista = new ArrayList();
        try {
            String glavnaPutanja = DohvatiParametre.getPutanja();
            String[] rastavi = glavnaPutanja.split("\\\\");
            String putanja = "";
            for (int i = 0; i < rastavi.length; i++) {
                rastavi[rastavi.length - 1] = nazivDatoteke;
                putanja += rastavi[i] + "/";
            }
            File file = new File(putanja.substring(0, putanja.length() - 1));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF8"));
            String redTeksta;
            while ((redTeksta = in.readLine()) != null) {
                lista.add(redTeksta);
            }
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public static void kreirajUlice() {
        String regex = "(u+[0-9]*);(.*);([0-9]*);([0-9]*);([0-9]*);([0-9]*)$";
        int id = 1;
        for (int i = 0; i < listaPodatakaUlice.size(); i++) {
            Pattern uzorak = Pattern.compile(regex);
            matcher2 = uzorak.matcher(listaPodatakaUlice.get(i));
            if (matcher2.matches()) {
                String zapis = listaPodatakaUlice.get(i);
                String[] dijeloviZapisa = zapis.split(";");
                Ulica ulica = new Ulica();
                ulica.setId(id++);
                ulica.setOznakaUlice(dijeloviZapisa[0]);
                ulica.setNaziv(dijeloviZapisa[1]);
                ulica.setBrojMjesta(Integer.parseInt(dijeloviZapisa[2]));
                ulica.setUdioMali(Integer.parseInt(dijeloviZapisa[3]));
                ulica.setUdioSrednji(Integer.parseInt(dijeloviZapisa[4]));
                ulica.setUdioVeliki(Integer.parseInt(dijeloviZapisa[5]));
                float brojMali = (float) (Integer.parseInt(dijeloviZapisa[2])) / 100 * (Integer.parseInt(dijeloviZapisa[3]));
                float brojSrednji = (float) (Integer.parseInt(dijeloviZapisa[2])) / 100 * (Integer.parseInt(dijeloviZapisa[4]));
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
                ulica.setBrojVeliki(Integer.parseInt(dijeloviZapisa[2]) - ((int) brojMali + (int) brojSrednji));
                listaUlica.add(ulica);
            } else if (!listaPodatakaUlice.get(i).equals("﻿id;naziv;broj mjesta; udio (%) mali;udio (%) srednji;udio (%) veliki")) {
                String greska = ("Krivo unesena ulica!");
                listaIspisPodataka.add(greska);
            }
        }
        listaIspisPodataka.add("Ulice kreirane!");
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
                    int gornjaGranicaMjesano = Integer.parseInt(parametri.dajParametar("maliMješano"));
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
                    int gornjaGranicaMjesano = Integer.parseInt(parametri.dajParametar("srednjiMješano"));
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
                    int gornjaGranicaMjesano = Integer.parseInt(parametri.dajParametar("velikiMješano"));
                    korisnik.setKolicinaStaklo(randomFloat((float) gornjaGranicaStaklo / 100 * velikiMin, (float) gornjaGranicaStaklo, sjemeGeneratora, decimale));
                    korisnik.setKolicinaPapir(randomFloat((float) gornjaGranicaPapir / 100 * velikiMin, (float) gornjaGranicaPapir, sjemeGeneratora, decimale));
                    korisnik.setKolicinaMetal(randomFloat((float) gornjaGranicaMetal / 100 * velikiMin, (float) gornjaGranicaMetal, sjemeGeneratora, decimale));
                    korisnik.setKolicinaBio(randomFloat((float) gornjaGranicaBio / 100 * velikiMin, (float) gornjaGranicaBio, sjemeGeneratora, decimale));
                    korisnik.setKolicinaMjesano(randomFloat((float) gornjaGranicaMjesano / 100 * velikiMin, (float) gornjaGranicaMjesano, sjemeGeneratora, decimale));
                }
                listaKorisnika.add(korisnik);
            }
        }
        listaIspisPodataka.add("Korisnici kreirani!");
    }

    public static void smecePoPodrucjuUlice() {
        for (int p = 0; p < listaPodrucja.size(); p++) {
            Float staklo = 0f;
            Float papir = 0f;
            Float metal = 0f;
            Float bio = 0f;
            Float mjesano = 0f;
            if (listaPodrucja.get(p).getListaPodpodrucjaPodrucja().isEmpty()) {
                for (int up = 0; up < listaPodrucja.get(p).getListaPodpodrucjaUlice().size(); up++) {
                    for (int u = 0; u < listaUlica.size(); u++) {
                        if (listaPodrucja.get(p).getListaPodpodrucjaUlice().get(up).equals(listaUlica.get(u).getOznakaUlice())) {//ako oznaka iz liset ulica odgovara oznaci ulice
                            staklo += listaUlica.get(u).getUkupnoStaklo();
                            papir += listaUlica.get(u).getUkupnoPapir();
                            bio += listaUlica.get(u).getUkupnoBio();
                            metal += listaUlica.get(u).getUkupnoMetal();
                            mjesano += listaUlica.get(u).getUkupnoMjesano();
                        }
                    }
                }
            }
            listaPodrucja.get(p).setUkupnoStaklo(staklo);
            listaPodrucja.get(p).setUkupnoPapir(papir);
            listaPodrucja.get(p).setUkupnoBio(bio);
            listaPodrucja.get(p).setUkupnoMetal(metal);
            listaPodrucja.get(p).setUkupnoMjesano(mjesano);
        }
    }

    public static void smecePoPodcrucju() {
        for (int p = listaPodrucja.size() - 1; p >= 0; p--) {
            Float staklo = 0f;
            Float papir = 0f;
            Float metal = 0f;
            Float bio = 0f;
            Float mjesano = 0f;
            if (listaPodrucja.get(p).getListaPodpodrucjaUlice().isEmpty()) {
                for (int lp = 0; lp < listaPodrucja.get(p).getListaPodpodrucjaPodrucja().size(); lp++) {
                    for (int i = 0; i < listaPodrucja.size(); i++) {
                        if (listaPodrucja.get(i).getOznakaPodrucja().equals(listaPodrucja.get(p).getListaPodpodrucjaPodrucja().get(lp))) {
                            staklo += listaPodrucja.get(i).getUkupnoStaklo();
                            papir += listaPodrucja.get(i).getUkupnoPapir();
                            bio += listaPodrucja.get(i).getUkupnoBio();
                            metal += listaPodrucja.get(i).getUkupnoMetal();
                            mjesano += listaPodrucja.get(i).getUkupnoMjesano();
                        }
                    }
                }
                listaPodrucja.get(p).setUkupnoStaklo(staklo);
                listaPodrucja.get(p).setUkupnoPapir(papir);
                listaPodrucja.get(p).setUkupnoBio(bio);
                listaPodrucja.get(p).setUkupnoMetal(metal);
                listaPodrucja.get(p).setUkupnoMjesano(mjesano);
            }
        }
        for (int i = 0; i < listaPodrucja.size(); i++) {
            if (listaPodrucja.get(i).ukupnoStaklo == 0 && listaPodrucja.get(i).ukupnoBio == 0
                    && listaPodrucja.get(i).ukupnoMjesano == 0) {
                smecePoPodcrucju();
            }
        }
    }

    public static void odrediIshodisnaPodrucja() {
        List<KompozicijaPodrucja> lista = new ArrayList();
        for (int lp = 0; lp < listaPodrucja.size(); lp++) {
            if (!listaPodrucja.get(lp).getListaPodrucja().isEmpty()) {
                for (int i = 0; i < listaPodrucja.get(lp).getListaPodrucja().size(); i++) {
                    lista.add(listaPodrucja.get(lp).getListaPodrucja().get(i));
                }
            }
        }
        for (int p = 0; p < listaPodrucja.size(); p++) {
            if (!lista.contains(listaPodrucja.get(p))) {
                listaIshodistaSustava.add(listaPodrucja.get(p));
            }
        }
        listaIspisPodataka.add("Ishodista sustava: ");
        for (KompozicijaPodrucja ishodiste : listaIshodistaSustava) {
            String ishodista = (ishodiste.getOznakaPodrucja() + "   " + ishodiste.getNazivPodrucja()
                    + "  podpodrucja: " + ishodiste.dijeloviPodrucja);
            listaIspisPodataka.add(ishodista);
        }
    }

    public static void smecePoUlicama() {
        for (int i = 0; i < listaUlica.size(); i++) {
            Float staklo = 0f;
            Float papir = 0f;
            Float metal = 0f;
            Float bio = 0f;
            Float mjesano = 0f;
            for (int e = 0; e < listaKorisnika.size(); e++) {
                if (listaUlica.get(i).getId() == listaKorisnika.get(e).getUlica()) {
                    staklo += listaKorisnika.get(e).getKolicinaStaklo();
                    papir += listaKorisnika.get(e).getKolicinaPapir();
                    metal += listaKorisnika.get(e).getKolicinaMetal();
                    bio += listaKorisnika.get(e).getKolicinaBio();
                    mjesano += listaKorisnika.get(e).getKolicinaMjesano();
                }
                listaUlica.get(i).setUkupnoStaklo(staklo);
                listaUlica.get(i).setUkupnoPapir(papir);
                listaUlica.get(i).setUkupnoMetal(metal);
                listaUlica.get(i).setUkupnoBio(bio);
                listaUlica.get(i).setUkupnoMjesano(mjesano);
            }
        }
    }

    public static void rasporediSpremnike() {
        int id = 1;
        List<Korisnik> maliKorisnici = new ArrayList();
        List<Korisnik> srednjiKorisnici = new ArrayList();
        List<Korisnik> velikiKorisnici = new ArrayList();

        for (int u = 0; u < listaUlica.size(); u++) {
            boolean prolaz = false;
            maliKorisnici.clear();
            srednjiKorisnici.clear();
            velikiKorisnici.clear();
            int broj = 0;
            for (int v = 0; v <= listaVrstaSpremnika.size(); v++) {
                int brojSpremnika = 0;
                if (broj == listaVrstaSpremnika.size()) {
                    broj--;
                }
                if (prolaz) {
                    int brojMalih = listaVrstaSpremnika.get(broj).getBrojMalih();
                    int brojSrednjih = listaVrstaSpremnika.get(broj).getBrojSrednjih();
                    int brojVelikih = listaVrstaSpremnika.get(broj).getBrojVelikih();

                    if (listaVrstaSpremnika.get(broj).getBrojMalih() != 0) {
                        int spremniciZaMale = maliKorisnici.size() / listaVrstaSpremnika.get(broj).getBrojMalih();
                        if (maliKorisnici.size() % listaVrstaSpremnika.get(broj).getBrojMalih() == 0) {
                            brojSpremnika += spremniciZaMale;
                            for (int a = 0; a < spremniciZaMale; a++) {
                                List<Korisnik> konacnaListaMali = new ArrayList();
                                if (!maliKorisnici.isEmpty() && maliKorisnici.size() >= listaVrstaSpremnika.get(broj).getBrojMalih()) {
                                    for (int e = maliKorisnici.size() - 1; e >= (maliKorisnici.size() - listaVrstaSpremnika.get(broj).getBrojMalih()); e--) {
                                        konacnaListaMali.add(maliKorisnici.get(e));
                                    }
                                    int brojMalihKorisnika = maliKorisnici.size();
                                    for (int i = brojMalihKorisnika - 1; i >= (brojMalihKorisnika - listaVrstaSpremnika.get(broj).getBrojMalih()); i--) {
                                        if (i == -1) {
                                            maliKorisnici.remove(0);
                                        }
                                        maliKorisnici.remove(i);
                                    }

                                    List<Korisnik> listaZaUpis = new ArrayList<>(konacnaListaMali);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "mali", listaZaUpis)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                    konacnaListaMali.clear();
                                } else if (maliKorisnici.size() < listaVrstaSpremnika.get(broj).getBrojMalih()) {
                                    List<Korisnik> listaZaUpis2 = new ArrayList<>(maliKorisnici);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "mali", listaZaUpis2)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                }
                            }
                        } else if (spremniciZaMale > 0) {
                            brojSpremnika += spremniciZaMale + 1;
                            for (int a = 0; a <= spremniciZaMale; a++) {
                                List<Korisnik> konacnaListaMali = new ArrayList();
                                if (!maliKorisnici.isEmpty() && maliKorisnici.size() >= listaVrstaSpremnika.get(broj).getBrojMalih()) {
                                    for (int e = maliKorisnici.size() - 1; e >= (maliKorisnici.size() - listaVrstaSpremnika.get(broj).getBrojMalih()); e--) {
                                        konacnaListaMali.add(maliKorisnici.get(e));
                                    }
                                    int brojMalihKorisnika = maliKorisnici.size();
                                    for (int i = brojMalihKorisnika - 1; i >= (brojMalihKorisnika - listaVrstaSpremnika.get(broj).getBrojMalih()); i--) {
                                        if (i == -1) {
                                            maliKorisnici.remove(0);
                                        }
                                        maliKorisnici.remove(i);
                                    }
                                    List<Korisnik> listaZaUpis = new ArrayList<>(konacnaListaMali);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "mali", listaZaUpis)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                    konacnaListaMali.clear();
                                } else if (maliKorisnici.size() < listaVrstaSpremnika.get(broj).getBrojMalih()) {
                                    List<Korisnik> listaZaUpis2 = new ArrayList<>(maliKorisnici);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "mali", listaZaUpis2)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                }
                            }
                        } else {
                            List<Korisnik> listaZaUpis2 = new ArrayList<>(maliKorisnici);
                            Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "mali", listaZaUpis2)
                                    .build();
                            listaSpremnika.add(spremnik);
                            brojSpremnika++;
                        }
                    }
                    if (listaVrstaSpremnika.get(broj).getBrojSrednjih() != 0) {
                        int spremniciZaSrednje = srednjiKorisnici.size() / listaVrstaSpremnika.get(broj).getBrojSrednjih();
                        if (srednjiKorisnici.size() % listaVrstaSpremnika.get(broj).getBrojSrednjih() == 0) {
                            brojSpremnika += spremniciZaSrednje;
                            for (int a = 0; a < spremniciZaSrednje; a++) {
                                List<Korisnik> konacnaListaSrednji = new ArrayList();
                                if (!srednjiKorisnici.isEmpty() && srednjiKorisnici.size() >= listaVrstaSpremnika.get(broj).getBrojSrednjih()) {
                                    for (int e = srednjiKorisnici.size() - 1; e >= (srednjiKorisnici.size() - listaVrstaSpremnika.get(broj).getBrojSrednjih()); e--) {
                                        konacnaListaSrednji.add(srednjiKorisnici.get(e));
                                    }
                                    int brojSrednjihKorisnika = srednjiKorisnici.size();
                                    for (int i = brojSrednjihKorisnika - 1; i >= (brojSrednjihKorisnika - listaVrstaSpremnika.get(broj).getBrojSrednjih()); i--) {
                                        if (i == -1) {
                                            srednjiKorisnici.remove(0);
                                        }
                                        srednjiKorisnici.remove(i);
                                    }

                                    List<Korisnik> listaZaUpis = new ArrayList<>(konacnaListaSrednji);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "srednji", listaZaUpis)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                    konacnaListaSrednji.clear();
                                } else if (srednjiKorisnici.size() < listaVrstaSpremnika.get(broj).getBrojSrednjih()) {
                                    List<Korisnik> listaZaUpis2 = new ArrayList<>(srednjiKorisnici);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "srednji", listaZaUpis2)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                }
                            }
                        } else if (spremniciZaSrednje > 0) {
                            brojSpremnika += spremniciZaSrednje + 1;
                            for (int a = 0; a <= spremniciZaSrednje; a++) {
                                List<Korisnik> konacnaListaSrednji = new ArrayList();
                                if (!srednjiKorisnici.isEmpty() && srednjiKorisnici.size() >= listaVrstaSpremnika.get(broj).getBrojSrednjih()) {
                                    for (int e = srednjiKorisnici.size() - 1; e >= (srednjiKorisnici.size() - listaVrstaSpremnika.get(broj).getBrojSrednjih()); e--) {
                                        konacnaListaSrednji.add(srednjiKorisnici.get(e));
                                    }
                                    int brojSrednjihKorisnika = srednjiKorisnici.size();
                                    for (int i = brojSrednjihKorisnika - 1; i >= (brojSrednjihKorisnika - listaVrstaSpremnika.get(broj).getBrojSrednjih()); i--) {
                                        if (i == -1) {
                                            srednjiKorisnici.remove(0);
                                        }
                                        srednjiKorisnici.remove(i);
                                    }
                                    List<Korisnik> listaZaUpis = new ArrayList<>(konacnaListaSrednji);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "srednji", listaZaUpis)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                    konacnaListaSrednji.clear();
                                } else if (srednjiKorisnici.size() < listaVrstaSpremnika.get(broj).getBrojSrednjih()) {
                                    List<Korisnik> listaZaUpis2 = new ArrayList<>(srednjiKorisnici);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "srednji", listaZaUpis2)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                }
                            }
                        } else {
                            List<Korisnik> listaZaUpis2 = new ArrayList<>(srednjiKorisnici);
                            Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "srednji", listaZaUpis2)
                                    .build();
                            listaSpremnika.add(spremnik);
                            brojSpremnika++;
                        }
                    }
                    if (listaVrstaSpremnika.get(broj).getBrojVelikih() != 0) {
                        int spremniciZaVelike = velikiKorisnici.size() / listaVrstaSpremnika.get(broj).getBrojVelikih();
                        if (velikiKorisnici.size() % listaVrstaSpremnika.get(broj).getBrojVelikih() == 0) {
                            brojSpremnika += spremniciZaVelike;
                            for (int a = 0; a < spremniciZaVelike; a++) {
                                List<Korisnik> konacnaListaVeliki = new ArrayList();
                                if (!velikiKorisnici.isEmpty() && velikiKorisnici.size() >= listaVrstaSpremnika.get(broj).getBrojVelikih()) {
                                    for (int e = velikiKorisnici.size() - 1; e >= (velikiKorisnici.size() - listaVrstaSpremnika.get(broj).getBrojVelikih()); e--) {
                                        konacnaListaVeliki.add(velikiKorisnici.get(e));
                                    }
                                    int brojVelikihKorisnika = velikiKorisnici.size();
                                    for (int i = brojVelikihKorisnika - 1; i >= (brojVelikihKorisnika - listaVrstaSpremnika.get(broj).getBrojVelikih()); i--) {
                                        if (i == -1) {
                                            velikiKorisnici.remove(0);
                                        }
                                        velikiKorisnici.remove(i);
                                    }

                                    List<Korisnik> listaZaUpis = new ArrayList<>(konacnaListaVeliki);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "veliki", listaZaUpis)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                    konacnaListaVeliki.clear();
                                } else if (velikiKorisnici.size() < listaVrstaSpremnika.get(broj).getBrojVelikih()) {
                                    List<Korisnik> listaZaUpis2 = new ArrayList<>(velikiKorisnici);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "veliki", listaZaUpis2)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                }
                            }
                        } else if (spremniciZaVelike > 0) {
                            brojSpremnika += spremniciZaVelike + 1;
                            for (int a = 0; a <= spremniciZaVelike; a++) {
                                List<Korisnik> konacnaListaVeliki = new ArrayList();
                                if (!velikiKorisnici.isEmpty() && velikiKorisnici.size() >= listaVrstaSpremnika.get(broj).getBrojVelikih()) {
                                    for (int e = velikiKorisnici.size() - 1; e >= (velikiKorisnici.size() - listaVrstaSpremnika.get(broj).getBrojVelikih()); e--) {
                                        konacnaListaVeliki.add(velikiKorisnici.get(e));
                                    }
                                    int brojVelikihKorisnika = velikiKorisnici.size();
                                    for (int i = brojVelikihKorisnika - 1; i >= (brojVelikihKorisnika - listaVrstaSpremnika.get(broj).getBrojVelikih()); i--) {
                                        if (i == -1) {
                                            velikiKorisnici.remove(0);
                                        }
                                        velikiKorisnici.remove(i);
                                    }
                                    List<Korisnik> listaZaUpis = new ArrayList<>(konacnaListaVeliki);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "veliki", listaZaUpis)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                    konacnaListaVeliki.clear();
                                } else if (velikiKorisnici.size() < listaVrstaSpremnika.get(broj).getBrojVelikih()) {
                                    List<Korisnik> listaZaUpis2 = new ArrayList<>(velikiKorisnici);
                                    Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "veliki", listaZaUpis2)
                                            .build();
                                    listaSpremnika.add(spremnik);
                                }
                            }
                        } else {
                            List<Korisnik> listaZaUpis2 = new ArrayList<>(velikiKorisnici);
                            Spremnik spremnik = new Spremnik.SpremnikBuilder(id++, listaVrstaSpremnika.get(broj).getNaziv(), listaUlica.get(u).getId(), "veliki", listaZaUpis2)
                                    .build();
                            listaSpremnika.add(spremnik);
                            brojSpremnika++;
                        }
                    }

                    maliKorisnici.clear();
                    srednjiKorisnici.clear();
                    velikiKorisnici.clear();
                    broj++;
                }

                for (int k = 0; k < listaKorisnika.size(); k++) {
                    if (listaUlica.get(u).getId() == listaKorisnika.get(k).getUlica()) {
                        if (listaKorisnika.get(k).getKategorija().equals("mali")) {
                            maliKorisnici.add(listaKorisnika.get(k));
                        } else if (listaKorisnika.get(k).getKategorija().equals("srednji")) {
                            srednjiKorisnici.add(listaKorisnika.get(k));
                        } else if (listaKorisnika.get(k).getKategorija().equals("veliki")) {
                            velikiKorisnici.add(listaKorisnika.get(k));
                        }
                        prolaz = true;
                    }
                }
            }
        }
        listaIspisPodataka.add("Spremnici kreirani i dodijeljeni korisnicima!");
    }

    public static List dohvatiPodatkeVozila(String nazivDatoteke) throws UnsupportedEncodingException, IOException {
        List<String> lista = new ArrayList();
        try {
            String glavnaPutanja = DohvatiParametre.getPutanja();
            String[] rastavi = glavnaPutanja.split("\\\\");
            String putanja = "";
            for (int i = 0; i < rastavi.length; i++) {
                rastavi[rastavi.length - 1] = nazivDatoteke;
                putanja += rastavi[i] + "/";
            }
            File file = new File(putanja.substring(0, putanja.length() - 1));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF8"));
            String redTeksta;
            while ((redTeksta = in.readLine()) != null) {
                lista.add(redTeksta);
            }
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public static void kreirajVozilaIVozace() {
        String regex = "(v+[0-9]*);([^\\ž]+);([0-1]);([0-4]);([0-9]*);(.*)$";
        int id = 1;
        int idVozac = 1;
        for (int i = 0; i < listaPodatakaVozila.size(); i++) {
            Pattern uzorak = Pattern.compile(regex);
            Matcher matcher = uzorak.matcher(listaPodatakaVozila.get(i));
            if (matcher.matches()) {
                String zapis = listaPodatakaVozila.get(i);
                String[] dijeloviZapisa = zapis.split(";");
                Vozilo vozilo = new Vozilo.VoziloBuilder(id++, dijeloviZapisa[1], Integer.parseInt(dijeloviZapisa[2]),
                        Integer.parseInt(dijeloviZapisa[3]), Integer.parseInt(dijeloviZapisa[4]), dijeloviZapisa[5], dijeloviZapisa[0])
                        .build();

                String[] vozaci = dijeloviZapisa[5].split(",");
                for (int v = 0; v < vozaci.length; v++) {
                    Vozaci vozac = new Vozaci();
                    vozac.id = idVozac++;
                    vozac.ime = vozaci[v].trim();
                    vozilo.listaVozaca.add(vozac);
                    listaVozaca.add(vozac);
                }
                listaVozila.add(vozilo);
            } else if (!listaPodatakaVozila.get(i).equals("﻿id;naziv;tip: 0 - dizel, 1 - električni;vrsta: 0 - staklo, 1 - papir, 2 - metal, 3 - bio, 4 - mješano; nosivost (kg);vozači")) {
                String greska = ("Krivo uneseo vozilo!");
                listaIspisPodataka.add(greska);
            }
        }
        Vozila vozila = new VozilaProxy("dispecer");
        vozila.odrediVrstuOtpada(listaVozila);
        listaIspisPodataka.add("Vozila kreirana!");
    }

    public static void dajStanjeVozaca() {
        List<VozacStanje> listaStanjaVozaca = new ArrayList<>();
        String valjak = "";
        for (int i = 0; i < listaVozaca.size(); i++) {
            listaVozaca.get(i).otkaz();
            listaVozaca.get(i).novi();
            listaStanjaVozaca.add(listaVozaca.get(i).getStanje());
        }
    }

    public static List dohvatiPodatkePodrucja(String nazivDatoteke) throws UnsupportedEncodingException, IOException {
        List<String> lista = new ArrayList();
        try {
            String glavnaPutanja = DohvatiParametre.getPutanja();
            String[] rastavi = glavnaPutanja.split("\\\\");
            String putanja = "";
            for (int i = 0; i < rastavi.length; i++) {
                rastavi[rastavi.length - 1] = nazivDatoteke;
                putanja += rastavi[i] + "/";
            }
            File file = new File(putanja.substring(0, putanja.length() - 1));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF8"));
            String redTeksta;
            while ((redTeksta = in.readLine()) != null) {
                lista.add(redTeksta);
            }
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public static void kreirajPodrucja() {
        String regex = "(p+[0-9]*);(.*);([^\\ž]+).*$";
        int id = 1;
        for (int i = 0; i < listaPodatakaPodrucja.size(); i++) {
            Pattern uzorak = Pattern.compile(regex);
            Matcher matcher = uzorak.matcher(listaPodatakaPodrucja.get(i));
            if (matcher.matches()) {
                String zapis = listaPodatakaPodrucja.get(i);
                String[] dijeloviZapisa = zapis.split(";");
                KompozicijaPodrucja podrucje = new KompozicijaPodrucja();
                podrucje.setId(id++);
                podrucje.setOznakaPodrucja(dijeloviZapisa[0]);
                podrucje.setNazivPodrucja(dijeloviZapisa[1]);
                String dijeloviPodrucja = dijeloviZapisa[2];
                podrucje.setDijeloviPodrucja(dijeloviPodrucja);
                if (dijeloviPodrucja.contains("p")) {
                    String[] podpodrucje = dijeloviPodrucja.split(",");
                    for (int p = 0; p < podpodrucje.length; p++) {
                        podrucje.listaPodpodrucjaPodrucja.add(podpodrucje[p]);
                    }
                } else if (dijeloviPodrucja.contains("u")) {
                    String[] ulice = dijeloviPodrucja.split(",");
                    for (int u = 0; u < ulice.length; u++) {
                        podrucje.listaPodpodrucjaUlice.add(ulice[u]);
                    }
                }
                listaPodrucja.add(podrucje);
            } else if (!listaPodatakaPodrucja.get(i).equals("﻿id;naziv;dijelovi")) {
                String greska = ("Krivo uneseno podrucje!");
                listaIspisPodataka.add(greska);
            }
        }
        listaIspisPodataka.add("Podrucja kreirana!");
        kreirajPodpodrucja();
    }

    public static void kreirajPodpodrucja() {
        for (int p = 0; p < listaPodrucja.size(); p++) {
            if (listaPodrucja.get(p).getDijeloviPodrucja().contains("p")) {
                for (int lp = 0; lp < listaPodrucja.get(p).getListaPodpodrucjaPodrucja().size(); lp++) {
                    for (int lpod = 0; lpod < listaPodrucja.size(); lpod++) {
                        if (listaPodrucja.get(lpod).getOznakaPodrucja().equals(listaPodrucja.get(p).getListaPodpodrucjaPodrucja().get(lp))) {
                            listaPodrucja.get(p).dodajPodpodrucja("p", listaPodrucja.get(lpod));
                            listaPodrucja.get(lpod).ispis();
                        }
                    }
                }
            } else if (listaPodrucja.get(p).getDijeloviPodrucja().contains("u")) {
                for (int u = 0; u < listaUlica.size(); u++) {
                    for (int lu = 0; lu < listaPodrucja.get(p).getListaPodpodrucjaUlice().size(); lu++) {
                        if (listaPodrucja.get(p).getListaPodpodrucjaUlice().get(lu).equals(listaUlica.get(u).getOznakaUlice())) {
                            Ulica ulica = listaUlica.get(u);
                            listaPodrucja.get(p).dodajPodpodrucja("u", ulica);
                            ulica.ispis();
                        }
                    }
                }
            }
        }
    }

    public static List dohvatiPodatkeDispecera(String nazivDatoteke) throws UnsupportedEncodingException, IOException {
        List<String> lista = new ArrayList();
        try {
            String glavnaPutanja = DohvatiParametre.getPutanja();
            String[] rastavi = glavnaPutanja.split("\\\\");
            String putanja = "";
            for (int i = 0; i < rastavi.length; i++) {
                rastavi[rastavi.length - 1] = nazivDatoteke;
                putanja += rastavi[i] + "/";
            }
            File file = new File(putanja.substring(0, putanja.length() - 1));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF8"));
            String redTeksta;
            while ((redTeksta = in.readLine()) != null) {
                lista.add(redTeksta);
            }
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public static void kreirajKomandeDispecera() {
        String regex = "(KRENI +[0-9]*|PRIPREMI|KVAR|KONTROLA|ISPRAZNI|STATUS|"
                + "GODIŠNJI ODMOR|BOLOVANJE|OTKAZ|NOVI|VOZAČI|IZLAZ);(.*)$";
        String regex2 = "(OBRADI|PREUZMI);(.*);(.*);";
        int id = 1;
        for (int i = 0; i < listaPodatakaDispecer.size(); i++) {
            Pattern uzorak = Pattern.compile(regex);
            Matcher matcher = uzorak.matcher(listaPodatakaDispecer.get(i));
            Pattern uzorak2 = Pattern.compile(regex2);
            Matcher matcher2 = uzorak2.matcher(listaPodatakaDispecer.get(i));
            if (matcher.matches() || listaPodatakaDispecer.get(i).equals("KRENI;") || listaPodatakaDispecer.get(i).equals("STATUS;")) {
                String zapis = listaPodatakaDispecer.get(i);
                String[] dijeloviZapisa = zapis.split(";");
                KomandaVozila komanda = new KomandaVozila();
                komanda.id = id++;
                komanda.komanda = dijeloviZapisa[0];
                if (dijeloviZapisa.length > 1) {
                    komanda.lista1 = dijeloviZapisa[1];
                }
                KomandaVozila komandaVozila = (KomandaVozila) komanda.clone();
                listaKomandi.add(komandaVozila);
            } else if (matcher2.matches()) {
                String zapis = listaPodatakaDispecer.get(i);
                String[] dijeloviZapisa = zapis.split(";");
                KomandaVozila komanda = new KomandaVozila();
                komanda.id = id++;
                komanda.komanda = dijeloviZapisa[0];
                if (dijeloviZapisa.length > 1) {
                    komanda.lista1 = dijeloviZapisa[1];
                    komanda.lista2 = dijeloviZapisa[2];
                }
                KomandaVozila komandaVozila = (KomandaVozila) komanda.clone();
                listaKomandi.add(komandaVozila);
            } else if (!listaPodatakaDispecer.get(i).equals("komanda;lista;lista;")) {
                String greska = ("Krivo unesena komanda!");
                listaIspisPodataka.add(greska);
            }
        }
    }

    public static void ispisiVrsteSpremnika() {
        listaIspisPodataka.add("Vrste spremnika:");
        for (int i = 0; i < listaVrstaSpremnika.size(); i++) {
            String ispis = ("|Naziv: " + listaVrstaSpremnika.get(i).getNaziv() + " |Vrsta: " + listaVrstaSpremnika.get(i).getVrsta()
                    + " |1 na broj malih: " + listaVrstaSpremnika.get(i).getBrojMalih() + " |1 na broj srednjih: "
                    + listaVrstaSpremnika.get(i).getBrojSrednjih() + " |1 na broj velikih: " + listaVrstaSpremnika.get(i).getBrojVelikih()
                    + " |Nosivost: " + listaVrstaSpremnika.get(i).getNosivost());
            listaIspisPodataka.add(ispis);
        }
    }

    public static void ispisiUlice() {
        listaIspisPodataka.add("Ulice");
        for (int i = 0; i < listaUlica.size(); i++) {
            String ispis = ("|Id: " + listaUlica.get(i).getId() + " |Oznaka ulice: " + listaUlica.get(i).getOznakaUlice()
                    + " |Naziv: " + listaUlica.get(i).getNaziv()
                    + " |Broj mjesta: " + listaUlica.get(i).getBrojMjesta() + " |Udio malih: " + listaUlica.get(i).getUdioMali()
                    + " |Udio srednji: " + listaUlica.get(i).getUdioSrednji() + " |Udio veliki: " + listaUlica.get(i).getUdioVeliki()
                    + " |Broj malih: " + listaUlica.get(i).getBrojMali() + " |Broj srednjih: " + listaUlica.get(i).getBrojSrednji()
                    + " |Broj velikih: " + listaUlica.get(i).getBrojVeliki());
            listaIspisPodataka.add(ispis);
        }
    }

    public static void ispisiVozila() {
        listaIspisPodataka.add("Vozila");
        for (int i = 0; i < listaVozila.size(); i++) {
            String ispis = ("|Id: " + listaVozila.get(i).getId() + " |Oznaka vozila: " + listaVozila.get(i).getOznakaVozila() + " |Naziv: " + listaVozila.get(i).getNaziv()
                    + " |Vrsta: " + listaVozila.get(i).getVrsta() + " |Nosivost: " + listaVozila.get(i).getNosivost()
                    + " |Vozači: " + listaVozila.get(i).getVozaci());
            listaIspisPodataka.add(ispis);
        }
    }

    public static void ispisiKorisnike() {
        listaIspisPodataka.add("Korisnici:");
        for (int i = 0; i < listaKorisnika.size(); i++) {
            String korisnik = ("|Id: " + listaKorisnika.get(i).getId() + " |Ulica: " + listaKorisnika.get(i).getUlica()
                    + " |Kategorija: " + listaKorisnika.get(i).getKategorija() + " |Staklo: " + listaKorisnika.get(i).getKolicinaStaklo() + "kg"
                    + " |Papir: " + listaKorisnika.get(i).getKolicinaPapir() + "kg" + " |Metal: " + listaKorisnika.get(i).getKolicinaMetal() + "kg"
                    + " |Bio: " + listaKorisnika.get(i).getKolicinaBio() + "kg" + " |Mješano: " + listaKorisnika.get(i).getKolicinaMjesano() + "kg");
            listaIspisPodataka.add(korisnik);
        }
    }

    public static void ispisiSpremnike() {
        listaIspisPodataka.add("Spremnici");
        for (int i = 0; i < listaSpremnika.size(); i++) {
            String ispis = ("|Id: " + listaSpremnika.get(i).getId() + " |Vrsta spremnika: " + listaSpremnika.get(i).getVrstaSpremnik()
                    + " |Ulica: " + listaSpremnika.get(i).getUlica() + " |Vrsta korisnika: " + listaSpremnika.get(i).getVrstaKorisnik()
                    + " |Ukupno smeca: " + listaSpremnika.get(i).getUkupnoSmeca() + "kg" + " |Korisnika: " + listaSpremnika.get(i).getKorisnici().size());
            listaIspisPodataka.add(ispis);
        }
    }

    public static void ispisSmecePodrucja() {
        String ispis = String.format("%-20s %-25s %-20s %-15s %-15s %-15s %-15s%n%n", "Oznaka podrucja", "Naziv Podrucja",
                "Ukupno stakla", "Ukupno papir", "Ukupno metal", "Ukupno bio", "Ukupno mjesano");
        listaIspisPodataka.add("_____________________");
        listaIspisPodataka.add("|Smece po podrucjima|");
        listaIspisPodataka.add("---------------------");
        listaIspisPodataka.add(ispis);

        for (int i = 0; i < listaPodrucja.size(); i++) {
            String ispis2 = String.format("%-20s %-25s %-20s %-15s %-15s %-15s %-15s%n", listaPodrucja.get(i).getOznakaPodrucja(), listaPodrucja.get(i).getNazivPodrucja(),
                    listaPodrucja.get(i).getUkupnoStaklo() + "kg", listaPodrucja.get(i).getUkupnoPapir() + "kg", listaPodrucja.get(i).getUkupnoMetal() + "kg",
                    listaPodrucja.get(i).getUkupnoBio() + "kg", listaPodrucja.get(i).getUkupnoMjesano() + "kg");
            listaIspisPodataka.add("-----------------------------------------------------------------------------------------------------------------------------------");
            listaIspisPodataka.add(ispis2);
            listaIspisPodataka.add("-----------------------------------------------------------------------------------------------------------------------------------");
            if (listaPodrucja.get(i).dijeloviPodrucja.contains("p")) {
                for (int pp = 0; pp < listaPodrucja.get(i).getListaPodrucja().size(); pp++) {
                    String ispis3 = String.format("%-20s %-25s %-20s %-15s %-15s %-15s %-15s%n", listaPodrucja.get(i).getListaPodrucja().get(pp).getOznakaPodrucja(),
                            listaPodrucja.get(i).getListaPodrucja().get(pp).getNazivPodrucja(), listaPodrucja.get(i).getListaPodrucja().get(pp).getUkupnoStaklo() + "kg",
                            listaPodrucja.get(i).getListaPodrucja().get(pp).getUkupnoPapir() + "kg", listaPodrucja.get(i).getListaPodrucja().get(pp).getUkupnoMetal() + "kg",
                            listaPodrucja.get(i).getListaPodrucja().get(pp).getUkupnoBio() + "kg", listaPodrucja.get(i).getListaPodrucja().get(pp).getUkupnoMjesano() + "kg");
                    listaIspisPodataka.add(ispis3);
                }
            } else if (listaPodrucja.get(i).dijeloviPodrucja.contains("u")) {
                for (int lu = 0; lu < listaPodrucja.get(i).getListaUlica().size(); lu++) {
                    String ispis4 = String.format("%-20s %-25s %-20s %-15s %-15s %-15s %-15s%n", listaPodrucja.get(i).getListaUlica().get(lu).getOznakaUlice(),
                            listaPodrucja.get(i).getListaUlica().get(lu).getNaziv(), listaPodrucja.get(i).getListaUlica().get(lu).getUkupnoStaklo() + "kg",
                            listaPodrucja.get(i).getListaUlica().get(lu).getUkupnoPapir() + "kg", listaPodrucja.get(i).getListaUlica().get(lu).getUkupnoMetal() + "kg",
                            listaPodrucja.get(i).getListaUlica().get(lu).getUkupnoBio() + "kg", listaPodrucja.get(i).getListaUlica().get(lu).getUkupnoMjesano() + "kg");
                    listaIspisPodataka.add(ispis4);
                }
            }
        }
    }

    public static void ispisiSmeceUlice() {
        listaIspisPodataka.add("Smece po ulicama: ");
        for (int i = 0; i < listaUlica.size(); i++) {
            String ispis = ("|Id: " + listaUlica.get(i).getId() + " |Naziv: " + listaUlica.get(i).getNaziv()
                    + " |Ukupno stakla: " + listaUlica.get(i).getUkupnoStaklo() + "kg"
                    + " |Ukupno papira: " + listaUlica.get(i).getUkupnoPapir() + "kg"
                    + " |Ukupno metala: " + listaUlica.get(i).getUkupnoMetal() + "kg"
                    + " |Ukupno bio: " + listaUlica.get(i).getUkupnoBio() + "kg"
                    + " |Ukupno mjesano: " + listaUlica.get(i).getUkupnoMjesano() + "kg");
            listaIspisPodataka.add(ispis);
        }
    }

    public static void dodijeliKorisnikuSpreminke() {
        for (int s = 0; s < listaSpremnika.size(); s++) {
            for (int k = 0; k < listaKorisnika.size(); k++) {
                if (listaSpremnika.get(s).getKorisnici().contains(listaKorisnika.get(k))
                        && listaSpremnika.get(s).getVrstaSpremnik().equals("staklo")) {
                    listaKorisnika.get(k).setSpremnikStaklo(listaSpremnika.get(s).getId());
                } else if (listaSpremnika.get(s).getKorisnici().contains(listaKorisnika.get(k))
                        && listaSpremnika.get(s).getVrstaSpremnik().equals("papir")) {
                    listaKorisnika.get(k).setSpremnikPapir(listaSpremnika.get(s).getId());
                } else if (listaSpremnika.get(s).getKorisnici().contains(listaKorisnika.get(k))
                        && listaSpremnika.get(s).getVrstaSpremnik().equals("metal")) {
                    listaKorisnika.get(k).setSpremnikMetal(listaSpremnika.get(s).getId());
                } else if (listaSpremnika.get(s).getKorisnici().contains(listaKorisnika.get(k))
                        && listaSpremnika.get(s).getVrstaSpremnik().equals("bio")) {
                    listaKorisnika.get(k).setSpremnikBio(listaSpremnika.get(s).getId());
                } else if (listaSpremnika.get(s).getKorisnici().contains(listaKorisnika.get(k))
                        && listaSpremnika.get(s).getVrstaSpremnik().equals("mješano")) {
                    listaKorisnika.get(k).setSpremnikMjesano(listaSpremnika.get(s).getId());
                }
            }
        }
    }

    public static void napuniSpremnik() {
        for (int s = 0; s < listaSpremnika.size(); s++) {
            Float ukupnoSmece = 0f;
            for (int ks = 0; ks < listaSpremnika.get(s).getKorisnici().size(); ks++) {
                if (listaSpremnika.get(s).getVrstaSpremnik().equals("staklo")) {
                    ukupnoSmece += listaSpremnika.get(s).getKorisnici().get(ks).getKolicinaStaklo();
                    for (int v = 0; v < listaVrstaSpremnika.size(); v++) {
                        if (listaSpremnika.get(s).getVrstaSpremnik().equals(listaVrstaSpremnika.get(v).getNaziv())) {
                            if (ukupnoSmece > listaVrstaSpremnika.get(v).getNosivost()) {
                                ukupnoSmece = (float) listaVrstaSpremnika.get(v).getNosivost();
                                String obavijest = ("Staklo spremnik " + listaSpremnika.get(s).getId() + " je pun");
                                listaIspisPodataka.add(obavijest);
                            }
                        }
                    }
                } else if (listaSpremnika.get(s).getVrstaSpremnik().equals("papir")) {
                    ukupnoSmece += listaSpremnika.get(s).getKorisnici().get(ks).getKolicinaPapir();
                    for (int v = 0; v < listaVrstaSpremnika.size(); v++) {
                        if (listaSpremnika.get(s).getVrstaSpremnik().equals(listaVrstaSpremnika.get(v).getNaziv())) {
                            if (ukupnoSmece > listaVrstaSpremnika.get(v).getNosivost()) {
                                ukupnoSmece = (float) listaVrstaSpremnika.get(v).getNosivost();
                                String obavijest = ("Papir spremnik " + listaSpremnika.get(s).getId() + " je pun");
                                listaIspisPodataka.add(obavijest);
                            }
                        }
                    }
                } else if (listaSpremnika.get(s).getVrstaSpremnik().equals("metal")) {
                    ukupnoSmece += listaSpremnika.get(s).getKorisnici().get(ks).getKolicinaMetal();
                    for (int v = 0; v < listaVrstaSpremnika.size(); v++) {
                        if (listaSpremnika.get(s).getVrstaSpremnik().equals(listaVrstaSpremnika.get(v).getNaziv())) {
                            if (ukupnoSmece > listaVrstaSpremnika.get(v).getNosivost()) {
                                ukupnoSmece = (float) listaVrstaSpremnika.get(v).getNosivost();
                                String obavijest = ("Metal spremnik" + listaSpremnika.get(s).getId() + " je pun");
                                listaIspisPodataka.add(obavijest);
                            }
                        }
                    }
                } else if (listaSpremnika.get(s).getVrstaSpremnik().equals("bio")) {
                    ukupnoSmece += listaSpremnika.get(s).getKorisnici().get(ks).getKolicinaBio();
                    for (int v = 0; v < listaVrstaSpremnika.size(); v++) {
                        if (listaSpremnika.get(s).getVrstaSpremnik().equals(listaVrstaSpremnika.get(v).getNaziv())) {
                            if (ukupnoSmece > listaVrstaSpremnika.get(v).getNosivost()) {
                                ukupnoSmece = (float) listaVrstaSpremnika.get(v).getNosivost();
                                String obavijest = ("Bio spremnik " + listaSpremnika.get(s).getId() + " je pun");
                                listaIspisPodataka.add(obavijest);
                            }
                        }
                    }
                } else if (listaSpremnika.get(s).getVrstaSpremnik().equals("mješano")) {
                    ukupnoSmece += listaSpremnika.get(s).getKorisnici().get(ks).getKolicinaMjesano();
                    for (int v = 0; v < listaVrstaSpremnika.size(); v++) {
                        if (listaSpremnika.get(s).getVrstaSpremnik().equals(listaVrstaSpremnika.get(v).getNaziv())
                                && listaSpremnika.get(s).getVrstaKorisnik().equals("mali") && listaVrstaSpremnika.get(v).getVrsta() == 0) {
                            if (ukupnoSmece > listaVrstaSpremnika.get(v).getNosivost()) {
                                ukupnoSmece = (float) listaVrstaSpremnika.get(v).getNosivost();
                                String obavijest = ("Mješani mali spremnik " + listaSpremnika.get(s).getId() + " je pun");
                                listaIspisPodataka.add(obavijest);
                            }
                        } else if (listaSpremnika.get(s).getVrstaSpremnik().equals(listaVrstaSpremnika.get(v).getNaziv())
                                && listaSpremnika.get(s).getVrstaKorisnik().equals("veliki") && listaVrstaSpremnika.get(v).getVrsta() == 1) {
                            if (ukupnoSmece > listaVrstaSpremnika.get(v).getNosivost()) {
                                ukupnoSmece = (float) listaVrstaSpremnika.get(v).getNosivost();
                                String obavijest = ("Mješani veliki spremnik " + listaSpremnika.get(s).getId() + " je pun");
                                listaIspisPodataka.add(obavijest);
                            }
                        }
                    }
                }
            }
            listaSpremnika.get(s).setUkupnoSmeca(ukupnoSmece);
        }
    }

    public static void pokupiSmece() {
        for (int v = 0; v < listaVozila.size(); v++) {
            if (listaVozila.get(v).getStanje() instanceof PrikupljaOtpad) {
                for (int s = 0; s < listaSpremnika.size(); s++) {
                    if (listaVozila.get(v).getVrsta() == 0 && listaSpremnika.get(s).getVrstaSpremnik().equals("staklo")
                            && listaSpremnika.get(s).getUkupnoSmeca() != 0 && (maxBrojCiklusa >= brojCiklusa || maxBrojCiklusa == 0)) {
                        brojCiklusa++;
                        listaVozila.get(v).setPopunjenost(listaVozila.get(v).getPopunjenost() + listaSpremnika.get(s).getUkupnoSmeca());
                        listaVozila.get(v).setBrojPokupljenihSpremnika(listaVozila.get(v).getBrojPokupljenihSpremnika() + 1);
                        listaSpremnika.get(s).setUkupnoSmeca(0f);

                        if (listaVozila.get(v).getPopunjenost() > listaVozila.get(v).getNosivost()) {
                            listaSpremnika.get(s).setUkupnoSmeca(listaVozila.get(v).getPopunjenost() - (float) listaVozila.get(v).getNosivost());

                            listaVozila.get(v).setPopunjenost(listaVozila.get(v).getNosivost());
                            listaVozila.get(v).setKolicinaPokupljenogOtpada(listaVozila.get(v).getKolicinaPokupljenogOtpada()
                                    + listaVozila.get(v).getNosivost());
                            listaVozila.get(v).setBrojZbrinjavanja(listaVozila.get(v).getBrojZbrinjavanja() + 1);

                            String podaciVozilo = ("(Ciklus: " + ciklus++ + ") Pokupljen je spremnik " + listaSpremnika.get(s).getId()
                                    + " u ulici " + listaSpremnika.get(s).getUlica() + ", popunjenost vozila " + listaVozila.get(v).getNaziv()
                                    + " je " + listaVozila.get(v).getPopunjenost() + "/" + listaVozila.get(v).getNosivost() + "kg");
                            listaIspisPodataka.add(podaciVozilo);
                            listaVozila.get(v).setPopunjenost(0f);

                        } else {
                            String podaciVozilo = ("(Ciklus: " + ciklus++ + ") Pokupljen je spremnik " + listaSpremnika.get(s).getId() + " u ulici " + listaSpremnika.get(s).getUlica()
                                    + ", popunjenost vozila " + listaVozila.get(v).getNaziv() + " je " + listaVozila.get(v).getPopunjenost()
                                    + "/" + listaVozila.get(v).getNosivost() + "kg");
                            listaIspisPodataka.add(podaciVozilo);
                        }
                        break;
                    } else if (listaVozila.get(v).getVrsta() == 2 && listaSpremnika.get(s).getVrstaSpremnik().equals("metal")
                            && listaSpremnika.get(s).getUkupnoSmeca() != 0 && (maxBrojCiklusa >= brojCiklusa || maxBrojCiklusa == 0)) {
                        brojCiklusa++;
                        listaVozila.get(v).setPopunjenost(listaVozila.get(v).getPopunjenost() + listaSpremnika.get(s).getUkupnoSmeca());
                        listaVozila.get(v).setBrojPokupljenihSpremnika(listaVozila.get(v).getBrojPokupljenihSpremnika() + 1);
                        listaSpremnika.get(s).setUkupnoSmeca(0f);

                        if (listaVozila.get(v).getPopunjenost() > listaVozila.get(v).getNosivost()) {
                            listaSpremnika.get(s).setUkupnoSmeca(listaVozila.get(v).getPopunjenost() - (float) listaVozila.get(v).getNosivost());

                            listaVozila.get(v).setPopunjenost(listaVozila.get(v).getNosivost());
                            listaVozila.get(v).setKolicinaPokupljenogOtpada(listaVozila.get(v).getKolicinaPokupljenogOtpada()
                                    + listaVozila.get(v).getNosivost());
                            listaVozila.get(v).setBrojZbrinjavanja(listaVozila.get(v).getBrojZbrinjavanja() + 1);

                            String podaciVozilo = ("(Ciklus: " + ciklus++ + ") Pokupljen je spremnik " + listaSpremnika.get(s).getId()
                                    + " u ulici " + listaSpremnika.get(s).getUlica() + ", popunjenost vozila " + listaVozila.get(v).getNaziv()
                                    + " je " + listaVozila.get(v).getPopunjenost() + "/" + listaVozila.get(v).getNosivost() + "kg");
                            listaIspisPodataka.add(podaciVozilo);
                            listaVozila.get(v).setPopunjenost(0f);

                        } else {
                            String podaciVozilo = ("(Ciklus: " + ciklus++ + ") Pokupljen je spremnik " + listaSpremnika.get(s).getId() + " u ulici " + listaSpremnika.get(s).getUlica()
                                    + ", popunjenost vozila " + listaVozila.get(v).getNaziv() + " je " + listaVozila.get(v).getPopunjenost()
                                    + "/" + listaVozila.get(v).getNosivost() + "kg");
                            listaIspisPodataka.add(podaciVozilo);
                        }
                        break;
                    } else if (listaVozila.get(v).getVrsta() == 3 && listaSpremnika.get(s).getVrstaSpremnik().equals("bio")
                            && listaSpremnika.get(s).getUkupnoSmeca() != 0 && (maxBrojCiklusa >= brojCiklusa || maxBrojCiklusa == 0)) {
                        brojCiklusa++;
                        listaVozila.get(v).setPopunjenost(listaVozila.get(v).getPopunjenost() + listaSpremnika.get(s).getUkupnoSmeca());
                        listaVozila.get(v).setBrojPokupljenihSpremnika(listaVozila.get(v).getBrojPokupljenihSpremnika() + 1);
                        listaSpremnika.get(s).setUkupnoSmeca(0f);

                        if (listaVozila.get(v).getPopunjenost() > listaVozila.get(v).getNosivost()) {
                            listaSpremnika.get(s).setUkupnoSmeca(listaVozila.get(v).getPopunjenost() - (float) listaVozila.get(v).getNosivost());

                            listaVozila.get(v).setPopunjenost(listaVozila.get(v).getNosivost());
                            listaVozila.get(v).setKolicinaPokupljenogOtpada(listaVozila.get(v).getKolicinaPokupljenogOtpada()
                                    + listaVozila.get(v).getNosivost());
                            listaVozila.get(v).setBrojZbrinjavanja(listaVozila.get(v).getBrojZbrinjavanja() + 1);

                            String podaciVozilo = ("(Ciklus: " + ciklus++ + ") Pokupljen je spremnik " + listaSpremnika.get(s).getId()
                                    + " u ulici " + listaSpremnika.get(s).getUlica() + ", popunjenost vozila " + listaVozila.get(v).getNaziv()
                                    + " je " + listaVozila.get(v).getPopunjenost() + "/" + listaVozila.get(v).getNosivost() + "kg");
                            listaIspisPodataka.add(podaciVozilo);
                            listaVozila.get(v).setPopunjenost(0f);

                        } else {
                            String podaciVozilo = ("(Ciklus: " + ciklus++ + ") Pokupljen je spremnik " + listaSpremnika.get(s).getId() + " u ulici " + listaSpremnika.get(s).getUlica()
                                    + ", popunjenost vozila " + listaVozila.get(v).getNaziv() + " je " + listaVozila.get(v).getPopunjenost()
                                    + "/" + listaVozila.get(v).getNosivost() + "kg");
                            listaIspisPodataka.add(podaciVozilo);
                        }
                        break;
                    } else if (listaVozila.get(v).getVrsta() == 1 && listaSpremnika.get(s).getVrstaSpremnik().equals("papir")
                            && listaSpremnika.get(s).getUkupnoSmeca() != 0 && (maxBrojCiklusa >= brojCiklusa || maxBrojCiklusa == 0)) {
                        brojCiklusa++;
                        listaVozila.get(v).setPopunjenost(listaVozila.get(v).getPopunjenost() + listaSpremnika.get(s).getUkupnoSmeca());
                        listaVozila.get(v).setBrojPokupljenihSpremnika(listaVozila.get(v).getBrojPokupljenihSpremnika() + 1);
                        listaSpremnika.get(s).setUkupnoSmeca(0f);

                        if (listaVozila.get(v).getPopunjenost() > listaVozila.get(v).getNosivost()) {
                            listaSpremnika.get(s).setUkupnoSmeca(listaVozila.get(v).getPopunjenost() - (float) listaVozila.get(v).getNosivost());

                            listaVozila.get(v).setPopunjenost(listaVozila.get(v).getNosivost());
                            listaVozila.get(v).setKolicinaPokupljenogOtpada(listaVozila.get(v).getKolicinaPokupljenogOtpada()
                                    + listaVozila.get(v).getNosivost());
                            listaVozila.get(v).setBrojZbrinjavanja(listaVozila.get(v).getBrojZbrinjavanja() + 1);

                            String podaciVozilo = ("(Ciklus: " + ciklus++ + ") Pokupljen je spremnik " + listaSpremnika.get(s).getId()
                                    + " u ulici " + listaSpremnika.get(s).getUlica() + ", popunjenost vozila " + listaVozila.get(v).getNaziv()
                                    + " je " + listaVozila.get(v).getPopunjenost() + "/" + listaVozila.get(v).getNosivost() + "kg");
                            listaIspisPodataka.add(podaciVozilo);
                            listaVozila.get(v).setPopunjenost(0f);

                        } else {
                            String podaciVozilo = ("(Ciklus: " + ciklus++ + ") Pokupljen je spremnik " + listaSpremnika.get(s).getId() + " u ulici " + listaSpremnika.get(s).getUlica()
                                    + ", popunjenost vozila " + listaVozila.get(v).getNaziv() + " je " + listaVozila.get(v).getPopunjenost()
                                    + "/" + listaVozila.get(v).getNosivost() + "kg");
                            listaIspisPodataka.add(podaciVozilo);
                        }
                        break;
                    } else if (listaVozila.get(v).getVrsta() == 4 && listaSpremnika.get(s).getVrstaSpremnik().equals("mješano")
                            && listaSpremnika.get(s).getUkupnoSmeca() != 0 && (maxBrojCiklusa >= brojCiklusa || maxBrojCiklusa == 0)) {
                        brojCiklusa++;
                        listaVozila.get(v).setPopunjenost(listaVozila.get(v).getPopunjenost() + listaSpremnika.get(s).getUkupnoSmeca());
                        listaVozila.get(v).setBrojPokupljenihSpremnika(listaVozila.get(v).getBrojPokupljenihSpremnika() + 1);
                        listaSpremnika.get(s).setUkupnoSmeca(0f);

                        if (listaVozila.get(v).getPopunjenost() > listaVozila.get(v).getNosivost()) {
                            listaSpremnika.get(s).setUkupnoSmeca(listaVozila.get(v).getPopunjenost() - (float) listaVozila.get(v).getNosivost());

                            listaVozila.get(v).setPopunjenost(listaVozila.get(v).getNosivost());
                            listaVozila.get(v).setKolicinaPokupljenogOtpada(listaVozila.get(v).getKolicinaPokupljenogOtpada()
                                    + listaVozila.get(v).getNosivost());
                            listaVozila.get(v).setBrojZbrinjavanja(listaVozila.get(v).getBrojZbrinjavanja() + 1);

                            String podaciVozilo = ("(Ciklus: " + ciklus++ + ") Pokupljen je spremnik " + listaSpremnika.get(s).getId()
                                    + " u ulici " + listaSpremnika.get(s).getUlica() + ", popunjenost vozila " + listaVozila.get(v).getNaziv()
                                    + " je " + listaVozila.get(v).getPopunjenost() + "/" + listaVozila.get(v).getNosivost() + "kg");
                            listaIspisPodataka.add(podaciVozilo);
                            listaVozila.get(v).setPopunjenost(0f);

                        } else {
                            String podaciVozilo = ("(Ciklus: " + ciklus++ + ") Pokupljen je spremnik " + listaSpremnika.get(s).getId() + " u ulici " + listaSpremnika.get(s).getUlica()
                                    + ", popunjenost vozila " + listaVozila.get(v).getNaziv() + " je " + listaVozila.get(v).getPopunjenost()
                                    + "/" + listaVozila.get(v).getNosivost() + "kg");
                            listaIspisPodataka.add(podaciVozilo);
                        }
                        break;
                    }

                }
            }
        }
    }

    public static void pokupiSmeceStart() {
        if (maxBrojCiklusa != 0) {
            for (int i = 0; i < maxBrojCiklusa; i++) {
                pokupiSmece();
            }
        } else {
            for (int u = 0; u < listaUlica.size(); u++) {
                for (int s = 0; s < listaSpremnika.size(); s++) {
                    if (listaSpremnika.get(s).getUkupnoSmeca() != 0f) {
                        listaSpremnika.get(s).getId();
                        pokupiSmece();
                    }
                }
            }
        }
    }

    public static void obradiKomandeDispecera() {
        for (int k = 0; k < listaKomandi.size(); k++) {
            if (listaKomandi.get(k).getKomanda().equals("PRIPREMI")) {
                String vozila[] = listaKomandi.get(k).getLista1().split(",");
                for (int v = 0; v < vozila.length; v++) {
                    for (int vo = 0; vo < listaVozila.size(); vo++) {
                        if (vozila[v].equals(listaVozila.get(vo).getOznakaVozila()) && !(listaVozila.get(vo).getStanje() instanceof PrikupljaOtpad)
                                && !(listaVozila.get(vo).getStanje() instanceof Kvar)) {
                            listaVozila.get(vo).prikupljaOtpad();
                        }
                    }
                }
            } else if (listaKomandi.get(k).getKomanda().contains("KRENI")) {
                if (listaKomandi.get(k).getKomanda().equals("KRENI")) {
                    ciklus = 1;
                    maxBrojCiklusa = 0;
                    pokupiSmeceStart();
                    brojCiklusa = 1;
                } else {
                    ciklus = 1;
                    String[] komanda = listaKomandi.get(k).getKomanda().split(" ");
                    maxBrojCiklusa = Integer.parseInt(komanda[1]);
                    pokupiSmeceStart();
                    brojCiklusa = 1;
                }

            } else if (listaKomandi.get(k).getKomanda().equals("KVAR")) {
                String vozila[] = listaKomandi.get(k).getLista1().split(",");
                for (int v = 0; v < vozila.length; v++) {
                    for (int vo = 0; vo < listaVozila.size(); vo++) {
                        if (vozila[v].equals(listaVozila.get(vo).getOznakaVozila())) {
                            listaVozila.get(vo).kvar();
                        }
                    }
                }
            } else if (listaKomandi.get(k).getKomanda().equals("KONTROLA")) {
                String vozila[] = listaKomandi.get(k).getLista1().split(",");
                for (int v = 0; v < vozila.length; v++) {
                    for (int vo = 0; vo < listaVozila.size(); vo++) {
                        if (vozila[v].equals(listaVozila.get(vo).getOznakaVozila())) {
                            listaVozila.get(vo).kontrola();
                        }
                    }
                }
            } else if (listaKomandi.get(k).getKomanda().equals("ISPRAZNI")) {
                String vozila[] = listaKomandi.get(k).getLista1().split(",");
                for (int v = 0; v < vozila.length; v++) {
                    for (int vo = 0; vo < listaVozila.size(); vo++) {
                        if (vozila[v].equals(listaVozila.get(vo).getOznakaVozila())) {
                            listaVozila.get(vo).prazniSmece();
                            listaVozila.get(vo).setKolicinaPokupljenogOtpada(listaVozila.get(vo).getKolicinaPokupljenogOtpada()
                                    + listaVozila.get(vo).getPopunjenost());
                            listaVozila.get(vo).setBrojZbrinjavanja(listaVozila.get(vo).getBrojZbrinjavanja() + 1);
                            listaVozila.get(vo).setPopunjenost(0.f);
                            listaVozila.get(vo).prikupljaOtpad();
                        }
                    }
                    for (int vo = 0; vo < listaVozila.size(); vo++) {
                        if (vozila[v].equals(listaVozila.get(vo).getOznakaVozila())) {
                            listaVozila.get(vo).setPopunjenost(0.f);
                        }
                    }
                }
            } else if (listaKomandi.get(k).getKomanda().equals("STATUS")) {
                String ispis = String.format("%-20s %-25s %-20s %-15s%n", "Vozila", "Stanje",
                        "Vrsta otpada", "Popunjenost vozila");
                listaIspisPodataka.add(ispis);
                for (int v = 0; v < listaVozila.size(); v++) {
                    if (listaVozila.get(v).getStanje() instanceof PrikupljaOtpad) {
                        String ispis2 = String.format("%-20s %-25s %-20s %-15s", listaVozila.get(v).getNaziv(), "prikuplja otpad",
                                listaVozila.get(v).getNazivOtpada(), listaVozila.get(v).getPopunjenost() + "/" + listaVozila.get(v).getNosivost() + "kg");
                        listaIspisPodataka.add(ispis2);
                    } else if (listaVozila.get(v).getStanje() instanceof Kontrola) {
                        String ispis2 = String.format("%-20s %-25s %-20s %-15s", listaVozila.get(v).getNaziv(), "vozilo na kontroli",
                                listaVozila.get(v).getNazivOtpada(), listaVozila.get(v).getPopunjenost() + "/" + listaVozila.get(v).getNosivost() + "kg");
                        listaIspisPodataka.add(ispis2);
                    }
                }
            } else if (listaKomandi.get(k).getKomanda().equals("GODIŠNJI ODMOR")) {
                String[] vozaci = listaKomandi.get(k).getLista1().split(",");
                for (int i = 0; i < listaVozaca.size(); i++) {
                    for (int v = 0; v < vozaci.length; v++) {
                        if (listaVozaca.get(i).ime.equals(vozaci[v])) {
                            listaVozaca.get(i).godisnji();
                        }
                    }
                }
            } else if (listaKomandi.get(k).getKomanda().equals("BOLOVANJE")) {
                String[] vozaci = listaKomandi.get(k).getLista1().split(",");
                for (int i = 0; i < listaVozaca.size(); i++) {
                    for (int v = 0; v < vozaci.length; v++) {
                        if (listaVozaca.get(i).ime.equals(vozaci[v])) {
                            listaVozaca.get(i).bolovanje();
                        }
                    }
                }
            } else if (listaKomandi.get(k).getKomanda().equals("OTKAZ")) {
                String[] vozaci = listaKomandi.get(k).getLista1().split(",");
                for (int i = 0; i < listaVozaca.size(); i++) {
                    for (int v = 0; v < vozaci.length; v++) {
                        if (listaVozaca.get(i).ime.equals(vozaci[v])) {
                            listaVozaca.get(i).otkaz();
                        }
                    }
                }
                for (Vozilo vozilo : listaVozila) {
                    for (int g = 0; g < vozilo.listaVozaca.size(); g++) {
                        if (vozilo.listaVozaca.get(g).getStanje() instanceof Otkaz) {
                            vozilo.listaVozaca.remove(vozilo.listaVozaca.get(g));
                        }
                    }
                }
            } else if (listaKomandi.get(k).getKomanda().equals("PREUZMI")) {
                for (Vozilo vozilo : listaVozila) {
                    for (int v = 0; v < vozilo.listaVozaca.size(); v++) {
                        if (vozilo.listaVozaca.get(v).ime.equals(listaKomandi.get(k).lista1)) {
                            vozilo.listaVozaca.remove(vozilo.listaVozaca.get(v));
                        }
                    }
                }
                for (Vozilo vozilo : listaVozila) {
                    if (vozilo.oznakaVozila.equals(listaKomandi.get(k).lista2)) {
                        for (Vozaci vozac : listaVozaca) {
                            if (vozac.ime.equals(listaKomandi.get(k).lista1) && !(vozac.getStanje() instanceof Otkaz)) {
                                vozilo.listaVozaca.add(vozac);
                                vozac.vozi();
                            }
                        }
                    }
                }
            } else if (listaKomandi.get(k).getKomanda().equals("NOVI")) {
                String[] vozaci = listaKomandi.get(k).getLista1().split(",");
                int id = listaVozaca.size() + 1;
                for (int i = 0; i < vozaci.length; i++) {
                    Vozaci vozac = new Vozaci();
                    vozac.id = id++;
                    vozac.ime = vozaci[i];
                    vozac.novi();
                    listaVozaca.add(vozac);
                }
            } else if (listaKomandi.get(k).getKomanda().equals("VOZAČI")) {
                String stanje = "";
                String ispis = String.format("%-20s %-25s %15s%n", "|Id|", "|Vozač|",
                        "|Stanje|");
                listaIspisPodataka.add("--------------------------------------------------------------");
                listaIspisPodataka.add(ispis);
                listaIspisPodataka.add("--------------------------------------------------------------");
                for (int i = 0; i < listaVozaca.size(); i++) {
                    if (listaVozaca.get(i).getStanje() instanceof Vozi) {
                        stanje = "Vozi";
                    } else if (listaVozaca.get(i).getStanje() instanceof Novi) {
                        stanje = "Novi";
                    } else if (listaVozaca.get(i).getStanje() instanceof Godisnji) {
                        stanje = "Godisnji";
                    } else if (listaVozaca.get(i).getStanje() instanceof Otkaz) {
                        stanje = "Otkaz";
                    } else if (listaVozaca.get(i).getStanje() instanceof Bolovanje) {
                        stanje = "Bolovanje";
                    }
                    String ispis2 = String.format("%-20s %-25s %15s%n", "|" + listaVozaca.get(i).id + "|",
                            "|" + listaVozaca.get(i).ime + "|", "|" + stanje + "|");
                    listaIspisPodataka.add(ispis2);
                }
                listaIspisPodataka.add("--------------------------------------------------------------");
            } else if (listaKomandi.get(k).getKomanda().equals("IZLAZ")) {
                unos.radi = false;

            }
        }

    }

    public static void obradiUnesenuKomandu(String naredba) {
        String regex = "(KRENI +[0-9]*|PRIPREMI|KVAR|KONTROLA|ISPRAZNI|STATUS|"
                + "GODIŠNJI ODMOR|BOLOVANJE|OTKAZ|NOVI|VOZAČI|IZLAZ);(.*)$";
        String regex2 = "(OBRADI|PREUZMI);(.*);(.*);";
        Pattern uzorak = Pattern.compile(regex);
        Matcher matcher = uzorak.matcher(naredba);
        Pattern uzorak2 = Pattern.compile(regex2);
        Matcher matcher2 = uzorak2.matcher(naredba);
        if (naredba.equals("KRENI;")) {
            listaIspisPodataka.add("Rezultati unesene komande " + naredba);
            listaIspisPodataka.add("----------------------------------------------");
            ciklus = 1;
            maxBrojCiklusa = 0;
            pokupiSmeceStart();
            brojCiklusa = 1;
        }
        if (matcher.matches() || matcher2.matches()) {
            listaIspisPodataka.add("Rezultati unesene komande " + naredba);
            listaIspisPodataka.add("----------------------------------------------");
            if (naredba.contains("PRIPREMI")) {
                String vozila[] = matcher.group(2).split(",");
                for (int v = 0; v < vozila.length; v++) {
                    if (vozila[v].endsWith(";")) {
                        vozila[v] = vozila[v].substring(0, vozila[v].length() - 1);
                    }
                    for (int vo = 0; vo < listaVozila.size(); vo++) {
                        if (vozila[v].equals(listaVozila.get(vo).getOznakaVozila()) && !(listaVozila.get(vo).getStanje() instanceof PrikupljaOtpad)
                                && !(listaVozila.get(vo).getStanje() instanceof Kvar)) {
                            listaVozila.get(vo).prikupljaOtpad();
                        }
                    }
                }
            } else if (naredba.contains("KRENI")) {
                String[] komanda = matcher.group(1).split(" ");
                ciklus = 1;
                maxBrojCiklusa = Integer.parseInt(komanda[1]);
                pokupiSmeceStart();
                brojCiklusa = 1;
            } else if (naredba.contains("KVAR")) {
                String vozila[] = matcher.group(2).split(",");
                for (int v = 0; v < vozila.length; v++) {
                    if (vozila[v].endsWith(";")) {
                        vozila[v] = vozila[v].substring(0, vozila[v].length() - 1);
                    }
                    for (int vo = 0; vo < listaVozila.size(); vo++) {
                        if (vozila[v].equals(listaVozila.get(vo).getOznakaVozila())) {
                            listaVozila.get(vo).kvar();
                        }
                    }
                }
            } else if (naredba.contains("KONTROLA")) {
                String vozila[] = matcher.group(2).split(",");
                for (int v = 0; v < vozila.length; v++) {
                    if (vozila[v].endsWith(";")) {
                        vozila[v] = vozila[v].substring(0, vozila[v].length() - 1);
                    }
                    for (int vo = 0; vo < listaVozila.size(); vo++) {
                        if (vozila[v].equals(listaVozila.get(vo).getOznakaVozila())) {
                            listaVozila.get(vo).kontrola();
                        }
                    }
                }
            } else if (naredba.contains("ISPRAZNI")) {
                String vozila[] = matcher.group(2).split(",");
                for (int v = 0; v < vozila.length; v++) {
                    for (int vo = 0; vo < listaVozila.size(); vo++) {
                        if (vozila[v].endsWith(";")) {
                            vozila[v] = vozila[v].substring(0, vozila[v].length() - 1);
                        }
                        if (vozila[v].equals(listaVozila.get(vo).getOznakaVozila())) {
                            listaVozila.get(vo).prazniSmece();
                            listaVozila.get(vo).setKolicinaPokupljenogOtpada(listaVozila.get(vo).getKolicinaPokupljenogOtpada()
                                    + listaVozila.get(vo).getPopunjenost());
                            listaVozila.get(vo).setBrojZbrinjavanja(listaVozila.get(vo).getBrojZbrinjavanja() + 1);
                            listaVozila.get(vo).setPopunjenost(0.f);
                            listaVozila.get(vo).prikupljaOtpad();
                        }
                    }
                    for (int vo = 0; vo < listaVozila.size(); vo++) {
                        if (vozila[v].equals(listaVozila.get(vo).getOznakaVozila())) {
                            listaVozila.get(vo).setPopunjenost(0.f);
                        }
                    }
                }
            } else if (naredba.contains("STATUS")) {
                String ispis = String.format("%-20s %-25s %-20s %-15s%n", "Vozila", "Stanje",
                        "Vrsta otpada", "Popunjenost vozila");
                listaIspisPodataka.add(ispis);
                for (int v = 0; v < listaVozila.size(); v++) {
                    if (listaVozila.get(v).getStanje() instanceof PrikupljaOtpad) {
                        String ispis2 = String.format("%-20s %-25s %-20s %-15s", listaVozila.get(v).getNaziv(), "prikuplja otpad",
                                listaVozila.get(v).getNazivOtpada(), listaVozila.get(v).getPopunjenost() + "/" + listaVozila.get(v).getNosivost() + "kg");
                        listaIspisPodataka.add(ispis2);
                    } else if (listaVozila.get(v).getStanje() instanceof Kontrola) {
                        String ispis2 = String.format("%-20s %-25s %-20s %-15s", listaVozila.get(v).getNaziv(), "vozilo na kontroli",
                                listaVozila.get(v).getNazivOtpada(), listaVozila.get(v).getPopunjenost() + "/" + listaVozila.get(v).getNosivost() + "kg");
                        listaIspisPodataka.add(ispis2);
                    }
                }
            } else if (naredba.contains("GODIŠNJI ODMOR")) {
                String[] vozaci = matcher.group(2).split(",");
                for (int i = 0; i < listaVozaca.size(); i++) {
                    for (int v = 0; v < vozaci.length; v++) {
                        if (vozaci[v].endsWith(";")) {
                            vozaci[v] = vozaci[v].substring(0, vozaci[v].length() - 1);
                        }
                        if (listaVozaca.get(i).ime.equals(vozaci[v])) {
                            listaVozaca.get(i).godisnji();
                        }
                    }
                }
            } else if (naredba.contains("BOLOVANJE")) {
                String[] vozaci = matcher.group(2).split(",");
                for (int i = 0; i < listaVozaca.size(); i++) {
                    for (int v = 0; v < vozaci.length; v++) {
                        if (vozaci[v].endsWith(";")) {
                            vozaci[v] = vozaci[v].substring(0, vozaci[v].length() - 1);
                        }
                        if (listaVozaca.get(i).ime.equals(vozaci[v])) {
                            listaVozaca.get(i).bolovanje();
                        }
                    }
                }
            } else if (naredba.contains("OTKAZ")) {
                String[] vozaci = matcher.group(2).split(",");
                for (int i = 0; i < listaVozaca.size(); i++) {
                    for (int v = 0; v < vozaci.length; v++) {
                        if (vozaci[v].endsWith(";")) {
                            vozaci[v] = vozaci[v].substring(0, vozaci[v].length() - 1);
                        }
                        if (listaVozaca.get(i).ime.equals(vozaci[v])) {
                            listaVozaca.get(i).otkaz();
                        }
                    }
                }
                for (Vozilo vozilo : listaVozila) {
                    for (int g = 0; g < vozilo.listaVozaca.size(); g++) {
                        if (vozilo.listaVozaca.get(g).getStanje() instanceof Otkaz) {
                            vozilo.listaVozaca.remove(vozilo.listaVozaca.get(g));
                        }
                    }
                }
            } else if (naredba.contains("PREUZMI")) {
                for (Vozilo vozilo : listaVozila) {
                    for (int v = 0; v < vozilo.listaVozaca.size(); v++) {
                        if (vozilo.listaVozaca.get(v).ime.equals(matcher2.group(2))) {
                            vozilo.listaVozaca.remove(vozilo.listaVozaca.get(v));
                        }
                    }
                }
                for (Vozilo vozilo : listaVozila) {
                    if (vozilo.oznakaVozila.equals(matcher2.group(3))) {
                        for (Vozaci vozac : listaVozaca) {
                            if (vozac.ime.equals(matcher2.group(2)) && !(vozac.getStanje() instanceof Otkaz)) {
                                vozilo.listaVozaca.add(vozac);
                                vozac.vozi();
                            }
                        }
                    }
                }
            } else if (naredba.contains("NOVI")) {
                String[] vozaci = matcher.group(2).split(",");
                int id = listaVozaca.size() + 1;
                for (int i = 0; i < vozaci.length; i++) {
                    if (vozaci[i].endsWith(";")) {
                        vozaci[i] = vozaci[i].substring(0, vozaci[i].length() - 1);
                    }
                    Vozaci vozac = new Vozaci();
                    vozac.id = id++;
                    vozac.ime = vozaci[i];
                    vozac.novi();
                    listaVozaca.add(vozac);
                }
            } else if (naredba.contains("VOZAČI")) {
                String stanje = "";
                String ispis = String.format("%-20s %-25s %15s%n", "|Id|", "|Vozač|",
                        "|Stanje|");
                listaIspisPodataka.add("--------------------------------------------------------------");
                listaIspisPodataka.add(ispis);
                listaIspisPodataka.add("--------------------------------------------------------------");
                for (int i = 0; i < listaVozaca.size(); i++) {
                    if (listaVozaca.get(i).getStanje() instanceof Vozi) {
                        stanje = "Vozi";
                    } else if (listaVozaca.get(i).getStanje() instanceof Novi) {
                        stanje = "Novi";
                    } else if (listaVozaca.get(i).getStanje() instanceof Godisnji) {
                        stanje = "Godisnji";
                    } else if (listaVozaca.get(i).getStanje() instanceof Otkaz) {
                        stanje = "Otkaz";
                    } else if (listaVozaca.get(i).getStanje() instanceof Bolovanje) {
                        stanje = "Bolovanje";
                    }
                    String ispis2 = String.format("%-20s %-25s %15s%n", "|" + listaVozaca.get(i).id + "|",
                            "|" + listaVozaca.get(i).ime + "|", "|" + stanje + "|");
                    listaIspisPodataka.add(ispis2);
                }
                listaIspisPodataka.add("--------------------------------------------------------------");
            } else if (naredba.contains("IZLAZ")) {
                unos.radi = false;

            }
        }
    }

    public static void isprazniVozilaKrajDana() {
        listaIspisPodataka.add("Kraj dana");
        for (int v = 0; v < listaVozila.size(); v++) {
            if (listaVozila.get(v).getPopunjenost() != 0) {
                listaVozila.get(v).setBrojZbrinjavanja(listaVozila.get(v).getBrojZbrinjavanja() + 1);
                listaVozila.get(v).setKolicinaPokupljenogOtpada(listaVozila.get(v).getKolicinaPokupljenogOtpada()
                        + listaVozila.get(v).getPopunjenost());
            }
            String podaciZbrinjavanje = ("Vozilo: " + listaVozila.get(v).getNaziv() + " zbrinjava: "
                    + listaVozila.get(v).getPopunjenost() + "kg otpada za kraj radnog dana");
            listaIspisPodataka.add(podaciZbrinjavanje);
            listaVozila.get(v).setPopunjenost(0f);

        }
        for (Vozilo vozilo : listaVozila) {
            if (vozilo.getStanje() instanceof PrikupljaOtpad) {
                vozilo.parking();
            }
        }
    }

    public static void ispisStatistickePodatke() {
        float ukupnoZbrinutoStaklo = 0f;
        float ukupnoZbrinutoPapir = 0f;
        float ukupnoZbrinutoMetal = 0f;
        float ukupnoZbrinutoBio = 0f;
        float ukupnoZbrinutoMjesano = 0f;
        for (int v = 0; v < listaVozila.size(); v++) {
            if (listaVozila.get(v).getVrsta() == 0) {
                ukupnoZbrinutoStaklo += listaVozila.get(v).getKolicinaPokupljenogOtpada();
            } else if (listaVozila.get(v).getVrsta() == 1) {
                ukupnoZbrinutoPapir += listaVozila.get(v).getKolicinaPokupljenogOtpada();
            } else if (listaVozila.get(v).getVrsta() == 2) {
                ukupnoZbrinutoMetal += listaVozila.get(v).getKolicinaPokupljenogOtpada();
            } else if (listaVozila.get(v).getVrsta() == 3) {
                ukupnoZbrinutoBio += listaVozila.get(v).getKolicinaPokupljenogOtpada();
            } else if (listaVozila.get(v).getVrsta() == 4) {
                ukupnoZbrinutoMjesano += listaVozila.get(v).getKolicinaPokupljenogOtpada();
            }
            String podaciVozila = ("Vozilo " + listaVozila.get(v).getNaziv() + " pokupljenih spremnika: "
                    + listaVozila.get(v).getBrojPokupljenihSpremnika() + " kolicina pokupljenog otpada: "
                    + listaVozila.get(v).getKolicinaPokupljenogOtpada() + "kg  broj odlazaka na zbrinjavanje otpada: "
                    + listaVozila.get(v).getBrojZbrinjavanja());
            listaIspisPodataka.add(podaciVozila);
        }
        String podaciOtpad = ("Ukupno zbrinuto otpada staklo: " + ukupnoZbrinutoStaklo + "kg papir: " + ukupnoZbrinutoPapir + "kg metal: "
                + ukupnoZbrinutoMetal + "kg bio: " + ukupnoZbrinutoBio + "kg mjesano: " + ukupnoZbrinutoMjesano + "kg");

        listaIspisPodataka.add(podaciOtpad);
    }
}
