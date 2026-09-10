package com.hand33h.tulostaulu;

import java.util.Locale;

/** Whiteout Survival hero battle metadata used by Battle Simulator. */
public final class HeroBattleData {
    private HeroBattleData() {}

    public static final String[] HEROES = {
        "None",
        "Ahmose — Gen 4 — Infantry","Aiden — Gen 17 — Infantry","Aisling — Gen 16 — Marksman","Alonso — Gen 2 — Marksman",
        "Bahiti — Gen 0 — Marksman","Bertha — Gen 17 — Lancer","Blanchette — Gen 10 — Marksman","Bradley — Gen 7 — Marksman",
        "Cara — Gen 14 — Marksman","Charlie — Gen 0 — Lancer","Cloris — Gen 0 — Marksman","Dominic — Gen 14 — Lancer",
        "Edith — Gen 7 — Infantry","Eleanor — Gen 17 — Marksman","Elif — Gen 14 — Infantry","Eleonora — Gen 11 — Infantry",
        "Estrella — Gen 15 — Lancer","Eugene — Gen 0 — Infantry","Flint — Gen 2 — Infantry","Flora — Gen 13 — Lancer",
        "Fred — Gen 9 — Lancer","Freya — Gen 10 — Lancer","Gatot — Gen 8 — Infantry","Gina — Gen 0 — Marksman",
        "Gisela — Gen 13 — Infantry","Gordon — Gen 7 — Lancer","Greg — Gen 3 — Marksman","Gregory — Gen 10 — Infantry",
        "Gwen — Gen 5 — Marksman","Hank — Gen 15 — Infantry","Hector — Gen 5 — Infantry","Hendrik — Gen 8 — Marksman",
        "Hervor — Gen 12 — Infantry","Jasser — Gen 0 — Marksman","Jeronimo — Gen 1 — Infantry","Jessie — Gen 0 — Lancer",
        "Karol — Gen 12 — Lancer","Ligeia — Gen 12 — Marksman","Ling Xue — Gen 0 — Lancer","Lloyd — Gen 11 — Lancer",
        "Logan — Gen 3 — Infantry","Lumak Bokan — Gen 0 — Lancer","Lynn — Gen 4 — Marksman","Magnus — Gen 9 — Infantry",
        "Mia — Gen 3 — Lancer","Molly — Gen 1 — Lancer","Natalia — Gen 1 — Infantry","Norah — Gen 5 — Lancer",
        "Patrick — Gen 0 — Lancer","Philly — Gen 2 — Lancer","Reina — Gen 4 — Lancer","Renee — Gen 6 — Lancer",
        "Rufus — Gen 11 — Marksman","Seigel — Gen 16 — Infantry","Seo-yoon — Gen 0 — Marksman","Sergey — Gen 0 — Infantry",
        "Smith — Gen 0 — Infantry","Sonya — Gen 8 — Lancer","Ursar — Gen 16 — Lancer","Viveca — Gen 15 — Marksman",
        "Vulcanus — Gen 13 — Marksman","Wayne — Gen 6 — Marksman","Wu Ming — Gen 6 — Infantry","Xura — Gen 9 — Marksman",
        "Zinman — Gen 1 — Marksman"
    };
    public static final String[] SKILL_LEVELS={"1","2","3","4","5"};
    public static final String[] EXCLUSIVE_LEVELS={"0","1","2","3","4","5","6","7","8","9","10"};
    public static final String[] HERO_GEAR_LEVELS=buildLevels(200);
    public static final String[] HERO_GEAR_SLOTS={"Headgear / Goggles","Gloves","Belt","Boots"};

