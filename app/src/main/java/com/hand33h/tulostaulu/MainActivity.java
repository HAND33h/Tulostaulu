package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {
    private static final int CREATE_XLSX = 9002;
    private static final String PREFS = "wos_tulostaulu";
    private static final String API_BASE = "https://woscontrol.com/api/v1";
    private static final int BG = Color.rgb(234, 244, 251);
    private static final int TEXT = Color.rgb(18, 43, 64);
    private static final int MUTED = Color.rgb(68, 94, 113);
    private static final int ACCENT = Color.rgb(20, 125, 190);

    private EditText stateInput, fidInput;
    private TextView statusText, resultsText;
    private Button exportButton;
    private final List<PlayerRow> rows = new ArrayList<>();
    private String currentState = "1674";
    private boolean english = false;
    private PlayerDataStore store;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        english = "en".equals(prefs.getString("lang", "fi"));
        store = new PlayerDataStore(this);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        scroll.setBackgroundColor(BG);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(40, 60, 40, 60);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(BG);
        scroll.addView(root);

        root.addView(text("👑 BUNNY KING • WOS", 29, true, TEXT, Gravity.CENTER), lp(0,0,0,4));
        root.addView(text(tr("WOS DATA / TULOSTAULU", "WOS DATA / SCOREBOARD"), 18, true, MUTED, Gravity.CENTER), lp(0,0,0,18));

        Spinner language = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"🇫🇮 Suomi", "🇬🇧 English"});
        language.setAdapter(adapter);
        language.setSelection(english ? 1 : 0);
        root.addView(language, new LinearLayout.LayoutParams(-1,-2));
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> p, android.view.View v, int pos, long id){
                boolean next=pos==1;
                if(next!=english){
                    getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("lang",next?"en":"fi").apply();
                    recreate();
                }
            }
            public void onNothingSelected(AdapterView<?> p){}
        });

        String savedState=prefs.getString("state","1674").trim();
        if(savedState.isEmpty())savedState="1674";
        currentState=savedState;
        stateInput = input(tr("Serverinumero", "Server number"), savedState, true);
        root.addView(stateInput, lp(0,16,0,0));
        fidInput = input(tr("Pelaajan FID (valinnainen)", "Player FID (optional)"), prefs.getString("fid",""), true);
        root.addView(fidInput, lp(0,14,0,0));

        root.addView(text(tr(
                "Ilman FID:tä näytetään tämän staten oma pelaajarekisteri. FID-haut ja OCR täydentävät rekisteriä. WOS Controlin /leaderboard-kutsua ei käytetä pelaajalistana.",
                "Without a FID the app shows its own state player registry. FID lookups and OCR grow the registry. WOS Control /leaderboard is not used as a player list."),
                13, true, MUTED, Gravity.CENTER), lp(0,12,0,0));

        Button loadButton = button(tr("NÄYTÄ SERVERIN DATA / HAE FID", "SHOW STATE DATA / FETCH FID"), ACCENT, 16);
        root.addView(loadButton, lp(0,24,0,10));
        Button dataCenter = button(tr("DATAKESKUS • FID-LISTA • OCR", "DATA CENTER • FID LIST • OCR"), Color.rgb(39,165,211), 15);
        root.addView(dataCenter, lp(0,0,0,10));
        exportButton = button(tr("VIE EXCEL (.XLSX)", "EXPORT EXCEL (.XLSX)"), Color.rgb(48,95,130), 15);
        exportButton.setEnabled(false);
        root.addView(exportButton, new LinearLayout.LayoutParams(-1,-2));

        statusText = text(tr("Valmis • oletus State #1674", "Ready • default State #1674"), 16, true, TEXT, Gravity.CENTER);
        root.addView(statusText, lp(0,24,0,16));
        resultsText = text(tr("Paina NÄYTÄ SERVERIN DATA. Jos rekisteri on tyhjä, lisää FID:t Datakeskuksessa tai tuo WOS-rankingkuvia.",
                "Press SHOW STATE DATA. If the registry is empty, add FIDs in Data Center or import WOS ranking screenshots."), 15, false, TEXT, Gravity.START);
        resultsText.setLineSpacing(0,1.12f);
        root.addView(resultsText, new LinearLayout.LayoutParams(-1,-2));
        root.addView(text("WOS community data • App owner: HAND33h", 13, true, MUTED, Gravity.CENTER), lp(0,36,0,0));

        loadButton.setOnClickListener(v -> loadBestData());
        dataCenter.setOnClickListener(v -> {saveInputs();startActivity(new Intent(this,DataSourcesActivity.class));});
        exportButton.setOnClickListener(v -> chooseXlsxLocation());
        setContentView(scroll);
    }

    private LinearLayout.LayoutParams lp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(l,t,r,b);return p;}
    private TextView text(String s,int size,boolean bold,int color,int gravity){TextView v=new TextView(this);v.setText(s);v.setTextSize(size);v.setTextColor(color);v.setGravity(gravity);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private EditText input(String hint,String value,boolean numeric){EditText e=new EditText(this);e.setHint(hint);e.setHintTextColor(Color.rgb(105,120,132));e.setTextColor(TEXT);e.setBackgroundColor(Color.WHITE);e.setPadding(22,16,22,16);e.setText(value);e.setSingleLine(true);e.setInputType(numeric?InputType.TYPE_CLASS_NUMBER:InputType.TYPE_CLASS_TEXT);return e;}
    private Button button(String label,int color,int size){Button b=new Button(this);b.setText(label);b.setTextColor(Color.WHITE);b.setTextSize(size);b.setTypeface(Typeface.DEFAULT_BOLD);b.setBackgroundColor(color);return b;}
    private String tr(String fi,String en){return english?en:fi;}

    private void saveInputs(){
        String state=stateInput.getText().toString().trim();if(state.isEmpty())state="1674";
        getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("state",state).putString("fid",fidInput.getText().toString().trim()).apply();
    }

    private void loadBestData(){
        String state=stateInput.getText().toString().trim(), fid=fidInput.getText().toString().trim();
        if(state.isEmpty()){state="1674";stateInput.setText(state);}
        currentState=state;
        getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("state",state).putString("fid",fid).apply();
        hideKeyboard();

        if(!fid.isEmpty()){
            String apiKey=getSharedPreferences(PREFS,MODE_PRIVATE).getString("api_key","").trim();
            if(apiKey.isEmpty()){
                Toast.makeText(this,tr("FID-livehaku tarvitsee WOS Control API-avaimen. Serverin paikallinen data toimii ilman avainta.","Live FID lookup requires a WOS Control API key. Local state data works without it."),Toast.LENGTH_LONG).show();
                showLocalState(state,false);
                return;
            }
            statusText.setText(tr("Haetaan pelaaja FID "+fid+"…","Loading player FID "+fid+"…"));
            resultsText.setText(tr("Haetaan…","Loading…"));
            new Thread(() -> fetchPlayer(fid,state)).start();
        } else {
            showLocalState(state,false);
            String apiKey=getSharedPreferences(PREFS,MODE_PRIVATE).getString("api_key","").trim();
            if(!apiKey.isEmpty())new Thread(() -> syncStateEndpoint(state)).start();
        }
    }

    private ApiResponse get(String path){
        HttpURLConnection c=null;
        try{
            String key=getSharedPreferences(PREFS,MODE_PRIVATE).getString("api_key","").trim();
            if(key.toLowerCase(Locale.ROOT).startsWith("bearer "))key=key.substring(7).trim();
            if(key.isEmpty())return new ApiResponse(401,"Missing API key");
            String clean=path.startsWith("/")?path.substring(1):path;
            URL url=new URL(API_BASE+"/"+clean);
            c=(HttpURLConnection)url.openConnection();c.setRequestMethod("GET");c.setConnectTimeout(15000);c.setReadTimeout(25000);
            c.setRequestProperty("Accept","application/json");c.setRequestProperty("X-API-Key",key);c.setRequestProperty("Authorization","Bearer "+key);c.setRequestProperty("User-Agent","WOSControl/1.0");
            int code=c.getResponseCode();BufferedReader r=new BufferedReader(new InputStreamReader(code>=200&&code<300?c.getInputStream():c.getErrorStream(), StandardCharsets.UTF_8));
            StringBuilder body=new StringBuilder();String line;while((line=r.readLine())!=null)body.append(line);r.close();return new ApiResponse(code,body.toString());
        }catch(Exception e){return new ApiResponse(-1,e.getMessage()==null?"Network error":e.getMessage());}finally{if(c!=null)c.disconnect();}
    }

    private void fetchPlayer(String fid,String requestedState){
        ApiResponse a=get("/player/"+Uri.encode(fid));
        if(a.code==401||a.code==403){showError(tr("WOS Control API-avain ei kelpaa.","WOS Control API key is unauthorized."));return;}
        if(a.code==429){showError(tr("API-kutsujen raja tuli vastaan. Yritä myöhemmin uudelleen.","API rate limit reached. Try again later."));return;}
        if(a.code<200||a.code>=300){showError("WOS Control HTTP "+a.code);return;}
        try{
            Object root=a.body.trim().startsWith("[")?new JSONArray(a.body):new JSONObject(a.body);
            JSONObject p=findBestPlayerObject(root);
            if(p==null){showError(tr("Pelaajaa ei löytynyt vastauksesta.","No player object found in response."));return;}
            PlayerDataStore.PlayerRecord rec=recordFromJson(fid,p,requestedState);
            store.save(rec);
            String actual=rec.stateId.isEmpty()?requestedState:rec.stateId;
            StringBuilder sb=new StringBuilder();
            sb.append(tr("PELAAJA TALLENNETTU TIETOKANTAAN","PLAYER SAVED TO DATABASE")).append("\n\n");
            sb.append(tr("Nimi: ","Name: ")).append(rec.nickname.isEmpty()?"-":rec.nickname).append("\nFID: ").append(fid).append("\n");
            sb.append(tr("State: ","State: ")).append(actual).append("\n");
            if(!rec.allianceTag.isEmpty())sb.append("Alliance: ").append(rec.allianceTag).append("\n");
            if(rec.personalPower>0)sb.append("Power: ").append(formatNumber(rec.personalPower)).append("\n");
            if(rec.heroPower>0)sb.append("Hero Power: ").append(formatNumber(rec.heroPower)).append("\n");
            if(rec.troopPower>0)sb.append("Troop Power: ").append(formatNumber(rec.troopPower)).append("\n");
            if(!rec.furnaceLevel.isEmpty())sb.append("Furnace: ").append(rec.furnaceLevel).append("\n");
            if(!normalize(actual).equals(normalize(requestedState)))sb.append("\n⚠ ").append(tr("Pelaaja on tällä hetkellä eri statessa kuin valittu #","Player is currently in a different state than selected #")).append(requestedState);
            final String text=sb.toString();
            runOnUiThread(()->{statusText.setText(tr("Live FID -data saatu ja tallennettu ✓","Live FID data received and saved ✓"));resultsText.setText(text);loadRowsFromStore(requestedState);});
        }catch(Exception e){showError(tr("Pelaajavastauksen käsittely epäonnistui.","Could not parse player response."));}
    }

    private PlayerDataStore.PlayerRecord recordFromJson(String fid,JSONObject p,String fallbackState){
        PlayerDataStore.PlayerRecord r=new PlayerDataStore.PlayerRecord();
        r.fid=fid;r.nickname=stringValue(p,"nickname","name","player_name","username","chief_name");
        r.stateId=stringValue(p,"state_id","state","kid","server","server_id","kingdom","kingdom_id");if(r.stateId.isEmpty())r.stateId=fallbackState;
        r.allianceTag=stringValue(p,"alliance_tag","tag","alliance_abbr");r.allianceName=stringValue(p,"alliance_name","alliance");
        r.personalPower=longValue(p,"might","power","total_power","player_power","personal_power","total_might");
        r.combatPower=longValue(p,"combat_power");r.heroPower=longValue(p,"hero_power");r.heroTotalPower=longValue(p,"hero_total_power");
        r.troopPower=longValue(p,"troop_power");r.petPower=longValue(p,"pet_power","pets_power");r.expertPower=longValue(p,"expert_power","experts_power");
        r.kills=longValue(p,"kills","kill_count");r.furnaceLevel=stringValue(p,"furnace","furnace_level","stove_lv","level","furnace_lv");
        r.source="WOS Control";r.sourceType="WOS_CONTROL_API";r.confidence=95;r.fetchedAt=System.currentTimeMillis();r.observedAt=r.fetchedAt;
        return r;
    }

    private void syncStateEndpoint(String state){
        ApiResponse a=get("/state/"+Uri.encode(state));
        int saved=0;
        if(a.code>=200&&a.code<300){
            try{
                Object root=a.body.trim().startsWith("[")?new JSONArray(a.body):new JSONObject(a.body);
                JSONArray players=findArray(root,"players",0);
                if(players!=null){
                    for(int i=0;i<players.length();i++){
                        JSONObject p=players.optJSONObject(i);if(p==null)continue;
                        String fid=stringValue(p,"fid","player_id","chief_id","uid");if(fid.isEmpty())continue;
                        PlayerDataStore.PlayerRecord rec=recordFromJson(fid,p,state);
                        if(normalize(rec.stateId).equals(normalize(state))&&store.save(rec))saved++;
                    }
                }
            }catch(Exception ignored){}
        }
        final int count=saved;
        runOnUiThread(()->{
            showLocalState(state,true);
            if(count>0)statusText.setText(tr("State-endpointilta tallennettiin ","Saved ")+count+tr(" pelaajaa • paikallinen TOP päivitetty ✓"," players from state endpoint • local TOP updated ✓"));
        });
    }

    private JSONArray findArray(Object node,String wanted,int depth){
        if(node==null||depth>6)return null;
        if(node instanceof JSONObject){
            JSONObject o=(JSONObject)node;
            JSONArray direct=o.optJSONArray(wanted);if(direct!=null)return direct;
            Iterator<String> it=o.keys();while(it.hasNext()){Object child=o.opt(it.next());if(child instanceof JSONObject||child instanceof JSONArray){JSONArray found=findArray(child,wanted,depth+1);if(found!=null)return found;}}
        }else if(node instanceof JSONArray){JSONArray a=(JSONArray)node;for(int i=0;i<a.length();i++){JSONArray found=findArray(a.opt(i),wanted,depth+1);if(found!=null)return found;}}
        return null;
    }

    private void showLocalState(String state,boolean afterSync){
        loadRowsFromStore(state);
        if(rows.isEmpty()){
            statusText.setText(tr("State #"+state+" • tietokannassa 0 pelaajaa","State #"+state+" • 0 players in database"));
            resultsText.setText(tr(
                    "Koko staten live-pelaajalistaa ei saatu avoimesta API:sta. Lisää tunnettuja FID:itä Datakeskuksessa tai tuo Whiteout Survival -leaderboard-kuvia OCR:llä. Sen jälkeen TOP 100 ja Excel muodostuvat tästä rekisteristä.",
                    "A complete live state player list was not available from the open API. Add known FIDs in Data Center or import Whiteout Survival leaderboard screenshots with OCR. TOP 100 and Excel will then be built from this registry."));
            exportButton.setEnabled(false);
            return;
        }
        StringBuilder b=new StringBuilder();b.append("STATE ").append(state).append(" • TOP ").append(rows.size()).append("\n\n").append(renderRows(rows));
        statusText.setText(tr("State #"+state+" • "+rows.size()+" pelaajaa tietokannassa"+(afterSync?" • synkronoitu":""),"State #"+state+" • "+rows.size()+" players in database"+(afterSync?" • synced":"")));
        resultsText.setText(b.toString());exportButton.setEnabled(true);
    }

    private void loadRowsFromStore(String state){
        List<PlayerDataStore.PlayerRecord> list=store.listState(state,100);rows.clear();int rank=1;
        for(PlayerDataStore.PlayerRecord r:list){long power=r.personalPower>0?r.personalPower:r.combatPower;String alliance=!r.allianceTag.isEmpty()?r.allianceTag:r.allianceName;rows.add(new PlayerRow(rank++,r.nickname,r.fid,alliance,power));}
        Collections.sort(rows,Comparator.comparingLong((PlayerRow p)->p.might).reversed());for(int i=0;i<rows.size();i++)rows.get(i).rank=i+1;
    }

    private JSONObject findBestPlayerObject(Object root)throws Exception{
        List<JSONObject> objects=new ArrayList<>();collect(root,objects,0);
        for(JSONObject o:objects){if(!stringValue(o,"fid","player_id","chief_id").isEmpty()||!stringValue(o,"nickname","player_name","chief_name").isEmpty())return o;}
        return root instanceof JSONObject?(JSONObject)root:null;
    }

    private void collect(Object n,List<JSONObject> out,int d)throws Exception{
        if(n==null||d>7)return;if(n instanceof JSONObject){JSONObject o=(JSONObject)n;out.add(o);Iterator<String> k=o.keys();while(k.hasNext()){Object x=o.opt(k.next());if(x instanceof JSONObject||x instanceof JSONArray)collect(x,out,d+1);}}else if(n instanceof JSONArray){JSONArray a=(JSONArray)n;for(int i=0;i<a.length();i++){Object x=a.opt(i);if(x instanceof JSONObject||x instanceof JSONArray)collect(x,out,d+1);}}
    }

    private String renderRows(List<PlayerRow> list){
        StringBuilder sb=new StringBuilder();int limit=Math.min(list.size(),100);
        for(int i=0;i<limit;i++){PlayerRow p=list.get(i);sb.append(p.rank).append(". ").append(p.name.isEmpty()?"FID "+p.fid:p.name);if(p.might>0)sb.append("\nPower: ").append(formatNumber(p.might));if(!p.alliance.isEmpty())sb.append(" • ").append(p.alliance);if(!p.fid.isEmpty())sb.append(" • FID ").append(p.fid);sb.append("\n\n");}return sb.toString();
    }

    private void chooseXlsxLocation(){
        if(rows.isEmpty()){Toast.makeText(this,tr("Ei pelaajadataa vietäväksi","No player data to export"),Toast.LENGTH_LONG).show();return;}
        Intent i=new Intent(Intent.ACTION_CREATE_DOCUMENT);i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");i.putExtra(Intent.EXTRA_TITLE,"State_"+currentState+".xlsx");startActivityForResult(i,CREATE_XLSX);
    }

    @Override protected void onActivityResult(int rc,int result,Intent data){super.onActivityResult(rc,result,data);if(rc==CREATE_XLSX&&result==RESULT_OK&&data!=null&&data.getData()!=null)writeXlsx(data.getData());}

    private void writeXlsx(Uri uri){
        try(OutputStream out=getContentResolver().openOutputStream(uri)){
            if(out==null)throw new Exception("File open failed");List<String[]> personal=new ArrayList<>();int rank=1;
            for(PlayerRow p:rows){int r=p.rank>0?p.rank:rank;personal.add(new String[]{String.valueOf(r),p.name,p.fid,String.valueOf(p.might),p.alliance});rank++;if(personal.size()>=100)break;}
            if(personal.isEmpty())throw new Exception("No player rows loaded");XlsxExporter.write(out,personal);Toast.makeText(this,tr("Excel tallennettu: ","Excel saved: ")+personal.size()+tr(" pelaajaa"," players"),Toast.LENGTH_LONG).show();
        }catch(Exception e){Toast.makeText(this,tr("Excel-tallennus epäonnistui: ","Excel save failed: ")+e.getMessage(),Toast.LENGTH_LONG).show();}
    }

    private String stringValue(JSONObject o,String...ks){for(String k:ks){Object v=o.opt(k);if(v!=null&&v!=JSONObject.NULL){String s=String.valueOf(v).trim();if(!s.isEmpty()&&!s.equals("{}")&&!s.equals("[]"))return s;}}return"";}
    private long longValue(JSONObject o,String...ks){for(String k:ks){Object v=o.opt(k);if(v==null||v==JSONObject.NULL)continue;try{if(v instanceof Number)return((Number)v).longValue();String s=String.valueOf(v).trim().replace(" ","").replace(",","");if(s.endsWith("B")||s.endsWith("b"))return(long)(Double.parseDouble(s.substring(0,s.length()-1))*1_000_000_000L);if(s.endsWith("M")||s.endsWith("m"))return(long)(Double.parseDouble(s.substring(0,s.length()-1))*1_000_000L);if(s.endsWith("K")||s.endsWith("k"))return(long)(Double.parseDouble(s.substring(0,s.length()-1))*1_000L);return(long)Double.parseDouble(s.replaceAll("[^0-9.\\-]",""));}catch(Exception ignored){}}return 0;}
    private String normalize(String v){return v==null?"":v.replace("#","").replaceAll("[^0-9]","");}
    private String formatNumber(long n){return String.format(Locale.US,"%,d",n).replace(',',' ');}
    private void showError(String m){runOnUiThread(()->{statusText.setText(m);resultsText.setText(tr("Ei live-dataa. Paikallinen state-rekisteri säilyy ennallaan.","No live data. The local state registry is unchanged."));exportButton.setEnabled(!rows.isEmpty());});}
    private void hideKeyboard(){InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);if(imm!=null)imm.hideSoftInputFromWindow(stateInput.getWindowToken(),0);}

    @Override protected void onDestroy(){super.onDestroy();if(store!=null)store.close();}
    private static class ApiResponse{final int code;final String body;ApiResponse(int c,String b){code=c;body=b==null?"":b;}}
    private static class PlayerRow{int rank;final String name,fid,alliance;final long might;PlayerRow(int r,String n,String f,String a,long m){rank=r;name=n==null?"":n;fid=f==null?"":f;alliance=a==null?"":a;might=m;}}
}
