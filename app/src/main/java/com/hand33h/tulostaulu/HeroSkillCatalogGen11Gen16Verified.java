package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Verified public Expedition data for Gen11 and Gen16. */
public final class HeroSkillCatalogGen11Gen16Verified {
    public static final class Row {
        public final int generation, expeditionOrder;
        public final String hero, skill, effect, target, trigger, sourceUrl;
        public final double[] values;
        Row(int g,int o,String h,String s,String e,String t,String tr,double[]v,String u){generation=g;expeditionOrder=o;hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;sourceUrl=u;}
    }
    private static double[] v(double...x){return x;}
    private static void add(List<Row>d,int g,int o,String h,String s,String e,String t,String tr,double[]v,String u){d.add(new Row(g,o,h,s,e,t,tr,v,u));}
    public static List<Row> all(){
        ArrayList<Row>d=new ArrayList<>();
        String g11="https://whiteoutdata.com/heroes/generation-11-heroes/";
        add(d,11,1,"Eleonora","Scorching Sun","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),g11);
        add(d,11,2,"Eleonora","Solaris Nexus Infantry","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(2,4,6,8,10),g11);
        add(d,11,2,"Eleonora","Solaris Nexus Marksman","DAMAGE_DEALT_UP","MARKSMAN","PASSIVE",v(2,4,6,8,10),g11);
        add(d,11,3,"Eleonora","Soaring Flame Damage","DAMAGE_DEALT_UP","ALL_TROOPS","EVERY_5_INFANTRY_ATTACKS_2_TURNS",v(5,10,15,20,25),g11);
        add(d,11,3,"Eleonora","Soaring Flame Guard","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EVERY_5_INFANTRY_ATTACKS_2_TURNS",v(5,10,15,20,25),g11);
        add(d,11,0,"Eleonora","Last Fortress","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),g11);
        add(d,11,1,"Lloyd","Bird Invasion","ENEMY_LETHALITY_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),g11);
        add(d,11,2,"Lloyd","Iceflare Bomb Damage","EXTRA_DAMAGE","LANCER","EVERY_3_TURNS",v(30,60,90,120,150),g11);
        add(d,11,2,"Lloyd","Iceflare Bomb Debuff","ENEMY_LETHALITY_DOWN","ENEMY_ALL","EVERY_3_TURNS_1_TURN",v(6,12,18,24,30),g11);
        add(d,11,3,"Lloyd","Ingenious Mastery","TROOP_LETHALITY_UP","ALL_TROOPS","PROC_40_PERCENT",v(10,20,30,40,50),g11);
        // WhiteoutData text says Defender Attack, while preview label says Defense Up: preserve as conflict.
        add(d,11,0,"Lloyd","Steel Maze","CONFLICT_ATTACK_VS_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),g11);
        add(d,11,1,"Rufus","Inferno Regiment","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),g11);
        add(d,11,2,"Rufus","Armor Crush Damage","EXTRA_DAMAGE","MARKSMAN","EACH_ATTACK",v(12,24,36,48,60),g11);
        add(d,11,2,"Rufus","Armor Crush Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","EACH_ATTACK_1_TURN",v(5,10,15,20,25),g11);
        add(d,11,3,"Rufus","Wrathful Quake","ENEMY_LETHALITY_DOWN","TARGET","PROC_20_PERCENT_2_TURNS",v(10,20,30,40,50),g11);
        add(d,11,0,"Rufus","Blazing Legion","TROOP_ATTACK_UP","ALL_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),g11);

        String g16="https://whiteoutdata.com/heroes/generation-16-heroes/";
        add(d,16,1,"Seigel","Armor of Night","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),g16);
        add(d,16,2,"Seigel","Night's Defense Infantry","TROOP_ATTACK_DOWN","INFANTRY","PASSIVE",v(4,8,12,16,20),g16);
        add(d,16,2,"Seigel","Night's Defense Enemy","ENEMY_ATTACK_DOWN","ENEMY_LANCER_MARKSMAN","PASSIVE",v(7,14,21,28,35),g16);
        add(d,16,3,"Seigel","Vanguard of Eternity Normal","NORMAL_ATTACK_DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(5,10,15,20,25),g16);
        add(d,16,3,"Seigel","Vanguard of Eternity Skill","SKILL_DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(6,12,18,24,30),g16);
        add(d,16,0,"Seigel","Hell's Vow","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),g16);
        add(d,16,1,"Ursar","Forest Spores","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(5,10,15,20,25),g16);
        add(d,16,2,"Ursar","Horn of the Ancients Lethality","TROOP_LETHALITY_UP","LANCER_MARKSMAN","2_TURNS",v(6,12,18,24,30),g16);
        add(d,16,2,"Ursar","Horn of the Ancients Defense","ENEMY_DEFENSE_DOWN","ENEMY_ALL","1_TURN",v(6,12,18,24,30),g16);
        add(d,16,3,"Ursar","Poison Tips Damage","EXTRA_DAMAGE","LANCER","EVERY_2_ATTACKS",v(20,40,60,80,100),g16);
        add(d,16,3,"Ursar","Poison Tips Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","EVERY_2_ATTACKS_1_TURN",v(5,10,15,20,25),g16);
        add(d,16,0,"Ursar","Typhoon Drums","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),g16);
        add(d,16,1,"Aisling","Songs of the Ancestors","DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),g16);
        add(d,16,2,"Aisling","Rock Storm Damage","EXTRA_DAMAGE","MARKSMAN","EVERY_3_TURNS",v(30,60,90,120,150),g16);
        add(d,16,2,"Aisling","Rock Storm Debuff","ENEMY_DAMAGE_DEALT_DOWN","ENEMY_ALL","EVERY_3_TURNS_1_TURN",v(6,12,18,24,30),g16);
        add(d,16,3,"Aisling","Forest Fury","EXTRA_DAMAGE","MARKSMAN","EVERY_3_TURNS",v(8,16,24,32,40),g16);
        add(d,16,0,"Aisling","Forest Guardian","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),g16);
        return Collections.unmodifiableList(d);
    }
    private HeroSkillCatalogGen11Gen16Verified(){}
}
