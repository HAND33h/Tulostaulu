package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Public-source cross-checked Expedition data only.
 * Missing/unverified values are intentionally not inferred.
 */
public final class VerifiedHeroSkillGen8 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Sonya","Treasure Hunter","DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Sonya","Bounty Temptation Lancer Damage","DAMAGE_DEALT_UP","LANCERS","EVERY_2_ATTACKS",v(15,30,45,60,75),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Sonya","Bounty Temptation Troop Attack","TROOP_ATTACK_UP","ALL_TROOPS","EVERY_2_ATTACKS_FOR_1_TURN",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Sonya","Torrential Impact","DAMAGE_DEALT","LANCERS","EVERY_5_TURNS",v(50,100,150,200,250),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09; stun duration is 1 turn in both sources")
    ));
    private VerifiedHeroSkillGen8(){}
}
