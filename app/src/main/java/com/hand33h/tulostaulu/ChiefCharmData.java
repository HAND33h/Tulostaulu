package com.hand33h.tulostaulu;

/**
 * Chief Charm progression, checked against WOS Forge 2026-09-09.
 * Values are per single charm and the published stat is Health & Lethality.
 * There are 18 charm slots (6 Chief Gear pieces x 3).
 */
public final class ChiefCharmData {
    private ChiefCharmData() {}

    public static final String SOURCE_URL = "https://wiki.wosforge.org/wiki/Chief_Charms";
    public static final int SLOT_COUNT = 18;

    public static final class Level {
        public final int level;
        public final int charmGuide;
        public final int charmDesign;
        public final int charmSecrets;
        public final double healthLethalityPct;
        public final long power;
        Level(int l,int g,int d,int s,double pct,long p){level=l;charmGuide=g;charmDesign=d;charmSecrets=s;healthLethalityPct=pct;power=p;}
    }

    private static final Level[] LEVELS = {
        new Level(0,0,0,0,0,0),
        new Level(1,5,5,0,9,205700),
        new Level(2,40,15,0,12,288000),
        new Level(3,60,40,0,16,370000),
        new Level(4,80,100,0,19,452000),
        new Level(5,100,200,0,25,576000),
        new Level(6,120,300,0,30,700000),
        new Level(7,140,400,0,35,824000),
        new Level(8,200,400,0,40,948000),
        new Level(9,300,400,0,45,1072000),
        new Level(10,420,420,0,50,1196000),
        new Level(11,560,420,0,55,1320000),
        new Level(12,580,450,15,64,1536000),
        new Level(13,580,450,30,73,1752000),
        new Level(14,600,500,45,82,1968000),
        new Level(15,600,500,70,91,2184000),
        new Level(16,650,550,100,100,2400000),
        new Level(17,765,630,135,109,2616000),
        new Level(18,1300,1130,180,118,2832000)
    };

    public static Level level(int value){return LEVELS[Math.max(0,Math.min(18,value))];}
    public static int maxLevel(){return 18;}

    public static int totalGuidesTo(int target){int n=0;for(int i=1;i<=Math.min(18,target);i++)n+=LEVELS[i].charmGuide;return n;}
    public static int totalDesignsTo(int target){int n=0;for(int i=1;i<=Math.min(18,target);i++)n+=LEVELS[i].charmDesign;return n;}
    public static int totalSecretsTo(int target){int n=0;for(int i=1;i<=Math.min(18,target);i++)n+=LEVELS[i].charmSecrets;return n;}

    public static int fullSetGuidesTo(int target){return totalGuidesTo(target)*SLOT_COUNT;}
    public static int fullSetDesignsTo(int target){return totalDesignsTo(target)*SLOT_COUNT;}
    public static int fullSetSecretsTo(int target){return totalSecretsTo(target)*SLOT_COUNT;}
}
