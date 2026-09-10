package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Guardrail for hero Expedition coverage in the aggregate Battle Simulator.
 *
 * A hero counts as covered when HeroBattleData can resolve verified Expedition
 * handling for the selected skill level. Covered does not mean every conditional
 * proc/turn/crit mechanic is flattened into the aggregate score; those mechanics
 * stay conditional until the simulator has an explicit model for them.
 */
public final class HeroBattleCoverageValidation {
    private HeroBattleCoverageValidation() {}

    public static List<String> missingSkillCoverage() {
        List<String> missing = new ArrayList<>();
        for (String hero : HeroBattleData.HEROES) {
            if (hero == null || "None".equals(HeroBattleData.nameOf(hero))) continue;
            HeroBattleData.Effect effect = HeroBattleData.directEffect(hero, 5, 0, false);
            if (!effect.hasVerifiedSkillData) missing.add(HeroBattleData.nameOf(hero));
        }
        return Collections.unmodifiableList(missing);
    }

    public static boolean allHeroesHaveVerifiedSkillHandling() {
        return missingSkillCoverage().isEmpty();
    }

    public static int coveredHeroCount() {
        int total = 0;
        for (String hero : HeroBattleData.HEROES) {
            if (hero == null || "None".equals(HeroBattleData.nameOf(hero))) continue;
            if (HeroBattleData.directEffect(hero, 5, 0, false).hasVerifiedSkillData) total++;
        }
        return total;
    }

    public static int totalHeroCount() {
        int total = 0;
        for (String hero : HeroBattleData.HEROES) {
            if (hero != null && !"None".equals(HeroBattleData.nameOf(hero))) total++;
        }
        return total;
    }
}
