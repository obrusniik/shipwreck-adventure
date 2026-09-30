/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazNapoveda implementuje pro hru příkaz "nápověda".
 *  Vypíše stručný popis cíle hry a seznam dostupných příkazů.
 *
 */
class PrikazNapoveda implements IPrikaz {

    private static final String NAZEV = "napoveda";
    private SeznamPrikazu platnePrikazy;

    /**
     *  Konstruktor třídy.
     *
     *  @param platnePrikazy seznam příkazů, který se zobrazuje uživateli
     */
    public PrikazNapoveda(SeznamPrikazu platnePrikazy) {
        this.platnePrikazy = platnePrikazy;
    }

    /**
     *  Vrací nápovědu — cíl hry a seznam příkazů.
     *
     *  @return text nápovědy
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return "Tvým úkolem je dostat se z ostrova:\n"
             + "  1) postav vor z dřeva a liány  (příkaz: postav)\n"
             + "  2) seber signální raketu na vulkánu\n"
             + "  3) dojdi na molo a odpal raketu (příkaz: odpali)\n"
             + "\n"
             + "Dostupné příkazy:\n"
             + platnePrikazy.vratNazvyPrikazu();
    }

    /**
     *  Vrací název příkazu.
     *
     *  @return "nápověda"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
