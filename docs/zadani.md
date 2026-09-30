# Zadání — Ztroskotání na ostrově

## Popis hry
Textová adventura. Hráč ztroskotal na ostrově a musí se z něj dostat:
postavit vor, najít signální raketu a odpálit ji z mola.

## Balíčky
- `cz.vse.adventura` — třída Start (spuštění)
- `cz.vse.adventura.logika` — herní logika (Hra, HerniPlan, Prostor, Vec, Batoh, Postava, příkazy)
- `cz.vse.adventura.uiText` — textové uživatelské rozhraní

## Prostory (10)
plaz (start), dzungle, vodopad, jeskyne (temný), zricenina (blokuje had),
vnitrozemi, vulkan, laguna, vybezek, molo (cíl)

```
                  vulkan
                    |
                vnitrozemi
                    |
   zricenina --- dzungle --- vodopad --- jeskyne (tma)
                    |
                  plaz
                /  |  \
          vybezek molo laguna --- vybezek
```

## Věci
| Název | Hmotnost (kg) | Lze sebrat |
|-------|---------------|------------|
| kokos | 0.5 | ano |
| trosky | 50 | ne |
| liana | 1.5 | ano |
| kamen | 1.0 | ano |
| palmovy_list | 0.2 | ano |
| lahev | 0.3 | ano |
| kresadlo | 0.2 | ano |
| mapa | 0.1 | ano |
| drevo | 5.0 | ano |
| raketa | 1.0 | ano |
| ryby | 0.8 | ano |
| lodka | 200 | ne |
| dalekohled | 0.5 | ano |
| pochoden | 0.3 | ano (vyrobí se) |

Batoh unese maximálně 10 kg.

## Příkazy
- z kostry: `jdi`, `konec`, `nápověda`, `pomoc`
- nové: `seber`, `poloz`, `inventar`, `vypis`, `prozkoumej`, `promluv`,
  `postav`, `odpali`, `hod`, `vyrob`

## Postavy
- **papousek** — pohybuje se po ostrově, po `promluv papousek` dá tip
- **had** — blokuje vstup do zříceniny, zažene se příkazem `hod kamen had`

## Zvláštní mechaniky
- **temná jeskyně** — bez pochodně nic nevidíš; pochodeň vyrobíš
  příkazem `vyrob pochoden` (z palmovy_list + kresadlo)
- **vor** — postaví se z `drevo` + `liana` příkazem `postav`

## Vítězná podmínka
Mít v batohu drevo + liana → `postav`. Pak seber raketu na vulkánu,
dojdi na molo a zadej `odpali`.

## Testy (JUnit 5)
- **HraTest** — start a konec, celá vítězná sekvence, blokování hadem,
  stavba bez materiálu, odpálení na špatném místě, neznámý příkaz
- **BatohTest** — hmotnostní limit 10 kg, sebrání (ne)přenositelné věci
- **ProstorTest** — průchody mezi prostory
- **SeznamPrikazuTest** — vkládání a hledání příkazů
