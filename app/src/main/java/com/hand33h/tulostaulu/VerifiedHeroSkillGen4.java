package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public-source cross-checked Gen4 hero data only. */
public final class VerifiedHeroSkillGen4 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Ahmose","Cthugha's Protection","DAMAGE_TAKEN_DOWN","NEARBY_FRIENDLY_TROOPS","EXPLORATION_AHMOSE_INVULNERABLE_CONTROL_IMMUNE_FOR_2S",v(30,40,50,60,70),"WhiteoutData / H5Joy / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Ahmose","Daybreak Knife","DAMAGE_DEALT","FRONT_ENEMIES","EXPLORATION_TARGET_DAMAGE_TAKEN_UP_20_PERCENT_FOR_2S",v(70,77,84,91,98),"WhiteoutData / H5Joy / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Ahmose","Ancestral Blessing","HEAL_FROM_ATTACK","AHMOSE","EXPLORATION_AFTER_CTHUGHAS_PROTECTION_OVER_5S",v(30,33,36,39,42),"WhiteoutData / H5Joy / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Ahmose","Viper Formation Lancer Marksman","DAMAGE_TAKEN_DOWN","LANCERS_AND_MARKSMEN","EXPEDITION_INFANTRY_PAUSES_ONCE_EVERY_4_ATTACKS_FOR_2_TURNS",v(10,15,20,25,30),"WhiteoutData / WoSForge Wiki; cross-checked 2026-09-09; WSCO summary conflicts and is excluded"),
        new Row("Ahmose","Viper Formation Infantry","DAMAGE_TAKEN_DOWN","INFANTRY","EXPEDITION_INFANTRY_PAUSES_ONCE_EVERY_4_ATTACKS_FOR_2_TURNS",v(10,25,40,55,70),"WhiteoutData / WoSForge Wiki; cross-checked 2026-09-09; WSCO summary conflicts and is excluded"),
        new Row("Ahmose","Prayer of Flame","DAMAGE_DEALT_UP","FRIENDLY_INFANTRY","EXPEDITION_PASSIVE",v(20,40,60,80,100),"WhiteoutData / WoSForge Wiki; cross-checked 2026-09-09; WSCO summary conflicts and is excluded"),
        new Row("Ahmose","Blade of Light Extra Damage","EXTRA_DAMAGE_PER_ATTACK","FRIENDLY_INFANTRY","EXPEDITION_TARGET_VULNERABILITY_FOR_1_TURN",v(12,24,36,48,60),"WhiteoutData / WoSForge Wiki; cross-checked 2026-09-09; WSCO summary conflicts and is excluded"),
        new Row("Ahmose","Blade of Light Vulnerability","DAMAGE_TAKEN_UP","ENEMY_TARGET","EXPEDITION_FOR_1_TURN",v(5,10,15,20,25),"WhiteoutData / WoSForge Wiki; cross-checked 2026-09-09; WSCO summary conflicts and is excluded"),
        new Row("Ahmose","Unyielding Determination","ATTACK_UP","FRIENDLY_TROOPS_UNDER_CTHUGHAS_PROTECTION","EXCLUSIVE_EXPLORATION_FOR_2_5S",v(30,33,36,39,42),"WhiteoutData exact ladder / H5Joy max 42 and 2.5s / Whiteout Survival Wiki max 42 and 2.5s; cross-checked 2026-09-09"),
        new Row("Ahmose","Oath of Guardian","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION_CITY_DEFENSE",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder / H5Joy and Whiteout Survival Wiki max 15 / Community Wiki widget progression corroborates 5-15 ladder; cross-checked 2026-09-09"),
        new Row("Reina","Phantom Assault","DAMAGE_DEALT","ENEMIES_FROM_BEHIND_AREA_OF_EFFECT","EXPLORATION_ILLUSION_AMBUSH",v(300,330,360,390,420),"WhiteoutData / H5Joy / Whiteout Survival Wiki / Community Wiki; cross-checked 2026-09-09"),
        new Row("Reina","Vanishing Technique","NORMAL_ATTACK_DODGE_CHANCE","REINA","EXPLORATION_WHEN_RECEIVING_NORMAL_ATTACK_ILLUSION_DODGE",v(5,10,15,20,25),"WhiteoutData / H5Joy / Whiteout Survival Wiki / Community Wiki; cross-checked 2026-09-09"),
        new Row("Reina","Poison of Demon","DAMAGE_DEALT","ENEMY_TARGET_HEROES_FIRST","EXPLORATION_IMMOBILIZE_FOR_1_5S",v(100,110,120,130,140),"WhiteoutData / H5Joy / Whiteout Survival Wiki / Community Wiki; cross-checked 2026-09-09"),
        new Row("Reina","Assassin's Instinct","NORMAL_ATTACK_DAMAGE_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(10,15,20,25,30),"WhiteoutData / WoSForge Wiki; cross-checked 2026-09-09"),
        new Row("Reina","Swift Jive","NORMAL_ATTACK_DODGE_CHANCE","ALL_TROOPS","EXPEDITION_WHEN_RECEIVING_NORMAL_ATTACK",v(4,8,12,16,20),"WhiteoutData / WoSForge Wiki; cross-checked 2026-09-09"),
        new Row("Reina","Shadow Blade","EXTRA_ATTACK_DAMAGE","LANCERS","EXPEDITION_25_PERCENT_CHANCE",v(120,140,160,180,200),"WhiteoutData / WoSForge Wiki; cross-checked 2026-09-09"),
        new Row("Reina","Silhouette Strike","EXTRA_KUNAI_DAMAGE","ENEMY_TARGET","EXCLUSIVE_EXPLORATION_40_PERCENT_CHANCE_WITH_NORMAL_ATTACK",v(25,30,35,40,45),"WhiteoutData exact ladder / H5Joy and WoSForge max 45 with 40% chance; cross-checked 2026-09-09"),
        new Row("Reina","Fiery Invasion","RALLY_LETHALITY_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder / H5Joy and WoSForge max 15; cross-checked 2026-09-09"),
        new Row("Lynn","Hymn of Sidrak Attack","ATTACK_UP","ALL_FRIENDLY_HEROES","EXPLORATION_CLEARS_DEBUFFS_AND_GRANTS_DEBUFF_IMMUNITY",v(3,4,5,6,7),"WhiteoutData / H5Joy / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Lynn","Hymn of Sidrak Duration","DURATION_SECONDS","ALL_FRIENDLY_HEROES","EXPLORATION_ATTACK_BUFF_AND_DEBUFF_IMMUNITY",v(3,3.5,4,4.5,5),"WhiteoutData / H5Joy / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Lynn","Lethal Finale","DAMAGE_DEALT","ENEMIES_ALONG_PROJECTILE_PATH","EXPLORATION_PENETRATING_SHOT",v(220,240,260,280,300),"WhiteoutData / H5Joy / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Lynn","Discordant Tune Attack Speed","ATTACK_SPEED_DOWN","ALL_ENEMY_HEROES","EXPLORATION_PASSIVE_DEBUFF",v(1,1.5,2,2.5,3),"WhiteoutData typo at Lv1 excluded / H5Joy / Whiteout Survival Wiki agree 1-3; cross-checked 2026-09-09"),
        new Row("Lynn","Discordant Tune Healing","HEALING_EFFECT_DOWN","ALL_ENEMY_HEROES","EXPLORATION_PASSIVE_DEBUFF",v(40,45,50,55,60),"WhiteoutData / H5Joy / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Lynn","Song of Lion","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_40_PERCENT_CHANCE",v(10,20,30,40,50),"WhiteoutData / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Lynn","Melancholic Ballad","DAMAGE_DEALT_DOWN","ALL_ENEMY_TROOPS","EXPEDITION_PASSIVE",v(4,8,12,16,20),"WhiteoutData / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Lynn","Oonai Cadenza","MARKSMAN_ATTACK_UP_PER_STACK","MARKSMEN","EXPEDITION_EVERY_3_ATTACKS_STACKS_UNTIL_BATTLE_END",v(1,2,3,4,5),"WhiteoutData Lv1 typo excluded / Whiteout Survival Wiki / Heaven Guardian agree 1-5; cross-checked 2026-09-09"),
        new Row("Lynn","Aira's Elegy","ATTACK_UP","LYNN","EXCLUSIVE_EXPLORATION_AFTER_HYMN_UNTIL_BATTLE_END",v(7,9,11,13,15),"WhiteoutData exact ladder / H5Joy and community sources max 15; cross-checked 2026-09-09"),
        new Row("Lynn","Iranon's Determination","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder / H5Joy and community sources max 15; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen4(){}
}
