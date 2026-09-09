package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Public-source cross-checked Gina skill data; Gen0 integration source. */
public final class VerifiedHeroSkillGina {
    public static final class Row {
        public final String hero, skill, effect, target, trigger, source;
        public final double[] values;
        Row(String h,String s,String e,String t,String tr,double[] v,String src){hero=h;skill=s;effect=e;target=t;trigger=tr;values=v;source=src;}
    }
    private static double[] v(double... x){return x;}
    public static final List<Row> DATA = Collections.unmodifiableList(Arrays.asList(
        new Row("Gina","Incendiary Arrow Main","DAMAGE_DEALT","TARGET","EXPLORATION_ACTIVE",v(210,230,250,270,290),"WOS Forge (updated 2026-09-06) / Whiteout Survival Wiki / WOS Heroes / Heaven Guardian 2026-08-19; cross-checked 2026-09-10"),
        new Row("Gina","Incendiary Arrow Splash","DAMAGE_DEALT","NEARBY_ENEMIES","EXPLORATION_ACTIVE",v(70,77,84,91,98),"WOS Forge (updated 2026-09-06) / Whiteout Survival Wiki / WOS Heroes / Heaven Guardian 2026-08-19; cross-checked 2026-09-10"),
        new Row("Gina","Windtalker","ATTACK_SPEED_UP","GINA","EXPLORATION_PASSIVE",v(10,15,20,25,30),"WOS Forge (updated 2026-09-06) / Whiteout Survival Wiki / WOS Heroes / Heaven Guardian 2026-08-19; cross-checked 2026-09-10"),
        new Row("Gina","Eagle Eyes","CRIT_RATE_UP","GINA","EXPLORATION_PASSIVE",v(7,10,13,16,20),"WOS Forge (updated 2026-09-06) / Whiteout Survival Wiki / WOS Heroes / Heaven Guardian 2026-08-19; cross-checked 2026-09-10"),
        new Row("Gina","Endurance Training","STAMINA_COST_DOWN","BEAST_AND_POLAR_TERROR_HUNTING","EXPEDITION_HUNTING_PASSIVE",v(10,12,15,18,20),"WOS Forge (updated 2026-09-06) / Whiteout Survival Wiki / WOS Heroes / Heaven Guardian 2026-08-19; cross-checked 2026-09-10"),
        new Row("Gina","Quick Paced","WILDERNESS_MARCH_SPEED_UP","HUNTING_MARCH","EXPEDITION_HUNTING_PASSIVE",v(20,40,60,80,100),"WOS Forge (updated 2026-09-06) / Whiteout Survival Wiki / WOS Heroes / Heaven Guardian 2026-08-19; cross-checked 2026-09-10")
    ));
    private VerifiedHeroSkillGina(){}
}
