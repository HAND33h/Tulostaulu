package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Public community server-age timeline. Planning aid only: rollout dates can vary by state. */
public final class ServerTimelineData {
    public static final class Unlock {
        public final String category, name;
        public final int approximateDay;
        Unlock(String c,String n,int d){category=c;name=n;approximateDay=d;}
    }
    private static final List<Unlock> DATA;
    static {
        ArrayList<Unlock> d=new ArrayList<>();
        String[] gens={"Gen1:0","Gen2:40","Gen3:120","Gen4:195","Gen5:270","Gen6:360","Gen7:440","Gen8:520","Gen9:600","Gen10:680","Gen11:760","Gen12:840","Gen13:920","Gen14:1000","Gen15:1080","Gen16:1160"};
        for(String s:gens){String[] p=s.split(":");d.add(new Unlock("HERO_GENERATION",p[0],Integer.parseInt(p[1])));}
        d.add(new Unlock("EXPERT","Holger / Agnes / Cyrille",120));
        d.add(new Unlock("EXPERT","Romulus",160));
        d.add(new Unlock("EXPERT","Valeria / Baldur / Fabian",200));
        d.add(new Unlock("EXPERT","Kathy / Ronne",230));
        d.add(new Unlock("PET_GENERATION","Gen1: Cave Hyena / Arctic Wolf / Musk Ox",54));
        d.add(new Unlock("PET_GENERATION","Gen2: Titan Roc / Giant Tapir",90));
        d.add(new Unlock("PET_GENERATION","Gen3: Snow Leopard / Giant Elk",140));
        d.add(new Unlock("PET_GENERATION","Gen4-5: Cave Lion / Snow Ape",200));
        d.add(new Unlock("PET_GENERATION","Gen6-7: Iron Rhino / Saber-tooth",280));
        d.add(new Unlock("PET_GENERATION","Gen8-9: Frost Gorilla / Mammoth",370));
        d.add(new Unlock("PET_GENERATION","Gen10: Frostscale Chameleon",450));
        DATA=Collections.unmodifiableList(d);
    }
    public static List<Unlock> all(){return DATA;}
    public static List<Unlock> availableAtDay(int day){ArrayList<Unlock> r=new ArrayList<>();for(Unlock u:DATA)if(day>=u.approximateDay)r.add(u);return r;}
    public static String sourceUrl(){return "https://www.whiteoutsurvival.wiki/server-timeline-server-age/";}
    private ServerTimelineData(){}
}
