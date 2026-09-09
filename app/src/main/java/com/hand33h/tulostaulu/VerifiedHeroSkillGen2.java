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
        new Row("Flint","Pyromaniac","DAMAGE_DEALT_UP","INFANTRY","EXPEDITION_PASSIVE",v(20,40,60,80,100),"WhiteoutData / Whiteout Battlemaster max confirmation; cross-checked 2026-09-09"),
        new Row("Flint","Burning Resolve","ATTACK_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WhiteoutData; cross-checked 2026-09-09"),
        new Row("Flint","Immolation","LETHALITY_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WhiteoutData; cross-checked 2026-09-09"),
        new Row("Flint","Vengeful Task","ATTACK_UP","FLINT","EXCLUSIVE_EXPLORATION_AFTER_INCINERATOR_UNTIL_BATTLE_END",v(8,12,16,20,24),"WhiteoutData exact ladder / H5Joy max 24; cross-checked 2026-09-09"),
        new Row("Flint","Dragonbreath","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder has mislabeled preview 'Defense Up' while description says Defender Attack; H5Joy confirms Attack max 15; cross-checked 2026-09-09"),
        new Row("Philly","First Aid","HEAL_FROM_ATTACK","ALL_FRIENDLY_HEROES","EXPLORATION_IMMEDIATE_HEAL",v(200,220,240,260,280),"WhiteoutData / H5Joy; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen2(){}
}
