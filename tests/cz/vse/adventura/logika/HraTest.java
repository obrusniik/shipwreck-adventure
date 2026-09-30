/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventura.logika;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HraTest {
    private Hra hra1;

    /**
     *  Před každým testem vytvoří novou hru (každá hra má svůj prázdný batoh).
     */
    @BeforeEach
    public void setUp() {
        hra1 = new Hra();
    }

    /**
     *  Úklid po testu.
     */
    @AfterEach
    public void tearDown() {
    }

    /**
     *  Testuje startovní polohu, jednoduchý přesun a příkaz konec.
     */
    @Test
    public void testStartAKonec() {
        assertEquals("plaz", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi dzungle");
        assertFalse(hra1.konecHry());
        assertEquals("dzungle", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("konec");
        assertTrue(hra1.konecHry());
    }

    /**
     *  Testuje kompletní vítěznou sekvenci hry:
     *  plaz → dzungle → seber liana → vnitrozemi → seber drevo → postav
     *  → vulkan → seber raketa → zpět na molo → odpali → výhra.
     */
    @Test
    public void testViteznaSekvence() {
        hra1.zpracujPrikaz("jdi dzungle");
        assertEquals("dzungle", hra1.getHerniPlan().getAktualniProstor().getNazev());

        hra1.zpracujPrikaz("seber liana");
        assertTrue(hra1.getHerniPlan().getBatoh().obsahujeVec("liana"));

        hra1.zpracujPrikaz("jdi vnitrozemi");
        assertEquals("vnitrozemi", hra1.getHerniPlan().getAktualniProstor().getNazev());

        hra1.zpracujPrikaz("seber drevo");
        assertTrue(hra1.getHerniPlan().getBatoh().obsahujeVec("drevo"));

        hra1.zpracujPrikaz("postav");
        assertTrue(hra1.getHerniPlan().jeVorPostaven());

        hra1.zpracujPrikaz("jdi vulkan");
        hra1.zpracujPrikaz("seber raketa");
        assertTrue(hra1.getHerniPlan().getBatoh().obsahujeVec("raketa"));

        hra1.zpracujPrikaz("jdi vnitrozemi");
        hra1.zpracujPrikaz("jdi dzungle");
        hra1.zpracujPrikaz("jdi plaz");
        hra1.zpracujPrikaz("jdi molo");

        hra1.zpracujPrikaz("odpal");
        assertTrue(hra1.konecHry(), "Po úspěšném odpálení musí hra skončit.");
        assertTrue(hra1.getHerniPlan().jeVyhrana(), "Hráč musí být označen jako vítěz.");
    }

    /**
     *  Testuje, že stavba voru bez materiálů selže.
     */
    @Test
    public void testPostavBezMaterialu() {
        String odpoved = hra1.zpracujPrikaz("postav");
        assertFalse(hra1.getHerniPlan().jeVorPostaven());
        assertTrue(odpoved.contains("potřebuješ") || odpoved.contains("chybí"));
    }

    /**
     *  Testuje, že příkaz odpali selže na špatném místě.
     */
    @Test
    public void testOdpaliNaSpatnemMiste() {
        String odpoved = hra1.zpracujPrikaz("odpal");
        assertFalse(hra1.konecHry());
        assertTrue(odpoved.contains("molo") || odpoved.contains("mola"));
    }

    /**
     *  Testuje, že neznámý příkaz nezpůsobí pád hry.
     */
    @Test
    public void testNeznámyPrikaz() {
        String odpoved = hra1.zpracujPrikaz("abrakadabra");
        assertFalse(hra1.konecHry());
        assertTrue(odpoved.toLowerCase().contains("nevím") || odpoved.toLowerCase().contains("neznám"));
    }

    /**
     *  Testuje, že had blokuje vstup do zříceniny dokud není zahnán.
     */
    @Test
    public void testHadBlokujeZriceninu() {
        hra1.zpracujPrikaz("jdi dzungle");
        String odpoved = hra1.zpracujPrikaz("jdi zricenina");
        assertTrue(odpoved.toLowerCase().contains("had"),
                "Cesta do zříceniny má být blokována hadem.");
        assertEquals("dzungle", hra1.getHerniPlan().getAktualniProstor().getNazev());
    }
}
