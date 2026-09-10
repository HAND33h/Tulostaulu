package com.hand33h.tulostaulu;

import android.widget.Spinner;

/** Converts Battle Simulator Spinner selections into the verified hero scoring bridge. */
public final class HeroBattleUiBridge {
    private HeroBattleUiBridge() {}

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
