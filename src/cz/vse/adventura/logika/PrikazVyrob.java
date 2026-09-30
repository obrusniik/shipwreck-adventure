/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída PrikazVyrob implementuje příkaz "vyrob".
 *  Vyrobí věc z materiálů v batohu. Aktuálně podporovaný recept:
 *  "vyrob pochoden" = palmovy_list + kresadlo.
 *
 */
class PrikazVyrob implements IPrikaz {
    private static final String NAZEV = "vyrob";
    private HerniPlan plan;

    /**
     *  Konstruktor.
     *
     *  @param plan herní plán
     */
    public PrikazVyrob(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provede výrobu zadaného předmětu.
     *
     *  @param parametry parametry[0] = co vyrobit (např. "pochoden")
     *  @return zpráva o výsledku výroby
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Co chceš vyrobit? Zadej: vyrob <věc>\n"
                 + "Recept: vyrob pochoden (palmovy_list + kresadlo)";
        }
        String co = parametry[0];
        if (co.equals("pochoden")) {
            return vyrobPochoden();
        }
        return "Nevím, jak vyrobit '" + co + "'.";
    }

    /**
     *  Vyrobí pochodeň, jsou-li v batohu palmový list a kresadlo.
     *
     *  @return výsledek výroby
     */
    private String vyrobPochoden() {
        Batoh batoh = plan.getBatoh();
        boolean maList = batoh.obsahujeVec("palmovy_list");
        boolean maKresadlo = batoh.obsahujeVec("kresadlo");

        if (!maList && !maKresadlo) {
            return "Na pochodeň potřebuješ palmovy_list a kresadlo. Oboje ti chybí.";
        }
        if (!maList) {
            return "Na pochodeň ještě potřebuješ: palmovy_list.";
        }
        if (!maKresadlo) {
            return "Na pochodeň ještě potřebuješ: kresadlo.";
        }
        batoh.odeberVec("palmovy_list");
        batoh.odeberVec("kresadlo");
        batoh.pridejVec(new Vec("pochoden", "hořící pochodeň, svítí v temných místech", 0.3, true));
        return "Vyrobil jsi pochodeň! S pochodní uvidíš i v temné jeskyni.";
    }

    /**
     *  Vrátí název příkazu.
     *
     *  @return "vyrob"
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
