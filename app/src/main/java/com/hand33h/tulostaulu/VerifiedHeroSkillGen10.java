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
        new Row("Gregory","Unbroken","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(4,8,12,16,20),"WOS Heroes / whiteout-survival.com; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen10(){}
}
