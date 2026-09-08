package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Curated public Expedition facts. Values are stored as Lv1..Lv5 ladders. */
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

        // Gen 2
        pub(d,"Flint","Pyromaniac","DAMAGE_DEALT_UP","INFANTRY","PASSIVE",v(20,40,60,80,100),"WhiteoutData Gen2");
        pub(d,"Flint","Burning Resolve","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen2");
        pub(d,"Flint","Immolation","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen2");
        pub(d,"Flint","Dragonbreath","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen2");
        pub(d,"Philly","Vigor Tactics Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"WhiteoutData Gen2");
        pub(d,"Philly","Vigor Tactics Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),"WhiteoutData Gen2");
        pub(d,"Philly","Dosage Boost","ATTACK_MULTIPLIER","ALL_TROOPS","ON_ATTACK_25_PERCENT",v(120,140,160,180,200),"WhiteoutData Gen2");
        pub(d,"Philly","Energizing Shot","DAMAGE_TAKEN_DOWN","ALL_TROOPS","ON_ATTACK_40_PERCENT",v(10,20,30,40,50),"WhiteoutData Gen2");
        pub(d,"Philly","First Aid Training","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen2");

        // Gen 3
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

        // Gen 4 — exact public Expedition ladders.
        pub(d,"Ahmose","Viper Formation Lancer/Marksman","DAMAGE_TAKEN_DOWN","INFANTRY_FROM_LANCER_MARKSMAN","EVERY_4_INFANTRY_ATTACKS_2_TURNS",v(10,15,20,25,30),"WhiteoutData Gen4");
        pub(d,"Ahmose","Viper Formation Infantry","DAMAGE_TAKEN_DOWN","INFANTRY_FROM_INFANTRY","EVERY_4_INFANTRY_ATTACKS_2_TURNS",v(10,25,40,55,70),"WhiteoutData Gen4");
        pub(d,"Ahmose","Prayer of Flame","DAMAGE_DEALT_UP","INFANTRY","PASSIVE",v(20,40,60,80,100),"WhiteoutData Gen4");
        pub(d,"Ahmose","Blade of Light Damage","EXTRA_DAMAGE","INFANTRY","EACH_ATTACK",v(12,24,36,48,60),"WhiteoutData Gen4");
        pub(d,"Ahmose","Blade of Light Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","EACH_ATTACK_1_TURN",v(5,10,15,20,25),"WhiteoutData Gen4");
        pub(d,"Ahmose","Oath of Guardian","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen4");
        pub(d,"Reina","Assassin's Instinct","NORMAL_ATTACK_DAMAGE_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(10,15,20,25,30),"WhiteoutData Gen4");
        pub(d,"Reina","Swift Jive","NORMAL_ATTACK_DODGE","ALL_TROOPS","ON_RECEIVING_NORMAL_ATTACK",v(4,8,12,16,20),"WhiteoutData Gen4");
        pub(d,"Reina","Shadow Blade","EXTRA_ATTACK_DAMAGE","LANCER","ON_ATTACK_25_PERCENT",v(120,140,160,180,200),"WhiteoutData Gen4");
        pub(d,"Reina","Fiery Invasion","RALLY_LETHALITY_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen4");
        pub(d,"Lynn","Song of Lion","DAMAGE_DEALT_UP","ALL_TROOPS","PROC_40_PERCENT",v(10,20,30,40,50),"WhiteoutData Gen4");
        pub(d,"Lynn","Melancholic Ballad","ENEMY_DAMAGE_DEALT_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"WhiteoutData Gen4");
        pub(d,"Lynn","Oonai Cadenza","MARKSMAN_ATTACK_UP","MARKSMAN","EVERY_3_ATTACKS_STACK_UNTIL_END",v(1,2,3,4,5),"WhiteoutData Gen4");
        pub(d,"Lynn","Iranon's Determination","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen4");

        // Gen 5 — exact public Expedition ladders.
        pub(d,"Hector","Survival Instincts","DAMAGE_TAKEN_DOWN","ALL_TROOPS","PROC_40_PERCENT",v(10,20,30,40,50),"WhiteoutData Gen5");
        pub(d,"Hector","Rampant Infantry","DAMAGE_DEALT_UP","INFANTRY","10_ATTACKS_DECAY_85_PERCENT",v(100,125,150,175,200),"WhiteoutData Gen5");
        pub(d,"Hector","Rampant Marksman","DAMAGE_DEALT_UP","MARKSMAN","10_ATTACKS_DECAY_85_PERCENT",v(20,40,60,80,100),"WhiteoutData Gen5");
        pub(d,"Hector","Blitz","ATTACK_MULTIPLIER","ALL_TROOPS","ON_ATTACK_25_PERCENT",v(120,140,160,180,200),"WhiteoutData Gen5");
        pub(d,"Hector","Goliath","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen5");
        pub(d,"Norah","Combined Arms Guard","DAMAGE_TAKEN_DOWN","INFANTRY_MARKSMAN","PASSIVE",v(3,6,9,12,15),"WhiteoutData Gen5");
        pub(d,"Norah","Combined Arms Damage","DAMAGE_DEALT_UP","INFANTRY_MARKSMAN","PASSIVE",v(3,6,9,12,15),"WhiteoutData Gen5");
        pub(d,"Norah","Sneak Strike","EXTRA_DAMAGE","LANCER","ON_ATTACK_20_PERCENT",v(20,40,60,80,100),"WhiteoutData Gen5");
        pub(d,"Norah","Momentum Damage","DAMAGE_DEALT_UP","ALL_TROOPS","EVERY_5_LANCER_ATTACKS_2_TURNS",v(5,10,15,20,25),"WhiteoutData Gen5");
        pub(d,"Norah","Momentum Guard","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EVERY_5_LANCER_ATTACKS_2_TURNS",v(5,10,15,20,25),"WhiteoutData Gen5");
        pub(d,"Norah","True Grit","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen5");
        pub(d,"Gwen","Eagle Vision","ENEMY_DAMAGE_TAKEN_UP","ENEMY_ALL","PASSIVE_PRIMARY",v(5,10,15,20,25),"WhiteoutData Gen5");
        pub(d,"Gwen","Air Dominance Damage","EXTRA_DAMAGE","ALL_TROOPS","EVERY_5_ATTACKS",v(20,40,60,80,100),"WhiteoutData Gen5");
        pub(d,"Gwen","Air Dominance Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","AFTER_EVERY_5_ATTACKS_NEXT_HIT",v(5,7.5,10,12.5,15),"WhiteoutData Gen5");
        pub(d,"Gwen","Blastmaster","EXTRA_DAMAGE","MARKSMAN","EVERY_4_ATTACKS_NEXT_HIT",v(10,20,30,40,50),"WhiteoutData Gen5");
        pub(d,"Gwen","Marauder","RALLY_LETHALITY_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen5");

        // Retained later-generation verified subset.
        pub(d,"Wu Ming","Crescent Uplift","DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),"WhiteoutData Gen6");
        pub(d,"Wu Ming","Elemental Resonance","SKILL_DAMAGE_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen6");
        pub(d,"Magnus","Rapacious","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(5,10,15,20,25),"WhiteoutData Gen9");
        pub(d,"Magnus","Iron Phalanx","TROOP_DEFENSE_UP","INFANTRY","ON_ATTACK_40_PERCENT_1_TURN",v(10,20,30,40,50),"WhiteoutData Gen9");
        pub(d,"Freya","Fog of War","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"WhiteoutData Gen10");
        pub(d,"Freya","Blood Moon Scythe","EXTRA_DAMAGE","LANCER","AFTER_NORMAL_ATTACK_50_PERCENT",v(20,40,60,80,100),"WhiteoutData Gen10");
        pub(d,"Blanchette","Armed to the Teeth","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen10");
        pub(d,"Eleonora","Scorching Sun","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(5,10,15,20,25),"WhiteoutData Gen11");
        pub(d,"Lloyd","Bird Invasion","ENEMY_LETHALITY_DOWN","ENEMY_ALL","PASSIVE_PRIMARY",v(4,8,12,16,20),"WhiteoutData Gen11");
        pub(d,"Rufus","Inferno Regiment","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(5,10,15,20,25),"WhiteoutData Gen11");
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
