package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verified high-level Chief Charm data for the current 2026 progression.
 *
 * Cross-checked 2026-09-10 against current WOS Forge and WoS Guru data.
 * Levels 17 and 18 unlock with Gen 8 state progression and each contain 9 sub-stages.
 * Costs below are totals to complete one charm's full level, not per sub-stage.
 *
 * Important: this class only owns the verified high-level 17-18 gate. It must not
 * claim that every level <=16 is universally unlocked, because lower high-level
 * charm caps are also state-progression gated in the live game.
 */
public final class VerifiedChiefCharmLevels2026 {
    public static final class Row {
        public final int level;
        public final int charmGuide;
        public final int charmDesign;
        public final int charmSecrets;
        public final double healthAndLethalityPercent;
        public final int power;
        public final int subStages;
        public final int unlockHeroGeneration;

        Row(int level, int guide, int design, int secrets, double stats, int power, int subStages, int unlockGen) {
            this.level = level;
            this.charmGuide = guide;
            this.charmDesign = design;
            this.charmSecrets = secrets;
            this.healthAndLethalityPercent = stats;
            this.power = power;
            this.subStages = subStages;
            this.unlockHeroGeneration = unlockGen;
        }
    }

    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row(17, 765, 630, 135, 109.0, 2616000, 9, 8),
        new Row(18, 1300, 1130, 180, 118.0, 2832000, 9, 8)
    ));

    /**
     * Returns whether this high-level data set verifies the requested level as
     * available for the supplied state hero generation. Lower levels are left
     * to the app's normal progression table instead of being assumed unlocked.
     */
    public static boolean isHighLevelUnlocked(int charmLevel, int unlockedHeroGeneration) {
        if (charmLevel == 17 || charmLevel == 18) {
            return unlockedHeroGeneration >= 8;
        }
        return false;
    }

    private VerifiedChiefCharmLevels2026() {}
}
