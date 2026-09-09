package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public-source cross-checked Gen3 hero data only. */
public final class VerifiedHeroSkillGen3 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Logan","Fists of Destruction","DAMAGE_DEALT","TARGET","EXPLORATION_ATTACK_SPEED_DOWN_50_PERCENT_FOR_4S",v(120,132,144,156,168),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Logan","Power Suit Proc","PROC_CHANCE","LOGAN","EXPLORATION_WHEN_ATTACKED_DEFENSE_BUFF_FOR_2S_UP_TO_5_STACKS",v(8,10,12,14,16),"WhiteoutData / H5Joy; cross-checked 2026-09-09; Defense ladder has Lv3 conflict (14 vs 13) and is intentionally not encoded"),
        new Row("Logan","Blustery Strike","DAMAGE_DEALT","CONE_AREA_TARGETS","EXPLORATION_30_PERCENT_STUN_CHANCE_FOR_1S",v(80,88,96,104,112),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Logan","Lion's Might","ATTACK_DOWN","ALL_ENEMY_TROOPS","EXPEDITION_PASSIVE",v(4,8,12,16,20),"WhiteoutData / WoS community references; cross-checked 2026-09-09"),
        new Row("Logan","Lion Intimidation","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EXPEDITION_PASSIVE",v(4,8,12,16,20),"WhiteoutData / WoS community references; cross-checked 2026-09-09"),
        new Row("Logan","Leader Inspiration","HEALTH_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WhiteoutData / WoS community references; cross-checked 2026-09-09"),
        new Row("Logan","Enhanced Fists of Steel","DAMAGE_DEALT_UP","FISTS_OF_DESTRUCTION","EXCLUSIVE_EXPLORATION",v(10,15,20,25,30),"WhiteoutData exact ladder / H5Joy max 30; cross-checked 2026-09-09"),
        new Row("Logan","Strong Protection","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder / H5Joy max 15; cross-checked 2026-09-09"),
        new Row("Mia","Fate's Finale","DAMAGE_DEALT","ENEMY_TARGET","EXPLORATION_EITHER_ATTACK_DOWN_20_PERCENT_FOR_2S_OR_STUN_1_5S",v(270,297,324,351,378),"WhiteoutData / Whiteout Survival Wiki / WOS Heroes; cross-checked 2026-09-09"),
        new Row("Mia","Bad Omen","FLUCTUATING_DAMAGE_BASE","ENEMY_TARGET","EXPLORATION_FINAL_ROLL_5_TO_600_PERCENT_OF_BASE",v(50,55,60,65,70),"WhiteoutData / Whiteout Survival Wiki / WOS Heroes; cross-checked 2026-09-09"),
        new Row("Mia","Guardian of Destiny","FLUCTUATING_HEAL_BASE","LOWEST_HEALTH_FRIENDLY_HERO","EXPLORATION_FINAL_ROLL_5_TO_400_PERCENT_OF_BASE",v(100,110,120,130,140),"WhiteoutData / Whiteout Survival Wiki / WOS Heroes; cross-checked 2026-09-09"),
        new Row("Mia","Bad Luck Streak","DAMAGE_TAKEN_UP","ENEMY_TARGET","EXPEDITION_50_PERCENT_CHANCE_ON_ALL_TROOP_ATTACKS",v(10,20,30,40,50),"WhiteoutData / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Mia","Lucky Charm","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_50_PERCENT_CHANCE_ON_ATTACK",v(10,20,30,40,50),"WhiteoutData / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Mia","Ritual Deciphering","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EXPEDITION_40_PERCENT_CHANCE",v(10,20,30,40,50),"WhiteoutData / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Mia","Vision of Truth","FLUCTUATION_LIMIT_UP","BAD_OMEN_AND_GUARDIAN_OF_DESTINY","EXCLUSIVE_EXPLORATION",v(30,60,90,120,150),"WhiteoutData exact ladder / H5Joy and multiple community sources max 150; cross-checked 2026-09-09"),
        new Row("Mia","Rally of Fate","RALLY_ATTACK_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder / H5Joy and multiple community sources max 15; cross-checked 2026-09-09"),
        new Row("Greg","Righteous Wind","DAMAGE_DEALT","AREA_OF_EFFECT","EXPLORATION_STUNS_FOR_2S",v(160,176,192,208,224),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Greg","Poetic Justice Damage","DAMAGE_DEALT","ENEMY_TARGET","EXPLORATION_RANDOM_PUNISHMENT_OR_50_PERCENT_ATTACK_HEAL_COMMENDATION",v(220,240,260,280,300),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Greg","Fair Judgment","DAMAGE_TAKEN_UP","ENEMY_TARGET","EXPLORATION_FOR_3S",v(10,15,20,25,30),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Greg","Sword of Justice","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_20_PERCENT_CHANCE_FOR_3_TURNS",v(8,16,24,32,40),"WhiteoutData / Gen3 community references; cross-checked 2026-09-09"),
        new Row("Greg","Deterrence of Law","DAMAGE_DEALT_DOWN","ALL_ENEMY_TROOPS","EXPEDITION_20_PERCENT_CHANCE_FOR_2_TURNS",v(10,20,30,40,50),"WhiteoutData / Gen3 community references; cross-checked 2026-09-09"),
        new Row("Greg","Law and Order","HEALTH_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WhiteoutData / Gen3 community references; cross-checked 2026-09-09"),
        new Row("Greg","Courtroom Order Silence","SILENCE_DURATION_SECONDS","ENEMY_TARGET","EXCLUSIVE_EXPLORATION_PREVENTS_SKILL_USE_ALREADY_CAST_SKILLS_UNAFFECTED",v(3,3.5,4,4.5,5),"WhiteoutData exact ladder / H5Joy max 5s; cross-checked 2026-09-09"),
        new Row("Greg","Courtroom Order Damage","DAMAGE_DEALT","ENEMY_TARGET","EXCLUSIVE_EXPLORATION_WITH_SILENCE",v(220,240,260,280,300),"WhiteoutData exact ladder / H5Joy max 300; cross-checked 2026-09-09"),
        new Row("Greg","Trumpet of Justice","RALLY_HEALTH_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder / H5Joy max 15; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen3(){}
}
