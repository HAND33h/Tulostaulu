package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Dawn Academy Expert system metadata for availability filtering and simulator UI.
 * Community-source timing is deliberately kept separate from hard combat math.
 * Checked 2026-09-09 against WSCO Expert overview.
 */
public final class ExpertSystemRules {
    private ExpertSystemRules() {}

    public static final String SOURCE = "https://www.whiteoutsurvival-community.com/tools/wiki/events/expert-wsco.html";
    public static final int AFFINITY_MIN = 0;
    public static final int AFFINITY_MAX = 100;
    public static final int SKILLS_PER_EXPERT = 4;

    public static final class Expert {
        public final String name;
        public final int generation;
        public final int typicalServerDay;
        public final Integer intimateCostClass;
        public final String note;
        Expert(String n,int g,int d,Integer c,String note){name=n;generation=g;typicalServerDay=d;intimateCostClass=c;this.note=note;}
    }

    private static final List<Expert> EXPERTS = Collections.unmodifiableList(Arrays.asList(
        new Expert("Agnes",1,150,50,"Dawn Academy Gen 1"),
        new Expert("Cyrille",1,150,50,"Dawn Academy Gen 1"),
        new Expert("Holger",1,150,80,"Dawn Academy Gen 1"),
        new Expert("Romulus",1,150,360,"Dawn Academy Gen 1"),
        new Expert("Baldur",2,195,60,"Dawn Academy Gen 2"),
        new Expert("Fabian",2,195,120,"Dawn Academy Gen 2"),
        new Expert("Valeria",2,195,200,"Dawn Academy Gen 2"),
        new Expert("Ronne",2,195,80,"Dawn Academy Gen 2"),
        new Expert("Kathy",3,240,80,"Frostfire Mine progression"),
        new Expert("Gareth",4,0,null,"Live only on eligible states; do not unlock from day alone")
    ));

    public static List<Expert> all(){ return EXPERTS; }

    public static boolean affinityValid(int affinity){ return affinity >= AFFINITY_MIN && affinity <= AFFINITY_MAX; }

    /** Typical-day helper only; state eligibility and rollout conditions can override it. */
    public static boolean typicallyAvailableByDay(Expert e,int serverDay){
        return e.typicalServerDay > 0 && serverDay >= e.typicalServerDay;
    }
}
