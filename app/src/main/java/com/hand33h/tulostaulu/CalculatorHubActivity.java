package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
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
        hero.addView(label("🧮  "+tr("WOS LASKUKESKUS","WOS CALCULATOR HUB"),26,true,Color.WHITE));
        hero.addView(label(tr("Yksi alisivu kaikille tärkeimmille Whiteout Survival -laskureille.","One subpage for the most useful Whiteout Survival calculators."),13,false,Color.rgb(202,224,238)));
        root.addView(hero,mp(0,0,0,16));

        root.addView(label(tr("MITEN MUUT LASKURIT TOIMIVAT","HOW OTHER CALCULATORS WORK"),13,true,Color.rgb(119,205,255)));
        LinearLayout how=box(Color.rgb(18,42,61),16);
        how.setPadding(dp(16),dp(14),dp(16),dp(14));
        how.addView(label(tr("Yleinen malli: valitse nykyinen taso → tavoitetaso → syötä omat materiaalit → laskuri näyttää kokonaiskulun, puuttuvat materiaalit, ajan/pikakiihdytykset, Powerin ja usein SvS-pisteet.","Common pattern: choose current level → target level → enter materials on hand → see total cost, shortages, time/speedups, Power and often SvS points."),13,false,Color.rgb(220,232,240)));
        root.addView(how,mp(0,7,0,18));

        addSection(root,tr("PÄÄLASKURIT","CORE CALCULATORS"));
        addCard(root,"🛡️",tr("Chief Gear","Chief Gear"),tr("Nykyinen → tavoite, Alloy, Polishing Solution, Design Plans, Lunar Amber, Power ja SvS-pisteet.","Current → target, Alloy, Polishing Solution, Design Plans, Lunar Amber, Power and SvS points."),"https://www.whiteoutsurvival-community.com/tools/chief-gear-calculator.html","WSCO");
        addCard(root,"💠",tr("Chief Charms","Chief Charms"),tr("Charm-tasot, materiaalit ja tavoitekulut.","Charm levels, materials and target costs."),"https://wosforge.org/calculators/charms","WOS Forge");
        addCard(root,"🔥",tr("Fire Crystals","Fire Crystals"),tr("FC/RFC-kehitys, rakennuskulut, aika ja speedupit.","FC/RFC progression, building costs, time and speedups."),"https://wosforge.org/calculators/fire-crystals","WOS Forge");
        addCard(root,"🏗️",tr("Buildings / Furnace","Buildings / Furnace"),tr("Rakennusten nykyinen ja tavoitetaso, resurssit ja rakennusaika.","Current and target building levels, resources and construction time."),"https://wosforge.org/calculators/buildings","WOS Forge");
        addCard(root,"🧠",tr("Research","Research"),tr("Growth, Economy ja Battle -tutkimukset, resurssit ja aika.","Growth, Economy and Battle research, resources and time."),"https://wosforge.org/calculators/research","WOS Forge");
        addCard(root,"🎓",tr("War Academy","War Academy"),tr("Tutkimuspolku, Fire Shards, Steel ja puuttuvat materiaalit.","Research roadmap, Fire Shards, Steel and material gaps."),"https://wosforge.org/calculators/war-academy","WOS Forge");
        addCard(root,"🦸",tr("Hero Gear","Hero Gear"),tr("XP 0–200, Essence Stone mastery, Mythril-portit ja SvS-pistearvio.","XP 0–200, Essence Stone mastery, Mythril gates and estimated SvS points."),"https://wosforge.org/calculators/hero-gear","WOS Forge");
        addCard(root,"🐾",tr("Pets","Pets"),tr("Nykyinen → tavoitetaso jokaiselle petille ja kokonaisresurssit.","Current → target level for each pet and total resources."),"https://wosforge.org/calculators/pets","WOS Forge");
        addCard(root,"⚔️",tr("Troops","Troops"),tr("Koulutus/promootio, resurssit, aika, Power ja tapahtumapisteet.","Training/promotion, resources, time, Power and event points."),"https://wosforge.org/calculators/troops","WOS Forge");
        addCard(root,"👨‍🔬",tr("Experts","Experts"),tr("Expert-resurssit ja kehitystavoitteet.","Expert resources and progression targets."),"https://wosforge.org/calculators/experts","WOS Forge");

        addSection(root,tr("EVENTIT JA TAISTELU","EVENTS & COMBAT"));
        addCard(root,"⚔️",tr("SvS Prep","SvS Prep"),tr("Yhdistää viiden prep-päivän resurssit ja arvioi SvS-pisteet.","Combines all five prep days and estimates SvS score."),"https://wos-frost-command.web.app/calculators/","Frost Command");
        addCard(root,"🐻",tr("Bear Trap / Rally","Bear Trap / Rally"),tr("Trooppisuhde, rally-herot, marssikapasiteetti ja puutteet.","Troop ratio, rally heroes, march capacity and shortages."),"https://survival-planner.com/","Survival Planner");
        addCard(root,"🧊",tr("KOI / King of Icefield","KOI / King of Icefield"),tr("Tapahtuman resurssisuunnittelu ja pistearviot.","Event resource planning and score estimates."),"https://www.whiteoutsurvival-community.com/tools/wosc-index.html","WSCO");
        addCard(root,"📦",tr("Chests","Chests"),tr("Arvioi arkkujen sisältöä ja saatavia materiaaleja.","Estimate chest contents and expected materials."),"https://wosforge.org/calculators/chests","WOS Forge");

        addSection(root,tr("YHDISTETTY NÄKYMÄ","COMBINED VIEW"));
        addCard(root,"📊",tr("Combined Calculator","Combined Calculator"),tr("WOS Forgen yhdistetty yhteenveto useista laskureista yhdessä näkymässä.","WOS Forge combined recap of multiple calculators in one view."),"https://wosforge.org/calculators/combined","WOS Forge");
        addCard(root,"🧰",tr("WSCO Tools Hub","WSCO Tools Hub"),tr("20+ laskuria ja suunnittelutyökalua samassa hakunäkymässä.","20+ calculators and planning tools in one searchable hub."),"https://www.whiteoutsurvival-community.com/tools/wosc-index.html","WSCO");

        root.addView(label(tr("Tavoite Bunny Kingissä: nämä tuodaan vähitellen natiiviksi niin, että valitset Current → Target ja sovellus laskee puutteen suoraan.","Bunny King goal: progressively make these native so you choose Current → Target and the app calculates the gap directly."),12,false,Color.rgb(140,170,188)),mp(0,18,0,0));
        setContentView(sc);
    }

    private void addSection(LinearLayout root,String s){root.addView(label(s,13,true,Color.rgb(119,205,255)),mp(0,4,0,7));}
    private void addCard(LinearLayout root,String icon,String title,String desc,String url,String source){
        LinearLayout card=box(Color.rgb(18,48,70),18);
        card.setPadding(dp(16),dp(14),dp(16),dp(14));
        card.addView(label(icon+"  "+title,17,true,Color.WHITE));
        card.addView(label(desc,12,false,Color.rgb(202,224,238)),mp(0,4,0,8));
        Button open=smallButton(tr("AVAA ","OPEN ")+source+"  →");
        open.setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
        card.addView(open);
        root.addView(card,mp(0,0,0,10));
    }
    private String tr(String fi,String en){return english?en:fi;}
    private LinearLayout box(int c,int r){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);GradientDrawable g=new GradientDrawable();g.setColor(c);g.setCornerRadius(dp(r));g.setStroke(dp(1),Color.rgb(42,86,112));l.setBackground(g);return l;}
    private TextView label(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private Button smallButton(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(20,126,196));g.setCornerRadius(dp(12));b.setBackground(g);return b;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
