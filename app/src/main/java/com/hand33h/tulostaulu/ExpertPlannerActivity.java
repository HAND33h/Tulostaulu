package com.hand33h.tulostaulu;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.*;
import java.util.*;

/** Native Expert planner. Requirements are derived from verified Expert metadata;
 * the user selects state only. No manual "required materials" fields. */
public class ExpertPlannerActivity extends Activity {
    private boolean en;
    private Spinner expert,current,target;
    private TextView result;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b); en="en".equals(getSharedPreferences("wos_tulostaulu",MODE_PRIVATE).getString("lang","fi"));
        ScrollView sc=new ScrollView(this); LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(18),dp(20),dp(18),dp(40)); root.setBackgroundColor(Color.rgb(7,23,39)); sc.addView(root);
        root.addView(t("👨‍🔬 "+tr("EXPERT UPGRADE PLANNER","EXPERT UPGRADE PLANNER"),25,true));
        root.addView(t(tr("SvS ensin • valitse vain nykytila ja tavoite — Tulostaulu johtaa vaatimukset datasta.","SvS first • select current state and target — Tulostaulu derives requirements from data."),13,false));
        List<ExpertSystemRules.Expert> data=ExpertSystemRules.all(); String[] names=new String[data.size()]; for(int i=0;i<data.size();i++) names[i]=(data.get(i).svsDirect?"🏆 ":"")+data.get(i).name;
        expert=spin(names); current=spin(levels()); target=spin(levels()); target.setSelection(100);
        root.addView(t(tr("Expert","Expert"),13,true)); root.addView(expert); root.addView(t(tr("Nykyinen Affinity","Current Affinity"),13,true)); root.addView(current); root.addView(t(tr("Tavoite Affinity","Target Affinity"),13,true)); root.addView(target);
        Button calc=new Button(this); calc.setText(tr("LASKE AUTOMAATTISESTI","CALCULATE AUTOMATICALLY")); calc.setAllCaps(false); root.addView(calc);
        result=t("",14,false); result.setPadding(0,dp(16),0,0); root.addView(result); calc.setOnClickListener(v->calculate(data)); calculate(data); setContentView(sc);
    }

    private void calculate(List<ExpertSystemRules.Expert> data){
        ExpertSystemRules.Expert e=data.get(expert.getSelectedItemPosition()); int a=current.getSelectedItemPosition(), z=target.getSelectedItemPosition();
        if(z<a){result.setText(tr("Tavoitteen pitää olla vähintään nykyinen Affinity.","Target must be at least current Affinity."));return;}
        int milestones=ExpertSystemRules.sigilMilestonesCrossed(a,z);
        StringBuilder s=new StringBuilder();
        s.append(e.name).append(" • Gen ").append(e.generation).append("\n");
        if(e.svsDirect) s.append(tr("🏆 SVS: KORKEA PRIORITEETTI","🏆 SVS: HIGH PRIORITY")).append("\n");
        s.append(tr("Rooli: ","Role: ")).append(e.role).append("\n");
        s.append(tr("Suositus: ","Recommendation: ")).append(e.recommendedStop).append("\n\n");
        s.append("Affinity ").append(a).append(" → ").append(z).append("\n");
        s.append(tr("Sigil-milestoneja ylitetään: ","Sigil milestones crossed: ")).append(milestones).append(" (10/20/…/100)\n");
        if(z>=100 && a<100) s.append(tr("🔓 Affinity 100: Expert Skill Research avautuu.\n","🔓 Affinity 100: Expert Skill Research unlocks.\n"));
        if(e.intimateCostClass!=null) s.append(tr("Varmennettu investment cost class: ","Verified investment cost class: ")).append(e.intimateCostClass).append("\n"); else s.append(tr("Kustannusta ei arvata — ei varmennettua taulukkoa.\n","Cost not guessed — no verified table.\n"));
        s.append("\n").append(tr("Skillit / vaikutus:\n","Skills / effects:\n")); for(int i=0;i<e.skills.length;i++) s.append(i+1).append(") ").append(e.skills[i]).append("\n"); s.append("Talent: ").append(e.talent).append("\n");
        if(e.svsDirect) s.append("\n").append(tr("SVS-ohje: priorisoi Preparation-pisteitä kasvattava kehitys ennen tavallista power-nousua; battle-skillit erotellaan prep-hyödystä.","SVS guidance: prioritize Preparation scoring benefit before generic power; battle skills are kept separate from prep value."));
        s.append("\n\n").append(tr("Affinity-lahjat: Compass +10 • Fiery Heart +100 • Sail of Conquest +1000. Books of Knowledge kuuluvat skill-progressioniin, eivät Affinity-tason geneeriseen materiaalipottiin.","Affinity gifts: Compass +10 • Fiery Heart +100 • Sail of Conquest +1000. Books of Knowledge belong to skill progression, not a generic Affinity material pool."));
        result.setText(s.toString());
    }
    private String[] levels(){String[] x=new String[101];for(int i=0;i<=100;i++)x[i]=String.valueOf(i);return x;}
    private Spinner spin(String[] a){Spinner s=new Spinner(this);s.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,a));return s;}
    private TextView t(String x,int size,boolean bold){TextView v=new TextView(this);v.setText(x);v.setTextSize(size);v.setTextColor(Color.WHITE);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);v.setPadding(0,dp(5),0,dp(8));return v;}
    private String tr(String fi,String eng){return en?eng:fi;} private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}
}
