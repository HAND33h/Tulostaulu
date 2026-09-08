package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Verified public Gen15 battle data. Source: WOS Heroes Generation 15 page. */
public final class Gen15BattleData {
    public static final String SOURCE_URL="https://wosheroes.com/heroes/generation-15-heroes/";
    public static final double[] EXPEDITION_STAR_ATK_DEF={270.48,293.51,316.54,339.57,362.60,404.05,436.30,468.54,500.78,533.02,565.26,623.28,668.36,713.54,758.72,803.80,848.97,930.22,993.43,1056.54,1119.75,1182.96,1246.17,1359.95,1448.34,1536.84,1625.33,1713.82,1802.32,1961.57};
    public static final int[] STAR_SHARD_COST={1,1,2,2,2,2,5,5,5,5,5,15,15,15,15,15,15,40,40,40,40,40,40,100,100,100,100,100,100,100};
    public static final int[] WIDGET_COST={5,10,15,20,25,30,35,40,45,50};
    public static final double[] WIDGET_EXP_LETHALITY_HP={69.40,138.80,208.20,277.60,347.00,416.40,485.80,555.20,624.60,694.00};

    public static final class Effect {
        public final String hero,skill,effect,target,trigger;
        public final double[] values;
        Effect(String h,String s,String e,String t,String tr,double...v){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;}
    }
    private static Effect e(String h,String s,String e,String t,String tr,double...v){return new Effect(h,s,e,t,tr,v);}

    public static final List<Effect> EFFECTS=Collections.unmodifiableList(Arrays.asList(
        e("Hank","Roaring Rage","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE_PRIMARY",5,10,15,20,25),
        e("Hank","Flying Sparks","DAMAGE_DEALT_UP","ALL_TROOPS","EVERY_5_INFANTRY_ATTACKS_2_TURNS",5,10,15,20,25),
        e("Hank","Flying Sparks","DAMAGE_TAKEN_DOWN","ALL_TROOPS","EVERY_5_INFANTRY_ATTACKS_2_TURNS",5,10,15,20,25),
        e("Hank","Raging Force","ENEMY_INFANTRY_DAMAGE_TAKEN_UP","ENEMY_INFANTRY","EVERY_4_TURNS_2_TURNS",6,12,18,24,30),
        e("Hank","Raging Force","ENEMY_MARKSMAN_DAMAGE_DEALT_DOWN","ENEMY_MARKSMAN","EVERY_4_TURNS_2_TURNS",6,12,18,24,30),
        e("Hank","Wall of Despair","DEFENDER_HEALTH_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",5,7.5,10,12.5,15),

        e("Estrella","Corrosive Color","ENEMY_DEFENSE_DOWN","ENEMY_ALL","PASSIVE_PRIMARY",5,10,15,20,25),
        e("Estrella","Dawn Canvas","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",3,6,9,12,15),
        e("Estrella","Dawn Canvas","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",2,4,6,8,10),
        e("Estrella","Splendid Scene","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",5,10,15,20,25),
        e("Estrella","Splendid Scene","DAMAGE_DEALT_UP","LANCER","PASSIVE",5,10,15,20,25),
        e("Estrella","Homeland Defense","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",5,7.5,10,12.5,15),

        e("Viveca","Nightfall Legion","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE_PRIMARY",5,10,15,20,25),
        e("Viveca","Shadow World","EXTRA_DAMAGE","MARKSMAN_TO_ENEMY_ALL","ON_ATTACK_20_PERCENT",20,40,60,80,100),
        e("Viveca","Children of the Mist","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",2,4,6,8,10),
        e("Viveca","Children of the Mist","DAMAGE_DEALT_UP","MARKSMAN","PASSIVE",2,4,6,8,10),
        e("Viveca","Song of Dawn","RALLY_LETHALITY_UP","RALLY_TROOPS","WIDGET_EXPEDITION",5,7.5,10,12.5,15)
    ));

    public static double starBonus(int starStep){return starStep<1||starStep>EXPEDITION_STAR_ATK_DEF.length?0:EXPEDITION_STAR_ATK_DEF[starStep-1];}
    public static double widgetExpBonus(int level){return level<1||level>10?0:WIDGET_EXP_LETHALITY_HP[level-1];}
    private Gen15BattleData(){}
}
