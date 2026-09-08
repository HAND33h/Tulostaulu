package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Public-source Expedition facts for battle modelling. Community facts are never official formulas. */
public final class PublicHeroSkillCatalog {
    public enum Confidence { OFFICIAL_MECHANIC, VERIFIED_PUBLIC, COMMUNITY_MODEL, UNVERIFIED }
    public enum SourceKind { OFFICIAL, PUBLIC_DATABASE, COMMUNITY_GUIDE }
    public static final class Skill {
        public final String hero,name,effect,target,trigger,source; public final double[] values;
        public final Confidence confidence; public final SourceKind sourceKind;
        Skill(String h,String n,String e,String t,String tr,double[]v,Confidence c,SourceKind sk,String s){hero=h;name=n;effect=e;target=t;trigger=tr;values=v;confidence=c;sourceKind=sk;source=s;}
        public double max(){return values==null||values.length==0?0:values[values.length-1];}
    }
    public static final class WidgetRule {
        public final int maxLevel=10;
        public int expeditionTierAt(int l){return l<2?0:Math.min(5,l/2);} public int explorationTierAt(int l){return l<1?0:Math.min(5,(l+1)/2);}
        public int widgetsForLevel(int l){return l<1||l>10?0:l*5;} public int widgetsToReach(int l){int n=Math.max(0,Math.min(10,l));return 5*n*(n+1)/2;}
    }
    public static final WidgetRule WIDGET_RULE=new WidgetRule();
    public static final class CombatRules { public static final int HEROES_PER_MARCH=3,LEADER_EXPEDITION_SKILLS=9,MAX_JOINER_PRIMARY_SKILLS=4; public static final double CITY_ATTACKER_DEATH_SHARE=.35; public static final String FRONT_ROW="Infantry",MIDDLE_ROW="Lancer",BACK_ROW="Marksman"; private CombatRules(){} }
    private static final List<Skill> DATA;
    static { ArrayList<Skill>d=new ArrayList<>();
        pub(d,"Sergey","Defender's Edge","DAMAGE_TAKEN_DOWN","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),"OutOfGames");
        pub(d,"Sergey","Weaken","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"OutOfGames");
        pub(d,"Ling Xue","Fearsome Aura","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"OutOfGames");

        // Gen 2 — WhiteoutData.
        pub(d,"Flint","Pyromaniac","DAMAGE_DEALT_UP","INFANTRY","PASSIVE",v(20,40,60,80,100),"WhiteoutData Gen2");
        pub(d,"Flint","Burning Resolve","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen2");
        pub(d,"Flint","Immolation","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen2");
        pub(d,"Flint","Dragonbreath","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen2");
        pub(d,"Philly","Vigor Tactics Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"WhiteoutData Gen2");
        pub(d,"Philly","Vigor Tactics Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),"WhiteoutData Gen2");
        pub(d,"Philly","Dosage Boost","ATTACK_MULTIPLIER","ALL_TROOPS","ON_ATTACK_25_PERCENT",v(120,140,160,180,200),"WhiteoutData Gen2");
        pub(d,"Philly","Energizing Shot","DAMAGE_TAKEN_DOWN","ALL_TROOPS","ON_ATTACK_40_PERCENT",v(10,20,30,40,50),"WhiteoutData Gen2");
        pub(d,"Philly","First Aid Training","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen2");

        // Gen 3 — WhiteoutData.
        pub(d,"Logan","Lion's Might","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"WhiteoutData Gen3");
        pub(d,"Logan","Lion Intimidation","DAMAGE_TAKEN_DOWN","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),"WhiteoutData Gen3");
        pub(d,"Logan","Leader Inspiration","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen3");
        pub(d,"Logan","Strong Protection","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen3");
        pub(d,"Mia","Bad Luck Streak","ENEMY_DAMAGE_TAKEN_UP","ENEMY_ALL","ON_ATTACK_50_PERCENT",v(10,20,30,40,50),"WhiteoutData Gen3");
        pub(d,"Mia","Lucky Charm","TROOP_ATTACK_UP","ALL_TROOPS","ON_ATTACK_50_PERCENT",v(10,20,30,40,50),"WhiteoutData Gen3");
        pub(d,"Mia","Ritual Deciphering","DAMAGE_TAKEN_DOWN","ALL_TROOPS","ON_ATTACK_40_PERCENT",v(10,20,30,40,50),"WhiteoutData Gen3");
        pub(d,"Mia","Rally of Fate","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen3");
        pub(d,"Greg","Sword of Justice","DAMAGE_DEALT_UP","ALL_TROOPS","ON_ATTACK_20_PERCENT_3_TURNS",v(8,16,24,32,40),"WhiteoutData Gen3");
        pub(d,"Greg","Deterrence of Law","ENEMY_DAMAGE_DEALT_DOWN","ENEMY_ALL","ON_ATTACK_20_PERCENT_2_TURNS",v(10,20,30,40,50),"WhiteoutData Gen3");
        pub(d,"Greg","Law and Order","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen3");
        pub(d,"Greg","Trumpet of Justice","RALLY_HEALTH_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen3");

        // Gen 6 / 9 / 10 retained.
        pub(d,"Wu Ming","Crescent Uplift","DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),"WhiteoutData Gen6");
        pub(d,"Wu Ming","Elemental Resonance","SKILL_DAMAGE_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen6");
        pub(d,"Magnus","Rapacious","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(5,10,15,20,25),"WhiteoutData Gen9");
        pub(d,"Magnus","Iron Phalanx","TROOP_DEFENSE_UP","INFANTRY","ON_ATTACK_40_PERCENT_1_TURN",v(10,20,30,40,50),"WhiteoutData Gen9");
        pub(d,"Freya","Fog of War","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"WhiteoutData Gen10");
        pub(d,"Freya","Blood Moon Scythe","EXTRA_DAMAGE","LANCER","AFTER_NORMAL_ATTACK_50_PERCENT",v(20,40,60,80,100),"WhiteoutData Gen10");
        pub(d,"Blanchette","Armed to the Teeth","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen10");

        // Gen 11 — WhiteoutData exact Expedition ladders.
        pub(d,"Eleonora","Scorching Sun","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(5,10,15,20,25),"WhiteoutData Gen11");
        pub(d,"Eleonora","Solaris Nexus Infantry","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(2,4,6,8,10),"WhiteoutData Gen11");
        pub(d,"Eleonora","Solaris Nexus Marksman","DAMAGE_DEALT_UP","MARKSMAN","PASSIVE",v(2,4,6,8,10),"WhiteoutData Gen11");
        pub(d,"Eleonora","Soaring Flame Damage","DAMAGE_DEALT_UP","ALL_TROOPS","EVERY_5_INFANTRY_ATTACKS_2_TURNS",v(5,10,15,20,25),"WhiteoutData Gen11");
        pub(d,"Eleonora","Soaring Flame Guard","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EVERY_5_INFANTRY_ATTACKS_2_TURNS",v(5,10,15,20,25),"WhiteoutData Gen11");
        pub(d,"Eleonora","Last Fortress","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen11");
        pub(d,"Lloyd","Bird Invasion","ENEMY_LETHALITY_DOWN","ENEMY_ALL","PASSIVE_PRIMARY",v(4,8,12,16,20),"WhiteoutData Gen11");
        pub(d,"Lloyd","Iceflare Bomb Damage","DAMAGE_UP","LANCER","EVERY_3_TURNS",v(30,60,90,120,150),"WhiteoutData Gen11");
        pub(d,"Lloyd","Iceflare Bomb Debuff","ENEMY_LETHALITY_DOWN","ENEMY_ALL","EVERY_3_TURNS_1_TURN",v(6,12,18,24,30),"WhiteoutData Gen11");
        pub(d,"Lloyd","Ingenious Mastery","TROOP_LETHALITY_UP","ALL_TROOPS","PROC_40_PERCENT",v(10,20,30,40,50),"WhiteoutData Gen11");
        pub(d,"Rufus","Inferno Regiment","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(5,10,15,20,25),"WhiteoutData Gen11");
        pub(d,"Rufus","Armor Crush Damage","DAMAGE_UP","MARKSMAN","EACH_ATTACK",v(12,24,36,48,60),"WhiteoutData Gen11");
        pub(d,"Rufus","Armor Crush Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","EACH_ATTACK_1_TURN",v(5,10,15,20,25),"WhiteoutData Gen11");
        pub(d,"Rufus","Wrathful Quake","ENEMY_LETHALITY_DOWN","TARGET","PROC_20_PERCENT_2_TURNS",v(10,20,30,40,50),"WhiteoutData Gen11");
        pub(d,"Rufus","Blazing Legion","TROOP_ATTACK_UP","ALL_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen11");

        // Gen 16 / 17 retained.
        pub(d,"Aisling","Songs of the Ancestors","DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(4,8,12,16,20),"WhiteoutData Gen16");
        pub(d,"Aisling","Rock Storm Damage","EXTRA_DAMAGE","MARKSMAN","EVERY_3_TURNS",v(30,60,90,120,150),"WhiteoutData Gen16");
        pub(d,"Aiden","Scarlet Brigade Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"WOS Heroes Gen17");
        pub(d,"Bertha","Dissection","SQUAD_LETHALITY_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(5,10,15,20,25),"WOS Heroes Gen17");
        pub(d,"Eleanor","Theoretical Advantage","ENEMY_DEFENSE_DOWN","ENEMY_ALL","PASSIVE_PRIMARY",v(5,10,15,20,25),"WOS Heroes Gen17");
        DATA=Collections.unmodifiableList(d);
    }
    private static void pub(List<Skill>d,String h,String n,String e,String t,String tr,double[]v,String src){d.add(new Skill(h,n,e,t,tr,v,Confidence.VERIFIED_PUBLIC,SourceKind.PUBLIC_DATABASE,src));}
    private static double[]v(double...x){return x;}
    public static List<Skill> forHero(String h){ArrayList<Skill>o=new ArrayList<>();if(h!=null)for(Skill s:DATA)if(s.hero.equalsIgnoreCase(h))o.add(s);return o;}
    public static List<Skill> all(){return DATA;} public static Skill primaryExpeditionSkill(String h){for(Skill s:DATA)if(s.hero.equalsIgnoreCase(h)&&s.trigger.contains("PRIMARY"))return s;return null;}
    public static double widgetValueAt(Skill s,int l){if(s==null||s.values==null||s.values.length==0)return 0;int t=WIDGET_RULE.expeditionTierAt(l);return t<=0?0:s.values[Math.min(t,s.values.length)-1];}
    private PublicHeroSkillCatalog(){}
}
