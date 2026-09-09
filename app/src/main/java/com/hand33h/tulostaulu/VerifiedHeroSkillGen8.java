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
        new Row("Sonya","Torrential Impact","DAMAGE_DEALT","LANCERS","EVERY_5_TURNS",v(50,100,150,200,250),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09; stun duration is 1 turn in both sources"),
        new Row("Sonya","Vortex Turret","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Sonya","Chilled to the Bone Attack","ATTACK_UP","SONYA","EXCLUSIVE_EXPLORATION",v(8,12,16,20,24),"Whiteout Survival / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Sonya","Chilled to the Bone Shatter Damage","DAMAGE_DEALT","NEARBY_ENEMIES_AFTER_EXTREME_COLD_FREEZE_ENDS","EXCLUSIVE_EXPLORATION",v(50,55,60,65,70),"Whiteout Survival / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gatot","Golden Guard","DEFENSE_UP","INFANTRY","PASSIVE",v(6,12,18,24,30),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gatot","King's Bestowal","SHIELD_VALUE_UP","INFANTRY","EACH_ATTACK_FOR_1_TURN",v(6,12,18,24,30),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gatot","Royal Legion","ENEMY_ATTACK_DOWN","ALL_ENEMY_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gatot","Indestructible City","DEFENSE_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gatot","King's Punishment Shield","SHIELD_VALUE_UP","KING'S_RESOLVE","EXCLUSIVE_EXPLORATION",v(55,65,75,85,95),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Gatot","King's Punishment Counterattack","COUNTERATTACK_UP","ENEMY_WHILE_SHIELD_ACTIVE","EXCLUSIVE_EXPLORATION",v(10,15,20,25,30),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Hendrik","Worm's Ravage","ENEMY_DEFENSE_DOWN","ALL_ENEMY_TROOPS","PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Hendrik","Armor of Barnacles","DEFENSE_UP","ALL_TROOPS","EVERY_4_TURNS_FOR_2_TURNS",v(6,12,18,24,30),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Hendrik","Dagon's Heir","DAMAGE_DEALT","ALL_ENEMIES","EVERY_3_TURNS_WITH_MARKSMEN",v(8,16,24,32,40),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Hendrik","Abyssal Blessing","TROOP_ATTACK_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / WhiteoutData; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen8(){}
}
