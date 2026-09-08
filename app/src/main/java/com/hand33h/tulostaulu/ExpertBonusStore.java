package com.hand33h.tulostaulu;

import android.content.Context;
import android.content.SharedPreferences;

public final class ExpertBonusStore {
    private static final String PREFS="wos_tulostaulu";
    private ExpertBonusStore(){}
    private static SharedPreferences p(Context c){return c.getSharedPreferences(PREFS,Context.MODE_PRIVATE);}
    public static double construction(Context c){return d(p(c).getString("expert_construction_speed","0"));}
    public static double research(Context c){return d(p(c).getString("expert_research_speed","0"));}
    public static double training(Context c){return d(p(c).getString("expert_training_speed","0"));}
    public static double healing(Context c){return d(p(c).getString("expert_healing_speed","0"));}
    public static double gathering(Context c){return d(p(c).getString("expert_gathering_speed","0"));}
    public static double attack(Context c){return d(p(c).getString("expert_attack","0"));}
    public static double defense(Context c){return d(p(c).getString("expert_defense","0"));}
    public static double lethality(Context c){return d(p(c).getString("expert_lethality","0"));}
    public static double health(Context c){return d(p(c).getString("expert_health","0"));}
    public static double event(Context c){return d(p(c).getString("expert_event_points","0"));}
    public static double resourceReduction(Context c){return d(p(c).getString("expert_resource_reduction","0"));}
    public static long reduce(long base,double pct){double f=Math.max(0d,1d-pct/100d);return Math.round(base*f);}
    public static long speedTime(long base,double pct){double f=1d+Math.max(0d,pct)/100d;return Math.round(base/f);}
    public static double addPct(double base,double pct){return base+pct;}
    public static String summary(Context c){
        return "Expert: Build +"+fmt(construction(c))+"% • Research +"+fmt(research(c))+"% • Training +"+fmt(training(c))+"% • ATK +"+fmt(attack(c))+"% • DEF +"+fmt(defense(c))+"% • Lethality +"+fmt(lethality(c))+"% • HP +"+fmt(health(c))+"% • Event +"+fmt(event(c))+"%";
    }
    private static String fmt(double x){return Math.abs(x-Math.rint(x))<0.0001?String.valueOf((long)Math.rint(x)):String.format(java.util.Locale.US,"%.2f",x);}
    private static double d(String s){try{return Double.parseDouble(s.replace(',','.').trim());}catch(Exception e){return 0d;}}
}
