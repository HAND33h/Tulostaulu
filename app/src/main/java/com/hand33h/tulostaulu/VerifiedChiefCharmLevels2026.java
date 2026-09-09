package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verified high-level Chief Charm data for the current 2026 progression.
 *
 * Re-audited 2026-09-10 against WOS Forge, WoS Guru, Heaven Guardian,
 * WhiteoutSurvival.dev and current community references.
 *
 * Stable consensus:
 * - Levels 12+ require Charm Secrets.
 * - Lv12-15 have 5 sub-stages; Lv16-18 have 9 sub-stages.
 * - Levels 17-18 unlock with Gen 8 state progression.
 * - Material costs/stat totals below are cross-checked across current sources.
 *
 * Progression conflict intentionally preserved:
 * - Some recent 2026 sources place Lv12-16 at Gen5.
 * - Other current references describe later state gating for the highest levels.
 * Therefore this class does NOT auto-unlock Lv12-16 from a disputed generation gate.
 *
 * Power note:
 * - Lv12-16 values use the 124,000-per-level progression corroborated by
 *   WhiteoutSurvival.dev, Heaven Guardian and current community tables.
 * - WOS Forge currently publishes higher Lv12-16 power values; that conflict is
 *   documented rather than silently accepted.
 */
public final class VerifiedChiefCharmLevels2026 {
    public static final int CHARM_SECRETS_START_LEVEL = 12;
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

    // unlockHeroGeneration == -1 means current public sources conflict on the exact gate.
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row(12, 580, 450, 15, 64.0, 1444000, 5, -1),
        new Row(13, 580, 450, 30, 73.0, 1568000, 5, -1),
        new Row(14, 600, 500, 45, 82.0, 1692000, 5, -1),
        new Row(15, 600, 500, 70, 91.0, 1816000, 5, -1),
        new Row(16, 650, 550, 100, 100.0, 1940000, 9, -1),
        new Row(17, 765, 630, 135, 109.0, 2616000, 9, 8),
        new Row(18, 1300, 1130, 180, 118.0, 2832000, 9, 8)
    ));

    /** Returns true only for a generation gate that is non-conflicting in current sources. */
    public static boolean isVerifiedHighLevelUnlocked(int charmLevel, int unlockedHeroGeneration) {
        if (charmLevel == 17 || charmLevel == 18) {
            return unlockedHeroGeneration >= LEVEL_17_TO_18_UNLOCK_HERO_GENERATION;
        }
        return false;
    }

    public static boolean hasProgressionSourceConflict(int charmLevel) {
        return charmLevel >= 12 && charmLevel <= 16;
    }

    private VerifiedChiefCharmLevels2026() {}
}
