package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Official-source guardrail for battle input coverage.
 * Source: Century Games Whiteout Survival FAQ "Which bonuses take effect on the battlefield?"
 *
 * Keep this separate from community-derived numeric formulas: this class says which
 * systems belong in battlefield calculations, not how hidden combat math combines them.
 */
public final class BattleBonusValidation {
    public static final String SOURCE_URL = "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/9143-which-bonuses-take-effect-on-the-battlefield/";

    public static final Set<String> REQUIRED_SYSTEMS = Collections.unmodifiableSet(new LinkedHashSet<>(Arrays.asList(
            "TROOPS",
            "HERO_LEVELS",
            "HERO_STAR_LEVELS",
            "HERO_SKILLS",
            "HERO_GEAR",
            "HERO_EXCLUSIVE_GEAR_STATS",
            "GLOBAL_JERONIMO_NATALIA",
            "PET_SKILLS",
            "PET_STATS",
            "PET_LEVELS",
            "PET_ADVANCEMENTS",
            "PET_REFINEMENTS",
            "CHIEF_CHARMS",
            "CHIEF_GEAR",
            "TECH",
            "FIRE_CRYSTAL_TECH",
            "FLAME_TECH",
            "ALLIANCE_TECH",
            "DAYBREAK_ISLAND",
            "SKINS",
            "VIP_LEVELS",
            "BUILDINGS",
            "FACILITY",
            "EXPERT_LEVEL",
            "EXPERT_RELATIONSHIP_ADVANCEMENTS",
            "EXPERT_RESEARCH",
            "EXPERT_SKILLS"
    )));

    // Official FAQ: global Jeronimo/Natalia bonuses apply when owned even if not deployed.
    public static final boolean GLOBAL_JERONIMO_NATALIA_REQUIRE_DEPLOYMENT = false;
    // Official FAQ: pet skills are active by default; manual activation does not stack them.
    public static final boolean PET_SKILL_MANUAL_ACTIVATION_STACKS = false;
    // Facility bonus must be active while the squad is being set.
    public static final boolean FACILITY_MUST_BE_ACTIVE_AT_SQUAD_SETUP = true;

    /** Returns required battlefield systems missing from a caller's captured input set. */
    public static Set<String> missingFrom(Set<String> capturedSystems) {
        LinkedHashSet<String> missing = new LinkedHashSet<>(REQUIRED_SYSTEMS);
        if (capturedSystems != null) missing.removeAll(capturedSystems);
        return Collections.unmodifiableSet(missing);
    }

    public static boolean isComplete(Set<String> capturedSystems) {
        return missingFrom(capturedSystems).isEmpty();
    }

    private BattleBonusValidation() {}
}
