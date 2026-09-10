package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Calibration dataset for real battle reports from user captures and public sources. */
public final class BattleReportDataset {
    public enum SourceType { USER_REPORT, REDDIT, OFFICIAL, COMMUNITY, OTHER }

    public static final class Case {
        public final String id;
        public final SourceType sourceType;
        public final String source;
        public final BattleReportSnapshot snapshot;
        public final BattleReportDetails details;
        public final boolean verifiedComplete;
        public final String notes;

        public Case(String id, SourceType sourceType, String source,
                    BattleReportSnapshot snapshot, BattleReportDetails details,
                    boolean verifiedComplete, String notes) {
            this.id = id;
            this.sourceType = sourceType;
            this.source = source;
            this.snapshot = snapshot;
            this.details = details;
            this.verifiedComplete = verifiedComplete;
            this.notes = notes;
        }
    }

    private final List<Case> cases = new ArrayList<>();

    public void add(Case report) {
        if (report == null || report.id == null || report.id.trim().isEmpty())
            throw new IllegalArgumentException("Report id required");
        for (Case c : cases) if (c.id.equals(report.id))
            throw new IllegalArgumentException("Duplicate report id: " + report.id);
        cases.add(report);
    }

    public List<Case> all() { return Collections.unmodifiableList(cases); }

    /** Only complete reports may be used as deterministic formula calibration targets. */
    public List<Case> calibrationCases() {
        List<Case> out = new ArrayList<>();
        for (Case c : cases) {
            if (c.verifiedComplete && c.snapshot != null &&
                    BattleReportImportValidator.validate(c.snapshot).usable) out.add(c);
        }
        return Collections.unmodifiableList(out);
    }
}
