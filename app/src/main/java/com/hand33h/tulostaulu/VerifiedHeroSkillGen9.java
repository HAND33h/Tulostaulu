package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class VerifiedHeroSkillGen9 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Xura","Fungal Fog","DAMAGE_TAKEN_DOWN","ALL_FRIENDLY_TROOPS","PASSIVE",v(4,8,12,16,20),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Xura","Piercing Arrow Damage","DAMAGE_UP","MARKSMEN_EVERY_2_STRIKES","PASSIVE",v(20,40,60,80,100),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Xura","Piercing Arrow Vulnerability","ENEMY_DAMAGE_TAKEN_UP","PIERCING_ARROW_TARGET","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Xura","Unorthodoxy Damage Taken","DAMAGE_TAKEN_DOWN","MARKSMEN","PASSIVE",v(3,6,9,12,15),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Xura","Unorthodoxy Damage Dealt","DAMAGE_UP","MARKSMEN","PASSIVE",v(2,4,6,8,10),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Xura","War Cry","DAMAGE_DEALT_UP","HIGHEST_ATTACK_ALLY","EXCLUSIVE_EXPLORATION_4S",v(20,30,40,50,60),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Xura","Gaiac Hymn","DEFENDER_ATTACK_UP","CITY_DEFENDERS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Fred","Hydraulic Suppression","ENEMY_LETHALITY_DOWN","ALL_ENEMY_TROOPS","PASSIVE",v(4,8,12,16,20),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Fred","Acidification","ENEMY_DAMAGE_TAKEN_UP","ENEMY_INFANTRY","PASSIVE",v(4,8,12,16,20),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Fred","Floodbringer Damage","DAMAGE_UP","LANCERS_EVERY_4_STRIKES","PASSIVE",v(40,80,120,160,200),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Fred","Floodbringer Enemy Damage Dealt","ENEMY_DAMAGE_DEALT_DOWN","ALL_ENEMY_TROOPS_NEXT_TURN","PASSIVE",v(4,8,12,16,20),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Fred","Idealism Attack","ATTACK_UP","FRED","EXCLUSIVE_EXPLORATION",v(8,12,16,20,24),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Fred","Idealism Defense Per Dispel","DEFENSE_UP_PER_DISPEL","FRED","EXCLUSIVE_EXPLORATION_MAX_5_STACKS",v(2,4,6,8,10),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Fred","Call of the Firefighter","RALLY_TROOPS_ATTACK_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Magnus","Rapacious","TROOP_ATTACK_UP","ALL_FRIENDLY_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Magnus","Iron Phalanx","DEFENSE_UP","INFANTRY_WHEN_ATTACKING","40_PERCENT_CHANCE_1_TURN",v(10,20,30,40,50),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Magnus","Iceman Damage Taken","DAMAGE_TAKEN_DOWN","FRIENDLY_INFANTRY","PASSIVE",v(2,4,6,8,10),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Magnus","Iceman Damage Dealt","DAMAGE_UP","FRIENDLY_MARKSMEN","PASSIVE",v(2,4,6,8,10),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Magnus","Heroic Stock Damage Taken","DAMAGE_TAKEN_DOWN","MAGNUS","EXCLUSIVE_EXPLORATION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Magnus","Heroic Stock Frozen Fury Defense Bonus","DEFENSE_UP","FROZEN_FURY","EXCLUSIVE_EXPLORATION",v(25,37.5,50,62.5,75),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Magnus","Valoric Inspiration","DEFENDER_HEALTH_UP","DEFENDER_SQUADS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen9(){}
}
