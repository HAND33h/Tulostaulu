package com.hand33h.tulostaulu;

import java.util.Locale;

/** Formats validated hero scoring details for the Battle Simulator result panel. */
public final class HeroBattleResultFormatter {
    private HeroBattleResultFormatter() {}

    public static String format(HeroBattleUiBridge.CheckedResult checked) {
        if (checked == null) return "Hero scoring unavailable";
        if (!checked.safe) return "⚠️ HERO SELECTION\n" + safe(checked.blockingWarning);
        if (checked.score == null) return "Hero scoring unavailable";

        double aDurability = HeroCombatScore.durabilityFactor(checked.score.attacker.damageTakenReduction);
        double aEnemyOutgoing = HeroCombatScore.enemyOutgoingFactor(checked.score.defender.enemyDamageDealtReduction);
        double dDurability = HeroCombatScore.durabilityFactor(checked.score.defender.damageTakenReduction);
        double dEnemyOutgoing = HeroCombatScore.enemyOutgoingFactor(checked.score.attacker.enemyDamageDealtReduction);
        double aNet = aDurability * aEnemyOutgoing;
        double dNet = dDurability * dEnemyOutgoing;

        StringBuilder out = new StringBuilder();
        out.append(HeroBattleScoreBridge.summary(checked.score));
        out.append(String.format(Locale.US,
                "\nFinal hero-adjusted scores — A: %,.0f | D: %,.0f",
                checked.score.attackerScore, checked.score.defenderScore));
        out.append(String.format(Locale.US,
                "\nReduction factors — A durability ×%.3f, enemy outgoing ×%.3f | D durability ×%.3f, enemy outgoing ×%.3f",
                aDurability, aEnemyOutgoing, dDurability, dEnemyOutgoing));
        out.append(String.format(Locale.US,
                "\nNet reduction multiplier — A ×%.3f (%+.1f%%) | D ×%.3f (%+.1f%%)",
                aNet, (aNet - 1.0) * 100.0, dNet, (dNet - 1.0) * 100.0));
        out.append("\nDamage Dealt is included by armyScore; reduction pipeline does not apply it twice.");
        if (checked.accuracyNotice != null && !checked.accuracyNotice.trim().isEmpty()) {
            out.append("\n\nAccuracy notice:\n").append(checked.accuracyNotice.trim());
        }
        return out.toString();
    }

    private static String safe(String value) {
        return value == null || value.trim().isEmpty() ? "Invalid hero selection" : value.trim();
    }
}
