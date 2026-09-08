package com.hand33h.tulostaulu;

import java.util.Locale;

/**
 * Turn-based Expedition battle approximation.
 * Both sides attack simultaneously each turn. Survivors carry into the next turn.
 * Damage reduction is multiplicative and kept separate from DEF/HP.
 * This engine intentionally does not claim Century Games' private damage formula.
 */
public final class RoundBattleEngine {
    private RoundBattleEngine() {}

    public static final int DEFAULT_MAX_TURNS = 50;

    public static final class Army {
        public double troops, atk, def, hp, leth, damageDealt, damageTakenReduction, counter;
        public Army(double troops,double atk,double def,double hp,double leth,double dealt,double reduction,double counter){
            this.troops=Math.max(0,troops);this.atk=atk;this.def=def;this.hp=hp;this.leth=leth;
            this.damageDealt=dealt;this.damageTakenReduction=clamp(reduction,0,95);this.counter=counter;
        }
    }

    public static final class Result {
        public int turns; public double attackerLeft,defenderLeft; public String winner; public String log;
    }

    public static Result fight(Army a,Army d,int maxTurns){
        Result r=new Result();StringBuilder log=new StringBuilder();int limit=Math.max(1,maxTurns);
        for(int turn=1;turn<=limit && a.troops>0.5 && d.troops>0.5;turn++){
            double aBefore=a.troops,dBefore=d.troops;
            double dmgA=turnDamage(a,d,turn),dmgD=turnDamage(d,a,turn);
            double lossD=Math.min(dBefore,damageToTroops(dmgA,d));
            double lossA=Math.min(aBefore,damageToTroops(dmgD,a));
            // Simultaneous resolution: both losses are based on start-of-turn troop counts.
            a.troops=Math.max(0,aBefore-lossA);d.troops=Math.max(0,dBefore-lossD);r.turns=turn;
            if(turn<=12 || turn==limit)log.append(String.format(Locale.US,"T%d A -%,.0f | D -%,.0f | left A %,.0f / D %,.0f\n",turn,lossA,lossD,a.troops,d.troops));
        }
        r.attackerLeft=a.troops;r.defenderLeft=d.troops;
        if(a.troops<=0.5&&d.troops<=0.5)r.winner="DRAW";else if(d.troops<=0.5)r.winner="ATTACKER";else if(a.troops<=0.5)r.winner="DEFENDER";else r.winner=a.troops>=d.troops?"ATTACKER (turn limit)":"DEFENDER (turn limit)";
        r.log=log.toString().trim();return r;
    }

    private static double turnDamage(Army own,Army enemy,int turn){
        double attack=Math.max(0.05,1+own.atk/100.0),leth=Math.max(0.05,1+own.leth/100.0);
        double defense=Math.max(0.05,1+enemy.def/100.0),health=Math.max(0.05,1+enemy.hp/100.0);
        double dealt=Math.max(0.05,1+own.damageDealt/100.0);
        double reduction=Math.max(0.05,1-enemy.damageTakenReduction/100.0);
        double counter=clamp(own.counter,0.80,1.20);
        // Calibrated pacing term keeps the public approximation multi-turn; it is not asserted as the game's hidden constant.
        final double pacing=0.115;
        return own.troops*pacing*(attack*leth)/(Math.sqrt(defense*health))*dealt*reduction*counter;
    }

    private static double damageToTroops(double damage,Army target){
        // HP is already represented in turnDamage; this conversion keeps casualties bounded and iterative.
        return Math.max(0,damage);
    }

    private static double clamp(double x,double lo,double hi){return Math.max(lo,Math.min(hi,x));}
}
