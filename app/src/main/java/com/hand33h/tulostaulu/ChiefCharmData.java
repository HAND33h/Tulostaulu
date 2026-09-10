package com.hand33h.tulostaulu;

/**
 * Chief Charm progression, re-audited against current WOS Forge on 2026-09-10.
 * Values are per single charm and the published stat is Health & Lethality.
 * Keep event/SvS scoring separate unless the exact live scoring rule is verified.
 */
public final class ChiefCharmData {
    private ChiefCharmData() {}

    public static final String SOURCE_URL = "https://wiki.wosforge.org/wiki/Chief_Charms";
    public static final int UNLOCK_FURNACE_LEVEL = 25;
    public static final int SLOT_COUNT = 18;
    public static final int SLOTS_PER_GEAR_PIECE = 3;
    public static final int SLOTS_PER_TROOP_TYPE = 6;
    public static final int MATERIAL_EXCHANGE_UNLOCK_LEVEL = 11;
    public static final int SECRETS_START_LEVEL = 12;

    public enum TroopType { INFANTRY, LANCER, MARKSMAN }

    public static final class Level {
        public final int level;
        public final int charmGuide;
        public final int charmDesign;
        public final int charmSecrets;
        public final double healthLethalityPct;
        public final long power;
        Level(int l,int g,int d,int s,double pct,long p){level=l;charmGuide=g;charmDesign=d;charmSecrets=s;healthLethalityPct=pct;power=p;}
    }

    public static final class Cost {
        public final int guides, designs, secrets;
        public final long powerGain;
        Cost(int g,int d,int s,long p){guides=g;designs=d;secrets=s;powerGain=p;}
    }

    private static final Level[] LEVELS = {
        new Level(0,0,0,0,0,0),
        new Level(1,5,5,0,9,205700), new Level(2,40,15,0,12,288000),
        new Level(3,60,40,0,16,370000), new Level(4,80,100,0,19,452000),
        new Level(5,100,200,0,25,576000), new Level(6,120,300,0,30,700000),
        new Level(7,140,400,0,35,824000), new Level(8,200,400,0,40,948000),
        new Level(9,300,400,0,45,1072000), new Level(10,420,420,0,50,1196000),
        new Level(11,560,420,0,55,1320000), new Level(12,580,450,15,64,1536000),
        new Level(13,580,450,30,73,1752000), new Level(14,600,500,45,82,1968000),
        new Level(15,600,500,70,91,2184000), new Level(16,650,550,100,100,2400000),
        new Level(17,765,630,135,109,2616000), new Level(18,1300,1130,180,118,2832000)
    };

    public static Level level(int value){return LEVELS[Math.max(0,Math.min(18,value))];}
    public static int maxLevel(){return 18;}
    public static boolean isUnlocked(int furnaceLevel){return furnaceLevel>=UNLOCK_FURNACE_LEVEL;}
    public static boolean materialExchangeUnlocked(int highestCharmLevel){return highestCharmLevel>=MATERIAL_EXCHANGE_UNLOCK_LEVEL;}
    public static boolean secretsRequiredForTarget(int targetLevel){return targetLevel>=SECRETS_START_LEVEL;}

    /** Published star/sub-stage count inside levels that are climbed in sub-stages. Lv18 is max and has no next-level climb. */
    public static int starStepsAtLevel(int charmLevel){
        if(charmLevel>=4 && charmLevel<=10)return 4;
        if(charmLevel>=11 && charmLevel<=15)return 5;
        if(charmLevel>=16 && charmLevel<=17)return 9;
        return 0;
    }

    /** Fixed gear-to-troop mapping for all 18 charm slots. */
    public static TroopType troopTypeForGear(String gearPiece){
        if(gearPiece==null)return null;
        String p=gearPiece.trim().toLowerCase();
        if(p.equals("jacket")||p.equals("pants")||p.equals("coat"))return TroopType.INFANTRY;
        if(p.equals("helmet")||p.equals("watch")||p.equals("cap"))return TroopType.LANCER;
        if(p.equals("ring")||p.equals("cane")||p.equals("weapon"))return TroopType.MARKSMAN;
        return null;
    }

    public static Cost costBetween(int current,int target){
        int from=Math.max(0,Math.min(18,current)), to=Math.max(0,Math.min(18,target));
        if(to<=from)return new Cost(0,0,0,0);
        int g=0,d=0,s=0;
        for(int i=from+1;i<=to;i++){g+=LEVELS[i].charmGuide;d+=LEVELS[i].charmDesign;s+=LEVELS[i].charmSecrets;}
        return new Cost(g,d,s,LEVELS[to].power-LEVELS[from].power);
    }

    /** Aggregate any 18-slot current/target plan without asking the user to enter required materials manually. */
    public static Cost costForSlots(int[] current,int[] target){
        if(current==null||target==null||current.length!=SLOT_COUNT||target.length!=SLOT_COUNT) throw new IllegalArgumentException("Expected 18 current and 18 target charm levels");
        int g=0,d=0,s=0; long p=0;
        for(int i=0;i<SLOT_COUNT;i++){
            Cost c=costBetween(current[i],target[i]); g+=c.guides; d+=c.designs; s+=c.secrets; p+=c.powerGain;
        }
        return new Cost(g,d,s,p);
    }

    public static int totalGuidesTo(int target){return costBetween(0,target).guides;}
    public static int totalDesignsTo(int target){return costBetween(0,target).designs;}
    public static int totalSecretsTo(int target){return costBetween(0,target).secrets;}
    public static int fullSetGuidesTo(int target){return totalGuidesTo(target)*SLOT_COUNT;}
    public static int fullSetDesignsTo(int target){return totalDesignsTo(target)*SLOT_COUNT;}
    public static int fullSetSecretsTo(int target){return totalSecretsTo(target)*SLOT_COUNT;}
}
