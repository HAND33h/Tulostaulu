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
        new Row("Rufus","Inferno Regiment","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / current Whiteout Survival Wiki; re-cross-checked 2026-09-10"),
        new Row("Rufus","Armor Crush Damage","EXTRA_DAMAGE","MARKSMAN","EACH_ATTACK",v(12,24,36,48,60),"WOS Heroes / current Whiteout Survival Wiki; re-cross-checked 2026-09-10"),
        new Row("Rufus","Armor Crush Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","1_TURN",v(5,10,15,20,25),"WOS Heroes / current Whiteout Survival Wiki; re-cross-checked 2026-09-10"),
        new Row("Rufus","Wrathful Quake","ENEMY_LETHALITY_DOWN","TARGET","ON_ATTACK_20_PERCENT_2_TURNS",v(10,20,30,40,50),"WOS Heroes / current Whiteout Survival Wiki; re-cross-checked 2026-09-10"),
        new Row("Rufus","Blazing Legion","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"Existing verified widget ladder; audited 2026-09-10"),

        new Row("Eleonora","Scorching Sun","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / current Whiteout Survival Wiki; re-cross-checked 2026-09-10"),
        new Row("Eleonora","Solaris Nexus Infantry","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(2,4,6,8,10),"Current Whiteout Survival Wiki explicitly gives 2/4/6/8/10; corrected from stale 5/10/15/20/25; cross-checked 2026-09-10"),
        new Row("Eleonora","Solaris Nexus Marksman","DAMAGE_DEALT_UP","MARKSMAN","PASSIVE",v(2,4,6,8,10),"Current Whiteout Survival Wiki explicitly gives 2/4/6/8/10; corrected from stale 5/10/15/20/25; cross-checked 2026-09-10"),
        new Row("Eleonora","Soaring Flame Damage Dealt","DAMAGE_DEALT_UP","ALL_TROOPS","EVERY_5_INFANTRY_ATTACKS_FOR_2_TURNS",v(5,10,15,20,25),"Current Whiteout Survival Wiki explicitly describes damage dealt + damage taken reduction; corrected old attack/defense semantics; cross-checked 2026-09-10"),
        new Row("Eleonora","Soaring Flame Damage Taken","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EVERY_5_INFANTRY_ATTACKS_FOR_2_TURNS",v(5,10,15,20,25),"Current Whiteout Survival Wiki explicitly describes damage dealt + damage taken reduction; corrected old attack/defense semantics; cross-checked 2026-09-10"),
        new Row("Eleonora","Last Fortress","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"Existing verified widget ladder; max 15 corroborated by current Whiteout Survival Wiki; cross-checked 2026-09-10"),

        new Row("Lloyd","Bird Invasion","ENEMY_LETHALITY_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"WOS Heroes / current Wiki / WSCO / Heaven Guardian; cross-checked 2026-09-10"),
        new Row("Lloyd","Iceflare Bomb Damage","ATTACK_UP","LANCER","EVERY_3_TURNS",v(30,60,90,120,150),"WOS Heroes / current Wiki / WSCO / Heaven Guardian; cross-checked 2026-09-10"),
        new Row("Lloyd","Iceflare Bomb Debuff","ENEMY_LETHALITY_DOWN","ENEMY_ALL","EVERY_3_TURNS_1_TURN",v(6,12,18,24,30),"WOS Heroes / current Wiki / WSCO / Heaven Guardian; cross-checked 2026-09-10"),
        new Row("Lloyd","Ingenious Mastery","TROOP_LETHALITY_UP","ALL_TROOPS","40_PERCENT_PROC",v(10,20,30,40,50),"WOS Heroes / current Wiki / WSCO / Heaven Guardian; cross-checked 2026-09-10"),
        new Row("Lloyd","Steel Maze","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"Current Whiteout Survival Wiki / WOS Heroes / WSCO / H5Joy / Heaven Guardian all identify Defender Troops Attack, max 15; stale conflicting upgrade label no longer treated as current truth; cross-checked 2026-09-10")
    ));
    private VerifiedHeroSkillGen11(){}
}
