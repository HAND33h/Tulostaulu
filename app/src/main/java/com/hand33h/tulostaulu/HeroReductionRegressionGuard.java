package com.hand33h.tulostaulu;

/** Deterministic regression checks for the Battle Simulator reduction-only scoring path. */
public final class HeroReductionRegressionGuard {
    private static final double EPSILON = 0.000001d;
    private HeroReductionRegressionGuard() {}

    public static boolean reductionOnlyMathIsSafe() {
        HeroCombatAccumulator.Totals attacker = new HeroCombatAccumulator.Totals();
        HeroCombatAccumulator.Totals defender = new HeroCombatAccumulator.Totals();
        attacker.damageDealt = 25.0;
        attacker.damageTakenReduction = 20.0;
        defender.enemyDamageDealtReduction = 10.0;
        double alreadyDamageAdjusted = 1250.0;
        double[] scores = HeroCombatAccumulator.applyReductionsToDamageAdjustedScores(alreadyDamageAdjusted,1000.0,attacker,defender);
        double expected = alreadyDamageAdjusted * HeroCombatScore.durabilityFactor(20.0) * HeroCombatScore.enemyOutgoingFactor(10.0);
        return close(scores[0], expected);
    }

    public static boolean zeroReductionsAreNeutral() {
        HeroCombatAccumulator.Totals attacker = new HeroCombatAccumulator.Totals();
        HeroCombatAccumulator.Totals defender = new HeroCombatAccumulator.Totals();
        double[] scores = HeroCombatAccumulator.applyReductionsToDamageAdjustedScores(1234.0,5678.0,attacker,defender);
        return close(scores[0],1234.0) && close(scores[1],5678.0);
    }

    public static boolean enemyReductionIsDirectional() {
        HeroCombatAccumulator.Totals attacker = new HeroCombatAccumulator.Totals();
        HeroCombatAccumulator.Totals defender = new HeroCombatAccumulator.Totals();
        attacker.enemyDamageDealtReduction = 20.0;
        double[] scores = HeroCombatAccumulator.applyReductionsToDamageAdjustedScores(1000.0,1000.0,attacker,defender);
        return close(scores[0],1000.0) && close(scores[1],800.0);
    }

    /** Both sides can own durability and enemy-outgoing reductions at the same time. */
    public static boolean bilateralReductionsAreSymmetric() {
        HeroCombatAccumulator.Totals attacker = new HeroCombatAccumulator.Totals();
        HeroCombatAccumulator.Totals defender = new HeroCombatAccumulator.Totals();
        attacker.damageTakenReduction = 20.0;
        attacker.enemyDamageDealtReduction = 15.0;
        defender.damageTakenReduction = 30.0;
        defender.enemyDamageDealtReduction = 10.0;
        double[] scores = HeroCombatAccumulator.applyReductionsToDamageAdjustedScores(1200.0,900.0,attacker,defender);
        double expectedA = 1200.0 * HeroCombatScore.durabilityFactor(20.0) * HeroCombatScore.enemyOutgoingFactor(10.0);
        double expectedD = 900.0 * HeroCombatScore.durabilityFactor(30.0) * HeroCombatScore.enemyOutgoingFactor(15.0);
        return close(scores[0],expectedA) && close(scores[1],expectedD);
    }

    public static boolean extremeReductionsStayFinite() {
        HeroCombatAccumulator.Totals attacker = new HeroCombatAccumulator.Totals();
        HeroCombatAccumulator.Totals defender = new HeroCombatAccumulator.Totals();
        attacker.damageTakenReduction = 500.0;
        defender.enemyDamageDealtReduction = 500.0;
        double[] scores = HeroCombatAccumulator.applyReductionsToDamageAdjustedScores(1000.0,1000.0,attacker,defender);
        return Double.isFinite(scores[0]) && Double.isFinite(scores[1]) && scores[0] > 0.0 && scores[1] > 0.0;
    }

    public static boolean verifiedHeroDataFlowsThroughAccumulator() {
        HeroCombatAccumulator.Totals jessie = HeroCombatAccumulator.collect(new String[]{"Jessie"},new int[]{5},new int[]{0},false);
        HeroCombatAccumulator.Totals lumak = HeroCombatAccumulator.collect(new String[]{"Lumak Bokan"},new int[]{5},new int[]{0},false);
        if (jessie.damageDealt <= 0.0 || jessie.damageTakenReduction <= 0.0) return false;
        if (lumak.enemyDamageDealtReduction <= 0.0) return false;
        double[] scores = HeroCombatAccumulator.applyReductionsToDamageAdjustedScores(1000.0,1000.0,jessie,lumak);
        double expectedAttacker = 1000.0 * HeroCombatScore.durabilityFactor(jessie.damageTakenReduction) * HeroCombatScore.enemyOutgoingFactor(lumak.enemyDamageDealtReduction);
        return close(scores[0],expectedAttacker);
    }

    public static boolean conditionalHeroesStayConditional() {
        HeroCombatAccumulator.Totals wayne = HeroCombatAccumulator.collect(new String[]{"Wayne"},new int[]{5},new int[]{0},false);
        HeroCombatAccumulator.Totals flora = HeroCombatAccumulator.collect(new String[]{"Flora"},new int[]{5},new int[]{0},false);
        boolean noPermanentFlattening = close(wayne.damageDealt,0.0)
                && close(wayne.damageTakenReduction,0.0)
                && close(wayne.enemyDamageDealtReduction,0.0)
                && close(flora.damageDealt,0.0)
                && close(flora.damageTakenReduction,0.0)
                && close(flora.enemyDamageDealtReduction,0.0);
        return noPermanentFlattening
                && HeroConditionalEffects.hasStructuredEffects("Wayne")
                && HeroConditionalEffects.hasStructuredEffects("Flora");
    }

    public static boolean allChecksPass() {
        return reductionOnlyMathIsSafe() && zeroReductionsAreNeutral() && enemyReductionIsDirectional()
                && bilateralReductionsAreSymmetric() && extremeReductionsStayFinite()
                && verifiedHeroDataFlowsThroughAccumulator() && conditionalHeroesStayConditional();
    }

    private static boolean close(double a,double b){return Math.abs(a-b)<=EPSILON;}
}
