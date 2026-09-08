package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.*;

import java.text.NumberFormat;
import java.util.*;

public class NativeCalculatorActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private boolean english;
    private final List<Row> rows=new ArrayList<>();
    private EditText current,target;
    private TextView result;
    private String mode;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        english="en".equals(getSharedPreferences(PREFS,MODE_PRIVATE).getString("lang","fi"));
        mode=getIntent().getStringExtra("mode"); if(mode==null) mode="chief_gear";

        ScrollView sc=new ScrollView(this);
        LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(18),dp(20),dp(18),dp(40)); root.setBackgroundColor(Color.rgb(7,23,39)); sc.addView(root);

        LinearLayout hero=box(Color.rgb(13,48,76),22); hero.setPadding(dp(20),dp(20),dp(20),dp(20));
        hero.addView(label(icon()+"  "+title(),25,true,Color.WHITE));
        hero.addView(label(tr("Natiivi Bunny King -laskuri • ei ulkoista verkkosivua","Native Bunny King calculator • no external website"),12,false,Color.rgb(202,224,238)));
        root.addView(hero,mp(0,0,0,16));

        LinearLayout levels=box(Color.rgb(18,42,61),16); levels.setPadding(dp(14),dp(14),dp(14),dp(14));
        levels.addView(label(tr("TASOT / TAVOITE","LEVELS / TARGET"),12,true,Color.rgb(119,205,255)));
        current=input(tr("Nykyinen taso / vaihe","Current level / stage"),false); target=input(tr("Tavoitetaso / vaihe","Target level / stage"),false);
        levels.addView(current,mp(0,8,0,6)); levels.addView(target); root.addView(levels,mp(0,0,0,14));

        root.addView(label(tr("MATERIAALIT","MATERIALS"),13,true,Color.rgb(119,205,255)),mp(0,0,0,7));
        for(String material:materials()) addMaterial(root,material);

        Button calc=button("🧮  "+tr("LASKE PUUTTUVAT","CALCULATE SHORTAGES")); root.addView(calc,mp(0,6,0,12));
        result=label(tr("Syötä tavoitteen kokonaiskulut ja mitä sinulla on nyt. Laskuri näyttää puuttuvan määrän.","Enter total target costs and what you currently own. The calculator shows the shortage."),14,false,Color.rgb(215,230,240));
        LinearLayout out=box(Color.rgb(18,48,70),16); out.setPadding(dp(15),dp(15),dp(15),dp(15)); out.addView(result); root.addView(out);

        root.addView(label(tr("Seuraava vaihe: tasokohtaiset WOS-kustannustaulukot voidaan lisätä tähän, jolloin Current → Target täyttää tavoitekulut automaattisesti.","Next step: WOS level-cost tables can be added here so Current → Target fills target costs automatically."),11,false,Color.rgb(130,160,180)),mp(0,14,0,0));
        calc.setOnClickListener(v->calculate()); setContentView(sc);
    }

    private void addMaterial(LinearLayout root,String name){
        LinearLayout card=box(Color.rgb(18,48,70),14); card.setPadding(dp(12),dp(11),dp(12),dp(11));
        card.addView(label(name,15,true,Color.WHITE));
        LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.HORIZONTAL);
        EditText need=input(tr("Tarve","Need"),true), have=input(tr("Omistat","Owned"),true);
        row.addView(need,new LinearLayout.LayoutParams(0,dp(52),1)); LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(0,dp(52),1); hp.setMargins(dp(8),0,0,0); row.addView(have,hp); card.addView(row,mp(0,8,0,0));
        rows.add(new Row(name,need,have)); root.addView(card,mp(0,0,0,8));
    }

    private void calculate(){
        StringBuilder sb=new StringBuilder();
        String c=current.getText().toString().trim(), t=target.getText().toString().trim();
        if(!c.isEmpty()||!t.isEmpty()) sb.append(tr("Taso: ","Level: ")).append(c.isEmpty()?"?":c).append(" → ").append(t.isEmpty()?"?":t).append("\n\n");
        long totalNeed=0,totalHave=0,totalMissing=0; boolean any=false;
        NumberFormat nf=NumberFormat.getIntegerInstance(Locale.US);
        for(Row r:rows){ long need=parse(r.need),have=parse(r.have),missing=Math.max(0,need-have); if(need>0||have>0) any=true; totalNeed+=need; totalHave+=have; totalMissing+=missing;
            sb.append(r.name).append("\n  ").append(tr("Tarve ","Need ")).append(nf.format(need)).append("  •  ").append(tr("Omistat ","Owned ")).append(nf.format(have)).append("  •  ").append(tr("Puuttuu ","Missing ")).append(nf.format(missing)).append("\n");
        }
        if(!any){Toast.makeText(this,tr("Syötä ainakin yksi materiaalimäärä.","Enter at least one material amount."),Toast.LENGTH_LONG).show();return;}
        sb.append("\n").append(tr("YHTEENVETO","SUMMARY")).append("\n").append(tr("Tarve yhteensä: ","Total need: ")).append(nf.format(totalNeed)).append("\n").append(tr("Omistat yhteensä: ","Total owned: ")).append(nf.format(totalHave)).append("\n").append(tr("Puuttuu yhteensä: ","Total missing: ")).append(nf.format(totalMissing));
        result.setText(sb.toString());
    }

    private long parse(EditText e){try{String s=e.getText().toString().replaceAll("[^0-9]","");return s.isEmpty()?0:Long.parseLong(s);}catch(Exception x){return 0;}}
    private String[] materials(){
        switch(mode){
            case "chief_gear": return new String[]{"Hardened Alloy","Polishing Solution","Design Plans","Lunar Amber"};
            case "charms": return new String[]{"Charm Guides","Charm Designs","Charm Secrets"};
            case "fire_crystals": return new String[]{"Fire Crystals","Refined Fire Crystals","Steel","Speedups (min)"};
            case "buildings": return new String[]{"Meat","Wood","Coal","Iron","Fire Crystals","Speedups (min)"};
            case "research": return new String[]{"Meat","Wood","Coal","Iron","Steel","Research Speedups (min)"};
            case "war_academy": return new String[]{"Fire Crystal Shards","Steel","Research Speedups (min)"};
            case "hero_gear": return new String[]{"Hero Gear XP","Essence Stones","Mythril","SvS points"};
            case "pets": return new String[]{"Pet Food","Taming Marks","Refinement materials","Advancement materials"};
            case "troops": return new String[]{"Meat","Wood","Coal","Iron","Training Speedups (min)","Troop count"};
            case "experts": return new String[]{"Expert XP","Expert Manuals","Expert materials"};
            case "svs": return new String[]{"Chief Gear materials","Charm materials","Fire Crystals","Hero Gear materials","Pet materials","Speedups (min)","Expected SvS points"};
            case "bear": return new String[]{"Infantry","Lancers","Marksmen","March capacity","Rally capacity"};
            case "koi": return new String[]{"Speedups (min)","Fire Crystals","Chief Gear materials","Charm materials","Expected points"};
            case "chests": return new String[]{"Chest count","Expected primary material","Expected secondary material"};
            default: return new String[]{"Resource A","Resource B","Resource C","Speedups (min)"};
        }
    }
    private String title(){switch(mode){case"chief_gear":return"Chief Gear";case"charms":return"Chief Charms";case"fire_crystals":return"Fire Crystals";case"buildings":return"Buildings / Furnace";case"research":return"Research";case"war_academy":return"War Academy";case"hero_gear":return"Hero Gear";case"pets":return"Pets";case"troops":return"Troops";case"experts":return"Experts";case"svs":return"SvS Prep";case"bear":return"Bear Trap / Rally";case"koi":return"KOI";case"chests":return"Chests";default:return"WOS Calculator";}}
    private String icon(){switch(mode){case"chief_gear":return"🛡️";case"charms":return"💠";case"fire_crystals":return"🔥";case"buildings":return"🏗️";case"research":return"🧠";case"war_academy":return"🎓";case"hero_gear":return"🦸";case"pets":return"🐾";case"troops":return"⚔️";case"experts":return"👨‍🔬";case"svs":return"⚔️";case"bear":return"🐻";case"koi":return"🧊";case"chests":return"📦";default:return"🧮";}}
    private String tr(String fi,String en){return english?en:fi;}
    private EditText input(String h,boolean num){EditText e=new EditText(this);e.setHint(h);e.setHintTextColor(Color.rgb(135,158,174));e.setTextColor(Color.WHITE);e.setSingleLine();e.setPadding(dp(12),dp(10),dp(12),dp(10));e.setInputType(num?InputType.TYPE_CLASS_NUMBER:InputType.TYPE_CLASS_TEXT);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(9,29,44));g.setCornerRadius(dp(11));e.setBackground(g);return e;}
    private LinearLayout box(int c,int r){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);GradientDrawable g=new GradientDrawable();g.setColor(c);g.setCornerRadius(dp(r));g.setStroke(dp(1),Color.rgb(42,86,112));l.setBackground(g);return l;}
    private TextView label(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{Color.rgb(20,126,196),Color.rgb(43,170,213)});g.setCornerRadius(dp(14));b.setBackground(g);return b;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);} private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
    static class Row{String name;EditText need,have;Row(String n,EditText a,EditText b){name=n;need=a;have=b;}}
}
