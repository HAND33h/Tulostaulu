package com.hand33h.tulostaulu;

/** Deterministic regression checks for the Battle Simulator reduction-only scoring path. */
public final class HeroReductionRegressionGuard {
    private static final double EPSILON = 0.000001d;
    private HeroReductionRegressionGuard() {}

    public static boolean reductionOnlyMathIsSafe() {
        HeroCombatAccumulator.Totals attacker = new HeroCombatAccumulator.Totals();
        HeroCombatAccumulator.Totals defender = new HeroCombatAccumulator.Totals();
        attacker.damageDealt = 25.0; // already included in the incoming score; must not be applied again
        attacker.damageTakenReduction = 20.0;
        defender.enemyDamageDealtReduction = 10.0;

        double alreadyDamageAdjusted = 1250.0;
        double[] scores = HeroCombatAccumulator.applyReductionsToDamageAdjustedScores(
                alreadyDamageAdjusted, 1000.0, attacker, defender);
        double expected = alreadyDamageAdjusted
                * HeroCombatScore.durabilityFactor(20.0)
                * HeroCombatScore.enemyOutgoingFactor(10.0);
        return close(scores[0], expected);
    }

    public static boolean zeroReductionsAreNeutral() {
        HeroCombatAccumulator.Totals attacker = new HeroCombatAccumulator.Totals();
        HeroCombatAccumulator.Totals defender = new HeroCombatAccumulator.Totals();
        double[] scores = HeroCombatAccumulator.applyReductionsToDamageAdjustedScores(
                1234.0, 5678.0, attacker, defender);
        return close(scores[0], 1234.0) && close(scores[1], 5678.0);
    }

    /** Guards both sides: opponent reduction must affect only the opposing score. */
    public static boolean enemyReductionIsDirectional() {
        HeroCombatAccumulator.Totals attacker = new HeroCombatAccumulator.Totals();
        HeroCombatAccumulator.Totals defender = new HeroCombatAccumulator.Totals();
        attacker.enemyDamageDealtReduction = 20.0;
        double[] scores = HeroCombatAccumulator.applyReductionsToDamageAdjustedScores(
                1000.0, 1000.0, attacker, defender);
        return close(scores[0], 1000.0) && close(scores[1], 800.0);
    }

    /** Extreme imported values must stay finite because reduction helpers clamp at +/-95%. */
    public static boolean extremeReductionsStayFinite() {
        HeroCombatAccumulator.Totals attacker = new HeroCombatAccumulator.Totals();
        HeroCombatAccumulator.Totals defender = new HeroCombatAccumulator.Totals();
        attacker.damageTakenReduction = 500.0;
        defender.enemyDamageDealtReduction = 500.0;
        double[] scores = HeroCombatAccumulator.applyReductionsToDamageAdjustedScores(
                1000.0, 1000.0, attacker, defender);
        return Double.isFinite(scores[0]) && Double.isFinite(scores[1])
                && scores[0] > 0.0 && scores[1] > 0.0;
    }

    public static boolean allChecksPass() {
        return reductionOnlyMathIsSafe()
                && zeroReductionsAreNeutral()
                && enemyReductionIsDirectional()
                && extremeReductionsStayFinite();
    }

    private static boolean close(double a, double b) {
        return Math.abs(a - b) <= EPSILON;
    }
}
