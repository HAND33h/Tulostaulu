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
            HeroBattleData.Effect attacker = HeroBattleData.directEffect(hero, 5, 0, false);
            HeroBattleData.Effect defender = HeroBattleData.directEffect(hero, 5, 0, true);
            if (!attacker.hasVerifiedSkillData || !defender.hasVerifiedSkillData) {
                missing.add(HeroBattleData.nameOf(hero));
            }
        }
        return Collections.unmodifiableList(missing);
    }

    /** Heroes with verified handling but no permanent numeric modifier in either side model. */
    public static List<String> conditionalOnlySkillCoverage() {
        List<String> conditional = new ArrayList<>();
        for (String hero : HeroBattleData.HEROES) {
            if (hero == null || "None".equals(HeroBattleData.nameOf(hero))) continue;
            HeroBattleData.Effect attacker = HeroBattleData.directEffect(hero, 5, 0, false);
            HeroBattleData.Effect defender = HeroBattleData.directEffect(hero, 5, 0, true);
            boolean verified = attacker.hasVerifiedSkillData && defender.hasVerifiedSkillData;
            if (verified && !hasNumericAggregateEffect(attacker) && !hasNumericAggregateEffect(defender)) {
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
            HeroBattleData.Effect attacker = HeroBattleData.directEffect(hero, 5, 0, false);
            HeroBattleData.Effect defender = HeroBattleData.directEffect(hero, 5, 0, true);
            boolean verified = attacker.hasVerifiedSkillData && defender.hasVerifiedSkillData;
            if (verified && (hasNumericAggregateEffect(attacker) || hasNumericAggregateEffect(defender))) {
                aggregate.add(HeroBattleData.nameOf(hero));
            }
        }
        return Collections.unmodifiableList(aggregate);
    }

    /** Heroes whose aggregate skill output differs between attacker and defender side. */
    public static List<String> sideSensitiveSkillCoverage() {
        List<String> sideSensitive = new ArrayList<>();
        for (String hero : HeroBattleData.HEROES) {
            if (hero == null || "None".equals(HeroBattleData.nameOf(hero))) continue;
            HeroBattleData.Effect attacker = HeroBattleData.directEffect(hero, 5, 10, false);
            HeroBattleData.Effect defender = HeroBattleData.directEffect(hero, 5, 10, true);
            if (attacker.hasVerifiedSkillData && defender.hasVerifiedSkillData && !sameNumericEffects(attacker, defender)) {
                sideSensitive.add(HeroBattleData.nameOf(hero));
            }
        }
        return Collections.unmodifiableList(sideSensitive);
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
                + conditionalOnlySkillCoverage().size() + " conditional-only, "
                + sideSensitiveSkillCoverage().size() + " side-sensitive), "
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

    private static boolean sameNumericEffects(HeroBattleData.Effect a, HeroBattleData.Effect b) {
        return same(a.atk,b.atk) && same(a.def,b.def) && same(a.hp,b.hp) && same(a.leth,b.leth)
                && same(a.enemyAtk,b.enemyAtk) && same(a.enemyDef,b.enemyDef)
                && same(a.enemyHp,b.enemyHp) && same(a.enemyLeth,b.enemyLeth)
                && same(a.infAtk,b.infAtk) && same(a.infDef,b.infDef)
                && same(a.infHp,b.infHp) && same(a.infLeth,b.infLeth)
                && same(a.lanAtk,b.lanAtk) && same(a.lanDef,b.lanDef)
                && same(a.lanHp,b.lanHp) && same(a.lanLeth,b.lanLeth)
                && same(a.marAtk,b.marAtk) && same(a.marDef,b.marDef)
                && same(a.marHp,b.marHp) && same(a.marLeth,b.marLeth)
                && same(a.damageDealt,b.damageDealt)
                && same(a.damageTakenReduction,b.damageTakenReduction)
                && same(a.enemyDamageDealtReduction,b.enemyDamageDealtReduction);
    }

    private static boolean same(double a, double b) {
        return Math.abs(a - b) <= 0.000001d;
    }

    private static boolean nonZero(double... values) {
        for (double value : values) if (Math.abs(value) > 0.000001d) return true;
        return false;
    }
}
