package com.hand33h.tulostaulu;

/** Additional verified Expedition effects used by the aggregate Battle Simulator. */
public final class HeroBattleDataExpanded {
    private HeroBattleDataExpanded() {}
    private static double v(int l,double a,double b,double c,double d,double e){int i=Math.max(1,Math.min(5,l));return new double[]{a,b,c,d,e}[i-1];}
    public static boolean apply(HeroBattleData.Effect x,String n,int l,boolean defenderSide){
        if("Sergey".equals(n)){x.damageTakenReduction+=v(l,4,8,12,16,20);x.enemyAtk+=v(l,4,8,12,16,20);x.note="Defenders' Edge damage-taken reduction + Weaken enemy Attack reduction applied.";return true;}
        if("Jessie".equals(n)){x.damageDealt+=v(l,5,10,15,20,25);x.damageTakenReduction+=v(l,4,8,12,16,20);x.note="Stand of Arms damage dealt + Bulwarks damage-taken reduction applied.";return true;}
        if("Bahiti".equals(n)){x.damageTakenReduction+=v(l,4,8,12,16,20);x.note="Sixth Sense damage-taken reduction applied. Proc effects are not averaged.";return true;}
        if("Jasser".equals(n)){x.damageDealt+=v(l,5,10,15,20,25);x.note="Tactical Genius damage dealt applied; Research Speed excluded.";return true;}
        if("Ling Xue".equals(n)){x.enemyAtk+=v(l,4,8,12,16,20);x.note="Fearsome Aura enemy Attack reduction applied; Training Speed excluded.";return true;}
        if("Lumak Bokan".equals(n)){x.enemyDamageDealtReduction+=v(l,4,8,12,16,20);x.note="Tactical Deception enemy Damage Dealt reduction applied; hunting march speed excluded.";return true;}
        if("Gina".equals(n)){x.note="Wilderness stamina/march utility only; no direct troop combat modifier.";return true;}
        if("Charlie".equals(n)||"Cloris".equals(n)||"Eugene".equals(n)||"Smith".equals(n)){x.note="Growth/resource utility excluded from battle score.";return true;}
        if("Seo-yoon".equals(n)){x.atk+=v(l,5,10,15,20,25);x.note="Rallying Beat all-troop Attack applied (5/10/15/20/25%); Soothing Dance Healing Speed excluded from combat.";return true;}
        if("Patrick".equals(n)){x.hp+=v(l,5,10,15,20,25);x.atk+=v(l,5,10,15,20,25);x.note="Super Nutrients Health + Caloric Booster Attack applied.";return true;}
        if("Seigel".equals(n)){x.hp+=v(l,5,10,15,20,25);x.note="Armor of Night Health applied; conditional effects excluded.";return true;}
        if("Ursar".equals(n)){x.enemyAtk+=v(l,5,10,15,20,25);x.note="Forest Spores enemy Attack reduction applied.";return true;}
        if("Aisling".equals(n)){x.damageDealt+=v(l,4,8,12,16,20);x.note="Songs of the Ancestors damage dealt applied.";return true;}
        if("Viveca".equals(n)){x.atk+=v(l,5,10,15,20,25);x.note="Nightfall Legion Attack applied.";return true;}
        if("Dominic".equals(n)){x.damageDealt+=v(l,4,8,12,16,20);x.note="Mystic Mechanism damage dealt applied.";return true;}
        if("Cara".equals(n)){x.enemyLeth+=v(l,4,8,12,16,20);x.note="Smoky Encounter enemy Lethality reduction applied.";return true;}
        if("Vulcanus".equals(n)){x.enemyAtk+=v(l,4,8,12,16,20);x.note="Raging Storm enemy Attack reduction applied.";return true;}
        if("Karol".equals(n)){x.atk+=v(l,3,6,9,12,15);x.def+=v(l,2,4,6,8,10);x.note="Standard of Ages Attack/Defense applied.";return true;}
        if("Ligeia".equals(n)){x.enemyDef+=v(l,5,10,15,20,25);x.note="Nerf Poison enemy Defense reduction applied.";return true;}
        if("Lloyd".equals(n)){x.enemyLeth+=v(l,4,8,12,16,20);x.note="Bird Invasion enemy Lethality reduction applied.";return true;}
        if("Rufus".equals(n)){x.atk+=v(l,5,10,15,20,25);x.note="Inferno Regiment Attack applied.";return true;}
        if("Freya".equals(n)){x.enemyAtk+=v(l,4,8,12,16,20);x.note="Fog of War enemy Attack reduction applied.";return true;}
        if("Blanchette".equals(n)){x.leth+=v(l,5,10,15,20,25);x.note="Armed to the Teeth Lethality applied.";return true;}
        if("Magnus".equals(n)){x.atk+=v(l,5,10,15,20,25);x.note="Rapacious Attack applied.";return true;}
        if("Fred".equals(n)){x.enemyLeth+=v(l,4,8,12,16,20);x.note="Hydraulic Suppression enemy Lethality reduction applied.";return true;}
        if("Xura".equals(n)){x.damageTakenReduction+=v(l,4,8,12,16,20);x.note="Fungal Fog damage-taken reduction recorded.";return true;}
        if("Sonya".equals(n)){x.damageDealt+=v(l,4,8,12,16,20);x.note="Treasure Hunter damage dealt applied.";return true;}
        if("Hendrik".equals(n)){x.enemyDef+=v(l,5,10,15,20,25);x.note="Worm's Ravage enemy Defense reduction applied.";return true;}
        if("Wu Ming".equals(n)){x.damageDealt+=v(l,4,8,12,16,20);x.note="Crescent Uplift damage dealt applied.";return true;}
        if("Gwen".equals(n)||"Lynn".equals(n)||"Alonso".equals(n)||"Reina".equals(n)||"Renee".equals(n)||"Gordon".equals(n)||"Mia".equals(n)){x.note="Conditional/proc Expedition effects retained without invented average.";return true;}
        if("Logan".equals(n)){x.enemyAtk+=v(l,4,8,12,16,20);x.hp+=v(l,5,10,15,20,25);x.damageTakenReduction+=v(l,4,8,12,16,20);x.note="Lion's Might, Leader Inspiration and Lion Intimidation recorded.";return true;}
        if("Greg".equals(n)){x.hp+=v(l,5,10,15,20,25);x.note="Law and Order Health applied.";return true;}
        if("Philly".equals(n)){x.atk+=v(l,3,6,9,12,15);x.def+=v(l,2,4,6,8,10);x.note="Vigor Tactics Attack/Defense applied.";return true;}
        return false;
    }
}
