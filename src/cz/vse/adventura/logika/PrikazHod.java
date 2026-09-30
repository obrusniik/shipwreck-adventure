/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazHod implementuje příkaz "hod".
 *  Hodí věc z batohu na cíl. Speciální případ "hod kamen had" zažene hada,
 *  pokud je v aktuálním nebo sousedním (blokovaném) prostoru.
 *
 */
class PrikazHod implements IPrikaz {
    private static final String NAZEV = "hod";
    private HerniPlan plan;

    /**
     *  Konstruktor.
     *
     *  @param plan herní plán
     */
    public PrikazHod(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provede hod věci na cíl.
     *
     *  @param parametry parametry[0] = věc, parametry[1] = cíl
     *  @return zpráva o výsledku hodu
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length < 2) {
            return "Co a na co chceš hodit? Zadej: hod <věc> <cíl>";
        }
        String nazevVeci = parametry[0];
        String nazevCile = parametry[1];
        Batoh batoh = plan.getBatoh();

        if (!batoh.obsahujeVec(nazevVeci)) {
            return "Věc '" + nazevVeci + "' v batohu nemáš.";
        }

        if (nazevVeci.equalsIgnoreCase("kamen") && nazevCile.equalsIgnoreCase("had")) {
            return hodKamenPoHadovi(batoh);
        }

        batoh.odeberVec(nazevVeci);
        return "Hodil jsi " + nazevVeci + " na " + nazevCile + ". Nic se nestalo.";
    }

    /**
     *  Pomocná metoda — pokusí se hodit kámen po hadovi.
     *  Had musí být v aktuálním nebo sousedním blokovaném prostoru.
     *
     *  @param batoh batoh hráče
     *  @return výsledek pokusu
     */
    private String hodKamenPoHadovi(Batoh batoh) {
        Prostor aktualni = plan.getAktualniProstor();
        Postava had = aktualni.najdiPostavu("had");
        if (had == null) {
            for (Prostor soused : aktualni.getVychody()) {
                if (soused.jeZablokovany()) {
                    had = soused.najdiPostavu("had");
                    if (had != null) {
                        break;
                    }
                }
            }
        }
        if (had == null) {
            return "Had tu není. Hod kámen jen pokud je had nablízku.";
        }
        batoh.odeberVec("kamen");
        plan.zazenHada();
        return "Hodil jsi kámen po hadovi — ŠVIC! Had se vyplašil a odplazil pryč.\n"
             + "Cesta do zříceniny je volná!";
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "hod"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
