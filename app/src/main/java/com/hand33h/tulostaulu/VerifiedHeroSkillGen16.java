package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Cross-validated Gen16 Expedition widget data. Values are Lv1..Lv5. */
public final class VerifiedHeroSkillGen16 {
    public static final class Row {
        public final String hero,skill,effect,target,trigger,source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double...x){return x;}
    public static final List<Row> DATA=Collections.unmodifiableList(Arrays.asList(
        new Row("Ursar","Typhoon Drums","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes; roster cross-check wos-wiki.de; cross-checked 2026-09-09"),
        new Row("Aisling","Forest Guardian","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes; roster cross-check wos-wiki.de; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen16(){}
}
