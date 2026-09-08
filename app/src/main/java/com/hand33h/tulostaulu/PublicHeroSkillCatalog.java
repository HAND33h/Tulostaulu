package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Public-source Expedition skill facts for battle modelling.
 * Never treats community/meta estimates as official formulas.
 * Values are Lv1..Lv5 when the source exposes a five-level ladder.
 */
public final class PublicHeroSkillCatalog {
    public enum Confidence { VERIFIED_PUBLIC, COMMUNITY_MODEL, UNVERIFIED }
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
        // Gen 0 / early heroes — public skill descriptions.
        add(d,"Sergey","Defender's Edge","DAMAGE_TAKEN_DOWN","ALL_TROOPS","PASSIVE",v(4,8,12,16,20),"OutOfGames hero guide");
        add(d,"Sergey","Weaken","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"OutOfGames hero guide");
        add(d,"Ling Xue","Fearsome Aura","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"OutOfGames hero guide");
        add(d,"Ling Xue","Total Control","TRAINING_SPEED_UP","CITY","PASSIVE",v(4,8,12,16,20),"OutOfGames hero guide");

        // Gen 10 — public Expedition descriptions used by the simulator scheduler.
        add(d,"Gregory","Legion of Sun","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),"public hero guides");
        add(d,"Gregory","Legion of Sun Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),"public hero guides");
        add(d,"Gregory","Charged Assault","CRIT_CHANCE","ALL_TROOPS","NORMAL_ATTACK",v(5,10,15,20,25),"public hero guides");
        add(d,"Gregory","Unbroken","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(4,8,12,16,20),"public hero guides");
        add(d,"Freya","Fog of War","ENEMY_ATTACK_DOWN","ENEMY_ALL","PASSIVE",v(4,8,12,16,20),"public hero guides");
        add(d,"Freya","Blood Moon Scythe","EXTRA_DAMAGE","LANCER","AFTER_NORMAL_ATTACK_50_PERCENT",v(20,40,60,80,100),"public hero guides");
        add(d,"Freya","Night's Vengeance Damage","DAMAGE_DEALT_UP","INFANTRY_MARKSMAN","PASSIVE",v(3,6,9,12,15),"public hero guides");
        add(d,"Freya","Night's Vengeance Guard","DAMAGE_TAKEN_DOWN","INFANTRY_MARKSMAN","PASSIVE",v(3,6,9,12,15),"public hero guides");
        add(d,"Blanchette","Armed to the Teeth","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),"public hero guides");
        add(d,"Blanchette","Blood Hunter","MARKSMAN_DAMAGE_UP","MARKSMAN","EVERY_3_ROUNDS",v(15,30,45,60,75),"public hero guides");
        add(d,"Blanchette","Crimson Sniper vs Lancer","DAMAGE_UP","MARKSMAN_TO_LANCER","EVERY_2_STRIKES",v(8,16,24,32,40),"public hero guides");
        add(d,"Blanchette","Crimson Sniper vs Marksman","DAMAGE_UP","MARKSMAN_TO_MARKSMAN","EVERY_2_STRIKES",v(4,8,12,16,20),"public hero guides");

        // Gen 17 — current public database, captured as explicit mechanics rather than flattened averages.
        add(d,"Eleanor","Theoretical Advantage","ENEMY_DEFENSE_DOWN","ENEMY_ALL","PASSIVE",v(5,10,15,20,25),"WOS Heroes Gen17");
        add(d,"Eleanor","Pierce Caliber Extra Damage","EXTRA_DAMAGE","MARKSMAN","EACH_ATTACK_50_PERCENT",v(20,40,60,80,100),"WOS Heroes Gen17");
        add(d,"Eleanor","Pierce Caliber Damage","DAMAGE_DEALT_UP","MARKSMAN","ON_PROC_1_TURN",v(8,16,24,32,40),"WOS Heroes Gen17");
        add(d,"Eleanor","Trajectoids","DAMAGE_UP","MARKSMAN_TO_MARKSMAN","EVERY_2_ATTACKS",v(12,24,36,48,60),"WOS Heroes Gen17");

        DATA=Collections.unmodifiableList(d);
    }
    private static void add(List<Skill>d,String h,String n,String e,String t,String tr,double[]v,String src){d.add(new Skill(h,n,e,t,tr,v,Confidence.VERIFIED_PUBLIC,src));}
    private static double[] v(double...x){return x;}
    public static List<Skill> forHero(String hero){ArrayList<Skill> out=new ArrayList<>();if(hero==null)return out;for(Skill s:DATA)if(s.hero.toLowerCase(Locale.ROOT).equals(hero.toLowerCase(Locale.ROOT)))out.add(s);return out;}
    public static List<Skill> all(){return DATA;}
    public static boolean hasVerifiedData(String hero){for(Skill s:DATA)if(s.hero.equalsIgnoreCase(hero)&&s.confidence==Confidence.VERIFIED_PUBLIC)return true;return false;}
    private PublicHeroSkillCatalog(){}
}
