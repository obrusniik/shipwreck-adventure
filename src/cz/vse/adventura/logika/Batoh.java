/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

import java.util.ArrayList;

/**
 *  Třída Batoh představuje batoh hráče. Uchovává seznam věcí a hlídá,
 *  aby celková hmotnost nepřesáhla maximální nosnost {@value #MAX_HMOTNOST} kg.
 *
 *  Tato třída je součástí jednoduché textové hry.
 */
public class Batoh {

    /** Maximální celková hmotnost věcí v batohu v kilogramech. */
    public static final double MAX_HMOTNOST = 10.0;

    private ArrayList<Vec> seznam;

    /**
     *  Konstruktor — vytvoří prázdný batoh.
     */
    public Batoh() {
        seznam = new ArrayList<>();
    }

    /**
     *  Pokusí se přidat věc do batohu. Věc se nepřidá, pokud by přesáhla
     *  maximální nosnost.
     *
     *@param  vec  věc, kterou chceme přidat
     *@return      true, pokud byla věc přidána
     */
    public boolean pridejVec(Vec vec) {
        if (getAktualniHmotnost() + vec.getHmotnost() > MAX_HMOTNOST) {
            return false;
        }
        seznam.add(vec);
        return true;
    }

    /**
     *  Odebere věc z batohu podle názvu.
     *
     *@param  nazev  název věci
     *@return        odebraná věc, nebo null pokud věc nebyla nalezena
     */
    public Vec odeberVec(String nazev) {
        for (int i = 0; i < seznam.size(); i++) {
            if (seznam.get(i).getNazev().equalsIgnoreCase(nazev)) {
                return seznam.remove(i);
            }
        }
        return null;
    }

    /**
     *  Najde věc v batohu podle názvu bez jejího odebrání.
     *
     *@param  nazev  název věci
     *@return        nalezená věc, nebo null
     */
    public Vec najdiVec(String nazev) {
        for (Vec vec : seznam) {
            if (vec.getNazev().equalsIgnoreCase(nazev)) {
                return vec;
            }
        }
        return null;
    }

    /**
     *  Zjistí, zda batoh obsahuje věc se zadaným názvem.
     *
     *@param  nazev  název věci
     *@return        true, pokud batoh věc obsahuje
     */
    public boolean obsahujeVec(String nazev) {
        return najdiVec(nazev) != null;
    }

    /**
     *  Spočítá aktuální celkovou hmotnost obsahu batohu.
     *
     *@return    aktuální hmotnost v kilogramech
     */
    public double getAktualniHmotnost() {
        double celkem = 0.0;
        for (Vec vec : seznam) {
            celkem += vec.getHmotnost();
        }
        return celkem;
    }

    /**
     *  Sestaví textový výpis obsahu batohu včetně aktuální a maximální hmotnosti.
     *
     *@return    textový výpis batohu
     */
    public String getPopis() {
        if (seznam.isEmpty()) {
            return "Batoh je prázdný.";
        }
        String text = "Batoh obsahuje:\n";
        for (Vec vec : seznam) {
            text += "  - " + vec.toString() + "\n";
        }
        text += "Hmotnost: " + getAktualniHmotnost() + " / " + MAX_HMOTNOST + " kg";
        return text;
    }

    /**
     *  Vrátí seznam věcí v batohu.
     *
     *@return    seznam věcí
     */
    public ArrayList<Vec> getSeznam() {
        return seznam;
    }
}
