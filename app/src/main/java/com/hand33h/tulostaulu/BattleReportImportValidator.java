package com.hand33h.tulostaulu;

/** Safety checks for WOS battle-report screenshot/manual imports before simulator use. */
public final class BattleReportImportValidator {
    private BattleReportImportValidator() {}

    public static final class Result {
        public final boolean usable;
        public final String message;
        Result(boolean usable, String message) {
            this.usable = usable;
            this.message = message;
        }
    }

    public static Result validate(BattleReportSnapshot report) {
        if (report == null) return fail("Battle report is missing.");
        if (!report.hasUsableStats()) return fail("All 24 troop stat bonuses are required before simulation.");
        if (report.attacker.troops <= 0 || report.defender.troops <= 0)
            return fail("Both sides need a valid troop count.");
        if (!report.attacker.troopOutcomeIsConsistent())
            return fail("Attacker outcome totals do not match the attacker troop count.");
        if (!report.defender.troopOutcomeIsConsistent())
            return fail("Defender outcome totals do not match the defender troop count.");
        return new Result(true, "Battle report data passed import validation.");
    }

    /** Allows stat-only report pages to populate simulator inputs before outcome pages are imported. */
    public static Result validateStatsOnly(BattleReportSnapshot report) {
        if (report == null) return fail("Battle report is missing.");
        if (!report.hasUsableStats()) return fail("All 24 troop stat bonuses have not been captured yet.");
        return new Result(true, "Battle report stat bonuses are ready for simulator import.");
    }

    private static Result fail(String message) {
        return new Result(false, message);
    }
}
