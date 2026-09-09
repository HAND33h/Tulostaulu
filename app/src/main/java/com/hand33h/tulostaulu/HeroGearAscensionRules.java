package com.hand33h.tulostaulu;

/**
 * Official Hero Gear Ascension / Empowerment gates.
 * Source checked 2026-09-09: Century Games Help Center.
 * No community-derived combat percentages are auto-applied here.
 */
public final class HeroGearAscensionRules {
    private HeroGearAscensionRules() {}

    public static final int ASCENSION_REQUIRED_ENHANCEMENT = 100;
    public static final int ASCENSION_REQUIRED_MASTERY = 10;
    public static final int MAX_ENHANCEMENT_AFTER_ASCENSION = 100;
    public static final int MAX_EMPOWERMENTS = 5;
    private static final int[] EMPOWERMENT_LEVELS = {20, 40, 60, 80, 100};

    /** Server age is an additional official gate, but Century Games does not publish one universal day here. */
    public static boolean canAscend(int enhancementLevel, int masteryLevel, boolean serverAgeGateOpen) {
        return serverAgeGateOpen
                && enhancementLevel >= ASCENSION_REQUIRED_ENHANCEMENT
                && masteryLevel >= ASCENSION_REQUIRED_MASTERY;
    }

    /** Empowerment requires Mythic gear to have already been ascended to Legendary. */
    public static boolean canEmpower(boolean legendaryAfterAscension, int enhancementLevel, int empowermentCount) {
        if (!legendaryAfterAscension || empowermentCount < 0 || empowermentCount >= MAX_EMPOWERMENTS) return false;
        return enhancementLevel == EMPOWERMENT_LEVELS[empowermentCount];
    }

    public static int nextEmpowermentLevel(int empowermentCount) {
        if (empowermentCount < 0 || empowermentCount >= MAX_EMPOWERMENTS) return -1;
        return EMPOWERMENT_LEVELS[empowermentCount];
    }

    public static boolean requiresMithrilForEmpowerment() { return true; }
    public static boolean requiresMythicGearForEmpowerment() { return true; }
}
