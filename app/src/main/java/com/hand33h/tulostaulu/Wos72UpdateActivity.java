package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;

public class Wos72UpdateActivity extends Activity {
 private static final String P="wos_tulostaulu";
 private LinearLayout root;
 @Override public void onCreate(Bundle b){super.onCreate(b);ScrollView s=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(28,32,28,48);root.setBackgroundColor(Color.rgb(7,23,39));s.addView(root);
 title("👑 WOS UPDATE CENTER 7.2","Current WOS planning modules. Live/season values must be confirmed from the game or verified dataset before spending resources.");
 merger(); canyon(); territory(); svs(); koi(); building(); expert(); transfer(); setContentView(s);}
 private void merger(){section("🌀 STATE MERGER CENTER","One-week preview checklist • alliance territory/building reset warning • state-history notes • post-merge Canyon qualification check. No global merge date is guessed.","merger_notes");}
 private void canyon(){section("🏜️ CANYON CLASH PLANNER 2.0","Legion timing • squad notes • lane/role assignments • scoring observations. Enter current event values from the game.","canyon_plan");}
 private void territory(){section("🗺️ TERRITORY PLANNER 2.0","HQ • banners • cities • Bear Trap • farms • 7×7 planning notes • orphan-banner checklist. Layout remains local.","territory_plan");}
 private void svs(){section("⚔️ SvS MEGA CENTER","Prep D1–D5 plan • inventory target • point-gap notes • battle phase • match archive. Day scoring is dataset/user supplied, never assumed universal.","svs_mega");}
 private void koi(){section("🐟 KOI DATA PLAN","Day/activity/troop-tier point values are stored as the current event snapshot. Unknown season values stay unknown instead of being invented.","koi_snapshot");}
 private void building(){section("🏗️ BUILDING INVENTORY","Track Meat • Wood • Coal • Iron • Fire Crystals • Refined Fire Crystals • speedups and save the current building target / missing-material notes.","building_inventory");}
 private void expert(){section("🧠 EXPERT TRACKER 2.0","Affinity • Expert Sigils • Books of Knowledge • learning time. Generation-specific costs are not guessed when verified data is unavailable.","expert_tracker");}
 private void transfer(){section("🚚 TRANSFER CALENDAR","September 2026 snapshot: Phase I 13 Sep • Phase II 16 Sep • Phase III 18 Sep (UTC). Keep official/current values separate from forecasts because merges and announcements can change future windows.","transfer_calendar");}
 private void section(String h,String d,String key){TextView a=t(h,18,Color.WHITE);root.addView(a);root.addView(t(d,13,Color.rgb(205,226,238)));EditText e=new EditText(this);e.setHint("Notes / current game values");e.setTextColor(Color.WHITE);e.setHintTextColor(Color.GRAY);e.setMinLines(2);SharedPreferences p=getSharedPreferences(P,MODE_PRIVATE);e.setText(p.getString(key,""));root.addView(e);Button save=new Button(this);save.setText("SAVE");save.setOnClickListener(v->{p.edit().putString(key,e.getText().toString()).apply();Toast.makeText(this,"Saved locally",Toast.LENGTH_SHORT).show();});root.addView(save);root.addView(t("────────────────────────",12,Color.rgb(70,110,135)));}
 private void title(String h,String d){root.addView(t(h,25,Color.WHITE));root.addView(t(d,13,Color.rgb(119,205,255)));}
 private TextView t(String x,int z,int c){TextView v=new TextView(this);v.setText(x);v.setTextSize(z);v.setTextColor(c);v.setPadding(0,8,0,10);return v;}
}
