package com.hand33h.tulostaulu;

import java.util.Locale;
import java.util.Random;

/**
 * Empirical Expedition battle engine.
 * Confirmed/public mechanics: turn-based, simultaneous resolution, front-row targeting,
 * hero skills independent from report stats, and probability/timed skills.
 * Unknown server-side constants remain calibration parameters rather than claimed facts.
 */
public final class RoundBattleEngine {
    private RoundBattleEngine() {}
    public static final int DEFAULT_MAX_TURNS=50;
    public static final double DEFAULT_PACING=0.115; // calibration parameter, not official WOS constant

    public static final class Army {
        public double troops,atk,def,hp,leth,damageDealt,damageTakenReduction,enemyDamageDealtReduction,counter;
        public Army(double troops,double atk,double def,double hp,double leth,double dealt,double reduction,double counter){
            this(troops,atk,def,hp,leth,dealt,reduction,0,counter);
        }
        public Army(double troops,double atk,double def,double hp,double leth,double dealt,double reduction,double enemyDealtReduction,double counter){
            this.troops=Math.max(0,troops);this.atk=atk;this.def=def;this.hp=hp;this.leth=leth;
            this.damageDealt=dealt;this.damageTakenReduction=clamp(reduction,0,95);
            this.enemyDamageDealtReduction=clamp(enemyDealtReduction,0,95);this.counter=counter;
        }
    }

    public static final class TimedSkill {
        public int everyTurns=0,startTurn=1,durationTurns=0;
        public double procChance=1.0,damageMultiplier=1.0,damageTakenReduction=0;
        public boolean active(int turn,Random rng){
            if(turn<startTurn)return false;if(durationTurns>0&&turn>=startTurn+durationTurns)return false;
            if(everyTurns>0&&((turn-startTurn)%everyTurns)!=0)return false;
            return rng.nextDouble()<clamp(procChance,0,1);
        }
    }

    public static final class Result {
        public int turns;public double attackerLeft,defenderLeft;public String winner,log;
    }

    public static Result fight(Army a,Army d,int maxTurns){return fight(a,d,maxTurns,null,null,1L,DEFAULT_PACING);}

    public static Result fight(Army a,Army d,int maxTurns,TimedSkill aSkill,TimedSkill dSkill,long seed,double pacing){
        Result r=new Result();StringBuilder log=new StringBuilder();Random rng=new Random(seed);int limit=Math.max(1,maxTurns);
        for(int turn=1;turn<=limit&&a.troops>0.5&&d.troops>0.5;turn++){
            double aBefore=a.troops,dBefore=d.troops;
            boolean ap=aSkill!=null&&aSkill.active(turn,rng),dp=dSkill!=null&&dSkill.active(turn,rng);
            double aMult=ap?Math.max(0,aSkill.damageMultiplier):1.0,dMult=dp?Math.max(0,dSkill.damageMultiplier):1.0;
            double aExtraRed=ap?aSkill.damageTakenReduction:0,dExtraRed=dp?dSkill.damageTakenReduction:0;
            double dmgA=turnDamage(a,d,pacing,aMult,dExtraRed),dmgD=turnDamage(d,a,pacing,dMult,aExtraRed);
            double lossD=Math.min(dBefore,Math.max(0,dmgA)),lossA=Math.min(aBefore,Math.max(0,dmgD));
            a.troops=Math.max(0,aBefore-lossA);d.troops=Math.max(0,dBefore-lossD);r.turns=turn;
            if(turn<=20||turn==limit)log.append(String.format(Locale.US,"T%d%s%s A -%,.0f | D -%,.0f | left A %,.0f / D %,.0f\n",turn,ap?" [A skill]":"",dp?" [D skill]":"",lossA,lossD,a.troops,d.troops));
        }
        r.attackerLeft=a.troops;r.defenderLeft=d.troops;
        if(a.troops<=0.5&&d.troops<=0.5)r.winner="DRAW";else if(d.troops<=0.5)r.winner="ATTACKER";else if(a.troops<=0.5)r.winner="DEFENDER";else r.winner=a.troops>=d.troops?"ATTACKER (turn limit)":"DEFENDER (turn limit)";
        r.log=log.toString().trim();return r;
    }

    private static double turnDamage(Army own,Army enemy,double pacing,double skillDamageMultiplier,double temporaryTargetReduction){
        // Empirical layer: ATK and Lethality multiply on offense; DEF and HP multiply on durability.
        double attack=Math.max(.05,1+own.atk/100.0),leth=Math.max(.05,1+own.leth/100.0);
        double defense=Math.max(.05,1+enemy.def/100.0),health=Math.max(.05,1+enemy.hp/100.0);
        double dealt=Math.max(.05,1+own.damageDealt/100.0);
        // Damage Taken and enemy Damage Dealt are separate layers; do not add them together.
        double taken=Math.max(.01,1-clamp(enemy.damageTakenReduction+temporaryTargetReduction,0,95)/100.0);
        double enemyDealtDebuff=Math.max(.01,1-enemy.enemyDamageDealtReduction/100.0);
        double counter=clamp(own.counter,.80,1.20);
        return own.troops*Math.max(.000001,pacing)*(attack*leth)/Math.sqrt(defense*health)*dealt*enemyDealtDebuff*taken*counter*skillDamageMultiplier;
    }

    public static double monteCarloWinRate(ArmyTemplate a,ArmyTemplate d,int runs,int maxTurns,TimedSkill as,TimedSkill ds,double pacing){
        int wins=0,n=Math.max(1,runs);for(int i=0;i<n;i++){Result r=fight(a.make(),d.make(),maxTurns,as,ds,0x9E3779B97F4A7C15L+i,pacing);if(r.winner.startsWith("ATTACKER"))wins++;}return 100.0*wins/n;
    }
    public static final class ArmyTemplate {
        final double troops,atk,def,hp,leth,dealt,reduction,enemyReduction,counter;
        public ArmyTemplate(Army a){troops=a.troops;atk=a.atk;def=a.def;hp=a.hp;leth=a.leth;dealt=a.damageDealt;reduction=a.damageTakenReduction;enemyReduction=a.enemyDamageDealtReduction;counter=a.counter;}
        Army make(){return new Army(troops,atk,def,hp,leth,dealt,reduction,enemyReduction,counter);}
    }
    private static double clamp(double x,double lo,double hi){return Math.max(lo,Math.min(hi,x));}
}
