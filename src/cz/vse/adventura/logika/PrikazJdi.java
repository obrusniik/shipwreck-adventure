/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazJdi implementuje příkaz "jdi".
 *  Přesune hráče do sousedního prostoru. Kontroluje, zda cílový prostor
 *  není zablokován postavou (např. hadem). Po úspěšném přesunu pohne papouškem.
 */
class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    private HerniPlan plan;

    /**
     *  Konstruktor.
     *
     *  @param plan herní plán
     */
    public PrikazJdi(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provádí příkaz "jdi". Zkouší přesun do zadaného sousedního prostoru.
     *  Pokud je cílový prostor blokován (had), přesun se neprovede.
     *
     *  @param parametry parametry[0] = jméno cílového prostoru
     *  @return text k vypsání
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Kam mám jít? Musíš zadat jméno východu.";
        }

        String smer = parametry[0];
        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) {
            return "Tam se odsud jít nedá!";
        }
        if (sousedniProstor.jeZablokovany()) {
            return "Cestu blokuje had! Zažeň ho nejdřív.\n"
                 + "(Tip: hod kamen had)";
        }

        plan.setAktualniProstor(sousedniProstor);

        // po přesunu hráče se papoušek přesune do náhodného prostoru
        plan.pohniPapouskem();

        return sousedniProstor.dlouhyPopis(plan.maSvetlo());
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "jdi"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
