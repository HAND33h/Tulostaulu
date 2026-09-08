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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class UnifiedActivity extends Activity {
    private static final String API="https://woscontrol.com/api/v1";
    private EditText server,key;
    private TextView status,result;
    private final List<Row> rows=new ArrayList<>();

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        SharedPreferences p=getSharedPreferences("wos_tulostaulu",MODE_PRIVATE);
        ScrollView sc=new ScrollView(this);LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(36,48,36,60);root.setBackgroundColor(Color.rgb(234,244,251));sc.addView(root);
        root.addView(t("👑 BUNNY KING • WOS",28,true,Gravity.CENTER));
        root.addView(t("YHDISTETTY WOS DATAKESKUS",18,true,Gravity.CENTER));
        root.addView(t("WOS Control + WOS Observer + WoS Guru + WhiteoutSurvival.dev",13,false,Gravity.CENTER));
        server=e("Serverinumero",p.getString("state","77"),true);key=e("WOS Control API key (wos_...)",p.getString("api_key",""),false);root.addView(server,m(0,18,0,8));root.addView(key,m(0,0,0,12));
        Button top=b("HAE SERVERIN TOP 100",Color.rgb(20,125,190));root.addView(top,m(0,0,0,8));
        Button advanced=b("AVAA WOS CONTROL -NÄKYMÄ",Color.rgb(54,96,130));root.addView(advanced,m(0,0,0,16));
        root.addView(t("JULKISET VARALÄHTEET",15,true,Gravity.CENTER));
        Button ob=b("WOS OBSERVER",Color.rgb(75,105,123)),gu=b("WOS GURU",Color.rgb(75,105,123)),dev=b("WHITEOUTSURVIVAL.DEV",Color.rgb(75,105,123)),docs=b("WOS CONTROL API / HANKI AVAIN",Color.rgb(75,105,123));
        root.addView(ob,m(0,8,0,6));root.addView(gu,m(0,0,0,6));root.addView(dev,m(0,0,0,6));root.addView(docs,m(0,0,0,18));
        status=t("Valmis. TOP100 käyttää WOS Controlia. Muut lähteet ovat mukana varalähteinä.",15,true,Gravity.CENTER);result=t("Ei ladattua dataa.",14,false,Gravity.START);root.addView(status);root.addView(result,m(0,12,0,0));root.addView(t("App owner: HAND33h",12,true,Gravity.CENTER),m(0,28,0,0));
        top.setOnClickListener(v->load());advanced.setOnClickListener(v->startActivity(new Intent(this,MainActivity.class)));ob.setOnClickListener(v->open("https://wos-observer.com/"));gu.setOnClickListener(v->open("https://wosguru.com/"));dev.setOnClickListener(v->open("https://whiteoutsurvival.dev/"));docs.setOnClickListener(v->open("https://woscontrol.com/api-docs"));setContentView(sc);
    }

    private void load(){String s=server.getText().toString().trim(),k=key.getText().toString().trim();if(s.isEmpty()){toast("Anna serverinumero");return;}if(k.isEmpty()){toast("Syötä oma WOS Control API-avain");return;}getSharedPreferences("wos_tulostaulu",MODE_PRIVATE).edit().putString("state",s).putString("api_key",k).apply();status.setText("Haetaan serverin "+s+" TOP 100…");result.setText("Haetaan…");new Thread(()->fetch(s,k)).start();}
    private void fetch(String state,String key){Resp st=get("/state/"+Uri.encode(state),key);if(!ok(st))return;Resp lr=get("/leaderboard?state_id="+Uri.encode(state),key);if(lr.code<200||lr.code>=300){err("Leaderboard HTTP "+lr.code+": "+shortx(lr.body));return;}try{rows.clear();Object root=lr.body.trim().startsWith("[")?new JSONArray(lr.body):new JSONObject(lr.body);List<JSONObject> all=new ArrayList<>();collect(root,all,0);for(JSONObject o:all){long p=num(o,"might","power","total_power","player_power");if(p<=0)continue;String rs=str(o,"state_id","state","server","server_id","kingdom");if(!rs.isEmpty()&&!digits(rs).equals(digits(state)))continue;String name=str(o,"nickname","name","player_name","username"),fid=str(o,"fid","player_id","chief_id"),a=str(o,"alliance","alliance_name","alliance_tag","tag");if(name.isEmpty()&&fid.isEmpty())continue;rows.add(new Row(name,fid,a,p));}rows.sort(Comparator.comparingLong((Row r)->r.power).reversed());if(rows.size()>100)rows.subList(100,rows.size()).clear();if(rows.isEmpty()){runOnUiThread(()->{status.setText("Serveridata toimii, mutta /leaderboard ei palauttanut serverikohtaista Power/Might TOP100 -listaa.");result.setText("WOS Control /state vastasi onnistuneesti. Voit käyttää alla olevia WOS Observer / WoS Guru / WhiteoutSurvival.dev -lähteitä täydentävänä lähteenä.");});return;}StringBuilder out=new StringBuilder();for(int i=0;i<rows.size();i++){Row r=rows.get(i);out.append(i+1).append(". ").append(r.name.isEmpty()?r.fid:r.name).append(" — ").append(fmt(r.power));if(!r.alliance.isEmpty())out.append(" [").append(r.alliance).append("]");if(!r.fid.isEmpty())out.append(" • ").append(r.fid);out.append('\n');}runOnUiThread(()->{status.setText("TOP "+rows.size()+" ladattu ✓");result.setText(out.toString());});}catch(Exception e){err("Vastauksen käsittely epäonnistui: "+e.getMessage());}}
    private Resp get(String path,String key){HttpURLConnection c=null;try{c=(HttpURLConnection)new URL(API+path).openConnection();c.setConnectTimeout(15000);c.setReadTimeout(25000);c.setRequestProperty("Accept","application/json");c.setRequestProperty("X-API-Key",key);c.setRequestProperty("Authorization","Bearer "+key);int code=c.getResponseCode();BufferedReader r=new BufferedReader(new InputStreamReader(code>=200&&code<300?c.getInputStream():c.getErrorStream(),StandardCharsets.UTF_8));StringBuilder b=new StringBuilder();String line;while((line=r.readLine())!=null)b.append(line);r.close();return new Resp(code,b.toString());}catch(Exception e){return new Resp(-1,e.getMessage());}finally{if(c!=null)c.disconnect();}}
    private boolean ok(Resp r){if(r.code==401||r.code==403){err("API-avain ei kelpaa tai käyttöoikeus puuttuu (HTTP "+r.code+")");return false;}if(r.code==429){err("API-kutsujen raja tuli vastaan.");return false;}if(r.code<200||r.code>=300){err("WOS Control HTTP "+r.code+": "+shortx(r.body));return false;}return true;}
    private void collect(Object n,List<JSONObject> out,int d){if(n==null||d>6)return;if(n instanceof JSONObject){JSONObject o=(JSONObject)n;out.add(o);Iterator<String> it=o.keys();while(it.hasNext())collect(o.opt(it.next()),out,d+1);}else if(n instanceof JSONArray){JSONArray a=(JSONArray)n;for(int i=0;i<a.length();i++)collect(a.opt(i),out,d+1);}}
    private String str(JSONObject o,String...ks){for(String k:ks){Object v=o.opt(k);if(v!=null&&v!=JSONObject.NULL){String s=String.valueOf(v).trim();if(!s.isEmpty()&&!"null".equalsIgnoreCase(s))return s;}}return "";}
    private long num(JSONObject o,String...ks){for(String k:ks){Object v=o.opt(k);if(v instanceof Number)return ((Number)v).longValue();try{String s=String.valueOf(v).replaceAll("[^0-9]","");if(!s.isEmpty())return Long.parseLong(s);}catch(Exception ignored){}}return 0;}
    private String digits(String s){return s==null?"":s.replaceAll("[^0-9]","");}private String fmt(long n){return String.format(Locale.US,"%,d",n).replace(',',' ');}private String shortx(String s){if(s==null)return"";return s.length()>180?s.substring(0,180)+"…":s;}
    private void open(String u){startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(u)));}private void err(String s){runOnUiThread(()->{status.setText("Virhe");result.setText(s);});}private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_LONG).show();}
    private TextView t(String s,int z,boolean bold,int g){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(Color.rgb(18,43,64));v.setGravity(g);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}private EditText e(String h,String val,boolean numeric){EditText x=new EditText(this);x.setHint(h);x.setText(val);x.setSingleLine(true);x.setBackgroundColor(Color.WHITE);x.setPadding(20,16,20,16);x.setTextColor(Color.rgb(18,43,64));x.setInputType(numeric?InputType.TYPE_CLASS_NUMBER:InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);return x;}private Button b(String s,int c){Button x=new Button(this);x.setText(s);x.setTextColor(Color.WHITE);x.setTypeface(Typeface.DEFAULT_BOLD);x.setBackgroundColor(c);return x;}private LinearLayout.LayoutParams m(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(l,t,r,b);return p;}
    static class Resp{final int code;final String body;Resp(int c,String b){code=c;body=b==null?"":b;}}static class Row{final String name,fid,alliance;final long power;Row(String n,String f,String a,long p){name=n;fid=f;alliance=a;power=p;}}
}
