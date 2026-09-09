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
        new Row("Renee","Wistful Enchantment","RALLY_TROOPS_LETHALITY_UP","RALLY_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes / whiteout-survival.com / H5Joy; cross-checked 2026-09-09"),
        new Row("Wu Ming","Cyclone Barrier","DAMAGE_DEALT","AREA_OF_EFFECT","EXPLORATION_INVULNERABLE_FOR_2S",v(100,110,120,130,140),"WOS Heroes / WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Wu Ming","Inner Clarity Attack","ATTACK_UP","WU_MING","EXPLORATION_FOR_4S",v(8,12,16,20,24),"WOS Heroes / WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Wu Ming","Inner Clarity Defense","DEFENSE_UP","WU_MING","EXPLORATION_FOR_4S",v(16,24,32,40,48),"WOS Heroes / WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Wu Ming","Remote Impact","DAMAGE_DEALT","RANDOM_ENEMY","EXPLORATION_EVERY_NORMAL_ATTACK",v(20,22,24,26,28),"WOS Heroes / WhiteoutData / H5Joy; cross-checked 2026-09-09"),
        new Row("Wu Ming","Shadow's Evasion Normal Attack","DAMAGE_TAKEN_DOWN","INFANTRY","EXPEDITION_NORMAL_ATTACKS",v(5,10,15,20,25),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Wu Ming","Shadow's Evasion Skill","SKILL_DAMAGE_TAKEN_DOWN","INFANTRY","EXPEDITION_SKILLS",v(6,12,18,24,30),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Wu Ming","Crescent Uplift","DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(4,8,12,16,20),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Wu Ming","Elemental Resonance","SKILL_DAMAGE_DEALT_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki; cross-checked 2026-09-09"),
        new Row("Wu Ming","Martial Zenith","DAMAGE_DEALT_UP","WU_MING","EXCLUSIVE_EXPLORATION",v(10,15,20,25,30),"WOS Heroes exact ladder / H5Joy max 30 / WoSTools max 30; cross-checked 2026-09-09"),
        new Row("Wu Ming","Steel Discipline","DEFENDER_DEFENSE_UP","DEFENDER_TROOPS","EXCLUSIVE_EXPEDITION",v(5,7.5,10,12.5,15),"WOS Heroes exact ladder / H5Joy max 15 / WoSTools max 15; cross-checked 2026-09-09"),
        new Row("Wayne","Hurricane Blowback","DAMAGE_DEALT","STRAIGHT_LINE_AREA_OF_EFFECT","EXPLORATION_OUTBOUND_AND_RETURN_SAME_DAMAGE",v(100,110,120,130,140),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki / H5Joy; cross-checked 2026-09-09"),
        new Row("Wayne","Phantom Blitz","EXTRA_NORMAL_ATTACK_CHANCE","WAYNE","EXPLORATION_EACH_NORMAL_ATTACK",v(15,20,25,30,35),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki / H5Joy; cross-checked 2026-09-09"),
        new Row("Wayne","Noon Time","CRIT_RATE_UP","WAYNE","EXPLORATION_ON_DEALING_DAMAGE",v(3,6,9,12,15),"WOS Heroes / WhiteoutData / Whiteout Survival Wiki / H5Joy; cross-checked 2026-09-09"),
        new Row("Wayne","Thunder Strike","EXTRA_ATTACK_DAMAGE","FRIENDLY_TROOPS","EXPEDITION_EVERY_4_TURNS",v(20,40,60,80,100),"Whiteout Survival Community Wiki / WSCO / WhiteoutData; cross-checked 2026-09-09"),
        new Row("Wayne","Roundabout Hit vs Lancers","EXTRA_DAMAGE_DEALT","MARKSMEN_VS_ENEMY_LANCERS","EXPEDITION_EVERY_OTHER_ATTACK",v(8,16,24,32,40),"Whiteout Survival Wiki / Whiteout Survival Community Wiki / WSCO; cross-checked 2026-09-09"),
        new Row("Wayne","Roundabout Hit vs Marksmen","EXTRA_DAMAGE_DEALT","MARKSMEN_VS_ENEMY_MARKSMEN","EXPEDITION_EVERY_OTHER_ATTACK",v(4,8,12,16,20),"Whiteout Survival Wiki / Whiteout Survival Community Wiki / WSCO; cross-checked 2026-09-09"),
        new Row("Wayne","Fleet","CRIT_RATE_UP","ALL_TROOPS","EXPEDITION_PASSIVE",v(5,10,15,20,25),"Whiteout Survival Wiki / Whiteout Survival Community Wiki / WSCO; cross-checked 2026-09-09")
    ));
    private VerifiedHeroSkillGen6(){}
}
