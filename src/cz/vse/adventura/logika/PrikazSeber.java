/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazSeber implementuje příkaz "seber".
 *  Sebere věc z aktuálního prostoru a vloží ji do batohu. Kontroluje,
 *  zda věc existuje, zda ji lze přenášet a zda se vejde do batohu.
 *
 */
class PrikazSeber implements IPrikaz {
    private static final String NAZEV = "seber";
    private HerniPlan plan;

    /**
     *  Konstruktor.
     *
     *  @param plan herní plán
     */
    public PrikazSeber(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provede sebrání věci.
     *
     *  @param parametry parametry[0] = název věci
     *  @return potvrzení nebo chybová hláška
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Co chceš sebrat? Zadej: seber <věc>";
        }

        String nazev = parametry[0];
        Prostor aktualni = plan.getAktualniProstor();
        Batoh batoh = plan.getBatoh();

        Vec vec = aktualni.najdiVec(nazev);
        if (vec == null) {
            return "Věc '" + nazev + "' tu není.";
        }
        if (!vec.jePrenositelna()) {
            return "Věc '" + nazev + "' nelze sebrat, je příliš těžká.";
        }
        if (batoh.getAktualniHmotnost() + vec.getHmotnost() > Batoh.MAX_HMOTNOST) {
            return "Batoh by byl moc těžký, tohle už neuneseš.";
        }

        aktualni.odeberVec(nazev);
        batoh.pridejVec(vec);
        return "Sebral jsi: " + vec.getNazev() + " (" + vec.getHmotnost() + " kg)";
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "seber"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
