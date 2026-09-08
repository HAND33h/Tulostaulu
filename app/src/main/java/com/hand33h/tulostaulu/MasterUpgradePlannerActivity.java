package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.widget.*;

public class MasterUpgradePlannerActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private LinearLayout root;
    private boolean en;
    private EditText constructionSpeed,researchSpeed,trainingSpeed,valeriaBonus;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);
        en="en".equals(p.getString("lang","fi"));
        ScrollView sc=new ScrollView(this);
        root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(20),dp(18),dp(44));root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);

        LinearLayout hero=card(13,48,76);hero.setPadding(dp(18),dp(18),dp(18),dp(18));
        hero.addView(txt("👑  "+tr("WOS MASTER UPGRADE PLANNER 4.0","WOS MASTER UPGRADE PLANNER 4.0"),25,true,Color.WHITE));
        hero.addView(txt(tr("Yksi paikka kaikelle: current → target, inventaario, puutteet, speed-bonukset, data, strategia ja SvS-suunnittelu.","One place for everything: current → target, inventory, shortages, speed bonuses, data, strategy and SvS planning."),12,false,Color.rgb(207,230,243)));
        root.addView(hero,mp(0,0,0,14));

        Button smart=button("👑  "+tr("AVAA SMART COMMAND CENTER 4.0","OPEN SMART COMMAND CENTER 4.0"));smart.setOnClickListener(v->startActivity(new Intent(this,SmartCommandCenterActivity.class)));root.addView(smart,mp(0,0,0,10));
        root.addView(info(tr("4.0 erottaa kolme kerrosta: ① Verified WOS cost/data ② OCR/FID-observed player data ③ Community Advice. Laskuri ei muuta community-neuvoa varmennetuksi pelikaavaksi eikä arvaa puuttuvia kustannuksia.","4.0 separates three layers: ① Verified WOS cost/data ② OCR/FID-observed player data ③ Community Advice. Community advice is never presented as a verified game formula and missing costs are not guessed.")));

        section(tr("YLEISET BONUKSET","GLOBAL BONUSES"));
        constructionSpeed=input("Construction Speed %",p.getString("planner_construction_speed","0"));
        researchSpeed=input("Research Speed %",p.getString("planner_research_speed","0"));
        trainingSpeed=input("Training Speed %",p.getString("planner_training_speed","0"));
        valeriaBonus=input("Valeria / SvS bonus %",p.getString("planner_valeria_bonus","0"));
        root.addView(constructionSpeed);root.addView(researchSpeed);root.addView(trainingSpeed);root.addView(valeriaBonus);
        Button save=button(tr("TALLENNA BONUKSET KAIKILLE LASKUREILLE","SAVE BONUSES FOR ALL CALCULATORS"));
        save.setOnClickListener(v->{p.edit().putString("planner_construction_speed",value(constructionSpeed)).putString("planner_research_speed",value(researchSpeed)).putString("planner_training_speed",value(trainingSpeed)).putString("planner_valeria_bonus",value(valeriaBonus)).apply();Toast.makeText(this,tr("Bonukset tallennettu.","Bonuses saved."),Toast.LENGTH_SHORT).show();});root.addView(save,mp(0,4,0,14));

        section(tr("SMART INPUT","SMART INPUT"));
        addActivity("📸",tr("Screenshot / OCR Import","Screenshot / OCR Import"),tr("Tunnista ranking/profiilidata kuvista ja lisää state-kohtaiseen rekisteriin.","Recognize ranking/profile data from screenshots and add it to the state registry."),ScreenshotImportActivity.class);
        addActivity("🧊",tr("State & Player Data","State & Player Data"),tr("FID-haku, paikallinen TOP100, historiatieto ja Excel-vienti.","FID lookup, local TOP100, history and Excel export."),DataSourcesActivity.class);

        section(tr("PÄIVITYSSUUNNITELMA 4.0","UPGRADE PLAN 4.0"));
        addPlanner("🛡️","Chief Gear",tr("1–6 gear-osaa • current → target • Alloy / Polish / Plans / Amber • power","1–6 gear pieces • current → target • Alloy / Polish / Plans / Amber • power"),"chief_gear");
        addPlanner("💠","Chief Charms",tr("1–18 charm-slotia • Lv0–18 • Guides / Designs / Jewel Secrets • power","1–18 charm slots • Lv0–18 • Guides / Designs / Jewel Secrets • power"),"charms");
        addPlanner("🔥","Furnace / Fire Crystals",tr("Lv30 → FC10 • Fire Crystals + Refined Fire Crystals","Lv30 → FC10 • Fire Crystals + Refined Fire Crystals"),"fire_crystals");
        addPlanner("⚔️","Troops",tr("T1–T11 • training / promotion • resources • time • power","T1–T11 • training / promotion • resources • time • power"),"troops");

        section(tr("LAAJAT LASKURIT","ADVANCED CALCULATORS"));
        addNative("🏗️","Buildings / Furnace",tr("Rakennuspolut, resurssit ja speedup-suunnittelu","Building paths, resources and speedup planning"),"buildings");
        addNative("🧠","Research",tr("Growth / Economy / Battle, resurssit ja research speed","Growth / Economy / Battle, resources and research speed"),"research");
        addNative("🎓","War Academy",tr("Steel, Fire Crystal Shards, Helios/T11/T12-polut","Steel, Fire Crystal Shards, Helios/T11/T12 paths"),"war_academy");
        addNative("🦸","Hero Gear",tr("Widgets, XP, Essence Stones ja Mythril","Widgets, XP, Essence Stones and Mythril"),"hero_gear");
        addNative("🐾","Pets",tr("Pet Food, Manuals, Potions, Serums ja refinement","Pet Food, Manuals, Potions, Serums and refinement"),"pets");
        addNative("👨‍🔬","Experts",tr("Affinity, Sigils, Books ja learning time — vain vahvistettu data","Affinity, Sigils, Books and learning time — verified data only"),"experts");

        section(tr("STRATEGIA JA TAISTELU","STRATEGY & COMBAT"));
        addActivity("💬","Community Strategy",tr("F2P / spender, Bear, rally, SvS/PvP; community-neuvo erillään pelidatasta.","F2P / spender, Bear, rally, SvS/PvP; community advice separate from game data."),CommunityStrategyActivity.class);
        addActivity("⚔️","Battle Simulator",tr("Hyökkääjä vs puolustaja, troop-jakauma ja bonusprosentit.","Attacker vs defender, troop split and bonus percentages."),BattleSimulatorActivity.class);
        addNative("⚔️","SvS Prep",tr("Yhdistä rakentaminen, research, gear, charms, pets ja troopit prep-päiville","Route construction, research, gear, charms, pets and troops to prep days"),"svs");
        addNative("🐻","Bear Trap / Rally",tr("Troop-jakauma, march/rally capacity ja taistelusuunnittelu","Troop split, march/rally capacity and combat planning"),"bear");
        addNative("🧊","King of Icefield",tr("Eventtiresurssit ja pistearvio","Event resources and score planning"),"koi");

        section(tr("DATA-LAATU","DATA QUALITY"));
        root.addView(info(tr("✓ Verified cost tables: vain Whiteout Survival -lähteistä tarkistettuja arvoja.\n✓ OCR/FID: jokainen havainto voidaan säilyttää lähteen ja ajan kanssa.\n✓ Community Advice: optimoitavat ratio/hero/priority-ehdotukset merkitty neuvoiksi.\n✓ State 1674: oletusserveri, mutta tietomalli toimii mille tahansa state-numerolle.\n⚠ Tuntemattomia T5/T6-välitasoja, Experts-kuluja tai event-pistekaavoja ei täytetä arvauksilla.","✓ Verified cost tables: values checked against Whiteout Survival sources only.\n✓ OCR/FID: observations can retain source and timestamp.\n✓ Community Advice: ratio/hero/priority suggestions are clearly labeled advice.\n✓ State 1674: default state, while the model supports any state number.\n⚠ Unknown T5/T6 intermediate values, Expert costs or event scoring formulas are not filled by guesswork.")));

        setContentView(sc);
    }

    private void addPlanner(String icon,String title,String desc,String mode){LinearLayout c=card(18,48,70);c.setPadding(dp(15),dp(13),dp(15),dp(13));c.addView(txt(icon+"  "+title,17,true,Color.WHITE));c.addView(txt(desc,12,false,Color.rgb(205,225,238)),mp(0,3,0,7));Button b=button(tr("AVAA 4.0 SUUNNITTELU  →","OPEN 4.0 PLANNER  →"));b.setOnClickListener(v->{Intent i=new Intent(this,UpgradePlannerActivity.class);i.putExtra("mode",mode);startActivity(i);});c.addView(b);root.addView(c,mp(0,0,0,9));}
    private void addNative(String icon,String title,String desc,String mode){LinearLayout c=card(18,48,70);c.setPadding(dp(15),dp(13),dp(15),dp(13));c.addView(txt(icon+"  "+title,17,true,Color.WHITE));c.addView(txt(desc,12,false,Color.rgb(205,225,238)),mp(0,3,0,7));Button b=button(tr("AVAA LASKURI  →","OPEN CALCULATOR  →"));b.setOnClickListener(v->{if("war_academy".equals(mode))startActivity(new Intent(this,WarAcademyActivity.class));else{Intent i=new Intent(this,NativeCalculatorActivity.class);i.putExtra("mode",mode);startActivity(i);}});c.addView(b);root.addView(c,mp(0,0,0,9));}
    private void addActivity(String icon,String title,String desc,Class<?> cls){LinearLayout c=card(18,48,70);c.setPadding(dp(15),dp(13),dp(15),dp(13));c.addView(txt(icon+"  "+title,17,true,Color.WHITE));c.addView(txt(desc,12,false,Color.rgb(205,225,238)),mp(0,3,0,7));Button b=button(tr("AVAA  →","OPEN  →"));b.setOnClickListener(v->startActivity(new Intent(this,cls)));c.addView(b);root.addView(c,mp(0,0,0,9));}
    private void section(String s){root.addView(txt(s,13,true,Color.rgb(119,205,255)),mp(0,8,0,7));}
    private EditText input(String hint,String initial){EditText e=new EditText(this);e.setHint(hint);e.setHintTextColor(Color.rgb(190,213,226));e.setTextColor(Color.WHITE);e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);e.setText(initial);e.setPadding(dp(14),dp(11),dp(14),dp(11));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(24,61,83));g.setCornerRadius(dp(12));g.setStroke(dp(1),Color.rgb(58,111,143));e.setBackground(g);e.setLayoutParams(mp(0,3,0,6));return e;}
    private String value(EditText e){String s=e.getText().toString().trim();return s.isEmpty()?"0":s;}
    private TextView info(String s){TextView v=txt(s,12,false,Color.rgb(215,231,241));v.setPadding(dp(12),dp(11),dp(12),dp(11));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(13,40,59));g.setCornerRadius(dp(12));v.setBackground(g);v.setLayoutParams(mp(0,0,0,12));return v;}
    private LinearLayout card(int r,int g,int b){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);GradientDrawable d=new GradientDrawable();d.setColor(Color.rgb(r,g,b));d.setCornerRadius(dp(18));d.setStroke(dp(1),Color.rgb(42,86,112));l.setBackground(d);return l;}
    private TextView txt(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(20,126,196));g.setCornerRadius(dp(12));b.setBackground(g);return b;}
    private String tr(String fi,String eng){return en?eng:fi;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
