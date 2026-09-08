package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Verified public Expedition + exclusive-gear data for Gen7 and Gen8. */
public final class HeroSkillCatalogGen7Gen8 {
    public static final class Entry {
        public final String hero,name,effect,target,trigger,source;
        public final double[] values;
        Entry(String h,String n,String e,String t,String tr,double[]v,String s){hero=h;name=n;effect=e;target=t;trigger=tr;values=v;source=s;}
    }
    private static final List<Entry> DATA;
    static {
        ArrayList<Entry>d=new ArrayList<>();
        // Generation 7 — max Expedition stat bonus 650.52% ATK/DEF for hero troop type.
        add(d,"Edith","Strategic Balance Marksman","DAMAGE_TAKEN_DOWN","MARKSMAN","PASSIVE",v(4,8,12,16,20),"WhiteoutData Gen7");
        add(d,"Edith","Strategic Balance Lancer","DAMAGE_DEALT_UP","LANCER","PASSIVE",v(4,8,12,16,20),"WhiteoutData Gen7");
        add(d,"Edith","Ironclad","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(4,8,12,16,20),"WhiteoutData Gen7");
        add(d,"Edith","Steel Sentinel","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen7");
        add(d,"Edith","Fortworks","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen7");
        add(d,"Gordon","Venom Infusion Damage","EXTRA_DAMAGE","LANCER","EVERY_2_ATTACKS",v(20,40,60,80,100),"WhiteoutData Gen7");
        add(d,"Gordon","Venom Infusion Poison","ENEMY_DAMAGE_DEALT_DOWN","TARGET","EVERY_2_ATTACKS_1_TURN",v(4,8,12,16,20),"WhiteoutData Gen7");
        add(d,"Gordon","Chemical Terror Damage","DAMAGE_DEALT_UP","LANCER","EVERY_3_TURNS_1_TURN",v(30,60,90,120,150),"WhiteoutData Gen7");
        add(d,"Gordon","Chemical Terror Debuff","ENEMY_DAMAGE_DEALT_DOWN","ENEMY_ALL","EVERY_3_TURNS_1_TURN",v(6,12,18,24,30),"WhiteoutData Gen7");
        add(d,"Gordon","Toxic Release Infantry","ENEMY_DAMAGE_TAKEN_UP","ENEMY_INFANTRY","EVERY_4_TURNS_2_TURNS",v(6,12,18,24,30),"WhiteoutData Gen7");
        add(d,"Gordon","Toxic Release Marksman","ENEMY_DAMAGE_DEALT_DOWN","ENEMY_MARKSMAN","EVERY_4_TURNS_2_TURNS",v(6,12,18,24,30),"WhiteoutData Gen7");
        add(d,"Gordon","Bio Assault","RALLY_LETHALITY_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen7");
        add(d,"Bradley","Veteran's Might","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(5,10,15,20,25),"WhiteoutData Gen7");
        add(d,"Bradley","Power Shot vs Lancer","DAMAGE_DEALT_UP","ALL_TO_LANCER","PASSIVE",v(6,12,18,24,30),"WhiteoutData Gen7");
        add(d,"Bradley","Power Shot vs Infantry","DAMAGE_DEALT_UP","ALL_TO_INFANTRY","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen7");
        add(d,"Bradley","Tactical Assistance","DAMAGE_DEALT_UP","ALL_TROOPS","EVERY_4_TURNS_2_TURNS",v(6,12,18,24,30),"WhiteoutData Gen7");
        add(d,"Bradley","Siege Insight","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen7");

        // Generation 8 — max Expedition stat bonus 780.62% ATK/DEF for hero troop type.
        add(d,"Gatot","Golden Guard","TROOP_DEFENSE_UP","INFANTRY","PASSIVE",v(6,12,18,24,30),"WhiteoutData Gen8");
        add(d,"Gatot","King's Bestowal","SHIELD_ATTACK_PERCENT","INFANTRY","EACH_ATTACK_1_TURN",v(6,12,18,24,30),"WhiteoutData Gen8");
        add(d,"Gatot","Royal Legion","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(5,10,15,20,25),"WhiteoutData Gen8");
        add(d,"Gatot","Indestructible City","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen8");
        add(d,"Sonya","Treasure Hunter","DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(4,8,12,16,20),"WhiteoutData Gen8");
        add(d,"Sonya","Bounty Temptation Damage","EXTRA_DAMAGE","LANCER","EVERY_2_ATTACKS",v(15,30,45,60,75),"WhiteoutData Gen8");
        add(d,"Sonya","Bounty Temptation Attack","TROOP_ATTACK_UP","ALL_TROOPS","EVERY_2_ATTACKS_1_TURN",v(5,10,15,20,25),"WhiteoutData Gen8");
        add(d,"Sonya","Torrential Impact","EXTRA_DAMAGE","LANCER","EVERY_5_TURNS_STUN_1_TURN",v(50,100,150,200,250),"WhiteoutData Gen8");
        add(d,"Sonya","Vortex Turret","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen8");
        add(d,"Hendrik","Worm's Ravage","ENEMY_DEFENSE_DOWN","ENEMY_ALL","PASSIVE_PRIMARY",v(5,10,15,20,25),"WhiteoutData Gen8");
        add(d,"Hendrik","Armor of Barnacles","TROOP_DEFENSE_UP","ALL_TROOPS","EVERY_4_TURNS_2_TURNS",v(6,12,18,24,30),"WhiteoutData Gen8");
        add(d,"Hendrik","Dagon's Heir","EXTRA_DAMAGE","MARKSMAN_TO_ALL","EVERY_3_TURNS",v(8,16,24,32,40),"WhiteoutData Gen8");
        add(d,"Hendrik","Abyssal Blessing","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData Gen8");
        DATA=Collections.unmodifiableList(d);
    }
    private static void add(List<Entry>d,String h,String n,String e,String t,String tr,double[]v,String s){d.add(new Entry(h,n,e,t,tr,v,s));}
    private static double[] v(double...x){return x;}
    public static List<Entry> all(){return DATA;}
    public static List<Entry> forHero(String hero){ArrayList<Entry>o=new ArrayList<>();if(hero!=null)for(Entry e:DATA)if(e.hero.equalsIgnoreCase(hero))o.add(e);return o;}
    private HeroSkillCatalogGen7Gen8(){}
}
