package com.hand33h.tulostaulu;

/** Publicly verified progression/mechanics facts used by the battle-data validator. */
public final class PublicHeroProgressionData {
    public static final int HERO_MAX_LEVEL = 80;
    public static final int HERO_UNLOCK_FURNACE_LEVEL = 4;
    public static final int HERO_LEVEL_80_FURNACE_LEVEL = 26;
    public static final int HERO_GEAR_UNLOCK_FURNACE_LEVEL = 15;
    public static final int MYTHIC_EXPEDITION_SKILLS = 3;
    public static final int EPIC_EXPEDITION_SKILLS = 2;
    public static final int RARE_EXPEDITION_SKILLS = 2;

    // WhiteoutData Hero XP chart: early level -> troop capacity.
    private static final int[] TROOP_CAPACITY_L1_L8 = {65,140,220,305,400,500,605,720};

    // WhiteoutData Hero Gear XP chart, enhancement XP needed for levels 1..100.
    private static final int[] HERO_GEAR_XP_L1_L100 = {
        10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100,105,
        110,115,120,125,130,135,140,145,150,160,170,180,190,200,210,220,230,240,250,270,
        290,310,330,350,370,390,410,430,450,470,490,510,530,550,570,590,610,630,650,680,
        710,740,770,800,830,860,890,920,950,990,1030,1070,1110,1150,1190,1230,1270,1310,1350,1400,
        1450,1500,1550,1600,1650,1700,1750,1800,1850,1900,1950,2000,2050,2100,2150,2200,2250,2300,2350,2400
    };

    public static int expeditionSkillCountForRarity(String rarity) {
        if (rarity == null) return 0;
        if ("MYTHIC".equalsIgnoreCase(rarity)) return MYTHIC_EXPEDITION_SKILLS;
        if ("EPIC".equalsIgnoreCase(rarity)) return EPIC_EXPEDITION_SKILLS;
        if ("RARE".equalsIgnoreCase(rarity)) return RARE_EXPEDITION_SKILLS;
        return 0;
    }

    public static int troopCapacityAtEarlyLevel(int heroLevel) {
        return heroLevel >= 1 && heroLevel <= TROOP_CAPACITY_L1_L8.length ? TROOP_CAPACITY_L1_L8[heroLevel - 1] : -1;
    }

    public static int heroGearXpForLevel(int gearLevel) {
        return gearLevel >= 1 && gearLevel <= HERO_GEAR_XP_L1_L100.length ? HERO_GEAR_XP_L1_L100[gearLevel - 1] : -1;
    }

    /** Known source conflict: Dragonbreath description says Defender Attack, preview says Defense. */
    public static boolean isKnownConflictingSkill(String hero, String skill) {
        return hero != null && skill != null && "Flint".equalsIgnoreCase(hero) && "Dragonbreath".equalsIgnoreCase(skill);
    }

    public static final String HERO_XP_SOURCE = "https://whiteoutdata.com/data/hero-xp-chart/";
    public static final String HERO_GEAR_XP_SOURCE = "https://whiteoutdata.com/data/hero-gear-xp-chart/";
    public static final String EXPEDITION_SOURCE = "https://whiteoutdata.com/others/exploration-vs-expedition/";
    public static final String FLINT_SOURCE = "https://whiteoutdata.com/heroes/generation-2-heroes/";

    private PublicHeroProgressionData() {}
}
