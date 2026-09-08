package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.text.NumberFormat;
import java.util.Locale;

public class UpgradePlannerActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private boolean en;
    private String mode;
    private LinearLayout root;
    private TextView result;
    private SharedPreferences prefs;

    private static final long[] CH_GUIDE={0,5,40,60,80,100,120,140,200,300,420,560,580,580,600,600,650,765,1300};
    private static final long[] CH_DESIGN={0,5,15,40,100,200,300,400,400,400,420,420,450,450,500,500,550,630,1130};
    private static final long[] CH_SECRET={0,0,0,0,0,0,0,0,0,0,0,0,15,30,45,70,100,135,180};
    private static final long[] CH_POWER={0,205700,288000,370000,452000,576000,700000,824000,948000,1072000,1196000,1320000,1444000,1568000,1692000,1816000,1940000,0,0};

    private static final String[] GEAR_LEVEL={
            "None","Green","Green ★","Blue","Blue ★","Blue ★★","Blue ★★★","Purple","Purple ★","Purple ★★","Purple ★★★",
            "Purple T1","Purple T1 ★","Purple T1 ★★","Purple T1 ★★★","Gold","Gold ★","Gold ★★","Gold ★★★","Gold T1","Gold T1 ★","Gold T1 ★★","Gold T1 ★★★",
            "Gold T2","Gold T2 ★","Gold T2 ★★","Gold T2 ★★★","Red","Red ★","Red ★★","Red ★★★","Red T1","Red T1 ★","Red T1 ★★","Red T1 ★★★",
            "Red T2","Red T2 ★","Red T2 ★★","Red T2 ★★★","Red T3","Red T3 ★","Red T3 ★★","Red T3 ★★★","Red T4","Red T4 ★","Red T4 ★★","Red T4 ★★★","Red T6 ★★★ (MAX)"};
    private static final long[] G_ALLOY={0,1500,3800,7000,9700,0,0,0,0,6500,8000,10000,11000,13000,15000,22000,23000,25000,26000,28000,30000,32000,35000,38000,43000,45000,48000,50000,52000,54000,56000,59000,61000,63000,65000,68000,70000,72000,74000,77000,80000,83000,86000,124000,140000,160000,180000};
    private static final long[] G_POLISH={0,15,40,70,95,0,0,0,0,65,80,95,110,130,160,220,230,250,260,280,300,320,340,390,430,460,500,530,560,590,620,670,700,730,760,810,840,870,900,950,990,1030,1070,1500,1650,1800,1950};
    private static final long[] G_PLAN={0,0,0,0,0,45,50,60,70,40,50,60,70,85,100,40,40,45,45,45,55,55,55,55,75,80,85,85,90,95,100,110,115,120,125,135,140,145,150,160,165,170,180,250,275,300,325};
    private static final long[] G_AMBER={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,10,10,10,15,15,15,15,20,20,20,20,25,25,25,25,40,40,40,40};
    private static final long[] G_POWER={0,224400,306000,408000,510000,612000,714000,816000,885360,954720,1024080,1093440,1162800,1232160,1301520,1362720,1423920,1485120,1546320,1607520,1668720,1729920,1791120,1852320,1913520,1974720,2040000,2142000,2244000,2346000,2448000,2550000,2652000,2754000,2856000,2958000,3060000,3162000,3264000,3366000,3468000,3570000,3672000,3876000,4080000,4284000,4488000};
    private static final long MAX6_ALLOY=25863000,MAX6_POLISH=272160,MAX6_PLAN=48240,MAX6_AMBER=6000,MAX6_POWER=36720000;

    private static final String[] FC_LEVEL={"Lv30","FC1","FC2","FC3","FC4","FC5","FC6","FC7","FC8","FC9","FC10"};
    private static final long[] FC_COST={0,660,790,1190,1400,1675,900,1080,1080,1260,1575};
    private static final long[] RFC_COST={0,0,0,0,0,0,60,90,120,180,420};

    private static final long[] T_MEAT={0,36,58,92,120,156,186,279,558,1394,2788,6970};
    private static final long[] T_WOOD={0,27,44,69,90,117,140,210,419,1046,2091,5228};
    private static final long[] T_COAL={0,7,10,17,21,27,33,49,98,244,488,1220};
    private static final long[] T_IRON={0,2,3,4,5,6,7,11,21,51,102,253};
    private static final long[] T_TIME={0,12,17,24,32,44,60,83,113,131,152,180};
    private static final long[] T_POWER={0,3,4,6,9,13,20,28,38,50,66,80};

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);prefs=getSharedPreferences(PREFS,MODE_PRIVATE);en="en".equals(prefs.getString("lang","fi"));
        mode=getIntent().getStringExtra("mode"); if(mode==null)mode="chief_gear";
        ScrollView sc=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(20),dp(18),dp(40));root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        LinearLayout hero=card(13,48,76);hero.setPadding(dp(18),dp(18),dp(18),dp(18));hero.addView(txt(icon()+"  "+title()+" 6.1",25,true,Color.WHITE));hero.addView(txt(tr("Tavoite → Inventory Vault → puuttuvat → teho/aika","Target → Inventory Vault → shortages → power/time"),12,false,Color.rgb(205,228,241)));root.addView(hero,mp(0,0,0,14));
        addSourceNote();if("chief_gear".equals(mode))buildGear();else if("charms".equals(mode))buildCharms();else if("fire_crystals".equals(mode)||"buildings".equals(mode))buildFire();else if("troops".equals(mode))buildTroops();else buildFallback();setContentView(sc);
    }

    private void addSourceNote(){root.addView(txt(tr("Inventory-kentät esitäytetään Operations Suite 6.1:n paikallisesta Inventory Vaultista. Puuttuvia WOS-kuluja tai power-arvoja ei arvata.","Inventory fields are prefilled from the local Operations Suite 6.1 Inventory Vault. Missing WOS costs or power values are not guessed."),11,false,Color.rgb(143,174,193)),mp(0,0,0,12));}

    private void buildGear(){section(tr("TAVOITE","TARGET"));Spinner current=spin(GEAR_LEVEL),target=spin(GEAR_LEVEL);target.setSelection(GEAR_LEVEL.length-1);root.addView(label(tr("Nykyinen taso","Current level")));root.addView(current);root.addView(label(tr("Tavoitetaso","Target level")));root.addView(target);Spinner qty=spin(new String[]{"1","2","3","4","5","6"});qty.setSelection(5);root.addView(label(tr("Kuinka monta gear-osaa päivitetään","How many gear pieces")));root.addView(qty);section(tr("INVENTORY VAULT","INVENTORY VAULT"));EditText alloy=vaultInput("Hardened Alloy","vault_alloy"),polish=vaultInput("Polishing Solution","vault_polish"),plans=vaultInput("Design Plans","vault_plans"),amber=vaultInput("Lunar Amber","vault_amber");root.addView(alloy);root.addView(polish);root.addView(plans);root.addView(amber);result=resultBox();Button calc=button(tr("LASKE PÄIVITYS","CALCULATE UPGRADE"));root.addView(calc);root.addView(result,mp(0,12,0,0));calc.setOnClickListener(v->{int a=current.getSelectedItemPosition(),z=target.getSelectedItemPosition(),n=qty.getSelectedItemPosition()+1;if(z<=a){toast(tr("Valitse nykyistä korkeampi tavoite.","Choose a target above current."));return;}long al=0,po=0,pl=0,am=0,pw=0;if(z==GEAR_LEVEL.length-1){long fullAl=MAX6_ALLOY/6,fullPo=MAX6_POLISH/6,fullPl=MAX6_PLAN/6,fullAm=MAX6_AMBER/6,fullPw=MAX6_POWER/6;long ua=0,up=0,ud=0,um=0;if(a>=G_ALLOY.length){toast(tr("T5/T6 välitasoja ei arvata.","T5/T6 intermediate levels are not guessed."));return;}for(int i=1;i<=a;i++){ua+=G_ALLOY[i];up+=G_POLISH[i];ud+=G_PLAN[i];um+=G_AMBER[i];}al=(fullAl-ua)*n;po=(fullPo-up)*n;pl=(fullPl-ud)*n;am=(fullAm-um)*n;pw=(fullPw-G_POWER[a])*n;}else{for(int i=a+1;i<=z;i++){al+=G_ALLOY[i];po+=G_POLISH[i];pl+=G_PLAN[i];am+=G_AMBER[i];}al*=n;po*=n;pl*=n;am*=n;pw=(G_POWER[z]-G_POWER[a])*n;}String s=summaryHeader(n)+line("Hardened Alloy",al,val(alloy))+line("Polishing Solution",po,val(polish))+line("Design Plans",pl,val(plans))+line("Lunar Amber",am,val(amber))+"\n⚡ Power +"+fmt(Math.max(0,pw));result.setText(s);saveResult(s);});}

    private void buildCharms(){section(tr("TAVOITE","TARGET"));Spinner current=spin(levels(0,18)),target=spin(levels(0,18));target.setSelection(18);root.addView(label(tr("Nykyinen charm-taso","Current charm level")));root.addView(current);root.addView(label(tr("Tavoitetaso","Target level")));root.addView(target);String[] q=new String[18];for(int i=0;i<18;i++)q[i]=String.valueOf(i+1);Spinner qty=spin(q);qty.setSelection(17);root.addView(label(tr("Kuinka monta 18 charm-slotista päivitetään","How many of 18 charm slots")));root.addView(qty);section(tr("INVENTORY VAULT","INVENTORY VAULT"));EditText guides=vaultInput("Charm Guides","vault_guides"),designs=vaultInput("Charm Designs","vault_designs"),secrets=vaultInput("Jewel Secrets","vault_secrets");root.addView(guides);root.addView(designs);root.addView(secrets);result=resultBox();Button calc=button(tr("LASKE CHARMIT","CALCULATE CHARMS"));root.addView(calc);root.addView(result,mp(0,12,0,0));calc.setOnClickListener(v->{int a=current.getSelectedItemPosition(),z=target.getSelectedItemPosition(),n=qty.getSelectedItemPosition()+1;if(z<=a){toast(tr("Valitse nykyistä korkeampi tavoite.","Choose a target above current."));return;}long g=0,d=0,s=0;for(int i=a+1;i<=z;i++){g+=CH_GUIDE[i];d+=CH_DESIGN[i];s+=CH_SECRET[i];}g*=n;d*=n;s*=n;String power=(z<=16)?"\n⚡ Power +"+fmt((CH_POWER[z]-CH_POWER[a])*n):"\n⚠ "+tr("Lv17–18 power jätetään näyttämättä, koska sitä ei arvata.","Lv17–18 power is hidden rather than guessed.");String out=summaryHeader(n)+line("Charm Guides",g,val(guides))+line("Charm Designs",d,val(designs))+line("Jewel Secrets",s,val(secrets))+power;result.setText(out);saveResult(out);});}

    private void buildFire(){section(tr("FURNACE / FIRE CRYSTAL","FURNACE / FIRE CRYSTAL"));Spinner current=spin(FC_LEVEL),target=spin(FC_LEVEL);target.setSelection(10);root.addView(label(tr("Nykyinen Furnace","Current Furnace")));root.addView(current);root.addView(label(tr("Tavoite","Target")));root.addView(target);EditText fc=vaultInput("Fire Crystals","vault_fc"),rfc=vaultInput("Refined Fire Crystals","vault_rfc");root.addView(fc);root.addView(rfc);result=resultBox();Button calc=button(tr("LASKE FC-PÄIVITYS","CALCULATE FC UPGRADE"));root.addView(calc);root.addView(result,mp(0,12,0,0));calc.setOnClickListener(v->{int a=current.getSelectedItemPosition(),z=target.getSelectedItemPosition();if(z<=a){toast(tr("Valitse korkeampi tavoite.","Choose a higher target."));return;}long f=0,r=0;for(int i=a+1;i<=z;i++){f+=FC_COST[i];r+=RFC_COST[i];}String out=line("Fire Crystals",f,val(fc))+line("Refined Fire Crystals",r,val(rfc))+"\n"+tr("Huom: nämä ovat Furnace-tierien varmennetut kokonaiskulut. Muiden rakennusten kulut pidetään erillään.","Note: these are verified Furnace tier totals. Other building costs remain separate.");result.setText(out);saveResult(out);});}

    private void buildTroops(){section(tr("KOULUTUS / PROMOTION","TRAINING / PROMOTION"));String[] tiers=new String[11];for(int i=0;i<11;i++)tiers[i]="T"+(i+1);Spinner from=spin(tiers),to=spin(tiers);to.setSelection(9);CheckBox promo=new CheckBox(this);promo.setTextColor(Color.WHITE);promo.setText(tr("Promotoi nykyisestä tieristä","Promote from current tier"));root.addView(label(tr("Nykyinen tier","Current tier")));root.addView(from);root.addView(label(tr("Tavoitetier","Target tier")));root.addView(to);root.addView(promo);EditText count=input(tr("Sotilaiden määrä","Troop count")),speed=input(tr("Training Speed %","Training Speed %"));speed.setText(prefs.getString("planner_training_speed","0"));root.addView(count);root.addView(speed);result=resultBox();Button calc=button(tr("LASKE TROOPIT","CALCULATE TROOPS"));root.addView(calc);root.addView(result,mp(0,12,0,0));calc.setOnClickListener(v->{int a=from.getSelectedItemPosition()+1,z=to.getSelectedItemPosition()+1;long n=val(count);if(n<=0){toast(tr("Anna sotilaiden määrä.","Enter troop count."));return;}if(promo.isChecked()&&z<=a){toast(tr("Promotion-tavoitteen pitää olla korkeampi.","Promotion target must be higher."));return;}long m=T_MEAT[z],w=T_WOOD[z],c=T_COAL[z],ir=T_IRON[z],sec=T_TIME[z],pw=T_POWER[z];if(promo.isChecked()){m-=T_MEAT[a];w-=T_WOOD[a];c-=T_COAL[a];ir-=T_IRON[a];sec-=T_TIME[a];pw-=T_POWER[a];}double sp=Math.max(0,val(speed));long adjusted=(long)Math.ceil((sec*n)/(1.0+sp/100.0));String out="Meat: "+fmt(m*n)+"\nWood: "+fmt(w*n)+"\nCoal: "+fmt(c*n)+"\nIron: "+fmt(ir*n)+"\n\n⏱ "+tr("Base aika: ","Base time: ")+formatSeconds(sec*n)+"\n⚡ "+tr("Speed huomioitu: ","With speed: ")+formatSeconds(adjusted)+"\nPower: +"+fmt(pw*n);result.setText(out);saveResult(out);});}

    private void buildFallback(){root.addView(txt(tr("Tämä laskuri käyttää vielä vanhaa näkymää. Avaa se laskukeskuksesta.","This calculator still uses the legacy view. Open it from the calculator hub."),14,false,Color.WHITE));}
    private void saveResult(String s){prefs.edit().putString("last_upgrade_mode",mode).putString("last_upgrade_summary",s).apply();}
    private EditText vaultInput(String hint,String key){EditText e=input(hint);e.setText(prefs.getString(key,"0"));return e;}
    private String summaryHeader(int n){return tr("Päivitettäviä kohteita: ","Upgrade targets: ")+n+"\n\n";}
    private String line(String name,long need,long have){return name+": "+fmt(need)+"  •  "+tr("puuttuu ","missing ")+fmt(Math.max(0,need-have))+"\n";}
    private String[] levels(int a,int b){String[] x=new String[b-a+1];for(int i=a;i<=b;i++)x[i-a]=tr("Taso ","Level ")+i;return x;}
    private String title(){if("chief_gear".equals(mode))return"Chief Gear";if("charms".equals(mode))return"Chief Charms";if("fire_crystals".equals(mode)||"buildings".equals(mode))return"Fire Crystals";if("troops".equals(mode))return"Troops";return"Upgrade Planner";}
    private String icon(){if("chief_gear".equals(mode))return"🛡️";if("charms".equals(mode))return"💠";if("fire_crystals".equals(mode)||"buildings".equals(mode))return"🔥";if("troops".equals(mode))return"⚔️";return"🧮";}
    private String tr(String fi,String enText){return en?enText:fi;}
    private void section(String s){root.addView(txt(s,13,true,Color.rgb(119,205,255)),mp(0,10,0,6));}
    private TextView label(String s){return txt(s,12,true,Color.rgb(190,218,233));}
    private EditText input(String hint){EditText e=new EditText(this);e.setHint(hint);e.setHintTextColor(Color.rgb(190,210,222));e.setTextColor(Color.WHITE);e.setTextSize(16);e.setSingleLine();e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);e.setPadding(dp(14),dp(12),dp(14),dp(12));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(24,61,83));g.setCornerRadius(dp(12));g.setStroke(dp(1),Color.rgb(58,111,143));e.setBackground(g);e.setLayoutParams(mp(0,4,0,7));return e;}
    private Spinner spin(String[] items){Spinner s=new Spinner(this);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(24,61,83));g.setCornerRadius(dp(12));g.setStroke(dp(1),Color.rgb(58,111,143));s.setBackground(g);ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items){@Override public View getView(int p,View c,ViewGroup vg){TextView v=(TextView)super.getView(p,c,vg);v.setTextColor(Color.WHITE);v.setTextSize(16);v.setPadding(dp(12),dp(12),dp(12),dp(12));return v;}@Override public View getDropDownView(int p,View c,ViewGroup vg){TextView v=(TextView)super.getDropDownView(p,c,vg);v.setTextColor(Color.WHITE);v.setTextSize(16);v.setBackgroundColor(Color.rgb(24,61,83));v.setPadding(dp(14),dp(14),dp(14),dp(14));return v;}};s.setAdapter(ad);s.setLayoutParams(mp(0,4,0,8));return s;}
    private TextView resultBox(){TextView v=txt(tr("Valmis laskentaan.","Ready to calculate."),15,false,Color.WHITE);v.setPadding(dp(14),dp(14),dp(14),dp(14));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(18,48,70));g.setCornerRadius(dp(14));g.setStroke(dp(1),Color.rgb(50,94,120));v.setBackground(g);return v;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(28,139,207));g.setCornerRadius(dp(14));b.setBackground(g);return b;}
    private LinearLayout card(int r,int g,int b){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);GradientDrawable d=new GradientDrawable();d.setColor(Color.rgb(r,g,b));d.setCornerRadius(dp(18));l.setBackground(d);return l;}
    private TextView txt(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private long val(EditText e){try{String s=e.getText().toString().replaceAll("[^0-9]","");return s.isEmpty()?0:Long.parseLong(s);}catch(Exception ex){return 0;}}
    private String fmt(long n){return NumberFormat.getIntegerInstance(Locale.US).format(n);}private String formatSeconds(long s){long d=s/86400;s%=86400;long h=s/3600;s%=3600;long m=s/60;long sec=s%60;return(d>0?d+"d ":"")+(h>0?h+"h ":"")+(m>0?m+"m ":"")+sec+"s";}private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_LONG).show();}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
