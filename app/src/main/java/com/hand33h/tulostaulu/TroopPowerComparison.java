package com.hand33h.tulostaulu;

/**
 * Normalized Troop Power Comparison data from a WOS battle report.
 * Keeps raw troop counts, average troop level, optional FC/tier indicator,
 * and formation share separate from aggregate Stat Bonuses.
 */
public final class TroopPowerComparison {
    public static final int INFANTRY = 0;
    public static final int LANCER = 1;
    public static final int MARKSMAN = 2;

    public static final class TroopClass {
        public long count;
        public double averageLevel;

        /**
         * Optional small tier/Fire Crystal indicator shown in some reports.
         * Zero means unknown/not captured; do not infer it from averageLevel.
         */
        public double tierIndicator;

        /** Formation share in percent (0..100). Negative means not captured. */
        public double formationPercent = -1.0;

        public boolean hasCount() { return count > 0; }
        public boolean hasAverageLevel() { return averageLevel > 0; }
        public boolean hasTierIndicator() { return tierIndicator > 0; }
        public boolean hasFormationPercent() {
            return formationPercent >= 0.0 && formationPercent <= 100.0;
        }
    }

    public static final class Side {
        public final TroopClass[] troops = {
                new TroopClass(), new TroopClass(), new TroopClass()
        };

        public long totalCount() {
            long total = 0;
            for (TroopClass troop : troops) total += troop.count;
            return total;
        }

        public void calculateFormationPercentages() {
            long total = totalCount();
            if (total <= 0) return;
            for (TroopClass troop : troops) {
                troop.formationPercent = 100.0 * troop.count / total;
            }
        }

        public boolean countMatches(long expectedTotal) {
            return expectedTotal >= 0 && totalCount() == expectedTotal;
        }
    }

    public final Side attacker = new Side();
    public final Side defender = new Side();

    /**
     * Cross-checks Troop Power counts against Battle Overview totals when both
     * sections were captured. This is useful for rejecting OCR mix-ups.
     */
    public boolean matchesOverview(BattleReportSnapshot snapshot) {
        if (snapshot == null) return false;
        return attacker.countMatches(snapshot.attacker.troops)
                && defender.countMatches(snapshot.defender.troops);
    }
}
