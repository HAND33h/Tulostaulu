package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public-source cross-checked Gen5 hero data only. */
public final class VerifiedHeroSkillGen5 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Hector","Sword Whirlwind","ATTACK_SPEED_UP","HECTOR","EXPLORATION_FOR_4S_CONTROL_IMMUNITY",v(80,90,100,110,120),"WhiteoutData / Whiteout Survival Wiki / Whiteout Survival Community Wiki; cross-checked 2026-09-09"),
        new Row("Hector","Desperado","DAMAGE_TAKEN_DOWN","HECTOR","EXPLORATION_WHILE_BELOW_50_PERCENT_HEALTH",v(20,30,40,50,60),"WhiteoutData / Whiteout Survival Wiki / Whiteout Survival Community Wiki; cross-checked 2026-09-09"),
        new Row("Hector","Adrenaline Surge","ATTACK_UP","HECTOR","EXPLORATION_WHILE_BELOW_50_PERCENT_HEALTH",v(16,24,32,40,48),"WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen5(){}
}
