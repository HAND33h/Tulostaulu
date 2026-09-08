package com.hand33h.tulostaulu;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Public-source WOS hero catalog for OCR/battle analysis.
 * Generation/class are roster facts. Battle notes are evidence-tagged so community
 * recommendations are never presented as official combat formulas.
 */
public final class HeroGenerationCatalog {
    public enum Evidence { ROSTER_FACT, PUBLIC_SKILL_DATA, COMMUNITY_META }
    public static final class Hero {
        public final String name, troop, battleNote;
        public final int generation;
        public final Evidence evidence;
        Hero(String n,int g,String t,String note,Evidence e){name=n;generation=g;troop=t;battleNote=note;evidence=e;}
        public String label(){return name+" • Gen "+generation+" • "+troop;}
    }
    private static final Map<String,Hero> HEROES;
    static {
        LinkedHashMap<String,Hero> m=new LinkedHashMap<>();
        add(m,"Natalia",1,"Infantry","Early-generation infantry leader.",Evidence.ROSTER_FACT);
        add(m,"Jeronimo",1,"Infantry","First Expedition skill is widely used as a rally-join damage buff; verify skill level from report.",Evidence.COMMUNITY_META);
        add(m,"Molly",1,"Lancer","Early-generation lancer.",Evidence.ROSTER_FACT);
        add(m,"Zinman",1,"Marksman","Early-generation marksman.",Evidence.ROSTER_FACT);
        add3(m,2,"Flint","Philly","Alonso"); add3(m,3,"Logan","Mia","Greg"); add3(m,4,"Ahmose","Reina","Lynn");
        add3(m,5,"Hector","Norah","Gwen"); add3(m,6,"Wu Ming","Renee","Wayne"); add3(m,7,"Edith","Gordon","Bradley");
        add3(m,8,"Gatot","Sonya","Hendrik"); add3(m,9,"Magnus","Fred","Xura");
        add(m,"Gregory",10,"Infantry","Gen10 frontline/rally leader; public skill data includes team buffs and infantry damage mitigation.",Evidence.PUBLIC_SKILL_DATA);
        add(m,"Freya",10,"Lancer","Public Expedition data: enemy ATK reduction plus conditional/follow-up damage effects.",Evidence.PUBLIC_SKILL_DATA);
        add(m,"Blanchette",10,"Marksman","Public Expedition data: all-troop lethality and recurring marksman damage effects.",Evidence.PUBLIC_SKILL_DATA);
        add(m,"Eleonora",11,"Infantry","Gen11 infantry frontline; community meta commonly uses her as main tank/caller.",Evidence.COMMUNITY_META);
        add(m,"Lloyd",11,"Lancer","Gen11 lancer with debuff-oriented public skill descriptions.",Evidence.PUBLIC_SKILL_DATA);
        add(m,"Rufus",11,"Marksman","Gen11 marksman damage dealer; public/community sources rate him strongly for rally offense.",Evidence.COMMUNITY_META);
        add3(m,12,"Hervor","Karol","Ligeia"); add3(m,13,"Gisela","Flora","Vulcanus"); add3(m,14,"Elif","Dominic","Cara");
        add3(m,15,"Hank","Estrella","Viveca"); add3(m,16,"Seigel","Ursar","Aisling"); add3(m,17,"Aiden","Bertha","Eleanor");
        HEROES=Collections.unmodifiableMap(m);
    }
    private static void add3(Map<String,Hero>m,int g,String inf,String lan,String mark){
        add(m,inf,g,"Infantry","Roster/class verified; detailed battle modifiers require sourced skill record.",Evidence.ROSTER_FACT);
        add(m,lan,g,"Lancer","Roster/class verified; detailed battle modifiers require sourced skill record.",Evidence.ROSTER_FACT);
        add(m,mark,g,"Marksman","Roster/class verified; detailed battle modifiers require sourced skill record.",Evidence.ROSTER_FACT);
    }
    private static void add(Map<String,Hero>m,String n,int g,String t,String note,Evidence e){m.put(n.toLowerCase(Locale.ROOT),new Hero(n,g,t,note,e));}
    public static Hero find(String name){return name==null?null:HEROES.get(name.toLowerCase(Locale.ROOT));}
    public static Map<String,Hero> all(){return HEROES;}
    public static String heroesDetectedIn(String text){
        if(text==null)return ""; String low=text.toLowerCase(Locale.ROOT); StringBuilder out=new StringBuilder();
        for(Hero h:HEROES.values()) if(low.contains(h.name.toLowerCase(Locale.ROOT))){if(out.length()>0)out.append("; ");out.append(h.label()).append(" [").append(h.evidence).append("]");}
        return out.toString();
    }
    private HeroGenerationCatalog(){}
}
