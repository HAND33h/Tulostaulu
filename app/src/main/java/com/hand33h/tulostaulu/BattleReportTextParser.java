package com.hand33h.tulostaulu;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Parses OCR/manual text from the WOS Stat Bonuses report page into simulator data. */
public final class BattleReportTextParser {
    private BattleReportTextParser() {}

    private static final String[] TROOPS = {"Infantry", "Lancer", "Marksman"};
    private static final String[] STATS = {"Attack", "Defense", "Lethality", "Health"};

    public static BattleReportSnapshot parseStatBonuses(String text) {
        BattleReportSnapshot report = new BattleReportSnapshot();
        if (text == null) return report;

        for (int troop = 0; troop < TROOPS.length; troop++) {
            for (int stat = 0; stat < STATS.length; stat++) {
                double[] pair = findPair(text, TROOPS[troop] + "\\s+" + STATS[stat]);
                if (pair == null) continue;
                set(report.attacker.stats[troop], stat, pair[0]);
                set(report.defender.stats[troop], stat, pair[1]);
            }
        }
        return report;
    }

    private static double[] findPair(String text, String labelRegex) {
        // Supports either: +2463.7% Infantry Attack +1333.4%
        // or OCR output where the label appears before the two percentages.
        Pattern leftRight = Pattern.compile("([+-]?\\d+(?:[.,]\\d+)?)%\\s+" + labelRegex + "\\s+([+-]?\\d+(?:[.,]\\d+)?)%", Pattern.CASE_INSENSITIVE);
        Matcher m = leftRight.matcher(text);
        if (m.find()) return new double[]{number(m.group(1)), number(m.group(2))};

        Pattern labelFirst = Pattern.compile(labelRegex + "\\s+([+-]?\\d+(?:[.,]\\d+)?)%\\s+([+-]?\\d+(?:[.,]\\d+)?)%", Pattern.CASE_INSENSITIVE);
        m = labelFirst.matcher(text);
        if (m.find()) return new double[]{number(m.group(1)), number(m.group(2))};
        return null;
    }

    private static double number(String value) {
        try { return Double.parseDouble(value.replace(',', '.')); }
        catch (RuntimeException ignored) { return 0.0; }
    }

    private static void set(BattleReportSnapshot.TroopStats target, int stat, double value) {
        switch (stat) {
            case 0: target.attack = value; break;
            case 1: target.defense = value; break;
            case 2: target.lethality = value; break;
            case 3: target.health = value; break;
            default: throw new IllegalArgumentException(String.format(Locale.US, "Unknown stat %d", stat));
        }
    }
}
