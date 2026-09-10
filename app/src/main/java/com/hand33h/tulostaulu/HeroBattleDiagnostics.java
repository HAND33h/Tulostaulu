package com.hand33h.tulostaulu;

import java.util.List;

/**
 * Human-readable diagnostics for the Battle Simulator hero model.
 * Intended for debug/about screens and pre-release validation.
 */
public final class HeroBattleDiagnostics {
    private HeroBattleDiagnostics() {}

    public static boolean isHealthy() {
        return HeroBattleCoverageValidation.missingSkillCoverage().isEmpty()
                && HeroBattleCoverageValidation.nonMonotonicSkillScaling().isEmpty();
    }

    public static String report() {
        StringBuilder out = new StringBuilder();
        out.append(HeroBattleCoverageValidation.coverageSummary());
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
