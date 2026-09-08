package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class UnifiedActivity extends Activity {
 private static final String PREFS="wos_tulostaulu";
 private EditText server,key; private TextView status,result; private boolean english,anonymous;

 @Override protected void onCreate(Bundle b){
  super.onCreate(b);
  SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);
  english="en".equals(p.getString("lang","fi"));anonymous=p.getBoolean("anonymous_mode",false);
  String savedState=p.getString("state","1674").trim();if(savedState.isEmpty())savedState="1674";

  ScrollView sc=new ScrollView(this); LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(20),dp(18),dp(40));root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
  LinearLayout hero=box(Color.rgb(13,48,76),22); hero.setPadding(dp(22),dp(22),dp(22),dp(22)); hero.addView(label("👑  BUNNY KING",30,true,Color.WHITE));hero.addView(label(tr("WOS KOMENTOKESKUS","WOS COMMAND CENTER"),15,true,Color.rgb(130,211,255)));hero.addView(label(tr("Kaikki tärkeät WOS-työkalut yhdessä paikassa","Your WOS tools, intel and creative studio in one place"),13,false,Color.rgb(202,224,238)));if(anonymous)hero.addView(label(tr("🕶️ ANONYYMI TILA KÄYTÖSSÄ","🕶️ ANONYMOUS MODE ON"),12,true,Color.rgb(145,230,255)));root.addView(hero,mp(0,0,0,14));

  Spinner lang=new Spinner(this);ArrayAdapter<String> la=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,new String[]{"🇫🇮 Suomi","🇬🇧 English"});lang.setAdapter(la);lang.setSelection(english?1:0);root.addView(lang,mp(0,0,0,14));lang.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener(){public void onItemSelected(android.widget.AdapterView<?> a,View v,int pos,long id){boolean n=pos==1;if(n!=english){getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("lang",n?"en":"fi").apply();recreate();}}public void onNothingSelected(android.widget.AdapterView<?> a){}});

  Button account=primary("👤  "+tr("OMA TILI / ANONYYMI TILA","MY ACCOUNT / ANONYMOUS MODE"));root.addView(account,mp(0,0,0,9));
  Button forum=primary("💬  "+tr("WOS FOORUMI","WOS FORUM"));root.addView(forum,mp(0,0,0,16));

  root.addView(section(tr("OMA SERVERI","YOUR STATE")));
  LinearLayout state=box(Color.rgb(18,42,61),18);state.setPadding(dp(16),dp(16),dp(16),dp(16));
  server=input(tr("Serverinumero","Server number"),savedState,true);
  key=input("WOS Control API key (wos_...)",anonymous?"":p.getString("api_key",""),false);
  state.addView(server);state.addView(key,mp(0,8,0,10));
  Button top=primary("🏆  "+tr("AVAA SERVERIN DATA / TOP 100","OPEN STATE DATA / TOP 100"));state.addView(top);root.addView(state,mp(0,7,0,18));
  if(anonymous){key.setEnabled(false);key.setAlpha(.45f);}

  root.addView(section(tr("SOVELLUKSET","GAME APPS")));
  LinearLayout grid=new LinearLayout(this);grid.setOrientation(LinearLayout.HORIZONTAL);Button player=card("🔎\n"+tr("PELAAJAHAKU","PLAYER SEARCH")+"\n"+tr("FID / profiili","FID / profile"));Button meme=card("😂\nMEME STUDIO\n"+tr("Luo ja tallenna","Create & save"));grid.addView(player,new LinearLayout.LayoutParams(0,dp(126),1));grid.addView(meme,mpw(0,dp(126),1,10));root.addView(grid,mp(0,7,0,10));
  LinearLayout grid2=new LinearLayout(this);grid2.setOrientation(LinearLayout.HORIZONTAL);Button intel=card("📊\nSTATE INTEL\n"+tr("Serverityökalut","State tools"));Button calc=card("🧮\nCALCULATORS\n"+tr("Suunnittelu","Planning"));grid2.addView(intel,new LinearLayout.LayoutParams(0,dp(126),1));grid2.addView(calc,mpw(0,dp(126),1,10));root.addView(grid2);

  root.addView(section(tr("PIKATOIMINNOT","QUICK ACCESS")),mp(0,18,0,7));LinearLayout quick=new LinearLayout(this);quick.setOrientation(LinearLayout.HORIZONTAL);Button gifts=mini("🎁\n"+tr("Koodit","Codes")),svs=mini("⚔️\nSvS"),data=mini("🧊\nData"),tools=mini("🗺️\nTools");quick.addView(gifts,new LinearLayout.LayoutParams(0,dp(78),1));quick.addView(svs,new LinearLayout.LayoutParams(0,dp(78),1));quick.addView(data,new LinearLayout.LayoutParams(0,dp(78),1));quick.addView(tools,new LinearLayout.LayoutParams(0,dp(78),1));root.addView(quick);

  status=label(anonymous?tr("Anonyymi tila • paikalliset työkalut käytössä","Anonymous mode • local tools enabled"):tr("Valmis • State #","Ready • State #")+savedState,14,true,Color.rgb(126,211,255));result=label(tr("Serverin TOP 100 muodostetaan omasta WOS-pelaajarekisteristä. FID-haut ja OCR kasvattavat dataa.","State TOP 100 is built from the local WOS player registry. FID lookups and OCR grow the data."),13,false,Color.rgb(210,225,235));root.addView(status,mp(0,20,0,5));root.addView(result);root.addView(label("Powered by WOS community  •  HAND33h",11,true,Color.rgb(120,150,170)),mp(0,28,0,0));

  account.setOnClickListener(v->startActivity(new Intent(this,MyAccountActivity.class)));
  forum.setOnClickListener(v->startActivity(new Intent(this,ForumActivity.class)));
  top.setOnClickListener(v->openStateData());
  player.setOnClickListener(v->{saveStateAndKey();startActivity(new Intent(this,MainActivity.class));});
  meme.setOnClickListener(v->startActivity(new Intent(this,MemeActivity.class)));
  intel.setOnClickListener(v->open("https://wosguru.com/svs-intel"));
  calc.setOnClickListener(v->startActivity(new Intent(this,CalculatorHubActivity.class)));
  gifts.setOnClickListener(v->open("https://www.whiteoutsurvival-community.com/en/gift-codes.html"));
  svs.setOnClickListener(v->open("https://wosguru.com/svs-intel"));
  data.setOnClickListener(v->{saveStateAndKey();startActivity(new Intent(this,DataSourcesActivity.class));});
  tools.setOnClickListener(v->open("https://www.whiteoutsurvival-community.com/tools/wosc-index.html"));
  setContentView(sc);
 }

 private void saveStateAndKey(){
  String s=server.getText().toString().trim();if(s.isEmpty())s="1674";
  SharedPreferences.Editor e=getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("state",s);
  if(!anonymous){String k=key.getText().toString().trim();if(!k.isEmpty())e.putString("api_key",k);}e.apply();
 }

 private void openStateData(){saveStateAndKey();String s=server.getText().toString().trim();if(s.isEmpty())s="1674";status.setText(tr("Avataan State #","Opening State #")+s+"…");startActivity(new Intent(this,MainActivity.class));}
 @Override protected void onResume(){super.onResume();if(getSharedPreferences(PREFS,MODE_PRIVATE).getBoolean("anonymous_mode",false)!=anonymous)recreate();}
 private String tr(String fi,String en){return english?en:fi;} private TextView section(String s){return label(s,13,true,Color.rgb(119,205,255));}
 private LinearLayout box(int c,int r){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);GradientDrawable g=new GradientDrawable();g.setColor(c);g.setCornerRadius(dp(r));g.setStroke(dp(1),Color.rgb(37,78,105));l.setBackground(g);return l;}
 private TextView label(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
 private EditText input(String h,String val,boolean num){EditText e=new EditText(this);e.setHint(h);e.setHintTextColor(Color.rgb(190,207,219));e.setText(val);e.setTextColor(Color.WHITE);e.setSingleLine();e.setPadding(dp(14),dp(12),dp(14),dp(12));e.setInputType(num?InputType.TYPE_CLASS_NUMBER:InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(31,72,96));g.setStroke(dp(1),Color.rgb(112,190,230));g.setCornerRadius(dp(12));e.setBackground(g);return e;}
 private Button primary(String s){Button b=new Button(this);b.setText(s);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{Color.rgb(20,126,196),Color.rgb(43,170,213)});g.setCornerRadius(dp(14));b.setBackground(g);return b;}
 private Button card(String s){Button b=new Button(this);b.setText(s);b.setTextColor(Color.WHITE);b.setTextSize(14);b.setGravity(Gravity.CENTER);b.setAllCaps(false);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(18,48,70));g.setCornerRadius(dp(18));g.setStroke(dp(1),Color.rgb(42,86,112));b.setBackground(g);return b;}
 private Button mini(String s){Button b=card(s);b.setTextSize(11);return b;} private void open(String u){startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(u)));}
 private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}private LinearLayout.LayoutParams mpw(int w,int h,float wt,int left){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(w,h,wt);p.setMargins(dp(left),0,0,0);return p;}
}
