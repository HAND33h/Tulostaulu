package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verified high-level Chief Charm data for the current 2026 progression.
 *
 * Cross-checked 2026-09-10 against current WOS Forge and recent 2026 progression guides.
 * - Levels 12-16 unlock with Gen 5 state progression.
 * - Levels 17-18 unlock with Gen 8 state progression; each has 9 sub-stages.
 * Costs below are totals to complete one charm's full level, not per sub-stage.
 */
public final class VerifiedChiefCharmLevels2026 {
    public static final int CHARM_SECRETS_START_LEVEL = 12;
    public static final int LEVEL_12_TO_16_UNLOCK_HERO_GENERATION = 5;
    public static final int LEVEL_17_TO_18_UNLOCK_HERO_GENERATION = 8;

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
        new Row(12, 580, 450, 15, 64.0, 1536000, 5, 5),
        new Row(13, 580, 450, 30, 73.0, 1752000, 5, 5),
        new Row(14, 600, 500, 45, 82.0, 1968000, 5, 5),
        new Row(15, 600, 500, 70, 91.0, 2184000, 5, 5),
        new Row(16, 650, 550, 100, 100.0, 2400000, 9, 5),
        new Row(17, 765, 630, 135, 109.0, 2616000, 9, 8),
        new Row(18, 1300, 1130, 180, 118.0, 2832000, 9, 8)
    ));

    /**
     * Returns whether the 2026 progression sources verify the requested high charm level
     * as available for the supplied state hero generation. Levels below 12 remain owned
     * by the app's normal early-progression logic.
     */
    public static boolean isVerifiedHighLevelUnlocked(int charmLevel, int unlockedHeroGeneration) {
        if (charmLevel >= 12 && charmLevel <= 16) {
            return unlockedHeroGeneration >= LEVEL_12_TO_16_UNLOCK_HERO_GENERATION;
        }
        if (charmLevel == 17 || charmLevel == 18) {
            return unlockedHeroGeneration >= LEVEL_17_TO_18_UNLOCK_HERO_GENERATION;
        }
        return false;
    }

    private VerifiedChiefCharmLevels2026() {}
}
