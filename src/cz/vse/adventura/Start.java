/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura;

import cz.vse.adventura.logika.Hra;
import cz.vse.adventura.logika.IHra;
import cz.vse.adventura.uiText.TextoveRozhrani;

public class Start {

    /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     * Bez argumentů spustí interaktivní režim, s argumentem auto-play ze souboru.
     *
     * @param args args[0] = cesta ke vstupnímu souboru (volitelně)
     */
    public static void main(String[] args) {
        IHra hra = new Hra();
        TextoveRozhrani ui = new TextoveRozhrani(hra);
        if (args.length == 0) {
            ui.hraj();
        } else {
            ui.hrajZeSouboru(args[0]);
        }
    }
}
