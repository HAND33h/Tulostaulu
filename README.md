# Bunny King / WOS Tulostaulu

HAND33h:n Android-projekti Whiteout Survivalin rankingien, sankaritietojen, taistelumallin ja kehitysplannerien hallintaan.

Jatkokehityksen päämoduuli on `app` (`com.hand33h.tulostaulu`). `android-bunny` on erillinen suppeampi projekti eikä korvaa päämoduulin ominaisuuksia.

## Käyttö

State valitaan itse. Uuden käyttäjän kenttä on tyhjä; aiemmin tallennettu oma valinta säilyy. Ranking-rekisteriä täydentävät FID-haut ja kuvakaappausten OCR-tuonti. WOS Controlin leaderboardia ei käsitellä automaattisesti koko staten pelaajalistana.

Battle Simulatorissa molemmat puolet alkavat samoista arvoista. Kopiointipainike siirtää koko hyökkääjän kokoonpanon puolustajalle, jonka jälkeen arvoja voi muuttaa. Prosenttitulos tarkoittaa mallin pisteosuutta; sitä ei ole kalibroitu voittotodennäköisyydeksi.

Koodissa on sankaritietoja, Gear-, Charm-, Pet- ja Expert-osuuksia, raporttien käsittelyä, XLSX-vienti sekä foorumin ja API-yhteyksien osia. Ominaisuuden löytyminen koodista ei yksin vahvista verkkopalvelun tai laskennan toimivuutta.

## Rakentaminen

JDK 17, Gradle 8.9 ja Android SDK 35: `gradle assembleDebug`. GitHub Actionsin APK-työnkulut ovat `.github/workflows`-hakemistossa.

## Syötteiden regressiotestit

```sh
mkdir -p /tmp/wos-validation
javac -d /tmp/wos-validation app/src/main/java/com/hand33h/tulostaulu/BattleInputValidation.java tests/BattleInputValidationTest.java
java -cp /tmp/wos-validation BattleInputValidationTest
```

Katso [9.10.2026 muutokset](CHANGELOG-2026-10-09.md).

Powered by [WOS Control](https://woscontrol.com/) • Owned by HAND33h
