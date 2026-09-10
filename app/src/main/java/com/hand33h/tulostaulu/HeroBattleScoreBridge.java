package com.hand33h.tulostaulu;

/**
 * Small bridge used by Battle Simulator to apply verified permanent hero
 * damage modifiers without duplicating combat math in the Activity.
 */
public final class HeroBattleScoreBridge {
    private HeroBattleScoreBridge() {}

    public static final class Result {
        public final double attackerScore;
        public final double defenderScore;
        public final HeroCombatAccumulator.Totals attacker;
        public final HeroCombatAccumulator.Totals defender;

        Result(double attackerScore, double defenderScore,
               HeroCombatAccumulator.Totals attacker,
               HeroCombatAccumulator.Totals defender) {
            this.attackerScore = attackerScore;
            this.defenderScore = defenderScore;
            this.attacker = attacker;
            this.defender = defender;
        }
    }

    /** Full path for base scores that do not already include hero Damage Dealt. */
    public static Result apply(
            double attackerBaseScore, double defenderBaseScore,
            String[] attackerHeroes, int[] attackerSkills, int[] attackerExclusive,
            String[] defenderHeroes, int[] defenderSkills, int[] defenderExclusive) {

        HeroCombatAccumulator.Totals attacker = HeroCombatAccumulator.collect(
                attackerHeroes, attackerSkills, attackerExclusive, false);
        HeroCombatAccumulator.Totals defender = HeroCombatAccumulator.collect(
                defenderHeroes, defenderSkills, defenderExclusive, true);

        double[] scores = HeroCombatAccumulator.applyToScores(
                attackerBaseScore, defenderBaseScore, attacker, defender);
        return new Result(scores[0], scores[1], attacker, defender);
    }

    /**
     * BattleSimulatorActivity already includes hero Damage Dealt inside armyScore().
     * This path applies only the two missing permanent reduction mechanics so
     * Damage Dealt is never counted twice.
     */
    public static Result applyReductionsToDamageAdjustedScores(
            double attackerDamageAdjustedScore, double defenderDamageAdjustedScore,
            String[] attackerHeroes, int[] attackerSkills, int[] attackerExclusive,
            String[] defenderHeroes, int[] defenderSkills, int[] defenderExclusive) {

        HeroCombatAccumulator.Totals attacker = HeroCombatAccumulator.collect(
                attackerHeroes, attackerSkills, attackerExclusive, false);
        HeroCombatAccumulator.Totals defender = HeroCombatAccumulator.collect(
                defenderHeroes, defenderSkills, defenderExclusive, true);

        double adjustedAttacker = attackerDamageAdjustedScore
                * HeroCombatScore.durabilityFactor(attacker.damageTakenReduction)
                * HeroCombatScore.enemyOutgoingFactor(defender.enemyDamageDealtReduction);
        double adjustedDefender = defenderDamageAdjustedScore
                * HeroCombatScore.durabilityFactor(defender.damageTakenReduction)
                * HeroCombatScore.enemyOutgoingFactor(attacker.enemyDamageDealtReduction);

        return new Result(adjustedAttacker, adjustedDefender, attacker, defender);
    }

    public static String summary(Result r) {
        if (r == null) return "Hero damage modifiers: unavailable";
        return String.format(java.util.Locale.US,
                "Hero damage modifiers — A: dealt %.1f%%, taken reduction %.1f%%, enemy dealt reduction %.1f%% | D: dealt %.1f%%, taken reduction %.1f%%, enemy dealt reduction %.1f%%",
                r.attacker.damageDealt, r.attacker.damageTakenReduction, r.attacker.enemyDamageDealtReduction,
                r.defender.damageDealt, r.defender.damageTakenReduction, r.defender.enemyDamageDealtReduction);
    }
}
