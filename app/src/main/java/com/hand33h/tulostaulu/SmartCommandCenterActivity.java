package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import java.text.NumberFormat;
import java.util.Locale;

public class SmartCommandCenterActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private boolean en;
    private LinearLayout root;
    private Spinner playstyle,goal,preset;
    private EditText state,totalTroops,infPct,lanPct,markPct;
    private TextView recommendation,ratioResult;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);
        en="en".equals(p.getString("lang","fi"));
        ScrollView sc=new ScrollView(this);
        root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(18),dp(18),dp(40));root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        cardTitle("👑  "+tr("WOS SMART COMMAND CENTER 5.0","WOS SMART COMMAND CENTER 5.0"),tr("Scan → data → strategy → planner → event sniper → T12 → transfer → battle → export.","Scan → data → strategy → planner → event sniper → T12 → transfer → battle → export."));

        section(tr("PELAAJAPROFIILI","PLAYER PROFILE"));
        state=input(tr("State / server","State / server"),true);state.setText(p.getString("state","1674"));root.addView(state);
        playstyle=spin(new String[]{"F2P","Low spender","Spender / Rally leader"});root.addView(playstyle);
        goal=spin(new String[]{tr("Yleinen kehitys","General progression"),"Bear Joiner","Bear Rally Leader","SvS / PvP",tr("Nopea power-kasvu","Fast power growth")});root.addView(goal);
        Button save=button(tr("TALLENNA PROFIILI JA SUOSITUS","SAVE PROFILE & RECOMMENDATION"));root.addView(save,mp(0,6,0,8));
        recommendation=info("");root.addView(recommendation);
        save.setOnClickListener(v->{p.edit().putString("state",state.getText().toString().trim()).putString("playstyle",playstyle.getSelectedItem().toString()).putString("strategy_goal",goal.getSelectedItem().toString()).apply();showRecommendation();});

        section(tr("SMART FLOW 5.0","SMART FLOW 5.0"));
        addLaunch("📸",tr("Smart Scan / OCR","Smart Scan / OCR"),tr("Tuo ranking- tai profiilikuvia ja vie tunnistettu data pelaajarekisteriin.","Import ranking/profile screenshots and push recognized data into the player registry."),ScreenshotImportActivity.class);
        addLaunch("🧊",tr("State & Player Data","State & Player Data"),tr("State 1674 oletuksena, FID-haku, paikallinen TOP100 ja Excel.","State 1674 default, FID lookup, local TOP100 and Excel."),DataSourcesActivity.class);
        addLaunch("👑","Master Upgrade Planner",tr("Gear, Charms, FC, speed-bonukset ja muut päivitykset yhdestä näkymästä.","Gear, Charms, FC, speed bonuses and upgrades in one view."),MasterUpgradePlannerActivity.class);
        addLaunch("🚀",tr("Advanced Ops 5.0","Advanced Ops 5.0"),tr("Event Sniper, T12, State Age, Transfer, VIP, Territory ja Alliance Ops.","Event Sniper, T12, State Age, Transfer, VIP, Territory and Alliance Ops."),AdvancedOpsActivity.class);
        addLaunch("💬","Community Strategy",tr("Reddit-yhteisön strategiat erillään varmennetusta pelidatasta.","Reddit community strategies kept separate from verified game data."),CommunityStrategyActivity.class);
        addLaunch("⚔️","Battle Simulator",tr("Vertaa hyökkääjää ja puolustajaa sekä WOS-bonusprosentteja.","Compare attacker, defender and WOS bonus percentages."),BattleSimulatorActivity.class);

        section(tr("TROOP RATIO LAB","TROOP RATIO LAB"));
        root.addView(info(tr("Community-presetit ovat lähtökohtia, eivät virallisia pelikaavoja. Prosentteja voi muuttaa vapaasti.","Community presets are starting points, not official game formulas. Percentages are fully editable.")));
        preset=spin(new String[]{tr("Oma suhde","Custom"),"Bear Joiner 10/10/80","Rally Leader 20/20/60","Balanced PvP 40/30/30"});root.addView(preset);
        totalTroops=input(tr("Joukkojen kokonaismäärä","Total troops"),true);root.addView(totalTroops);
        infPct=input("Infantry %",true);lanPct=input("Lancers %",true);markPct=input("Marksmen %",true);infPct.setText("40");lanPct.setText("30");markPct.setText("30");root.addView(infPct);root.addView(lanPct);root.addView(markPct);
        preset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){public void onNothingSelected(AdapterView<?> a){}public void onItemSelected(AdapterView<?> a,View v,int pos,long id){if(pos==1)setRatio(10,10,80);else if(pos==2)setRatio(20,20,60);else if(pos==3)setRatio(40,30,30);}});
        Button calc=button(tr("LASKE JOUKKOMÄÄRÄT","CALCULATE TROOP COUNTS"));root.addView(calc,mp(0,6,0,8));ratioResult=info(tr("Syötä kokonaismäärä ja suhde.","Enter total troops and ratio."));root.addView(ratioResult);
        calc.setOnClickListener(v->calcRatio());

        section(tr("SvS QUICK PLAN","SvS QUICK PLAN"));
        root.addView(info(tr("Päiväkohtainen community-template: varmista aina oman event-version pistekategoriat pelistä ennen resurssien käyttöä.","Day-by-day community template: always verify the current event point categories in-game before spending resources.")));
        day("D1",tr("Rakentaminen / FC / speedups – säästä isoimmat päivitykset pistepäivään.","Construction / FC / speedups – save major upgrades for the scoring day."));
        day("D2",tr("Chief Gear / Charms – käytä Master Planneria ennen avaamista.","Chief Gear / Charms – use Master Planner before spending."));
        day("D3",tr("Hero / Hero Gear / shards – tarkista päivän pisteytys ensin.","Hero / Hero Gear / shards – verify the day scoring first."));
        day("D4",tr("Pets / Experts / muut progression-kohteet tilanteen mukaan.","Pets / Experts / other progression according to the current event."));
        day("D5",tr("Training / promotion – syötä oma Training Speed % laskuriin.","Training / promotion – use your Training Speed % in the calculator."));

        section(tr("PIKALASKURIT","QUICK CALCULATORS"));
        quick("🛡️ Chief Gear","chief_gear");quick("💠 Chief Charms","charms");quick("🔥 Fire Crystals","fire_crystals");quick("⚔️ Troops","troops");quickNative("🐾 Pets","pets");quickNative("🦸 Hero Gear","hero_gear");quickNative("🧠 Research","research");quickNative("👨‍🔬 Experts","experts");
        root.addView(info(tr("Verified data, OCR/FID-havainnot ja Community Advice pidetään erillään. Tuntemattomia kustannuksia, event-pisteitä tai unlock-päiviä ei arvata.","Verified data, OCR/FID observations and Community Advice stay separate. Unknown costs, event scores or unlock dates are not guessed.")),mp(0,12,0,0));
        setContentView(sc);showRecommendation();
    }

    private void showRecommendation(){int g=goal.getSelectedItemPosition();String s;
        if(g==1)s=tr("🐻 Bear Joiner: aloita Marksman-painotteisesta testistä, käytä oikeaa joiner-heroa ja vertaa tulosta omista raporteista. Avaa Battle/Community Strategy ennen investointeja.","🐻 Bear Joiner: start with a marksman-heavy test, use a suitable joiner hero and compare against your own reports. Open Battle/Community Strategy before investing.");
        else if(g==2)s=tr("👑 Rally Leader: priorisoi kokonaisstatit, hero-skillit ja rally-kapasiteetti. Käytä Battle Simulatoria sekä Master Planneria yhdessä.","👑 Rally Leader: prioritize total stats, hero skills and rally capacity. Use Battle Simulator together with Master Planner.");
        else if(g==3)s=tr("⚔️ SvS/PvP: pidä resurssit event-päiville, tarkista pistekategoriat pelistä ja tee Gear/Charm/FC-päivitykset Master Plannerin kautta.","⚔️ SvS/PvP: hold resources for event days, verify scoring categories in-game and route Gear/Charm/FC upgrades through Master Planner.");
        else if(g==4)s=tr("📈 Power-kasvu: vertaile FC-, Gear-, Charm-, Troop- ja Hero Gear -polkuja ja valitse ensin varmennettu, tehokkain puute per oma varasto.","📈 Power growth: compare FC, Gear, Charm, Troop and Hero Gear paths and prioritize the best verified shortage for your inventory.");
        else s=tr("🧭 Yleinen kehitys: pidä pääjärjestys tasapainossa — Furnace/FC, Chief Gear, Charms, Hero/Hero Gear, Pets/Experts ja Research. Käytä Master Planneria ennen suurempaa resurssikulutusta.","🧭 General progression: keep a balanced order — Furnace/FC, Chief Gear, Charms, Hero/Hero Gear, Pets/Experts and Research. Use Master Planner before major spending.");
        recommendation.setText(s);
    }

    private void calcRatio(){long total=num(totalTroops);double i=dbl(infPct),l=dbl(lanPct),m=dbl(markPct),sum=i+l+m;if(total<=0||sum<=0){toast(tr("Tarkista joukkomäärä ja prosentit.","Check troop count and percentages."));return;}long inf=Math.round(total*i/sum),lan=Math.round(total*l/sum),mark=Math.max(0,total-inf-lan);ratioResult.setText("Infantry: "+fmt(inf)+"\nLancers: "+fmt(lan)+"\nMarksmen: "+fmt(mark)+"\n\n"+tr("Normalisointi tehdään automaattisesti, joten prosenttien ei tarvitse olla täsmälleen 100.","Ratios are normalized automatically, so percentages do not need to total exactly 100."));}
    private void setRatio(int a,int b,int c){infPct.setText(String.valueOf(a));lanPct.setText(String.valueOf(b));markPct.setText(String.valueOf(c));}
    private void quick(String title,String mode){Button b=button(title+"  →");b.setOnClickListener(v->{Intent i=new Intent(this,UpgradePlannerActivity.class);i.putExtra("mode",mode);startActivity(i);});root.addView(b,mp(0,0,0,8));}
    private void quickNative(String title,String mode){Button b=button(title+"  →");b.setOnClickListener(v->{Intent i=new Intent(this,NativeCalculatorActivity.class);i.putExtra("mode",mode);startActivity(i);});root.addView(b,mp(0,0,0,8));}
    private void addLaunch(String icon,String title,String desc,Class<?> cls){LinearLayout c=card();c.addView(text(icon+"  "+title,17,true,Color.WHITE));c.addView(text(desc,12,false,Color.rgb(205,226,238)));Button b=button(tr("AVAA  →","OPEN  →"));b.setOnClickListener(v->startActivity(new Intent(this,cls)));c.addView(b);root.addView(c,mp(0,0,0,10));}
    private void day(String d,String s){TextView v=info(d+"  •  "+s);root.addView(v,mp(0,0,0,6));}
    private void cardTitle(String t,String s){LinearLayout c=card();c.addView(text(t,25,true,Color.WHITE));c.addView(text(s,12,false,Color.rgb(205,226,238)));root.addView(c,mp(0,0,0,14));}
    private void section(String s){root.addView(text(s,13,true,Color.rgb(119,205,255)),mp(0,6,0,7));}
    private LinearLayout card(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(dp(16),dp(14),dp(16),dp(14));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(16,48,69));g.setCornerRadius(dp(18));g.setStroke(dp(1),Color.rgb(42,86,112));l.setBackground(g);return l;}
    private TextView info(String s){TextView v=text(s,12,false,Color.rgb(214,232,242));v.setPadding(dp(12),dp(10),dp(12),dp(10));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(11,37,55));g.setCornerRadius(dp(12));g.setStroke(dp(1),Color.rgb(34,78,102));v.setBackground(g);return v;}
    private EditText input(String hint,boolean numeric){EditText e=new EditText(this);e.setHint(hint);e.setHintTextColor(Color.rgb(140,170,188));e.setTextColor(Color.WHITE);if(numeric)e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);return e;}
    private Spinner spin(String[] a){Spinner s=new Spinner(this);ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,a);s.setAdapter(ad);return s;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(20,126,196));g.setCornerRadius(dp(12));b.setBackground(g);return b;}
    private TextView text(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private long num(EditText e){try{return Long.parseLong(e.getText().toString().replace(" ","").trim());}catch(Exception x){return 0;}}
    private double dbl(EditText e){try{return Double.parseDouble(e.getText().toString().replace(',','.').trim());}catch(Exception x){return 0;}}
    private String fmt(long n){return NumberFormat.getIntegerInstance(en?Locale.US:new Locale("fi","FI")).format(n);}
    private String tr(String fi,String eng){return en?eng:fi;}
    private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
