package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Additional public Expedition facts for Gen12-16. Max-only rows are explicitly marked. */
public final class PublicHeroSkillCatalogGen12to16 {
    public static final class Skill {
        public final int generation; public final String hero,name,effect,target,trigger,source;
        public final double[] values; public final boolean fullLadder;
        Skill(int g,String h,String n,String e,String t,String tr,double[]v,boolean f,String s){generation=g;hero=h;name=n;effect=e;target=t;trigger=tr;values=v;fullLadder=f;source=s;}
        public double max(){return values.length==0?0:values[values.length-1];}
    }
    private static final List<Skill> DATA;
    static { ArrayList<Skill>d=new ArrayList<>();
        // Gen12 — WSCO public hero pages. Max-only where the public page exposes only max values.
        max(d,12,"Hervor","Call For Blood","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",25,"WSCO Hervor Gen12");
        max(d,12,"Hervor","Undying Normal","NORMAL_ATTACK_DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",25,"WSCO Hervor Gen12");
        max(d,12,"Hervor","Undying Skill","SKILL_DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",30,"WSCO Hervor Gen12");
        max(d,12,"Hervor","Bloodthirsty Guard","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",15,"WSCO Hervor Gen12");
        max(d,12,"Hervor","Bloodthirsty Damage","DAMAGE_DEALT_UP","INFANTRY","PASSIVE",10,"WSCO Hervor Gen12");
        max(d,12,"Hervor","Fort of Rock","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",15,"WSCO Hervor Gen12");
        max(d,12,"Karol","In the Wings","DAMAGE_TAKEN_DOWN","ALL_TROOPS","PASSIVE",20,"WSCO Karol Gen12");
        max(d,12,"Karol","Shieldbreaker vs Lancer","DAMAGE_UP","ALL_TROOPS_TO_LANCER","PASSIVE",30,"WSCO Karol Gen12");
        max(d,12,"Karol","Shieldbreaker vs Infantry","DAMAGE_UP","ALL_TROOPS_TO_INFANTRY","PASSIVE",25,"WSCO Karol Gen12");
        max(d,12,"Karol","Standard of Ages Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",15,"WSCO Karol Gen12");
        max(d,12,"Karol","Standard of Ages Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",10,"WSCO Karol Gen12");
        max(d,12,"Karol","Triumphant March","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",15,"WSCO Karol Gen12");
        max(d,12,"Ligeia","Nerf Poison","ENEMY_DEFENSE_DOWN","ENEMY_ALL","PASSIVE",25,"WSCO Ligeia Gen12");
        max(d,12,"Ligeia","Corrosion Damage","DAMAGE_UP","MARKSMAN","EVERY_2_ATTACKS",100,"WSCO Ligeia Gen12");
        max(d,12,"Ligeia","Corrosion Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","EVERY_2_ATTACKS_1_TURN",25,"WSCO Ligeia Gen12");
        max(d,12,"Ligeia","Toxic Tip Damage","DAMAGE_UP","MARKSMAN","EVERY_2_ATTACKS",100,"WSCO Ligeia Gen12");
        max(d,12,"Ligeia","Toxic Tip Debuff","ENEMY_DAMAGE_DEALT_DOWN","TARGET","EVERY_2_ATTACKS_1_TURN",20,"WSCO Ligeia Gen12");
        max(d,12,"Ligeia","Trap Nest","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",15,"WSCO Ligeia Gen12");

        // Gen13.
        max(d,13,"Gisela","Alloyed Defense","TROOP_DEFENSE_UP","INFANTRY","PASSIVE",30,"WSCO Gisela Gen13");
        max(d,13,"Gisela","Scavengeworks","TROOP_DEFENSE_UP","ALL_TROOPS","PROC_40_PERCENT_1_TURN",50,"WSCO Gisela Gen13");
        max(d,13,"Gisela","Trial Shield","DAMAGE_TAKEN_DOWN","ALL_TROOPS","PROC_40_PERCENT",50,"WSCO Gisela Gen13");
        max(d,13,"Gisela","Auto-Target","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",15,"WSCO Gisela Gen13");
        max(d,13,"Flora","Enmiring Vines","ENEMY_DAMAGE_TAKEN_UP","ENEMY_ALL","PROC_50_PERCENT",50,"WSCO Flora Gen13");
        max(d,13,"Flora","Plantage Infantry","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",25,"WSCO Flora Gen13");
        max(d,13,"Flora","Plantage Lancer","DAMAGE_DEALT_UP","LANCER","PASSIVE",25,"WSCO Flora Gen13");
        max(d,13,"Flora","Confusion Pollen Infantry","ENEMY_DAMAGE_TAKEN_UP","ENEMY_INFANTRY","EVERY_4_TURNS_2_TURNS",30,"WSCO Flora Gen13");
        max(d,13,"Flora","Confusion Pollen Marksman","ENEMY_DAMAGE_DEALT_DOWN","ENEMY_MARKSMAN","EVERY_4_TURNS_2_TURNS",30,"WSCO Flora Gen13");
        max(d,13,"Flora","Fruit of Life","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",15,"WSCO Flora Gen13");
        max(d,13,"Vulcanus","Raging Storm","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",20,"WSCO Vulcanus Gen13");
        max(d,13,"Vulcanus","Breaker Steel Damage","DAMAGE_UP","ALL_TROOPS","EVERY_5_ATTACKS",100,"WSCO Vulcanus Gen13");
        max(d,13,"Vulcanus","Breaker Steel Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","EVERY_5_ATTACKS_NEXT_HIT",15,"WSCO Vulcanus Gen13");
        max(d,13,"Vulcanus","True Strike Defense","ENEMY_DEFENSE_DOWN","ENEMY_INFANTRY_LANCER","3_TURNS",60,"WSCO Vulcanus Gen13");
        max(d,13,"Vulcanus","True Strike Marksman","TROOP_ATTACK_UP","MARKSMAN","1_TURN",60,"WSCO Vulcanus Gen13");
        max(d,13,"Vulcanus","Born King","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",15,"WSCO Vulcanus Gen13");

        // Gen14 — full Lv1..Lv5 ladders exposed by WSCO for core Expedition skills.
        full(d,14,"Elif","Veil Entangle","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE_PRIMARY",v(5,10,15,20,25),"WSCO Elif Gen14");
        full(d,14,"Elif","Exotic Formation Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"WSCO Elif Gen14");
        full(d,14,"Elif","Exotic Formation Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),"WSCO Elif Gen14");
        full(d,14,"Elif","Ribbon Shield","SHIELD_ATTACK_MULTIPLIER","INFANTRY","ON_ATTACK_1_TURN",v(6,12,18,24,30),"WSCO Elif Gen14");
        max(d,14,"Elif","Defender Dance","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",15,"WSCO Elif Gen14");
        full(d,14,"Dominic","Magic Props","DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(4,8,12,16,20),"WSCO Dominic Gen14");
        full(d,14,"Dominic","Poison Spikes Damage","DAMAGE_UP","LANCER","EACH_ATTACK",v(12,24,36,48,60),"WSCO Dominic Gen14");
        full(d,14,"Dominic","Poison Spikes Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","EACH_ATTACK_1_TURN",v(5,10,15,20,25),"WSCO Dominic Gen14");
        full(d,14,"Dominic","Optical Mirror Guard","DAMAGE_TAKEN_DOWN","INFANTRY_MARKSMAN","PASSIVE",v(3,6,9,12,15),"WSCO Dominic Gen14");
        full(d,14,"Dominic","Optical Mirror Damage","DAMAGE_DEALT_UP","INFANTRY_MARKSMAN","PASSIVE",v(3,6,9,12,15),"WSCO Dominic Gen14");
        full(d,14,"Cara","Smoke Grenades","ENEMY_LETHALITY_DOWN","ENEMY_ALL","PASSIVE_PRIMARY",v(4,8,12,16,20),"WSCO Cara Gen14");
        full(d,14,"Cara","Mech Pets","NORMAL_ATTACK_DAMAGE_UP","ALL_TROOPS","PASSIVE",v(10,15,20,25,30),"WSCO Cara Gen14");
        full(d,14,"Cara","Flying Broom vs Lancer","DAMAGE_UP","MARKSMAN_TO_LANCER","EVERY_2_ATTACKS",v(8,16,24,32,40),"WSCO Cara Gen14");
        full(d,14,"Cara","Flying Broom vs Marksman","DAMAGE_UP","MARKSMAN_TO_MARKSMAN","EVERY_2_ATTACKS",v(4,8,12,16,20),"WSCO Cara Gen14");
        max(d,14,"Cara","Defender Inspiration","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",15,"WSCO Cara Gen14");

        // Gen16 additional public facts. Keep max-only where exact ladder was not exposed.
        full(d,16,"Seigel","Armor of Night","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(5,10,15,20,25),"WSCO Seigel Gen16");
        full(d,16,"Seigel","Night's Defense Infantry","TROOP_ATTACK_DOWN","INFANTRY","PASSIVE",v(4,8,12,16,20),"WSCO Seigel Gen16");
        full(d,16,"Seigel","Night's Defense Enemy","ENEMY_ATTACK_DOWN","ENEMY_MARKSMAN_LANCER","PASSIVE",v(7,14,21,28,35),"WSCO Seigel Gen16");
        max(d,16,"Seigel","Hell's Vow","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",15,"WSCO Seigel Gen16");
        full(d,16,"Ursar","Forest Spores","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE_PRIMARY",v(5,10,15,20,25),"WSCO Ursar Gen16");
        full(d,16,"Ursar","Horn of the Ancients Lethality","TROOP_LETHALITY_UP","LANCER_MARKSMAN","2_TURNS",v(6,12,18,24,30),"WSCO Ursar Gen16");
        full(d,16,"Ursar","Horn of the Ancients Defense","ENEMY_DEFENSE_DOWN","ENEMY_ALL","1_TURN",v(6,12,18,24,30),"WSCO Ursar Gen16");
        max(d,16,"Ursar","Typhoon Drums","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",15,"WSCO Ursar Gen16");
        DATA=Collections.unmodifiableList(d);
    }
    private static void full(List<Skill>d,int g,String h,String n,String e,String t,String tr,double[]v,String s){d.add(new Skill(g,h,n,e,t,tr,v,true,s));}
    private static void max(List<Skill>d,int g,String h,String n,String e,String t,String tr,double m,String s){d.add(new Skill(g,h,n,e,t,tr,new double[]{m},false,s));}
    private static double[] v(double...x){return x;}
    public static List<Skill> all(){return DATA;}
    public static List<Skill> forHero(String hero){ArrayList<Skill>o=new ArrayList<>();if(hero!=null)for(Skill s:DATA)if(s.hero.equalsIgnoreCase(hero))o.add(s);return o;}
    public static List<Skill> forGeneration(int generation){ArrayList<Skill>o=new ArrayList<>();for(Skill s:DATA)if(s.generation==generation)o.add(s);return o;}
    private PublicHeroSkillCatalogGen12to16(){}
}
