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
        new Row("Ahmose","Oath of Guardian","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION_CITY_DEFENSE",v(5,7.5,10,12.5,15),"WhiteoutData exact ladder / H5Joy and Whiteout Survival Wiki max 15 / Community Wiki widget progression corroborates 5-15 ladder; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen4(){}
}
