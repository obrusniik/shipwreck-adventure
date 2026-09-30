/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazPostav implementuje příkaz "postav".
 *  Postaví vor z dřeva a liány v batohu. Po úspěchu odebere materiály
 *  a nastaví příznak postaveného voru v herním plánu.
 *
 */
class PrikazPostav implements IPrikaz {
    private static final String NAZEV = "postav";
    private HerniPlan plan;

    /**
     *  Konstruktor.
     *
     *  @param plan herní plán
     */
    public PrikazPostav(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Postaví vor, pokud má hráč v batohu drevo a lianu.
     *
     *  @param parametry nevyužito
     *  @return zpráva o výsledku stavby
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (plan.jeVorPostaven()) {
            return "Vor jsi už postavil!";
        }
        Batoh batoh = plan.getBatoh();
        boolean maDrevo = batoh.obsahujeVec("drevo");
        boolean maLianu = batoh.obsahujeVec("liana");

        if (!maDrevo && !maLianu) {
            return "Na stavbu voru potřebuješ drevo a liana. Oboje ti chybí.";
        }
        if (!maDrevo) {
            return "Na stavbu voru ještě potřebuješ: drevo.";
        }
        if (!maLianu) {
            return "Na stavbu voru ještě potřebuješ: liana.";
        }
        batoh.odeberVec("drevo");
        batoh.odeberVec("liana");
        plan.setVorPostaven(true);
        return "Postavil jsi vor z dřeva a liány!\n"
             + "Teď potřebuješ signální raketu — najdeš ji na vulkánu.";
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "postav"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
