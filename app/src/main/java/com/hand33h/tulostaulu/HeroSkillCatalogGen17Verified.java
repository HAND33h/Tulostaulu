package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cross-checked Generation 17 Expedition skill ladders.
 * Public community data only; keep provenance visible and do not treat as an official combat formula.
 */
public final class HeroSkillCatalogGen17Verified {
    public static final class Entry {
        public final String hero, name, effect, target, trigger, sourceUrl, note;
        public final double[] values;
        public final int expeditionOrder;
        Entry(String hero,String name,String effect,String target,String trigger,double[] values,int order,String sourceUrl,String note){
            this.hero=hero;this.name=name;this.effect=effect;this.target=target;this.trigger=trigger;
            this.values=values;this.expeditionOrder=order;this.sourceUrl=sourceUrl;this.note=note;
        }
    }

    private static double[] v(double... x){ return x; }
    private static final List<Entry> DATA;
    static {
        ArrayList<Entry> d=new ArrayList<>();
        final String WOSH="https://wosheroes.com/heroes/generation-17-heroes/";
        final String BERTHA="https://www.whiteoutsurvival-community.com/guides/heroes/en/bertha.html";
        final String ELEANOR="https://wos-wiki.de/en/helden/eleanor/";

        // Aiden — WOS Heroes exact Lv1..Lv5 ladders.
        d.add(new Entry("Aiden","Starfire Wall","TROOP_DEFENSE_UP","INFANTRY","PROC_40_PERCENT_1_TURN",v(10,20,30,40,50),1,WOSH,"First Expedition skill; defensive rally-joiner effect."));
        d.add(new Entry("Aiden","Scarlet Brigade Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),2,WOSH,"Attack component."));
        d.add(new Entry("Aiden","Scarlet Brigade Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),2,WOSH,"Defense component."));
        d.add(new Entry("Aiden","Rush","TROOP_ATTACK_UP","ALL_TROOPS","EVERY_2_TURNS_1_TURN",v(10,20,30,40,50),3,WOSH,"Exact public ladder."));

        // Bertha — exact values cross-checked between WSCO and wos-wiki.de.
        d.add(new Entry("Bertha","Dissection","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),1,BERTHA,"Friendly Squad Lethality; first Expedition skill."));
        d.add(new Entry("Bertha","Vengeance Damage","EXTRA_DAMAGE","LANCER","EVERY_2_LANCER_ATTACKS",v(25,50,75,100,125),2,BERTHA,"Damage component."));
        d.add(new Entry("Bertha","Vengeance Marksman Attack","MARKSMAN_ATTACK_UP","MARKSMAN","EVERY_2_LANCER_ATTACKS_1_TURN",v(6,12,18,24,30),2,BERTHA,"Marksman Attack component."));
        d.add(new Entry("Bertha","Lethal Precision Damage","DAMAGE_DEALT_UP","LANCER","EACH_ATTACK",v(12,24,36,48,60),3,BERTHA,"Lancer damage component."));
        d.add(new Entry("Bertha","Lethal Precision Weakness","ENEMY_DAMAGE_TAKEN_UP","TARGET","EACH_LANCER_ATTACK_1_TURN",v(5,10,15,20,25),3,BERTHA,"Weakness component."));

        // Eleanor — exact public ladders; source independently describes all three Expedition skills.
        d.add(new Entry("Eleanor","Theoretical Advantage","ENEMY_DEFENSE_DOWN","ENEMY_ALL","PASSIVE",v(5,10,15,20,25),1,ELEANOR,"First Expedition skill."));
        d.add(new Entry("Eleanor","Pierce Caliber Extra Damage","EXTRA_DAMAGE","MARKSMAN","ON_ATTACK_50_PERCENT",v(20,40,60,80,100),2,ELEANOR,"Extra-damage component."));
        d.add(new Entry("Eleanor","Pierce Caliber Damage","MARKSMAN_DAMAGE_UP","MARKSMAN","ON_ATTACK_50_PERCENT_1_TURN",v(8,16,24,32,40),2,ELEANOR,"Damage-inflicted component."));
        d.add(new Entry("Eleanor","Trajectoids","DAMAGE_DEALT_UP","MARKSMAN_VS_MARKSMAN","EVERY_2_MARKSMAN_ATTACKS",v(12,24,36,48,60),3,ELEANOR,"Exact public ladder."));

        DATA=Collections.unmodifiableList(d);
    }
    public static List<Entry> all(){ return DATA; }
    private HeroSkillCatalogGen17Verified(){}
}
