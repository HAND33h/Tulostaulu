package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/** Public-source Expedition skill facts for battle modelling. */
public final class PublicHeroSkillCatalog {
    public enum Confidence { OFFICIAL_MECHANIC, VERIFIED_PUBLIC, COMMUNITY_MODEL, UNVERIFIED }
    public static final class Skill {
        public final String hero,name,effect,target,trigger,source;
        public final double[] values;
        public final Confidence confidence;
        Skill(String h,String n,String e,String t,String tr,double[] v,Confidence c,String s){hero=h;name=n;effect=e;target=t;trigger=tr;values=v;confidence=c;source=s;}
        public double max(){return values==null||values.length==0?0:values[values.length-1];}
    }
    private static final List<Skill> DATA;
    static {
        ArrayList<Skill> d=new ArrayList<>();
        // Early public data.
        pub(d,"Sergey","Defender's Edge","DAMAGE_TAKEN_DOWN","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),"public hero guide");
        pub(d,"Sergey","Weaken","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"public hero guide");
        pub(d,"Ling Xue","Fearsome Aura","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"public hero guide");

        // Gen 10.
        pub(d,"Gregory","Legion of Sun","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"public hero guides");
        pub(d,"Gregory","Legion of Sun Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),"public hero guides");
        pub(d,"Gregory","Charged Assault","CRIT_CHANCE","ALL_TROOPS","NORMAL_ATTACK",v(5,10,15,20,25),"public hero guides");
        pub(d,"Gregory","Unbroken","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(4,8,12,16,20),"public hero guides");
        pub(d,"Freya","Fog of War","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"public hero guides");
        pub(d,"Freya","Blood Moon Scythe","EXTRA_DAMAGE","LANCER","AFTER_NORMAL_ATTACK_50_PERCENT",v(20,40,60,80,100),"public hero guides");
        pub(d,"Freya","Night's Vengeance Damage","DAMAGE_DEALT_UP","INFANTRY_MARKSMAN","PASSIVE",v(3,6,9,12,15),"public hero guides");
        pub(d,"Freya","Night's Vengeance Guard","DAMAGE_TAKEN_DOWN","INFANTRY_MARKSMAN","PASSIVE",v(3,6,9,12,15),"public hero guides");
        pub(d,"Blanchette","Armed to the Teeth","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"public hero guides");
        pub(d,"Blanchette","Blood Hunter","MARKSMAN_DAMAGE_UP","MARKSMAN","EVERY_3_ROUNDS",v(15,30,45,60,75),"public hero guides");
        pub(d,"Blanchette","Crimson Sniper vs Lancer","DAMAGE_UP","MARKSMAN_TO_LANCER","EVERY_2_STRIKES",v(8,16,24,32,40),"public hero guides");
        pub(d,"Blanchette","Crimson Sniper vs Marksman","DAMAGE_UP","MARKSMAN_TO_MARKSMAN","EVERY_2_STRIKES",v(4,8,12,16,20),"public hero guides");

        // Gen 17: WOS Heroes exposes exact Lv1-Lv5 Expedition ladders.
        pub(d,"Aiden","Starfire Wall","TROOP_DEFENSE_UP","INFANTRY","EACH_TRIGGER_40_PERCENT_1_TURN",v(10,20,30,40,50),"WOS Heroes Gen17");
        pub(d,"Aiden","Scarlet Brigade Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"WOS Heroes Gen17");
        pub(d,"Aiden","Scarlet Brigade Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),"WOS Heroes Gen17");
        pub(d,"Aiden","Rush","TROOP_ATTACK_UP","ALL_TROOPS","EVERY_2_TURNS_1_TURN",v(10,20,30,40,50),"WOS Heroes Gen17");
        pub(d,"Aiden","Guardians of Iron","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","EXCLUSIVE_WEAPON",v(5,7.5,10,12.5,15),"WOS Heroes Gen17");

        pub(d,"Bertha","Dissection","SQUAD_LETHALITY_UP","ALL_TROOPS","PASSIVE_PRIMARY",v(5,10,15,20,25),"WOS Heroes Gen17");
        // Other Bertha Expedition effects are intentionally not guessed until their complete ladders are captured.
        pub(d,"Bertha","Antibodies","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","EXCLUSIVE_WEAPON",v(5,7.5,10,12.5,15),"WOS Heroes Gen17");

        pub(d,"Eleanor","Theoretical Advantage","ENEMY_DEFENSE_DOWN","ENEMY_ALL","PASSIVE_PRIMARY",v(5,10,15,20,25),"WOS Heroes Gen17");
        pub(d,"Eleanor","Pierce Caliber Extra Damage","EXTRA_DAMAGE","MARKSMAN","EACH_ATTACK_50_PERCENT",v(20,40,60,80,100),"WOS Heroes Gen17");
        pub(d,"Eleanor","Pierce Caliber Damage","DAMAGE_DEALT_UP","MARKSMAN","ON_PROC_1_TURN",v(8,16,24,32,40),"WOS Heroes Gen17");
        pub(d,"Eleanor","Trajectoids","DAMAGE_UP","MARKSMAN_TO_MARKSMAN","EVERY_2_ATTACKS",v(12,24,36,48,60),"WOS Heroes Gen17");
        pub(d,"Eleanor","Doom Patrol","RALLY_SQUAD_LETHALITY_UP","RALLY_TROOPS","EXCLUSIVE_WEAPON",v(5,7.5,10,12.5,15),"WOS Heroes Gen17 / WoS Guru");

        DATA=Collections.unmodifiableList(d);
    }
    private static void pub(List<Skill>d,String h,String n,String e,String t,String tr,double[]v,String src){d.add(new Skill(h,n,e,t,tr,v,Confidence.VERIFIED_PUBLIC,src));}
    private static double[] v(double...x){return x;}
    public static List<Skill> forHero(String hero){ArrayList<Skill> out=new ArrayList<>();if(hero==null)return out;for(Skill s:DATA)if(s.hero.equalsIgnoreCase(hero))out.add(s);return out;}
    public static List<Skill> all(){return DATA;}
    public static boolean hasVerifiedData(String hero){for(Skill s:DATA)if(s.hero.equalsIgnoreCase(hero)&&s.confidence==Confidence.VERIFIED_PUBLIC)return true;return false;}
    public static Skill primaryExpeditionSkill(String hero){for(Skill s:DATA)if(s.hero.equalsIgnoreCase(hero)&&s.trigger.contains("PRIMARY"))return s;return null;}
    private PublicHeroSkillCatalog(){}
}