    public static final class Effect {
        public double atk,def,hp,leth;
        public double enemyAtk,enemyDef,enemyHp,enemyLeth;
        public double infAtk,infDef,infHp,infLeth,lanAtk,lanDef,lanHp,lanLeth,marAtk,marDef,marHp,marLeth;
        public double damageDealt,damageTakenReduction,enemyDamageDealtReduction;
        public String note=""; public boolean hasVerifiedSkillData;
    }
    public static String nameOf(String selected){if(selected==null)return "None";int p=selected.indexOf(" — ");String raw=p<0?selected:selected.substring(0,p);return HeroNameAliases.canonicalize(raw);}
    public static int generationOf(String selected){if(selected==null)return 0;int p=selected.indexOf("Gen ");if(p<0)return 0;int s=p+4,e=s;while(e<selected.length()&&Character.isDigit(selected.charAt(e)))e++;try{return Integer.parseInt(selected.substring(s,e));}catch(Exception ignored){return 0;}}
    public static String troopTypeOf(String selected){if(selected==null)return "None";if(selected.endsWith("Infantry"))return "Infantry";if(selected.endsWith("Lancer"))return "Lancer";if(selected.endsWith("Marksman"))return "Marksman";return "None";}
    public static double exclusiveExpeditionLethHp(String selected,int weaponLevel){if(selected==null||"None".equals(nameOf(selected))||weaponLevel<=0)return 0;int lv=Math.max(0,Math.min(10,weaponLevel));String n=nameOf(selected);int g=generationOf(selected);if(g==1){if("Jeronimo".equals(n))return 6.25*lv;if("Natalia".equals(n))return 5.50*lv;if("Molly".equals(n)||"Zinman".equals(n))return 5.00*lv;return 0;}double[] step={0,0,6,7,9.25,11.10,13.35,16.05,19.30,23.20,27.85,33.45,40.15,48.20,57.85,69.40,83.30,99.95};return(g>=2&&g<step.length)?step[g]*lv:0;}
    public static int exclusiveExpeditionTier(int weaponLevel){return Math.max(0,Math.min(5,weaponLevel/2));}
    private static double v(int level,double a,double b,double c,double d,double e){return new double[]{a,b,c,d,e}[Math.max(1,Math.min(5,level))-1];}
    private static double ewTierValue(int weaponLevel){int t=exclusiveExpeditionTier(weaponLevel);return t==0?0:new double[]{5,7.5,10,12.5,15}[t-1];}

