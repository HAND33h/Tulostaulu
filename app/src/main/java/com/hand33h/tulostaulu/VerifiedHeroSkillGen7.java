package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public-source cross-checked Gen7 hero data only. */
public final class VerifiedHeroSkillGen7 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Edith","Ironclad Punch Damage","DAMAGE_DEALT","ENEMIES_IN_FAN_AHEAD","EXPLORATION_STUN_1S",v(100,110,120,130,140),"WOS Heroes / WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Edith","Ironclad Punch Attack","ATTACK_UP","MR_TIN","EXPLORATION_FOR_2S",v(20,40,60,80,100),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Edith","Escape Capsule","DAMAGE_DEALT","NEARBY_ENEMIES","EXPLORATION_AT_0_HEALTH",v(200,220,240,260,280),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Edith","Preemptive Alerts","PROC_CHANCE","MR_TIN","EXPLORATION_REDUCES_DAMAGE_TAKEN_BY_50_PERCENT",v(10,20,30,40,50),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Edith","Strategic Balance Marksmen","DAMAGE_TAKEN_DOWN","MARKSMEN","EXPEDITION_PASSIVE",v(4,8,12,16,20),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Edith","Strategic Balance Lancers","DAMAGE_DEALT_UP","LANCERS","EXPEDITION_PASSIVE",v(4,8,12,16,20),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Edith","Ironclad","DAMAGE_TAKEN_DOWN","INFANTRY","EXPEDITION_PASSIVE",v(4,8,12,16,20),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Edith","Steel Sentinel","HEALTH_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Edith","Fortworks","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gordon","Poison Blast","DAMAGE_DEALT","NEARBY_ENEMIES","EXPLORATION_EVERY_0_5S_FOR_3S",v(50,55,60,65,70),"WOS Heroes / WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Gordon","Toxic Molotov Damage","DAMAGE_DEALT","TARGET","EXPLORATION_EVERY_0_5S",v(25,27.5,30,32.5,35),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gordon","Toxic Molotov Vulnerability","DAMAGE_TAKEN_UP","TARGET","EXPLORATION_FOR_2S",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gordon","Tolerization","DEFENSE_UP","GORDON","EXPLORATION_PASSIVE",v(25,37.5,50,62.5,75),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gordon","Venom Infusion Damage","DAMAGE_DEALT_UP","LANCERS","EXPEDITION_EVERY_2_ATTACKS",v(20,40,60,80,100),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gordon","Venom Infusion Poison","ENEMY_DAMAGE_DEALT_DOWN","POISONED_TARGET","EXPEDITION_FOR_1_TURN",v(4,8,12,16,20),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gordon","Chemical Terror Lancer Damage","DAMAGE_DEALT_UP","LANCERS","EXPEDITION_EVERY_3_TURNS_FOR_1_TURN",v(30,60,90,120,150),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gordon","Chemical Terror Enemy Damage","ENEMY_DAMAGE_DEALT_DOWN","ALL_ENEMY_TROOPS","EXPEDITION_EVERY_3_TURNS_FOR_1_TURN",v(6,12,18,24,30),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gordon","Toxic Release Infantry","ENEMY_DAMAGE_TAKEN_UP","ENEMY_INFANTRY","EXPEDITION_EVERY_4_TURNS_FOR_2_TURNS",v(6,12,18,24,30),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gordon","Toxic Release Marksmen","ENEMY_DAMAGE_DEALT_DOWN","ENEMY_MARKSMEN","EXPEDITION_EVERY_4_TURNS_FOR_2_TURNS",v(6,12,18,24,30),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gordon","Bio Assault","RALLY_SQUADS_LETHALITY_UP","RALLY_SQUADS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Bradley","Destructor","DAMAGE_DEALT","AREA_OF_EFFECT","EXPLORATION",v(300,330,360,390,420),"WhiteoutData / whiteout-survival.com / H5Joy; cross-checked 2026-09-09"),
        new Row("Bradley","Incendiary Shell Impact","DAMAGE_DEALT","AREA_OF_EFFECT","EXPLORATION",v(60,66,72,78,84),"WhiteoutData / whiteout-survival.com / H5Joy; cross-checked 2026-09-09"),
        new Row("Bradley","Incendiary Shell Crater","DAMAGE_DEALT","ENEMIES_IN_FLAMING_CRATER","EXPLORATION_EVERY_0_5S_FOR_2S",v(17,19,21,23,25),"WhiteoutData / whiteout-survival.com / H5Joy; cross-checked 2026-09-09"),
        new Row("Bradley","Audacious","ATTACK_UP","BRADLEY","EXPLORATION_PASSIVE",v(10,14,18,22,26),"WhiteoutData / whiteout-survival.com / H5Joy; cross-checked 2026-09-09"),
        new Row("Bradley","Veteran's Might","ATTACK_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Bradley","Power Shot vs Lancers","DAMAGE_DEALT_UP","ENEMY_LANCERS","EXPEDITION_ALL_TROOPS",v(6,12,18,24,30),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Bradley","Power Shot vs Infantry","DAMAGE_DEALT_UP","ENEMY_INFANTRY","EXPEDITION_ALL_TROOPS",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Bradley","Tactical Assistance","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_EVERY_4_TURNS_FOR_2_TURNS",v(6,12,18,24,30),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen7(){}
}
