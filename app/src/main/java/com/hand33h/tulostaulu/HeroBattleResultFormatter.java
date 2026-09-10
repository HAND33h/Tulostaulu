package com.hand33h.tulostaulu;

import java.util.Locale;

/** Formats validated hero scoring details for the Battle Simulator result panel. */
public final class HeroBattleResultFormatter {
    private HeroBattleResultFormatter() {}

    public static String format(HeroBattleUiBridge.CheckedResult checked) {
        if (checked == null) return "Hero scoring unavailable";
        if (!checked.safe) return "⚠️ HERO SELECTION\n" + safe(checked.blockingWarning);
        if (checked.score == null) return "Hero scoring unavailable";

        StringBuilder out = new StringBuilder();
        out.append(HeroBattleScoreBridge.summary(checked.score));
        out.append(String.format(Locale.US,
                "\nAdjusted hero scores — A: %,.0f | D: %,.0f",
                checked.score.attackerScore, checked.score.defenderScore));
        if (checked.accuracyNotice != null && !checked.accuracyNotice.trim().isEmpty()) {
            out.append("\n\nAccuracy notice:\n").append(checked.accuracyNotice.trim());
        }
        return out.toString();
    }

    private static String safe(String value) {
        return value == null || value.trim().isEmpty() ? "Invalid hero selection" : value.trim();
    }
}
