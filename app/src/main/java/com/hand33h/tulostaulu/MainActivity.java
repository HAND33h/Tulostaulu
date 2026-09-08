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
import android.view.View;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity {
    private static final int CREATE_CSV = 9001;
    private static final String PREFS = "wos_tulostaulu";
    private static final String API_BASE = "https://woscontrol.com/api/v1";
    private static final int BG = Color.rgb(234, 244, 251);
    private static final int TEXT = Color.rgb(18, 43, 64);
    private static final int MUTED = Color.rgb(68, 94, 113);
    private static final int ACCENT = Color.rgb(20, 125, 190);

    private EditText stateInput, apiKeyInput;
    private TextView statusText, resultsText;
    private Button exportButton;
    private final List<PlayerRow> rows = new ArrayList<>();
    private String currentState = "77";
    private boolean english = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        english = "en".equals(prefs.getString("lang", "fi"));

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        scroll.setBackgroundColor(BG);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(48, 72, 48, 72);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(BG);
        scroll.addView(root);

        TextView title = text(english ? "WOS SCOREBOARD" : "WOS TULOSTAULU", 30, true, TEXT, Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(-1, -2));
        TextView subtitle = text(english ? "State / Server rankings" : "State / Server -ranking", 17, true, MUTED, Gravity.CENTER);
        LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, -2); sp.setMargins(0, 12, 0, 18); root.addView(subtitle, sp);

        TextView languageLabel = text(english ? "Language" : "Kieli", 15, true, TEXT, Gravity.START);
        root.addView(languageLabel, new LinearLayout.LayoutParams(-1, -2));
        Spinner language = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"🇫🇮 Suomi", "🇬🇧 English"});
        language.setAdapter(adapter);
        language.setSelection(english ? 1 : 0);
        root.addView(language, new LinearLayout.LayoutParams(-1, -2));
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean nextEnglish = position == 1;
                if (nextEnglish != english) {
                    getSharedPreferences(PREFS, MODE_PRIVATE).edit().putString("lang", nextEnglish ? "en" : "fi").apply();
                    recreate();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        stateInput = input(english ? "Server number" : "Serverinumero", prefs.getString("state", "77"), true);
        LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(-1, -2); ip.setMargins(0, 18, 0, 0); root.addView(stateInput, ip);
        apiKeyInput = input("WOS Control API key (wos_...)", prefs.getString("api_key", ""), false);
        LinearLayout.LayoutParams kp = new LinearLayout.LayoutParams(-1, -2); kp.setMargins(0, 18, 0, 0); root.addView(apiKeyInput, kp);

        Button loadButton = button(english ? "LOAD RANKINGS" : "LATAA TULOSTAULU", ACCENT, 17);
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(-1, -2); bp.setMargins(0, 28, 0, 14); root.addView(loadButton, bp);
        exportButton = button(english ? "EXPORT CSV / EXCEL" : "VIE CSV / EXCEL", Color.rgb(48, 95, 130), 16);
        exportButton.setEnabled(false); root.addView(exportButton, new LinearLayout.LayoutParams(-1, -2));

        statusText = text(english ? "Choose a server and enter your WOS Control API key." : "Valitse serveri ja syötä oma WOS Control API-avain.", 18, true, TEXT, Gravity.CENTER);
        LinearLayout.LayoutParams stp = new LinearLayout.LayoutParams(-1, -2); stp.setMargins(0, 28, 0, 20); root.addView(statusText, stp);
        resultsText = text(english ? "No data loaded." : "Ei ladattua dataa.", 16, false, TEXT, Gravity.START);
        resultsText.setLineSpacing(0, 1.12f); root.addView(resultsText, new LinearLayout.LayoutParams(-1, -2));
        TextView credit = text("Powered by WOS Control • App owner: HAND33h", 13, true, MUTED, Gravity.CENTER);
        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(-1, -2); cp.setMargins(0, 42, 0, 0); root.addView(credit, cp);

        loadButton.setOnClickListener(v -> loadState());
        exportButton.setOnClickListener(v -> chooseCsvLocation());
        setContentView(scroll);
    }

    private TextView text(String s, int size, boolean bold, int color, int gravity) {
        TextView v = new TextView(this); v.setText(s); v.setTextSize(size); v.setTextColor(color); v.setGravity(gravity);
        if (bold) v.setTypeface(Typeface.DEFAULT_BOLD); return v;
    }
    private EditText input(String hint, String value, boolean numeric) {
        EditText e = new EditText(this); e.setHint(hint); e.setHintTextColor(Color.rgb(105,120,132)); e.setTextColor(TEXT);
        e.setBackgroundColor(Color.WHITE); e.setPadding(24,18,24,18); e.setText(value); e.setSingleLine(true);
        e.setInputType(numeric ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        if (numeric) { e.setTextSize(22); e.setGravity(Gravity.CENTER); } return e;
    }
    private Button button(String label, int color, int size) {
        Button b = new Button(this); b.setText(label); b.setTextColor(Color.WHITE); b.setTextSize(size); b.setTypeface(Typeface.DEFAULT_BOLD); b.setBackgroundColor(color); return b;
    }
    private String tr(String fi, String en) { return english ? en : fi; }

    private void loadState() {
        String state = stateInput.getText().toString().trim();
        String apiKey = apiKeyInput.getText().toString().trim();
        if (state.isEmpty()) { Toast.makeText(this, tr("Anna serverinumero", "Enter a server number"), Toast.LENGTH_SHORT).show(); return; }
        if (apiKey.isEmpty()) { Toast.makeText(this, tr("Syötä oma WOS Control API-avain", "Enter your WOS Control API key"), Toast.LENGTH_LONG).show(); return; }
        currentState = state;
        getSharedPreferences(PREFS, MODE_PRIVATE).edit().putString("state", state).putString("api_key", apiKey).apply();
        rows.clear(); exportButton.setEnabled(false); resultsText.setText(tr("Haetaan...", "Loading..."));
        statusText.setText(tr("Haetaan serverin " + state + " dataa…", "Loading server " + state + " data…")); hideKeyboard();
        new Thread(() -> fetchLeaderboard(state, apiKey)).start();
    }

    private void fetchLeaderboard(String state, String apiKey) {
        HttpURLConnection c = null;
        try {
            URL url = new URL(API_BASE + "/leaderboard?state_id=" + URLEncoder.encode(state, StandardCharsets.UTF_8.name()));
            c = (HttpURLConnection) url.openConnection(); c.setRequestMethod("GET"); c.setConnectTimeout(15000); c.setReadTimeout(20000);
            c.setRequestProperty("Accept", "application/json"); c.setRequestProperty("X-API-Key", apiKey); c.setRequestProperty("Authorization", "Bearer " + apiKey);
            int code = c.getResponseCode();
            BufferedReader r = new BufferedReader(new InputStreamReader(code >= 200 && code < 300 ? c.getInputStream() : c.getErrorStream(), StandardCharsets.UTF_8));
            StringBuilder body = new StringBuilder(); String line; while ((line = r.readLine()) != null) body.append(line); r.close();
            if (code == 401 || code == 403) { showError(tr("API-avain ei kelpaa tai sillä ei ole oikeutta", "API key is invalid or unauthorized") + " (HTTP " + code + ")."); return; }
            if (code == 429) { showError(tr("API-kutsujen raja tuli vastaan. Yritä myöhemmin uudelleen.", "API rate limit reached. Try again later.")); return; }
            if (code < 200 || code >= 300) { String msg = extractError(body.toString()); showError("WOS Control HTTP " + code + (msg.isEmpty() ? "" : ": " + msg)); return; }
            List<PlayerRow> parsed = parsePlayers(body.toString(), state);
            if (parsed.isEmpty()) { showError(tr("Yhteys toimii, mutta API ei palauttanut tämän serverin Might-rankingrivejä.", "Connection works, but the API did not return Might ranking rows for this server.")); return; }
            Collections.sort(parsed, Comparator.comparingLong((PlayerRow p) -> p.might).reversed());
            for (int i=0;i<parsed.size();i++) if (parsed.get(i).rank <= 0) parsed.get(i).rank = i+1;
            rows.clear(); rows.addAll(parsed);
            runOnUiThread(() -> { statusText.setText(tr("Serveri ", "Server ") + state + " • " + rows.size() + tr(" pelaajaa ✓", " players ✓")); resultsText.setText(renderRows(rows)); exportButton.setEnabled(true); });
        } catch (Exception e) { showError(tr("Datan haku epäonnistui: ", "Data load failed: ") + e.getMessage()); }
        finally { if (c != null) c.disconnect(); }
    }

    private List<PlayerRow> parsePlayers(String body, String state) throws Exception {
        Object root = body.trim().startsWith("[") ? new JSONArray(body) : new JSONObject(body); List<JSONObject> objs = new ArrayList<>(); collect(root, objs, 0); List<PlayerRow> out = new ArrayList<>();
        for (JSONObject o: objs) {
            long might = longValue(o,"might","power","total_power","player_power"); if (might <= 0) continue;
            String rs = stringValue(o,"state_id","state","kid","server","server_id"); if (!rs.isEmpty() && !normalize(rs).equals(normalize(state))) continue;
            String name=stringValue(o,"nickname","name","player_name","username"), fid=stringValue(o,"fid","player_id","id"), alliance=stringValue(o,"alliance","alliance_name","alliance_tag","tag");
            if (name.isEmpty() && fid.isEmpty()) continue; out.add(new PlayerRow((int)longValue(o,"rank","ranking","position"),name,fid,alliance,might));
        }
        List<PlayerRow> dedup = new ArrayList<>();
        for (PlayerRow p: out) { boolean exists=false; for (PlayerRow q:dedup) if ((!p.fid.isEmpty()&&p.fid.equals(q.fid)) || (p.fid.isEmpty()&&!p.name.isEmpty()&&p.name.equals(q.name)&&p.might==q.might)) { exists=true; break; } if (!exists) dedup.add(p); }
        return dedup;
    }
    private void collect(Object n,List<JSONObject> out,int d) throws Exception { if(n==null||d>6)return; if(n instanceof JSONObject){JSONObject o=(JSONObject)n;out.add(o);Iterator<String> k=o.keys();while(k.hasNext()){Object x=o.opt(k.next());if(x instanceof JSONObject||x instanceof JSONArray)collect(x,out,d+1);}} else if(n instanceof JSONArray){JSONArray a=(JSONArray)n;for(int i=0;i<a.length();i++){Object x=a.opt(i);if(x instanceof JSONObject||x instanceof JSONArray)collect(x,out,d+1);}} }
    private String renderRows(List<PlayerRow> list) { StringBuilder sb=new StringBuilder(); int limit=Math.min(list.size(),100); for(int i=0;i<limit;i++){PlayerRow p=list.get(i);sb.append(p.rank).append(". ").append(p.name.isEmpty()?"FID "+p.fid:p.name).append("\nMight: ").append(formatNumber(p.might));if(!p.alliance.isEmpty())sb.append(" • ").append(p.alliance);if(!p.fid.isEmpty())sb.append(" • FID ").append(p.fid);sb.append("\n\n");} if(list.size()>limit)sb.append(tr("Näytetään 100 ensimmäistä. CSV sisältää kaikki rivit.","Showing first 100. CSV contains all rows."));return sb.toString(); }
    private void chooseCsvLocation(){if(rows.isEmpty())return;Intent i=new Intent(Intent.ACTION_CREATE_DOCUMENT);i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("text/csv");i.putExtra(Intent.EXTRA_TITLE,"WOS_state_"+currentState+"_might.csv");startActivityForResult(i,CREATE_CSV);}
    @Override protected void onActivityResult(int rc,int result,Intent data){super.onActivityResult(rc,result,data);if(rc==CREATE_CSV&&result==RESULT_OK&&data!=null&&data.getData()!=null)writeCsv(data.getData());}
    private void writeCsv(Uri uri){try(OutputStream out=getContentResolver().openOutputStream(uri)){if(out==null)throw new Exception(tr("Tiedostoa ei voitu avata","File could not be opened"));StringBuilder csv=new StringBuilder("\uFEFFRank;Player;FID;Alliance;Might;State\r\n");for(PlayerRow p:rows)csv.append(p.rank).append(';').append(csv(p.name)).append(';').append(csv(p.fid)).append(';').append(csv(p.alliance)).append(';').append(p.might).append(';').append(csv(currentState)).append("\r\n");out.write(csv.toString().getBytes(StandardCharsets.UTF_8));Toast.makeText(this,tr("CSV tallennettu ✓","CSV saved ✓"),Toast.LENGTH_LONG).show();}catch(Exception e){Toast.makeText(this,tr("Tallennus epäonnistui: ","Save failed: ")+e.getMessage(),Toast.LENGTH_LONG).show();}}
    private String csv(String v){if(v==null)return"";return"\""+v.replace("\"","\"\"")+"\"";}
    private String extractError(String b){try{return stringValue(new JSONObject(b),"error","message","detail");}catch(Exception e){return b.length()>160?b.substring(0,160):b;}}
    private String stringValue(JSONObject o,String...ks){for(String k:ks){Object v=o.opt(k);if(v!=null&&v!=JSONObject.NULL){String s=String.valueOf(v).trim();if(!s.isEmpty()&&!s.equals("{}")&&!s.equals("[]"))return s;}}return"";}
    private long longValue(JSONObject o,String...ks){for(String k:ks){Object v=o.opt(k);if(v==null||v==JSONObject.NULL)continue;try{String s=String.valueOf(v).replace(" ","").replace(",","");if(s.endsWith("B")||s.endsWith("b"))return(long)(Double.parseDouble(s.substring(0,s.length()-1))*1_000_000_000L);if(s.endsWith("M")||s.endsWith("m"))return(long)(Double.parseDouble(s.substring(0,s.length()-1))*1_000_000L);return(long)Double.parseDouble(s);}catch(Exception ignored){}}return 0;}
    private String normalize(String v){return v.replace("#","").replaceAll("[^0-9]","");}
    private String formatNumber(long n){return String.format(java.util.Locale.US,"%,d",n).replace(',',' ');}
    private void showError(String m){runOnUiThread(()->{statusText.setText(m);resultsText.setText(tr("Ei ranking-dataa.","No ranking data."));exportButton.setEnabled(false);});}
    private void hideKeyboard(){InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);imm.hideSoftInputFromWindow(stateInput.getWindowToken(),0);}
    private static class PlayerRow{int rank;final String name,fid,alliance;final long might;PlayerRow(int r,String n,String f,String a,long m){rank=r;name=n;fid=f;alliance=a;might=m;}}
}
