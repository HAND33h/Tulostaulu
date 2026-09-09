package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verified Gen10 Blanchette Expedition data.
 * Provenance: WOS Heroes and whiteout-survival.com, cross-checked 2026-09-09.
 * Only values that agree across both public sources are included.
 */
public final class VerifiedHeroSkillGen10Blanchette {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }

    private static double[] v(double... x){return x;}

    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Blanchette","Armed to the Teeth","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / whiteout-survival.com; cross-checked 2026-09-09"),
        new Row("Blanchette","Blood Hunter","EXTRA_DAMAGE","MARKSMEN_EVERY_3_ROUNDS","PASSIVE",v(15,30,45,60,75),"WOS Heroes / whiteout-survival.com; cross-checked 2026-09-09"),
        new Row("Blanchette","Crimson Sniper vs Lancers","EXTRA_DAMAGE","MARKSMEN_VS_LANCERS_EVERY_2_STRIKES","PASSIVE",v(8,16,24,32,40),"WOS Heroes / whiteout-survival.com; cross-checked 2026-09-09"),
        new Row("Blanchette","Crimson Sniper vs Marksmen","EXTRA_DAMAGE","MARKSMEN_VS_MARKSMEN_EVERY_2_STRIKES","PASSIVE",v(4,8,12,16,20),"WOS Heroes / whiteout-survival.com; cross-checked 2026-09-09")
    ));

    private VerifiedHeroSkillGen10Blanchette(){}
}
