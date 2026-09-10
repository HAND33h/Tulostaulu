package com.hand33h.tulostaulu;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Human-readable diagnostics for the Battle Simulator hero model.
 * Intended for debug/about screens, live selection warnings and pre-release validation.
 */
public final class HeroBattleDiagnostics {
    private HeroBattleDiagnostics() {}

    public static boolean isHealthy() {
        return HeroBattleCoverageValidation.missingSkillCoverage().isEmpty()
                && HeroBattleCoverageValidation.nonMonotonicSkillScaling().isEmpty();
    }

    /** Compact status suitable for a simulator header/banner. */
    public static String statusLine() {
        return (isHealthy() ? "Hero model OK — " : "Hero model warning — ")
                + HeroBattleCoverageValidation.coverageSummary();
    }

    /**
     * Validates the three Expedition hero slots before simulation.
     * Duplicate heroes are rejected and any selected hero without verified skill handling is surfaced.
     */
    public static String selectionWarning(String... selectedHeroes) {
        if (selectedHeroes == null || selectedHeroes.length == 0) return null;
        Set<String> seen = new HashSet<>();
        List<String> missing = HeroBattleCoverageValidation.missingSkillCoverage();
        for (String selected : selectedHeroes) {
            String name = HeroBattleData.nameOf(selected);
            if (name == null || "None".equals(name)) continue;
            if (!seen.add(name)) return "Duplicate hero selected: " + name;
            if (missing.contains(name)) return "Hero skill handling is not yet verified: " + name;
        }
        return null;
    }

    /** Returns true when a three-slot Expedition selection is safe for aggregate scoring. */
    public static boolean selectionIsSafe(String... selectedHeroes) {
        return selectionWarning(selectedHeroes) == null;
    }

    public static String report() {
        StringBuilder out = new StringBuilder();
        out.append(statusLine());
        append(out, "Missing", HeroBattleCoverageValidation.missingSkillCoverage());
        append(out, "Scaling warnings", HeroBattleCoverageValidation.nonMonotonicSkillScaling());
        append(out, "Conditional-only", HeroBattleCoverageValidation.conditionalOnlySkillCoverage());
        append(out, "Side-sensitive", HeroBattleCoverageValidation.sideSensitiveSkillCoverage());
        return out.toString();
    }

    private static void append(StringBuilder out, String label, List<String> heroes) {
        out.append("\n").append(label).append(": ");
        if (heroes == null || heroes.isEmpty()) {
            out.append("none");
            return;
        }
        for (int i = 0; i < heroes.size(); i++) {
            if (i > 0) out.append(", ");
            out.append(heroes.get(i));
        }
    }
}
