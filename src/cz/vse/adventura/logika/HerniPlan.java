/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Class HerniPlan - třída představující mapu a stav adventury "Ztroskotání na ostrově".
 *
 *  Vytváří všech 10 prostorů, propojuje je východy, umisťuje věci a postavy.
 *  Pamatuje si aktuální prostor hráče, batoh a stav hry (vor postaven, výhra, konec).
 *
 */
public class HerniPlan {

    private Prostor aktualniProstor;
    private Batoh batoh;
    private Postava papousek;
    private Postava had;
    private Prostor prostorPapouska;
    private boolean vorPostaven;
    private boolean konec;
    private boolean vyhral;
    private Random random;
    private ArrayList<Prostor> prostoryPapouska;

    /**
     *  Konstruktor — vytvoří batoh a celý herní svět.
     */
    public HerniPlan() {
        batoh = new Batoh();
        random = new Random();
        prostoryPapouska = new ArrayList<>();
        vorPostaven = false;
        konec = false;
        vyhral = false;
        zalozProstoryHry();
    }

    /**
     *  Vytváří jednotlivé prostory, propojuje je východy, umisťuje věci a postavy.
     *  Hráč začíná na pláži.
     */
    private void zalozProstoryHry() {
        // --- vytvoření prostorů ---
        Prostor plaz = new Prostor("plaz", "písečná pláž s troskami lodi");
        Prostor dzungle = new Prostor("dzungle", "hustá tropická džungle");
        Prostor vodopad = new Prostor("vodopad", "vodopád padající do jezírka");
        Prostor jeskyne = new Prostor("jeskyne", "temná jeskyně skrývající kresadlo", true);
        Prostor zricenina = new Prostor("zricenina", "zbytky staré budovy v džungli");
        Prostor vnitrozemi = new Prostor("vnitrozemi", "otevřená planina ve středu ostrova");
        Prostor vulkan = new Prostor("vulkan", "úpatí vyhaslého vulkánu");
        Prostor laguna = new Prostor("laguna", "tichá tyrkysová laguna s palmami");
        Prostor vybezek = new Prostor("vybezek", "skalnatý výběžek s výhledem na ostrov");
        Prostor molo = new Prostor("molo", "staré dřevěné molo, odkud lze vyplout");

        // --- propojení prostorů (obousměrně) ---
        plaz.setVychod(dzungle);
        plaz.setVychod(molo);
        plaz.setVychod(vybezek);
        plaz.setVychod(laguna);

        dzungle.setVychod(plaz);
        dzungle.setVychod(vodopad);
        dzungle.setVychod(vnitrozemi);
        dzungle.setVychod(zricenina);

        vodopad.setVychod(dzungle);
        vodopad.setVychod(jeskyne);

        jeskyne.setVychod(vodopad);

        zricenina.setVychod(dzungle);

        vnitrozemi.setVychod(dzungle);
        vnitrozemi.setVychod(vulkan);

        vulkan.setVychod(vnitrozemi);

        laguna.setVychod(plaz);
        laguna.setVychod(vybezek);

        vybezek.setVychod(plaz);
        vybezek.setVychod(laguna);

        molo.setVychod(plaz);

        // --- prostory, kde se může pohybovat papoušek ---
        prostoryPapouska.add(plaz);
        prostoryPapouska.add(dzungle);
        prostoryPapouska.add(vybezek);
        prostoryPapouska.add(laguna);
        prostoryPapouska.add(vnitrozemi);

        // --- vložení věcí do prostorů ---
        plaz.vlozVec(new Vec("kokos", "zralý kokos plný mléka", 0.5, true));
        plaz.vlozVec(new Vec("trosky", "obrovské trosky lodi, nikam je nedostaneš", 50.0, false));

        dzungle.vlozVec(new Vec("liana", "pevná liána, hodí se jako lano", 1.5, true));
        dzungle.vlozVec(new Vec("kamen", "hladký těžký kámen", 1.0, true));
        dzungle.vlozVec(new Vec("palmovy_list", "suchý palmový list, dobře hoří", 0.2, true));

        vodopad.vlozVec(new Vec("lahev", "skleněná láhev s vodou", 0.3, true));
        jeskyne.vlozVec(new Vec("kresadlo", "kamenné kresadlo na rozdělání ohně", 0.2, true));
        zricenina.vlozVec(new Vec("mapa", "stará mapa ostrova", 0.1, true));
        vnitrozemi.vlozVec(new Vec("drevo", "velká kláda dřeva na stavbu voru", 5.0, true));
        vulkan.vlozVec(new Vec("raketa", "signální raketa, přivolá pomoc", 1.0, true));

        laguna.vlozVec(new Vec("ryby", "čerstvě ulovené ryby", 0.8, true));
        laguna.vlozVec(new Vec("lodka", "malá provrtaná loďka, neplave", 200.0, false));

        vybezek.vlozVec(new Vec("dalekohled", "dalekohled, je přes něj vidět loď", 0.5, true));

        // --- vytvoření postav ---
        papousek = new Postava("papousek", "pestrobarevný mluvicí papoušek", false);
        papousek.pridejDialog("Krkk! Postav vor z dřeva a liány!");
        papousek.pridejDialog("Krkk! Raketa leží na vulkánu!");
        papousek.pridejDialog("Krk! Had se bojí kamenů, hoď po něm!");
        papousek.pridejDialog("Krkk! V jeskyni je tma, vyrob pochodeň z palmového listu a kresadla!");
        papousek.pridejDialog("Krkk! Odpal raketu na molu!");

        had = new Postava("had", "velký had blokující vstup do zříceniny", true);
        had.pridejDialog("Ssssssss... syčí varovně.");

        plaz.vlozPostavu(papousek);
        prostorPapouska = plaz;

        zricenina.vlozPostavu(had);

        // hráč začíná na pláži
        aktualniProstor = plaz;
    }

