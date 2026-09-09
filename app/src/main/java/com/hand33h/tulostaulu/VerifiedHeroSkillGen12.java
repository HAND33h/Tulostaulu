package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Cross-validated Gen12 Expedition data. Values are Lv1..Lv5. */
public final class VerifiedHeroSkillGen12 {
    public static final class Row {
        public final String hero,skill,effect,target,trigger,source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double...x){return x;}
    public static final List<Row> DATA=Collections.unmodifiableList(Arrays.asList(
        new Row("Hervor","Call For Blood","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WosGift / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Hervor","Undying Normal Attack","NORMAL_ATTACK_DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WosGift / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Hervor","Undying Skill","SKILL_DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(6,12,18,24,30),"WOS Heroes / WosGift / Whiteout Survival Wiki / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Hervor","Battlethirsty Damage Taken","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(3,6,9,12,15),"WOS Heroes / WosGift / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Hervor","Battlethirsty Damage Dealt","DAMAGE_DEALT_UP","INFANTRY","PASSIVE",v(2,4,6,8,10),"WOS Heroes / WosGift / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Hervor","Fort of Rock","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / whiteout-survival.com; full ladder cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen12(){}
}
