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
        new Row("Jeronimo","Expert Swordsmanship","STUN_CHANCE","ENEMY_TARGET","EXPEDITION_ALL_TROOP_ATTACKS_STUN_FOR_1_TURN",v(4,8,12,16,20),"One Chilled Gamer / whiteout-survival.com exact ladder agree on stun chance 4/8/12/16/20 for 1 turn; Whiteout Survival Wiki currently conflicts with a 6/12/18/24/30 damage-dealt description, retained as provenance note; cross-checked 2026-09-09"),
        new Row("Jeronimo","Shield of Swords","DAMAGE_TAKEN_DOWN","JERONIMO","EXCLUSIVE_EXPLORATION_WHILE_ATTACKING",v(10,15,20,25,30),"One Chilled Gamer exact ladder / H5Joy max 30 / WSCO max 30; cross-checked 2026-09-09"),
        new Row("Jeronimo","Discernment","RALLY_TROOPS_ATTACK_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"One Chilled Gamer exact ladder / H5Joy max 15 / WSCO max 15; cross-checked 2026-09-09"),
        new Row("Jeronimo","Natural Leader Lethality","LETHALITY_UP","ALL_TROOPS","MAX_STAR_TALENT_GLOBAL",v(3,6,9,12,15),"WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Jeronimo","Natural Leader Health","HEALTH_UP","ALL_TROOPS","MAX_STAR_TALENT_GLOBAL",v(3,6,9,12,15),"WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Natalia","Beast Charge","DAMAGE_DEALT","ENEMIES_IN_RANGE_AREA_OF_EFFECT","EXPLORATION_KNOCKBACK_AND_STUN_1S",v(160,176,192,208,224),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Natalia","Whip","DAMAGE_DEALT","TARGET","EXPLORATION_ACTIVE",v(150,165,180,195,210),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Natalia","Rage Response","ATTACK_UP","NATALIA","EXPLORATION_10_PERCENT_CHANCE_ON_DAMAGE_FOR_3S_MAX_5_STACKS",v(4,6,8,10,12),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Natalia","Wildling Roar","STUN_CHANCE","ENEMY_TROOPS","EXPEDITION_ON_ATTACK_STUN_FOR_1_TURN",v(4,8,12,16,20),"WSCO / Gen1 comparison; cross-checked 2026-09-09; replaces incorrectly modeled Feral Protection"),
        new Row("Natalia","Queen of the Wild","ATTACK_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WOS Forge / Whiteout Survival Wiki / WSCO; cross-checked 2026-09-09"),
        new Row("Natalia","Call of the Wild","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_BEAST_RALLIES_ONLY",v(5,10,15,20,25),"WSCO / Gen1 comparison; cross-checked 2026-09-09; target semantics corrected to beast rallies"),
        new Row("Natalia","Unity","DAMAGE_DEALT_UP","NATALIA","EXCLUSIVE_EXPLORATION",v(10,15,20,25,30),"One Chilled Gamer exact ladder / H5Joy max 30 / WOS Forge max 30; cross-checked 2026-09-09"),
        new Row("Natalia","Invincibles","RALLY_TROOPS_LETHALITY_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"One Chilled Gamer exact ladder / H5Joy max 15 / WOS Forge max 15; cross-checked 2026-09-09"),
        new Row("Natalia","Ursus Strength Attack","ATTACK_UP","ALL_TROOPS","MAX_STAR_TALENT_GLOBAL",v(2,4,6,8,10),"WOS Forge / Whiteout Survival Community Wiki; cross-checked 2026-09-09"),
        new Row("Natalia","Ursus Strength Defense","DEFENSE_UP","ALL_TROOPS","MAX_STAR_TALENT_GLOBAL",v(2,4,6,8,10),"WOS Forge / Whiteout Survival Community Wiki; cross-checked 2026-09-09"),
        new Row("Molly","Super Snowball","DAMAGE_DEALT","AREA_OF_EFFECT","EXPLORATION_FREEZE_FOR_1_5S",v(180,198,216,234,252),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Molly","Frost Ambush","DAMAGE_DEALT","TARGET","EXPLORATION_ACTIVE",v(150,165,180,195,210),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Molly","Youthful Persistence","ATTACK_SPEED_UP","MOLLY","EXPLORATION_WHILE_HEALTH_BELOW_50_PERCENT",v(20,30,40,50,60),"H5Joy / WOS Forge / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Molly","Snow's Grace","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EXPEDITION_40_PERCENT_CHANCE",v(10,20,30,40,50),"WOS Forge / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Molly","Ice Dominion","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_50_PERCENT_CHANCE_ON_ATTACK",v(10,20,30,40,50),"WOS Forge / Whiteout Survival Wiki / WSCO; cross-checked 2026-09-09"),
        new Row("Molly","Youthful Rage","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WOS Forge / Whiteout Survival Wiki / WSCO; cross-checked 2026-09-09"),
        new Row("Molly","Modified Launcher","DAMAGE_DEALT_UP","MOLLY","EXCLUSIVE_EXPLORATION",v(10,15,20,25,30),"One Chilled Gamer exact ladder / Whiteout Survival Wiki max 30 / WSCO max 30; cross-checked 2026-09-09"),
        new Row("Molly","Snowy Blessing","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"One Chilled Gamer exact ladder / Whiteout Survival Wiki max 15 / WSCO max 15; cross-checked 2026-09-09"),
        new Row("Zinman","Nail Scatter","DAMAGE_DEALT","TARGET","EXPLORATION_STUN_FOR_2S",v(55,60,65,70,75),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Zinman","Quick Defense","DEFENSE_UP","ZINMAN","EXPLORATION_WHILE_HEALTH_BELOW_50_PERCENT",v(50,75,100,125,150),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Zinman","Robust","ATTACK_SPEED_UP","ZINMAN","EXPLORATION_PASSIVE",v(10,15,20,25,30),"H5Joy / WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Zinman","Implacable Defense","DEFENSE_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(2,4,6,8,10),"WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Zinman","Implacable Health","HEALTH_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(2,4,6,8,10),"WOS Forge / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Zinman","Bastionist Resource Cost","BASIC_RESOURCE_COST_DOWN","CITY_BUILDING_UPGRADES","EXPEDITION_DEVELOPMENT_PASSIVE",v(3,6,9,12,15),"Whiteout Survival Wiki / WSCO; cross-checked 2026-09-09"),
        new Row("Zinman","Bastionist Building Speed","BUILDING_UPGRADE_SPEED_UP","CITY_BUILDING_UPGRADES","EXPEDITION_DEVELOPMENT_PASSIVE",v(3,6,9,12,15),"Whiteout Survival Wiki / WSCO; cross-checked 2026-09-09"),
        new Row("Zinman","Positional Battler","LETHALITY_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"Whiteout Survival Wiki / WSCO; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen1(){}
}
