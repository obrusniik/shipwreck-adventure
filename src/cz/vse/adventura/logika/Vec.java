/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída Vec - představuje věc/předmět v herním světě.
 *  Každá věc má název (slouží jako identifikátor v příkazech),
 *  popis, hmotnost v kilogramech a příznak, zda lze sebrat do batohu.
 *
 *  Tato třída je součástí jednoduché textové hry.
 */
public class Vec {

    private String nazev;
    private String popis;
    private double hmotnost;
    private boolean prenositelna;

    /**
     *  Konstruktor třídy Vec.
     *
     *@param  nazev         identifikační název věci (např. "kamen")
     *@param  popis         krátký popis věci
     *@param  hmotnost      hmotnost v kilogramech
     *@param  prenositelna  true, pokud lze věc sebrat do batohu
     */
    public Vec(String nazev, String popis, double hmotnost, boolean prenositelna) {
        this.nazev = nazev;
        this.popis = popis;
        this.hmotnost = hmotnost;
        this.prenositelna = prenositelna;
    }

    /**
     *  Vrací název věci.
     *
     *@return    název věci
     */
    public String getNazev() {
        return nazev;
    }

    /**
     *  Vrací popis věci.
     *
     *@return    popis věci
     */
    public String getPopis() {
        return popis;
    }

    /**
     *  Vrací hmotnost věci v kilogramech.
     *
     *@return    hmotnost v kg
     */
    public double getHmotnost() {
        return hmotnost;
    }

    /**
     *  Vrací příznak přenositelnosti.
     *
     *@return    true, pokud lze věc sebrat
     */
    public boolean jePrenositelna() {
        return prenositelna;
    }

    /**
     *  Vrací textovou reprezentaci věci ve formátu "nazev (hmotnost kg)".
     *
     *@return    textová reprezentace věci
     */
    @Override
    public String toString() {
        return nazev + " (" + hmotnost + " kg)";
    }
}