    public static Effect directEffect(String selected,int skillLevel,int weaponLevel,boolean defenderSide){
        Effect x=new Effect();String n=nameOf(selected);int l=Math.max(1,Math.min(5,skillLevel));double ew=ewTierValue(weaponLevel);if("None".equals(n))return x;
        if("Aiden".equals(n)){x.atk+=v(l,3,6,9,12,15);x.def+=v(l,2,4,6,8,10);if(defenderSide)x.atk+=ew;x.note="Scarlet Brigade applied; proc/timed skills conditional.";x.hasVerifiedSkillData=true;}
        else if("Bertha".equals(n)){x.leth+=v(l,5,10,15,20,25);x.note="Squad Lethality applied; cycle/target skills conditional.";x.hasVerifiedSkillData=true;}
        else if("Eleanor".equals(n)){x.enemyDef+=v(l,5,10,15,20,25);x.note="Enemy Defense reduction applied; proc/target skills conditional.";x.hasVerifiedSkillData=true;}
        else if("Estrella".equals(n)){x.enemyDef+=v(l,5,10,15,20,25);x.atk+=v(l,3,6,9,12,15);x.def+=v(l,2,4,6,8,10);x.note="Corrosive Color + Dawn Canvas applied.";x.hasVerifiedSkillData=true;}
        else if("Hank".equals(n)){x.leth+=v(l,5,10,15,20,25);if(defenderSide)x.hp+=ew;x.note="Roaring Rage applied; timed/target skills conditional.";x.hasVerifiedSkillData=true;}
        else if("Norah".equals(n)){if(defenderSide)x.def+=ew;x.note="Conditional Expedition effects retained; True Grit applies on defense.";x.hasVerifiedSkillData=true;}
        else if("Hector".equals(n)){if(defenderSide)x.atk+=ew;x.note="Proc/timed Expedition effects retained; Goliath applies on defense.";x.hasVerifiedSkillData=true;}
        else if("Ahmose".equals(n)){if(defenderSide)x.hp+=ew;x.note="Troop/timing effects retained; Exclusive defender Health applies.";x.hasVerifiedSkillData=true;}
        else if("Edith".equals(n)){x.hp+=v(l,5,10,15,20,25);if(defenderSide)x.hp+=ew;x.note="Steel Sentinel Health applied; troop-specific effects conditional.";x.hasVerifiedSkillData=true;}
        else if("Bradley".equals(n)){x.atk+=v(l,5,10,15,20,25);if(defenderSide)x.atk+=ew;x.note="Veteran's Might applied; target/timing effects conditional.";x.hasVerifiedSkillData=true;}
        else if("Eleonora".equals(n)){x.hp+=v(l,5,10,15,20,25);if(defenderSide)x.hp+=ew;x.note="Scorching Sun Health applied; troop/timing effects conditional.";x.hasVerifiedSkillData=true;}
        else if("Elif".equals(n)){x.enemyAtk+=v(l,5,10,15,20,25);x.atk+=v(l,3,6,9,12,15);x.def+=v(l,2,4,6,8,10);if(defenderSide)x.def+=ew;x.note="Enemy ATK reduction + allied ATK/DEF applied.";x.hasVerifiedSkillData=true;}
        else if("Flint".equals(n)){x.atk+=v(l,5,10,15,20,25);x.leth+=v(l,5,10,15,20,25);if(defenderSide)x.atk+=ew;x.note="Troop Attack + Lethality applied.";x.hasVerifiedSkillData=true;}
        else if("Gatot".equals(n)){x.infDef+=v(l,6,12,18,24,30);x.enemyAtk+=v(l,5,10,15,20,25);if(defenderSide)x.def+=ew;x.note="Infantry Defense and enemy Attack reduction applied.";x.hasVerifiedSkillData=true;}
        else if("Gisela".equals(n)){x.infDef+=v(l,6,12,18,24,30);if(defenderSide)x.atk+=ew;x.note="Infantry Defense applied; proc effects conditional.";x.hasVerifiedSkillData=true;}
        else if("Gregory".equals(n)){x.atk+=v(l,3,6,9,12,15);x.def+=v(l,2,4,6,8,10);if(defenderSide)x.leth+=ew;x.note="Troop Attack/Defense applied.";x.hasVerifiedSkillData=true;}
        else if("Hervor".equals(n)){x.leth+=v(l,5,10,15,20,25);if(defenderSide)x.def+=ew;x.note="Troop Lethality applied; troop-specific reductions conditional.";x.hasVerifiedSkillData=true;}
        else if("Jeronimo".equals(n)){x.atk+=v(l,5,10,15,20,25);x.damageDealt+=v(l,5,10,15,20,25);x.note="Battle Manifesto + Swordmentor applied; timed skill conditional.";x.hasVerifiedSkillData=true;}
        else if("Natalia".equals(n)){x.atk+=v(l,5,10,15,20,25);x.damageDealt+=v(l,5,10,15,20,25);x.note="Queen/Call of the Wild applied; Feral Protection proc conditional.";x.hasVerifiedSkillData=true;}
        else if("Molly".equals(n)){x.damageDealt+=v(l,5,10,15,20,25);if(defenderSide)x.leth+=ew;x.note="Youthful Rage applied; proc conditional.";x.hasVerifiedSkillData=true;}
        else if("Zinman".equals(n)){x.leth+=v(l,5,10,15,20,25);if(defenderSide)x.atk+=ew;x.note="Positional Battler applied; construction skill ignored.";x.hasVerifiedSkillData=true;}
        if(!x.hasVerifiedSkillData)x.hasVerifiedSkillData=HeroBattleDataExpanded.apply(x,n,l,defenderSide);
        return x;
    }
    public static String exclusiveSummary(String selected,int weaponLevel){if(weaponLevel<=0||"None".equals(nameOf(selected)))return "No Exclusive Weapon expedition stat";double val=exclusiveExpeditionLethHp(selected,weaponLevel);if(val<=0)return "No verified Expedition Lethality/HP table for this hero";return String.format(Locale.US,"%s EW Lv%d: +%.2f%% Lethality & +%.2f%% Health to %s troops",nameOf(selected),weaponLevel,val,val,troopTypeOf(selected));}
    private static String[] buildLevels(int max){String[] out=new String[max+1];for(int i=0;i<=max;i++)out[i]=String.valueOf(i);return out;}
}
