package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Cross-validated Gen14 Expedition data. Values are Lv1..Lv5. */
public final class VerifiedHeroSkillGen14 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final int expeditionOrder;
        public final double[] values;
        Row(String h,String s,int o,String e,String t,String tr,double[] v,String src){hero=h;skill=s;expeditionOrder=o;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA=Collections.unmodifiableList(Arrays.asList(
        new Row("Elif","Veil Entangle",1,"ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(5,10,15,20,25),"WSCO Gen14 / WoS Tools"),
        new Row("Elif","Exotic Formation Attack",2,"TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"WSCO Gen14 / WoS Tools"),
        new Row("Elif","Exotic Formation Defense",2,"TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),"WSCO Gen14 / WoS Tools"),
        new Row("Elif","Ribbon Shield",3,"SHIELD_ATTACK_PERCENT","INFANTRY","ON_ATTACK_1_TURN",v(6,12,18,24,30),"WSCO Gen14 / WoS Tools"),
        new Row("Elif","Guardian's Grace",0,"DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WoS Tools / WOS Heroes; cross-checked 2026-09-09"),
        new Row("Dominic","Magic Props",1,"DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),"WSCO Gen14"),
        new Row("Dominic","Poison Spikes Damage",2,"DAMAGE_DEALT_UP","LANCER","EACH_ATTACK",v(12,24,36,48,60),"WSCO Gen14"),
        new Row("Dominic","Poison Spikes Debuff",2,"ENEMY_DAMAGE_TAKEN_UP","TARGET","POISONED_1_TURN",v(5,10,15,20,25),"WSCO Gen14"),
        new Row("Dominic","Optical Mirror Guard",3,"DAMAGE_TAKEN_DOWN","INFANTRY_MARKSMAN","PASSIVE",v(3,6,9,12,15),"WSCO Gen14"),
        new Row("Dominic","Optical Mirror Damage",3,"DAMAGE_DEALT_UP","INFANTRY_MARKSMAN","PASSIVE",v(3,6,9,12,15),"WSCO Gen14"),
        new Row("Dominic","Grand Fantasy",0,"RALLY_TROOP_LETHALITY_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WoS H5Joy / WoS Guru; cross-checked 2026-09-09"),
        new Row("Cara","Smoky Encounter",1,"ENEMY_LETHALITY_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"WOS Heroes / WoS Tools; cross-checked 2026-09-09"),
        new Row("Cara","Mech Pet",2,"NORMAL_ATTACK_DAMAGE_UP","ALL_TROOPS","PASSIVE",v(10,15,20,25,30),"WOS Heroes / WoS Tools; cross-checked 2026-09-09"),
        new Row("Cara","Witch's Wrath vs Lancer",3,"DAMAGE_DEALT_UP","MARKSMAN_VS_LANCER","EVERY_2_ATTACKS",v(8,16,24,32,40),"WOS Heroes / WoS Tools; cross-checked 2026-09-09"),
        new Row("Cara","Witch's Wrath vs Marksman",3,"DAMAGE_DEALT_UP","MARKSMAN_VS_MARKSMAN","EVERY_2_ATTACKS",v(4,8,12,16,20),"WOS Heroes / WoS Tools; cross-checked 2026-09-09"),
        new Row("Cara","Shrouded Haven",0,"DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WoS H5Joy / Whiteout Battlemaster")
    ));
    private VerifiedHeroSkillGen14(){}
}
