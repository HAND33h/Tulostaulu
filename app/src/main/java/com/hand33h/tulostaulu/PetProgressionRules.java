package com.hand33h.tulostaulu;

/**
 * Verified Pet-system mechanics for battle/progression tooling.
 * Numeric stat gains are intentionally not guessed: exact values belong in pet-specific data.
 * Sources checked 2026-09-09: Century Games Pet System FAQs.
 */
public final class PetProgressionRules {
    private PetProgressionRules() {}

    public enum Resource {
        PET_FOOD,
        COMMON_WILD_MARK,
        ADVANCED_WILD_MARK,
        TAMING_MANUAL,
        ENERGIZING_POTION,
        STRENGTHENING_SERUM
    }

    public enum System {
        LEVEL_UP,
        REFINEMENT,
        ADVANCED_REFINEMENT,
        POTENTIAL_ADVANCEMENT
    }

    public static System usedFor(Resource resource) {
        switch (resource) {
            case PET_FOOD: return System.LEVEL_UP;
            case COMMON_WILD_MARK: return System.REFINEMENT;
            case ADVANCED_WILD_MARK: return System.ADVANCED_REFINEMENT;
            case TAMING_MANUAL:
            case ENERGIZING_POTION:
            case STRENGTHENING_SERUM:
                return System.POTENTIAL_ADVANCEMENT;
            default: throw new IllegalArgumentException("Unknown resource: " + resource);
        }
    }

    /** Pet feature also requires a server-age gate that varies by rollout/state. */
    public static final int MIN_FURNACE_LEVEL = 18;

    /** Official FAQ: each pet can be tamed and fed twice daily. */
    public static final int DAILY_TAME_ATTEMPTS_PER_PET = 2;
    public static final int DAILY_FEED_ATTEMPTS_PER_PET = 2;

    /** Refinement changes stats only when the player confirms replacement. */
    public static final boolean REFINEMENT_REQUIRES_CONFIRM_TO_REPLACE = true;

    /** Skipping/continuing past a refinement still consumes the refinement items. */
    public static final boolean SKIPPED_REFINEMENT_CONSUMES_ITEMS = true;

    /** Century Games states that refinement is irreversible after replacement is confirmed. */
    public static final boolean CONFIRMED_REFINEMENT_IS_IRREVERSIBLE = true;

    /** Refinement is not monotonic; a roll can reduce individual stats or Total Power. */
    public static final boolean REFINEMENT_CAN_CHANGE_STATS = true;

    /** The extra warning is tied to a Total Power drop, not every individual affix decrease. */
    public static final boolean SECOND_WARNING_TRIGGERED_BY_TOTAL_POWER_DROP = true;

    /**
     * After a pet's secondary-stat quality has increased, later refinement does not reduce
     * that quality grade even when the numeric refinement bonus changes.
     */
    public static final boolean SECONDARY_STAT_QUALITY_GRADE_DOES_NOT_DROP_AFTER_INCREASE = true;

    /** Do not infer an exact unlock day globally; Century Games describes it as a server-age gate. */
    public static boolean featureEligible(int furnaceLevel, boolean serverAgeGateOpen) {
        return furnaceLevel >= MIN_FURNACE_LEVEL && serverAgeGateOpen;
    }

    /**
     * Safe UI helper: never auto-replace a refinement solely because some stats increased.
     * Explicit confirmation is required so optimizers cannot silently consume an irreversible roll.
     */
    public static boolean mayAutoReplaceRefinement() {
        return false;
    }

    public static final String SOURCE_PET_SYSTEM =
            "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/section/1275-pet-system/";
    public static final String SOURCE_REFINEMENT =
            "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/6656-why-does-the-refinement-value-of-my-pet-decrease/";
}
