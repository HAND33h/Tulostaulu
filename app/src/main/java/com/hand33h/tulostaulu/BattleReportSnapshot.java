package com.hand33h.tulostaulu;

/**
 * Normalized data extracted from a Whiteout Survival battle report screenshot.
 * Keeps screenshot/import data separate from the simulator math so OCR or manual
 * parsing can be added without changing battle scoring.
 */
public final class BattleReportSnapshot {
    public static final int INFANTRY = 0;
    public static final int LANCER = 1;
    public static final int MARKSMAN = 2;

    public static final class TroopStats {
        public double attack;
        public double defense;
        public double lethality;
        public double health;
    }

    public static final class Side {
        public long troops;
        public long losses;
        public long injured;
        public long lightlyInjured;
        public long survivors;
        public final TroopStats[] stats = {
                new TroopStats(), new TroopStats(), new TroopStats()
        };

        public boolean troopOutcomeIsConsistent() {
            if (troops < 0 || losses < 0 || injured < 0 || lightlyInjured < 0 || survivors < 0) return false;
            return losses + injured + lightlyInjured + survivors == troops;
        }
    }

    public final Side attacker = new Side();
    public final Side defender = new Side();
    public String sourceMailId;

    public boolean hasUsableStats() {
        return sideHasStats(attacker) && sideHasStats(defender);
    }

    private static boolean sideHasStats(Side side) {
        if (side == null) return false;
        for (TroopStats troop : side.stats) {
            if (troop.attack <= 0 || troop.defense <= 0 || troop.lethality <= 0 || troop.health <= 0) return false;
        }
        return true;
    }
}
