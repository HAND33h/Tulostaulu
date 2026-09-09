package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Cross-validated Gen15 Expedition data. Values are Lv1..Lv5. */
public final class VerifiedHeroSkillGen15 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final int expeditionOrder;
        public final double[] values;
        Row(String h,String s,int o,String e,String t,String tr,double[] v,String src){hero=h;skill=s;expeditionOrder=o;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA=Collections.unmodifiableList(Arrays.asList(
        new Row("Hank","Wall of Despair",0,"DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / H5Joy; cross-checked 2026-09-09"),
        new Row("Estrella","Homeland Defense",0,"DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / H5Joy / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Viveca","Song of Dawn",0,"RALLY_LETHALITY_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / Whiteout Survival Wiki / WoS Tools; cross-checked 2026-09-09"),
        new Row("Hank","Roaring Rage",1,"TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WoS Tools / Whiteout Survival Wiki; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen15(){}
}
