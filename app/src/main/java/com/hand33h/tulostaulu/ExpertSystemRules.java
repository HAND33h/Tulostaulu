package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Dawn Academy Expert metadata used by native planners. Checked 2026-09-09. */
public final class ExpertSystemRules {
    private ExpertSystemRules() {}
    public static final String COMMUNITY_SOURCE="https://www.whiteoutsurvival-community.com/tools/wiki/events/expert-wsco.html";
    public static final String OFFICIAL_SKILL_RESEARCH_SOURCE="https://centurygames.helpshift.com/hc/en/64-whiteout-survival/faq/8418-how-do-i-unlock-expert-skill-research/";
    public static final int AFFINITY_MIN=0, AFFINITY_MAX=100, SKILLS_PER_EXPERT=4, SKILL_RESEARCH_UNLOCK_AFFINITY=100, SIGIL_MILESTONE_STEP=10;
    public static final int COMPASS_AFFINITY=10, FIERY_HEART_AFFINITY=100, SAIL_OF_CONQUEST_AFFINITY=1000;
    public enum Focus { ECONOMY, BEAR, ARENA, ALLIANCE_EVENTS, FOUNDRY, SVS, TRADE_ROUTE, FROSTFIRE, UNIVERSAL_COMBAT, RECOVERY }
    public static final class Expert {
        public final String name; public final int generation,typicalServerDay; public final Integer intimateCostClass,totalSigils,totalBooks; public final Focus focus; public final boolean svsDirect; public final String recommendedStop,role; public final String[] skills; public final String talent,note;
        Expert(String n,int g,int d,Integer c,Integer sigils,Integer books,Focus f,boolean svs,String stop,String role,String[] skills,String talent,String note){name=n;generation=g;typicalServerDay=d;intimateCostClass=c;totalSigils=sigils;totalBooks=books;focus=f;svsDirect=svs;recommendedStop=stop;this.role=role;this.skills=skills;this.talent=talent;this.note=note;}
    }
    private static final List<Expert> EXPERTS=Collections.unmodifiableList(Arrays.asList(
      new Expert("Agnes",1,150,50,275,21000,Focus.ECONOMY,false,"Affinity 70","City / daily utility",new String[]{"Efficient Recon","Optimization","Project Management","Covert Knowledge"},"Earthbreaker","Economy priority"),
      new Expert("Cyrille",1,150,50,275,21150,Focus.BEAR,false,"Affinity 60","Bear Hunt specialist",new String[]{"Entrapment","Scavenging","Weapon Master","Ursa's Bane"},"Hunter's Heart","Bear priority"),
      new Expert("Holger",1,150,80,440,81000,Focus.ARENA,false,"Affinity 50","Arena specialist",new String[]{"Arena Elite","Crowd Pleaser","Arena Star","Legacy"},"Blade Dancing","Arena priority"),
      new Expert("Romulus",1,150,360,1820,413500,Focus.UNIVERSAL_COMBAT,false,"Rally leads","Universal combat / rally",new String[]{"Call of War","Last Line","Spirit of Aeetis","One Heart"},"Commander's Crest","High cost"),
      new Expert("Baldur",2,195,60,330,63000,Focus.ALLIANCE_EVENTS,false,"Affinity 60","Alliance events",new String[]{"Blazing Sunrise","Honored Conquest","Bounty Hunter","Dawn Hymn"},"Master Negotiator","Alliance-event ROI"),
      new Expert("Fabian",2,195,120,660,156500,Focus.FOUNDRY,false,"Skill 2 / Skill 4 rally lead","Foundry / Tundra Arms",new String[]{"Salvager","Crisis Rescue","Heightened Firepower","Battle Bulwark"},"Craftsman of War","Mode specialist"),
      new Expert("Valeria",2,195,200,1100,345000,Focus.SVS,true,"SvS priority","State of Power / SvS",new String[]{"Well Prepared","Radiant Honor","Battle Concerto","Crushing Force"},"Conqueror's Spirit","Direct SvS preparation and battle value"),
      new Expert("Ronne",2,195,80,440,108000,Focus.TRADE_ROUTE,false,"Affinity 40-60","Tundra Trade Route",new String[]{"Cartographic Memory","Treasure Sent","Giving Back","Gold Class"},"Trade Dominion","Truck utility"),
      new Expert("Kathy",3,240,80,550,126000,Focus.FROSTFIRE,false,"Affinity 50","Frostfire Mine",new String[]{"Icefire Hunter","Valorous Cold","Winter Treasures","Efficient Mining"},"Child of Frost","Frostfire ROI"),
      new Expert("Gareth",4,0,null,null,null,Focus.RECOVERY,false,"Verify live state","Recovery / squad durability",new String[]{"Regrouping","Gifts of Iron","Porcupine","Undefeated Will"},"Fearsome Reputation","Eligible states only; exact totals intentionally unverified")
    ));
    public static List<Expert> all(){return EXPERTS;}
    public static Expert byName(String name){if(name==null)return null;for(Expert e:EXPERTS)if(e.name.equalsIgnoreCase(name))return e;return null;}
    public static boolean affinityValid(int x){return x>=0&&x<=100;}
    public static boolean skillResearchUnlocked(int x){return x>=100;}
    public static int sigilMilestonesCrossed(int current,int target){if(!affinityValid(current)||!affinityValid(target)||target<=current)return 0;return target/10-current/10;}
    public static long affinityFromGifts(long compass,long heart,long sail){return Math.max(0,compass)*10+Math.max(0,heart)*100+Math.max(0,sail)*1000;}
    public static boolean isSvsPriority(Expert e){return e!=null&&e.svsDirect;}
    public static boolean hasVerifiedMaxTotals(Expert e){return e!=null&&e.totalSigils!=null&&e.totalBooks!=null;}
    public static boolean typicallyAvailableByDay(Expert e,int day){return e!=null&&e.typicalServerDay>0&&day>=e.typicalServerDay;}
    /** Planner ranking: direct SvS value first, then verified progression data. */
    public static int svsPlannerPriority(Expert e){if(e==null)return 0;if(e.svsDirect)return 100;if(e.focus==Focus.UNIVERSAL_COMBAT)return 70;if(e.focus==Focus.BEAR||e.focus==Focus.ALLIANCE_EVENTS)return 50;return 20;}
}
