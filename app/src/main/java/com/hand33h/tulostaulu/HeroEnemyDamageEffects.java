package com.hand33h.tulostaulu;

/**
 * Verified Expedition effects that modify the opposing army's total damage dealt.
 * Kept separate from ATK/DEF/HP/LETH so these effects are never mapped to the wrong stat.
 */
public final class HeroEnemyDamageEffects {
    private HeroEnemyDamageEffects() {}

    private static double v(int level,double a,double b,double c,double d,double e){
        int i=Math.max(1,Math.min(5,level));
        return new double[]{a,b,c,d,e}[i-1];
    }

    /** Percentage-point reduction to all enemy troops' damage dealt. */
    public static double damageDealtReduction(String selected,int skillLevel){
        String hero=HeroBattleData.nameOf(selected);
        if("Lumak Bokan".equals(hero)) return v(skillLevel,4,8,12,16,20); // Tactical Deception
        return 0;
    }
}
