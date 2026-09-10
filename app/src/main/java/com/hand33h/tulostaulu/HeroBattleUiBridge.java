package com.hand33h.tulostaulu;

import android.widget.Spinner;

/** Converts Battle Simulator Spinner selections into validated verified hero scoring. */
public final class HeroBattleUiBridge {
    private HeroBattleUiBridge() {}

    public static final class CheckedResult {
        public final boolean safe;
        public final String blockingWarning;
        public final String accuracyNotice;
        public final HeroBattleScoreBridge.Result score;

        CheckedResult(boolean safe, String blockingWarning, String accuracyNotice,
                      HeroBattleScoreBridge.Result score) {
            this.safe = safe;
            this.blockingWarning = blockingWarning;
            this.accuracyNotice = accuracyNotice;
            this.score = score;
        }
    }

    public static CheckedResult validateAndApply(
            double attackerBaseScore, double defenderBaseScore,
            Spinner[] attackerHeroes, Spinner[] attackerSkills, Spinner[] attackerExclusive,
            Spinner[] defenderHeroes, Spinner[] defenderSkills, Spinner[] defenderExclusive) {
        HeroBattleDiagnostics.SelectionResult check = HeroBattlePreflight.validate(
                attackerHeroes, attackerSkills, defenderHeroes, defenderSkills);
        if (!check.safe) return new CheckedResult(false, check.blockingWarning, check.accuracyNotice, null);
        HeroBattleScoreBridge.Result score = apply(
                attackerBaseScore, defenderBaseScore,
                attackerHeroes, attackerSkills, attackerExclusive,
                defenderHeroes, defenderSkills, defenderExclusive);
        return new CheckedResult(true, null, check.accuracyNotice, score);
    }

    public static HeroBattleScoreBridge.Result apply(
            double attackerBaseScore, double defenderBaseScore,
            Spinner[] attackerHeroes, Spinner[] attackerSkills, Spinner[] attackerExclusive,
            Spinner[] defenderHeroes, Spinner[] defenderSkills, Spinner[] defenderExclusive) {
        return HeroBattleScoreBridge.apply(
                attackerBaseScore, defenderBaseScore,
                strings(attackerHeroes), ints(attackerSkills), ints(attackerExclusive),
                strings(defenderHeroes), ints(defenderSkills), ints(defenderExclusive));
    }

    private static String[] strings(Spinner[] spinners) {
        if (spinners == null) return new String[0];
        String[] out = new String[spinners.length];
        for (int i = 0; i < spinners.length; i++) {
            Object value = spinners[i] == null ? null : spinners[i].getSelectedItem();
            out[i] = value == null ? null : value.toString();
        }
        return out;
    }

    private static int[] ints(Spinner[] spinners) {
        if (spinners == null) return new int[0];
        int[] out = new int[spinners.length];
        for (int i = 0; i < spinners.length; i++) {
            Object value = spinners[i] == null ? null : spinners[i].getSelectedItem();
            out[i] = parse(value);
        }
        return out;
    }

    private static int parse(Object value) {
        if (value == null) return 0;
        try { return Integer.parseInt(value.toString().trim()); }
        catch (NumberFormatException ignored) { return 0; }
    }
}
