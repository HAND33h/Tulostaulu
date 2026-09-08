package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
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
import java.util.List;
import java.util.Locale;

public class OperationsSuite6Activity extends Activity {
    private static final String PREFS="wos_tulostaulu";
    private LinearLayout root; private boolean en;
    private EditText fc,rfc,alloy,polish,plans,amber,guides,designs,secrets,petFood,manuals,potions,serums,heroXp,essence,mythril,speedups;
    private EditText total,inf,lan,mark; private TextView formationResult,recommendationResult;
    private EditText foundry,championship,showdown,council;
    private Spinner currentGear,currentCharm,currentFc;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);en="en".equals(p.getString("lang","fi"));
        ScrollView sc=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(18),dp(18),dp(44));root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        hero("👑 WOS OPERATIONS SUITE 6.1",tr("Inventory → ranked next upgrade → battle history → formations → alliance ops → event planning","Inventory → ranked next upgrade → battle history → formations → alliance ops → event planning"));

        section(tr("INVENTORY VAULT","INVENTORY VAULT"));
        root.addView(info(tr("Yksi paikallinen varasto kaikille laskureille. Arvot tallennetaan vain tähän laitteeseen.","One local inventory for all calculators. Values stay on this device.")));
        fc=field("Fire Crystals",p,"vault_fc");rfc=field("Refined Fire Crystals",p,"vault_rfc");alloy=field("Hardened Alloy",p,"vault_alloy");polish=field("Polishing Solution",p,"vault_polish");plans=field("Design Plans",p,"vault_plans");amber=field("Lunar Amber",p,"vault_amber");guides=field("Charm Guides",p,"vault_guides");designs=field("Charm Designs",p,"vault_designs");secrets=field("Charm/Jewel Secrets",p,"vault_secrets");petFood=field("Pet Food",p,"vault_petfood");manuals=field("Taming Manuals",p,"vault_manuals");potions=field("Energizing Potions",p,"vault_potions");serums=field("Strengthening Serums",p,"vault_serums");heroXp=field("Hero Gear XP",p,"vault_heroxp");essence=field("Essence Stones",p,"vault_essence");mythril=field("Mythril",p,"vault_mythril");speedups=field(tr("Speedups minuutteina","Speedups in minutes"),p,"vault_speedups");
        Button saveVault=button(tr("TALLENNA INVENTORY VAULT","SAVE INVENTORY VAULT"));saveVault.setOnClickListener(v->{saveVault(p);toast(tr("Inventory tallennettu","Inventory saved"));});root.addView(saveVault,mp(0,6,0,14));

        section(tr("BEST NEXT UPGRADE – OMA VARASTO","BEST NEXT UPGRADE – YOUR INVENTORY"));
        root.addView(info(tr("Tämä ranking käyttää vain sovelluksessa jo varmennettuja Chief Gear-, Charm- ja Furnace-taulukoita. Puuttuvaa power-dataa ei arvioida.","This ranking only uses Chief Gear, Charm and Furnace tables already verified in the app. Missing power data is not estimated.")));
        currentGear=spin(UpgradeRecommendationEngine.GEAR_LEVEL);currentGear.setSelection(clamp(p.getInt("best_current_gear",0),0,UpgradeRecommendationEngine.GEAR_LEVEL.length-1));
        currentCharm=spin(UpgradeRecommendationEngine.CHARM_LEVEL);currentCharm.setSelection(clamp(p.getInt("best_current_charm",0),0,UpgradeRecommendationEngine.CHARM_LEVEL.length-1));
        currentFc=spin(UpgradeRecommendationEngine.FC_LEVEL);currentFc.setSelection(clamp(p.getInt("best_current_fc",0),0,UpgradeRecommendationEngine.FC_LEVEL.length-1));
        root.addView(label(tr("Nykyinen Chief Gear -taso (vertailtava osa)","Current Chief Gear level (comparison piece)")));root.addView(currentGear);
        root.addView(label(tr("Nykyinen Charm-taso (vertailtava slot)","Current Charm level (comparison slot)")));root.addView(currentCharm);
        root.addView(label(tr("Nykyinen Furnace-taso","Current Furnace level")));root.addView(currentFc);
        Button rank=button(tr("LASKE PARAS SEURAAVA PÄIVITYS","RANK NEXT UPGRADES"));root.addView(rank,mp(0,6,0,8));recommendationResult=info("");root.addView(recommendationResult);rank.setOnClickListener(v->{saveVault(p);p.edit().putInt("best_current_gear",currentGear.getSelectedItemPosition()).putInt("best_current_charm",currentCharm.getSelectedItemPosition()).putInt("best_current_fc",currentFc.getSelectedItemPosition()).apply();rankUpgrades(p);});
        addLaunch("👑",tr("Master Upgrade Planner","Master Upgrade Planner"),MasterUpgradePlannerActivity.class);
        addLaunch("🚀",tr("Advanced Ops / Event Sniper / T12","Advanced Ops / Event Sniper / T12"),AdvancedOpsActivity.class);

        section(tr("MULTI-SCREENSHOT BATTLE LAB","MULTI-SCREENSHOT BATTLE LAB"));
        root.addView(info(tr("Lue useita saman taistelun raporttikuvia yhdellä kertaa, kerää troop/stat-havaintoja ja rakenna paikallinen raporttihistoria.","Read multiple screenshots from the same battle, collect troop/stat observations and build a local report history.")));
        addLaunch("⚔️",tr("Battle Report Analyzer 6.1","Battle Report Analyzer 6.1"),BattleReportAnalyzerActivity.class);
        addLaunch("🧪",tr("Battle Simulator","Battle Simulator"),BattleSimulatorActivity.class);

        section(tr("FORMATION GENERATOR","FORMATION GENERATOR"));
        total=input(tr("Joukkojen kokonaismäärä","Total troops"),"");inf=input("Infantry %","40");lan=input("Lancer %","30");mark=input("Marksman %","30");root.addView(total);root.addView(inf);root.addView(lan);root.addView(mark);
        LinearLayout presets=new LinearLayout(this);presets.setOrientation(LinearLayout.HORIZONTAL);Button bear=small("Bear 10/10/80"),rally=small("Rally 20/20/60"),pvp=small("PvP 40/30/30");presets.addView(bear,new LinearLayout.LayoutParams(0,-2,1));presets.addView(rally,new LinearLayout.LayoutParams(0,-2,1));presets.addView(pvp,new LinearLayout.LayoutParams(0,-2,1));root.addView(presets);bear.setOnClickListener(v->setRatio(10,10,80));rally.setOnClickListener(v->setRatio(20,20,60));pvp.setOnClickListener(v->setRatio(40,30,30));
        Button form=button(tr("LASKE FORMATION","CALCULATE FORMATION"));formationResult=info("");form.setOnClickListener(v->calcFormation());root.addView(form,mp(0,6,0,8));root.addView(formationResult);

        section(tr("ALLIANCE OPERATIONS","ALLIANCE OPERATIONS"));
        addLaunch("🧊",tr("State / Player / Alliance data","State / Player / Alliance data"),DataSourcesActivity.class);
        addLaunch("📸",tr("Attendance / ranking OCR","Attendance / ranking OCR"),ScreenshotImportActivity.class);
        addLaunch("💬",tr("Alliance forum","Alliance forum"),ForumActivity.class);
        root.addView(info(tr("Officer-tiedot säilytetään paikallisina muistiinpanoina eikä niitä lähetetä ulkopuoliselle palvelulle.","Officer data is kept as local notes and is not sent to an external service.")));
        foundry=noteField(tr("Foundry teams / ajat","Foundry teams / times"),p.getString("ops_foundry",""));championship=noteField(tr("Alliance Championship lanes","Alliance Championship lanes"),p.getString("ops_championship",""));showdown=noteField(tr("Alliance Showdown 6-stage plan","Alliance Showdown 6-stage plan"),p.getString("ops_showdown",""));council=noteField(tr("State Council / NAP / minister notes","State Council / NAP / minister notes"),p.getString("ops_council",""));root.addView(foundry);root.addView(championship);root.addView(showdown);root.addView(council);
        Button saveOps=button(tr("TALLENNA ALLIANCE OPS","SAVE ALLIANCE OPS"));saveOps.setOnClickListener(v->{p.edit().putString("ops_foundry",foundry.getText().toString()).putString("ops_championship",championship.getText().toString()).putString("ops_showdown",showdown.getText().toString()).putString("ops_council",council.getText().toString()).apply();toast(tr("Alliance Ops tallennettu","Alliance Ops saved"));});root.addView(saveOps,mp(0,6,0,14));

        section(tr("DATA UPDATE ENGINE","DATA UPDATE ENGINE"));
        root.addView(info(tr("Tietokerros erottaa Verified WOS Data / OCR-FID Observed Data / Community Advice. Datasetit versioidaan lähteellä, verified_at-ajalla ja checksumilla ennen automaattista käyttöönottoa. Local schema 6.1 • baseline 2026-09-08.","The data layer separates Verified WOS Data / OCR-FID Observed Data / Community Advice. Datasets are versioned with source, verified_at and checksum before automatic activation. Local schema 6.1 • baseline 2026-09-08.")));
        root.addView(info(tr("SvS- ja event-pisteitä ei kovakoodata, jos ne voivat riippua seasonista. Experts- ja T5/T6-välitasoja ei täytetä arvauksilla.","SvS and event scores are not hard-coded when they can vary by season. Experts and T5/T6 intermediate values are not filled with guesses.")));
        setContentView(sc);rankUpgrades(p);
    }

    private void saveVault(SharedPreferences p){SharedPreferences.Editor e=p.edit();put(e,"vault_fc",fc);put(e,"vault_rfc",rfc);put(e,"vault_alloy",alloy);put(e,"vault_polish",polish);put(e,"vault_plans",plans);put(e,"vault_amber",amber);put(e,"vault_guides",guides);put(e,"vault_designs",designs);put(e,"vault_secrets",secrets);put(e,"vault_petfood",petFood);put(e,"vault_manuals",manuals);put(e,"vault_potions",potions);put(e,"vault_serums",serums);put(e,"vault_heroxp",heroXp);put(e,"vault_essence",essence);put(e,"vault_mythril",mythril);put(e,"vault_speedups",speedups);e.apply();}
    private void rankUpgrades(SharedPreferences p){if(recommendationResult==null)return;List<UpgradeRecommendationEngine.Candidate> list=UpgradeRecommendationEngine.rank(p,currentGear==null?p.getInt("best_current_gear",0):currentGear.getSelectedItemPosition(),currentCharm==null?p.getInt("best_current_charm",0):currentCharm.getSelectedItemPosition(),currentFc==null?p.getInt("best_current_fc",0):currentFc.getSelectedItemPosition());StringBuilder s=new StringBuilder();int n=1;for(UpgradeRecommendationEngine.Candidate c:list){s.append(n++).append(". ").append(c.affordable?"✅ ":"⏳ ").append(c.title).append("\n");if(c.powerGain>0)s.append("Power +").append(fmt(c.powerGain)).append("\n");s.append(c.details).append("\n");}if(list.isEmpty())s.append(tr("Ei seuraavaa varmennettua päivitystä näillä tasoilla.","No next verified upgrade for these levels."));recommendationResult.setText(s.toString());p.edit().putString("last_best_upgrade",s.toString()).apply();}
    private EditText field(String name,SharedPreferences p,String key){EditText e=input(name,p.getString(key,"0"));root.addView(e);return e;}
    private EditText noteField(String hint,String v){EditText e=new EditText(this);e.setHint(hint);e.setHintTextColor(Color.rgb(140,170,188));e.setText(v);e.setTextColor(Color.WHITE);e.setMinLines(2);return e;}
    private void calcFormation(){long n=lng(total);double a=dbl(inf),b=dbl(lan),c=dbl(mark),s=a+b+c;if(n<=0||s<=0){toast(tr("Tarkista arvot","Check values"));return;}long i=Math.round(n*a/s),l=Math.round(n*b/s),m=Math.max(0,n-i-l);formationResult.setText("Infantry: "+fmt(i)+"\nLancer: "+fmt(l)+"\nMarksman: "+fmt(m)+"\n\n"+tr("Community-presetit ovat testilähtökohtia, eivät virallinen WOS-kaava.","Community presets are test starting points, not an official WOS formula."));getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("last_formation",i+"/"+l+"/"+m).apply();}
    private void setRatio(int a,int b,int c){inf.setText(String.valueOf(a));lan.setText(String.valueOf(b));mark.setText(String.valueOf(c));}
    private void addLaunch(String icon,String title,Class<?> cls){Button b=button(icon+"  "+title+"  →");b.setOnClickListener(v->startActivity(new Intent(this,cls)));root.addView(b,mp(0,0,0,8));}
    private void put(SharedPreferences.Editor e,String k,EditText v){e.putString(k,v.getText().toString().trim().isEmpty()?"0":v.getText().toString().trim());}
    private EditText input(String hint,String value){EditText e=new EditText(this);e.setHint(hint);e.setHintTextColor(Color.rgb(140,170,188));e.setText(value);e.setTextColor(Color.WHITE);e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);return e;}
    private Spinner spin(String[] items){Spinner s=new Spinner(this);ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items){@Override public View getView(int p,View c,ViewGroup vg){TextView v=(TextView)super.getView(p,c,vg);v.setTextColor(Color.WHITE);v.setPadding(dp(10),dp(10),dp(10),dp(10));return v;}@Override public View getDropDownView(int p,View c,ViewGroup vg){TextView v=(TextView)super.getDropDownView(p,c,vg);v.setTextColor(Color.WHITE);v.setBackgroundColor(Color.rgb(24,61,83));v.setPadding(dp(12),dp(12),dp(12),dp(12));return v;}};s.setAdapter(ad);return s;}
    private TextView label(String s){return text(s,12,true,Color.rgb(190,218,233));}
    private void hero(String a,String b){LinearLayout c=card();c.addView(text(a,25,true,Color.WHITE));c.addView(text(b,12,false,Color.rgb(205,226,238)));root.addView(c,mp(0,0,0,14));}
    private void section(String s){root.addView(text(s,13,true,Color.rgb(119,205,255)),mp(0,8,0,7));}
    private LinearLayout card(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(dp(16),dp(14),dp(16),dp(14));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(16,48,69));g.setCornerRadius(dp(18));g.setStroke(dp(1),Color.rgb(42,86,112));l.setBackground(g);return l;}
    private TextView info(String s){TextView v=text(s,12,false,Color.rgb(214,232,242));v.setPadding(dp(12),dp(10),dp(12),dp(10));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(11,37,55));g.setCornerRadius(dp(12));v.setBackground(g);return v;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(20,126,196));g.setCornerRadius(dp(12));b.setBackground(g);return b;}
    private Button small(String s){Button b=button(s);b.setTextSize(10);return b;}
    private TextView text(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private long lng(EditText e){try{return Long.parseLong(e.getText().toString().replaceAll("[^0-9]",""));}catch(Exception x){return 0;}}
    private double dbl(EditText e){try{return Double.parseDouble(e.getText().toString().replace(',','.').trim());}catch(Exception x){return 0;}}
    private String fmt(long n){return NumberFormat.getIntegerInstance(en?Locale.US:new Locale("fi","FI")).format(n);}
    private int clamp(int n,int lo,int hi){return Math.max(lo,Math.min(hi,n));}
    private String tr(String fi,String eng){return en?eng:fi;}private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
