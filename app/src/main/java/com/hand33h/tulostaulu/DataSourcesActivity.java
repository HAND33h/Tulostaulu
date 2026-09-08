package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DataSourcesActivity extends Activity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        ScrollView sc=new ScrollView(this);
        LinearLayout root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18),dp(20),dp(18),dp(40));
        root.setBackgroundColor(Color.rgb(7,23,39));
        sc.addView(root);

        root.addView(text("🧊 WOS DATA SOURCES",27,true,Color.WHITE));
        root.addView(text("Only sources verified to be Whiteout Survival are accepted into the app database.",13,false,Color.rgb(170,201,218)));

        Button hub=button("OPEN LOCAL WOS DATA HUB");
        root.addView(hub,margin(0,16,0,18));
        hub.setOnClickListener(v->startActivity(new Intent(this,DataHubActivity.class)));

        addSource(root,"WOS Control","Player/FID, state, alliance and transfer API. Used as the primary current-player source when the API key is available.","https://woscontrol.com/api-docs");
        addSource(root,"WOS Observer","Observed Whiteout Survival player identity, power history, alliances and event results. Treated as snapshot/observed data, not a complete live population.","https://wos-observer.com/");
        addSource(root,"Whiteout Survival Community (WSCO)","Structured Whiteout Survival reference data: furnace, chief gear, charms, pets, research, VIP, War Academy and other calculator datasets.","https://www.whiteoutsurvival-community.com/tools/database/");
        addSource(root,"WoS Guru","Whiteout Survival SvS/state intelligence and leaderboard-oriented analysis.","https://wosguru.com/svs-intel");
        addSource(root,"State 646","Whiteout Survival state site with a vision-powered leaderboard scanner and player database. Used as an architecture/reference source.","https://state646.com/");
        addSource(root,"State 2180 Event Hub","Whiteout Survival event rankings with Auto-OCR. Useful reference for OCR field extraction and event snapshots.","https://event-2180.com/event-hub/svs-ranking.php");
        addSource(root,"State 3232","Whiteout Survival public state site with SvS records, player event rankings and alliance information.","https://state3232.com/");
        addSource(root,"WOS Forge","Whiteout Survival community wiki/reference data for progression, gear, pets, experts and transfer-related systems.","https://wiki.wosforge.org/");

        root.addView(text("REJECTED SOURCE",13,true,Color.rgb(255,170,170)),margin(0,18,0,4));
        root.addView(text("KVK.GG is not Whiteout Survival and is excluded from all player/state imports.",13,false,Color.rgb(230,205,205)));

        root.addView(text("SOURCE RULES",13,true,Color.rgb(119,205,255)),margin(0,18,0,4));
        root.addView(text("Every imported value keeps source_type, source, observed_at, fetched_at and confidence. Static calculator data is never presented as live player data. Observed leaderboard data is never presented as a complete state population.",13,false,Color.rgb(220,232,240)));

        setContentView(sc);
    }

    private void addSource(LinearLayout root,String title,String desc,String url){
        root.addView(text(title,15,true,Color.WHITE));
        root.addView(text(desc,12,false,Color.rgb(170,201,218)));
        Button b=button("OPEN");
        root.addView(b,margin(0,4,0,12));
        b.setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
    }

    private TextView text(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setTextColor(Color.WHITE);b.setAllCaps(false);b.setTypeface(Typeface.DEFAULT_BOLD);b.setBackgroundColor(Color.rgb(39,165,211));return b;}
    private LinearLayout.LayoutParams margin(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}
}
