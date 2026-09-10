package com.hand33h.tulostaulu;

import android.widget.Spinner;

/**
 * Single entry point for Battle Simulator hero validation, permanent scoring
 * and result diagnostics. Keeps Activity integration small and auditable.
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
            double attackerBaseScore, double defenderBaseScore,
            Spinner[] attackerHeroes, Spinner[] attackerSkills, Spinner[] attackerExclusive,
            Spinner[] defenderHeroes, Spinner[] defenderSkills, Spinner[] defenderExclusive) {

        HeroBattleUiBridge.CheckedResult checked = HeroBattleUiBridge.validateAndApply(
                attackerBaseScore, defenderBaseScore,
                attackerHeroes, attackerSkills, attackerExclusive,
                defenderHeroes, defenderSkills, defenderExclusive);

        if (!checked.safe || checked.score == null) {
            return new Output(false, attackerBaseScore, defenderBaseScore,
                    HeroBattleResultFormatter.format(checked));
        }

        return new Output(true,
                checked.score.attackerScore,
                checked.score.defenderScore,
                HeroBattleResultFormatter.format(checked));
    }
}
