package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Official battle-report visibility policy.
 * Source checked 2026-09-09: Century Games Whiteout Survival Help Center,
 * "What Special Bonuses are shown in battle reports?".
 *
 * Important: hero skill bonuses are calculated during battle and are NOT shown
 * in battle reports. An analyzer must not infer that a missing report field means
 * a hero skill is inactive.
 */
public final class BattleReportBonusPolicy {
    private BattleReportBonusPolicy() {}

    public enum Visibility { SHOWN_IN_REPORT, CALCULATED_DURING_BATTLE_NOT_SHOWN, UNKNOWN }

    private static final Set<String> SHOWN = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "BUFFS", "PENALTIES", "POSITION_PERKS", "PET_SKILL_BONUSES",
            "CHIEF_GEAR_BONUSES", "HERO_GEAR_BONUSES", "TECH_RESEARCH_BONUSES",
            "ALLIANCE_TERRITORY_BUFFS", "CASTLE_SKIN_BONUSES",
            "HERO_EXCLUSIVE_GEAR_BONUSES", "HERO_TALENT_BONUSES"
    )));

    public static Visibility visibility(String key) {
        if (key == null) return Visibility.UNKNOWN;
        String k = key.trim().toUpperCase();
        if ("HERO_SKILL_BONUSES".equals(k)) return Visibility.CALCULATED_DURING_BATTLE_NOT_SHOWN;
        return SHOWN.contains(k) ? Visibility.SHOWN_IN_REPORT : Visibility.UNKNOWN;
    }

    public static boolean safeToInferFromMissingReportField(String key) {
        return visibility(key) != Visibility.CALCULATED_DURING_BATTLE_NOT_SHOWN;
    }

    public static String analyzerWarning() {
        return "Hero skill bonuses are not displayed in battle reports; they are calculated during battle. Do not treat a missing hero-skill report field as zero.";
    }

    public static String sourceUrl() {
        return "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/8051-what-special-bonuses-are-shown-in-battle-reports/";
    }
}
