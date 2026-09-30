/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazVypis implementuje příkaz "vypis".
 *  Syntaxe: "vypis prostor" nebo "vypis batoh".
 *
 */
class PrikazVypis implements IPrikaz {
    private static final String NAZEV = "vypis";
    private HerniPlan plan;

    /**
     *  Konstruktor.
     *
     *  @param plan herní plán
     */
    public PrikazVypis(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provede výpis prostoru nebo batohu podle parametru.
     *
     *  @param parametry parametry[0] = "prostor" nebo "batoh"
     *  @return požadovaný výpis nebo chybová hláška
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Co chceš vypsat? Zadej: vypis prostor | vypis batoh";
        }
        String co = parametry[0];
        if (co.equals("prostor")) {
            return plan.getAktualniProstor().dlouhyPopis(plan.maSvetlo());
        }
        if (co.equals("batoh")) {
            return plan.getBatoh().getPopis();
        }
        return "Neznámý cíl výpisu: '" + co + "'. Zadej 'prostor' nebo 'batoh'.";
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "vypis"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
