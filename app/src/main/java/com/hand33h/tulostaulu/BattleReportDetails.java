package com.hand33h.tulostaulu;

/** Detailed WOS report data that is not safely represented by the aggregate stat bonuses. */
public final class BattleReportDetails {
    public static final int SIDE_HEROES = 3;
    public static final int SIDE_EXPERTS = 3;
    public static final int EXPERT_SKILLS = 5;

    public static final class HeroDetail {
        public String name;
        public int level;
        public int exclusiveLevel;
        public long kills;
        public int skillTriggers;
    }

    public static final class ExpertSkill {
        public int level;
        public boolean active;
        public boolean captured;
    }

    public static final class ExpertDetail {
        public String name;
        public int level;
        public final ExpertSkill[] skills = new ExpertSkill[EXPERT_SKILLS];
        public ExpertDetail() {
            for (int i = 0; i < skills.length; i++) skills[i] = new ExpertSkill();
        }
    }

    public static final class Side {
        public final HeroDetail[] heroes = new HeroDetail[SIDE_HEROES];
        public final ExpertDetail[] experts = new ExpertDetail[SIDE_EXPERTS];
        public String bonusSource;

        public Side() {
            for (int i = 0; i < heroes.length; i++) heroes[i] = new HeroDetail();
            for (int i = 0; i < experts.length; i++) experts[i] = new ExpertDetail();
        }
    }

    public final Side attacker = new Side();
    public final Side defender = new Side();

    /** Hero trigger data is intentionally separate from Stat Bonuses to avoid double counting. */
    public boolean hasBattleDetailData() {
        for (HeroDetail h : attacker.heroes) if (h.kills > 0 || h.skillTriggers > 0) return true;
        for (HeroDetail h : defender.heroes) if (h.kills > 0 || h.skillTriggers > 0) return true;
        return false;
    }
}
