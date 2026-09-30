/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

/**
 *  Třída Hra - třída představující logiku adventury.
 *
 *  Hlavní třída logiky aplikace. Vytváří herní plán, registruje všechny
 *  příkazy a vyhodnocuje vstup od uživatele. Vypisuje uvítací a ukončovací
 *  text hry.
 *
 *  @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jiří Obrusník
 *  @version    1.0
 */
public class Hra implements IHra {
    private SeznamPrikazu platnePrikazy;
    private HerniPlan herniPlan;
    private boolean konecHry = false;

    /**
     *  Konstruktor — vytvoří herní plán a zaregistruje všechny dostupné příkazy.
     */
    public Hra() {
        herniPlan = new HerniPlan();
        platnePrikazy = new SeznamPrikazu();

        // Existující příkazy z kostry
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazPomoc(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));

        // Nové příkazy — semestrální projekt
        platnePrikazy.vlozPrikaz(new PrikazSeber(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazPoloz(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazInventar(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazVypis(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazProzkoumej(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazPromluv(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazPostav(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazOdpal(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazHod(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazVyrob(herniPlan));
    }

    /**
     *  Vrátí úvodní zprávu pro hráče.
     *
     *  @return úvodní text + popis startovního prostoru
     */
    public String vratUvitani() {
        return "Vítejte ve hře Ztroskotání na ostrově!\n"
             + "Probouzíš se na pláži po ztroskotání lodi.\n"
             + "Cíl: postav vor (drevo + liana), najdi raketu na vulkánu\n"
             + "a odpal ji na molu — záchranáři tě uvidí.\n"
             + "Napište 'nápověda', pokud si nevíte rady.\n\n"
             + herniPlan.getAktualniProstor().dlouhyPopis(herniPlan.maSvetlo());
    }

    /**
     *  Vrátí závěrečnou zprávu pro hráče (výhru nebo prohru).
     *
     *  @return závěrečný text
     */
    public String vratEpilog() {
        if (herniPlan.jeVyhrana()) {
            return "Záchranný člun spatřil tvou raketu a míří k tobě!\n"
                 + "GRATULUJEME — ostrov je za tebou!";
        }
        return "Dík, že jste si zahráli. Ahoj.";
    }

    /**
     *  Zjistí, zda hra skončila.
     *
     *  @return  true, pokud hra skončila
     */
    public boolean konecHry() {
        return konecHry || herniPlan.jeKonec();
    }

    /**
     *  Zpracuje řetězec zadaný uživatelem — rozdělí ho na slovo příkazu
     *  a parametry, najde odpovídající příkaz a spustí ho.
     *
     *  @param  radek  text, který zadal uživatel
     *  @return        text k vypsání
     */
    public String zpracujPrikaz(String radek) {
        String[] slova = radek.trim().split("[ \t]+");
        String slovoPrikazu = slova[0];
        String[] parametry = new String[slova.length - 1];
        for (int i = 0; i < parametry.length; i++) {
            parametry[i] = slova[i + 1];
        }
        String textKVypsani;
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.provedPrikaz(parametry);
        } else {
            textKVypsani = "Nevím, co tím myslíš? Tento příkaz neznám.";
        }
        return textKVypsani;
    }

    /**
     *  Nastaví příznak konce hry. Používá ji PrikazKonec.
     *
     *  @param  konecHry  true = konec hry
     */
    void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }

    /**
     *  Vrátí odkaz na herní plán (využíváno hlavně v testech).
     *
     *  @return  herní plán
     */
    public HerniPlan getHerniPlan() {
        return herniPlan;
    }
}
