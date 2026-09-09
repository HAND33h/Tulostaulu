package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public-source cross-checked Gen1 hero data only. */
public final class VerifiedHeroSkillGen1 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Jeronimo","Combo Slash","DAMAGE_DEALT_PER_SLASH","TARGET_AREA_OF_EFFECT","EXPLORATION_THREE_SLASHES_AFTER_LAUNCH",v(160,176,192,208,224),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Jeronimo","Sword Art","DAMAGE_DEALT","RECTANGULAR_AREA_AHEAD","EXPLORATION_EACH_ATTACK",v(15,17,19,21,23),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Jeronimo","Lone Wolf","ATTACK_UP","JERONIMO","EXPLORATION_WHILE_HEALTH_ABOVE_50_PERCENT",v(16,24,32,40,48),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Jeronimo","Battle Manifesto","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WOS Forge / Whiteout Survival Wiki / WSCO; cross-checked 2026-09-09"),
        new Row("Jeronimo","Swordmentor","ATTACK_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WOS Forge / Whiteout Survival Wiki / WSCO; cross-checked 2026-09-09"),
        new Row("Jeronimo","Expert Swordsmanship","SOURCE_CONFLICT","ALL_TROOPS","EXPEDITION_CONFLICTING_PUBLIC_DESCRIPTIONS",v(6,12,18,24,30),"WOS Forge says Damage Dealt Up for 2 turns every 4 turns; WSCO/other public guide says 4/8/12/16/20% stun chance for 1 turn. Marked conflict; do not simulate automatically; cross-checked 2026-09-09"),
        new Row("Jeronimo","Shield of Swords","DAMAGE_TAKEN_DOWN","JERONIMO","EXCLUSIVE_EXPLORATION_WHILE_ATTACKING_MAX_VERIFIED",v(30),"H5Joy / WOS Forge / Whiteout Survival Wiki max value; exact lower-level ladder not encoded; cross-checked 2026-09-09"),
        new Row("Jeronimo","Discernment","RALLY_TROOPS_ATTACK_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION_MAX_VERIFIED",v(15),"H5Joy / WOS Forge / Whiteout Survival Wiki max value; exact lower-level ladder not encoded; cross-checked 2026-09-09"),
        new Row("Jeronimo","Natural Leader Lethality","LETHALITY_UP","ALL_TROOPS","MAX_STAR_TALENT_GLOBAL",v(3,6,9,12,15),"WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Jeronimo","Natural Leader Health","HEALTH_UP","ALL_TROOPS","MAX_STAR_TALENT_GLOBAL",v(3,6,9,12,15),"WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),

        new Row("Natalia","Beast Charge","DAMAGE_DEALT","ENEMIES_IN_RANGE_AREA_OF_EFFECT","EXPLORATION_KNOCKBACK_AND_STUN_1S",v(160,176,192,208,224),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Natalia","Whip","DAMAGE_DEALT","TARGET","EXPLORATION_ACTIVE",v(150,165,180,195,210),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Natalia","Rage Response","ATTACK_UP","NATALIA","EXPLORATION_10_PERCENT_CHANCE_ON_DAMAGE_FOR_3S_MAX_5_STACKS",v(4,6,8,10,12),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Natalia","Feral Protection","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EXPEDITION_40_PERCENT_CHANCE",v(10,20,30,40,50),"WOS Forge / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Natalia","Queen of the Wild","ATTACK_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WOS Forge / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Natalia","Call of the Wild","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WOS Forge / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Natalia","Unity","DAMAGE_DEALT_UP","ALL_TROOPS","EXCLUSIVE_MAX_VERIFIED",v(30),"H5Joy / WOS Forge max value; exact lower-level ladder not encoded; cross-checked 2026-09-09"),
        new Row("Natalia","Invincibles","RALLY_TROOPS_LETHALITY_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION_MAX_VERIFIED",v(15),"H5Joy / WOS Forge max value; exact lower-level ladder not encoded; cross-checked 2026-09-09"),
        new Row("Natalia","Ursus Strength Attack","ATTACK_UP","ALL_TROOPS","MAX_STAR_TALENT_GLOBAL",v(2,4,6,8,10),"WOS Forge / Whiteout Survival Community Wiki; cross-checked 2026-09-09"),
        new Row("Natalia","Ursus Strength Defense","DEFENSE_UP","ALL_TROOPS","MAX_STAR_TALENT_GLOBAL",v(2,4,6,8,10),"WOS Forge / Whiteout Survival Community Wiki; cross-checked 2026-09-09"),

        new Row("Molly","Super Snowball","DAMAGE_DEALT","AREA_OF_EFFECT","EXPLORATION_FREEZE_FOR_1_5S",v(180,198,216,234,252),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Molly","Frost Ambush","DAMAGE_DEALT","TARGET","EXPLORATION_ACTIVE",v(150,165,180,195,210),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Molly","Youthful Persistence","ATTACK_SPEED_UP","MOLLY","EXPLORATION_WHILE_HEALTH_BELOW_50_PERCENT",v(20,30,40,50,60),"H5Joy / WOS Forge / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Molly","Snow's Grace","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EXPEDITION_40_PERCENT_CHANCE",v(10,20,30,40,50),"WOS Forge / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Molly","Ice Dominion","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_50_PERCENT_CHANCE_ON_ATTACK",v(10,20,30,40,50),"WOS Forge / Whiteout Survival Wiki / WSCO; cross-checked 2026-09-09"),
        new Row("Molly","Youthful Rage","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WOS Forge / Whiteout Survival Wiki / WSCO; cross-checked 2026-09-09"),
        new Row("Molly","Modified Launcher","DAMAGE_DEALT_UP","MOLLY","EXCLUSIVE_EXPLORATION_MAX_VERIFIED",v(30),"H5Joy / WOS Forge / Whiteout Survival Wiki max value; exact lower-level ladder not encoded; cross-checked 2026-09-09"),
        new Row("Molly","Snowy Blessing","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION_MAX_VERIFIED",v(15),"H5Joy / WOS Forge / Whiteout Survival Wiki max value; exact lower-level ladder not encoded; cross-checked 2026-09-09"),

        new Row("Zinman","Nail Scatter","DAMAGE_DEALT","TARGET","EXPLORATION_STUN_FOR_2S",v(55,60,65,70,75),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Zinman","Quick Defense","DEFENSE_UP","ZINMAN","EXPLORATION_WHILE_HEALTH_BELOW_50_PERCENT",v(50,75,100,125,150),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Zinman","Robust","ATTACK_SPEED_UP","ZINMAN","EXPLORATION_PASSIVE",v(10,15,20,25,30),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Zinman","Implacable Defense","DEFENSE_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(2,4,6,8,10),"WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Zinman","Implacable Health","HEALTH_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(2,4,6,8,10),"WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Zinman","Positional Battler","LETHALITY_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WOS Forge / Whiteout Survival Wiki / Community Wiki; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen1(){}
}
