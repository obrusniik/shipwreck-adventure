/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

import java.util.ArrayList;

/**
 *  Třída Postava představuje postavu ve hře (např. papoušek nebo had).
 *  Postava se může nacházet v některém prostoru, může blokovat vstup
 *  a umí říkat dialogy, které se postupně střídají.
 *
 *  Tato třída je součástí jednoduché textové hry.
 *
 */
public class Postava {

    private String jmeno;
    private String popis;
    private boolean blokuje;
    private ArrayList<String> dialogy;
    private int indexDialogu;

    /**
     *  Konstruktor.
     *
     *@param  jmeno    jméno postavy (např. "had")
     *@param  popis    krátký popis postavy
     *@param  blokuje  true, pokud postava blokuje vstup do svého prostoru
     */
    public Postava(String jmeno, String popis, boolean blokuje) {
        this.jmeno = jmeno;
        this.popis = popis;
        this.blokuje = blokuje;
        this.dialogy = new ArrayList<>();
        this.indexDialogu = 0;
    }

    /**
     *  Přidá větu, kterou postava může říct.
     *
     *@param  dialog  text dialogu
     */
    public void pridejDialog(String dialog) {
        dialogy.add(dialog);
    }

    /**
     *  Vrátí aktuální dialog a posune se na další (dialogy se cyklicky střídají).
     *
     *@return    text dialogu
     */
    public String promluv() {
        if (dialogy.isEmpty()) {
            return jmeno + " mlčí.";
        }
        String dialog = dialogy.get(indexDialogu);
        indexDialogu = indexDialogu + 1;
        if (indexDialogu >= dialogy.size()) {
            indexDialogu = 0;
        }
        return jmeno + ": \"" + dialog + "\"";
    }

    /**
     *  Vrátí jméno postavy.
     *
     *@return    jméno postavy
     */
    public String getJmeno() {
        return jmeno;
    }

    /**
     *  Vrátí popis postavy.
     *
     *@return    popis postavy
     */
    public String getPopis() {
        return popis;
    }

    /**
     *  Zjistí, zda postava blokuje vstup.
     *
     *@return    true, pokud postava blokuje vstup
     */
    public boolean jeBlokujici() {
        return blokuje;
    }

    /**
     *  Nastaví, zda postava blokuje vstup.
     *
     *@param  blokuje  true = blokuje
     */
    public void setBlokuje(boolean blokuje) {
        this.blokuje = blokuje;
    }
}
