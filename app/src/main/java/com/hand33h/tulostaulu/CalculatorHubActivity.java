package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CalculatorHubActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private boolean english;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);
        english="en".equals(p.getString("lang","fi"));

        ScrollView sc=new ScrollView(this);
        LinearLayout root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18),dp(20),dp(18),dp(40));
        root.setBackgroundColor(Color.rgb(7,23,39));
        sc.addView(root);

        LinearLayout hero=box(Color.rgb(13,48,76),22);
        hero.setPadding(dp(20),dp(20),dp(20),dp(20));
        hero.addView(label("🧮  "+tr("WOS LASKUKESKUS 2.0","WOS CALCULATOR HUB 2.0"),26,true,Color.WHITE));
        hero.addView(label(tr("Päivityslaskurit on uudistettu: current → target, kappalemäärä, oma varasto, puuttuvat materiaalit ja aika/teho.","Upgrade calculators rebuilt: current → target, quantity, inventory, shortages and time/power."),13,false,Color.rgb(202,224,238)));
        root.addView(hero,mp(0,0,0,16));

        addSection(root,tr("PÄIVITETYT PÄÄLASKURIT","UPGRADED CORE CALCULATORS"));
        addUpgrade(root,"🛡️","Chief Gear",tr("1–6 osaa, Alloy, Polishing, Plans, Amber, power ja puutteet","1–6 pieces, Alloy, Polishing, Plans, Amber, power and shortages"),"chief_gear");
        addUpgrade(root,"💠","Chief Charms",tr("1–18 charm-slotia, Lv0–18, Guides, Designs, Secrets ja puutteet","1–18 charm slots, Lv0–18, Guides, Designs, Secrets and shortages"),"charms");
        addUpgrade(root,"🔥","Fire Crystals",tr("Furnace Lv30 → FC10, FC/RFC ja oma varasto","Furnace Lv30 → FC10, FC/RFC and inventory"),"fire_crystals");
        addUpgrade(root,"🏗️","Buildings / Furnace",tr("Furnace-päivityspolku ja vahvistetut FC/RFC-kulut","Furnace upgrade path and verified FC/RFC costs"),"buildings");
        addUpgrade(root,"⚔️","Troops",tr("Training/promotion, resurssit, power sekä oma Training Speed %","Training/promotion, resources, power and your Training Speed %"),"troops");

        addSection(root,tr("MUUT LASKURIT","OTHER CALCULATORS"));
        addNative(root,"🧠","Research",tr("Resurssit, Steel ja research-speedupit","Resources, Steel and research speedups"),"research");
        addNative(root,"🎓","War Academy",tr("Helios T11 + Exalted/Molten T12, RFC, Shards ja Steel","Helios T11 + Exalted/Molten T12, RFC, Shards and Steel"),"war_academy");
        addNative(root,"🦸","Hero Gear",tr("Hero Gear XP, Essence Stones ja Mythril","Hero Gear XP, Essence Stones and Mythril"),"hero_gear");
        addNative(root,"🐾","Pets",tr("Pet-kehitysmateriaalit ja tavoitepuutteet","Pet upgrade materials and shortages"),"pets");
        addNative(root,"👨‍🔬","Experts",tr("Expert XP, manuals ja materiaalit","Expert XP, manuals and materials"),"experts");

        addSection(root,tr("EVENTIT JA TAISTELU","EVENTS & COMBAT"));
        addBattle(root);
        addNative(root,"⚔️","SvS Prep",tr("Yhdistä prep-resurssit ja arvioi puutteet","Combine prep resources and estimate shortages"),"svs");
        addNative(root,"🐻","Bear Trap / Rally",tr("Infantry, Lancers, Marksmen ja rally-kapasiteetti","Infantry, Lancers, Marksmen and rally capacity"),"bear");
        addNative(root,"🧊","KOI / King of Icefield",tr("Eventtiresurssit ja pistearvio","Event resources and score estimate"),"koi");
        addNative(root,"📦","Chests",tr("Arkkumäärät ja odotetut materiaalit","Chest counts and expected materials"),"chests");

        root.addView(label(tr("Uudistuksen malli on otettu WOS-yhteisön parhaista käytännöistä: nykyinen taso → tavoite, inventaario → puute, nopeusbonus → todellinen aika. Ulkoisten sivujen koodia ei kopioida.","The redesign follows the best WOS community calculator patterns: current → target, inventory → shortage, speed bonus → effective time. No external site code is copied."),12,false,Color.rgb(140,170,188)),mp(0,10,0,0));
        setContentView(sc);
    }

    private void addSection(LinearLayout root,String s){root.addView(label(s,13,true,Color.rgb(119,205,255)),mp(0,4,0,7));}

    private void addBattle(LinearLayout root){
        LinearLayout card=box(Color.rgb(18,48,70),18); card.setPadding(dp(16),dp(14),dp(16),dp(14));
        card.addView(label("⚔️  Battle Simulator",17,true,Color.WHITE));
        card.addView(label(tr("Attacker vs Defender, troop-jakauma, WOS bonusprosentit ja tappioarvio","Attacker vs Defender, troop split, WOS bonus stats and loss estimate"),12,false,Color.rgb(202,224,238)),mp(0,4,0,8));
        Button open=smallButton(tr("AVAA TAISTELUSIMULAATTORI  →","OPEN BATTLE SIMULATOR  →"));
        open.setOnClickListener(v->startActivity(new Intent(this,BattleSimulatorActivity.class)));
        card.addView(open); root.addView(card,mp(0,0,0,10));
    }

    private void addUpgrade(LinearLayout root,String icon,String title,String desc,String mode){
        LinearLayout card=box(Color.rgb(18,58,82),18);card.setPadding(dp(16),dp(14),dp(16),dp(14));
        card.addView(label(icon+"  "+title+"  • 2.0",17,true,Color.WHITE));
        card.addView(label(desc,12,false,Color.rgb(202,224,238)),mp(0,4,0,8));
        Button open=smallButton(tr("AVAA UUSI LASKURI  →","OPEN NEW CALCULATOR  →"));
        open.setOnClickListener(v->{Intent i=new Intent(this,UpgradePlannerActivity.class);i.putExtra("mode",mode);startActivity(i);});
        card.addView(open);root.addView(card,mp(0,0,0,10));
    }

    private void addNative(LinearLayout root,String icon,String title,String desc,String mode){
        LinearLayout card=box(Color.rgb(18,48,70),18); card.setPadding(dp(16),dp(14),dp(16),dp(14));
        card.addView(label(icon+"  "+title,17,true,Color.WHITE));
        card.addView(label(desc,12,false,Color.rgb(202,224,238)),mp(0,4,0,8));
        Button open=smallButton(tr("AVAA LASKURI  →","OPEN CALCULATOR  →"));
        open.setOnClickListener(v->{
            if("war_academy".equals(mode)) startActivity(new Intent(this,WarAcademyActivity.class));
            else {Intent i=new Intent(this,NativeCalculatorActivity.class);i.putExtra("mode",mode);startActivity(i);}
        });
        card.addView(open); root.addView(card,mp(0,0,0,10));
    }
    private String tr(String fi,String en){return english?en:fi;}
    private LinearLayout box(int c,int r){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);GradientDrawable g=new GradientDrawable();g.setColor(c);g.setCornerRadius(dp(r));g.setStroke(dp(1),Color.rgb(42,86,112));l.setBackground(g);return l;}
    private TextView label(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private Button smallButton(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(20,126,196));g.setCornerRadius(dp(12));b.setBackground(g);return b;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
