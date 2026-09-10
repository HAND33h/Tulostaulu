package com.hand33h.tulostaulu;

/**
 * Pre-simulation guard for both Expedition lineups.
 * Keeps invalid hero selections out of the scoring path without changing verified combat values.
 */
public final class HeroBattleSelectionGuard {
    private HeroBattleSelectionGuard() {}

    public static String validate(String[] attacker, String[] defender) {
        String attackerWarning = validateSide("Attacker", attacker);
        if (attackerWarning != null) return attackerWarning;
        return validateSide("Defender", defender);
    }

    public static boolean isSafe(String[] attacker, String[] defender) {
        return validate(attacker, defender) == null;
    }

    private static String validateSide(String label, String[] heroes) {
        if (heroes == null) return label + " hero selection is missing";
        String warning = HeroBattleDiagnostics.selectionWarning(heroes);
        return warning == null ? null : label + ": " + warning;
    }
}
