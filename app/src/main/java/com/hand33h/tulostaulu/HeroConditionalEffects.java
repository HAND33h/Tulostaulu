package com.hand33h.tulostaulu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Structured metadata for verified Expedition effects that must not be flattened into
 * a permanent aggregate bonus. This is intentionally separate from HeroBattleData.Effect.
 */
public final class HeroConditionalEffects {
    private HeroConditionalEffects() {}

    public enum Trigger { EVERY_N_TURNS, EVERY_N_ATTACKS, CHANCE_ON_ATTACK, PASSIVE }
    public enum Target { ALL_TROOPS, INFANTRY, LANCER, MARKSMAN, ENEMY_ALL, ENEMY_INFANTRY, ENEMY_LANCER, ENEMY_MARKSMAN }
    public enum Modifier { EXTRA_ATTACK_DAMAGE, DAMAGE_DEALT, DAMAGE_TAKEN, DAMAGE_TAKEN_REDUCTION, DAMAGE_DEALT_REDUCTION, CRIT_RATE }

    public static final class Effect {
        public final String hero, skill;
        public final Trigger trigger;
        public final Target target;
        public final Modifier modifier;
        public final double value, chance;
        public final int interval, durationTurns;
        Effect(String hero,String skill,Trigger trigger,Target target,Modifier modifier,double value,int interval,double chance,int durationTurns){this.hero=hero;this.skill=skill;this.trigger=trigger;this.target=target;this.modifier=modifier;this.value=value;this.interval=interval;this.chance=chance;this.durationTurns=durationTurns;}
    }

    public static List<Effect> forHero(String hero,int skillLevel){
        String name=HeroBattleData.nameOf(hero);int l=Math.max(1,Math.min(5,skillLevel));List<Effect> out=new ArrayList<>();
        if("Wayne".equals(name)){
            out.add(e(name,"Thunder Strike",Trigger.EVERY_N_TURNS,Target.ALL_TROOPS,Modifier.EXTRA_ATTACK_DAMAGE,v(l,20,40,60,80,100),4,1,0));
            out.add(e(name,"Roundabout Hit",Trigger.EVERY_N_ATTACKS,Target.ENEMY_LANCER,Modifier.EXTRA_ATTACK_DAMAGE,v(l,8,16,24,32,40),2,1,0));
            out.add(e(name,"Roundabout Hit",Trigger.EVERY_N_ATTACKS,Target.ENEMY_MARKSMAN,Modifier.EXTRA_ATTACK_DAMAGE,v(l,4,8,12,16,20),2,1,0));
            out.add(e(name,"Fleet",Trigger.PASSIVE,Target.ALL_TROOPS,Modifier.CRIT_RATE,v(l,5,10,15,20,25),0,1,0));
        }else if("Flora".equals(name)){
            out.add(e(name,"Enmiring Vines",Trigger.CHANCE_ON_ATTACK,Target.ENEMY_ALL,Modifier.DAMAGE_TAKEN,v(l,10,20,30,40,50),0,.50,0));
            out.add(e(name,"Plantage",Trigger.PASSIVE,Target.INFANTRY,Modifier.DAMAGE_TAKEN_REDUCTION,v(l,5,10,15,20,25),0,1,0));
            out.add(e(name,"Plantage",Trigger.PASSIVE,Target.LANCER,Modifier.EXTRA_ATTACK_DAMAGE,v(l,5,10,15,20,25),0,1,0));
            out.add(e(name,"Confusion Pollen",Trigger.EVERY_N_TURNS,Target.ENEMY_INFANTRY,Modifier.DAMAGE_TAKEN,v(l,6,12,18,24,30),4,1,2));
            out.add(e(name,"Confusion Pollen",Trigger.EVERY_N_TURNS,Target.ENEMY_MARKSMAN,Modifier.DAMAGE_DEALT_REDUCTION,v(l,6,12,18,24,30),4,1,2));
        }
        return Collections.unmodifiableList(out);
    }

    public static boolean hasStructuredEffects(String hero){return !forHero(hero,5).isEmpty();}
    private static Effect e(String h,String s,Trigger tr,Target ta,Modifier m,double v,int i,double c,int d){return new Effect(h,s,tr,ta,m,v,i,c,d);}
    private static double v(int l,double a,double b,double c,double d,double e){return new double[]{a,b,c,d,e}[l-1];}
}
