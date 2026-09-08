package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.*;

public class ExpertSettingsActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private LinearLayout root; private SharedPreferences p;
    private EditText build,research,training,healing,gathering,attack,defense,lethality,health,event,resource;
    @Override protected void onCreate(Bundle b){super.onCreate(b);p=getSharedPreferences(PREFS,MODE_PRIVATE);ScrollView sc=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(28,30,28,50);root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        TextView h=t("🧠 EXPERT BONUS CENTER",24,true,Color.WHITE);root.addView(h);root.addView(t("Syötä vain omassa pelissä näkyvät Expert-bonukset. Näitä arvoja käytetään yhteisesti laskureissa. Sovellus ei arvaa puuttuvia Expert-arvoja.",13,false,Color.rgb(205,226,238)));
        build=f("Construction speed %","expert_construction_speed");research=f("Research speed %","expert_research_speed");training=f("Training speed %","expert_training_speed");healing=f("Healing speed %","expert_healing_speed");gathering=f("Gathering speed %","expert_gathering_speed");attack=f("Troop Attack %","expert_attack");defense=f("Troop Defense %","expert_defense");lethality=f("Troop Lethality %","expert_lethality");health=f("Troop Health %","expert_health");event=f("Event point bonus %","expert_event_points");resource=f("Resource cost reduction %","expert_resource_reduction");
        Button save=new Button(this);save.setText("TALLENNA EXPERT-BONUKSET");save.setOnClickListener(v->save());root.addView(save);root.addView(t("Huom: materiaalivähennystä käytetään vain laskureissa, joissa olet itse syöttänyt tähän varmennetun Expert-prosentin. Muuten materiaalit pysyvät alkuperäisinä.",12,false,Color.rgb(150,190,210)));setContentView(sc);
    }
    private EditText f(String label,String key){root.addView(t(label,12,true,Color.rgb(119,205,255)));EditText e=new EditText(this);e.setText(p.getString(key,"0"));e.setTextColor(Color.WHITE);e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);root.addView(e);return e;}
    private void save(){SharedPreferences.Editor e=p.edit();e.putString("expert_construction_speed",v(build)).putString("expert_research_speed",v(research)).putString("expert_training_speed",v(training)).putString("expert_healing_speed",v(healing)).putString("expert_gathering_speed",v(gathering)).putString("expert_attack",v(attack)).putString("expert_defense",v(defense)).putString("expert_lethality",v(lethality)).putString("expert_health",v(health)).putString("expert_event_points",v(event)).putString("expert_resource_reduction",v(resource)).apply();Toast.makeText(this,"Expert-bonukset tallennettu",Toast.LENGTH_SHORT).show();}
    private String v(EditText x){String s=x.getText().toString().trim();return s.isEmpty()?"0":s;}
    private TextView t(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);v.setPadding(0,8,0,8);return v;}
}
