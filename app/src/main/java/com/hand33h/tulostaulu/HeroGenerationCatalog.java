package com.hand33h.tulostaulu;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/** Public-source hero generation/class catalog used by OCR and battle analysis. */
public final class HeroGenerationCatalog {
    public static final class Hero { public final String name; public final int generation; public final String troop; Hero(String n,int g,String t){name=n;generation=g;troop=t;} }
    private static final Map<String,Hero> HEROES;
    static {
        LinkedHashMap<String,Hero> m=new LinkedHashMap<>();
        // Gen 1 has two legendary Infantry heroes.
        add(m,"Natalia",1,"Infantry"); add(m,"Jeronimo",1,"Infantry"); add(m,"Molly",1,"Lancer"); add(m,"Zinman",1,"Marksman");
        add3(m,2,"Flint","Philly","Alonso");
        add3(m,3,"Logan","Mia","Greg");
        add3(m,4,"Ahmose","Reina","Lynn");
        add3(m,5,"Hector","Norah","Gwen");
        add3(m,6,"Wu Ming","Renee","Wayne");
        add3(m,7,"Edith","Gordon","Bradley");
        add3(m,8,"Gatot","Sonya","Hendrik");
        add3(m,9,"Magnus","Fred","Xura");
        add3(m,10,"Gregory","Freya","Blanchette");
        add3(m,11,"Eleonora","Lloyd","Rufus");
        add3(m,12,"Hervor","Karol","Ligeia");
        add3(m,13,"Gisela","Flora","Vulcanus");
        add3(m,14,"Elif","Dominic","Cara");
        add3(m,15,"Hank","Estrella","Viveca");
        add3(m,16,"Seigel","Ursar","Aisling");
        add3(m,17,"Aiden","Bertha","Eleanor");
        HEROES=Collections.unmodifiableMap(m);
    }
    private static void add3(Map<String,Hero>m,int g,String inf,String lan,String mark){add(m,inf,g,"Infantry");add(m,lan,g,"Lancer");add(m,mark,g,"Marksman");}
    private static void add(Map<String,Hero>m,String n,int g,String t){m.put(n.toLowerCase(Locale.ROOT),new Hero(n,g,t));}
    public static Hero find(String name){return name==null?null:HEROES.get(name.toLowerCase(Locale.ROOT));}
    public static Map<String,Hero> all(){return HEROES;}
    private HeroGenerationCatalog(){}
}
