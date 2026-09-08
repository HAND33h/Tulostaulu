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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataSourcesActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private EditText state;
    private TextView status,data;
    private boolean english;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);
        english="en".equals(p.getString("lang","fi"));
        ScrollView sc=new ScrollView(this);
        LinearLayout root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(20),dp(18),dp(40));root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        root.addView(text("🧊 "+tr("WOS DATAKESKUS","WOS DATA CENTER"),27,true,Color.WHITE));
        root.addView(text(tr("Avoin state- ja pelaajadata samaan näkymään.","Open state and player data in one view."),13,false,Color.rgb(170,201,218)));
        state=new EditText(this);state.setText(p.getString("state","1674"));state.setHint(tr("Serverinumero","State number"));state.setSingleLine();state.setInputType(InputType.TYPE_CLASS_NUMBER);state.setTextColor(Color.WHITE);state.setHintTextColor(Color.LTGRAY);state.setBackgroundColor(Color.rgb(31,72,96));state.setPadding(dp(12),dp(12),dp(12),dp(12));root.addView(state,margin(0,16,0,8));
        Button scan=button(tr("HAE JULKINEN STATE-DATA","LOAD PUBLIC STATE DATA"));root.addView(scan);
        status=text(tr("Valmis","Ready"),13,true,Color.rgb(126,211,255));root.addView(status,margin(0,14,0,6));
        data=text(p.getString("public_data_snapshot",tr("Ei vielä dataa.","No data yet.")),13,false,Color.rgb(226,239,247));data.setTextIsSelectable(true);root.addView(data);

        root.addView(text(tr("LÄHTEET","SOURCES"),12,true,Color.rgb(119,205,255)),margin(0,20,0,6));
        addSource(root,"KVK.GG",tr("State leaderboard: ID, nimi, alliance, Power, KP, deaths, healed, kill/heal, troop power.","State leaderboard: ID, name, alliance, Power, KP, deaths, healed, kill/heal, troop power."),"https://kvk.gg/kingdom/"+p.getString("state","1674"));
        addSource(root,"WOS Control",tr("FID/player, state, transfer ja alliance API.","FID/player, state, transfer and alliance API."),"https://woscontrol.com/api-docs");
        addSource(root,"WOS Observer",tr("Power-historia, pelaajaidentiteetti, alliancet ja event-havainnot.","Power history, player identity, alliances and event observations."),"https://wos-observer.com/");
        addSource(root,"WSCO",tr("Buildings, furnace, gear, charms, pets, research, VIP, War Academy ja JSON-taulut.","Buildings, furnace, gear, charms, pets, research, VIP, War Academy and JSON tables."),"https://www.whiteoutsurvival-community.com/tools/database/");
        addSource(root,"WOS Guru",tr("SvS/state-intel ja community-laskurit.","SvS/state intel and community calculators."),"https://wosguru.com/svs-intel");

        scan.setOnClickListener(v->{String s=state.getText().toString().trim();if(s.isEmpty()){Toast.makeText(this,tr("Anna serveri","Enter state"),Toast.LENGTH_SHORT).show();return;}getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("state",s).apply();status.setText(tr("Haetaan…","Loading…"));new Thread(()->loadKvk(s)).start();});
        setContentView(sc);
    }

    private void loadKvk(String s){
        Result r=get("https://kvk.gg/kingdom/"+Uri.encode(s));
        StringBuilder out=new StringBuilder();
        out.append("STATE ").append(s).append("\n").append(tr("Haettu: ","Fetched: ")).append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(new Date())).append("\n\nKVK.GG HTTP ").append(r.code).append("\n");
        if(r.code>=200&&r.code<300){
            List<Row> rows=parse(r.body);
            out.append(tr("Tunnistettuja leaderboard-rivejä: ","Detected leaderboard rows: ")).append(rows.size()).append("\n\n");
            for(int i=0;i<Math.min(100,rows.size());i++){Row x=rows.get(i);out.append(i+1).append(". ").append(x.name).append(" | ID ").append(x.id).append(" | ").append(x.alliance).append(" | Power ").append(x.power).append(" | KP ").append(x.kp).append(" | Deaths ").append(x.deaths).append(" | Healed ").append(x.healed).append(" | K/H ").append(x.ratio).append(" | Troop ").append(x.troop).append("\n");}
            if(rows.isEmpty())out.append(tr("Sivu vastasi, mutta taulukkoa ei saatu jäsennettyä tässä versiossa.\n","Page responded, but the table could not be parsed in this version.\n"));
        }else out.append(shortText(r.body)).append("\n");
        out.append("\nFIELD MAP\nsource_id | fid | nickname | state_id | alliance | total_power | hero_power | troop_power | KP | kills | deaths | healed | furnace_level | chief_gear | charms | pets | experts | source | observed_at\n");
        out.append(tr("KVK.GG:n ID pidetään source_id:nä, kunnes FID varmistetaan toisesta lähteestä.","KVK.GG ID stays source_id until FID is confirmed by another source."));
        String snapshot=out.toString();getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("public_data_snapshot",snapshot).apply();runOnUiThread(()->{data.setText(snapshot);status.setText(tr("Data haettu ja snapshot tallennettu ✓","Data loaded and snapshot saved ✓"));});
    }

    private Result get(String u){HttpURLConnection c=null;try{c=(HttpURLConnection)new URL(u).openConnection();c.setConnectTimeout(15000);c.setReadTimeout(25000);c.setRequestProperty("User-Agent","Mozilla/5.0 WOS-Bunny-King/1.0");int code=c.getResponseCode();BufferedReader br=new BufferedReader(new InputStreamReader(code>=200&&code<300?c.getInputStream():c.getErrorStream(),StandardCharsets.UTF_8));StringBuilder b=new StringBuilder();String x;while((x=br.readLine())!=null)b.append(x).append('\n');br.close();return new Result(code,b.toString());}catch(Exception e){return new Result(-1,e.getMessage());}finally{if(c!=null)c.disconnect();}}
    private List<Row> parse(String html){List<Row> out=new ArrayList<>();Matcher rm=Pattern.compile("(?is)<tr[^>]*>(.*?)</tr>").matcher(html==null?"":html);Pattern td=Pattern.compile("(?is)<t[dh][^>]*>(.*?)</t[dh]>");while(rm.find()){Matcher cm=td.matcher(rm.group(1));List<String> c=new ArrayList<>();while(cm.find())c.add(clean(cm.group(1)));if(c.size()<9)continue;String id=c.get(1).replaceAll("[^0-9]","");if(id.isEmpty())continue;out.add(new Row(id,c.get(2),c.get(3),c.get(4),c.get(5),c.get(6),c.get(7),c.get(8),c.size()>9?c.get(9):""));}return out;}
    private String clean(String s){return s.replaceAll("(?is)<script.*?</script>"," ").replaceAll("(?is)<style.*?</style>"," ").replaceAll("<[^>]+>"," ").replace("&nbsp;"," ").replace("&amp;","&").replaceAll("\\s+"," ").trim();}
    private String shortText(String s){String x=clean(s==null?"":s);return x.length()>600?x.substring(0,600)+"…":x;}
    private void addSource(LinearLayout root,String title,String desc,String url){root.addView(text(title,15,true,Color.WHITE));root.addView(text(desc,12,false,Color.rgb(170,201,218)));Button b=button(tr("AVAA","OPEN"));root.addView(b,margin(0,4,0,12));b.setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url))));}
    private TextView text(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setTextColor(Color.WHITE);b.setAllCaps(false);b.setTypeface(Typeface.DEFAULT_BOLD);b.setBackgroundColor(Color.rgb(39,165,211));return b;}
    private LinearLayout.LayoutParams margin(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private String tr(String fi,String en){return english?en:fi;}
    static class Result{int code;String body;Result(int c,String b){code=c;body=b==null?"":b;}}
    static class Row{String id,name,alliance,power,kp,deaths,healed,ratio,troop;Row(String i,String n,String a,String p,String k,String d,String h,String r,String t){id=i;name=n;alliance=a;power=p;kp=k;deaths=d;healed=h;ratio=r;troop=t;}}
}
