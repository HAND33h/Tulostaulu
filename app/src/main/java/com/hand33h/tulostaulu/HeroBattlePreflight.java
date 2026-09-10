package com.hand33h.tulostaulu;

import android.widget.Spinner;

/**
 * UI adapter for Battle Simulator hero pre-flight validation.
 * Keeps Spinner parsing out of the verified hero model and makes the next Activity wiring minimal.
 */
public final class HeroBattlePreflight {
    private HeroBattlePreflight() {}

    public static HeroBattleDiagnostics.SelectionResult validate(
            Spinner[] attackerHeroes, Spinner[] attackerSkills,
            Spinner[] defenderHeroes, Spinner[] defenderSkills) {
        return HeroBattleDiagnostics.validateBattleSelections(
                selectedHeroes(attackerHeroes), selectedLevels(attackerSkills),
                selectedHeroes(defenderHeroes), selectedLevels(defenderSkills));
    }

    public static String accuracyNotice(
            Spinner[] attackerHeroes, Spinner[] attackerSkills,
            Spinner[] defenderHeroes, Spinner[] defenderSkills) {
        HeroBattleDiagnostics.SelectionResult result = validate(
                attackerHeroes, attackerSkills, defenderHeroes, defenderSkills);
        return result.accuracyNotice;
    }

    private static String[] selectedHeroes(Spinner[] spinners) {
        if (spinners == null) return new String[0];
        String[] values = new String[spinners.length];
        for (int i = 0; i < spinners.length; i++) {
            Object selected = spinners[i] == null ? null : spinners[i].getSelectedItem();
            values[i] = selected == null ? null : selected.toString();
        }
        return values;
    }

    private static int[] selectedLevels(Spinner[] spinners) {
        if (spinners == null) return new int[0];
        int[] values = new int[spinners.length];
        for (int i = 0; i < spinners.length; i++) {
            Object selected = spinners[i] == null ? null : spinners[i].getSelectedItem();
            values[i] = parseLevel(selected);
        }
        return values;
    }

    private static int parseLevel(Object selected) {
        if (selected == null) return 1;
        try {
            return Math.max(1, Math.min(5, Integer.parseInt(selected.toString().trim())));
        } catch (NumberFormatException ignored) {
            return 1;
        }
    }
}
