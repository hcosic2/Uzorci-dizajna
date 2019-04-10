/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

/**
 *
 * @author Hrvoje
 */
public class DohvatiParametre {

    private static volatile DohvatiParametre INSTANCE;
    protected static String datoteka;
    private Properties postavke;

    private DohvatiParametre(String datoteka) {
        this.datoteka = datoteka;
        this.postavke = new Properties();
    }
    
    public static DohvatiParametre getInstance(String datoteka){
        if(INSTANCE == null)
            synchronized(DohvatiParametre.class){
                INSTANCE = new DohvatiParametre(datoteka);
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
            }

            File datKonf = new File(datoteka);

            if (!datKonf.exists()) {
                System.out.println("Datoteka " + datoteka + " ne postoji!");
            } else if (datKonf.isDirectory()) {
                System.out.println("Datoteka " + datoteka + " nije datoteka!");
            }

            try {
                InputStream is = Files.newInputStream(datKonf.toPath(), StandardOpenOption.READ);
                Properties prop = new Properties();
                prop.load(is);
                kopirajParametre(prop);
                is.close();
            } catch (IOException ex) {
                System.out.println("Problem kod citanja datoteke"
                        + datKonf.getAbsolutePath());
            }
        } else {
            System.out.println("Ekstenzija datoteke nepoznata!");
        }

    }
    
    public void dodajParametar(Properties postavke) {
        for(Object k1 : postavke.keySet()){
            String k = (String) k1; 
            String v = postavke.getProperty(k);
            this.postavke.setProperty(k, v);
        }
    }
    
    public void kopirajParametre(Properties postavke) {
        this.postavke.clear();
        dodajParametar(postavke);
    }

}
