package com.hand33h.tulostaulu;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.widget.*;
import java.text.NumberFormat;
import java.util.Locale;

public class WarAcademyActivity extends Activity {
    private boolean en;
    private EditText ownedRfc, ownedShard, ownedSteel, ownedMeat, ownedWood, ownedCoal, ownedIron;
    private TextView result;

    // Verified public T12 aggregate totals per troop type from WoSTools live-game tables (Apr 27 2026 update).
    private static final long[] T12_UNLOCK={440,3080,1624000,0,0,0,0}; // RFC, shards, steel, meat, wood, coal, iron
    private static final long[] T12_MAX={3896,67944,17775750,570110000,570110000,107850000,28500000};

    @Override protected void onCreate(Bundle b){super.onCreate(b);en="en".equals(getSharedPreferences("wos_tulostaulu",MODE_PRIVATE).getString("lang","fi"));
        ScrollView sc=new ScrollView(this); LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(18),dp(20),dp(18),dp(40)); root.setBackgroundColor(Color.rgb(7,23,39)); sc.addView(root);
        LinearLayout hero=box(Color.rgb(13,48,76),22); hero.setPadding(dp(20),dp(20),dp(20),dp(20)); hero.addView(txt("🎓  WAR ACADEMY",26,true,Color.WHITE)); hero.addView(txt(tr("Helios T11 + Exalted / Molten T12 suunnittelu","Helios T11 + Exalted / Molten T12 planning"),13,false,Color.rgb(202,224,238))); root.addView(hero,mp(0,0,0,16));

        root.addView(info(tr("T12-rakenne: 5 Exalted-trackia per troop type (5 tasoa), sitten Molten I, boss-tech, Molten II, Solar Supremacy ja Molten III. Exalted avautuu War Academy FC5–FC10; Molten I/II/III vaatii FC10.","T12 structure: 5 Exalted tracks per troop type (5 levels), then Molten I, boss tech, Molten II, Solar Supremacy and Molten III. Exalted opens from War Academy FC5–FC10; Molten I/II/III requires FC10.")));

        Spinner troop=spin(new String[]{"Infantry","Lancer","Marksman","All 3 troop types"});
        Spinner goal=spin(new String[]{tr("T12 unlock (5 Exalted tracks)","T12 unlock (5 Exalted tracks)"),tr("T12 fully max","T12 fully max")});
        root.addView(txt(tr("Troop type","Troop type"),12,true,cyan())); root.addView(troop); root.addView(txt(tr("Tavoite","Goal"),12,true,cyan()),mp(0,8,0,0)); root.addView(goal);

        root.addView(txt(tr("OMA VARASTO","OWNED RESOURCES"),13,true,cyan()),mp(0,14,0,7));
        ownedRfc=input("Refined Fire Crystals",true); ownedShard=input("Fire Crystal Shards",true); ownedSteel=input("Steel",true); ownedMeat=input("Meat",true); ownedWood=input("Wood",true); ownedCoal=input("Coal",true); ownedIron=input("Iron",true);
        root.addView(ownedRfc);root.addView(ownedShard);root.addView(ownedSteel);root.addView(ownedMeat);root.addView(ownedWood);root.addView(ownedCoal);root.addView(ownedIron);

        Button calc=button(tr("LASKE T12 TARVE","CALCULATE T12 COST")); root.addView(calc,mp(0,10,0,10)); result=out(); root.addView(result); calc.setOnClickListener(v->calculate(troop.getSelectedItemPosition(),goal.getSelectedItemPosition()));

        root.addView(txt(tr("SHARD-MUUNNIN","SHARD CONVERTER"),13,true,cyan()),mp(0,18,0,7));
        EditText steel=input("Steel",true),fc=input("Fire Crystals",true); root.addView(steel);root.addView(fc); TextView conv=out(); Button cv=button(tr("LASKE SHARDIT","CALCULATE SHARDS"));root.addView(cv);root.addView(conv,mp(0,8,0,0));
        cv.setOnClickListener(v->{long a=Math.min(20,val(steel)/5000); long b2=Math.min(260,(val(fc)/10)*13); conv.setText(tr("Steel-vaihto: ","Steel exchange: ")+fmt(a)+"\n"+tr("FC-vaihto: ","FC exchange: ")+fmt(b2)+"\n"+tr("Yhteensä: ","Total: ")+fmt(a+b2)+" shards");});

