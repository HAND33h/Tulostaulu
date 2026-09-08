package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Cross-checked public Expedition data. Kept separate until central catalog migration. */
public final class VerifiedHeroSkillAdditions {
    public static final class Entry {
        public final String hero,name,effect,target,trigger,sourceUrl,note;
        public final double[] values;
        public final int expeditionOrder;
        Entry(String h,String n,String e,String t,String tr,double[]v,int o,String u,String note){hero=h;name=n;effect=e;target=t;trigger=tr;values=v;expeditionOrder=o;sourceUrl=u;this.note=note;}
    }
    private static final List<Entry> DATA;
    static {
        ArrayList<Entry>d=new ArrayList<>();
        // Gen1
        add(d,"Jeronimo","Battle Manifesto","DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),1,"https://wosheroes.com/heroes/jeronimo/","");
        add(d,"Jeronimo","Swordmentor","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),2,"https://wosheroes.com/heroes/jeronimo/","");
        add(d,"Jeronimo","Expert Swordsmanship","DAMAGE_DEALT_UP","ALL_TROOPS","EVERY_4_TURNS_2_TURNS",v(6,12,18,24,30),3,"https://wosheroes.com/heroes/jeronimo/","Cross-check preferred over conflicting WSCO stun wording.");
        add(d,"Natalia","Feral Protection","DAMAGE_TAKEN_DOWN","ALL_TROOPS","PROC_40_PERCENT",v(10,20,30,40,50),1,"https://wiki.wosforge.org/wiki/Natalia","");
        add(d,"Natalia","Queen of the Wild","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),2,"https://wiki.wosforge.org/wiki/Natalia","");
        add(d,"Natalia","Call of the Wild","DAMAGE_DEALT_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),3,"https://wiki.wosforge.org/wiki/Natalia","");
        add(d,"Natalia","Invincibles","RALLY_LETHALITY_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),0,"https://www.wosbattlemaster.com/heroes/natalia","");
        add(d,"Zinman","Implacable Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),1,"https://www.whiteoutsurvival.wiki/heroes/zinman/","");
        add(d,"Zinman","Implacable Health","TROOP_HEALTH_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),1,"https://www.whiteoutsurvival.wiki/heroes/zinman/","");
        add(d,"Zinman","Positional Battler","TROOP_LETHALITY_UP","ALL_TROOPS","PASSIVE",v(5,10,15,20,25),3,"https://www.whiteoutsurvival.wiki/heroes/zinman/","WSCO describes this as damage; WOS wiki labels Lethality. Preserve discrepancy.");
        add(d,"Zinman","Defend to Attack","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),0,"https://www.whiteoutsurvival-community.com/guides/heroes/en/zinman.html","");
        // Gen6 max values cross-checked; exact lower ladders still intentionally omitted.
        add(d,"Renee","Nightmare Trace","EXTRA_DAMAGE","LANCER","EVERY_3_TURNS_1_TURN",v(150),1,"https://www.whiteoutsurvival-community.com/guides/heroes/en/renee.html","Max verified; lower levels not encoded.");
        add(d,"Renee","Dreamcatcher","DAMAGE_DEALT_UP","LANCER_TO_MARKED", "PASSIVE",v(75),2,"https://www.whiteoutsurvival-community.com/guides/heroes/en/renee.html","Max verified; lower levels not encoded.");
        add(d,"Renee","Dreamslice","DAMAGE_DEALT_UP","LANCER_TO_MARKED","PASSIVE",v(25),3,"https://www.whiteoutsurvival-community.com/guides/heroes/en/renee.html","Max verified; lower levels not encoded.");
        add(d,"Renee","Wistful Enchantment","RALLY_LETHALITY_UP","RALLY_TROOPS","WIDGET_EXPEDITION",v(15),0,"https://www.whiteoutsurvival-community.com/guides/heroes/en/renee.html","Max verified.");
        // Gen9 Xura max values; no fabricated lower ladders.
        add(d,"Xura","Fungal Fog","DAMAGE_TAKEN_DOWN","ALL_TROOPS","PASSIVE",v(20),1,"https://www.whiteoutsurvival-community.com/guides/heroes/en/xura.html","Max verified.");
        add(d,"Xura","Piercing Arrow Damage","EXTRA_DAMAGE","MARKSMAN","EVERY_2_ATTACKS",v(100),2,"https://www.whiteoutsurvival-community.com/guides/heroes/en/xura.html","Max verified.");
        add(d,"Xura","Piercing Arrow Debuff","ENEMY_DAMAGE_TAKEN_UP","TARGET","EVERY_2_ATTACKS_1_TURN",v(25),2,"https://www.whiteoutsurvival-community.com/guides/heroes/en/xura.html","Max verified.");
        add(d,"Xura","Unorthodoxy Damage","DAMAGE_DEALT_UP","MARKSMAN","PASSIVE",v(10),3,"https://www.whiteoutsurvival-community.com/guides/heroes/en/xura.html","Max verified.");
        add(d,"Xura","Unorthodoxy Guard","DAMAGE_TAKEN_DOWN","MARKSMAN","PASSIVE",v(15),3,"https://www.whiteoutsurvival-community.com/guides/heroes/en/xura.html","Max verified.");
        add(d,"Xura","Gaiac Hymn","DEFENDER_ATTACK_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(15),0,"https://www.whiteoutsurvival-community.com/guides/heroes/en/xura.html","Max verified.");
        // Gen10 exact ladders from WhiteoutData.
        add(d,"Gregory","Legion of the Sun Attack","TROOP_ATTACK_UP","ALL_TROOPS","PASSIVE",v(3,6,9,12,15),1,"https://whiteoutdata.com/heroes/generation-10-heroes/","");
        add(d,"Gregory","Legion of the Sun Defense","TROOP_DEFENSE_UP","ALL_TROOPS","PASSIVE",v(2,4,6,8,10),1,"https://whiteoutdata.com/heroes/generation-10-heroes/","");
        add(d,"Gregory","Charged Assault","CRIT_RATE_UP","ALL_TROOPS_NORMAL_ATTACK","PASSIVE",v(5,10,15,20,25),2,"https://whiteoutdata.com/heroes/generation-10-heroes/","");
        add(d,"Gregory","Unbroken","DAMAGE_TAKEN_DOWN","INFANTRY","PASSIVE",v(4,8,12,16,20),3,"https://whiteoutdata.com/heroes/generation-10-heroes/","");
        add(d,"Gregory","Day of the Guard","DEFENDER_LETHALITY_UP","DEFENDER_TROOPS","WIDGET_EXPEDITION",v(5,7.5,10,12.5,15),0,"https://whiteoutdata.com/heroes/generation-10-heroes/","");
        DATA=Collections.unmodifiableList(d);
    }
    private static void add(List<Entry>d,String h,String n,String e,String t,String tr,double[]v,int o,String u,String note){d.add(new Entry(h,n,e,t,tr,v,o,u,note));}
    private static double[] v(double...x){return x;}
    public static List<Entry> all(){return DATA;}
    private VerifiedHeroSkillAdditions(){}
}
