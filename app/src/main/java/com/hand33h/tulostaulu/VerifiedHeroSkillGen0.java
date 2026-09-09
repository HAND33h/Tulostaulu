package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public-source cross-checked Generation 0 epic hero skill data. */
public final class VerifiedHeroSkillGen0 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Sergey","Shielded Strike","DAMAGE_DEALT","AREA_OF_EFFECT","EXPLORATION_KNOCKBACK",v(200,220,240,260,280),"Whiteout Survival Wiki / WSCO; cross-checked 2026-09-10"),
        new Row("Sergey","Joint Defense","DEFENSE_UP","ALL_FRIENDLY_HEROES","EXPLORATION_PASSIVE",v(5,7.5,10,12.5,15),"Whiteout Survival Wiki / WSCO; cross-checked 2026-09-10"),
        new Row("Sergey","Shield Block","DAMAGE_TAKEN_DOWN","SERGEY","EXPLORATION_PASSIVE",v(10,15,20,25,30),"Whiteout Survival Wiki / WSCO; cross-checked 2026-09-10"),
        new Row("Sergey","Defenders' Edge","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EXPEDITION_PASSIVE",v(4,8,12,16,20),"Whiteout Survival Wiki / WSCO; cross-checked 2026-09-10"),
        new Row("Sergey","Weaken","ATTACK_DOWN","ALL_ENEMY_TROOPS","EXPEDITION_PASSIVE",v(4,8,12,16,20),"Whiteout Survival Wiki / WSCO; cross-checked 2026-09-10"),

        new Row("Jessie","Burst Fire","DAMAGE_DEALT_PER_TICK","FORWARD_ARC","EXPLORATION_EVERY_0_5S_FOR_2S",v(55,60,65,70,75),"Whiteout Survival Wiki / WSCO / WoSTools; cross-checked 2026-09-10"),
        new Row("Jessie","Defense Upgrade","DEFENSE_UP","JESSIE","EXPLORATION_PASSIVE",v(25,37.5,50,62.5,70),"Whiteout Survival Wiki / WSCO / WoSTools; cross-checked 2026-09-10"),
        new Row("Jessie","Weapon Upgrade","ATTACK_UP","JESSIE","EXPLORATION_PASSIVE",v(8,12,16,20,24),"Whiteout Survival Wiki / WSCO / WoSTools; cross-checked 2026-09-10"),
        new Row("Jessie","Stand of Arms","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"Whiteout Survival Wiki / WSCO / WoSTools; cross-checked 2026-09-10"),
        new Row("Jessie","Bulwarks","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EXPEDITION_PASSIVE",v(4,8,12,16,20),"Whiteout Survival Wiki / WSCO / WoSTools; cross-checked 2026-09-10"),

        new Row("Bahiti","Precise Shot","DAMAGE_DEALT","TARGET","EXPLORATION_ACTIVE",v(400,440,480,520,560),"Whiteout Survival Wiki / WSCO / WOS Heroes / One Chilled Gamer; cross-checked 2026-09-10"),
        new Row("Bahiti","Quick Shot","ATTACK_SPEED_UP","BAHITI","EXPLORATION_PASSIVE",v(10,15,20,25,30),"Whiteout Survival Wiki / WSCO / WOS Heroes / One Chilled Gamer; cross-checked 2026-09-10"),
        new Row("Bahiti","Pathfinder Vision","DAMAGE_DEALT_UP","BAHITI","EXPLORATION_PASSIVE",v(10,15,20,25,30),"Whiteout Survival Wiki / WSCO / WOS Heroes / One Chilled Gamer; cross-checked 2026-09-10"),
        new Row("Bahiti","Sixth Sense","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EXPEDITION_PASSIVE",v(4,8,12,16,20),"Whiteout Survival Wiki / WSCO / WOS Heroes / One Chilled Gamer; cross-checked 2026-09-10"),
        new Row("Bahiti","Fluorescence","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_50_PERCENT_CHANCE_ON_ATTACK",v(10,20,30,40,50),"Whiteout Survival Wiki / WSCO / WOS Heroes / One Chilled Gamer; cross-checked 2026-09-10")
    ));
    private VerifiedHeroSkillGen0(){}
}
