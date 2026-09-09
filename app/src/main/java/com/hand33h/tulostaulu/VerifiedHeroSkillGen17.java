package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Cross-validated Gen17 Expedition data. Values are Lv1..Lv5. */
public final class VerifiedHeroSkillGen17 {
    public static final class Row {
        public final String hero,skill,effect,target,trigger,source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double...x){return x;}
    public static final List<Row> DATA=Collections.unmodifiableList(Arrays.asList(
        new Row("Aiden","Starfire Wall","TROOP_DEFENSE_UP","ALL_TROOPS","INFANTRY_40_PERCENT_PROC_1_TURN",v(10,20,30,40,50),"WOS Heroes / wos-wiki.de; cross-checked 2026-09-09"),
        new Row("Aiden","Scarlet Brigade Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"WOS Heroes / wos-wiki.de; cross-checked 2026-09-09"),
        new Row("Aiden","Scarlet Brigade Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),"WOS Heroes / wos-wiki.de; cross-checked 2026-09-09"),
        new Row("Aiden","Rush","TROOP_ATTACK_UP","ALL_TROOPS","EVERY_2_TURNS_1_TURN",v(10,20,30,40,50),"WOS Heroes / wos-wiki.de; cross-checked 2026-09-09"),
        new Row("Aiden","Guardians of Iron","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / Whiteout Survival Wiki; full ladder cross-checked 2026-09-09"),
        new Row("Bertha","Dissection","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / wos-wiki.de / WSCO; cross-checked 2026-09-09"),
        new Row("Bertha","Vengeance Damage","DAMAGE_DEALT_UP","LANCER","EVERY_2_ATTACKS",v(25,50,75,100,125),"WOS Heroes / wos-wiki.de / WSCO; cross-checked 2026-09-09"),
        new Row("Bertha","Vengeance Marksman Attack","ATTACK_UP","MARKSMAN","EVERY_2_LANCER_ATTACKS_1_TURN",v(6,12,18,24,30),"WOS Heroes / wos-wiki.de / WSCO; cross-checked 2026-09-09"),
        new Row("Bertha","Lethal Precision Damage","DAMAGE_DEALT_UP","LANCER","EACH_ATTACK",v(12,24,36,48,60),"WOS Heroes / wos-wiki.de / WSCO; cross-checked 2026-09-09"),
        new Row("Bertha","Lethal Precision Weakness","ENEMY_DAMAGE_TAKEN_UP","TARGET","1_TURN",v(5,10,15,20,25),"WOS Heroes / wos-wiki.de / WSCO; cross-checked 2026-09-09"),
        new Row("Bertha","Antibodies","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki; full ladder cross-checked 2026-09-09"),
        new Row("Eleanor","Theoretical Advantage","ENEMY_DEFENSE_DOWN","ENEMY_ALL","PASSIVE_PRIMARY",v(5,10,15,20,25),"WOS Heroes / Whiteout Survival Wiki / wos-wiki.de; cross-checked 2026-09-09"),
        new Row("Eleanor","Pierce Caliber Extra Damage","EXTRA_DAMAGE","MARKSMAN","ON_ATTACK_50_PERCENT",v(20,40,60,80,100),"WOS Heroes / Whiteout Survival Wiki / wos-wiki.de; cross-checked 2026-09-09"),
        new Row("Eleanor","Pierce Caliber Damage","DAMAGE_DEALT_UP","MARKSMAN","ON_ATTACK_50_PERCENT_1_TURN",v(8,16,24,32,40),"WOS Heroes / Whiteout Survival Wiki / wos-wiki.de; cross-checked 2026-09-09"),
        new Row("Eleanor","Trajectoids","DAMAGE_DEALT_UP","MARKSMAN_VS_MARKSMAN","EVERY_2_ATTACKS",v(12,24,36,48,60),"WOS Heroes / Whiteout Survival Wiki / wos-wiki.de; cross-checked 2026-09-09"),
        new Row("Eleanor","Doom Patrol","RALLY_LETHALITY_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / Heaven Guardian / wos-wiki.de; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen17(){}
}