    /**
     *  Přesune papoušek do náhodného prostoru ze seznamu povolených.
     *  S 50% pravděpodobností zůstane na místě. Volá se po pohybu hráče.
     */
    public void pohniPapouskem() {
        if (random.nextInt(2) == 0) {
            return;
        }
        Prostor novy = prostoryPapouska.get(random.nextInt(prostoryPapouska.size()));
        prostorPapouska.odeberPostavu(papousek);
        novy.vlozPostavu(papousek);
        prostorPapouska = novy;
    }

    /**
     *  Zjistí, zda má hráč k dispozici světlo. Pokud aktuální prostor není temný,
     *  vrací true; jinak hledá pochodeň v batohu.
     *
     *@return    true, pokud hráč vidí
     */
    public boolean maSvetlo() {
        if (!aktualniProstor.jeTemny()) {
            return true;
        }
        return batoh.obsahujeVec("pochoden");
    }

    /**
     *  Zažene hada — od teď už neblokuje vstup do zříceniny.
     */
    public void zazenHada() {
        had.setBlokuje(false);
    }

    /**
     *  Vrací aktuální prostor hráče.
     *
     *@return    aktuální prostor
     */
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    /**
     *  Nastaví aktuální prostor (používá se při přechodu mezi prostory).
     *
     *@param  prostor  nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
        aktualniProstor = prostor;
    }

    /**
     *  Vrací batoh hráče.
     *
     *@return    batoh
     */
    public Batoh getBatoh() {
        return batoh;
    }

    /**
     *  Vrací postavu papoušku.
     *
     *@return    papoušek
     */
    public Postava getPapousek() {
        return papousek;
    }

    /**
     *  Vrací postavu hada.
     *
     *@return    had
     */
    public Postava getHad() {
        return had;
    }

    /**
     *  Zjistí, zda byl vor postaven.
     *
     *@return    true, pokud je vor postaven
     */
    public boolean jeVorPostaven() {
        return vorPostaven;
    }

    /**
     *  Nastaví, že vor byl postaven.
     *
     *@param  vorPostaven  true = vor postaven
     */
    public void setVorPostaven(boolean vorPostaven) {
        this.vorPostaven = vorPostaven;
    }

    /**
     *  Zjistí, zda hra skončila.
     *
     *@return    true, pokud hra skončila
     */
    public boolean jeKonec() {
        return konec;
    }

    /**
     *  Nastaví příznak konce hry.
     *
     *@param  konec  true = hra skončila
     */
    public void setKonec(boolean konec) {
        this.konec = konec;
    }

    /**
     *  Zjistí, zda hráč vyhrál.
     *
     *@return    true, pokud hráč vyhrál
     */
    public boolean jeVyhrana() {
        return vyhral;
    }

    /**
     *  Nastaví příznak výhry.
     *
     *@param  vyhral  true = výhra
     */
    public void setVyhrana(boolean vyhral) {
        this.vyhral = vyhral;
    }
}
