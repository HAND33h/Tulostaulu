package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Verified WhiteoutData Expedition/widget data for Gen6 and Gen9. */
public final class HeroSkillCatalogGen6Gen9Verified {
    public static final class Entry {
        public final int generation, expeditionOrder;
        public final String hero,name,effect,target,trigger,sourceUrl;
        public final double[] values;
        Entry(int g,int o,String h,String n,String e,String t,String tr,double[]v,String u){generation=g;expeditionOrder=o;hero=h;name=n;effect=e;target=t;trigger=tr;values=v;sourceUrl=u;}
    }
    private static final List<Entry> DATA;
    static {
        ArrayList<Entry>d=new ArrayList<>();
        String g6="https://whiteoutdata.com/heroes/generation-6-heroes/";
        add(d,6,1,"Wu Ming","Shadow's Evasion Normal","DAMAGE_TAKEN_DOWN","INFANTRY_NORMAL_ATTACK","PASSIVE",v(5,10,15,20,25),g6);
        add(d,6,1,"Wu Ming","Shadow's Evasion Skill","DAMAGE_TAKEN_DOWN","INFANTRY_SKILL","PASSIVE",v(6,12,18,24,30),g6);
        add(d,6,2,"Wu Ming","Crescent Uplift","DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),g6);
        add(d,6,3,"Wu Ming","Elemental Resonance","SKILL_DAMAGE_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),g6);
        add(d,6,4,"Wu Ming","Steel Discipline","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),g6);
        add(d,6,1,"Renee","Nightmare Trace","EXTRA_DAMAGE","LANCER","EVERY_2_TURNS_MARK_NEXT_TURN",v(40,80,120,160,200),g6);
        add(d,6,2,"Renee","Dreamcatcher","DAMAGE_DEALT_UP","LANCER_TO_DREAM_MARK","PASSIVE",v(30,60,90,120,150),g6);
        add(d,6,3,"Renee","Dreamslice","DAMAGE_DEALT_UP","ALL_TROOPS_TO_DREAM_MARK","PASSIVE",v(15,30,45,60,75),g6);
        add(d,6,1,"Wayne","Thunder Strike","EXTRA_ATTACK_DAMAGE","ALL_TROOPS","EVERY_4_TURNS",v(20,40,60,80,100),g6);
        add(d,6,2,"Wayne","Roundabout Hit vs Lancer","EXTRA_DAMAGE","MARKSMAN_TO_LANCER","EVERY_2_ATTACKS",v(8,16,24,32,40),g6);
        add(d,6,2,"Wayne","Roundabout Hit vs Marksman","EXTRA_DAMAGE","MARKSMAN_TO_MARKSMAN","EVERY_2_ATTACKS",v(4,8,12,16,20),g6);
        add(d,6,3,"Wayne","Fleet","CRIT_RATE_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),g6);
        add(d,6,4,"Wayne","Offensive Defense","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),g6);

        String g9="https://whiteoutdata.com/heroes/generation-9-heroes/";
        add(d,9,1,"Magnus","Rapacious","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),g9);
        add(d,9,2,"Magnus","Iron Phalanx","TROOP_DEFENSE_UP","INFANTRY","ON_ATTACK_40_PERCENT_1_TURN",v(10,20,30,40,50),g9);
        add(d,9,3,"Magnus","Iceman Infantry","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(2,4,6,8,10),g9);
        add(d,9,3,"Magnus","Iceman Marksman","DAMAGE_DEALT_UP","MARKSMAN","PASSIVE",v(2,4,6,8,10),g9);
        add(d,9,4,"Magnus","Valoric Inspiration","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),g9);
        add(d,9,1,"Fred","Hydraulic Suppression","ENEMY_LETHALITY_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),g9);
        add(d,9,2,"Fred","Acidification","ENEMY_DAMAGE_TAKEN_UP","ENEMY_INFANTRY","PASSIVE",v(4,8,12,16,20),g9);
        add(d,9,3,"Fred","Floodbringer Damage","EXTRA_DAMAGE","LANCER","EVERY_4_STRIKES",v(40,80,120,160,200),g9);
        add(d,9,3,"Fred","Floodbringer Debuff","ENEMY_DAMAGE_DEALT_DOWN","ENEMY_ALL","NEXT_TURN_AFTER_EVERY_4_STRIKES",v(4,8,12,16,20),g9);
        add(d,9,4,"Fred","Call of the Firefighter","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),g9);
        add(d,9,1,"Xura","Fungal Fog","DAMAGE_TAKEN_DOWN","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),g9);
        add(d,9,2,"Xura","Piercing Arrow Damage","EXTRA_DAMAGE","MARKSMAN","EVERY_2_STRIKES",v(20,40,60,80,100),g9);
        add(d,9,2,"Xura","Piercing Arrow Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","EVERY_2_STRIKES_1_TURN",v(5,10,15,20,25),g9);
        add(d,9,3,"Xura","Unorthodoxy Guard","DAMAGE_TAKEN_DOWN","MARKSMAN","PASSIVE",v(3,6,9,12,15),g9);
        add(d,9,3,"Xura","Unorthodoxy Damage","DAMAGE_DEALT_UP","MARKSMAN","PASSIVE",v(2,4,6,8,10),g9);
        add(d,9,4,"Xura","Gaiac Hymn","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),g9);
        DATA=Collections.unmodifiableList(d);
    }
    private static void add(List<Entry>d,int g,int o,String h,String n,String e,String t,String tr,double[]v,String u){d.add(new Entry(g,o,h,n,e,t,tr,v,u));}
    private static double[] v(double...x){return x;}
    public static List<Entry> all(){return DATA;}
    public static List<Entry> forHero(String hero){ArrayList<Entry>o=new ArrayList<>();if(hero!=null)for(Entry e:DATA)if(e.hero.equalsIgnoreCase(hero))o.add(e);return o;}
    private HeroSkillCatalogGen6Gen9Verified(){}
}
