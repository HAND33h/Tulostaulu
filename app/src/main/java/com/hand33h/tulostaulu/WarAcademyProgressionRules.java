package com.hand33h.tulostaulu;

/**
 * Verified War Academy / T12 progression gates.
 *
 * Official source checked 2026-09-09:
 * Century Games FAQ 8826 — Flame Tech appears after the state-age gate
 * and after any Helios (T11) troop has been unlocked.
 *
 * Community progression detail is kept explicitly separate from the official gate.
 */
public final class WarAcademyProgressionRules {
    private WarAcademyProgressionRules() {}

    public static final String OFFICIAL_T12_UNLOCK_SOURCE =
            "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/8826-how-do-i-unlock-exalted-troops-t12/";
    public static final String COMMUNITY_TREE_SOURCE =
            "https://wostools.net/wiki/buildings/war-academy";

    /** Century Games: Flame Tech requires a state-age gate plus at least one unlocked Helios troop. */
    public static boolean flameTechEligible(boolean stateAgeGateOpen, boolean hasAnyHeliosT11) {
        return stateAgeGateOpen && hasAnyHeliosT11;
    }

    /** War Academy research is distinct from normal Research Center research. */
    public static final boolean USE_SEPARATE_WAR_ACADEMY_CALCULATOR = true;

    /** The three troop branches must be tracked independently. */
    public enum TroopBranch { INFANTRY, LANCER, MARKSMAN }

    /**
     * Community-verified T12 tree structure. Do not treat this as an official hidden formula.
     * Tier 1 consists of five Exalted tracks with five levels each; later tiers strengthen T12.
     */
    public static final int EXALTED_TIER1_TRACKS = 5;
    public static final int EXALTED_TIER1_LEVELS_PER_TRACK = 5;
    public static final int MOLTEN_I_MAX_LEVEL = 20;
    public static final int MOLTEN_II_MAX_LEVEL = 50;
    public static final int MOLTEN_III_MAX_LEVEL = 50;
    public static final int SOLAR_SUPREMACY_MAX_LEVEL = 15;

    public static int tier1LevelsPerBranch() {
        return EXALTED_TIER1_TRACKS * EXALTED_TIER1_LEVELS_PER_TRACK;
    }

    public static boolean tier1Complete(int[] trackLevels) {
        if (trackLevels == null || trackLevels.length != EXALTED_TIER1_TRACKS) return false;
        for (int level : trackLevels) if (level < EXALTED_TIER1_LEVELS_PER_TRACK) return false;
        return true;
    }

    /** Exact state age is intentionally not guessed; Century Games describes it only as a certain age. */
    public static boolean canPlanT12(boolean stateAgeGateOpen, boolean hasAnyHeliosT11) {
        return flameTechEligible(stateAgeGateOpen, hasAnyHeliosT11);
    }
}
