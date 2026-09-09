package com.hand33h.tulostaulu;

/**
 * Verified battle/squad snapshot and Expedition rules.
 * Sources checked 2026-09-09: Century Games Help Center FAQs 8048, 9143, 9148, 9149 and 8051.
 * These are game-behaviour rules, not reverse-engineered damage coefficients.
 */
public final class BattleSnapshotRules {
    private BattleSnapshotRules() {}

    public static final boolean PREPARATION_BONUSES_RECALCULATE = true;
    public static final boolean END_OF_PREPARATION_IS_FINAL_SNAPSHOT = true;
    public static final boolean POST_SNAPSHOT_GEAR_CHANGES_APPLY = false;
    public static final boolean CAPACITY_CHANGE_PRESERVES_TROOP_RATIO = true;
    public static final boolean FACILITY_MUST_BE_ACTIVE_AT_SQUAD_SETUP = true;

    public static final boolean PET_SKILLS_ACTIVE_BY_DEFAULT = true;
    public static final boolean MANUAL_PET_SKILL_ACTIVATION_STACKS = false;
    public static final boolean JERONIMO_GLOBAL_BONUS_REQUIRES_DEPLOYMENT = false;
    public static final boolean NATALIA_GLOBAL_BONUS_REQUIRES_DEPLOYMENT = false;
    public static final boolean HERO_SKILLS_SHOWN_IN_BATTLE_REPORT = false;

    // Official Expedition mechanics.
    public static final boolean EXPEDITION_IS_TURN_BASED = true;
    public static final boolean BOTH_SIDES_ATTACK_SIMULTANEOUSLY_PER_TURN = true;
    public static final boolean HERO_SKILLS_REQUIRE_MATCHING_TROOP_TYPE = false;
    public static final boolean HERO_SKILLS_INDEPENDENT_OF_REPORT_STATS = true;
    public static final int RALLY_LEADER_HERO_SKILLS = 9;
    public static final int MAX_MEMBER_PRIMARY_HERO_SKILLS = 4;
    public static final double CITY_ATTACKER_CASUALTY_DEATH_SHARE = 0.35;
    public static final String FRONT_ROW = "Infantry";
    public static final String MIDDLE_ROW = "Lancer";
    public static final String BACK_ROW = "Marksman";
    public static final String[] GARRISON_SAME_LEVEL_REPLENISHMENT_ORDER = {"Infantry", "Lancer", "Marksman"};

    public static double[] redistributeKeepingRatio(long newCapacity, long infantry, long lancer, long marksman) {
        long total = Math.max(0, infantry) + Math.max(0, lancer) + Math.max(0, marksman);
        if (newCapacity <= 0 || total <= 0) return new double[]{0,0,0};
        return new double[]{
                newCapacity * (double)Math.max(0, infantry) / total,
                newCapacity * (double)Math.max(0, lancer) / total,
                newCapacity * (double)Math.max(0, marksman) / total
        };
    }

    public static final String SOURCE_COMBAT =
            "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/8048-combat-faq/";
    public static final String SOURCE_BATTLEFIELD_BONUSES =
            "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/9143-which-bonuses-take-effect-on-the-battlefield/";
    public static final String SOURCE_SQUAD_RECALCULATION =
            "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/9148-if-i-upgrade-chief-charms-chief-gear-hero-gear-after-setting-my-squad-will-it-affect-my-squad/";
    public static final String SOURCE_SNAPSHOT_LOCK =
            "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/9149-will-unequipping-or-upgrading-gear-during-battle-take-effect-in-real-time/";
    public static final String SOURCE_REPORT_BONUSES =
            "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/8051-what-special-bonuses-are-shown-in-battle-reports/";
}
