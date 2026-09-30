/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazPoloz implementuje příkaz "poloz".
 *  Vyjme věc z batohu a položí ji do aktuálního prostoru.
 *
 */
class PrikazPoloz implements IPrikaz {
    private static final String NAZEV = "poloz";
    private HerniPlan plan;

    /**
     *  Konstruktor.
     *
     *  @param plan herní plán
     */
    public PrikazPoloz(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Vyndá věc z batohu a umístí ji do prostoru.
     *
     *  @param parametry parametry[0] = název věci
     *  @return potvrzení nebo chybová hláška
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Co chceš položit? Zadej: poloz <věc>";
        }

        String nazev = parametry[0];
        Batoh batoh = plan.getBatoh();
        Vec vec = batoh.odeberVec(nazev);

        if (vec == null) {
            return "Věc '" + nazev + "' v batohu nemáš.";
        }

        plan.getAktualniProstor().vlozVec(vec);
        return "Položil jsi: " + vec.getNazev();
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "poloz"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
