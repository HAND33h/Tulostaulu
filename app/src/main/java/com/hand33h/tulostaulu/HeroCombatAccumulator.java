package com.hand33h.tulostaulu;

/**
 * Accumulates permanent hero combat modifiers before Battle Simulator scoring.
 * Conditional/proc/turn mechanics remain represented separately by HeroConditionalEffects.
 */
public final class HeroCombatAccumulator {
    private HeroCombatAccumulator() {}

    public static final class Totals {
        public double damageDealt;
        public double damageTakenReduction;
        public double enemyDamageDealtReduction;

        public void add(HeroBattleData.Effect effect) {
            if (effect == null) return;
            damageDealt += effect.damageDealt;
            damageTakenReduction += effect.damageTakenReduction;
            enemyDamageDealtReduction += effect.enemyDamageDealtReduction;
        }

        public double scoreFactor(double enemyReductionAppliedToThisSide) {
            return HeroCombatScore.aggregateFactor(
                    damageDealt,
                    damageTakenReduction,
                    enemyReductionAppliedToThisSide);
        }
    }

    public static Totals collect(String[] heroes, int[] skillLevels, int[] exclusiveLevels, boolean defenderSide) {
        Totals totals = new Totals();
        if (heroes == null) return totals;
        for (int i = 0; i < heroes.length; i++) {
            String hero = heroes[i];
            if (hero == null || "None".equals(HeroBattleData.nameOf(hero))) continue;
            int skill = valueAt(skillLevels, i, 5);
            int exclusive = valueAt(exclusiveLevels, i, 0);
            totals.add(HeroBattleData.directEffect(hero, skill, exclusive, defenderSide));
        }
        return totals;
    }

    /** Applies both sides' permanent damage modifiers to their existing aggregate scores. */
    public static double[] applyToScores(double attackerScore, double defenderScore, Totals attacker, Totals defender) {
        if (attacker == null) attacker = new Totals();
        if (defender == null) defender = new Totals();
        double adjustedAttacker = attackerScore * attacker.scoreFactor(defender.enemyDamageDealtReduction);
        double adjustedDefender = defenderScore * defender.scoreFactor(attacker.enemyDamageDealtReduction);
        return new double[]{adjustedAttacker, adjustedDefender};
    }

    private static int valueAt(int[] values, int index, int fallback) {
        return values != null && index < values.length ? values[index] : fallback;
    }
}
