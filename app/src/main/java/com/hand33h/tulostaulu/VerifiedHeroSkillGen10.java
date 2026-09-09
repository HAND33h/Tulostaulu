package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class VerifiedHeroSkillGen10 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Gregory","Legion of the Sun Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"WOS Heroes / whiteout-survival.com; cross-checked 2026-09-09"),
        new Row("Gregory","Legion of the Sun Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),"WOS Heroes / whiteout-survival.com; cross-checked 2026-09-09"),
        new Row("Gregory","Charged Assault","CRIT_CHANCE_UP","ALL_TROOPS_NORMAL_ATTACKS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / whiteout-survival.com; cross-checked 2026-09-09"),
        new Row("Gregory","Unbroken","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(4,8,12,16,20),"WOS Heroes / whiteout-survival.com; cross-checked 2026-09-09"),
        new Row("Gregory","Day of the Guard","LETHALITY_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION_WIDGET",v(5,7.5,10,12.5,15),"WOS Heroes / whiteoutdata.com; cross-checked 2026-09-09"),
        new Row("Gregory","Sacrificial Will","ATTACK_UP","ALL_FRIENDLY_TROOPS","ON_GREGORY_DEFEAT_FOR_5_SECONDS",v(8,12,16,20,24),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Freya","Chain Sunder Main","DAMAGE_DEALT","TARGET_AND_SURROUNDING_ENEMIES","EXPLORATION_ACTIVE",v(200,220,240,260,280),"WOS Forge current / WOS Heroes / Whiteout Survival Wiki / Heaven Guardian 2026-08-18; cross-checked 2026-09-10"),
        new Row("Freya","Chain Sunder Mark Splash","DAMAGE_SPLASH_PERCENT","SURROUNDING_ENEMIES","EXPLORATION_MARK_TRIGGER_COOLDOWN_0_5S",v(40,50,60,70,80),"WOS Forge current / WOS Heroes / Whiteout Survival Wiki / Heaven Guardian 2026-08-18; cross-checked 2026-09-10"),
        new Row("Freya","Prickled Bind Damage","DAMAGE_DEALT","TARGET","EXPLORATION_ACTIVE_IMMOBILIZE_1_5S",v(100,110,120,130,140),"WOS Forge current / WOS Heroes / Whiteout Survival Wiki / Heaven Guardian 2026-08-18; cross-checked 2026-09-10"),
        new Row("Freya","Crystal Fury Required Attacks","ATTACKS_REQUIRED","FREYA","EXPLORATION_PERIODIC",v(8,7,6,5,4),"WOS Forge current / WOS Heroes / Whiteout Survival Wiki / WOS Tools / Heaven Guardian 2026-08-18 agree on 8/7/6/5/4; h5joy has conflicting Lv2=6 and is not used; cross-checked 2026-09-10"),
        new Row("Freya","Crystal Fury Crit Rate","CRIT_RATE_UP","FREYA","EXPLORATION_PERIODIC",v(60,70,80,90,100),"WOS Forge current / WOS Heroes / Whiteout Survival Wiki / WOS Tools / Heaven Guardian 2026-08-18; cross-checked 2026-09-10"),
        new Row("Freya","Fog of War","TROOP_ATTACK_DOWN","ALL_ENEMY_TROOPS","PASSIVE",v(4,8,12,16,20),"WOS Heroes / whiteoutdata.com / whiteoutsurvival.wiki; cross-checked 2026-09-09"),
        new Row("Freya","Blood Moon Scythe","REAP_DAMAGE","LANCER_NORMAL_ATTACKS","50_PERCENT_CHANCE",v(20,40,60,80,100),"WOS Heroes / whiteoutdata.com / whiteoutsurvival.wiki; cross-checked 2026-09-09"),
        new Row("Freya","Night's Vengeance Damage Taken","DAMAGE_TAKEN_DOWN","INFANTRY_AND_MARKSMEN","PASSIVE",v(3,6,9,12,15),"WOS Heroes / whiteoutdata.com / whiteoutsurvival.wiki; cross-checked 2026-09-09"),
        new Row("Freya","Night's Vengeance Damage Dealt","DAMAGE_DEALT_UP","INFANTRY_AND_MARKSMEN","PASSIVE",v(3,6,9,12,15),"WOS Heroes / whiteoutdata.com / whiteoutsurvival.wiki; cross-checked 2026-09-09"),
        new Row("Freya","Defender of the Watch","DEFENSE_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData / WOS Battlemaster; cross-checked 2026-09-09"),
        new Row("Freya","Night Raid Damage","DAMAGE_DEALT_UP","FREYA","EXCLUSIVE_EXPLORATION",v(10,15,20,25,30),"WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Freya","Night Raid Summonee Damage","DAMAGE_DEALT","ENEMY_SUMMONEES","EXCLUSIVE_EXPLORATION_ON_SUMMON",v(100,150,200,250,300),"WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Blanchette","Armed to the Teeth","LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Blanchette","Blood Hunter","DAMAGE_UP","MARKSMEN_EVERY_3_ROUNDS","PASSIVE",v(15,30,45,60,75),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Blanchette","Crimson Sniper vs Lancers","EXTRA_DAMAGE_UP","ENEMY_LANCERS","MARKSMEN_EVERY_2_STRIKES",v(8,16,24,32,40),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Blanchette","Crimson Sniper vs Marksmen","EXTRA_DAMAGE_UP","ENEMY_MARKSMEN","MARKSMEN_EVERY_2_STRIKES",v(4,8,12,16,20),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Blanchette","Lightning Strike","RALLY_TROOPS_LETHALITY_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Blanchette","Hunter's Rage","ATTACK_SPEED_UP","BLANCHETTE","EXCLUSIVE_EXPLORATION_HEAL_BLOCK_PLUS_5S",v(10,15,20,25,30),"WOS Heroes / Whiteout Survival Wiki; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen10(){}
}
