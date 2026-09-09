package com.hand33h.tulostaulu;

/**
 * Verified Pet-system mechanics for battle/progression tooling.
 * Numeric stat gains are intentionally not guessed: exact values belong in pet-specific data.
 * Sources checked 2026-09-09:
 * - Century Games Pet System / pet item FAQ
 * - https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/6650-what-are-the-functions-of-the-new-pet-items/
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

    /** Pet feature also requires Furnace level 18; server-age gate varies by rollout/state. */
    public static final int MIN_FURNACE_LEVEL = 18;

    /** Official FAQ: each pet can be tamed and fed twice daily. */
    public static final int DAILY_TAME_ATTEMPTS_PER_PET = 2;
    public static final int DAILY_FEED_ATTEMPTS_PER_PET = 2;

    /**
     * Refinement is not monotonic: replacing a roll can change stats. The app must never
     * assume that spending a mark guarantees a higher combat stat or total power.
     */
    public static final boolean REFINEMENT_CAN_CHANGE_STATS = true;

    /** Official FAQ: the extra warning is tied to a Total Power drop, not every affix decrease. */
    public static final boolean SECOND_WARNING_TRIGGERED_BY_TOTAL_POWER_DROP = true;

    /** Do not infer an exact unlock day globally; Century Games describes it as a server-age gate. */
    public static boolean featureEligible(int furnaceLevel, boolean serverAgeGateOpen) {
        return furnaceLevel >= MIN_FURNACE_LEVEL && serverAgeGateOpen;
    }
}
