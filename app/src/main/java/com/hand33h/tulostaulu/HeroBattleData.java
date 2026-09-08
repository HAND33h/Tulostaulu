package com.hand33h.tulostaulu;

/**
 * Whiteout Survival hero roster metadata used by Battle Simulator.
 * Roster verified against WOS Forge Heroes list (Gen 0..17, Sep 2026).
 * Exact Expedition/Special Weapon numeric effects are intentionally NOT guessed here;
 * BattleSimulatorActivity only applies user-entered/verified hero bonus values until
 * per-hero tables are populated from verified source data.
 */
public final class HeroBattleData {
    private HeroBattleData() {}

    public static final String[] HEROES = {
        "None",
        "Ahmose — Gen 4 — Infantry","Aiden — Gen 17 — Infantry","Aisling — Gen 16 — Marksman","Alonso — Gen 2 — Marksman",
        "Bahiti — Gen 0 — Marksman","Bertha — Gen 17 — Lancer","Blanchette — Gen 10 — Marksman","Bradley — Gen 7 — Marksman",
        "Cara — Gen 14 — Marksman","Charlie — Gen 0 — Lancer","Cloris — Gen 0 — Marksman","Dominic — Gen 14 — Lancer",
        "Edith — Gen 7 — Infantry","Eleanor — Gen 17 — Marksman","Elif — Gen 14 — Infantry","Eleonora — Gen 11 — Infantry",
        "Estrella — Gen 15 — Lancer","Eugene — Gen 0 — Infantry","Flint — Gen 2 — Infantry","Flora — Gen 13 — Lancer",
        "Fred — Gen 9 — Lancer","Freya — Gen 10 — Lancer","Gatot — Gen 8 — Infantry","Gina — Gen 0 — Marksman",
        "Gisela — Gen 13 — Infantry","Gordon — Gen 7 — Lancer","Greg — Gen 3 — Marksman","Gregory — Gen 10 — Infantry",
        "Gwen — Gen 5 — Marksman","Hank — Gen 15 — Infantry","Hector — Gen 5 — Infantry","Hendrik — Gen 8 — Marksman",
        "Hervor — Gen 12 — Infantry","Jasser — Gen 0 — Marksman","Jeronimo — Gen 1 — Infantry","Jessie — Gen 0 — Lancer",
        "Karol — Gen 12 — Lancer","Ligeia — Gen 12 — Marksman","Ling Xue — Gen 0 — Lancer","Lloyd — Gen 11 — Lancer",
        "Logan — Gen 3 — Infantry","Lumak Bokan — Gen 0 — Lancer","Lynn — Gen 4 — Marksman","Magnus — Gen 9 — Infantry",
        "Mia — Gen 3 — Lancer","Molly — Gen 1 — Lancer","Natalia — Gen 1 — Infantry","Norah — Gen 5 — Lancer",
        "Patrick — Gen 0 — Lancer","Philly — Gen 2 — Lancer","Reina — Gen 4 — Lancer","Renee — Gen 6 — Lancer",
        "Rufus — Gen 11 — Marksman","Seigel — Gen 16 — Infantry","Seo-yoon — Gen 0 — Marksman","Sergey — Gen 0 — Infantry",
        "Smith — Gen 0 — Infantry","Sonya — Gen 8 — Lancer","Ursar — Gen 16 — Lancer","Viveca — Gen 15 — Marksman",
        "Vulcanus — Gen 13 — Marksman","Wayne — Gen 6 — Marksman","Wu Ming — Gen 6 — Infantry","Xura — Gen 9 — Marksman",
        "Zinman — Gen 1 — Marksman"
    };

    public static final String[] SKILL_LEVELS = {"1","2","3","4","5"};
    public static final String[] EXCLUSIVE_LEVELS = {"0","1","2","3","4","5","6","7","8","9","10"};
    public static final String[] HERO_GEAR_LEVELS = buildLevels(200);
    public static final String[] HERO_GEAR_SLOTS = {"Headgear / Goggles","Gloves","Belt","Boots"};

    private static String[] buildLevels(int max) {
        String[] out = new String[max + 1];
        for (int i = 0; i <= max; i++) out[i] = String.valueOf(i);
        return out;
    }
}
