package com.hand33h.tulostaulu;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.text.NumberFormat;
import java.util.*;

public class NativeCalculatorActivity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private boolean en;
    private String mode;
    private LinearLayout root;
    private TextView result;
    private final List<Row> rows=new ArrayList<>();

    // Chief Charms: verified completed-level costs 1..16 plus Gen 8 Lv17/Lv18 totals per charm.
    private static final long[] CH_GUIDE={0,5,40,60,80,100,120,140,200,300,420,560,580,580,600,600,650,765,1300};
    private static final long[] CH_DESIGN={0,5,15,40,100,200,300,400,400,400,420,420,450,450,500,500,550,630,1130};
    private static final long[] CH_SECRET={0,0,0,0,0,0,0,0,0,0,0,0,15,30,45,70,100,135,180};
    private static final long[] CH_POWER={0,205700,288000,370000,452000,576000,700000,824000,948000,1072000,1196000,1320000,1444000,1568000,1692000,1816000,1940000,0,0};
    private static final long[] WIDGET={5,10,15,20,25,30,35,40,45,50};

    // Chief Gear verified milestone data through Legendary/Red T4 ★★★.
    private static final String[] GEAR_LEVEL={
            "None","Green","Green ★","Blue","Blue ★","Blue ★★","Blue ★★★","Purple","Purple ★","Purple ★★","Purple ★★★",
            "Purple T1","Purple T1 ★","Purple T1 ★★","Purple T1 ★★★","Gold","Gold ★","Gold ★★","Gold ★★★","Gold T1","Gold T1 ★","Gold T1 ★★","Gold T1 ★★★",
            "Gold T2","Gold T2 ★","Gold T2 ★★","Gold T2 ★★★","Red","Red ★","Red ★★","Red ★★★","Red T1","Red T1 ★","Red T1 ★★","Red T1 ★★★",
            "Red T2","Red T2 ★","Red T2 ★★","Red T2 ★★★","Red T3","Red T3 ★","Red T3 ★★","Red T3 ★★★","Red T4","Red T4 ★","Red T4 ★★","Red T4 ★★★",
            "Red T6 ★★★ (MAX)"};
    private static final long[] G_ALLOY={0,1500,3800,7000,9700,0,0,0,0,6500,8000,10000,11000,13000,15000,22000,23000,25000,26000,28000,30000,32000,35000,38000,43000,45000,48000,50000,52000,54000,56000,59000,61000,63000,65000,68000,70000,72000,74000,77000,80000,83000,86000,124000,140000,160000,180000};
    private static final long[] G_POLISH={0,15,40,70,95,0,0,0,0,65,80,95,110,130,160,220,230,250,260,280,300,320,340,390,430,460,500,530,560,590,620,670,700,730,760,810,840,870,900,950,990,1030,1070,1500,1650,1800,1950};
    private static final long[] G_PLAN={0,0,0,0,0,45,50,60,70,40,50,60,70,85,100,40,40,45,45,45,55,55,55,55,75,80,85,85,90,95,100,110,115,120,125,135,140,145,150,160,165,170,180,250,275,300,325};
    private static final long[] G_AMBER={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,10,10,10,15,15,15,15,20,20,20,20,25,25,25,25,40,40,40,40};
    private static final long[] G_POWER={0,224400,306000,408000,510000,612000,714000,816000,885360,954720,1024080,1093440,1162800,1232160,1301520,1362720,1423920,1485120,1546320,1607520,1668720,1729920,1791120,1852320,1913520,1974720,2040000,2142000,2244000,2346000,2448000,2550000,2652000,2754000,2856000,2958000,3060000,3162000,3264000,3366000,3468000,3570000,3672000,3876000,4080000,4284000,4488000};
    // Exact full-set max totals from scratch to Red T6 ★★★.
    private static final long MAX6_ALLOY=25863000, MAX6_POLISH=272160, MAX6_PLAN=48240, MAX6_AMBER=6000, MAX6_POWER=36720000;

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
        super.onCreate(b);
        en="en".equals(getSharedPreferences(PREFS,MODE_PRIVATE).getString("lang","fi"));
        mode=getIntent().getStringExtra("mode"); if(mode==null) mode="chief_gear";
        ScrollView sc=new ScrollView(this);
        root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(18),dp(20),dp(18),dp(40)); root.setBackgroundColor(Color.rgb(7,23,39)); sc.addView(root);
        LinearLayout hero=box(13,48,76,22); hero.setPadding(dp(20),dp(20),dp(20),dp(20));
        hero.addView(txt(icon()+"  "+title(),25,true,Color.WHITE));
        hero.addView(txt(tr("Bunny King • vahvistettu WOS-data","Bunny King • verified WOS data"),12,false,Color.rgb(220,236,246)));
        root.addView(hero,mp(0,0,0,16));
        if(mode.equals("chief_gear")) buildChiefGear(); else if(mode.equals("charms")) buildCharms(); else if(mode.equals("hero_gear")) buildHero(); else if(mode.equals("war_academy")) buildWar(); else if(mode.equals("fire_crystals")||mode.equals("buildings")) buildFire(); else if(mode.equals("troops")) buildTroops(); else if(mode.equals("pets")) buildPets(); else buildGeneric();
        setContentView(sc);
    }

    private void buildChiefGear(){
        root.addView(info(tr("Chief Gear on nyt valittavissa Green → Red T4 ★★★ sekä suoraan maksimitavoitteeseen Red T6 ★★★. T6-maksimitavoite käyttää vahvistettua koko setin kokonaisdataa.","Chief Gear now supports Green → Red T4 ★★★ plus a direct max target of Red T6 ★★★. The T6 max target uses verified full-set totals.")));
        Spinner c=spin(GEAR_LEVEL),t=spin(GEAR_LEVEL); t.setSelection(GEAR_LEVEL.length-1);
        root.addView(txt(tr("Nykyinen taso","Current level"),12,true,cyan())); root.addView(c,mp(0,4,0,8));
        root.addView(txt(tr("Tavoitetaso","Target level"),12,true,cyan())); root.addView(t,mp(0,4,0,8));
        CheckBox all=new CheckBox(this); all.setTextColor(Color.WHITE); all.setText(tr("Laske kaikki 6 Chief Gear -osaa","Calculate all 6 Chief Gear pieces")); all.setChecked(true); root.addView(all);
        EditText a=input(tr("Omistat Hardened Alloy","Hardened Alloy owned"),true),p=input(tr("Omistat Polishing Solution","Polishing Solution owned"),true),d=input(tr("Omistat Design Plans","Design Plans owned"),true),am=input(tr("Omistat Lunar Amber","Lunar Amber owned"),true);
        root.addView(a); root.addView(p); root.addView(d); root.addView(am);
        result=out(); Button b=button(tr("LASKE CHIEF GEAR","CALCULATE CHIEF GEAR")); root.addView(b); root.addView(result,mp(0,12,0,0));
        b.setOnClickListener(v->{
            int x=c.getSelectedItemPosition(), z=t.getSelectedItemPosition(); int n=all.isChecked()?6:1;
            if(z<=x){toast(tr("Valitse korkeampi tavoitetaso.","Choose a higher target level."));return;}
            long al,po,pl,la,pw;
            if(z==GEAR_LEVEL.length-1){
                long fullAl=MAX6_ALLOY/6, fullPo=MAX6_POLISH/6, fullPl=MAX6_PLAN/6, fullLa=MAX6_AMBER/6, fullPw=MAX6_POWER/6;
                long usedAl=0,usedPo=0,usedPl=0,usedLa=0;
                if(x<G_ALLOY.length){for(int i=1;i<=x;i++){usedAl+=G_ALLOY[i];usedPo+=G_POLISH[i];usedPl+=G_PLAN[i];usedLa+=G_AMBER[i];}}
                else {toast(tr("T5/T6-välitasojen kustannuksia ei arvata. Käytä nykyisenä enintään Red T4 ★★★ ja tavoitteena T6 ★★★.","T5/T6 intermediate costs are not guessed. Use current up to Red T4 ★★★ with T6 ★★★ as target."));return;}
                al=(fullAl-usedAl)*n; po=(fullPo-usedPo)*n; pl=(fullPl-usedPl)*n; la=(fullLa-usedLa)*n;
                long currentPw=(x<G_POWER.length)?G_POWER[x]:0; pw=(fullPw-currentPw)*n;
            } else {
                al=po=pl=la=0; for(int i=x+1;i<=z;i++){al+=G_ALLOY[i];po+=G_POLISH[i];pl+=G_PLAN[i];la+=G_AMBER[i];}
                al*=n;po*=n;pl*=n;la*=n; pw=(G_POWER[z]-G_POWER[x])*n;
            }
            result.setText("Hardened Alloy: "+fmt(al)+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,al-val(a)))+"\nPolishing Solution: "+fmt(po)+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,po-val(p)))+"\nDesign Plans: "+fmt(pl)+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,pl-val(d)))+"\nLunar Amber: "+fmt(la)+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,la-val(am)))+"\n\nPower: +"+fmt(Math.max(0,pw)));
        });
    }

    private void buildCharms(){
        root.addView(info(tr("Chief Charms: tasot 0–18. Lv17 ja Lv18 avautuvat Gen 8 -vaiheessa ja sisältävät 9 alivaihetta kumpikin. Tässä laskuri käyttää koko Lv17/Lv18-tason vahvistettuja materiaalikustannuksia.","Chief Charms: levels 0–18. Lv17 and Lv18 unlock at Gen 8 and each contain 9 sub-stages. This calculator uses the verified full-level material totals for Lv17/Lv18.")));
        Spinner c=spin(levels(0,18)),t=spin(levels(0,18)); t.setSelection(18);
        root.addView(txt(tr("Nykyinen taso","Current level"),12,true,cyan()));root.addView(c,mp(0,4,0,8));
        root.addView(txt(tr("Tavoitetaso","Target level"),12,true,cyan()));root.addView(t,mp(0,4,0,8));
        CheckBox all=new CheckBox(this);all.setText(tr("Laske kaikki 18 charmia","Calculate all 18 charms"));all.setTextColor(Color.WHITE);all.setChecked(true);root.addView(all);
        EditText og=input(tr("Omistat Charm Guides","Charm Guides owned"),true),od=input(tr("Omistat Charm Designs","Charm Designs owned"),true),os=input(tr("Omistat Charm / Jewel Secrets","Charm / Jewel Secrets owned"),true);root.addView(og);root.addView(od);root.addView(os);
        result=out();Button b=button(tr("LASKE CHARMS","CALCULATE CHARMS"));root.addView(b);root.addView(result,mp(0,12,0,0));
        b.setOnClickListener(v->{int a=c.getSelectedItemPosition(),z=t.getSelectedItemPosition();if(z<=a){toast(tr("Tavoitteen pitää olla nykyistä tasoa korkeampi.","Target must be above current level."));return;}long g=0,d=0,s=0;for(int i=a+1;i<=z;i++){g+=CH_GUIDE[i];d+=CH_DESIGN[i];s+=CH_SECRET[i];}int n=all.isChecked()?18:1;g*=n;d*=n;s*=n;String p="";if(z<=16){long power=(CH_POWER[z]-CH_POWER[a])*n;p="\n\nPower: +"+fmt(power);}else p="\n\n"+tr("Lv17–18 Power ei näytetä, jotta arvoa ei arvata.","Lv17–18 Power is not shown to avoid guessing.");result.setText("Charm Guides: "+fmt(g)+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,g-val(og)))+"\nCharm Designs: "+fmt(d)+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,d-val(od)))+"\nCharm Secrets: "+fmt(s)+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,s-val(os)))+p);});
    }

    private void buildFire(){root.addView(info(tr("Furnacen kokonaiset Fire Crystal -tierit. Jokainen tier sisältää 5 rakennusvaihetta. Refined Fire Crystals alkavat FC5→FC6-vaiheessa.","Furnace whole Fire Crystal tiers. Each tier contains 5 build stages. Refined Fire Crystals start in the FC5→FC6 band.")));Spinner c=spin(FC_LEVEL),t=spin(FC_LEVEL);root.addView(txt(tr("Nykyinen Furnace","Current Furnace"),12,true,cyan()));root.addView(c);root.addView(txt(tr("Tavoite","Target"),12,true,cyan()));root.addView(t);EditText of=input(tr("Omistat Fire Crystals","Fire Crystals owned"),true),orf=input(tr("Omistat Refined Fire Crystals","Refined Fire Crystals owned"),true);root.addView(of);root.addView(orf);result=out();Button b=button(tr("LASKE FURNACE FC","CALCULATE FURNACE FC"));root.addView(b);root.addView(result,mp(0,12,0,0));b.setOnClickListener(v->{int x=c.getSelectedItemPosition(),z=t.getSelectedItemPosition();if(z<=x){toast(tr("Valitse korkeampi tavoite.","Choose a higher target."));return;}long fc=0,rfc=0;for(int i=x+1;i<=z;i++){fc+=FC_COST[i];rfc+=RFC_COST[i];}result.setText("Fire Crystals: "+fmt(fc)+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,fc-val(of)))+"\nRefined Fire Crystals: "+fmt(rfc)+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,rfc-val(orf))));});}

    private void buildTroops(){root.addView(info(tr("Tarkka Infantry T1–T11 koulutuskustannus per sotilas. Promotion käyttää kustannusten erotusta.","Exact Infantry T1–T11 training cost per troop. Promotion uses the cost difference.")));String[] tiers=new String[11];for(int i=0;i<11;i++)tiers[i]="T"+(i+1);Spinner from=spin(tiers),to=spin(tiers);to.setSelection(9);CheckBox promo=new CheckBox(this);promo.setTextColor(Color.WHITE);promo.setText(tr("Promotoi nykyisestä tieristä","Promote from current tier"));root.addView(from);root.addView(to);root.addView(promo);EditText count=input(tr("Sotilaiden määrä","Troop count"),true);root.addView(count);result=out();Button b=button(tr("LASKE TROOPIT","CALCULATE TROOPS"));root.addView(b);root.addView(result,mp(0,12,0,0));b.setOnClickListener(v->{int a=from.getSelectedItemPosition()+1,z=to.getSelectedItemPosition()+1;long n=val(count);if(n<=0){toast(tr("Anna sotilaiden määrä.","Enter troop count."));return;}if(promo.isChecked()&&z<=a){toast(tr("Promotion-tavoitteen pitää olla korkeampi.","Promotion target must be higher."));return;}long m=T_MEAT[z],w=T_WOOD[z],co=T_COAL[z],ir=T_IRON[z],sec=T_TIME[z],pow=T_POWER[z];if(promo.isChecked()){m-=T_MEAT[a];w-=T_WOOD[a];co-=T_COAL[a];ir-=T_IRON[a];sec-=T_TIME[a];pow-=T_POWER[a];}result.setText("Meat: "+fmt(m*n)+"\nWood: "+fmt(w*n)+"\nCoal: "+fmt(co*n)+"\nIron: "+fmt(ir*n)+"\n"+tr("Aika: ","Time: ")+formatSeconds(sec*n)+"\nPower: +"+fmt(pow*n));});}

    private void buildPets(){root.addView(info(tr("WOS:ssa on 14 pettiä ja 5 rarityä. Alla vahvistetut kokonaiskulut rarityn maksimitasolle.","WOS has 14 pets across 5 rarities. Below are verified total costs to the rarity max level.")));String[] names={"Cave Hyena • Common • Lv50","Arctic Wolf / Musk Ox • Uncommon • Lv60","Giant Tapir / Titan Roc • Rare • Lv70","Giant Elk / Snow Leopard • Epic • Lv80","Legendary SSR • Lv100"};Spinner pet=spin(names);root.addView(pet);EditText food=input(tr("Omistat Pet Food","Pet Food owned"),true),man=input(tr("Omistat Taming Manuals","Taming Manuals owned"),true),ser=input(tr("Omistat Strengthening Serums","Strengthening Serums owned"),true);root.addView(food);root.addView(man);root.addView(ser);result=out();Button b=button(tr("LASKE PET","CALCULATE PET"));root.addView(b);root.addView(result,mp(0,12,0,0));long[] pf={28900,83800,189000,360400,834625},tm={240,515,860,1440,2990},ss={10,30,70,130,310};b.setOnClickListener(v->{int i=pet.getSelectedItemPosition();result.setText("Pet Food: "+fmt(pf[i])+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,pf[i]-val(food)))+"\nTaming Manuals: "+fmt(tm[i])+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,tm[i]-val(man)))+"\nStrengthening Serums: "+fmt(ss[i])+" • "+tr("puuttuu ","missing ")+fmt(Math.max(0,ss[i]-val(ser))));});}

    private void buildHero(){root.addView(info(tr("Hero Gear Widget 0–10 tarkalla datalla.","Hero Gear Widget 0–10 with exact data.")));Spinner c=spin(levels(0,10)),t=spin(levels(0,10));root.addView(c);root.addView(t);EditText owned=input(tr("Omistat Widgettejä","Widgets owned"),true);root.addView(owned);result=out();Button b=button(tr("LASKE WIDGETIT","CALCULATE WIDGETS"));root.addView(b);root.addView(result,mp(0,12,0,0));b.setOnClickListener(v->{int a=c.getSelectedItemPosition(),z=t.getSelectedItemPosition();long need=0;for(int i=a;i<z;i++)need+=WIDGET[i];result.setText("Widgets: "+fmt(need)+"\n"+tr("Puuttuu: ","Missing: ")+fmt(Math.max(0,need-val(owned))));});}

    private void buildWar(){root.addView(info(tr("War Academy: 5 000 Steel = 1 Fire Crystal Shard (max 20/pv) ja 10 Fire Crystals = 13 Shardia.","War Academy: 5,000 Steel = 1 Fire Crystal Shard (max 20/day) and 10 Fire Crystals = 13 Shards.")));EditText steel=input("Steel",true),fc=input("Fire Crystals",true),owned=input(tr("Omistat Shardeja","Shards owned"),true),need=input(tr("Tarvittavat Shardit","Shards required"),true);root.addView(steel);root.addView(fc);root.addView(owned);root.addView(need);result=out();Button b=button(tr("LASKE SHARDIT","CALCULATE SHARDS"));root.addView(b);root.addView(result,mp(0,12,0,0));b.setOnClickListener(v->{long fromSteel=Math.min(20,val(steel)/5000),fromFc=Math.min(200,(val(fc)/10)*13),total=val(owned)+fromSteel+fromFc;result.setText(tr("Steel-vaihdosta: ","From Steel: ")+fmt(fromSteel)+"\n"+tr("Fire Crystal -vaihdosta: ","From Fire Crystals: ")+fmt(fromFc)+"\n"+tr("Shardit yhteensä: ","Total Shards: ")+fmt(total)+"\n"+tr("Puuttuu: ","Missing: ")+fmt(Math.max(0,val(need)-total)));});}

    private void buildGeneric(){root.addView(info(tr("Materiaalivarasto- ja puutelaskenta.","Inventory and shortage calculator.")));for(String m:materials())addMat(m);result=out();Button b=button(tr("LASKE PUUTTUVAT","CALCULATE SHORTAGES"));root.addView(b);root.addView(result,mp(0,12,0,0));b.setOnClickListener(v->{StringBuilder s=new StringBuilder();for(Row r:rows){long n=val(r.need),h=val(r.have);s.append(r.name).append(": ").append(fmt(n)).append(" • ").append(tr("puuttuu ","missing ")).append(fmt(Math.max(0,n-h))).append('\n');}result.setText(s.toString());});}
    private void addMat(String n){root.addView(txt(n,14,true,Color.WHITE));LinearLayout l=new LinearLayout(this);EditText a=input(tr("Tarve","Need"),true),b=input(tr("Omistat","Owned"),true);l.addView(a,new LinearLayout.LayoutParams(0,dp(54),1));l.addView(b,new LinearLayout.LayoutParams(0,dp(54),1));root.addView(l);rows.add(new Row(n,a,b));}
    private String[] materials(){switch(mode){case"research":return new String[]{"Meat","Wood","Coal","Iron","Steel","Research Speedups (min)"};case"experts":return new String[]{"Expert XP","Expert Manuals","Expert materials"};case"svs":return new String[]{"Chief Gear materials","Charm materials","Fire Crystals","Hero Gear materials","Pet materials","Speedups (min)","Expected SvS points"};case"bear":return new String[]{"Infantry","Lancers","Marksmen","March capacity","Rally capacity"};case"koi":return new String[]{"Speedups (min)","Fire Crystals","Chief Gear materials","Charm materials","Expected points"};case"chests":return new String[]{"Chest count","Expected primary material","Expected secondary material"};default:return new String[]{"Resource A","Resource B","Resource C"};}}
    private String title(){switch(mode){case"chief_gear":return"Chief Gear";case"charms":return"Chief Charms";case"fire_crystals":return"Fire Crystals";case"buildings":return"Buildings / Furnace";case"research":return"Research";case"war_academy":return"War Academy";case"hero_gear":return"Hero Gear";case"pets":return"Pets";case"troops":return"Troops";case"experts":return"Experts";case"svs":return"SvS Prep";case"bear":return"Bear Trap / Rally";case"koi":return"KOI";case"chests":return"Chests";default:return"WOS Calculator";}}
    private String icon(){switch(mode){case"chief_gear":return"🛡️";case"charms":return"💠";case"fire_crystals":return"🔥";case"buildings":return"🏗️";case"research":return"🧠";case"war_academy":return"🎓";case"hero_gear":return"🦸";case"pets":return"🐾";case"troops":return"⚔️";case"experts":return"👨‍🔬";case"svs":return"⚔️";case"bear":return"🐻";case"koi":return"🧊";case"chests":return"📦";default:return"🧮";}}

    private String[] levels(int a,int b){String[] x=new String[b-a+1];for(int i=a;i<=b;i++)x[i-a]=tr("Taso ","Level ")+i;return x;}
    private Spinner spin(String[] items){Spinner s=new Spinner(this);s.setPadding(dp(12),dp(4),dp(8),dp(4));GradientDrawable bg=new GradientDrawable();bg.setColor(Color.rgb(24,61,83));bg.setCornerRadius(dp(12));bg.setStroke(dp(1),Color.rgb(58,111,143));s.setBackground(bg);ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items){@Override public View getView(int position,View convertView,ViewGroup parent){TextView v=(TextView)super.getView(position,convertView,parent);v.setTextColor(Color.WHITE);v.setTextSize(17);v.setPadding(dp(10),dp(12),dp(10),dp(12));return v;}@Override public View getDropDownView(int position,View convertView,ViewGroup parent){TextView v=(TextView)super.getDropDownView(position,convertView,parent);v.setTextColor(Color.WHITE);v.setTextSize(16);v.setBackgroundColor(Color.rgb(24,61,83));v.setPadding(dp(16),dp(14),dp(16),dp(14));return v;}};ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);s.setAdapter(ad);return s;}
    private TextView info(String s){TextView v=txt(s,13,false,Color.rgb(222,236,245));v.setPadding(dp(12),dp(12),dp(12),dp(12));return v;}
    private TextView out(){return txt(tr("Valmis laskentaan.","Ready to calculate."),14,false,Color.WHITE);}
    private int cyan(){return Color.rgb(119,205,255);} private String tr(String f,String e){return en?e:f;}
    private long val(EditText e){try{String s=e.getText().toString().replaceAll("[^0-9]","");return s.isEmpty()?0:Long.parseLong(s);}catch(Exception x){return 0;}}
    private String fmt(long n){return NumberFormat.getIntegerInstance(Locale.US).format(n);} private String formatSeconds(long s){long d=s/86400;s%=86400;long h=s/3600;s%=3600;long m=s/60;long sec=s%60;return(d>0?d+"d ":"")+(h>0?h+"h ":"")+(m>0?m+"m ":"")+sec+"s";} private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_LONG).show();}
    private EditText input(String h,boolean num){EditText e=new EditText(this);e.setHint(h);e.setHintTextColor(Color.rgb(205,222,233));e.setTextColor(Color.WHITE);e.setTextSize(17);e.setSingleLine();e.setPadding(dp(16),dp(12),dp(16),dp(12));e.setInputType(num?InputType.TYPE_CLASS_NUMBER:InputType.TYPE_CLASS_TEXT);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(24,61,83));g.setCornerRadius(dp(13));g.setStroke(dp(1),Color.rgb(58,111,143));e.setBackground(g);LinearLayout.LayoutParams p=mp(0,4,0,6);e.setLayoutParams(p);return e;}
    private LinearLayout box(int r,int g,int b,int rad){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);GradientDrawable d=new GradientDrawable();d.setColor(Color.rgb(r,g,b));d.setCornerRadius(dp(rad));l.setBackground(d);return l;}
    private TextView txt(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setTextColor(Color.WHITE);b.setAllCaps(false);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(28,139,207));g.setCornerRadius(dp(14));b.setBackground(g);b.setPadding(dp(12),dp(12),dp(12),dp(12));return b;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);} private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
    static class Row{String name;EditText need,have;Row(String n,EditText a,EditText b){name=n;need=a;have=b;}}
}
