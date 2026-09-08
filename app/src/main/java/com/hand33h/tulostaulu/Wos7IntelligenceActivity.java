package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Wos7IntelligenceActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private LinearLayout root;
    private EditText svsState,svsOpponent,svsPrep,svsCastle,svsNotes;
    private EditText foundry,castle,transferTarget,transferGroup,transferNotes;
    private EditText goal;
    private final List<EditText[]> sniperRows=new ArrayList<>();
    private TextView sniperResult,archiveView;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);
        ScrollView sc=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(28,28,28,48);root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        title("👑 WOS INTELLIGENCE CENTER 7.0","SvS Archive • Foundry/Castle Ops • Event Sniper 2.0 • T12 • Transfer • Battle History");

        section("SVS MATCH ARCHIVE");
        info("Local archive for Prep/Castle results. Verified external archive submissions can be checked in WSCO; this app keeps your own notes separate.");
        svsState=input("Our state",true);svsState.setText(p.getString("state","1674"));svsOpponent=input("Opponent state",true);svsPrep=input("Prep result / score",false);svsCastle=input("Castle result / score",false);svsNotes=input("Notes / source",false);
        add(svsState,svsOpponent,svsPrep,svsCastle,svsNotes);
        Button saveSvs=button("SAVE SVS MATCH");saveSvs.setOnClickListener(v->saveSvs());root.addView(saveSvs);
        Button wscoSvs=button("OPEN WSCO SVS CENTER");wscoSvs.setOnClickListener(v->open("https://www.whiteoutsurvival-community.com/"));root.addView(wscoSvs);
        archiveView=info(p.getString("svs_archive","No local matches yet."));root.addView(archiveView);

        section("FOUNDRY / CASTLE / ALLIANCE OPS");
        foundry=input("Foundry teams, lanes, times",false);foundry.setText(p.getString("w7_foundry",""));castle=input("Castle plan / rotations / assignments",false);castle.setText(p.getString("w7_castle",""));root.addView(foundry);root.addView(castle);
        Button saveOps=button("SAVE OFFICER PLAN");saveOps.setOnClickListener(v->{p.edit().putString("w7_foundry",foundry.getText().toString()).putString("w7_castle",castle.getText().toString()).apply();toast("Saved");});root.addView(saveOps);

        section("EVENT SNIPER 2.0");
        info("Enter the current in-game points per unit, stock and a relative cost weight. The planner chooses the lowest weighted-cost actions first. Season/day values are never assumed.");
        goal=input("Point gap to close",true);root.addView(goal);
        for(int i=1;i<=5;i++)addSniperRow("Action "+i);
        Button calc=button("CALCULATE LOW-COST PLAN");calc.setOnClickListener(v->calcSniper());root.addView(calc);sniperResult=info("Ready.");root.addView(sniperResult);

        section("T12 / EXALTED TROOPS");
        info("War Academy and T12 stay on verified tables. Fresh T12 and T11→T12 promotion are treated separately where supported.");
        launch("OPEN WAR ACADEMY / T12",WarAcademyActivity.class);
        Button troops=button("OPEN TROOP TRAINING / PROMOTION");troops.setOnClickListener(v->{Intent i=new Intent(this,UpgradePlannerActivity.class);i.putExtra("mode","troops");startActivity(i);});root.addView(troops);

        section("TRANSFER INTELLIGENCE");
        info("Keep official in-game transfer group/date separate from community forecast. WSCO notes that eligibility and grouping must be checked in the event UI.");
        transferTarget=input("Target state",true);transferGroup=input("Official group / forecast label",false);transferNotes=input("Power cap, invite, passes, notes",false);transferTarget.setText(p.getString("w7_transfer_target",""));transferGroup.setText(p.getString("w7_transfer_group",""));transferNotes.setText(p.getString("w7_transfer_notes",""));add(transferTarget,transferGroup,transferNotes);
        Button saveTransfer=button("SAVE TRANSFER PLAN");saveTransfer.setOnClickListener(v->{p.edit().putString("w7_transfer_target",transferTarget.getText().toString()).putString("w7_transfer_group",transferGroup.getText().toString()).putString("w7_transfer_notes",transferNotes.getText().toString()).apply();toast("Saved");});root.addView(saveTransfer);
        Button transferBoard=button("OPEN LIVE TRANSFER BOARD");transferBoard.setOnClickListener(v->open("https://www.whiteoutsurvival-community.com/tools/state-transfer-board.html"));root.addView(transferBoard);

        section("BATTLE + DATA");
        launch("MULTI-SCREENSHOT BATTLE ANALYZER",BattleReportAnalyzerActivity.class);
        launch("WOS DATA CATALOG 7.0",DataCatalogActivity.class);
        info("Data policy: verified structured data, OCR/FID observations, community forecasts and strategy advice remain separate.");
        setContentView(sc);
    }

    private void saveSvs(){SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);String old=p.getString("svs_archive","");String date=new SimpleDateFormat("yyyy-MM-dd",Locale.US).format(new Date());String rec=date+" | State "+svsState.getText()+" vs "+svsOpponent.getText()+" | Prep: "+svsPrep.getText()+" | Castle: "+svsCastle.getText()+" | "+svsNotes.getText();String all=rec+(old.isEmpty()?"":"\n\n"+old);if(all.length()>12000)all=all.substring(0,12000);p.edit().putString("svs_archive",all).apply();archiveView.setText(all);toast("SvS match saved");}
    private void addSniperRow(String name){LinearLayout r=new LinearLayout(this);r.setOrientation(LinearLayout.HORIZONTAL);EditText n=input(name,false),pts=input("pts/unit",true),stock=input("stock",true),cost=input("cost",true);n.setText(name);r.addView(n,new LinearLayout.LayoutParams(0,-2,1.5f));r.addView(pts,new LinearLayout.LayoutParams(0,-2,1));r.addView(stock,new LinearLayout.LayoutParams(0,-2,1));r.addView(cost,new LinearLayout.LayoutParams(0,-2,1));root.addView(r);sniperRows.add(new EditText[]{n,pts,stock,cost});}
    private void calcSniper(){long target=lng(goal);if(target<=0){toast("Enter point gap");return;}class A{String n;long p,s;double c;A(String n,long p,long s,double c){this.n=n;this.p=p;this.s=s;this.c=c;}}List<A>a=new ArrayList<>();for(EditText[] r:sniperRows){long pp=lng(r[1]),ss=lng(r[2]);double cc=dbl(r[3]);if(pp>0&&ss>0)a.add(new A(r[0].getText().toString(),pp,ss,cc<=0?1:cc));}Collections.sort(a,Comparator.comparingDouble(x->x.c/x.p));long remain=target,points=0;double cost=0;StringBuilder out=new StringBuilder();for(A x:a){if(remain<=0)break;long use=Math.min(x.s,(long)Math.ceil(remain/(double)x.p));if(use<=0)continue;long got=use*x.p;points+=got;remain=Math.max(0,remain-got);cost+=use*x.c;out.append(x.n).append(": ").append(use).append(" × ").append(x.p).append(" = ").append(got).append(" pts\n");}if(points<target)out.append("\n⚠ Not enough stock. Missing ").append(target-points).append(" pts");else out.append("\n✓ Target covered. Overshoot: ").append(points-target).append(" pts\nWeighted cost: ").append(String.format(Locale.US,"%.2f",cost));out.append("\n\nPlanner uses user-entered current event values; it is not a universal WOS scoring table.");sniperResult.setText(out.toString());}
    private void launch(String s,Class<?> c){Button b=button(s);b.setOnClickListener(v->startActivity(new Intent(this,c)));root.addView(b);}
    private void open(String u){try{startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(u)));}catch(Exception e){toast("Could not open link");}}
    private void add(EditText... e){for(EditText x:e)root.addView(x);}private void title(String a,String b){TextView x=txt(a,24);root.addView(x);root.addView(txt(b,13));}private void section(String s){TextView v=txt("\n"+s,15);v.setTextColor(Color.rgb(119,205,255));root.addView(v);}private TextView info(String s){TextView v=txt(s,13);v.setPadding(12,12,12,12);return v;}private TextView txt(String s,int z){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(Color.WHITE);return v;}private EditText input(String h,boolean num){EditText e=new EditText(this);e.setHint(h);e.setHintTextColor(Color.LTGRAY);e.setTextColor(Color.WHITE);if(num)e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);return e;}private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);return b;}private long lng(EditText e){try{return Long.parseLong(e.getText().toString().replaceAll("[^0-9]",""));}catch(Exception x){return 0;}}private double dbl(EditText e){try{return Double.parseDouble(e.getText().toString().replace(',','.').trim());}catch(Exception x){return 0;}}private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
}
