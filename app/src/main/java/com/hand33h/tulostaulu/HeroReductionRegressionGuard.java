package com.hand33h.tulostaulu;

/**
 * Lightweight deterministic regression checks for the reduction-only scoring path.
 * These checks contain no Android dependencies and can also be reused by CI tests.
 */
public final class HeroReductionRegressionGuard {
    private static final double EPSILON = 0.000001d;

    private HeroReductionRegressionGuard() {}

    /**
     * Verifies that a score which already contains Damage Dealt is changed only
     * by own Damage Taken Reduction and the opponent's Enemy Damage Dealt Reduction.
     */
    public static boolean reductionOnlyMathIsSafe() {
        HeroCombatAccumulator.Totals attacker = new HeroCombatAccumulator.Totals();
        HeroCombatAccumulator.Totals defender = new HeroCombatAccumulator.Totals();

        attacker.damageDealt = 25.0; // Must NOT be applied by the reduction-only path.
        attacker.damageTakenReduction = 20.0;
        defender.enemyDamageDealtReduction = 10.0;

        double alreadyDamageAdjusted = 1250.0;
        double[] scores = applyReductionOnly(alreadyDamageAdjusted, 1000.0, attacker, defender);
        double expected = alreadyDamageAdjusted
                * HeroCombatScore.durabilityFactor(20.0)
                * HeroCombatScore.enemyOutgoingFactor(10.0);

        return close(scores[0], expected);
    }

    /** Verifies that zero reductions leave already damage-adjusted scores untouched. */
    public static boolean zeroReductionsAreNeutral() {
        HeroCombatAccumulator.Totals attacker = new HeroCombatAccumulator.Totals();
        HeroCombatAccumulator.Totals defender = new HeroCombatAccumulator.Totals();
        double[] scores = applyReductionOnly(1234.0, 5678.0, attacker, defender);
        return close(scores[0], 1234.0) && close(scores[1], 5678.0);
    }

    public static boolean allChecksPass() {
        return reductionOnlyMathIsSafe() && zeroReductionsAreNeutral();
    }

    private static double[] applyReductionOnly(
            double attackerScore, double defenderScore,
            HeroCombatAccumulator.Totals attacker,
            HeroCombatAccumulator.Totals defender) {
        double adjustedAttacker = attackerScore
                * HeroCombatScore.durabilityFactor(attacker.damageTakenReduction)
                * HeroCombatScore.enemyOutgoingFactor(defender.enemyDamageDealtReduction);
        double adjustedDefender = defenderScore
                * HeroCombatScore.durabilityFactor(defender.damageTakenReduction)
                * HeroCombatScore.enemyOutgoingFactor(attacker.enemyDamageDealtReduction);
        return new double[]{adjustedAttacker, adjustedDefender};
    }

    private static boolean close(double a, double b) {
        return Math.abs(a - b) <= EPSILON;
    }
}
