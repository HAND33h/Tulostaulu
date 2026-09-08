package com.hand33h.tulostaulu;

/**
 * Hero Gear progression metadata for Battle Simulator.
 * Verified progression rules: Grey/Green/Blue/Purple -> Mythic (Gold) -> Legendary (Red).
 * Mythic Mastery Forging unlocks at Furnace 20 + gear Enhancement 20.
 * Legendary ascension requires Mythic Enhancement 100 + Mastery 10; the new Legendary piece
 * restarts Enhancement at 1 while retaining prior stats. Empowerment milestones are +20/+40/+60/+80/+100.
 *
 * This class intentionally does not invent per-piece Command percentages where the exact base item table
 * is not available in structured form. Those values remain additive manual verified inputs in the simulator.
 */
public final class HeroGearData {
    private HeroGearData() {}

    public static final String[] QUALITY={"None","Grey","Green","Blue","Purple","Mythic (Gold)","Legendary (Red)"};
    public static final String[] ENHANCEMENT=buildLevels(100);
    public static final String[] MASTERY=buildLevels(20);
    public static final String[] ASCENSION={"0","20","40","60","80","100"};

    // Official/official-wiki published Mastery forging bonus checkpoints that are safe to expose exactly.
    // Level 4+ has sub-stages; the app tracks the selected integer Mastery level separately.
    public static double verifiedMasteryCheckpointPct(int mastery){
        if(mastery<=0)return 0;
        if(mastery==1)return 10;
        if(mastery==2)return 20;
        if(mastery==3)return 30;
        if(mastery==4)return 40;
        if(mastery==5)return 50;
        if(mastery==6)return 60;
        if(mastery==7)return 70;
        return Double.NaN; // later levels exist; do not invent a percentage from incomplete published text.
    }

    public static String validate(String quality,int enhancement,int mastery,int ascension){
        if("None".equals(quality))return null;
        boolean mythic="Mythic (Gold)".equals(quality), legendary="Legendary (Red)".equals(quality);
        if(!mythic&&!legendary && (mastery>0||ascension>0))return "Mastery/Ascension only applies to Mythic/Legendary Hero Gear.";
        if(mythic && mastery>0 && enhancement<20)return "Mythic Mastery requires Enhancement Lv20+.";
        if(mythic && mastery>10)return "Mythic Mastery is capped at Lv10 before Legendary ascension.";
        if(mythic && ascension>0)return "Empowerment/Ascension is Legendary-only.";
        if(legendary && mastery<10)return "Legendary gear should retain at least Mastery Lv10 from ascension.";
        if(!legendary && ascension>0)return "Ascension milestone requires Legendary gear.";
        return null;
    }

    public static String summary(String quality,int enhancement,int mastery,int ascension){
        if("None".equals(quality))return "None";
        StringBuilder s=new StringBuilder(quality).append(" E").append(enhancement);
        if(mastery>0)s.append(" M").append(mastery);
        if(ascension>0)s.append(" +").append(ascension);
        double pct=verifiedMasteryCheckpointPct(mastery);
        if(!Double.isNaN(pct)&&mastery>0)s.append(" (Mastery checkpoint +").append((int)pct).append("% gear stats)");
        return s.toString();
    }

    private static String[] buildLevels(int max){String[] a=new String[max+1];for(int i=0;i<=max;i++)a[i]=String.valueOf(i);return a;}
}
