package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Cross-validated Gen11 Expedition data. Values are Lv1..Lv5. */
public final class VerifiedHeroSkillGen11 {
    public static final class Row {
        public final String hero,skill,effect,target,trigger,source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double...x){return x;}
    public static final List<Row> DATA=Collections.unmodifiableList(Arrays.asList(
        new Row("Rufus","Inferno Regiment","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Rufus","Armor Crush Damage","EXTRA_DAMAGE","MARKSMAN","EACH_ATTACK",v(12,24,36,48,60),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Rufus","Armor Crush Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","1_TURN",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Rufus","Wrathful Quake","ENEMY_LETHALITY_DOWN","TARGET","ON_ATTACK_20_PERCENT_2_TURNS",v(10,20,30,40,50),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Rufus","Blazing Legion","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),

        new Row("Eleonora","Scorching Sun","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Eleonora","Solaris Nexus Infantry","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Eleonora","Solaris Nexus Marksman","DAMAGE_DEALT_UP","MARKSMAN","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Eleonora","Soaring Flame Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Eleonora","Soaring Flame Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Eleonora","Last Fortress","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),

        new Row("Lloyd","Bird Invasion","ENEMY_LETHALITY_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"WOS Heroes / WSCO; full ladder + max cross-checked 2026-09-09"),
        new Row("Lloyd","Iceflare Bomb Damage","ATTACK_UP","LANCER","EVERY_3_TURNS",v(30,60,90,120,150),"WOS Heroes / WSCO; full ladder + max cross-checked 2026-09-09"),
        new Row("Lloyd","Iceflare Bomb Debuff","ENEMY_LETHALITY_DOWN","ENEMY_ALL","EVERY_3_TURNS_1_TURN",v(6,12,18,24,30),"WOS Heroes / WSCO; full ladder + max cross-checked 2026-09-09"),
        new Row("Lloyd","Ingenious Mastery","TROOP_LETHALITY_UP","ALL_TROOPS","40_PERCENT_PROC",v(10,20,30,40,50),"WOS Heroes / WSCO; full ladder + max cross-checked 2026-09-09"),
        new Row("Lloyd","Steel Maze","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09; SOURCE_CONFLICT: WhiteoutData description says Defender Troops Attack while upgrade label says Defense")
    ));
    private VerifiedHeroSkillGen11(){}
}
