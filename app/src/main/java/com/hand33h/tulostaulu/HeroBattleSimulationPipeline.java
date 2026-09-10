package com.hand33h.tulostaulu;

import android.widget.Spinner;

/**
 * Single entry point for Battle Simulator hero validation, permanent scoring
 * and result diagnostics. BattleSimulatorActivity's armyScore already includes
 * hero Damage Dealt, so this pipeline deliberately applies reductions only.
 */
public final class HeroBattleSimulationPipeline {
    private HeroBattleSimulationPipeline() {}

    public static final class Output {
        public final boolean safe;
        public final double attackerScore;
        public final double defenderScore;
        public final String message;

        Output(boolean safe, double attackerScore, double defenderScore, String message) {
            this.safe = safe;
            this.attackerScore = attackerScore;
            this.defenderScore = defenderScore;
            this.message = message;
        }
    }

    public static Output run(
            double attackerDamageAdjustedScore, double defenderDamageAdjustedScore,
            Spinner[] attackerHeroes, Spinner[] attackerSkills, Spinner[] attackerExclusive,
            Spinner[] defenderHeroes, Spinner[] defenderSkills, Spinner[] defenderExclusive) {

        if (!validBaseScore(attackerDamageAdjustedScore) || !validBaseScore(defenderDamageAdjustedScore)) {
            return new Output(false, attackerDamageAdjustedScore, defenderDamageAdjustedScore,
                    "⚠️ HERO SCORE\nBase battle score is invalid; simulation stopped before hero reductions.");
        }

        HeroBattleUiBridge.CheckedResult checked = HeroBattleUiBridge.validateAndApplyReductions(
                attackerDamageAdjustedScore, defenderDamageAdjustedScore,
                attackerHeroes, attackerSkills, attackerExclusive,
                defenderHeroes, defenderSkills, defenderExclusive);

        if (!checked.safe || checked.score == null) {
            return new Output(false, attackerDamageAdjustedScore, defenderDamageAdjustedScore,
                    HeroBattleResultFormatter.format(checked));
        }

        double attacker = checked.score.attackerScore;
        double defender = checked.score.defenderScore;
        if (!validAdjustedScore(attacker) || !validAdjustedScore(defender) || attacker + defender <= 0.0) {
            return new Output(false, attackerDamageAdjustedScore, defenderDamageAdjustedScore,
                    "⚠️ HERO SCORE\nHero-adjusted score is invalid; result was not used.");
        }

        return new Output(true, attacker, defender, HeroBattleResultFormatter.format(checked));
    }

    private static boolean validBaseScore(double value) {
        return Double.isFinite(value) && value >= 0.0;
    }

    private static boolean validAdjustedScore(double value) {
        return Double.isFinite(value) && value >= 0.0;
    }
}
