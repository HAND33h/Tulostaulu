package com.hand33h.tulostaulu;

/**
 * Whiteout Survival combat mechanics verified against Century Games Combat FAQ.
 * Keep these as hard constraints; they are mechanics, not inferred damage formulae.
 * Source: https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/8048-combat-faq/
 */
public final class OfficialCombatRules {
    public static final String SOURCE_URL = "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/8048-combat-faq/";

    public static final String FRONT_ROW = "Infantry";
    public static final String MIDDLE_ROW = "Lancer";
    public static final String BACK_ROW = "Marksman";

    public static final boolean EXPEDITION_IS_TURN_BASED = true;
    public static final boolean SIDES_ATTACK_SIMULTANEOUSLY_EACH_TURN = true;
    public static final boolean EXPEDITION_SKILLS_ACTIVE_WITHOUT_MATCHING_TROOP = true;
    public static final boolean HERO_SKILLS_INDEPENDENT_OF_REPORT_STATS = true;

    public static final int HEROES_PER_MARCH = 3;
    public static final int RALLY_LEADER_EXPEDITION_SKILLS = 9;
    public static final int MAX_RALLY_MEMBER_PRIMARY_SKILLS = 4;
    public static final int MAX_GARRISON_MEMBER_PRIMARY_SKILLS = 4;
    public static final double CITY_ATTACKER_CASUALTY_DEATH_SHARE = 0.35;

    /** Rally/garrison member contribution is the FIRST Expedition skill of the captain hero. */
    public static boolean isJoinerEligibleExpeditionOrder(int expeditionOrder) {
        return expeditionOrder == 1;
    }

    private OfficialCombatRules() {}
}
