package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/** Official Century Games guardrail for battlefield input coverage. */
public final class BattleBonusValidation {
    public static final String SOURCE_INCLUDED = "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/9143-which-bonuses-take-effect-on-the-battlefield/";
    public static final String SOURCE_EXCLUDED = "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/9144-which-bonuses-do-not-take-effect-on-the-battlefield/";

    public static final Set<String> REQUIRED_SYSTEMS = Collections.unmodifiableSet(new LinkedHashSet<>(Arrays.asList(
            "TROOPS", "HERO_LEVELS", "HERO_STAR_LEVELS", "HERO_SKILLS", "HERO_GEAR",
            "HERO_EXCLUSIVE_GEAR_STATS", "GLOBAL_JERONIMO_NATALIA", "PET_SKILLS", "PET_STATS",
            "PET_LEVELS", "PET_ADVANCEMENTS", "PET_REFINEMENTS", "CHIEF_CHARMS", "CHIEF_GEAR",
            "TECH", "FIRE_CRYSTAL_TECH", "FLAME_TECH", "ALLIANCE_TECH", "DAYBREAK_ISLAND",
            "SKINS", "VIP_LEVELS", "BUILDINGS", "FACILITY", "EXPERT_LEVEL",
            "EXPERT_RELATIONSHIP_ADVANCEMENTS", "EXPERT_RESEARCH", "EXPERT_SKILLS"
    )));

    /** These may exist elsewhere in the app/report, but must never be added to battlefield math. */
    public static final Set<String> EXCLUDED_SYSTEMS = Collections.unmodifiableSet(new LinkedHashSet<>(Arrays.asList(
            "HERO_EXCLUSIVE_GEAR_SKILLS", "TERRITORY_BONUSES", "STATE_POSITIONS",
            "PRESIDENT_SKILLS", "CITY_BONUSES", "FROSTDRAGON_TYRANT_TITLES"
    )));

    public static final boolean GLOBAL_JERONIMO_NATALIA_REQUIRE_DEPLOYMENT = false;
    public static final boolean PET_SKILL_MANUAL_ACTIVATION_STACKS = false;
    public static final boolean FACILITY_MUST_BE_ACTIVE_AT_SQUAD_SETUP = true;

    public static Set<String> missingFrom(Set<String> capturedSystems) {
        LinkedHashSet<String> missing = new LinkedHashSet<>(REQUIRED_SYSTEMS);
        if (capturedSystems != null) missing.removeAll(capturedSystems);
        return Collections.unmodifiableSet(missing);
    }

    public static Set<String> invalidForBattlefield(Set<String> capturedSystems) {
        LinkedHashSet<String> invalid = new LinkedHashSet<>();
        if (capturedSystems != null) {
            for (String system : capturedSystems) if (EXCLUDED_SYSTEMS.contains(system)) invalid.add(system);
        }
        return Collections.unmodifiableSet(invalid);
    }

    public static boolean isComplete(Set<String> capturedSystems) {
        return missingFrom(capturedSystems).isEmpty() && invalidForBattlefield(capturedSystems).isEmpty();
    }

    public static boolean affectsBattlefield(String system) {
        return system != null && REQUIRED_SYSTEMS.contains(system) && !EXCLUDED_SYSTEMS.contains(system);
    }

    private BattleBonusValidation() {}
}
