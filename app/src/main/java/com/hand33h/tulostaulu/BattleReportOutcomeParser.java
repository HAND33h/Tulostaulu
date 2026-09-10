package com.hand33h.tulostaulu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Parses Battle Overview troop outcome rows from OCR/manual WOS report text. */
public final class BattleReportOutcomeParser {
    private BattleReportOutcomeParser() {}

    public static void apply(String text, BattleReportSnapshot report) {
        if (text == null || report == null) return;
        long[] troops = pair(text, "Troops");
        long[] losses = pair(text, "Losses");
        long[] injured = pair(text, "Injured");
        long[] light = pair(text, "Lightly\\s+Injured");
        long[] survivors = pair(text, "Survivors");
        if (troops != null) setTroops(report, troops);
        if (losses != null) { report.attacker.losses=losses[0]; report.defender.losses=losses[1]; }
        if (injured != null) { report.attacker.injured=injured[0]; report.defender.injured=injured[1]; }
        if (light != null) { report.attacker.lightlyInjured=light[0]; report.defender.lightlyInjured=light[1]; }
        if (survivors != null) { report.attacker.survivors=survivors[0]; report.defender.survivors=survivors[1]; }
        Matcher id = Pattern.compile("Mail\\s*ID\\s*[:#]?\\s*([0-9]{6,})", Pattern.CASE_INSENSITIVE).matcher(text);
        if (id.find()) report.sourceMailId=id.group(1);
    }

    private static void setTroops(BattleReportSnapshot report,long[] v){report.attacker.troops=v[0];report.defender.troops=v[1];}

    private static long[] pair(String text,String label){
        String n="([0-9][0-9, .]*)";
        Matcher m=Pattern.compile(n+"\\s+"+label+"\\s+"+n,Pattern.CASE_INSENSITIVE).matcher(text);
        if(m.find()) return new long[]{number(m.group(1)),number(m.group(2))};
        m=Pattern.compile(label+"\\s+"+n+"\\s+"+n,Pattern.CASE_INSENSITIVE).matcher(text);
        if(m.find()) return new long[]{number(m.group(1)),number(m.group(2))};
        return null;
    }

    private static long number(String value){
        try{return Long.parseLong(value.replaceAll("[^0-9]",""));}
        catch(RuntimeException ignored){return 0L;}
    }
}
