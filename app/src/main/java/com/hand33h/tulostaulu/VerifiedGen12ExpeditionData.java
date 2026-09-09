package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Cross-checked Generation 12 Expedition skill ladders.
 * Source: WOS Heroes Generation 12, checked 2026-09-09.
 * Kept separate until the central PublicHeroSkillCatalog migration is complete.
 */
public final class VerifiedGen12ExpeditionData {
    public static final String SOURCE_URL = "https://wosheroes.com/heroes/generation-12-heroes/";

    public static final class Entry {
        public final String hero, skill, effect, target, trigger;
        public final int expeditionOrder;
        public final double[] values;
        Entry(String hero, int order, String skill, String effect, String target, String trigger, double... values) {
            this.hero = hero; this.expeditionOrder = order; this.skill = skill; this.effect = effect;
            this.target = target; this.trigger = trigger; this.values = values;
        }
    }

    private static Entry e(String h,int o,String s,String fx,String t,String tr,double...v){return new Entry(h,o,s,fx,t,tr,v);}

    public static final List<Entry> ALL = Collections.unmodifiableList(Arrays.asList(
        e("Hervor",1,"Call For Blood","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",5,10,15,20,25),
        e("Hervor",2,"Undying - Normal Attack","NORMAL_ATTACK_DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",5,10,15,20,25),
        e("Hervor",2,"Undying - Skill","SKILL_DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",6,12,18,24,30),
        e("Hervor",3,"Battlethirsty - Guard","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",3,6,9,12,15),
        e("Hervor",3,"Battlethirsty - Damage","DAMAGE_DEALT_UP","INFANTRY","PASSIVE",2,4,6,8,10),

        e("Karol",1,"In the Wings","DAMAGE_TAKEN_DOWN","ALL_TROOPS","PASSIVE",4,8,12,16,20),
        e("Karol",2,"Shieldbreaker - Lancer","DAMAGE_DEALT_UP","ALL_TROOPS_VS_LANCER","PASSIVE",6,12,18,24,30),
        e("Karol",2,"Shieldbreaker - Infantry","DAMAGE_DEALT_UP","ALL_TROOPS_VS_INFANTRY","PASSIVE",5,10,15,20,25),
        e("Karol",3,"Standard of Ages - Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",3,6,9,12,15),
        e("Karol",3,"Standard of Ages - Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",2,4,6,8,10),

        e("Ligeia",1,"Nerf Poison","ENEMY_DEFENSE_DOWN","ENEMY_ALL","PASSIVE",5,10,15,20,25),
        e("Ligeia",2,"Corrosion - Damage","EXTRA_DAMAGE","MARKSMAN","EVERY_2_ATTACKS",20,40,60,80,100),
        e("Ligeia",2,"Corrosion - Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","EVERY_2_ATTACKS_1_TURN",5,10,15,20,25),
        e("Ligeia",3,"Toxic Tip - Damage","DAMAGE_DEALT_UP","MARKSMAN","EVERY_2_ATTACKS",20,40,60,80,100),
        e("Ligeia",3,"Toxic Tip - Debuff","ENEMY_DAMAGE_DEALT_DOWN","TARGET","EVERY_2_ATTACKS_1_TURN",4,8,12,16,20)
    ));

    private VerifiedGen12ExpeditionData() {}
}
