package com.hand33h.tulostaulu;

/**
 * Additional verified Expedition effects that can be represented safely by the
 * aggregate Battle Simulator. Conditional/proc/turn/target effects are described
 * in notes rather than converted into invented averages.
 */
public final class HeroBattleDataExpanded {
    private HeroBattleDataExpanded() {}

    private static double v(int level,double a,double b,double c,double d,double e){
        int i=Math.max(1,Math.min(5,level));return new double[]{a,b,c,d,e}[i-1];
    }

    public static boolean apply(HeroBattleData.Effect x,String n,int l,boolean defenderSide){
        if("Seigel".equals(n)){
            x.hp+=v(l,5,10,15,20,25);
            x.note="Armor of Night Health applied. Night's Defense troop-type Attack penalties/reductions and Vanguard damage reductions remain troop-specific.";return true;
        }
        if("Ursar".equals(n)){
            x.enemyAtk+=v(l,5,10,15,20,25);
            x.note="Forest Spores enemy Attack reduction applied. Horn of the Ancients and Poison Tips are turn/troop specific.";return true;
        }
        if("Aisling".equals(n)){
            x.damageDealt+=v(l,4,8,12,16,20);
            x.note="Songs of the Ancestors damage dealt applied. Rock Storm and Forest Fury are timed Marksman effects.";return true;
        }
        if("Viveca".equals(n)){
            x.atk+=v(l,5,10,15,20,25);
            x.note="Nightfall Legion Attack applied. Shadow World proc and Children of the Mist troop-specific effects remain conditional.";return true;
        }
        if("Dominic".equals(n)){
            x.damageDealt+=v(l,4,8,12,16,20);
            x.note="Mystic Mechanism all-troop damage dealt applied. Spiky Assault and Mirror Maze are troop/target specific.";return true;
        }
        if("Cara".equals(n)){
            x.enemyLeth+=v(l,4,8,12,16,20);
            x.note="Smoky Encounter enemy Lethality reduction applied. Mech Pet is normal-attack-only; Witch's Wrath is target/timing specific.";return true;
        }
        if("Vulcanus".equals(n)){
            x.enemyAtk+=v(l,4,8,12,16,20);
            x.note="Raging Storm enemy Attack reduction applied. Breaker Steel/True Strike are attack-cycle effects.";return true;
        }
        if("Karol".equals(n)){
            x.atk+=v(l,3,6,9,12,15);x.def+=v(l,2,4,6,8,10);
            x.note="Standard of Ages Attack/Defense applied. In the Wings damage reduction and Shieldbreaker target bonuses are not folded into core stats.";return true;
        }
        if("Ligeia".equals(n)){
            x.enemyDef+=v(l,5,10,15,20,25);
            x.note="Nerf Poison enemy Defense reduction applied. Corrosion/Toxic Tip are turn/target effects.";return true;
        }
        if("Lloyd".equals(n)){
            x.enemyLeth+=v(l,4,8,12,16,20);
            x.note="Bird Invasion enemy Lethality reduction applied. Iceflare Bomb and Ingenious Mastery are timed/proc effects.";return true;
        }
        if("Rufus".equals(n)){
            x.atk+=v(l,5,10,15,20,25);
            x.note="Inferno Regiment Attack applied. Armor Crush is target-specific and Wrathful Quake is a 20% proc.";return true;
        }
        if("Freya".equals(n)){
            x.enemyAtk+=v(l,4,8,12,16,20);
            x.note="Fog of War enemy Attack reduction applied. Blood Moon Scythe and Night's Vengeance are proc/troop specific.";return true;
        }
        if("Blanchette".equals(n)){
            x.leth+=v(l,5,10,15,20,25);
            x.note="Armed to the Teeth Lethality applied. Blood Hunter and Crimson Sniper are attack-cycle/target effects.";return true;
        }
        if("Magnus".equals(n)){
            x.atk+=v(l,5,10,15,20,25);
            x.note="Rapacious Attack applied. Iron Phalanx is a 40% Infantry proc; Iceman remains conditional.";return true;
        }
        if("Fred".equals(n)){
            x.enemyLeth+=v(l,4,8,12,16,20);
            x.note="Hydraulic Suppression enemy Lethality reduction applied. Acidification/Floodbringer are target/turn specific.";return true;
        }
        if("Xura".equals(n)){
            x.damageTakenReduction+=v(l,4,8,12,16,20);
            x.note="Fungal Fog global damage-taken reduction recorded. Piercing Arrow and Unorthodoxy are Marksman/target specific.";return true;
        }
        if("Sonya".equals(n)){
            x.damageDealt+=v(l,4,8,12,16,20);
            x.note="Treasure Hunter all-troop damage applied. Bounty Temptation/Torrential Impact are timed Lancer effects.";return true;
        }
        if("Hendrik".equals(n)){
            x.enemyDef+=v(l,5,10,15,20,25);
            x.note="Worm's Ravage enemy Defense reduction applied. Armor of Barnacles/Dagon's Heir are timed effects.";return true;
        }
        if("Wu Ming".equals(n)){
            x.damageDealt+=v(l,4,8,12,16,20);
            x.note="Crescent Uplift all-troop damage dealt applied. Shadow's Evasion is Infantry-specific; Elemental Resonance is skill-damage-only.";return true;
        }
        if("Gwen".equals(n)){
            x.note="Eagle Vision increases target damage taken; Air Dominance and Blastmaster are attack-cycle effects. Kept out of aggregate score to avoid double counting target vulnerability.";return true;
        }
        if("Lynn".equals(n)){
            x.note="Melancholic Ballad reduces enemy damage dealt 4–20%; Song of Lion is a 40% proc and Oonai Cadenza stacks. Effects kept conditional in aggregate score.";return true;
        }
        if("Logan".equals(n)){
            x.enemyAtk+=v(l,4,8,12,16,20);x.hp+=v(l,5,10,15,20,25);x.damageTakenReduction+=v(l,4,8,12,16,20);
            x.note="Lion's Might enemy Attack reduction, Leader Inspiration Health and Lion Intimidation damage reduction recorded.";return true;
        }
        if("Greg".equals(n)){
            x.hp+=v(l,5,10,15,20,25);
            x.note="Law and Order Health applied. Sword of Justice and Deterrence of Law are 20% proc effects.";return true;
        }
        if("Philly".equals(n)){
            x.atk+=v(l,3,6,9,12,15);x.def+=v(l,2,4,6,8,10);
            x.note="Vigor Tactics Attack/Defense applied. Dosage Boost and Energizing Shot are proc effects.";return true;
        }
        if("Alonso".equals(n)){
            x.note="Onslaught (40%), Iron Strength (20%) and Poison Harpoon (50%) are all proc-based; no invented average is added.";return true;
        }
        if("Reina".equals(n)){
            x.note="Assassin's Instinct is normal-attack damage only; Swift Jive is dodge and Shadow Blade is a 25% Lancer proc. Kept conditional.";return true;
        }
        if("Renee".equals(n)){
            x.note="Nightmare Trace, Dreamcatcher and Dreamslice depend on Dream Marks/turn timing; kept conditional.";return true;
        }
        if("Gordon".equals(n)){
            x.note="Venom Infusion, Chemical Terror and Toxic Release are turn/troop-specific; kept conditional.";return true;
        }
        if("Mia".equals(n)){
            x.note="Bad Luck Streak and Lucky Charm are 50% procs; Ritual Deciphering is a 40% proc. No invented average added.";return true;
        }
        return false;
    }
}
