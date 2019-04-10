/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3;

import java.awt.event.InputMethodListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author Hrvoje
 */
public class DohvatiParametre {

    private static volatile DohvatiParametre INSTANCE;
    protected static String datoteka;
    private Properties postavke;
    public static String putanja;

    private DohvatiParametre(String datoteka) {
        this.datoteka = datoteka;
        this.postavke = new Properties();
    }

    public static DohvatiParametre getInstance(String datoteka) {
        if (INSTANCE == null) {
            synchronized (DohvatiParametre.class) {
                INSTANCE = new DohvatiParametre(datoteka);
            }
        }
        return INSTANCE;
    }

    public String dajParametar(String nazivParametra) {
        return this.postavke.getProperty(nazivParametra);
    }

    public void preuzmiParametre(String datoteka) {
        if (datoteka.toLowerCase().endsWith(".txt")) {

            if (datoteka == null || datoteka.length() == 0) {
                System.out.println("Datoteka mora imati naziv!");
                MainProgram.listaIspisPodataka.add("Datoteka mora imati naziv!");
                
            }
            
            File datKonf = new File(datoteka);
            setPutanja(datKonf.getAbsolutePath());
            

            if (!datKonf.exists()) {
                System.out.println("Datoteka " + datoteka + " ne postoji!");
                MainProgram.listaIspisPodataka.add("Datoteka " + datoteka + " ne postoji!");
                System.exit(0);
            } else if (datKonf.isDirectory()) {
                System.out.println("Datoteka " + datoteka + " nije datoteka!");
                MainProgram.listaIspisPodataka.add("Datoteka " + datoteka + " nije datoteka!");
            }

            try {
                File file = new File(datKonf.getAbsolutePath());
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(file), "UTF8"));

                Properties prop = new Properties();
                prop.load(in);
                kopirajParametre(prop);
                in.close();
            } catch (IOException ex) {
                System.out.println("Problem kod citanja datoteke"
                        + datKonf.getAbsolutePath());
                MainProgram.listaIspisPodataka.add("Problem kod citanja datoteke"
                        + datKonf.getAbsolutePath());
                
            }
            
        } else {
            System.out.println("Ekstenzija datoteke nepoznata!");
            MainProgram.listaIspisPodataka.add("Ekstenzija datoteke nepoznata!");
        }

    }

    public void dodajParametar(Properties postavke) {
        for (Object k1 : postavke.keySet()) {
            String k = (String) k1;
            String v = postavke.getProperty(k);
            this.postavke.setProperty(k, v);
        }
    }

    public void kopirajParametre(Properties postavke) {
        this.postavke.clear();
        dodajParametar(postavke);
    }

    public static String getPutanja() {
        return putanja;
    }

    public void setPutanja(String putanja) {
        this.putanja = putanja;
    }
}
