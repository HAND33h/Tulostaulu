package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public-source cross-checked Gen6 hero data only. */
public final class VerifiedHeroSkillGen6 {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Renee","Illusion Cloud","DAMAGE_DEALT","AREA_OF_EFFECT","EXPLORATION_CONFUSES_FOR_1S",v(100,110,120,130,140),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Renee","Starpaint Damage","DAMAGE_DEALT","TARGET","EXPLORATION_APPLIES_STAR_MARK_FOR_4S",v(50,55,60,65,70),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Renee","Starpaint Vulnerability","DAMAGE_TAKEN_UP","STAR_MARKED_TARGET","EXPLORATION_FOR_4S",v(2,3,4,5,6),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Renee","Dream Vision Attack","ATTACK_UP","RENEE","EXPLORATION_PASSIVE_VS_STAR_MARKED_TARGETS",v(8,12,16,20,24),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Renee","Dream Vision Marked Damage","DAMAGE_DEALT_UP","STAR_MARKED_TARGET","EXPLORATION_PASSIVE",v(4,6,8,10,12),"WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Renee","Nightmare Trace","EXTRA_LANCER_DAMAGE_UP","DREAM_MARKED_TARGET","EXPEDITION_EVERY_2_TURNS_NEXT_TURN_MARK_LASTS_1_TURN",v(40,80,120,160,200),"WhiteoutData / Whiteout Survival Community Wiki / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Renee","Dreamcatcher","DAMAGE_DEALT_UP","LANCERS_VS_DREAM_MARKED_TARGET","EXPEDITION_PASSIVE",v(30,60,90,120,150),"WhiteoutData / Whiteout Survival Community Wiki / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Renee","Dreamslice","DAMAGE_DEALT_UP","ALL_TROOPS_VS_DREAM_MARKED_TARGET","EXPEDITION_PASSIVE",v(15,30,45,60,75),"WhiteoutData / Whiteout Survival Community Wiki / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Renee","Dream Illusion","CONFUSION_CHANCE","TARGET","EXCLUSIVE_EXPLORATION_CONFUSES_FOR_1S",v(2,3.5,5,6.5,8),"WOS Heroes / whiteout-survival.com / H5Joy; cross-checked 2026-09-09"),
        new Row("Renee","Wistful Enchantment","RALLY_TROOPS_LETHALITY_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / whiteout-survival.com / H5Joy; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen6(){}
}
