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
        new Row("Bahiti","Fluorescence","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_50_PERCENT_CHANCE_ON_ATTACK",v(10,20,30,40,50),"Whiteout Survival Wiki / WSCO / WOS Heroes / One Chilled Gamer; cross-checked 2026-09-10"),

        new Row("Patrick","BBQ Feast Heal","HEAL_FROM_ATTACK","ALL_FRIENDLY_HEROES","EXPLORATION_ACTIVE",v(200,220,240,260,280),"Whiteout Survival Wiki / WSCO / Heaven Guardian; cross-checked 2026-09-10"),
        new Row("Patrick","BBQ Feast Attack","ATTACK_UP","ALL_FRIENDLY_HEROES","EXPLORATION_FOR_4S",v(5,5.5,6,6.5,7),"Whiteout Survival Wiki / WSCO; cross-checked 2026-09-10"),
        new Row("Patrick","Thick Belly","DAMAGE_TAKEN_DOWN","PATRICK","EXPLORATION_PASSIVE",v(10,15,20,25,30),"Whiteout Survival Wiki / WSCO; cross-checked 2026-09-10"),
        new Row("Patrick","Emergency Snack","HEAL_FROM_ATTACK","PATRICK","EXPLORATION_EVERY_5S",v(50,55,60,65,70),"Whiteout Survival Wiki / WSCO / Heaven Guardian; cross-checked 2026-09-10"),
        new Row("Patrick","Super Nutrients","HEALTH_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"Whiteout Survival Wiki / WSCO / Heaven Guardian; cross-checked 2026-09-10"),
        new Row("Patrick","Caloric Booster","ATTACK_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"Whiteout Survival Wiki / WSCO / Heaven Guardian; cross-checked 2026-09-10"),

        new Row("Jasser","Triple Volley Second Shot","DAMAGE_DEALT","TARGET","EXPLORATION_SECOND_OF_THREE_SHOTS",v(125,137.5,150,162.5,175),"Whiteout Survival Wiki / Community Wiki / WSCO; cross-checked 2026-09-10"),
        new Row("Jasser","Triple Volley Third Shot","DAMAGE_DEALT","AREA_OF_EFFECT","EXPLORATION_THIRD_OF_THREE_SHOTS",v(150,165,180,195,210),"Whiteout Survival Wiki / Community Wiki / WSCO; cross-checked 2026-09-10; first shot fixed at 100%"),
        new Row("Jasser","Suppressive Fire Damage","DAMAGE_DEALT","TARGET","EXPLORATION_ACTIVE",v(100,110,120,130,140),"Whiteout Survival Wiki / Community Wiki / WSCO; cross-checked 2026-09-10"),
        new Row("Jasser","Suppressive Fire Attack Speed","ATTACK_SPEED_DOWN","TARGET","EXPLORATION_FOR_2S",v(30,35,40,45,50),"Whiteout Survival Wiki / Community Wiki / WSCO; cross-checked 2026-09-10"),
        new Row("Jasser","Natural Precision","ATTACK_UP","JASSER","EXPLORATION_PASSIVE",v(8,12,16,20,24),"Whiteout Survival Wiki / Community Wiki / WSCO; cross-checked 2026-09-10"),
        new Row("Jasser","Tactical Genius","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"Whiteout Survival Wiki / Community Wiki / Heaven Guardian; cross-checked 2026-09-10"),
        new Row("Jasser","Enlightened Warfare","RESEARCH_SPEED_UP","CITY_RESEARCH","EXPEDITION_GROWTH_PASSIVE",v(3,6,9,12,15),"Whiteout Survival Wiki / Community Wiki / Heaven Guardian; cross-checked 2026-09-10")
    ));
    private VerifiedHeroSkillGen0(){}
}
