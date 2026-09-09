package com.hand33h.tulostaulu;

/**
 * Verified battle/squad snapshot rules.
 * Sources checked 2026-09-09:
 * Century Games Help Center FAQ 9143, 9148 and 8051.
 *
 * Keep this separate from estimated combat formulas: these are game-behaviour rules,
 * not reverse-engineered damage coefficients.
 */
public final class BattleSnapshotRules {
    private BattleSnapshotRules() {}

    /** During Preparation, changed Chief bonuses recalculate squad power in real time. */
    public static final boolean PREPARATION_BONUSES_RECALCULATE = true;

    /** The values at the end of Preparation become the final squad snapshot. */
    public static final boolean END_OF_PREPARATION_IS_FINAL_SNAPSHOT = true;

    /** If squad capacity changes, troops are redistributed using the original troop ratio. */
    public static final boolean CAPACITY_CHANGE_PRESERVES_TROOP_RATIO = true;

    /** Facility bonus must be active when the squad is set. */
    public static final boolean FACILITY_MUST_BE_ACTIVE_AT_SQUAD_SETUP = true;

    /** Pet skills are active by default; manually activating the same skill does not stack it. */
    public static final boolean PET_SKILLS_ACTIVE_BY_DEFAULT = true;
    public static final boolean MANUAL_PET_SKILL_ACTIVATION_STACKS = false;

    /** Global bonuses from Jeronimo and Natalia apply when owned even if not deployed. */
    public static final boolean JERONIMO_GLOBAL_BONUS_REQUIRES_DEPLOYMENT = false;
    public static final boolean NATALIA_GLOBAL_BONUS_REQUIRES_DEPLOYMENT = false;

    /** Hero skill bonuses are battle-time calculations and are not shown in battle reports. */
    public static final boolean HERO_SKILLS_SHOWN_IN_BATTLE_REPORT = false;

    public static double[] redistributeKeepingRatio(long newCapacity, long infantry, long lancer, long marksman) {
        long total = Math.max(0, infantry) + Math.max(0, lancer) + Math.max(0, marksman);
        if (newCapacity <= 0 || total <= 0) return new double[]{0,0,0};
        return new double[]{
                newCapacity * (double)Math.max(0, infantry) / total,
                newCapacity * (double)Math.max(0, lancer) / total,
                newCapacity * (double)Math.max(0, marksman) / total
        };
    }

    public static final String SOURCE_BATTLEFIELD_BONUSES =
            "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/9143-which-bonuses-take-effect-on-the-battlefield/";
    public static final String SOURCE_SQUAD_RECALCULATION =
            "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/9148-if-i-upgrade-chief-charms-chief-gear-hero-gear-after-setting-my-squad-will-it-affect-my-squad/";
    public static final String SOURCE_REPORT_BONUSES =
            "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/8051-what-special-bonuses-are-shown-in-battle-reports/";
}
