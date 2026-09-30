/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazPomoc implementuje příkaz "pomoc".
 *  Vypíše seznam všech příkazů, které lze ve hře použít.
 *
 */
class PrikazPomoc implements IPrikaz {
    private static final String NAZEV = "pomoc";
    private SeznamPrikazu platnePrikazy;

    /**
     *  Konstruktor.
     *
     *  @param platnePrikazy seznam příkazů, který se má vypsat
     */
    public PrikazPomoc(SeznamPrikazu platnePrikazy) {
        this.platnePrikazy = platnePrikazy;
    }

    /**
     *  Vrátí seznam použitelných příkazů.
     *
     *  @param parametry nevyužito
     *  @return text s příkazy
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return "Můžeš zadat tyto příkazy:\n" + platnePrikazy.vratNazvyPrikazu();
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "pomoc"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
