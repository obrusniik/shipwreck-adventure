/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BatohTest {

    private Batoh batoh;

    /**
     *  Před každým testem vytvoří nový prázdný batoh.
     */
    @BeforeEach
    public void setUp() {
        batoh = new Batoh();
    }

    /**
     *  Testuje, že nový batoh je prázdný.
     */
    @Test
    public void testPrazdny() {
        assertEquals(0.0, batoh.getAktualniHmotnost(), 0.001);
        assertTrue(batoh.getSeznam().isEmpty());
    }

    /**
     *  Testuje přidání věci do batohu.
     */
    @Test
    public void testPridaniVeci() {
        Vec kokos = new Vec("kokos", "kokos", 0.5, true);
        assertTrue(batoh.pridejVec(kokos));
        assertTrue(batoh.obsahujeVec("kokos"));
        assertEquals(0.5, batoh.getAktualniHmotnost(), 0.001);
    }

    /**
     *  Testuje, že batoh nepřijme věc, která by přesáhla max. nosnost (10 kg).
     *  drevo 5 kg + drevo 5 kg = 10 kg (OK), pak +liana 1.5 kg = přesah.
     */
    @Test
    public void testPrekroceniHmotnosti() {
        Vec drevo1 = new Vec("drevo", "dřevo", 5.0, true);
        Vec drevo2 = new Vec("drevo", "dřevo", 5.0, true);
        Vec liana = new Vec("liana", "liána", 1.5, true);
        assertTrue(batoh.pridejVec(drevo1));
        assertTrue(batoh.pridejVec(drevo2));
        assertFalse(batoh.pridejVec(liana), "Liana by přesáhla limit 10 kg.");
        assertEquals(10.0, batoh.getAktualniHmotnost(), 0.001);
    }

    /**
     *  Testuje, že přes příkaz "seber" nelze sebrat nepřenositelný předmět (trosky).
     */
    @Test
    public void testSeberNeprenositelne() {
        Hra hra = new Hra();
        Batoh batohHry = hra.getHerniPlan().getBatoh();
        String odpoved = hra.zpracujPrikaz("seber trosky");
        assertFalse(batohHry.obsahujeVec("trosky"));
        assertTrue(odpoved.toLowerCase().contains("nelze")
                || odpoved.toLowerCase().contains("těžk"));
    }

    /**
     *  Testuje, že přes příkaz "seber" lze sebrat běžný předmět (kokos).
     */
    @Test
    public void testSeberPrenositelne() {
        Hra hra = new Hra();
        Batoh batohHry = hra.getHerniPlan().getBatoh();
        hra.zpracujPrikaz("seber kokos");
        assertTrue(batohHry.obsahujeVec("kokos"));
    }

    /**
     *  Testuje odebrání věci z batohu.
     */
    @Test
    public void testOdebrani() {
        Vec kamen = new Vec("kamen", "kámen", 1.0, true);
        batoh.pridejVec(kamen);
        Vec odebrany = batoh.odeberVec("kamen");
        assertNotNull(odebrany);
        assertFalse(batoh.obsahujeVec("kamen"));
    }
}
