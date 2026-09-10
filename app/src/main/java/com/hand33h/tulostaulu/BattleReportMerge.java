package com.hand33h.tulostaulu;

/** Combines OCR/manual text from multiple screenshots of one WOS battle report. */
public final class BattleReportMerge {
    private BattleReportMerge() {}

    public static BattleReportSnapshot fromPages(String... pages) {
        BattleReportSnapshot merged = new BattleReportSnapshot();
        if (pages == null) return merged;

        for (String page : pages) {
            if (page == null || page.trim().isEmpty()) continue;
            BattleReportSnapshot stats = BattleReportTextParser.parseStatBonuses(page);
            mergeStats(merged, stats);
            BattleReportOutcomeParser.apply(page, merged);
        }
        return merged;
    }

    private static void mergeStats(BattleReportSnapshot target, BattleReportSnapshot source) {
        for (int i = 0; i < 3; i++) {
            mergeTroop(target.attacker.stats[i], source.attacker.stats[i]);
            mergeTroop(target.defender.stats[i], source.defender.stats[i]);
        }
        if ((target.sourceMailId == null || target.sourceMailId.isEmpty()) && source.sourceMailId != null)
            target.sourceMailId = source.sourceMailId;
    }

    private static void mergeTroop(BattleReportSnapshot.TroopStats target, BattleReportSnapshot.TroopStats source) {
        if (source.attack > 0) target.attack = source.attack;
        if (source.defense > 0) target.defense = source.defense;
        if (source.lethality > 0) target.lethality = source.lethality;
        if (source.health > 0) target.health = source.health;
    }

    public static BattleReportImportValidator.Result validate(String... pages) {
        return BattleReportImportValidator.validate(fromPages(pages));
    }
}
