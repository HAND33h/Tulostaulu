package com.hand33h.tulostaulu;

import java.util.HashSet;
import java.util.Set;

/** Tracks public/user report provenance and deduplicates a large calibration corpus. */
public final class BattleReportSourceRegistry {
    public static final int TARGET_REPORTS = 15000;
    private final Set<String> fingerprints = new HashSet<>();
    private int accepted;
    private int partial;
    private int rejected;

    public enum Origin { USER, REDDIT, YOUTUBE, OFFICIAL, COMMUNITY, OTHER }

    public boolean register(Origin origin, String sourceId, String mailId,
                            BattleReportSnapshot snapshot, boolean complete) {
        if (origin == null || sourceId == null || sourceId.trim().isEmpty()) {
            rejected++; return false;
        }
        String key = fingerprint(origin, sourceId, mailId, snapshot);
        if (!fingerprints.add(key)) { rejected++; return false; }
        if (!complete || snapshot == null || !BattleReportImportValidator.validate(snapshot).usable) {
            partial++; return false;
        }
        accepted++;
        return true;
    }

    public int accepted() { return accepted; }
    public int partial() { return partial; }
    public int rejected() { return rejected; }
    public int remaining() { return Math.max(0, TARGET_REPORTS - accepted); }
    public double progressPercent() { return 100.0 * accepted / TARGET_REPORTS; }

    private static String fingerprint(Origin origin, String sourceId, String mailId,
                                      BattleReportSnapshot s) {
        if (mailId != null && !mailId.trim().isEmpty()) return "mail:" + mailId.trim();
        StringBuilder b = new StringBuilder(origin.name()).append('|').append(sourceId.trim());
        if (s != null) b.append('|').append(s.attacker.troops).append('|').append(s.defender.troops)
                .append('|').append(s.attacker.injured).append('|').append(s.defender.injured)
                .append('|').append(s.attacker.survivors).append('|').append(s.defender.survivors);
        return b.toString();
    }
}
