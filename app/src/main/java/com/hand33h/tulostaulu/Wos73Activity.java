package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Wos73Activity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private SharedPreferences p; private LinearLayout root; private boolean en;
    private EditText koiGoal,koiHave,koiDays,koiNotes,bannerMeat,bannerWood,bannerCoal,bannerIron,bannerCount;
    private EditText ministerName,ministerSlot,ministerTime,rosterNames,rosterPower,castlePlan,champPlayers,c1,c2,c3,historyNote;
    private EditText sniperGap,s1Pts,s1Stock,s2Pts,s2Stock,s3Pts,s3Stock,staminaNow,staminaCost,valeriaPct;
    private TextView koiResult,bannerResult,champResult,historyResult,sniperResult,staminaResult;

    @Override protected void onCreate(Bundle b){super.onCreate(b);p=getSharedPreferences(PREFS,MODE_PRIVATE);en="en".equals(p.getString("lang","fi"));
        ScrollView sc=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(18),dp(18),dp(40));root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        title("👑 WOS COMMAND CENTER 7.3.1",tr("KOI • Event Sniper • Stamina • Banners • Ministers • Roster • Castle • Championship • History","KOI • Event Sniper • Stamina • Banners • Ministers • Roster • Castle • Championship • History"));

        section("7-DAY KOI PLANNER");
        root.addView(info(tr("Syötä tämän eventin live-pistearvot ja oma tavoite. Päiväkohtaisia pisteitä ei kovakoodata, jotta planneri toimii season-muutoksissa.","Enter live values from the current event and your target. Day values are not hardcoded so the planner remains valid across seasons.")));
        koiGoal=input(tr("Tavoitepisteet","Target points"),true,p.getString("koi_goal",""));koiHave=input(tr("Nykyiset pisteet","Current points"),true,p.getString("koi_have",""));koiDays=input(tr("Päiviä jäljellä","Days remaining"),true,p.getString("koi_days","7"));koiNotes=note(tr("D1–D7 aktiviteetit, piste/unit, troop promotion, stamina, speedups...","D1–D7 activities, points/unit, troop promotion, stamina, speedups..."),p.getString("koi_notes",""));root.addView(koiGoal);root.addView(koiHave);root.addView(koiDays);root.addView(koiNotes);Button koi=button(tr("LASKE KOI PÄIVÄTAVOITE","CALCULATE KOI DAILY TARGET"));koiResult=info("");koi.setOnClickListener(v->calcKoi());root.addView(koi);root.addView(koiResult);

        section("EVENT SNIPER 3.0");
        root.addView(info(tr("Syötä tämän päivän oikeat piste/unit-arvot pelistä. Optimointi käyttää vain syöttämääsi live-dataa eikä oleta universaalia event-pisteytystä.","Enter today's actual points-per-unit values from the game. Optimization uses only your live inputs and does not assume universal event scoring.")));
        sniperGap=input(tr("Pistevaje","Point gap"),true,p.getString("sniper_gap",""));root.addView(sniperGap);
        s1Pts=input("Action 1 points/unit",true,p.getString("sniper_a1_pts",""));s1Stock=input("Action 1 stock",true,p.getString("sniper_a1_stock",""));root.addView(s1Pts);root.addView(s1Stock);
        s2Pts=input("Action 2 points/unit",true,p.getString("sniper_a2_pts",""));s2Stock=input("Action 2 stock",true,p.getString("sniper_a2_stock",""));root.addView(s2Pts);root.addView(s2Stock);
        s3Pts=input("Action 3 points/unit",true,p.getString("sniper_a3_pts",""));s3Stock=input("Action 3 stock",true,p.getString("sniper_a3_stock",""));root.addView(s3Pts);root.addView(s3Stock);
        Button sn=button(tr("LASKE HALVIN PISTEVAJE","CALCULATE LOWEST-USE GAP"));sniperResult=info("");sn.setOnClickListener(v->calcSniper());root.addView(sn);root.addView(sniperResult);

        section("SvS D3 STAMINA PLANNER");
        root.addView(info(tr("Beast/Polar Terror -staminaa varten. Syötä tämänhetkinen stamina ja yhden toiminnon stamina-kulu. Valeria-bonus on käyttäjän syöttämä prosentti, ei kovakoodattu.","For Beast/Polar Terror stamina planning. Enter current stamina and stamina per action. Valeria bonus is user-entered, not hardcoded.")));
        staminaNow=input(tr("Stamina nyt","Current stamina"),true,p.getString("stamina_now",""));staminaCost=input(tr("Stamina / toiminto","Stamina / action"),true,p.getString("stamina_cost",""));valeriaPct=input(tr("Valeria Well Prepared %","Valeria Well Prepared %"),true,p.getString("valeria_pct","0"));root.addView(staminaNow);root.addView(staminaCost);root.addView(valeriaPct);Button st=button(tr("LASKE STAMINA","CALCULATE STAMINA"));staminaResult=info("");st.setOnClickListener(v->calcStamina());root.addView(st);root.addView(staminaResult);

        section("BANNER CALCULATOR");
        root.addView(info(tr("Alliance banner -resurssibudjetti. Syötä yhden bannerin nykyinen kustannus suoraan pelistä ja määrä.","Alliance banner resource budget. Enter current per-banner costs directly from the game and quantity.")));
        bannerCount=input(tr("Bannerien määrä","Banner count"),true,"1");bannerMeat=input("Meat / banner",true,"");bannerWood=input("Wood / banner",true,"");bannerCoal=input("Coal / banner",true,"");bannerIron=input("Iron / banner",true,"");root.addView(bannerCount);root.addView(bannerMeat);root.addView(bannerWood);root.addView(bannerCoal);root.addView(bannerIron);Button bc=button(tr("LASKE BANNER-BUDJETTI","CALCULATE BANNER BUDGET"));bannerResult=info("");bc.setOnClickListener(v->calcBanner());root.addView(bc);root.addView(bannerResult);

        section("MINISTER SCHEDULER");
        ministerName=input(tr("Pelaaja / FID","Player / FID"),false,"");ministerSlot=input(tr("Ministerirooli","Minister role"),false,"");ministerTime=input(tr("Aika UTC, esim. 18:30","Time UTC, e.g. 18:30"),false,"");root.addView(ministerName);root.addView(ministerSlot);root.addView(ministerTime);Button ms=button(tr("LISÄÄ MINISTERIVUORO","ADD MINISTER SLOT"));ms.setOnClickListener(v->append("minister_schedule",ministerTime.getText()+" • "+ministerSlot.getText()+" • "+ministerName.getText()));root.addView(ms);root.addView(info(p.getString("minister_schedule",tr("Ei vielä vuoroja.","No slots yet."))));

        section("ALLIANCE ROSTER MANAGER");
        rosterNames=note(tr("Yksi pelaaja per rivi: nimi | FID | rooli | online-aika","One player per line: name | FID | role | online time"),p.getString("alliance_roster",""));rosterPower=input(tr("Rosterin arvioitu yhteispower","Estimated roster total power"),true,p.getString("roster_power",""));root.addView(rosterNames);root.addView(rosterPower);Button rs=button(tr("TALLENNA ROSTER","SAVE ROSTER"));rs.setOnClickListener(v->{p.edit().putString("alliance_roster",rosterNames.getText().toString()).putString("roster_power",rosterPower.getText().toString()).apply();toast(tr("Roster tallennettu","Roster saved"));});root.addView(rs);

        section("CASTLE BATTLE PLANNER");
        castlePlan=note(tr("Rally leaders, garrison, reinforcement waves, minister timing, voice roles...","Rally leaders, garrison, reinforcement waves, minister timing, voice roles..."),p.getString("castle_battle_plan",""));root.addView(castlePlan);Button cs=button(tr("TALLENNA CASTLE PLAN","SAVE CASTLE PLAN"));cs.setOnClickListener(v->{p.edit().putString("castle_battle_plan",castlePlan.getText().toString()).apply();toast(tr("Castle plan tallennettu","Castle plan saved"));});root.addView(cs);

        section("ALLIANCE CHAMPIONSHIP 7.2");
        root.addView(info(tr("Käytä pelin Championship UI:n Squad Power -arvoja. 5.9.2026 muutosten vuoksi aikataulua ei oleteta vanhasta event-mallista.","Use the Championship UI Squad Power values. Because of the 5 Sep 2026 changes, timing is not assumed from the old event format.")));
        champPlayers=input(tr("Pelaajat","Players"),true,"60");c1=input("Enemy Squad Power L1",true,"");c2=input("Enemy Squad Power L2",true,"");c3=input("Enemy Squad Power L3",true,"");root.addView(champPlayers);root.addView(c1);root.addView(c2);root.addView(c3);Button cc=button(tr("OPTIMOI LANE-JAKO","OPTIMIZE LANES"));champResult=info("");cc.setOnClickListener(v->calcChamp());root.addView(cc);root.addView(champResult);

        section("UNIFIED CALCULATION HISTORY");
        historyNote=note(tr("Lisää oma merkintä historiaan","Add a note to history"),"");root.addView(historyNote);Button hs=button(tr("TALLENNA HISTORIAAN","SAVE TO HISTORY"));historyResult=info(loadHistory());hs.setOnClickListener(v->{saveHistory("Manual",historyNote.getText().toString());historyResult.setText(loadHistory());historyNote.setText("");});root.addView(hs);root.addView(historyResult);Button hr=button(tr("PÄIVITÄ HISTORIA","REFRESH HISTORY"));hr.setOnClickListener(v->historyResult.setText(loadHistory()));root.addView(hr);
        setContentView(sc);
    }

    private void calcKoi(){long goal=n(koiGoal),have=n(koiHave),days=Math.max(1,n(koiDays));long gap=Math.max(0,goal-have);long daily=(long)Math.ceil(gap/(double)days);String out=tr("Pistevaje: ","Point gap: ")+fmt(gap)+"\n"+tr("Minimi / jäljellä oleva päivä: ","Minimum / remaining day: ")+fmt(daily);koiResult.setText(out);p.edit().putString("koi_goal",koiGoal.getText().toString()).putString("koi_have",koiHave.getText().toString()).putString("koi_days",koiDays.getText().toString()).putString("koi_notes",koiNotes.getText().toString()).apply();saveHistory("KOI",out);}
    private void calcSniper(){long gap=n(sniperGap);long[] pts={n(s1Pts),n(s2Pts),n(s3Pts)};long[] stock={n(s1Stock),n(s2Stock),n(s3Stock)};long remain=Math.max(0,gap);StringBuilder b=new StringBuilder();for(int pass=0;pass<3;pass++){int best=-1;double bestWaste=Double.MAX_VALUE;for(int i=0;i<3;i++){if(pts[i]<=0||stock[i]<=0)continue;double waste=(double)Math.max(0,pts[i]-Math.max(1,remain));if(waste<bestWaste){bestWaste=waste;best=i;}}if(best<0||remain<=0)break;long need=(long)Math.ceil(remain/(double)pts[best]);long use=Math.min(stock[best],need);long gain=use*pts[best];b.append("Action ").append(best+1).append(": ").append(use).append(" × ").append(fmt(pts[best])).append(" = ").append(fmt(gain)).append("\n");remain=Math.max(0,remain-gain);stock[best]=0;}b.append("\n").append(tr("Jäljelle jäävä pistevaje: ","Remaining point gap: ")).append(fmt(remain));String out=b.toString();sniperResult.setText(out);p.edit().putString("sniper_gap",sniperGap.getText().toString()).putString("sniper_a1_pts",s1Pts.getText().toString()).putString("sniper_a1_stock",s1Stock.getText().toString()).putString("sniper_a2_pts",s2Pts.getText().toString()).putString("sniper_a2_stock",s2Stock.getText().toString()).putString("sniper_a3_pts",s3Pts.getText().toString()).putString("sniper_a3_stock",s3Stock.getText().toString()).apply();saveHistory("Event Sniper 3.0",out);}
    private void calcStamina(){double now=d(staminaNow),cost=d(staminaCost),bonus=d(valeriaPct);if(now<=0||cost<=0){toast(tr("Tarkista stamina-arvot","Check stamina values"));return;}double effectiveCost=cost*(1.0-Math.max(0,bonus)/100.0);if(effectiveCost<=0)effectiveCost=cost;long actions=(long)Math.floor(now/effectiveCost);String out=tr("Arvioitu tehokas stamina / toiminto: ","Estimated effective stamina / action: ")+String.format(Locale.US,"%.2f",effectiveCost)+"\n"+tr("Mahdolliset toiminnot: ","Possible actions: ")+fmt(actions)+"\n\n"+tr("Valeria-% käytetään vain syöttämäsi arvon mukaan.","Valeria % is applied only from your entered value.");staminaResult.setText(out);p.edit().putString("stamina_now",staminaNow.getText().toString()).putString("stamina_cost",staminaCost.getText().toString()).putString("valeria_pct",valeriaPct.getText().toString()).apply();saveHistory("SvS D3 Stamina",out);}
    private void calcBanner(){long q=Math.max(0,n(bannerCount));String out="Meat: "+fmt(q*n(bannerMeat))+"\nWood: "+fmt(q*n(bannerWood))+"\nCoal: "+fmt(q*n(bannerCoal))+"\nIron: "+fmt(q*n(bannerIron));bannerResult.setText(out);saveHistory("Banner",out);}
    private void calcChamp(){int players=(int)n(champPlayers);double a=d(c1),b=d(c2),c=d(c3),sum=a+b+c;if(players<=0||sum<=0){toast(tr("Tarkista arvot","Check values"));return;}int l1=(int)Math.round(players*a/sum),l2=(int)Math.round(players*b/sum),l3=Math.max(0,players-l1-l2);String out="Lane 1: "+l1+"\nLane 2: "+l2+"\nLane 3: "+l3+"\n"+tr("Perustuu syötettyyn Squad Power -suhteeseen.","Based on the entered Squad Power ratio.");champResult.setText(out);saveHistory("Championship 7.2",out);}
    private void append(String key,String line){String old=p.getString(key,"");String now=(old.isEmpty()?"":old+"\n")+line;p.edit().putString(key,now).apply();toast(tr("Tallennettu","Saved"));}
    private void saveHistory(String type,String body){if(body==null||body.trim().isEmpty())return;String old=p.getString("calc_history_v73","");String ts=new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.US).format(new Date());String row=ts+" • "+type+"\n"+body.trim();String all=row+(old.isEmpty()?"":"\n\n"+old);if(all.length()>18000)all=all.substring(0,18000);p.edit().putString("calc_history_v73",all).apply();}
    private String loadHistory(){String own=p.getString("calc_history_v73","");String legacy=p.getString("last_upgrade_calculation","");String battle=p.getString("last_battle_analysis","");StringBuilder b=new StringBuilder();if(!own.isEmpty())b.append(own);if(!legacy.isEmpty())b.append("\n\nLEGACY UPGRADE\n").append(trim(legacy,700));if(!battle.isEmpty())b.append("\n\nLEGACY BATTLE\n").append(trim(battle,700));return b.length()==0?tr("Ei vielä historiaa.","No history yet."):b.toString();}
    private EditText input(String hint,boolean num,String value){EditText e=new EditText(this);e.setHint(hint);e.setHintTextColor(Color.rgb(145,175,194));e.setText(value);e.setTextColor(Color.WHITE);if(num)e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);return e;}
    private EditText note(String hint,String value){EditText e=input(hint,false,value);e.setMinLines(3);e.setGravity(48);return e;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(20,126,196));g.setCornerRadius(dp(12));b.setBackground(g);return b;}
    private TextView info(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.rgb(214,232,242));v.setPadding(dp(12),dp(10),dp(12),dp(10));return v;}
    private void title(String a,String b){TextView t=info(a+"\n"+b);t.setTextSize(22);t.setTypeface(Typeface.DEFAULT_BOLD);root.addView(t);}
    private void section(String s){TextView v=info("\n"+s);v.setTextColor(Color.rgb(119,205,255));v.setTypeface(Typeface.DEFAULT_BOLD);root.addView(v);}
    private long n(EditText e){try{return Long.parseLong(e.getText().toString().replace(" ","").trim());}catch(Exception x){return 0;}}
    private double d(EditText e){try{return Double.parseDouble(e.getText().toString().replace(',','.').trim());}catch(Exception x){return 0;}}
    private String fmt(long x){return java.text.NumberFormat.getIntegerInstance(en?Locale.US:new Locale("fi","FI")).format(x);}
    private String trim(String s,int n){return s.length()>n?s.substring(0,n)+"…":s;}
    private String tr(String fi,String enText){return en?enText:fi;}
    private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}
}
