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
    private String currentState = "77";
    private boolean english = false;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        english = "en".equals(prefs.getString("lang", "fi"));

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

        stateInput = input(tr("Serverinumero", "Server number"), prefs.getString("state","77"), true);
        root.addView(stateInput, lp(0,16,0,0));
        fidInput = input(tr("Pelaajan FID (valinnainen)", "Player FID (optional)"), prefs.getString("fid",""), true);
        root.addView(fidInput, lp(0,14,0,0));

        root.addView(text(tr("API-avain käytetään suoraan WOS Controliin. Aseta avain ensin etusivulla.",
                "The API key is used directly with WOS Control. Set the key on the home screen first."),
                13, true, MUTED, Gravity.CENTER), lp(0,12,0,0));

        Button loadButton = button(tr("HAE WOS CONTROL -DATA", "LOAD WOS CONTROL DATA"), ACCENT, 16);
        root.addView(loadButton, lp(0,24,0,10));
        exportButton = button(tr("VIE EXCEL (.XLSX)", "EXPORT EXCEL (.XLSX)"), Color.rgb(48,95,130), 15);
        exportButton.setEnabled(false);
        root.addView(exportButton, new LinearLayout.LayoutParams(-1,-2));

        statusText = text(tr("Syötä serveri. FID:llä haetaan pelaajatiedot; ilman FID:tä serverin pelaajalista.",
                "Enter a server. With a FID, player data is loaded; without one, the state player list is loaded."),
                16, true, TEXT, Gravity.CENTER);
        root.addView(statusText, lp(0,24,0,16));
        resultsText = text(tr("Ei ladattua dataa.", "No data loaded."), 15, false, TEXT, Gravity.START);
        resultsText.setLineSpacing(0,1.12f);
        root.addView(resultsText, new LinearLayout.LayoutParams(-1,-2));
        root.addView(text("Powered by WOS Control • App owner: HAND33h", 13, true, MUTED, Gravity.CENTER), lp(0,36,0,0));

        loadButton.setOnClickListener(v -> loadBestData());
        exportButton.setOnClickListener(v -> chooseXlsxLocation());
        setContentView(scroll);
    }

    private LinearLayout.LayoutParams lp(int l,int t,int r,int b){
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(l,t,r,b);return p;
    }
    private TextView text(String s,int size,boolean bold,int color,int gravity){
        TextView v=new TextView(this);v.setText(s);v.setTextSize(size);v.setTextColor(color);v.setGravity(gravity);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;
    }
    private EditText input(String hint,String value,boolean numeric){
        EditText e=new EditText(this);e.setHint(hint);e.setHintTextColor(Color.rgb(105,120,132));e.setTextColor(TEXT);e.setBackgroundColor(Color.WHITE);e.setPadding(22,16,22,16);e.setText(value);e.setSingleLine(true);e.setInputType(numeric?InputType.TYPE_CLASS_NUMBER:InputType.TYPE_CLASS_TEXT);return e;
    }
    private Button button(String label,int color,int size){
        Button b=new Button(this);b.setText(label);b.setTextColor(Color.WHITE);b.setTextSize(size);b.setTypeface(Typeface.DEFAULT_BOLD);b.setBackgroundColor(color);return b;
    }
    private String tr(String fi,String en){return english?en:fi;}

    private void loadBestData(){
        String state=stateInput.getText().toString().trim(), fid=fidInput.getText().toString().trim();
        if(state.isEmpty()){Toast.makeText(this,tr("Anna serverinumero","Enter a server number"),Toast.LENGTH_SHORT).show();return;}
        String apiKey=getSharedPreferences(PREFS,MODE_PRIVATE).getString("api_key","").trim();
        if(apiKey.isEmpty()){Toast.makeText(this,tr("Aseta WOS Control API-avain etusivulla ensin","Set the WOS Control API key on the home screen first"),Toast.LENGTH_LONG).show();return;}
        currentState=state;
        getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("state",state).putString("fid",fid).apply();
        rows.clear();
        exportButton.setEnabled(false);
        resultsText.setText(tr("Haetaan...","Loading..."));
        hideKeyboard();
        if(!fid.isEmpty()){
            statusText.setText(tr("Haetaan pelaaja FID "+fid+"…","Loading player FID "+fid+"…"));
            new Thread(() -> fetchPlayer(fid)).start();
        } else {
            statusText.setText(tr("Haetaan serverin "+state+" pelaajat…","Loading players for state "+state+"…"));
            new Thread(() -> fetchState(state)).start();
        }
    }

    private ApiResponse get(String path){
        HttpURLConnection c=null;
        try{
            String key=getSharedPreferences(PREFS,MODE_PRIVATE).getString("api_key","").trim();
            if(key.toLowerCase(java.util.Locale.ROOT).startsWith("bearer "))key=key.substring(7).trim();
            if(key.isEmpty())return new ApiResponse(401,"Missing API key");
            String clean=path.startsWith("/")?path.substring(1):path;
            URL url=new URL(API_BASE+"/"+clean);
            c=(HttpURLConnection)url.openConnection();
            c.setRequestMethod("GET");
            c.setConnectTimeout(15000);
            c.setReadTimeout(25000);
            c.setRequestProperty("Accept","application/json");
            c.setRequestProperty("X-API-Key",key);
            c.setRequestProperty("Authorization","Bearer "+key);
            c.setRequestProperty("User-Agent","WOSControl/1.0");
            int code=c.getResponseCode();
            BufferedReader r=new BufferedReader(new InputStreamReader(code>=200&&code<300?c.getInputStream():c.getErrorStream(), StandardCharsets.UTF_8));
            StringBuilder body=new StringBuilder();
            String line;
            while((line=r.readLine())!=null)body.append(line);
            r.close();
            return new ApiResponse(code,body.toString());
        }catch(Exception e){
            return new ApiResponse(-1,e.getMessage()==null?"Network error":e.getMessage());
        }finally{
            if(c!=null)c.disconnect();
        }
    }

    private void fetchPlayer(String fid){
        ApiResponse a=get("/player/"+Uri.encode(fid));
        if(!ok(a))return;
        try{
            Object parsedRoot=a.body.trim().startsWith("[")?new JSONArray(a.body):new JSONObject(a.body);
            JSONObject p=findBestPlayerObject(parsedRoot);
            if(p==null){showError(tr("Pelaajaa ei löytynyt vastauksesta.","No player object found in response."));return;}
            String name=stringValue(p,"nickname","name","player_name","username","chief_name");
            String state=stringValue(p,"state_id","state","kid","server","server_id","kingdom","kingdom_id");
            String alliance=stringValue(p,"alliance","alliance_name","alliance_tag","tag","alliance_abbr");
            String furnace=stringValue(p,"furnace","furnace_level","stove_lv","level","furnace_lv");
            long might=longValue(p,"might","power","total_power","player_power","personal_power","total_might");
            StringBuilder sb=new StringBuilder();
            sb.append(tr("PELAAJATIEDOT","PLAYER DATA")).append("\n\n");
            sb.append(tr("Nimi: ","Name: ")).append(name.isEmpty()?"-":name).append("\nFID: ").append(fid).append("\n");
            if(!state.isEmpty())sb.append(tr("Serveri: ","Server: ")).append(state).append("\n");
            if(!alliance.isEmpty())sb.append("Alliance: ").append(alliance).append("\n");
            if(might>0)sb.append("Might: ").append(formatNumber(might)).append("\n");
            if(!furnace.isEmpty())sb.append("Furnace: ").append(furnace).append("\n");
            runOnUiThread(() -> {statusText.setText(tr("Pelaajatiedot ladattu ✓","Player data loaded ✓"));resultsText.setText(sb.toString());});
        }catch(Exception e){
            showError(tr("Pelaajavastauksen käsittely epäonnistui: ","Could not parse player response: ")+e.getMessage());
        }
    }

    private void fetchState(String state){
        ApiResponse stateResponse=get("/state/"+Uri.encode(state));
        if(!ok(stateResponse))return;

        ApiResponse leaderboard=get("/leaderboard?state_id="+Uri.encode(state));
        List<PlayerRow> parsed=new ArrayList<>();
        if(leaderboard.code>=200&&leaderboard.code<300){
            try{parsed=parsePlayers(leaderboard.body,state,true);}catch(Exception ignored){}
        }

        // Some API responses omit state_id inside each row because the request is already state-scoped.
        // Also inspect /state/{id} in case the backend returns nested player records there.
        if(parsed.isEmpty()){
            try{parsed=parsePlayers(stateResponse.body,state,false);}catch(Exception ignored){}
        }

        if(!parsed.isEmpty()){
            Collections.sort(parsed, Comparator.comparingLong((PlayerRow p)->p.might).reversed());
            for(int i=0;i<parsed.size();i++) if(parsed.get(i).rank<=0) parsed.get(i).rank=i+1;
            if(parsed.size()>100) parsed=new ArrayList<>(parsed.subList(0,100));
            rows.clear();
            rows.addAll(parsed);
            final String body=tr("SERVERI ","STATE ")+state+"\n\n"+
                    tr("Pelaajarivejä löytyi: ","Player rows found: ")+rows.size()+"\n\n"+
                    renderRows(rows);
            runOnUiThread(() -> {
                statusText.setText(tr("Pelaajadata ladattu – Excel valmis ✓","Player data loaded – Excel ready ✓"));
                resultsText.setText(body);
                exportButton.setEnabled(true);
            });
            return;
        }

        final String diagnostic=tr(
                "Serveritiedot saatiin, mutta WOS Control ei palauttanut tästä hausta pelaajarivejä. Exceliä ei enää luoda tyhjänä.\n\nLeaderboard HTTP: ",
                "State data was returned, but WOS Control returned no player rows for this request. The app will no longer create an empty Excel file.\n\nLeaderboard HTTP: ")
                +leaderboard.code+"\n"+
                tr("Leaderboard-vastaus: ","Leaderboard response: ")+shortText(leaderboard.body)+"\n\n"+
                tr("State-vastaus: ","State response: ")+shortText(stateResponse.body);
        runOnUiThread(() -> {
            statusText.setText(tr("Pelaajadataa ei palautunut","No player data returned"));
            resultsText.setText(diagnostic);
            exportButton.setEnabled(false);
        });
    }

    private boolean ok(ApiResponse a){
        if(a.code==401||a.code==403){showError(tr("WOS Control API-avain puuttuu tai ei kelpaa","WOS Control API key is missing or unauthorized")+" (HTTP "+a.code+").");return false;}
        if(a.code==429){showError(tr("API-kutsujen raja tuli vastaan. Yritä myöhemmin uudelleen.","API rate limit reached. Try again later."));return false;}
        if(a.code<200||a.code>=300){showError("WOS Control HTTP "+a.code+(a.body.isEmpty()?"":": "+shortText(a.body)));return false;}
        return true;
    }

    private JSONObject findBestPlayerObject(Object root)throws Exception{
        List<JSONObject> objects=new ArrayList<>();
        collect(root,objects,0);
        for(JSONObject o:objects){
            if(!stringValue(o,"fid","player_id","chief_id").isEmpty() ||
                    !stringValue(o,"nickname","player_name","chief_name").isEmpty()) return o;
        }
        return root instanceof JSONObject?(JSONObject)root:null;
    }

    private List<PlayerRow> parsePlayers(String body,String requestedState,boolean stateScoped)throws Exception{
        if(body==null||body.trim().isEmpty())return new ArrayList<>();
        Object root=body.trim().startsWith("[")?new JSONArray(body):new JSONObject(body);
        List<JSONObject> objs=new ArrayList<>();
        collect(root,objs,0);
        List<PlayerRow> out=new ArrayList<>();
        for(JSONObject o:objs){
            long might=longValue(o,"might","power","total_power","player_power","personal_power","total_might");
            if(might<=0)continue;

            String rs=stringValue(o,"state_id","state","kid","server","server_id","kingdom","kingdom_id");
            if(!rs.isEmpty() && !normalize(rs).equals(normalize(requestedState)))continue;
            if(rs.isEmpty() && !stateScoped){
                // /state may contain unrelated numeric objects; only accept rows that clearly look like players.
            }

            String name=stringValue(o,"nickname","name","player_name","username","chief_name");
            String fid=stringValue(o,"fid","player_id","chief_id","uid");
            String alliance=stringValue(o,"alliance","alliance_name","alliance_tag","tag","alliance_abbr");
            if(name.isEmpty()&&fid.isEmpty())continue;

            boolean duplicate=false;
            for(PlayerRow old:out){
                if(!fid.isEmpty()&&!old.fid.isEmpty()&&fid.equals(old.fid)){duplicate=true;break;}
                if(fid.isEmpty()&&old.fid.isEmpty()&&!name.isEmpty()&&name.equalsIgnoreCase(old.name)&&might==old.might){duplicate=true;break;}
            }
            if(duplicate)continue;

            out.add(new PlayerRow((int)longValue(o,"rank","ranking","position","place"),name,fid,alliance,might));
        }
        return out;
    }

    private void collect(Object n,List<JSONObject> out,int d)throws Exception{
        if(n==null||d>7)return;
        if(n instanceof JSONObject){
            JSONObject o=(JSONObject)n;
            out.add(o);
            Iterator<String> k=o.keys();
            while(k.hasNext()){
                Object x=o.opt(k.next());
                if(x instanceof JSONObject||x instanceof JSONArray)collect(x,out,d+1);
            }
        }else if(n instanceof JSONArray){
            JSONArray a=(JSONArray)n;
            for(int i=0;i<a.length();i++){
                Object x=a.opt(i);
                if(x instanceof JSONObject||x instanceof JSONArray)collect(x,out,d+1);
            }
        }
    }

    private String summarizeJson(String body,int max){
        try{
            Object root=body.trim().startsWith("[")?new JSONArray(body):new JSONObject(body);
            StringBuilder sb=new StringBuilder();appendScalars(root,sb,"",0,max);
            return sb.length()==0?shortText(body):sb.toString();
        }catch(Exception e){return shortText(body);}
    }
    private void appendScalars(Object node,StringBuilder sb,String prefix,int depth,int max){
        if(depth>3||lineCount(sb)>=max)return;
        if(node instanceof JSONObject){
            JSONObject o=(JSONObject)node;Iterator<String> it=o.keys();
            while(it.hasNext()&&lineCount(sb)<max){
                String k=it.next();Object v=o.opt(k);
                if(v instanceof JSONObject||v instanceof JSONArray)appendScalars(v,sb,prefix+k+".",depth+1,max);
                else if(v!=null&&v!=JSONObject.NULL){String s=String.valueOf(v);if(s.length()<=180)sb.append(prettyKey(prefix+k)).append(": ").append(s).append("\n");}
            }
        }else if(node instanceof JSONArray){
            JSONArray a=(JSONArray)node;for(int i=0;i<a.length()&&i<8&&lineCount(sb)<max;i++)appendScalars(a.opt(i),sb,prefix,depth+1,max);
        }
    }
    private int lineCount(StringBuilder sb){int c=0;for(int i=0;i<sb.length();i++)if(sb.charAt(i)=='\n')c++;return c;}
    private String prettyKey(String k){return k.replace('_',' ').replace("."," › ");}
    private String shortText(String s){if(s==null)return"";s=s.trim();return s.length()>500?s.substring(0,500)+"…":s;}

    private String renderRows(List<PlayerRow> list){
        StringBuilder sb=new StringBuilder();
        int limit=Math.min(list.size(),100);
        for(int i=0;i<limit;i++){
            PlayerRow p=list.get(i);
            sb.append(p.rank).append(". ").append(p.name.isEmpty()?"FID "+p.fid:p.name)
                    .append("\nMight: ").append(formatNumber(p.might));
            if(!p.alliance.isEmpty())sb.append(" • ").append(p.alliance);
            if(!p.fid.isEmpty())sb.append(" • FID ").append(p.fid);
            sb.append("\n\n");
        }
        return sb.toString();
    }

    private void chooseXlsxLocation(){
        if(rows.isEmpty()){
            Toast.makeText(this,tr("Ei pelaajadataa vietäväksi","No player data to export"),Toast.LENGTH_LONG).show();
            return;
        }
        Intent i=new Intent(Intent.ACTION_CREATE_DOCUMENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        i.putExtra(Intent.EXTRA_TITLE,"State_"+currentState+".xlsx");
        startActivityForResult(i,CREATE_XLSX);
    }

    @Override protected void onActivityResult(int rc,int result,Intent data){
        super.onActivityResult(rc,result,data);
        if(rc==CREATE_XLSX&&result==RESULT_OK&&data!=null&&data.getData()!=null)writeXlsx(data.getData());
    }

    private void writeXlsx(Uri uri){
        try(OutputStream out=getContentResolver().openOutputStream(uri)){
            if(out==null)throw new Exception("File open failed");
            List<String[]> personal=new ArrayList<>();
            int rank=1;
            for(PlayerRow p:rows){
                int r=p.rank>0?p.rank:rank;
                personal.add(new String[]{String.valueOf(r),p.name,p.fid,String.valueOf(p.might),p.alliance});
                rank++;
                if(personal.size()>=100)break;
            }
            if(personal.isEmpty())throw new Exception("No player rows loaded");
            XlsxExporter.write(out,personal);
            Toast.makeText(this,tr("Excel tallennettu: ","Excel saved: ")+personal.size()+tr(" pelaajaa"," players"),Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(this,tr("Excel-tallennus epäonnistui: ","Excel save failed: ")+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private String stringValue(JSONObject o,String...ks){
        for(String k:ks){Object v=o.opt(k);if(v!=null&&v!=JSONObject.NULL){String s=String.valueOf(v).trim();if(!s.isEmpty()&&!s.equals("{}")&&!s.equals("[]"))return s;}}return"";
    }
    private long longValue(JSONObject o,String...ks){
        for(String k:ks){
            Object v=o.opt(k);if(v==null||v==JSONObject.NULL)continue;
            try{
                if(v instanceof Number)return((Number)v).longValue();
                String s=String.valueOf(v).trim().replace(" ","").replace(",","");
                if(s.endsWith("B")||s.endsWith("b"))return(long)(Double.parseDouble(s.substring(0,s.length()-1))*1_000_000_000L);
                if(s.endsWith("M")||s.endsWith("m"))return(long)(Double.parseDouble(s.substring(0,s.length()-1))*1_000_000L);
                if(s.endsWith("K")||s.endsWith("k"))return(long)(Double.parseDouble(s.substring(0,s.length()-1))*1_000L);
                return(long)Double.parseDouble(s.replaceAll("[^0-9.\\-]",""));
            }catch(Exception ignored){}
        }
        return 0;
    }
    private String normalize(String v){return v==null?"":v.replace("#","").replaceAll("[^0-9]","");}
    private String formatNumber(long n){return String.format(java.util.Locale.US,"%,d",n).replace(',',' ');}
    private void showError(String m){runOnUiThread(()->{statusText.setText(m);resultsText.setText(tr("Ei dataa.","No data."));exportButton.setEnabled(false);});}
    private void hideKeyboard(){InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);if(imm!=null)imm.hideSoftInputFromWindow(stateInput.getWindowToken(),0);}

    private static class ApiResponse{final int code;final String body;ApiResponse(int c,String b){code=c;body=b==null?"":b;}}
    private static class PlayerRow{int rank;final String name,fid,alliance;final long might;PlayerRow(int r,String n,String f,String a,long m){rank=r;name=n;fid=f;alliance=a;might=m;}}
}
