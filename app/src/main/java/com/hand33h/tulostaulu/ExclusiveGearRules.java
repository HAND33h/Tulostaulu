package com.hand33h.tulostaulu;

/**
 * Publicly cross-checked Exclusive Hero Gear / Widget progression rules.
 * These are progression mechanics, not an attempt to reverse-engineer the combat formula.
 * Sources checked 2026-09-09:
 * - https://wosguru.com/hero-gear
 * - hero-specific public tables (wos.h5joy-games.com)
 */
public final class ExclusiveGearRules {
    private ExclusiveGearRules() {}

    public static final int MIN_LEVEL = 0;
    public static final int MAX_LEVEL = 10;
    public static final int WIDGETS_PER_LEVEL = 5;

    /** Exploration exclusive skill is available at craft/L1 and improves at odd gear levels. */
    public static int explorationSkillTier(int gearLevel) {
        int l = clamp(gearLevel);
        if (l < 1) return 0;
        return Math.min(5, (l + 1) / 2);
    }

    /** Expedition exclusive skill unlocks at L2 and improves at even gear levels. */
    public static int expeditionSkillTier(int gearLevel) {
        int l = clamp(gearLevel);
        if (l < 2) return 0;
        return Math.min(5, l / 2);
    }

    /** Public widget charts use 5/10/.../50 widgets for levels 1..10. */
    public static int widgetsForLevel(int gearLevel) {
        int l = clamp(gearLevel);
        return l * WIDGETS_PER_LEVEL;
    }

    /** Cumulative widgets from L0 through target level: 5*(1+...+L). */
    public static int cumulativeWidgetsToReach(int gearLevel) {
        int l = clamp(gearLevel);
        return WIDGETS_PER_LEVEL * l * (l + 1) / 2;
    }

    public static boolean isExplorationUpgradeLevel(int gearLevel) {
        int l = clamp(gearLevel);
        return l >= 1 && (l & 1) == 1;
    }

    public static boolean isExpeditionUpgradeLevel(int gearLevel) {
        int l = clamp(gearLevel);
        return l >= 2 && (l & 1) == 0;
    }

    private static int clamp(int level) {
        return Math.max(MIN_LEVEL, Math.min(MAX_LEVEL, level));
    }
}
