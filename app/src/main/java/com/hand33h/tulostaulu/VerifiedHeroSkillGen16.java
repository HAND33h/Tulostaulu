package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Cross-validated Gen16 Expedition data. Values are Lv1..Lv5. */
public final class VerifiedHeroSkillGen16 {
    public static final class Row {
        public final String hero,skill,effect,target,trigger,source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double...x){return x;}
    public static final List<Row> DATA=Collections.unmodifiableList(Arrays.asList(
        new Row("Seigel","Armor of Night","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Seigel","Night's Defense Infantry","TROOP_ATTACK_DOWN","INFANTRY","PASSIVE_TRADEOFF",v(4,8,12,16,20),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Seigel","Night's Defense Enemy Lancer Marksman","ENEMY_ATTACK_DOWN","ENEMY_LANCER_MARKSMAN","PASSIVE_TRADEOFF",v(7,14,21,28,35),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Seigel","Vanguard of Eternity Normal","NORMAL_ATTACK_DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Seigel","Vanguard of Eternity Skill","SKILL_DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(6,12,18,24,30),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Seigel","Hell's Vow","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Ursar","Forest Spores","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData / WSCO; cross-checked 2026-09-09"),
        new Row("Ursar","Horn of the Ancients Lethality","LETHALITY_UP","LANCER_MARKSMAN","2_TURNS",v(6,12,18,24,30),"WOS Heroes / WhiteoutData / WSCO; cross-checked 2026-09-09"),
        new Row("Ursar","Horn of the Ancients Defense","ENEMY_DEFENSE_DOWN","ENEMY_ALL","1_TURN",v(6,12,18,24,30),"WOS Heroes / WhiteoutData / WSCO; cross-checked 2026-09-09"),
        new Row("Ursar","Poison Tips Damage","EXTRA_DAMAGE","LANCER","EVERY_2_ATTACKS",v(20,40,60,80,100),"WOS Heroes / WhiteoutData / WSCO; cross-checked 2026-09-09"),
        new Row("Ursar","Poison Tips Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","1_TURN",v(5,10,15,20,25),"WOS Heroes / WhiteoutData / WSCO; cross-checked 2026-09-09"),
        new Row("Ursar","Typhoon Drums","RALLY_ATTACK_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / H5Joy / Whiteout Battlemaster; cross-checked 2026-09-09"),
        new Row("Aisling","Songs of the Ancestors","DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),"Whiteout Survival Wiki / WhiteoutData / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Aisling","Rock Storm Damage","EXTRA_DAMAGE","MARKSMAN","EVERY_3_TURNS",v(30,60,90,120,150),"Whiteout Survival Wiki / WhiteoutData / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Aisling","Rock Storm Debuff","ENEMY_DAMAGE_DEALT_DOWN","ENEMY_ALL","EVERY_3_TURNS_1_TURN",v(6,12,18,24,30),"Whiteout Survival Wiki / WhiteoutData / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Aisling","Forest Fury","EXTRA_DAMAGE","MARKSMAN","EVERY_3_TURNS",v(8,16,24,32,40),"Whiteout Survival Wiki / WhiteoutData / Heaven Guardian; cross-checked 2026-09-09"),
        new Row("Aisling","Forest Guardian","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / Whiteout Survival Wiki / H5Joy; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen16(){}
}
