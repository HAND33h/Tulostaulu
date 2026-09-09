package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public-source cross-checked Gen5 hero data only. */
public final class VerifiedHeroSkillGen5 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Hector","Sword Whirlwind","ATTACK_SPEED_UP","HECTOR","EXPLORATION_FOR_4S_CONTROL_IMMUNITY",v(80,90,100,110,120),"WhiteoutData / Whiteout Survival Wiki / Whiteout Survival Community Wiki; cross-checked 2026-09-09"),
        new Row("Hector","Desperado","DAMAGE_TAKEN_DOWN","HECTOR","EXPLORATION_WHILE_BELOW_50_PERCENT_HEALTH",v(20,30,40,50,60),"WhiteoutData / Whiteout Survival Wiki / Whiteout Survival Community Wiki; cross-checked 2026-09-09"),
        new Row("Hector","Adrenaline Surge","ATTACK_UP","HECTOR","EXPLORATION_WHILE_BELOW_50_PERCENT_HEALTH",v(16,24,32,40,48),"WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Hector","Survival Instincts","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EXPEDITION_40_PERCENT_CHANCE",v(10,20,30,40,50),"WhiteoutData / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Hector","Rampant Infantry","DAMAGE_DEALT_UP","INFANTRY","EXPEDITION_FIRST_10_ATTACKS_EACH_NEXT_85_PERCENT_OF_PREVIOUS",v(100,125,150,175,200),"WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Hector","Rampant Marksman","DAMAGE_DEALT_UP","MARKSMEN","EXPEDITION_FIRST_10_ATTACKS_EACH_NEXT_85_PERCENT_OF_PREVIOUS",v(20,40,60,80,100),"WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Hector","Blitz","ATTACK_DAMAGE","ALL_TROOPS","EXPEDITION_25_PERCENT_CHANCE",v(120,140,160,180,200),"WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Hector","Reaper's Embrace","HEAL_FROM_DAMAGE_DEALT","HECTOR","EXCLUSIVE_EXPLORATION_EXTENDS_SWORD_WHIRLWIND_UP_TO_1_5S",v(7,9,11,13,15),"WhiteoutData exact heal ladder / H5Joy max 15 and 1.5s / WoS Wiki max 15 and 1.5s; cross-checked 2026-09-09"),
        new Row("Hector","Goliath","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder / H5Joy max 15 / WoS Wiki max 15; cross-checked 2026-09-09"),
        new Row("Norah","Barrage","DAMAGE_DEALT","RANDOM_TARGET_AREA_OF_EFFECT","EXPLORATION_5_GRENADES_HEROES_FIRST",v(60,66,72,78,84),"WhiteoutData / H5Joy / Whiteout Survival Wiki / WoSTools; cross-checked 2026-09-09"),
        new Row("Norah","Flashbang","DAMAGE_DEALT","TARGET","EXPLORATION_STUNS_FOR_1_5S",v(50,55,60,65,70),"WhiteoutData / H5Joy / Whiteout Survival Wiki / WoSTools; cross-checked 2026-09-09"),
        new Row("Norah","Valkyrie Cry","ATTACK_UP","ALL_TROOPS","EXPLORATION_PASSIVE",v(3,3.5,4,4.5,5),"WhiteoutData / H5Joy / Whiteout Survival Wiki / WoSTools; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen5(){}
}
