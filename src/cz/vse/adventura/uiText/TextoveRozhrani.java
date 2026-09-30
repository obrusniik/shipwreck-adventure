/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.uiText;

import cz.vse.adventura.logika.IHra;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *  Class TextoveRozhrani
 *
 *  Uživatelské rozhraní aplikace Adventura. Vytváří instanci hry, čte vstup
 *  od uživatele (klávesnice nebo soubor) a vypisuje odpovědi hry.
 *
 *  Podporuje dva režimy:
 *  <ul>
 *    <li>{@link #hraj()} — interaktivní (Scanner ze System.in)</li>
 *    <li>{@link #hrajZeSouboru(String)} — auto-play ze souboru (výstup do vystup.txt)</li>
 *  </ul>
 *
 */
public class TextoveRozhrani {
    private IHra hra;
    private Scanner ctecka;

    /**
     *  Vytváří rozhraní napojené na herní engine.
     *
     *  @param  hra  herní engine implementující rozhraní {@link IHra}
     */
    public TextoveRozhrani(IHra hra) {
        this.hra = hra;
        this.ctecka = new Scanner(System.in);
    }

    /**
     *  Hlavní metoda hry v interaktivním režimu. Vypíše úvodní text,
     *  pak opakovaně čte a zpracovává příkazy do konce hry.
     *  Nakonec vypíše epilog.
     */
    public void hraj() {
        System.out.println(hra.vratUvitani());

        while (!hra.konecHry()) {
            String radek = prectiString();
            System.out.println(hra.zpracujPrikaz(radek));
        }

        System.out.println(hra.vratEpilog());
    }

    /**
     *  Spustí hru v souborovém režimu. Příkazy čte z daného souboru
     *  (jeden příkaz na řádek) a celý průběh hry zapisuje do "vystup.txt".
     *
     *  @param  nazevSouboru  cesta ke vstupnímu souboru s příkazy
     */
    public void hrajZeSouboru(String nazevSouboru) {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(nazevSouboru));
            PrintWriter writer = new PrintWriter("vystup.txt")
        ) {
            String uvitani = hra.vratUvitani();
            System.out.println(uvitani);
            writer.println(uvitani);

            String radek;
            while (!hra.konecHry() && (radek = reader.readLine()) != null) {
                System.out.println("> " + radek);
                writer.println("> " + radek);

                String odpoved = hra.zpracujPrikaz(radek);
                System.out.println(odpoved);
                writer.println(odpoved);
            }

            String epilog = hra.vratEpilog();
            System.out.println(epilog);
            writer.println(epilog);

            System.out.println("\n[Výstup zapsán do: vystup.txt]");

        } catch (FileNotFoundException e) {
            System.err.println("Soubor nenalezen: " + nazevSouboru);
        } catch (IOException e) {
            System.err.println("Chyba při čtení/zápisu: " + e.getMessage());
        }
    }

    /**
     *  Přečte jeden příkaz ze standardního vstupu.
     *
     *  @return  přečtený řádek
     */
    private String prectiString() {
        System.out.print("> ");
        return ctecka.nextLine();
    }
}
