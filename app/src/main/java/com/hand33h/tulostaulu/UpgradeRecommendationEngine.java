package com.hand33h.tulostaulu;

import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Local recommendation engine using only tables already verified and bundled in the app. */
public final class UpgradeRecommendationEngine {
    private UpgradeRecommendationEngine() {}

    public static final String[] FC_LEVEL={"Lv30","FC1","FC2","FC3","FC4","FC5","FC6","FC7","FC8","FC9","FC10"};
    private static final long[] FC_COST={0,660,790,1190,1400,1675,900,1080,1080,1260,1575};
    private static final long[] RFC_COST={0,0,0,0,0,0,60,90,120,180,420};

    public static final String[] CHARM_LEVEL={"Lv0","Lv1","Lv2","Lv3","Lv4","Lv5","Lv6","Lv7","Lv8","Lv9","Lv10","Lv11","Lv12","Lv13","Lv14","Lv15","Lv16","Lv17","Lv18"};
    private static final long[] CH_GUIDE={0,5,40,60,80,100,120,140,200,300,420,560,580,580,600,600,650,765,1300};
    private static final long[] CH_DESIGN={0,5,15,40,100,200,300,400,400,400,420,420,450,450,500,500,550,630,1130};
    private static final long[] CH_SECRET={0,0,0,0,0,0,0,0,0,0,0,0,15,30,45,70,100,135,180};
    private static final long[] CH_POWER={0,205700,288000,370000,452000,576000,700000,824000,948000,1072000,1196000,1320000,1444000,1568000,1692000,1816000,1940000,0,0};

    public static final String[] GEAR_LEVEL={
            "None","Green","Green ★","Blue","Blue ★","Blue ★★","Blue ★★★","Purple","Purple ★","Purple ★★","Purple ★★★",
            "Purple T1","Purple T1 ★","Purple T1 ★★","Purple T1 ★★★","Gold","Gold ★","Gold ★★","Gold ★★★","Gold T1","Gold T1 ★","Gold T1 ★★","Gold T1 ★★★",
            "Gold T2","Gold T2 ★","Gold T2 ★★","Gold T2 ★★★","Red","Red ★","Red ★★","Red ★★★","Red T1","Red T1 ★","Red T1 ★★","Red T1 ★★★",
            "Red T2","Red T2 ★","Red T2 ★★","Red T2 ★★★","Red T3","Red T3 ★","Red T3 ★★","Red T3 ★★★","Red T4","Red T4 ★","Red T4 ★★","Red T4 ★★★"};
    private static final long[] G_ALLOY={0,1500,3800,7000,9700,0,0,0,0,6500,8000,10000,11000,13000,15000,22000,23000,25000,26000,28000,30000,32000,35000,38000,43000,45000,48000,50000,52000,54000,56000,59000,61000,63000,65000,68000,70000,72000,74000,77000,80000,83000,86000,124000,140000,160000,180000};
    private static final long[] G_POLISH={0,15,40,70,95,0,0,0,0,65,80,95,110,130,160,220,230,250,260,280,300,320,340,390,430,460,500,530,560,590,620,670,700,730,760,810,840,870,900,950,990,1030,1070,1500,1650,1800,1950};
    private static final long[] G_PLAN={0,0,0,0,0,45,50,60,70,40,50,60,70,85,100,40,40,45,45,45,55,55,55,55,75,80,85,85,90,95,100,110,115,120,125,135,140,145,150,160,165,170,180,250,275,300,325};
    private static final long[] G_AMBER={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,10,10,10,15,15,15,15,20,20,20,20,25,25,25,25,40,40,40,40};
    private static final long[] G_POWER={0,224400,306000,408000,510000,612000,714000,816000,885360,954720,1024080,1093440,1162800,1232160,1301520,1362720,1423920,1485120,1546320,1607520,1668720,1729920,1791120,1852320,1913520,1974720,2040000,2142000,2244000,2346000,2448000,2550000,2652000,2754000,2856000,2958000,3060000,3162000,3264000,3366000,3468000,3570000,3672000,3876000,4080000,4284000,4488000};

    public static final class Candidate {
        public String title, details;
        public long powerGain;
        public boolean affordable;
        public int missingKinds;
        public double score;
    }

    public static List<Candidate> rank(SharedPreferences p, int gearLevel, int charmLevel, int fcLevel) {
        List<Candidate> out=new ArrayList<>();
        long alloy=v(p,"vault_alloy"),polish=v(p,"vault_polish"),plans=v(p,"vault_plans"),amber=v(p,"vault_amber");
        long guides=v(p,"vault_guides"),designs=v(p,"vault_designs"),secrets=v(p,"vault_secrets");
        long fc=v(p,"vault_fc"),rfc=v(p,"vault_rfc");

        if(gearLevel>=0 && gearLevel+1<GEAR_LEVEL.length){int z=gearLevel+1;Candidate c=new Candidate();c.title="Chief Gear → "+GEAR_LEVEL[z];c.powerGain=Math.max(0,G_POWER[z]-G_POWER[gearLevel]);c.missingKinds=miss(G_ALLOY[z],alloy)+miss(G_POLISH[z],polish)+miss(G_PLAN[z],plans)+miss(G_AMBER[z],amber);c.affordable=c.missingKinds==0;c.details=need("Alloy",G_ALLOY[z],alloy)+need("Polish",G_POLISH[z],polish)+need("Plans",G_PLAN[z],plans)+need("Amber",G_AMBER[z],amber);c.score=score(c);out.add(c);}
        if(charmLevel>=0 && charmLevel+1<CHARM_LEVEL.length){int z=charmLevel+1;Candidate c=new Candidate();c.title="Chief Charm → "+CHARM_LEVEL[z];c.powerGain=(z<=16?Math.max(0,CH_POWER[z]-CH_POWER[charmLevel]):0);c.missingKinds=miss(CH_GUIDE[z],guides)+miss(CH_DESIGN[z],designs)+miss(CH_SECRET[z],secrets);c.affordable=c.missingKinds==0;c.details=need("Guides",CH_GUIDE[z],guides)+need("Designs",CH_DESIGN[z],designs)+need("Secrets",CH_SECRET[z],secrets);c.score=score(c);out.add(c);}
        if(fcLevel>=0 && fcLevel+1<FC_LEVEL.length){int z=fcLevel+1;Candidate c=new Candidate();c.title="Furnace → "+FC_LEVEL[z];c.powerGain=0;c.missingKinds=miss(FC_COST[z],fc)+miss(RFC_COST[z],rfc);c.affordable=c.missingKinds==0;c.details=need("Fire Crystals",FC_COST[z],fc)+need("RFC",RFC_COST[z],rfc);c.score=score(c);out.add(c);}
        Collections.sort(out,new Comparator<Candidate>(){public int compare(Candidate a,Candidate b){return Double.compare(b.score,a.score);}});
        return out;
    }

    private static double score(Candidate c){double s=c.affordable?1_000_000:0;s+=Math.min(c.powerGain,900_000);s-=c.missingKinds*100_000;return s;}
    private static int miss(long need,long have){return need>have?1:0;}
    private static String need(String n,long need,long have){return n+": "+need+" (missing "+Math.max(0,need-have)+")\n";}
    private static long v(SharedPreferences p,String k){try{return Long.parseLong(p.getString(k,"0").replaceAll("[^0-9]",""));}catch(Exception e){return 0;}}
}
