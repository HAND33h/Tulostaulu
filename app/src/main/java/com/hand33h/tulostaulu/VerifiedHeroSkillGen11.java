package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Cross-validated Gen11 Expedition data. Values are Lv1..Lv5. */
public final class VerifiedHeroSkillGen11 {
    public static final class Row {
        public final String hero,skill,effect,target,trigger,source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double...x){return x;}
    public static final List<Row> DATA=Collections.unmodifiableList(Arrays.asList(
        new Row("Rufus","Inferno Regiment","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Rufus","Armor Crush Damage","EXTRA_DAMAGE","MARKSMAN","EACH_ATTACK",v(12,24,36,48,60),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Rufus","Armor Crush Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","1_TURN",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Rufus","Wrathful Quake","ENEMY_LETHALITY_DOWN","TARGET","ON_ATTACK_20_PERCENT_2_TURNS",v(10,20,30,40,50),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09"),
        new Row("Rufus","Blazing Legion","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; full ladder cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen11(){}
}
