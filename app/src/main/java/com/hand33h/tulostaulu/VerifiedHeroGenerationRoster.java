package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public WOS legendary hero generation roster, cross-checked 2026-09-09. */
public final class VerifiedHeroGenerationRoster {
    public static final String PRIMARY_SOURCE = "https://wosheroes.com/";
    public static final String CROSS_CHECK = "https://wos-wiki.de/en/helden/";
    public static final class Generation {
        public final int generation;
        public final int typicalUnlockDay;
        public final List<String> heroes;
        Generation(int g,int d,String...h){generation=g;typicalUnlockDay=d;heroes=Collections.unmodifiableList(Arrays.asList(h));}
    }
    public static final List<Generation> DATA=Collections.unmodifiableList(Arrays.asList(
        new Generation(1,0,"Natalia","Jeronimo","Molly","Zinman"),
        new Generation(2,40,"Flint","Philly","Alonso"),
        new Generation(3,120,"Logan","Mia","Greg"),
        new Generation(4,195,"Ahmose","Reina","Lynn"),
        new Generation(5,270,"Hector","Norah","Gwen"),
        new Generation(6,360,"Wu Ming","Renee","Wayne"),
        new Generation(7,440,"Edith","Gordon","Bradley"),
        new Generation(8,520,"Gatot","Sonya","Hendrik"),
        new Generation(9,600,"Magnus","Fred","Xura"),
        new Generation(10,700,"Gregory","Freya","Blanchette"),
        new Generation(11,800,"Eleonora","Lloyd","Rufus"),
        new Generation(12,870,"Hervor","Karol","Ligeia"),
        new Generation(13,950,"Gisela","Flora","Vulcanus"),
        new Generation(14,1040,"Elif","Dominic","Cara"),
        new Generation(15,1130,"Hank","Estrella","Viveca"),
        new Generation(16,1220,"Seigel","Ursar","Aisling"),
        new Generation(17,1310,"Aiden","Bertha","Eleanor")
    ));
    private VerifiedHeroGenerationRoster(){}
}
