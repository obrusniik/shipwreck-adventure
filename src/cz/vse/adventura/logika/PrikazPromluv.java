/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazPromluv implementuje příkaz "promluv".
 *  Najde postavu v aktuálním prostoru a vrátí její aktuální dialog.
 *
 */
class PrikazPromluv implements IPrikaz {
    private static final String NAZEV = "promluv";
    private HerniPlan plan;

    /**
     *  Konstruktor.
     *
     *  @param plan herní plán
     */
    public PrikazPromluv(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Osloví postavu se zadaným jménem v aktuálním prostoru.
     *
     *  @param parametry parametry[0] = jméno postavy
     *  @return dialog postavy nebo chybová hláška
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "S kým chceš mluvit? Zadej: promluv <jméno>";
        }
        String jmeno = parametry[0];
        Postava postava = plan.getAktualniProstor().najdiPostavu(jmeno);
        if (postava == null) {
            return "Tady žádný " + jmeno + " není.";
        }
        return postava.promluv();
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "promluv"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
