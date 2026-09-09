package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public-source cross-checked Gen7 hero data only. */
public final class VerifiedHeroSkillGen7 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Edith","Ironclad Punch Damage","DAMAGE_DEALT","ENEMIES_IN_FAN_AHEAD","EXPLORATION_STUN_1S",v(100,110,120,130,140),"WOS Heroes / WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Edith","Ironclad Punch Attack","ATTACK_UP","MR_TIN","EXPLORATION_FOR_2S",v(20,40,60,80,100),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Edith","Escape Capsule","DAMAGE_DEALT","NEARBY_ENEMIES","EXPLORATION_AT_0_HEALTH",v(200,220,240,260,280),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Edith","Preemptive Alerts","PROC_CHANCE","MR_TIN","EXPLORATION_REDUCES_DAMAGE_TAKEN_BY_50_PERCENT",v(10,20,30,40,50),"WhiteoutData / H5Joy; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen7(){}
}
