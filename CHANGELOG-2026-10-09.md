# Bunny King – 9.10.2026

Jatketaan HAND33h/Tulostaulu-projektin laajaa app-moduulia. Alkuperäisiä APK:ita ei muutettu.

## Muutokset

1. Hyökkääjällä ja puolustajalla samat lähtöarvot vertailun pohjaksi.
2. Kopiointipainike siirtää joukkomäärät, jakaumat, prosentit, sankarit, skill-tasot, exclusive-aseet, Hero Gearin, Chief Gearin, kaikki 18 charmia, Petin ja Expertin puolustajalle.
3. Puuttuvat ja ei-äärelliset numerot hylätään kentän nimellä; desimaalipilkku toimii.
4. Joukkomäärä vaatii positiivisen kokonaisluvun; jokainen joukko-osuus on 0–100 ja summa 100 %. Negatiiviset syötettävät bonukset hylätään.
5. Tulos ilmoittaa mallin pisteosuuden, ei kalibroimatonta voittotodennäköisyyttä. Pisteosuuden laskenta kestää myös erittäin suurten äärellisten lukujen summan ylivuodon.
6. Oletusserveri poistettu kaikista state-kentistä. Uuden käyttäjän kenttä on tyhjä; aiemmin tallennettu oma valinta säilyy. Tyhjä state estää ranking-haun ja OCR-tuonnin.

Sankarien ability-prosentteja, ehdollisia efektejä ja tietotaulukoita ei muutettu. Rally leader + neljä joineria on yhä erikseen tarkistettava ja kytkettävä käyttöliittymään; sitä ei väitetä tässä toteutetuksi.

## Tarkistus

18 syöte- ja pisteosuusregressiotestiä läpäisi. BattleSimulatorActivity ja sen lähdekoodiriippuvuudet kääntyivät Android 35 -rajapintaa vasten. Tämä ei ole koko APK-build eikä laitetesti.

## Palautettavuus

Muutos omalla Git-haaralla. Alkuperäiset APK:t ovat aiemmassa palautuspaketissa. Lähdekoodirepo on ensisijainen jatkokehityksen pohja, ei aiempi riisuttu android-bunny-moduuli.
