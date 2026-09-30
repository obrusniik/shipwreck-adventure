/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazProzkoumej implementuje příkaz "prozkoumej".
 *  Zobrazí podrobný popis aktuálního prostoru.
 *
 */
class PrikazProzkoumej implements IPrikaz {
    private static final String NAZEV = "prozkoumej";
    private HerniPlan plan;

    /**
     *  Konstruktor.
     *
     *  @param plan herní plán
     */
    public PrikazProzkoumej(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Vrátí podrobný popis aktuálního prostoru.
     *
     *  @param parametry nevyužito
     *  @return popis aktuálního prostoru
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return plan.getAktualniProstor().dlouhyPopis(plan.maSvetlo());
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "prozkoumej"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
