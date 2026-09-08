package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/** Universal WOS state data center. Works with any state number and builds a local FID registry. */
public class DataSourcesActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private static final String API="https://woscontrol.com/api/v1";
    private EditText state,fids;
    private TextView status,data;
    private boolean english;
    private PlayerDataStore store;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);
        english="en".equals(p.getString("lang","fi"));
        store=new PlayerDataStore(this);

        ScrollView sc=new ScrollView(this);
        LinearLayout root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(20),dp(18),dp(40));root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        root.addView(text("🧊 "+tr("WOS DATAKESKUS","WOS DATA CENTER"),27,true,Color.WHITE));
        root.addView(text(tr("Toimii millä tahansa Whiteout Survival -serverinumerolla. Tunnetut FID:t kerätään omaan rekisteriin ja päivitetään live-haulla.","Works with any Whiteout Survival state number. Known FIDs are kept in a local registry and refreshed with live lookups."),13,false,Color.rgb(170,201,218)));

        state=input(tr("Serverinumero, esim. 1674","State number, e.g. 1674"),p.getString("state","1674"),true,false);root.addView(state,margin(0,16,0,8));
        fids=input(tr("FID:t: yksi per rivi tai pilkulla eroteltuna","FIDs: one per line or comma separated"),"",true,true);root.addView(fids,margin(0,0,0,8));

        Button importBtn=button(tr("HAE / TALLENNA FID:T","FETCH / SAVE FIDS"));root.addView(importBtn);
        Button refreshBtn=button(tr("PÄIVITÄ TÄMÄN SERVERIN TUNNETUT PELAAJAT","REFRESH KNOWN PLAYERS FOR THIS STATE"));root.addView(refreshBtn,margin(0,8,0,0));
        Button showBtn=button(tr("NÄYTÄ PAIKALLINEN TOP 100","SHOW LOCAL TOP 100"));root.addView(showBtn,margin(0,8,0,0));
        Button ocrBtn=button(tr("TUO WOS LEADERBOARD -KUVAT (OCR)","IMPORT WOS LEADERBOARD SCREENSHOTS (OCR)"));root.addView(ocrBtn,margin(0,8,0,0));

        status=text(tr("Valmis","Ready"),13,true,Color.rgb(126,211,255));root.addView(status,margin(0,14,0,6));
        data=text(tr("Ei vielä dataa.","No data yet."),13,false,Color.rgb(226,239,247));data.setTextIsSelectable(true);root.addView(data);

        root.addView(text(tr("VAHVISTETUT WOS-LÄHTEET","VERIFIED WOS SOURCES"),12,true,Color.rgb(119,205,255)),margin(0,20,0,6));
        addSource(root,"WOS Control",tr("FID/player, state, transfer ja alliance API. Live FID -päivitysten ensisijainen lähde.","FID/player, state, transfer and alliance API. Primary source for live FID refreshes."),"https://woscontrol.com/api-docs");
        addSource(root,"WOS Observer",tr("Havaittu leaderboard- ja historiadata; ei merkitä täydelliseksi live-populaatioksi.","Observed leaderboard/history data; not treated as a complete live population."),"https://wos-observer.com/");
        addSource(root,"WSCO",tr("WOS:n buildings, furnace, gear, charms, pets, research, VIP ja War Academy -taulut.","WOS buildings, furnace, gear, charms, pets, research, VIP and War Academy tables."),"https://www.whiteoutsurvival-community.com/tools/database/");
        addSource(root,"WOS Guru",tr("Whiteout Survival state/SvS-intel ja community-laskurit.","Whiteout Survival state/SvS intel and community calculators."),"https://wosguru.com/svs-intel");
        root.addView(text(tr("Väärän pelin tai vahvistamatonta dataa ei tuoda pelaajarekisteriin.","Data from another game or an unverified source is never imported into the player registry."),12,true,Color.rgb(255,190,120)));

        importBtn.setOnClickListener(v->startFidImport());
        refreshBtn.setOnClickListener(v->refreshKnown());
        showBtn.setOnClickListener(v->showLocal());
        ocrBtn.setOnClickListener(v->{saveState();startActivity(new Intent(this,ScreenshotImportActivity.class));});
        setContentView(sc);
    }

    private void startFidImport(){
        String s=saveState(); if(s.isEmpty())return;
        Set<String> ids=parseFids(fids.getText().toString());
        if(ids.isEmpty()){Toast.makeText(this,tr("Anna vähintään yksi FID","Enter at least one FID"),Toast.LENGTH_SHORT).show();return;}
        status.setText(tr("Haetaan ","Fetching ")+ids.size()+tr(" pelaajaa…"," players…"));
        new Thread(()->fetchMany(s,new ArrayList<>(ids))).start();
    }

    private void refreshKnown(){
        String s=saveState(); if(s.isEmpty())return;
        List<PlayerDataStore.PlayerRecord> known=store.listState(s,1000);
        Set<String> ids=new LinkedHashSet<>();for(PlayerDataStore.PlayerRecord r:known)if(r.fid!=null&&!r.fid.isEmpty())ids.add(r.fid);
        if(ids.isEmpty()){status.setText(tr("Tälle serverille ei ole vielä tunnettuja FID:itä. Tuo FID-lista tai leaderboard-kuvia.","No known FIDs for this state yet. Import FIDs or leaderboard screenshots."));return;}
        status.setText(tr("Päivitetään ","Refreshing ")+ids.size()+tr(" tunnettua pelaajaa…"," known players…"));
        new Thread(()->fetchMany(s,new ArrayList<>(ids))).start();
    }

    private void fetchMany(String requestedState,List<String> ids){
        int ok=0,wrongState=0,failed=0;
        for(String fid:ids){
            Resp r=getPlayer(fid);
            if(r.code<200||r.code>=300){failed++;continue;}
            try{
                JSONObject p=findPlayer(r.body);
                if(p==null){failed++;continue;}
                PlayerDataStore.PlayerRecord rec=recordFromJson(fid,p);
                if(rec.stateId.isEmpty())rec.stateId=requestedState;
                if(!digits(rec.stateId).equals(digits(requestedState))){wrongState++;continue;}
                if(store.save(rec))ok++;else failed++;
            }catch(Exception e){failed++;}
        }
        final int a=ok,w=wrongState,f=failed;
        runOnUiThread(()->{status.setText(tr("Valmis: ","Done: ")+a+tr(" tallennettu, "," saved, ")+w+tr(" eri serverillä, "," on another state, ")+f+tr(" epäonnistui."," failed."));showLocal();});
    }

    private PlayerDataStore.PlayerRecord recordFromJson(String fid,JSONObject p){
        PlayerDataStore.PlayerRecord r=new PlayerDataStore.PlayerRecord();
        r.fid=fid;r.nickname=str(p,"nickname","name","player_name","username","chief_name");
        r.stateId=str(p,"state_id","state","kid","server","server_id","kingdom","kingdom_id");
        r.allianceTag=str(p,"alliance_tag","tag","alliance_abbr");r.allianceName=str(p,"alliance_name","alliance");
        r.personalPower=num(p,"might","power","total_power","player_power","personal_power","total_might");
        r.combatPower=num(p,"combat_power");r.heroPower=num(p,"hero_power");r.heroTotalPower=num(p,"hero_total_power");
        r.troopPower=num(p,"troop_power");r.petPower=num(p,"pet_power","pets_power");r.expertPower=num(p,"expert_power","experts_power");
        r.kills=num(p,"kills","kill_count");r.furnaceLevel=str(p,"furnace","furnace_level","stove_lv","furnace_lv");
        r.source="WOS Control";r.sourceType="WOS_CONTROL_API";r.confidence=95;r.fetchedAt=System.currentTimeMillis();r.observedAt=r.fetchedAt;
        return r;
    }

    private Resp getPlayer(String fid){
        HttpURLConnection c=null;try{
            String key=getSharedPreferences(PREFS,MODE_PRIVATE).getString("api_key","").trim();
            if(key.toLowerCase(Locale.ROOT).startsWith("bearer "))key=key.substring(7).trim();
            if(key.isEmpty())return new Resp(401,"Missing API key");
            c=(HttpURLConnection)new URL(API+"/player/"+Uri.encode(fid)).openConnection();c.setConnectTimeout(15000);c.setReadTimeout(25000);c.setRequestProperty("Accept","application/json");c.setRequestProperty("X-API-Key",key);c.setRequestProperty("Authorization","Bearer "+key);c.setRequestProperty("User-Agent","WOSControl/1.0");
            int code=c.getResponseCode();BufferedReader br=new BufferedReader(new InputStreamReader(code>=200&&code<300?c.getInputStream():c.getErrorStream(),StandardCharsets.UTF_8));StringBuilder b=new StringBuilder();String x;while((x=br.readLine())!=null)b.append(x);br.close();return new Resp(code,b.toString());
        }catch(Exception e){return new Resp(-1,e.getMessage());}finally{if(c!=null)c.disconnect();}
    }

    private JSONObject findPlayer(String body)throws Exception{
        Object root=body.trim().startsWith("[")?new JSONArray(body):new JSONObject(body);List<JSONObject> all=new ArrayList<>();collect(root,all,0);
        for(JSONObject o:all)if(!str(o,"fid","player_id","chief_id","nickname","player_name","chief_name").isEmpty())return o;
        return root instanceof JSONObject?(JSONObject)root:null;
    }

    private void showLocal(){
        String s=saveState();if(s.isEmpty())return;List<PlayerDataStore.PlayerRecord> list=store.listState(s,100);
        Collections.sort(list,Comparator.comparingLong((PlayerDataStore.PlayerRecord r)->r.personalPower).reversed());
        StringBuilder b=new StringBuilder();b.append("STATE ").append(s).append("  •  ").append(tr("paikallisia pelaajia: ","local players: ")).append(list.size()).append("\n");
        b.append(tr("Päivitetty: ","Updated: ")).append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(new Date())).append("\n\n");
        int i=0;for(PlayerDataStore.PlayerRecord r:list){b.append(++i).append(". ").append(r.nickname==null||r.nickname.isEmpty()?r.fid:r.nickname).append("  | FID ").append(r.fid);if(r.personalPower>0)b.append(" | ").append(String.format(Locale.US,"%,d",r.personalPower));if(r.allianceTag!=null&&!r.allianceTag.isEmpty())b.append(" [").append(r.allianceTag).append("]");if(r.furnaceLevel!=null&&!r.furnaceLevel.isEmpty())b.append(" | ").append(r.furnaceLevel);b.append(" | ").append(r.sourceType).append("\n");}
        if(list.isEmpty())b.append(tr("Ei vielä pelaajia. Syötä FID-lista tai tuo WOS leaderboard -kuvia.","No players yet. Enter a FID list or import WOS leaderboard screenshots."));
        data.setText(b.toString());
    }

    private String saveState(){String s=state.getText().toString().trim();if(s.isEmpty()){Toast.makeText(this,tr("Anna serverinumero","Enter a state number"),Toast.LENGTH_SHORT).show();return"";}getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("state",s).apply();return s;}
    private Set<String> parseFids(String raw){Set<String> out=new LinkedHashSet<>();if(raw==null)return out;for(String x:raw.split("[^0-9]+"))if(x.matches("\\d{6,14}"))out.add(x);return out;}
    private void collect(Object n,List<JSONObject> o,int d){if(n==null||d>7)return;if(n instanceof JSONObject){JSONObject j=(JSONObject)n;o.add(j);Iterator<String> it=j.keys();while(it.hasNext())collect(j.opt(it.next()),o,d+1);}else if(n instanceof JSONArray){JSONArray a=(JSONArray)n;for(int i=0;i<a.length();i++)collect(a.opt(i),o,d+1);}}
    private String str(JSONObject o,String...ks){for(String k:ks){Object v=o.opt(k);if(v!=null&&v!=JSONObject.NULL&&!String.valueOf(v).trim().isEmpty())return String.valueOf(v).trim();}return"";}
    private long num(JSONObject o,String...ks){for(String k:ks)try{Object v=o.opt(k);if(v instanceof Number)return((Number)v).longValue();String s=String.valueOf(v).replaceAll("[^0-9]","");if(!s.isEmpty())return Long.parseLong(s);}catch(Exception ignored){}return 0;}
    private String digits(String s){return s==null?"":s.replaceAll("[^0-9]","");}
    private void addSource(LinearLayout root,String title,String desc,String url){root.addView(text(title,15,true,Color.WHITE));root.addView(text(desc,12,false,Color.rgb(170,201,218)));Button b=button(tr("AVAA","OPEN"));root.addView(b,margin(0,4,0,12));b.setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url))));}
    private EditText input(String hint,String value,boolean numeric,boolean multi){EditText e=new EditText(this);e.setHint(hint);e.setText(value);e.setTextColor(Color.WHITE);e.setHintTextColor(Color.LTGRAY);e.setBackgroundColor(Color.rgb(31,72,96));e.setPadding(dp(12),dp(12),dp(12),dp(12));e.setInputType(numeric?InputType.TYPE_CLASS_NUMBER:InputType.TYPE_CLASS_TEXT);if(multi){e.setSingleLine(false);e.setMinLines(3);e.setGravity(Gravity.TOP);}else e.setSingleLine();return e;}
    private TextView text(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setTextColor(Color.WHITE);b.setAllCaps(false);b.setTypeface(Typeface.DEFAULT_BOLD);b.setBackgroundColor(Color.rgb(39,165,211));return b;}
    private LinearLayout.LayoutParams margin(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private String tr(String fi,String en){return english?en:fi;}
    static class Resp{int code;String body;Resp(int c,String b){code=c;body=b==null?"":b;}}
}
