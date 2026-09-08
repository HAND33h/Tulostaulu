package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class Wos7ExpansionActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private LinearLayout root; private boolean en; private SharedPreferences p;
    private final EditText[] heroName=new EditText[5],heroLevel=new EditText[5],heroStars=new EditText[5],heroSkill=new EditText[5],heroGear=new EditText[5];
    private EditText bearTotal,bearInf,bearLan,bearMark,champPlayers,e1,e2,e3,cjNotes,iwlHeroes,iwlTroops,iwlCapacity,mapNotes;
    private TextView bearResult,champResult,iwlResult,historyResult;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);p=getSharedPreferences(PREFS,MODE_PRIVATE);en="en".equals(p.getString("lang","fi"));
        ScrollView sc=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(18),dp(18),dp(48));root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        hero("👑 WOS EXPANSION 7.1",tr("Hero roster • Bear • Championship • Crazy Joe • IWL • Alliance Map • History • Share","Hero roster • Bear • Championship • Crazy Joe • IWL • Alliance Map • History • Share"));

        section("HERO ROSTER / TRACKER");
        root.addView(info(tr("Tallenna tärkeimmät herot paikallisesti. Näitä ei käsitellä virallisena API-datana.","Store your key heroes locally. These values are not treated as official API data.")));
        for(int i=0;i<5;i++){LinearLayout row=card();heroName[i]=input(tr("Hero "+(i+1)+" nimi","Hero "+(i+1)+" name"),false,p.getString("hero_"+i+"_name",""));heroLevel[i]=input("Lv",true,p.getString("hero_"+i+"_level",""));heroStars[i]=input(tr("Tähdet","Stars"),true,p.getString("hero_"+i+"_stars",""));heroSkill[i]=input(tr("Expedition skill","Expedition skill"),true,p.getString("hero_"+i+"_skill",""));heroGear[i]=input("Exclusive Gear",true,p.getString("hero_"+i+"_gear",""));row.addView(heroName[i]);row.addView(heroLevel[i]);row.addView(heroStars[i]);row.addView(heroSkill[i]);row.addView(heroGear[i]);root.addView(row,mp(0,0,0,8));}
        Button saveHero=button(tr("TALLENNA HERO ROSTER","SAVE HERO ROSTER"));saveHero.setOnClickListener(v->saveHeroes());root.addView(saveHero,mp(0,0,0,14));

        section("BEAR HUNT OPTIMIZER");
        root.addView(info(tr("Laskee formationit antamillasi prosenteilla ja tarjoaa testipresetit. Sovellus ei väitä community-presettejä viralliseksi vahinkokaavaksi.","Calculates formations from your percentages and offers test presets. Community presets are not treated as an official damage formula.")));
        bearTotal=input(tr("Joukkojen kokonaismäärä","Total troops"),true,"");bearInf=input("Infantry %",true,"10");bearLan=input("Lancer %",true,"10");bearMark=input("Marksman %",true,"80");root.addView(bearTotal);root.addView(bearInf);root.addView(bearLan);root.addView(bearMark);
        LinearLayout bp=new LinearLayout(this);bp.setOrientation(LinearLayout.HORIZONTAL);Button b1=small("10/10/80"),b2=small("15/10/75"),b3=small("20/10/70");bp.addView(b1,new LinearLayout.LayoutParams(0,-2,1));bp.addView(b2,new LinearLayout.LayoutParams(0,-2,1));bp.addView(b3,new LinearLayout.LayoutParams(0,-2,1));root.addView(bp);b1.setOnClickListener(v->ratio(10,10,80));b2.setOnClickListener(v->ratio(15,10,75));b3.setOnClickListener(v->ratio(20,10,70));
        Button bearCalc=button(tr("LASKE BEAR FORMATION","CALCULATE BEAR FORMATION"));bearResult=info("");bearCalc.setOnClickListener(v->calcBear());root.addView(bearCalc,mp(0,6,0,8));root.addView(bearResult);

        section("ALLIANCE CHAMPIONSHIP LANE OPTIMIZER");
        root.addView(info(tr("Syötä vastustajan UI:ssa näkyvät kolme lane-poweria. Pelaajat jaetaan suhteessa laneihin; käytä lopulliseen päätökseen aina pelin Championship UI:n power-arvoja.","Enter the three lane powers shown in the Championship UI. Players are allocated proportionally; always use the live Championship UI numbers for final decisions.")));
        champPlayers=input(tr("Rekisteröidyt pelaajat","Registered players"),true,"60");e1=input("Enemy Lane 1 power",true,"");e2=input("Enemy Lane 2 power",true,"");e3=input("Enemy Lane 3 power",true,"");root.addView(champPlayers);root.addView(e1);root.addView(e2);root.addView(e3);Button champ=button(tr("LASKE LANE-JAKO","CALCULATE LANES"));champResult=info("");champ.setOnClickListener(v->calcChamp());root.addView(champ,mp(0,6,0,8));root.addView(champResult);

        section("CRAZY JOE PLANNER");
        root.addView(info(tr("Pidä wave-/HQ-/online-offline-suunnitelma yhdessä paikassa. Syötä oma suunnitelmasi pelin nykyisen event-version mukaan.","Keep wave/HQ/online-offline planning in one place. Enter your plan according to the current event version.")));
        cjNotes=note(tr("Wave 1–20 / HQ / online-offline / hero assignments","Wave 1–20 / HQ / online-offline / hero assignments"),p.getString("cj_plan",""));root.addView(cjNotes);Button saveCj=button(tr("TALLENNA CRAZY JOE PLAN","SAVE CRAZY JOE PLAN"));saveCj.setOnClickListener(v->{p.edit().putString("cj_plan",cjNotes.getText().toString()).apply();toast(tr("Crazy Joe plan tallennettu","Crazy Joe plan saved"));});root.addView(saveCj,mp(0,6,0,14));

        section("IWL FORMATION GUARD");
        root.addView(info(tr("3.9.2026 sääntömuutos: participating formation tarvitsee 3 heroa ja täyden troop capacityn ennen tallennusta; Qualifierin alettua kokoonpano lukittuu.","3 Sep 2026 rule change: a participating formation needs 3 heroes and full troop capacity before saving; composition locks once Qualifier starts.")));
        iwlHeroes=input(tr("Herojen määrä formationissa","Heroes in formation"),true,"3");iwlTroops=input(tr("Valitut joukot","Selected troops"),true,"");iwlCapacity=input(tr("Troop capacity","Troop capacity"),true,"");root.addView(iwlHeroes);root.addView(iwlTroops);root.addView(iwlCapacity);Button guard=button(tr("TARKISTA IWL FORMATION","CHECK IWL FORMATION"));iwlResult=info("");guard.setOnClickListener(v->checkIwl());root.addView(guard,mp(0,6,0,8));root.addView(iwlResult);

        section("ALLIANCE MAP / OFFICER BOARD");
        root.addView(info(tr("Paikallinen kartta-/territory-muistio rakennuksille, bannereille, hiveille ja Sunfire/Foundry-järjestelyille.","Local map/territory notes for buildings, banners, hive and Sunfire/Foundry coordination.")));
        mapNotes=note(tr("Esim. HQ (50,50), Bear (54,48), Banner A → ...","e.g. HQ (50,50), Bear (54,48), Banner A → ..."),p.getString("alliance_map_notes",""));root.addView(mapNotes);Button saveMap=button(tr("TALLENNA ALLIANCE MAP","SAVE ALLIANCE MAP"));saveMap.setOnClickListener(v->{p.edit().putString("alliance_map_notes",mapNotes.getText().toString()).apply();toast(tr("Alliance Map tallennettu","Alliance Map saved"));});root.addView(saveMap,mp(0,6,0,14));

        section("CALCULATION HISTORY");
        historyResult=info(loadHistory());root.addView(historyResult);Button refresh=button(tr("PÄIVITÄ HISTORIA","REFRESH HISTORY"));refresh.setOnClickListener(v->historyResult.setText(loadHistory()));root.addView(refresh,mp(0,6,0,8));
        Button share=button(tr("JAA DISCORD / SHARE CARD","SHARE DISCORD / SHARE CARD"));share.setOnClickListener(v->shareCard(buildShareText()));root.addView(share,mp(0,0,0,14));

        section("LINKIT 7.1");
        addLaunch("⚔️ Battle Report Analyzer",BattleReportAnalyzerActivity.class);addLaunch("🧬 Data Engine 7.0",DataCatalogActivity.class);addLaunch("🧠 Intelligence Center",Wos7IntelligenceActivity.class);addLaunch("🚀 Advanced Ops",AdvancedOpsActivity.class);
        setContentView(sc);
    }

    private void saveHeroes(){SharedPreferences.Editor e=p.edit();for(int i=0;i<5;i++){e.putString("hero_"+i+"_name",heroName[i].getText().toString().trim());e.putString("hero_"+i+"_level",heroLevel[i].getText().toString().trim());e.putString("hero_"+i+"_stars",heroStars[i].getText().toString().trim());e.putString("hero_"+i+"_skill",heroSkill[i].getText().toString().trim());e.putString("hero_"+i+"_gear",heroGear[i].getText().toString().trim());}e.apply();toast(tr("Hero roster tallennettu","Hero roster saved"));}
    private void ratio(int a,int b,int c){bearInf.setText(""+a);bearLan.setText(""+b);bearMark.setText(""+c);}
    private void calcBear(){long n=num(bearTotal);double a=dbl(bearInf),b=dbl(bearLan),c=dbl(bearMark),s=a+b+c;if(n<=0||s<=0){toast(tr("Tarkista arvot","Check values"));return;}long i=Math.round(n*a/s),l=Math.round(n*b/s),m=Math.max(0,n-i-l);String hero=bestHeroSummary();String out="Infantry: "+fmt(i)+"\nLancer: "+fmt(l)+"\nMarksman: "+fmt(m)+"\n\n"+hero+"\n\n"+tr("Vertaa vahinko Battle Report Analyzerissa ennen kuin lukitset oman presetin.","Compare damage in Battle Report Analyzer before locking your own preset.");bearResult.setText(out);p.edit().putString("last_bear_plan",out).apply();}
    private String bestHeroSummary(){int best=-1,skill=-1;for(int i=0;i<5;i++){int x=(int)num(heroSkill[i]);if(!heroName[i].getText().toString().trim().isEmpty()&&x>skill){skill=x;best=i;}}return best>=0?tr("Roster-havainto: korkein syötetty Expedition skill: ","Roster observation: highest entered Expedition skill: ")+heroName[best].getText()+" (Lv "+skill+")":tr("Hero rosterissa ei vielä dataa.","No hero roster data yet.");}
    private void calcChamp(){int n=(int)num(champPlayers);double a=dbl(e1),b=dbl(e2),c=dbl(e3),s=a+b+c;if(n<=0||s<=0){toast(tr("Syötä pelaajat ja lane-powerit","Enter players and lane powers"));return;}int l1=(int)Math.round(n*a/s),l2=(int)Math.round(n*b/s),l3=Math.max(0,n-l1-l2);String out="Lane 1: "+l1+"\nLane 2: "+l2+"\nLane 3: "+l3+"\n\n"+tr("Algoritmi käyttää vain syöttämiesi lane-powerien suhteita. Se ei korvaa pelin matchmaking-/squad-power-kaavaa.","The algorithm uses only the relative lane powers you entered. It does not replace the game's matchmaking/squad-power formula.");champResult.setText(out);p.edit().putString("last_championship_plan",out).apply();}
    private void checkIwl(){long h=num(iwlHeroes),t=num(iwlTroops),cap=num(iwlCapacity);boolean heroes=h==3,full=cap>0&&t>=cap;String out=(heroes?"✅":"❌")+" 3 heroes\n"+(full?"✅":"❌")+" full troop capacity\n\n"+(heroes&&full?tr("Formation täyttää syötetyn tarkistuksen. Varmista vielä live IWL UI ennen Qualifieria.","Formation passes the entered checks. Verify the live IWL UI before Qualifier."):tr("Formation ei vielä täytä 3 hero + full capacity -ehtoja.","Formation does not yet satisfy the 3 hero + full capacity conditions."));iwlResult.setText(out);p.edit().putString("last_iwl_check",out).apply();}
    private String loadHistory(){StringBuilder b=new StringBuilder();String[] keys={"last_upgrade_calculation","last_battle_analysis","last_bear_plan","last_championship_plan","last_iwl_check"};String[] labels={"Upgrade","Battle","Bear","Championship","IWL"};for(int i=0;i<keys.length;i++){String v=p.getString(keys[i],"");if(!v.isEmpty())b.append("• ").append(labels[i]).append("\n").append(trim(v,450)).append("\n\n");}if(b.length()==0)b.append(tr("Ei vielä laskuhistoriaa.","No calculation history yet."));return b.toString();}
    private String buildShareText(){return "👑 WOS BUNNY KING 7.1\nState #"+p.getString("state","1674")+"\n\n"+loadHistory()+"\nPowered by WOS community • HAND33h";}
    private void shareCard(String text){try{TextView card=new TextView(this);card.setText(text);card.setTextColor(Color.WHITE);card.setTextSize(16);card.setPadding(40,40,40,40);card.setBackgroundColor(Color.rgb(10,34,52));int w=1080;card.measure(View.MeasureSpec.makeMeasureSpec(w,View.MeasureSpec.EXACTLY),View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));int h=Math.max(600,card.getMeasuredHeight());card.layout(0,0,w,h);Bitmap bm=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);Canvas c=new Canvas(bm);card.draw(c);File dir=new File(getCacheDir(),"shares");dir.mkdirs();File f=new File(dir,"wos-share.png");FileOutputStream os=new FileOutputStream(f);bm.compress(Bitmap.CompressFormat.PNG,95,os);os.close();Uri u=FileProvider.getUriForFile(this,getPackageName()+".fileprovider",f);Intent s=new Intent(Intent.ACTION_SEND);s.setType("image/png");s.putExtra(Intent.EXTRA_STREAM,u);s.putExtra(Intent.EXTRA_TEXT,"WOS Bunny King 7.1");s.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);startActivity(Intent.createChooser(s,tr("Jaa WOS-kortti","Share WOS card")));}catch(Exception ex){Intent s=new Intent(Intent.ACTION_SEND);s.setType("text/plain");s.putExtra(Intent.EXTRA_TEXT,text);startActivity(Intent.createChooser(s,tr("Jaa","Share")));}}
    private void addLaunch(String title,Class<?> cls){Button b=button(title+"  →");b.setOnClickListener(v->startActivity(new Intent(this,cls)));root.addView(b,mp(0,0,0,8));}
    private EditText input(String hint,boolean num,String value){EditText e=new EditText(this);e.setHint(hint);e.setHintTextColor(Color.rgb(145,175,194));e.setText(value);e.setTextColor(Color.WHITE);if(num)e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);return e;}
    private EditText note(String hint,String value){EditText e=input(hint,false,value);e.setMinLines(3);return e;}
    private LinearLayout card(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(dp(12),dp(8),dp(12),dp(8));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(13,42,61));g.setCornerRadius(dp(14));l.setBackground(g);return l;}
    private void hero(String a,String b){LinearLayout c=card();c.addView(text(a,25,true,Color.WHITE));c.addView(text(b,12,false,Color.rgb(205,226,238)));root.addView(c,mp(0,0,0,14));}
    private void section(String s){root.addView(text(s,13,true,Color.rgb(119,205,255)),mp(0,8,0,7));}
    private TextView info(String s){TextView v=text(s,12,false,Color.rgb(214,232,242));v.setPadding(dp(12),dp(10),dp(12),dp(10));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(11,37,55));g.setCornerRadius(dp(12));v.setBackground(g);return v;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(20,126,196));g.setCornerRadius(dp(12));b.setBackground(g);return b;}
    private Button small(String s){Button b=button(s);b.setTextSize(10);return b;}
    private TextView text(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private long num(EditText e){try{String s=e.getText().toString().replaceAll("[^0-9]","");return s.isEmpty()?0:Long.parseLong(s);}catch(Exception x){return 0;}}
    private double dbl(EditText e){try{return Double.parseDouble(e.getText().toString().replace(',','.').trim());}catch(Exception x){return 0;}}
    private String fmt(long n){return NumberFormat.getIntegerInstance(en?Locale.US:new Locale("fi","FI")).format(n);}private String trim(String s,int n){return s.length()>n?s.substring(0,n)+"…":s;}
    private String tr(String fi,String eng){return en?eng:fi;}private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
