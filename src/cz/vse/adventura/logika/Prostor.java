/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry.
 *
 * "Prostor" reprezentuje jedno místo (lokaci) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy, věci, postavy
 * a může být temný (vyžaduje světlo pro úplný popis).
 *
 */
public class Prostor {

    private String nazev;
    private String popis;
    private boolean temny;
    private Set<Prostor> vychody;
    private Map<String, Vec> veci;
    private List<Postava> postavy;

    /**
     * Vytvoření prostoru se zadaným názvem a popisem.
     *
     * @param nazev název prostoru, jednoznačný identifikátor (jedno slovo)
     * @param popis popis prostoru
     */
    public Prostor(String nazev, String popis) {
        this(nazev, popis, false);
    }

    /**
     * Vytvoření prostoru s možností nastavit temnotu.
     *
     * @param nazev název prostoru
     * @param popis popis prostoru
     * @param temny true = prostor je temný (bez světla nelze vidět)
     */
    public Prostor(String nazev, String popis, boolean temny) {
        this.nazev = nazev;
        this.popis = popis;
        this.temny = temny;
        this.vychody = new HashSet<>();
        this.veci = new HashMap<>();
        this.postavy = new ArrayList<>();
    }

    /**
     * Definuje východ z prostoru (sousední prostor).
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Porovnání dvou prostorů podle názvu.
     *
     * @param o objekt k porovnání
     * @return true, pokud má zadaný prostor stejný název
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prostor)) {
            return false;
        }
        Prostor druhy = (Prostor) o;
        return java.util.Objects.equals(this.nazev, druhy.nazev);
    }

    /**
     * Vrací číselný identifikátor instance odvozený z názvu.
     *
     * @return hashCode podle názvu
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }

    /**
     * Vrací název prostoru.
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * Vrací příznak temnoty.
     *
     * @return true = prostor je temný
     */
    public boolean jeTemny() {
        return temny;
    }

    /**
     * Vrací "dlouhý" popis prostoru se všemi informacemi.
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return dlouhyPopis(true);
    }

    /**
     * Vrací "dlouhý" popis prostoru s ohledem na dostupnost světla.
     * V temném prostoru bez světla vrátí pouze zprávu o tmě.
     *
     * @param maSvetlo true, pokud má hráč k dispozici světlo (pochodeň)
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis(boolean maSvetlo) {
        if (temny && !maSvetlo) {
            return "Jsi v prostoru: " + nazev + "\n"
                 + "Je tu tma jako v pytli. Potřebuješ světlo.\n"
                 + "(Tip: vyrobíš pochodeň: vyrob pochoden)";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Jsi v prostoru: ").append(popis).append(".\n");
        sb.append(popisVychodu()).append("\n");
        sb.append(popisVeci());
        String pp = popisPostav();
        if (!pp.isEmpty()) {
            sb.append("\n").append(pp);
        }
        return sb.toString();
    }

    /**
     * Sestaví popis sousedních východů.
     *
     * @return text se seznamem východů
     */
    private String popisVychodu() {
        StringBuilder sb = new StringBuilder("východy:");
        for (Prostor sousedni : vychody) {
            sb.append(" ").append(sousedni.getNazev());
        }
        return sb.toString();
    }

    /**
     * Sestaví popis věcí v prostoru.
     *
     * @return text se seznamem věcí
     */
    private String popisVeci() {
        if (veci.isEmpty()) {
            return "věci: žádné";
        }
        StringBuilder sb = new StringBuilder("věci:");
        for (Vec vec : veci.values()) {
            sb.append(" ").append(vec.getNazev());
            if (!vec.jePrenositelna()) {
                sb.append("[!]");
            }
        }
        return sb.toString();
    }

    /**
     * Sestaví popis postav v prostoru.
     *
     * @return text se seznamem postav, nebo prázdný řetězec
     */
    private String popisPostav() {
        if (postavy.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("postavy:");
        for (Postava p : postavy) {
            sb.append(" ").append(p.getJmeno());
        }
        return sb.toString();
    }

    /**
     * Vrací sousední prostor podle jeho názvu.
     *
     * @param nazevSouseda jméno hledaného sousedního prostoru
     * @return sousední prostor, nebo null
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        for (Prostor sousedni : vychody) {
            if (sousedni.getNazev().equals(nazevSouseda)) {
                return sousedni;
            }
        }
        return null;
    }

    /**
     * Vrací nemodifikovatelnou kolekci sousedních prostorů.
     *
     * @return kolekce sousedních prostorů
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }

    // === Práce s věcmi ===

    /**
     * Vloží věc do prostoru.
     *
     * @param vec věc, kterou chceme do prostoru umístit
     */
    public void vlozVec(Vec vec) {
        veci.put(vec.getNazev().toLowerCase(), vec);
    }

    /**
     * Odebere věc z prostoru podle názvu.
     *
     * @param nazev název věci
     * @return odebraná věc, nebo null
     */
    public Vec odeberVec(String nazev) {
        return veci.remove(nazev.toLowerCase());
    }

    /**
     * Najde věc v prostoru podle názvu bez jejího odebrání.
     *
     * @param nazev název věci
     * @return nalezená věc, nebo null
     */
    public Vec najdiVec(String nazev) {
        return veci.get(nazev.toLowerCase());
    }

    /**
     * Zjistí, zda prostor obsahuje věc se zadaným názvem.
     *
     * @param nazev název věci
     * @return true, pokud prostor obsahuje danou věc
     */
    public boolean obsahujeVec(String nazev) {
        return veci.containsKey(nazev.toLowerCase());
    }

    /**
     * Vrátí mapu všech věcí v prostoru.
     *
     * @return mapa věcí (klíč = název lowercase)
     */
    public Map<String, Vec> getVeci() {
        return veci;
    }

    // === Práce s postavami ===

    /**
     * Vloží pohyblivou postavu do prostoru.
     *
     * @param postava postava, která vstoupila do prostoru
     */
    public void vlozPostavu(Postava postava) {
        postavy.add(postava);
    }

    /**
     * Odebere postavu z prostoru.
     *
     * @param postava postava, která opouští prostor
     */
    public void odeberPostavu(Postava postava) {
        postavy.remove(postava);
    }

    /**
     * Najde postavu v prostoru podle jména.
     *
     * @param jmeno jméno postavy
     * @return nalezená postava, nebo null
     */
    public Postava najdiPostavu(String jmeno) {
        for (Postava postava : postavy) {
            if (postava.getJmeno().equalsIgnoreCase(jmeno)) {
                return postava;
            }
        }
        return null;
    }

    /**
     * Zjistí, zda je vstup do prostoru blokován některou z postav.
     *
     * @return true, pokud je v prostoru blokující postava
     */
    public boolean jeZablokovany() {
        for (Postava postava : postavy) {
            if (postava.jeBlokujici()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vrátí seznam postav v prostoru.
     *
     * @return seznam postav
     */
    public List<Postava> getPostavy() {
        return postavy;
    }
}
