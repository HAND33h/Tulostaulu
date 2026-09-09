package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Dawn Academy Expert metadata used by native planners.
 * Official mechanics are kept separate from community strategy metadata.
 * Never invent missing per-generation costs: null means not verified.
 * Checked 2026-09-09.
 */
public final class ExpertSystemRules {
    private ExpertSystemRules() {}

    public static final String COMMUNITY_SOURCE = "https://www.whiteoutsurvival-community.com/tools/wiki/events/expert-wsco.html";
    public static final String OFFICIAL_SKILL_RESEARCH_SOURCE = "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/8418-how-do-i-unlock-expert-skill-research/";
    public static final String OFFICIAL_BOOK_SOURCE = "https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/8404-what-is-the-book-of-knowledge-and-how-do-i-acquire-it/";

    public static final int AFFINITY_MIN = 0;
    public static final int AFFINITY_MAX = 100;
    public static final int SKILLS_PER_EXPERT = 4;
    public static final int SKILL_RESEARCH_UNLOCK_AFFINITY = 100;
    public static final int BOOK_PHASE_GATE_LEVEL = 10;
    public static final int SIGIL_MILESTONE_STEP = 10;

    public static final int COMPASS_AFFINITY = 10;
    public static final int FIERY_HEART_AFFINITY = 100;
    public static final int SAIL_OF_CONQUEST_AFFINITY = 1000;

    public enum Focus { ECONOMY, BEAR, ARENA, ALLIANCE_EVENTS, FOUNDRY, SVS, TRADE_ROUTE, FROSTFIRE, UNIVERSAL_COMBAT, RECOVERY }

    public static final class Expert {
        public final String name;
        public final int generation;
        public final int typicalServerDay;
        public final Integer intimateCostClass;
        public final Focus focus;
        public final boolean svsDirect;
        public final String recommendedStop;
        public final String role;
        public final String[] skills;
        public final String talent;
        public final String note;
        Expert(String n,int g,int d,Integer c,Focus f,boolean svs,String stop,String role,String[] skills,String talent,String note){
            name=n;generation=g;typicalServerDay=d;intimateCostClass=c;focus=f;svsDirect=svs;recommendedStop=stop;this.role=role;this.skills=skills;this.talent=talent;this.note=note;
        }
    }

    private static final List<Expert> EXPERTS = Collections.unmodifiableList(Arrays.asList(
        new Expert("Agnes",1,150,50,Focus.ECONOMY,false,"Affinity 70","City / daily utility",new String[]{"Efficient Recon","Optimization","Project Management","Covert Knowledge"},"Earthbreaker","Strong economy priority; community strategy metadata"),
        new Expert("Cyrille",1,150,50,Focus.BEAR,false,"Affinity 60","Bear Hunt specialist",new String[]{"Entrapment","Scavenging","Weapon Master","Ursa's Bane"},"Hunter's Heart","Bear progression and gear-material utility"),
        new Expert("Holger",1,150,80,Focus.ARENA,false,"Affinity 50","Arena specialist",new String[]{"Arena Elite","Crowd Pleaser","Arena Star","Legacy"},"Blade Dancing","Situational outside Arena"),
        new Expert("Romulus",1,150,360,Focus.UNIVERSAL_COMBAT,false,"Affinity 40 F2P","Universal combat / rally",new String[]{"Call of War","Last Line","Spirit of Aeetis","One Heart"},"Commander's Crest","High cost; deeper investment mainly for rally leads"),
        new Expert("Baldur",2,195,60,Focus.ALLIANCE_EVENTS,false,"Affinity 60","Alliance event progression",new String[]{"Blazing Sunrise","Honored Conquest","Bounty Hunter","Dawn Hymn"},"Master Negotiator","High account-growth utility"),
        new Expert("Fabian",2,195,120,Focus.FOUNDRY,false,"Skill 2 / Skill 4 rally lead","Foundry / Tundra Arms specialist",new String[]{"Salvager","Crisis Rescue","Heightened Firepower","Battle Bulwark"},"Craftsman of War","Mode specialist"),
        new Expert("Valeria",2,195,200,Focus.SVS,true,"Skills 1-2; Skill 4 rally lead","State of Power / SvS specialist",new String[]{"Well Prepared","Radiant Honor","Battle Concerto","Crushing Force"},"Conqueror's Spirit","Top SvS-priority Expert; preparation and battle-phase utility"),
        new Expert("Ronne",2,195,80,Focus.TRADE_ROUTE,false,"Affinity 40-60","Tundra Trade Route specialist",new String[]{"Cartographic Memory","Treasure Sent","Giving Back","Gold Class"},"Trade Dominion","Alliance Showdown / truck utility"),
        new Expert("Kathy",3,240,80,Focus.FROSTFIRE,false,"Affinity 50","Frostfire Mine specialist",new String[]{"Icefire Hunter","Valorous Cold","Winter Treasures","Efficient Mining"},"Child of Frost","Frostfire-specific ROI"),
        new Expert("Gareth",4,0,null,Focus.RECOVERY,false,"Verify live state","Recovery / squad durability",new String[]{"Regrouping","Gifts of Iron","Porcupine","Undefeated Will"},"Fearsome Reputation","Eligible states only; exact Sigil cost class not published, never guess")
    ));

    public static List<Expert> all(){ return EXPERTS; }
    public static boolean affinityValid(int affinity){ return affinity >= AFFINITY_MIN && affinity <= AFFINITY_MAX; }
    public static boolean skillResearchUnlocked(int affinity){ return affinity >= SKILL_RESEARCH_UNLOCK_AFFINITY; }

    /** Count the 10-level Expert Sigil milestone rolls crossed by a current -> target plan. */
    public static int sigilMilestonesCrossed(int currentAffinity,int targetAffinity){
        if(!affinityValid(currentAffinity)||!affinityValid(targetAffinity)||targetAffinity<=currentAffinity) return 0;
        return targetAffinity/SIGIL_MILESTONE_STEP-currentAffinity/SIGIL_MILESTONE_STEP;
    }

    /** Stable gift denominations. Does not pretend that Expert level == raw Affinity points. */
    public static long affinityFromGifts(long compass,long fieryHeart,long sail){
        return Math.max(0,compass)*COMPASS_AFFINITY + Math.max(0,fieryHeart)*FIERY_HEART_AFFINITY + Math.max(0,sail)*SAIL_OF_CONQUEST_AFFINITY;
    }

    /** Books gate the next skill phase after level 10 once skill XP is full. */
    public static boolean bookPhaseGateReached(int skillLevel,boolean skillXpFull){ return skillLevel >= BOOK_PHASE_GATE_LEVEL && skillXpFull; }

    public static Expert byName(String name){
        if(name==null) return null;
        for(Expert e:EXPERTS) if(e.name.equalsIgnoreCase(name)) return e;
        return null;
    }

    public static boolean isSvsPriority(Expert e){ return e != null && e.svsDirect; }

    /** Typical-day helper only; state eligibility and rollout conditions can override it. */
    public static boolean typicallyAvailableByDay(Expert e,int serverDay){ return e.typicalServerDay > 0 && serverDay >= e.typicalServerDay; }
}
