package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Guardrail for verified Expedition hero handling in the aggregate Battle Simulator. */
public final class HeroBattleCoverageValidation {
    private HeroBattleCoverageValidation() {}

    public static List<String> missingSkillCoverage() {
        List<String> missing = new ArrayList<>();
        for (String hero : HeroBattleData.HEROES) {
            if (skip(hero)) continue;
            if (!verifiedAtEverySkillLevel(hero)) missing.add(HeroBattleData.nameOf(hero));
        }
        return Collections.unmodifiableList(missing);
    }

    /** Heroes with verified handling but no permanent numeric modifier in either side model. */
    public static List<String> conditionalOnlySkillCoverage() {
        List<String> conditional = new ArrayList<>();
        for (String hero : HeroBattleData.HEROES) {
            if (skip(hero) || !verifiedAtEverySkillLevel(hero)) continue;
            HeroBattleData.Effect attacker = HeroBattleData.directEffect(hero, 5, 0, false);
            HeroBattleData.Effect defender = HeroBattleData.directEffect(hero, 5, 0, true);
            if (!hasNumericAggregateEffect(attacker) && !hasNumericAggregateEffect(defender)) conditional.add(HeroBattleData.nameOf(hero));
        }
        return Collections.unmodifiableList(conditional);
    }

    /** Heroes whose verified handling contributes at least one permanent numeric aggregate modifier. */
    public static List<String> numericAggregateSkillCoverage() {
        List<String> aggregate = new ArrayList<>();
        for (String hero : HeroBattleData.HEROES) {
            if (skip(hero) || !verifiedAtEverySkillLevel(hero)) continue;
            HeroBattleData.Effect attacker = HeroBattleData.directEffect(hero, 5, 0, false);
            HeroBattleData.Effect defender = HeroBattleData.directEffect(hero, 5, 0, true);
            if (hasNumericAggregateEffect(attacker) || hasNumericAggregateEffect(defender)) aggregate.add(HeroBattleData.nameOf(hero));
        }
        return Collections.unmodifiableList(aggregate);
    }

    /** Heroes whose aggregate output differs between attacker and defender side at max EW. */
    public static List<String> sideSensitiveSkillCoverage() {
        List<String> sideSensitive = new ArrayList<>();
        for (String hero : HeroBattleData.HEROES) {
            if (skip(hero) || !verifiedAtEverySkillLevel(hero)) continue;
            HeroBattleData.Effect attacker = HeroBattleData.directEffect(hero, 5, 10, false);
            HeroBattleData.Effect defender = HeroBattleData.directEffect(hero, 5, 10, true);
            if (!sameNumericEffects(attacker, defender)) sideSensitive.add(HeroBattleData.nameOf(hero));
        }
        return Collections.unmodifiableList(sideSensitive);
    }

    /** Detects broken/non-monotonic permanent aggregate values between skill levels 1..5. */
    public static List<String> nonMonotonicSkillScaling() {
        List<String> broken = new ArrayList<>();
        for (String hero : HeroBattleData.HEROES) {
            if (skip(hero) || !verifiedAtEverySkillLevel(hero)) continue;
            if (!monotonicForSide(hero, false) || !monotonicForSide(hero, true)) broken.add(HeroBattleData.nameOf(hero));
        }
        return Collections.unmodifiableList(broken);
    }

    public static boolean allHeroesHaveVerifiedSkillHandling() { return missingSkillCoverage().isEmpty(); }

    public static int coveredHeroCount() { return numericAggregateSkillCoverage().size() + conditionalOnlySkillCoverage().size(); }

    public static int totalHeroCount() {
        int total = 0;
        for (String hero : HeroBattleData.HEROES) if (!skip(hero)) total++;
        return total;
    }

    public static String coverageSummary() {
        return "Hero skills: " + coveredHeroCount() + "/" + totalHeroCount()
                + " verified all levels (" + numericAggregateSkillCoverage().size() + " aggregate, "
                + conditionalOnlySkillCoverage().size() + " conditional-only, "
                + sideSensitiveSkillCoverage().size() + " side-sensitive), "
                + missingSkillCoverage().size() + " missing, "
                + nonMonotonicSkillScaling().size() + " scaling warnings";
    }

    private static boolean verifiedAtEverySkillLevel(String hero) {
        for (int level = 1; level <= 5; level++) {
            HeroBattleData.Effect attacker = HeroBattleData.directEffect(hero, level, 0, false);
            HeroBattleData.Effect defender = HeroBattleData.directEffect(hero, level, 0, true);
            if (!attacker.hasVerifiedSkillData || !defender.hasVerifiedSkillData) return false;
        }
        return true;
    }

    private static boolean monotonicForSide(String hero, boolean defender) {
        HeroBattleData.Effect previous = HeroBattleData.directEffect(hero, 1, 0, defender);
        for (int level = 2; level <= 5; level++) {
            HeroBattleData.Effect current = HeroBattleData.directEffect(hero, level, 0, defender);
            if (decreased(previous, current)) return false;
            previous = current;
        }
        return true;
    }

    private static boolean decreased(HeroBattleData.Effect a, HeroBattleData.Effect b) {
        return less(b.atk,a.atk)||less(b.def,a.def)||less(b.hp,a.hp)||less(b.leth,a.leth)
                ||less(b.enemyAtk,a.enemyAtk)||less(b.enemyDef,a.enemyDef)||less(b.enemyHp,a.enemyHp)||less(b.enemyLeth,a.enemyLeth)
                ||less(b.infAtk,a.infAtk)||less(b.infDef,a.infDef)||less(b.infHp,a.infHp)||less(b.infLeth,a.infLeth)
                ||less(b.lanAtk,a.lanAtk)||less(b.lanDef,a.lanDef)||less(b.lanHp,a.lanHp)||less(b.lanLeth,a.lanLeth)
                ||less(b.marAtk,a.marAtk)||less(b.marDef,a.marDef)||less(b.marHp,a.marHp)||less(b.marLeth,a.marLeth)
                ||less(b.damageDealt,a.damageDealt)||less(b.damageTakenReduction,a.damageTakenReduction)
                ||less(b.enemyDamageDealtReduction,a.enemyDamageDealtReduction);
    }

    private static boolean hasNumericAggregateEffect(HeroBattleData.Effect x) {
        return nonZero(x.atk,x.def,x.hp,x.leth,x.enemyAtk,x.enemyDef,x.enemyHp,x.enemyLeth,
                x.infAtk,x.infDef,x.infHp,x.infLeth,x.lanAtk,x.lanDef,x.lanHp,x.lanLeth,
                x.marAtk,x.marDef,x.marHp,x.marLeth,x.damageDealt,x.damageTakenReduction,x.enemyDamageDealtReduction);
    }

    private static boolean sameNumericEffects(HeroBattleData.Effect a, HeroBattleData.Effect b) {
        return !decreased(a,b) && !decreased(b,a);
    }

    private static boolean skip(String hero) { return hero == null || "None".equals(HeroBattleData.nameOf(hero)); }
    private static boolean less(double a,double b) { return a < b - 0.000001d; }
    private static boolean nonZero(double... values) { for(double value:values) if(Math.abs(value)>0.000001d)return true; return false; }
}
