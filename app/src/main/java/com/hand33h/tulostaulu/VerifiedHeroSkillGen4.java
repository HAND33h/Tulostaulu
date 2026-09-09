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
        new Row("Ahmose","Ancestral Blessing","HEAL_FROM_ATTACK","AHMOSE","EXPLORATION_AFTER_CTHUGHAS_PROTECTION_OVER_5S",v(30,33,36,39,42),"WhiteoutData / H5Joy / Whiteout Survival Wiki; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen4(){}
}
