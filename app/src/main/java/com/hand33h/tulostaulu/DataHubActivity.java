package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataHubActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private EditText stateInput;
    private TextView output;
    private PlayerDataStore store;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        store=new PlayerDataStore(this);
        SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);
        ScrollView sc=new ScrollView(this);
        LinearLayout root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18),dp(20),dp(18),dp(40));
        root.setBackgroundColor(Color.rgb(7,23,39));
        sc.addView(root);

        root.addView(label("🧊 WOS DATA HUB",28,true,Color.WHITE));
        root.addView(label("Verified Whiteout Survival sources only",13,true,Color.rgb(119,205,255)),mp(0,4,0,16));

        stateInput=new EditText(this);
        stateInput.setHint("State / server");
        stateInput.setText(p.getString("state","77"));
        stateInput.setSingleLine(true);
        stateInput.setTextColor(Color.WHITE);
        stateInput.setHintTextColor(Color.rgb(160,180,195));
        stateInput.setBackgroundColor(Color.rgb(18,48,70));
        stateInput.setPadding(dp(14),dp(12),dp(14),dp(12));
        root.addView(stateInput);

        Button load=new Button(this);
        load.setText("LOAD LOCAL PLAYER DATABASE");
        load.setTextColor(Color.WHITE);
        load.setTypeface(Typeface.DEFAULT_BOLD);
        load.setBackgroundColor(Color.rgb(20,126,196));
        root.addView(load,mp(0,10,0,14));

        root.addView(label("DATA FIELDS",14,true,Color.rgb(119,205,255)));
        root.addView(label("FID • nickname • state • alliance • personal power • combat power • hero power • hero total power • troop power • pet power • expert power • furnace • kills • exploration • labyrinth • chief gear • chief charms • rank • event • source • observed time • fetched time • confidence",13,false,Color.rgb(220,232,240)),mp(0,6,0,14));

        root.addView(label("VERIFIED SOURCE TYPES",14,true,Color.rgb(119,205,255)));
        String src="WOS_CONTROL_API\nWOS_OFFICIAL_VALIDATION\nLEADERBOARD_OCR\nPROFILE_SCREENSHOT_OCR\nWOS_OBSERVER\nPUBLIC_WOS_STATE_SITE\nWSCO_STATIC_DATA\nMANUAL_IMPORT";
        root.addView(label(src,13,false,Color.rgb(220,232,240)),mp(0,6,0,14));

        root.addView(label("RULE",14,true,Color.rgb(119,205,255)));
        root.addView(label("A source is stored only after it has been verified as Whiteout Survival. Every player value keeps its source type and timestamps. Snapshot history is appended instead of overwritten.",13,false,Color.rgb(220,232,240)),mp(0,6,0,14));

        output=label("No local player rows loaded.",13,false,Color.rgb(220,232,240));
        root.addView(output,mp(0,10,0,0));
        load.setOnClickListener(v->loadState());
        setContentView(sc);
    }

    private void loadState(){
        String state=stateInput.getText().toString().trim();
        getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("state",state).apply();
        List<PlayerDataStore.PlayerRecord> rows=store.listState(state,100);
        if(rows.isEmpty()){
            output.setText("State #"+state+"\nNo locally stored players yet. Add players through verified API, OCR or manual import sources.");
            return;
        }
        StringBuilder sb=new StringBuilder("State #").append(state).append(" • ").append(rows.size()).append(" players\n\n");
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        int i=1;
        for(PlayerDataStore.PlayerRecord r:rows){
            sb.append(i++).append(". ").append(r.nickname==null||r.nickname.isEmpty()?r.fid:r.nickname);
            if(r.allianceTag!=null&&!r.allianceTag.isEmpty()) sb.append(" [").append(r.allianceTag).append("]");
            if(r.personalPower>0) sb.append(" • ").append(String.format(Locale.US,"%,d",r.personalPower));
            sb.append("\nFID: ").append(r.fid)
              .append(" • ").append(r.sourceType==null?"":r.sourceType)
              .append(" • ").append(r.fetchedAt>0?f.format(new Date(r.fetchedAt)):"no timestamp")
              .append("\n\n");
        }
        output.setText(sb.toString());
    }

    private TextView label(String s,int z,boolean bold,int color){ TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(color);v.setGravity(Gravity.START);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v; }
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}    
    private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
