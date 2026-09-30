/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazInventar implementuje příkaz "inventar".
 *  Zobrazí obsah batohu hráče včetně aktuální a maximální hmotnosti.
 *
 */
class PrikazInventar implements IPrikaz {
    private static final String NAZEV = "inventar";
    private HerniPlan plan;

    /**
     *  Konstruktor.
     *
     *  @param plan herní plán
     */
    public PrikazInventar(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Vrátí textový výpis obsahu batohu.
     *
     *  @param parametry nevyužito
     *  @return obsah batohu
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return plan.getBatoh().getPopis();
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "inventar"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
