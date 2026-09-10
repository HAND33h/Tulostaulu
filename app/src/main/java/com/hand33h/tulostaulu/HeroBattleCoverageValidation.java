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

    /** Heroes with verified handling but no permanent numeric modifier in the aggregate model. */
    public static List<String> conditionalOnlySkillCoverage() {
        List<String> conditional = new ArrayList<>();
        for (String hero : HeroBattleData.HEROES) {
            if (hero == null || "None".equals(HeroBattleData.nameOf(hero))) continue;
            HeroBattleData.Effect effect = HeroBattleData.directEffect(hero, 5, 0, false);
            if (effect.hasVerifiedSkillData && !hasNumericAggregateEffect(effect)) {
                conditional.add(HeroBattleData.nameOf(hero));
            }
        }
        return Collections.unmodifiableList(conditional);
    }

    /** Heroes whose verified handling contributes at least one permanent numeric aggregate modifier. */
    public static List<String> numericAggregateSkillCoverage() {
        List<String> aggregate = new ArrayList<>();
        for (String hero : HeroBattleData.HEROES) {
            if (hero == null || "None".equals(HeroBattleData.nameOf(hero))) continue;
            HeroBattleData.Effect effect = HeroBattleData.directEffect(hero, 5, 0, false);
            if (effect.hasVerifiedSkillData && hasNumericAggregateEffect(effect)) {
                aggregate.add(HeroBattleData.nameOf(hero));
            }
        }
        return Collections.unmodifiableList(aggregate);
    }

    public static boolean allHeroesHaveVerifiedSkillHandling() {
        return missingSkillCoverage().isEmpty();
    }

    public static int coveredHeroCount() {
        return numericAggregateSkillCoverage().size() + conditionalOnlySkillCoverage().size();
    }

    public static int totalHeroCount() {
        int total = 0;
        for (String hero : HeroBattleData.HEROES) {
            if (hero != null && !"None".equals(HeroBattleData.nameOf(hero))) total++;
        }
        return total;
    }

    /** Compact diagnostic suitable for debug/about screens and build checks. */
    public static String coverageSummary() {
        return "Hero skills: " + coveredHeroCount() + "/" + totalHeroCount()
                + " verified (" + numericAggregateSkillCoverage().size() + " aggregate, "
                + conditionalOnlySkillCoverage().size() + " conditional-only), "
                + missingSkillCoverage().size() + " missing";
    }

    private static boolean hasNumericAggregateEffect(HeroBattleData.Effect x) {
        return nonZero(x.atk, x.def, x.hp, x.leth,
                x.enemyAtk, x.enemyDef, x.enemyHp, x.enemyLeth,
                x.infAtk, x.infDef, x.infHp, x.infLeth,
                x.lanAtk, x.lanDef, x.lanHp, x.lanLeth,
                x.marAtk, x.marDef, x.marHp, x.marLeth,
                x.damageDealt, x.damageTakenReduction, x.enemyDamageDealtReduction);
    }

    private static boolean nonZero(double... values) {
        for (double value : values) if (Math.abs(value) > 0.000001d) return true;
        return false;
    }
}
