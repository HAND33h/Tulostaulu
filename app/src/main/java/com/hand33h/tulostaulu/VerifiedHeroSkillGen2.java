package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public-source cross-checked Gen2 hero data only. */
public final class VerifiedHeroSkillGen2 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Flint","Fires of Vengeance Damage","DAMAGE_PER_TICK","TARGET","EXPLORATION_EVERY_0_5S",v(60,66,72,78,84),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Flint","Fires of Vengeance Vulnerability","DAMAGE_TAKEN_UP","TARGET","EXPLORATION_FOR_2S",v(10,15,20,25,30),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Flint","Incinerator","HEAL_MAX_HEALTH_PERCENT","FLINT","EXPLORATION_ONCE_WHEN_HEALTH_BELOW_50_PERCENT",v(20,25,30,35,40),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Flint","Heat Diffusion","ATTACK_SPEED_UP","FRIENDLY_HEROES","EXPLORATION_PASSIVE",v(3,4,5,6,7),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Flint","Pyromaniac","DAMAGE_DEALT_UP","INFANTRY","EXPEDITION_PASSIVE",v(20,40,60,80,100),"September 2026 balance update / current public data; updated 2026-09-10"),
        new Row("Flint","Burning Resolve","ATTACK_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Flint","Immolation","LETHALITY_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"September 2026 balance update / current public data; updated 2026-09-10"),
        new Row("Flint","Vengeful Task","ATTACK_UP","FLINT","EXCLUSIVE_EXPLORATION_AFTER_INCINERATOR_UNTIL_BATTLE_END",v(8,12,16,20,24),"WhiteoutData exact ladder / H5Joy max 24; cross-checked 2026-09-09"),
        new Row("Flint","Dragonbreath","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder / H5Joy max 15; cross-checked 2026-09-09"),
        new Row("Philly","First Aid","HEAL_FROM_ATTACK","ALL_FRIENDLY_HEROES","EXPLORATION_IMMEDIATE_HEAL",v(200,220,240,260,280),"Whiteout Survival Wiki / WhiteoutData; cross-checked 2026-09-10"),
        new Row("Philly","Restorative Hands","HEAL_FROM_ATTACK","WEAKEST_FRIENDLY_HERO","EXPLORATION_IMMEDIATE_HEAL",v(100,110,120,130,140),"Whiteout Survival Wiki / WhiteoutData; cross-checked 2026-09-10"),
        new Row("Philly","Paralytic Lotion","DAMAGE_DEALT","ENEMY_TARGET","EXPLORATION_DISABLE_FOR_1S",v(140,154,168,182,196),"Whiteout Survival Wiki / WhiteoutData; cross-checked 2026-09-10"),
        new Row("Philly","Vigor Tactics Attack","ATTACK_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(3,6,9,12,15),"Current Whiteout Survival Wiki / WhiteoutData / Heaven Guardian agree; Miracle Herb was replaced by Vigor Tactics in the December 2023 official skill optimization; verified 2026-09-10"),
        new Row("Philly","Vigor Tactics Defense","DEFENSE_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(2,4,6,8,10),"Current Whiteout Survival Wiki / WhiteoutData / Heaven Guardian agree; Miracle Herb was replaced by Vigor Tactics in the December 2023 official skill optimization; verified 2026-09-10"),
        new Row("Philly","Dosage Boost","EXTRA_ATTACK_DAMAGE","ALL_TROOPS","EXPEDITION_25_PERCENT_CHANCE_ON_ATTACK",v(120,140,160,180,200),"Current public data; cross-checked 2026-09-10"),
        new Row("Philly","Energizing Shot","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EXPEDITION_40_PERCENT_CHANCE",v(10,20,30,40,50),"September 2026 balance update replaces Numbing Spores stun; updated 2026-09-10"),
        new Row("Philly","Extraction","HEALING_EFFECT_UP","PHILLY_HEALS","EXCLUSIVE_EXPLORATION",v(30,40,50,60,70),"WhiteoutData exact ladder / H5Joy max 70; cross-checked 2026-09-09"),
        new Row("Philly","First Aid Training","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder / H5Joy max 15; cross-checked 2026-09-09"),
        new Row("Alonso","Trapnet","DAMAGE_DEALT","TARGET_AREA_OF_EFFECT","EXPLORATION_IMMOBILIZE_FOR_1_5S",v(200,220,240,260,280),"Whiteout Survival Wiki / Community Wiki; cross-checked 2026-09-10"),
        new Row("Alonso","Tidal Force","DAMAGE_DEALT","TARGET_AREA_OF_EFFECT","EXPLORATION_ACTIVE",v(50,55,60,65,70),"Whiteout Survival Wiki / Community Wiki; cross-checked 2026-09-10"),
        new Row("Alonso","Harpoon Blast Attacks Required","ATTACKS_REQUIRED","ALONSO","EXPLORATION_STUN_TRIGGER",v(8,7,7,6,5),"Whiteout Survival Wiki / Community Wiki; cross-checked 2026-09-10"),
        new Row("Alonso","Harpoon Blast Stun Time","STUN_DURATION_SECONDS","TARGET","EXPLORATION_AFTER_REQUIRED_STRIKES",v(0.2,0.2,0.4,0.4,0.5),"Whiteout Survival Wiki / Community Wiki; cross-checked 2026-09-10"),
        new Row("Alonso","Onslaught","LETHALITY_UP","ALL_TROOPS","EXPEDITION_40_PERCENT_CHANCE_ON_ATTACK",v(10,20,30,40,50),"September 2026 balance update replaces old stun version; current Whiteout Survival Wiki and WOS Forge agree; updated 2026-09-10"),
        new Row("Alonso","Iron Strength","ENEMY_DAMAGE_DEALT_DOWN","ALL_ENEMY_TROOPS","EXPEDITION_20_PERCENT_CHANCE_ON_ATTACK_FOR_2_TURNS",v(10,20,30,40,50),"Whiteout Survival Wiki / current public data; cross-checked 2026-09-10"),
        new Row("Alonso","Poison Harpoon","EXTRA_DAMAGE_DEALT","ALL_TROOPS","EXPEDITION_50_PERCENT_CHANCE_ON_ATTACK",v(10,20,30,40,50),"Whiteout Survival Wiki / current public data; cross-checked 2026-09-10"),
        new Row("Alonso","Harpoon Enhancement","RALLY_TROOPS_LETHALITY_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder / H5Joy max 15; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen2(){}
}