        root.addView(info(tr("T11 Helios -puussa on troop-tyypeittäin Flame + Helios -teknologiat. Esim. Infantry: Flame Squad, Flame Shield, Flame Strike, Flame Tomahawk, Flame Protection, Flame Legion, Helios Infantry, Training, Healing ja First Aid. Bunny King näyttää T12:n vahvistetut kokonaiskustannukset nyt; yksittäisten satojen T11/T12 rivien tuonti jatkuu vain ristivarmistetusta datasta.","The T11 Helios tree contains Flame + Helios technologies for each troop type. Example Infantry: Flame Squad, Flame Shield, Flame Strike, Flame Tomahawk, Flame Protection, Flame Legion, Helios Infantry, Training, Healing and First Aid. Bunny King now uses verified T12 aggregate costs; individual T11/T12 rows are added only when cross-checked.")));
        setContentView(sc);
    }

    private void calculate(int troop,int goal){long[] base=goal==0?T12_UNLOCK:T12_MAX; long mult=troop==3?3:1; EditText[] owned={ownedRfc,ownedShard,ownedSteel,ownedMeat,ownedWood,ownedCoal,ownedIron}; String[] names={"Refined FC","FC Shards","Steel","Meat","Wood","Coal","Iron"}; StringBuilder s=new StringBuilder();s.append(tr("TARVE","REQUIRED")).append("\n");for(int i=0;i<base.length;i++){long need=base[i]*mult,missing=Math.max(0,need-val(owned[i]));s.append(names[i]).append(": ").append(fmt(need)).append("  •  ").append(tr("puuttuu ","missing ")).append(fmt(missing)).append('\n');} if(goal==0)s.append("\n").append(tr("T12 unlock = kaikki 5 Exalted-trackia valmiiksi yhdelle troop typelle.","T12 unlock = complete all 5 Exalted tracks for one troop type."));else s.append("\n").append(tr("Fully max sisältää Exalted + Molten I/II/III + boss-tech + Solar Supremacy.","Fully max includes Exalted + Molten I/II/III + boss tech + Solar Supremacy."));result.setText(s.toString());}
    private String tr(String f,String e){return en?e:f;} private long val(EditText e){try{String s=e.getText().toString().replaceAll("[^0-9]","");return s.isEmpty()?0:Long.parseLong(s);}catch(Exception x){return 0;}} private String fmt(long n){return NumberFormat.getIntegerInstance(Locale.US).format(n);} private int cyan(){return Color.rgb(119,205,255);} private Spinner spin(String[] a){Spinner s=new Spinner(this);s.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,a));return s;}
    private EditText input(String h,boolean num){EditText e=new EditText(this);e.setHint(h);e.setHintTextColor(Color.rgb(135,158,174));e.setTextColor(Color.WHITE);e.setSingleLine();e.setPadding(dp(12),dp(10),dp(12),dp(10));e.setInputType(num?InputType.TYPE_CLASS_NUMBER:InputType.TYPE_CLASS_TEXT);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(9,29,44));g.setCornerRadius(dp(11));e.setBackground(g);return e;}
    private LinearLayout box(int c,int r){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);GradientDrawable g=new GradientDrawable();g.setColor(c);g.setCornerRadius(dp(r));l.setBackground(g);return l;} private TextView txt(String s,int z,boolean b,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(b)v.setTypeface(Typeface.DEFAULT_BOLD);return v;} private TextView info(String s){TextView v=txt(s,13,false,Color.rgb(210,228,238));v.setPadding(dp(12),dp(12),dp(12),dp(12));return v;} private TextView out(){TextView v=txt(tr("Valmis laskentaan.","Ready to calculate."),14,false,Color.WHITE);v.setPadding(dp(14),dp(14),dp(14),dp(14));return v;} private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);return b;} private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);} private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
