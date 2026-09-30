# Uživatelská příručka — Ztroskotání na ostrově

## Cíl hry
Tvá loď ztroskotala. Záchranáři tě najdou jen když:
1. Postavíš vor z dřeva a liány
2. Najdeš signální raketu na vulkánu
3. Odpálíš raketu z mola

## Spuštění
```bash
./run.sh                # interaktivní
./run.sh hra.txt        # auto-play
```

## Příkazy
| Příkaz | Syntaxe | Popis |
|--------|---------|-------|
| `jdi` | `jdi <prostor>` | Přesun do sousedního prostoru |
| `seber` | `seber <věc>` | Sebere věc do batohu |
| `poloz` | `poloz <věc>` | Položí věc z batohu do prostoru |
| `inventar` | `inventar` | Obsah batohu |
| `vypis` | `vypis prostor` / `vypis batoh` | Podrobný výpis |
| `prozkoumej` | `prozkoumej` | Popis aktuálního prostoru |
| `promluv` | `promluv <jméno>` | Mluví s postavou |
| `postav` | `postav` | Postaví vor (drevo + liana) |
| `odpali` | `odpali` | Odpálí raketu z mola |
| `hod` | `hod <věc> <cíl>` | Hod (např. `hod kamen had`) |
| `vyrob` | `vyrob pochoden` | Vyrobí pochodeň |
| `nápověda` | `nápověda` | Nápověda |
| `konec` | `konec` | Ukončí hru |

## Batoh
Max nosnost 10 kg. Sleduj `inventar`.

## Tipy
- `jeskyne` je temná — vyrob `pochoden` (palmovy_list + kresadlo)
- `had` blokuje `zricenina` — zažeň ho `hod kamen had`
- `papousek` se pohybuje — `promluv papousek` dá tip
