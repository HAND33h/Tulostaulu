package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Cross-checked Gen13 Expedition ladders. Primary: WOS Heroes; cross-check: WoS Tools (2026-09-09). */
public final class VerifiedHeroSkillGen13 {
    public static final class Entry {
        public final String hero, skill, effect, target, trigger, sourceUrl;
        public final int expeditionOrder;
        public final double[] values;
        Entry(String h,String s,String e,String t,String tr,int o,double[] v,String u){hero=h;skill=s;effect=e;target=t;trigger=tr;expeditionOrder=o;values=v;sourceUrl=u;}
    }
    private static double[] v(double... x){return x;}
    private static final String SRC="https://wosheroes.com/heroes/generation-13-heroes/";
    public static final List<Entry> DATA=Collections.unmodifiableList(Arrays.asList(
        new Entry("Gisela","Alloyed Defense","DEFENSE_UP","INFANTRY","PASSIVE",1,v(6,12,18,24,30),SRC),
        new Entry("Gisela","Scavengeworks","TROOP_DEFENSE_UP","ALL_TROOPS","INFANTRY_40_PERCENT_PROC_1_TURN",2,v(10,20,30,40,50),SRC),
        new Entry("Gisela","Trial Shield","DAMAGE_TAKEN_DOWN","ALL_TROOPS","40_PERCENT_PROC",3,v(10,20,30,40,50),SRC),
        new Entry("Gisela","Auto-Target","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",0,v(5,7.5,10,12.5,15),SRC),

        new Entry("Flora","Enmiring Vines","ENEMY_DAMAGE_TAKEN_UP","ENEMY_ALL","50_PERCENT_PROC",1,v(10,20,30,40,50),SRC),
        new Entry("Flora","Plantage Infantry","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",2,v(5,10,15,20,25),SRC),
        new Entry("Flora","Plantage Lancer","DAMAGE_DEALT_UP","LANCER","PASSIVE",2,v(5,10,15,20,25),SRC),
        new Entry("Flora","Confusion Pollen Infantry","ENEMY_DAMAGE_TAKEN_UP","ENEMY_INFANTRY","EVERY_4_TURNS_2_TURNS",3,v(6,12,18,24,30),SRC),
        new Entry("Flora","Confusion Pollen Marksman","ENEMY_DAMAGE_DEALT_DOWN","ENEMY_MARKSMAN","EVERY_4_TURNS_2_TURNS",3,v(6,12,18,24,30),SRC),
        // Fruit of Life Lv1-Lv5 cross-checked against WoS Tools; H5Joy independently confirms +15% max.
        new Entry("Flora","Fruit of Life","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",0,v(5,7.5,10,12.5,15),SRC),

        new Entry("Vulcanus","Raging Storm","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",1,v(4,8,12,16,20),SRC),
        new Entry("Vulcanus","Breaker Steel Damage","EXTRA_DAMAGE","ALL_TROOPS","EVERY_5_ATTACKS",2,v(20,40,60,80,100),SRC),
        new Entry("Vulcanus","Breaker Steel Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","AFTER_EVERY_5_ATTACKS_NEXT_ATTACK",2,v(5,7.5,10,12.5,15),SRC),
        new Entry("Vulcanus","True Strike Defense","ENEMY_DEFENSE_DOWN","ENEMY_INFANTRY_LANCER","3_TURNS",3,v(12,24,36,48,60),SRC),
        new Entry("Vulcanus","True Strike Marksman","MARKSMAN_ATTACK_UP","MARKSMAN","1_TURN",3,v(12,24,36,48,60),SRC),
        new Entry("Vulcanus","Born King","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",0,v(5,7.5,10,12.5,15),SRC)
    ));
    private VerifiedHeroSkillGen13(){}
}
