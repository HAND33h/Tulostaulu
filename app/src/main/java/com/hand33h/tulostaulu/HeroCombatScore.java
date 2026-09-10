package com.hand33h.tulostaulu;

/**
 * Central math for verified permanent hero damage modifiers.
 * Conditional/proc/turn effects stay outside this aggregate score.
 */
public final class HeroCombatScore {
    private HeroCombatScore() {}

    /** Converts Damage Dealt bonus to a multiplicative outgoing-damage factor. */
    public static double outgoingFactor(double damageDealtPercent) {
        return positiveFactor(damageDealtPercent);
    }

    /** Converts own Damage Taken Reduction to an effective durability multiplier. */
    public static double durabilityFactor(double damageTakenReductionPercent) {
        double reduction = clampReduction(damageTakenReductionPercent);
        return 1.0 / (1.0 - reduction / 100.0);
    }

    /**
     * Enemy Damage Dealt Reduction is applied to the opponent's outgoing damage.
     * A positive 20% reduction therefore produces factor 0.80.
     */
    public static double enemyOutgoingFactor(double enemyDamageDealtReductionPercent) {
        double reduction = clampReduction(enemyDamageDealtReductionPercent);
        return 1.0 - reduction / 100.0;
    }

    /** Combined permanent aggregate multiplier for one side. */
    public static double aggregateFactor(
            double ownDamageDealtPercent,
            double ownDamageTakenReductionPercent,
            double enemyDamageDealtReductionPercentAppliedToThisSide) {
        return outgoingFactor(ownDamageDealtPercent)
                * durabilityFactor(ownDamageTakenReductionPercent)
                * enemyOutgoingFactor(enemyDamageDealtReductionPercentAppliedToThisSide);
    }

    private static double positiveFactor(double percent) {
        return Math.max(0.05, 1.0 + percent / 100.0);
    }

    /** Avoid divide-by-zero / negative durability while retaining extreme inputs safely. */
    private static double clampReduction(double percent) {
        return Math.max(-95.0, Math.min(95.0, percent));
    }
}
