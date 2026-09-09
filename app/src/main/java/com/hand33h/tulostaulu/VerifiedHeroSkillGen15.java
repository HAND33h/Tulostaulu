package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Cross-validated Gen15 Expedition data. Values are Lv1..Lv5. */
public final class VerifiedHeroSkillGen15 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final int expeditionOrder;
        public final double[] values;
        Row(String h,String s,int o,String e,String t,String tr,double[] v,String src){hero=h;skill=s;expeditionOrder=o;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA=Collections.unmodifiableList(Arrays.asList(
        new Row("Hank","Wall of Despair",0,"DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / H5Joy; cross-checked 2026-09-09"),
        new Row("Hank","Roaring Rage",1,"TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WoS Tools / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Hank","Flying Sparks",2,"DAMAGE_DEALT_UP","ALL_TROOPS","EVERY_5_INFANTRY_ATTACKS_2_TURNS",v(5,10,15,20,25),"WOS Heroes / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Hank","Flying Sparks",2,"DAMAGE_TAKEN_DOWN","ALL_TROOPS","EVERY_5_INFANTRY_ATTACKS_2_TURNS",v(5,10,15,20,25),"WOS Heroes / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Hank","Raging Force",3,"ENEMY_INFANTRY_DAMAGE_TAKEN_UP","ENEMY_INFANTRY","EVERY_4_TURNS_2_TURNS",v(6,12,18,24,30),"WOS Heroes / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Hank","Raging Force",3,"ENEMY_MARKSMAN_DAMAGE_DEALT_DOWN","ENEMY_MARKSMAN","EVERY_4_TURNS_2_TURNS",v(6,12,18,24,30),"WOS Heroes / Whiteout Survival Wiki; cross-checked 2026-09-09"),

        new Row("Estrella","Homeland Defense",0,"DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / H5Joy / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Estrella","Corrosive Color",1,"ENEMY_DEFENSE_DOWN","ENEMY_ALL","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WoS Tools / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Estrella","Dawn Canvas",2,"TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"WOS Heroes / WoS Tools / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Estrella","Dawn Canvas",2,"TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),"WOS Heroes / WoS Tools / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Estrella","Splendid Scene",3,"DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WoS Tools / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Estrella","Splendid Scene",3,"DAMAGE_DEALT_UP","LANCER","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WoS Tools / Whiteout Survival Wiki; cross-checked 2026-09-09"),

        new Row("Viveca","Song of Dawn",0,"RALLY_LETHALITY_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / Whiteout Survival Wiki / WoS Tools; cross-checked 2026-09-09"),
        new Row("Viveca","Nightfall Legion",1,"TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WoS Tools / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Viveca","Shadow World",2,"EXTRA_DAMAGE","MARKSMAN_TO_ENEMY_ALL","ON_ATTACK_20_PERCENT",v(20,40,60,80,100),"WOS Heroes / WoS Tools / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Viveca","Children of the Mist",3,"DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(2,4,6,8,10),"WOS Heroes / WoS Tools / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Viveca","Children of the Mist",3,"DAMAGE_DEALT_UP","MARKSMAN","PASSIVE",v(2,4,6,8,10),"WOS Heroes / WoS Tools / Whiteout Survival Wiki; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen15(){}
}
