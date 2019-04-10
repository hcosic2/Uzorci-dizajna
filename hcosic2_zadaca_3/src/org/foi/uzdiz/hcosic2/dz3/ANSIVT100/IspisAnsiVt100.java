/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.hcosic2.dz3.ANSIVT100;

/**
 *
 * @author Hrvoje
 */
public class IspisAnsiVt100 {

    private static IspisAnsiVt100 instanca = null;
    public static final String ANSI_ESC = "\033[";

    //System.out.print(ANSI_ESC + "2J");
    public static IspisAnsiVt100 getInstanca() {
        if (instanca == null) {
            instanca = new IspisAnsiVt100();
        }
        return instanca;
    }

    static void postavi(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }
    
    public void brisiEkran() {
        System.out.print(ANSI_ESC + "2J");
    }

    public void prikazi(int x, int y, int boja, String tekst) {
        postavi(x, y);
        System.out.print(ANSI_ESC + boja + "m");
        System.out.print(tekst);
    }

}
