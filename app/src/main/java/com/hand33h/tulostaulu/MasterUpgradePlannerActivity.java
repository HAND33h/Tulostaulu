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
        hero.addView(txt("👑  "+tr("WOS MASTER UPGRADE PLANNER","WOS MASTER UPGRADE PLANNER"),25,true,Color.WHITE));
        hero.addView(txt(tr("Yksi paikka kaikelle: current → target, omat varastot, puuttuvat materiaalit, power, aika ja SvS-suunnittelu.","One place for everything: current → target, inventory, shortages, power, time and SvS planning."),12,false,Color.rgb(207,230,243)));
        root.addView(hero,mp(0,0,0,14));

        root.addView(info(tr("Rakennettu parhaiden WOS-laskurimallien pohjalta: WSCO, WOS Forge, WOSCalculator ja WoSTools. Käytämme omaa UI:ta ja vain vahvistettua Whiteout Survival -dataa. Tuntemattomia kustannuksia ei arvata.","Built from the strongest WOS calculator patterns: WSCO, WOS Forge, WOSCalculator and WoSTools. We use our own UI and verified Whiteout Survival data only. Unknown costs are never guessed.")));

        section(tr("YLEISET BONUKSET","GLOBAL BONUSES"));
        constructionSpeed=input(tr("Construction Speed %","Construction Speed %"),p.getString("planner_construction_speed","0"));
        researchSpeed=input(tr("Research Speed %","Research Speed %"),p.getString("planner_research_speed","0"));
        trainingSpeed=input(tr("Training Speed %","Training Speed %"),p.getString("planner_training_speed","0"));
        valeriaBonus=input(tr("Valeria / SvS bonus %","Valeria / SvS bonus %"),p.getString("planner_valeria_bonus","0"));
        root.addView(constructionSpeed);root.addView(researchSpeed);root.addView(trainingSpeed);root.addView(valeriaBonus);
        Button save=button(tr("TALLENNA BONUKSET","SAVE BONUSES"));
        save.setOnClickListener(v->{p.edit().putString("planner_construction_speed",value(constructionSpeed)).putString("planner_research_speed",value(researchSpeed)).putString("planner_training_speed",value(trainingSpeed)).putString("planner_valeria_bonus",value(valeriaBonus)).apply();Toast.makeText(this,tr("Bonukset tallennettu.","Bonuses saved."),Toast.LENGTH_SHORT).show();});
        root.addView(save,mp(0,4,0,14));

        section(tr("PÄIVITYSSUUNNITELMA 3.0","UPGRADE PLAN 3.0"));
        addPlanner("🛡️","Chief Gear",tr("1–6 gear-osaa • current → target • Alloy / Polish / Plans / Amber • power","1–6 gear pieces • current → target • Alloy / Polish / Plans / Amber • power"),"chief_gear");
        addPlanner("💠","Chief Charms",tr("1–18 charm-slotia • Lv0–18 • Guides / Designs / Jewel Secrets • power","1–18 charm slots • Lv0–18 • Guides / Designs / Jewel Secrets • power"),"charms");
        addPlanner("🔥","Furnace / Fire Crystals",tr("Lv30 → FC10 • Fire Crystals + Refined Fire Crystals","Lv30 → FC10 • Fire Crystals + Refined Fire Crystals"),"fire_crystals");
        addPlanner("⚔️","Troops",tr("T1–T11 • training / promotion • resources • time • power","T1–T11 • training / promotion • resources • time • power"),"troops");

        section(tr("LAAJAT LASKURIT","ADVANCED CALCULATORS"));
        addNative("🏗️",tr("Buildings / Furnace","Buildings / Furnace"),tr("Rakennuspolut, resurssit ja speedup-suunnittelu","Building paths, resources and speedup planning"),"buildings");
        addNative("🧠","Research",tr("Growth / Economy / Battle, resurssit ja research speed","Growth / Economy / Battle, resources and research speed"),"research");
        addNative("🎓","War Academy",tr("Steel, Fire Crystal Shards, Helios/T11/T12-polut","Steel, Fire Crystal Shards, Helios/T11/T12 paths"),"war_academy");
        addNative("🦸","Hero Gear",tr("Widgets, XP, Essence Stones ja Mythril","Widgets, XP, Essence Stones and Mythril"),"hero_gear");
        addNative("🐾","Pets",tr("Pet Food, Manuals, Potions, Serums ja refinement","Pet Food, Manuals, Potions, Serums and refinement"),"pets");
        addNative("👨‍🔬","Experts",tr("Affinity, Sigils, Books ja learning time — ei arvattuja taulukoita","Affinity, Sigils, Books and learning time — no guessed tables"),"experts");

        section("SvS / EVENT");
        addNative("⚔️","SvS Prep",tr("Yhdistä rakentaminen, research, gear, charms, pets ja troopit prep-päiville","Route construction, research, gear, charms, pets and troops to prep days"),"svs");
        addNative("🐻","Bear Trap / Rally",tr("Troop-jakauma, march/rally capacity ja taistelusuunnittelu","Troop split, march/rally capacity and combat planning"),"bear");
        addNative("🧊","King of Icefield",tr("Eventtiresurssit ja pistearvio","Event resources and score planning"),"koi");

        section(tr("DATA-LAATU","DATA QUALITY"));
        root.addView(info(tr("✓ WSCO: rakennukset, research, gear, charms, pets, War Academy, T12 ja muut koneelliset taulukot.\n✓ WOS Forge: current→target / combined-planner / speed bonus -mallit.\n✓ WOSCalculator: globaalit Construction / Research / Training Speed -asetukset ja overview-malli.\n✓ WoSTools: 18 charm-slotin ja SvS prep -mallit.\n⚠ Experts: verkkosivun koneellista täyttä kustannustaulukkoa ei ole julkaistu, joten sovellus ei täytä puuttuvia numeroita arvauksilla.","✓ WSCO: machine-readable buildings, research, gear, charms, pets, War Academy, T12 and more.\n✓ WOS Forge: current→target / combined planner / speed bonus patterns.\n✓ WOSCalculator: global Construction / Research / Training Speed settings and overview pattern.\n✓ WoSTools: 18-slot charms and SvS prep patterns.\n⚠ Experts: a complete machine-readable web cost table is not published, so the app does not invent missing values.")));

        setContentView(sc);
    }

    private void addPlanner(String icon,String title,String desc,String mode){
        LinearLayout c=card(18,48,70);c.setPadding(dp(15),dp(13),dp(15),dp(13));c.addView(txt(icon+"  "+title,17,true,Color.WHITE));c.addView(txt(desc,12,false,Color.rgb(205,225,238)),mp(0,3,0,7));Button b=button(tr("AVAA 3.0 SUUNNITTELU  →","OPEN 3.0 PLANNER  →"));b.setOnClickListener(v->{Intent i=new Intent(this,UpgradePlannerActivity.class);i.putExtra("mode",mode);startActivity(i);});c.addView(b);root.addView(c,mp(0,0,0,9));
    }
    private void addNative(String icon,String title,String desc,String mode){
        LinearLayout c=card(18,48,70);c.setPadding(dp(15),dp(13),dp(15),dp(13));c.addView(txt(icon+"  "+title,17,true,Color.WHITE));c.addView(txt(desc,12,false,Color.rgb(205,225,238)),mp(0,3,0,7));Button b=button(tr("AVAA LASKURI  →","OPEN CALCULATOR  →"));b.setOnClickListener(v->{if("war_academy".equals(mode))startActivity(new Intent(this,WarAcademyActivity.class));else{Intent i=new Intent(this,NativeCalculatorActivity.class);i.putExtra("mode",mode);startActivity(i);}});c.addView(b);root.addView(c,mp(0,0,0,9));
    }
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
