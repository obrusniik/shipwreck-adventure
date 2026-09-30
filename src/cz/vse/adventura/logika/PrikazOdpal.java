/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazOdpali implementuje vítězný příkaz "odpali".
 *  Odpálí signální raketu z mola. Vyžaduje: být na molu, mít postaven vor
 *  a mít raketu v batohu. Při splnění všech podmínek hra končí výhrou.
 *
 */
class PrikazOdpal implements IPrikaz {
    private static final String NAZEV = "odpal";
    private HerniPlan plan;

    /**
     *  Konstruktor.
     *
     *  @param plan herní plán
     */
    public PrikazOdpal(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Odpálí raketu, je-li hráč na molu a má vor + raketu.
     *
     *  @param parametry nevyužito
     *  @return zpráva o výsledku
     */
    @Override
    public String provedPrikaz(String... parametry) {
        String nazevProstoru = plan.getAktualniProstor().getNazev();
        if (!nazevProstoru.equalsIgnoreCase("molo")) {
            return "Raketu musíš odpálit z mola. Jsi teď v: " + nazevProstoru;
        }
        if (!plan.jeVorPostaven()) {
            return "Nejdřív musíš postavit vor! (drevo + liana → postav)";
        }
        Batoh batoh = plan.getBatoh();
        if (!batoh.obsahujeVec("raketa")) {
            return "Nemáš raketu! Najdi ji na vulkánu a seber ji.";
        }
        batoh.odeberVec("raketa");
        plan.setVyhrana(true);
        plan.setKonec(true);
        return "WHOOOSH! Signální raketa letí vysoko do nebe!\n"
             + "Záchranný člun na obzoru otáčí kurz a míří k tobě!";
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "odpali"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
