package com.hand33h.tulostaulu;

/**
 * Verified current progression caps after the August 2026 rollout adjustment.
 * Cross-checked 2026-09-10 against current post-adjustment public sources.
 *
 * These are state-progression gated unlocks, not universal day-one caps:
 * - Mythic T6 Chief Gear (3-Star) unlocks after state progress has Gen 6 heroes.
 * - Chief Charm Lv.17 and Lv.18 unlock once state progress has Gen 8 heroes.
 * - Lv.17 and Lv.18 each contain 9 sub-stages.
 *
 * Important chronology: the original August preview proposed earlier Gen4/Gen5
 * unlock timing, but the developer subsequently postponed these ceilings to Gen6/Gen8.
 */
public final class VerifiedProgressionCaps2026 {
    public static final String CHIEF_GEAR_MAX_TIER = "MYTHIC_T6";
    public static final int CHIEF_GEAR_MAX_STARS = 3;
    public static final int CHIEF_GEAR_UNLOCK_HERO_GENERATION = 6;

    public static final int CHIEF_CHARM_MAX_LEVEL = 18;
    public static final int CHIEF_CHARM_HIGH_LEVEL_SUBSTAGES = 9;
    public static final int CHIEF_CHARM_HIGH_LEVEL_START = 17;
    public static final int CHIEF_CHARM_UNLOCK_HERO_GENERATION = 8;

    public static boolean supportsChiefGearT6ThreeStar(int unlockedHeroGeneration) {
        return unlockedHeroGeneration >= CHIEF_GEAR_UNLOCK_HERO_GENERATION;
    }

    public static boolean supportsCharmLevel18(int unlockedHeroGeneration) {
        return unlockedHeroGeneration >= CHIEF_CHARM_UNLOCK_HERO_GENERATION;
    }

    private VerifiedProgressionCaps2026() {}
}
