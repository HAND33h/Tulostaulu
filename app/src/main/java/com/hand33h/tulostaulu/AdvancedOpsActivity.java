package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.*;
import java.text.NumberFormat;
import java.util.*;

public class AdvancedOpsActivity extends Activity {
    private boolean en;
    private LinearLayout root;
    private EditText gap;
    private final EditText[] names=new EditText[5], points=new EditText[5], stock=new EditText[5];
    private TextView sniperResult;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        en="en".equals(getSharedPreferences("wos_tulostaulu",MODE_PRIVATE).getString("lang","fi"));
        ScrollView sc=new ScrollView(this); root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(18),dp(18),dp(18),dp(40)); root.setBackgroundColor(Color.rgb(7,23,39)); sc.addView(root);

        title("🚀  "+tr("WOS ADVANCED OPS 5.0","WOS ADVANCED OPS 5.0"),tr("Event Sniper, T12, State Age, Transfer, VIP, Territory ja Alliance Ops yhdessä.","Event Sniper, T12, State Age, Transfer, VIP, Territory and Alliance Ops in one place."));

        section(tr("EVENT SNIPER / POINT GAP","EVENT SNIPER / POINT GAP"));
        root.addView(info(tr("Syötä tämän päivän pelissä näkyvät pistearvot. Laskuri ei käytä arvattuja event-kaavoja. Se etsii omasta varastostasi yhdistelmän, joka ylittää tavoitepisteet mahdollisimman pienellä toimenpidemäärällä.","Enter the point values shown in-game for the current day. The calculator does not guess event formulas. It finds a combination from your stock that closes the point gap with as few actions as possible.")));
        gap=input(tr("Puuttuvat pisteet","Points remaining"),true); root.addView(gap);
        for(int i=0;i<5;i++){
            LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.HORIZONTAL);
            names[i]=input(tr("Toiminto ","Action ")+(i+1),false); points[i]=input(tr("Pisteet/kpl","Points/unit"),true); stock[i]=input(tr("Varasto","Stock"),true);
            row.addView(names[i],new LinearLayout.LayoutParams(0,-2,1.3f)); row.addView(points[i],new LinearLayout.LayoutParams(0,-2,1)); row.addView(stock[i],new LinearLayout.LayoutParams(0,-2,1)); root.addView(row,mp(0,4,0,0));
        }
        Button solve=button(tr("LASKE HALVIN TOIMENPIDEMÄÄRÄ","CALCULATE MINIMUM ACTIONS")); root.addView(solve,mp(0,8,0,8)); sniperResult=info(tr("Syötä pistevaje ja vähintään yksi toiminto.","Enter a point gap and at least one action.")); root.addView(sniperResult); solve.setOnClickListener(v->solveGap());

        section(tr("T12 / EXALTED TROOPS","T12 / EXALTED TROOPS"));
        root.addView(info(tr("T12-polku pidetään erillään tavallisesta troop-laskurista: War Academy research, Equipment, Skills, Solar Supremacy ja troop-kohtaiset linjat. T11→T12 promotion ja uusi T12 training ovat eri kustannuspolkuja. Täydelliset kulut näytetään vain varmennetusta datasta.","The T12 path is kept separate from normal troops: War Academy research, Equipment, Skills, Solar Supremacy and troop-specific lines. T11→T12 promotion and fresh T12 training are different cost paths. Full costs are shown only when backed by verified data.")));
        Button wa=button(tr("🎓 AVAA WAR ACADEMY / T12","🎓 OPEN WAR ACADEMY / T12")); wa.setOnClickListener(v->startActivity(new Intent(this,WarAcademyActivity.class))); root.addView(wa,mp(0,0,0,8));
        Button troops=button(tr("⚔️ AVAA TROOP-LASKURI","⚔️ OPEN TROOP CALCULATOR")); troops.setOnClickListener(v->{Intent i=new Intent(this,UpgradePlannerActivity.class);i.putExtra("mode","troops");startActivity(i);}); root.addView(troops,mp(0,0,0,12));

        section(tr("STATE AGE / UNLOCK TIMELINE","STATE AGE / UNLOCK TIMELINE"));
        root.addView(info(tr("State Age -osio näyttää mitä sisältöä kannattaa odottaa seuraavaksi: hero-sukupolvet, pets, FC, Experts, War Academy ja muut unlockit. Ajantasainen lähde avataan WSCO:n tietokantaan, koska unlock-päivät voivat muuttua patchien mukana.","State Age helps track what content should unlock next: hero generations, pets, FC, Experts, War Academy and more. The live source opens WSCO because unlock timing can change with patches.")));
        linkButton("🗓️ "+tr("AVAA WSCO STATE AGE DATA","OPEN WSCO STATE AGE DATA"),"https://www.whiteoutsurvival-community.com/tools/database/");

        section(tr("STATE TRANSFER PLANNER","STATE TRANSFER PLANNER"));
        root.addView(info(tr("Transfer-suunnittelussa pidetään erillään Transfer Score, troop power ja state-kohtaiset rajat. Sovellus ei arvaa score-kaavaa; käytä varmennettua transfer-dataa ja omaa profiilia ennen päätöstä.","Transfer planning keeps Transfer Score, troop power and state-specific limits separate. The app does not guess the score formula; use verified transfer data and your own profile before deciding.")));
        linkButton("🧭 "+tr("AVAA TRANSFER BOARD","OPEN TRANSFER BOARD"),"https://www.whiteoutsurvival-community.com/tools/state-transfer-board.html");
        linkButton("📘 "+tr("AVAA TRANSFER GUIDE","OPEN TRANSFER GUIDE"),"https://www.whiteoutsurvival-community.com/tools/wiki/events/state-transfer-guide.html");

        section(tr("VIP / HERO / EXPERT PROGRESSION","VIP / HERO / EXPERT PROGRESSION"));
        Button vip=button("👑 VIP"); vip.setOnClickListener(v->{Intent i=new Intent(this,NativeCalculatorActivity.class);i.putExtra("mode","vip");startActivity(i);}); root.addView(vip,mp(0,0,0,8));
        Button hero=button("🦸 Hero Gear"); hero.setOnClickListener(v->{Intent i=new Intent(this,NativeCalculatorActivity.class);i.putExtra("mode","hero_gear");startActivity(i);}); root.addView(hero,mp(0,0,0,8));
        Button exp=button("👨‍🔬 Experts"); exp.setOnClickListener(v->{Intent i=new Intent(this,NativeCalculatorActivity.class);i.putExtra("mode","experts");startActivity(i);}); root.addView(exp,mp(0,0,0,12));

        section(tr("TERRITORY / ALLIANCE OPS","TERRITORY / ALLIANCE OPS"));
        root.addView(info(tr("Officer-työkalut: Territory Planner, banneri- ja hive-suunnittelu, recruitment, event-ajat ja alliance-operaatiot. Ulkoiset plannerit avataan lähteeseen, kunnes oma karttamoottori on varmennettu.","Officer tools: Territory Planner, banner/hive planning, recruitment, event times and alliance operations. External planners open at the source until an in-app map engine is verified.")));
        linkButton("🗺️ "+tr("TERRITORY PLANNER","TERRITORY PLANNER"),"https://www.whiteoutsurvival-community.com/tools/territory-planner.html");
        Button forum=button("💬 "+tr("ALLIANCE / FOORUMI","ALLIANCE / FORUM")); forum.setOnClickListener(v->startActivity(new Intent(this,ForumActivity.class))); root.addView(forum,mp(0,0,0,8));
        linkButton("📅 "+tr("EVENT CALENDAR / WOS FORGE","EVENT CALENDAR / WOS FORGE"),"https://wosforge.org/");

        root.addView(info(tr("5.0-periaate: Verified WOS Data ≠ OCR/FID Observations ≠ Community Advice. Tuntemattomia kustannuksia, unlock-päiviä tai event-pisteitä ei arvata.","5.0 principle: Verified WOS Data ≠ OCR/FID Observations ≠ Community Advice. Unknown costs, unlock dates or event point values are never guessed.")),mp(0,12,0,0));
        setContentView(sc);
    }

    private void solveGap(){
        long target=num(gap); if(target<=0){toast(tr("Syötä pistevaje.","Enter a point gap."));return;}
        List<Item> items=new ArrayList<>();
        for(int i=0;i<5;i++){long p=num(points[i]),s=num(stock[i]);String n=names[i].getText().toString().trim();if(p>0&&s>0)items.add(new Item(n.isEmpty()?tr("Toiminto ","Action ")+(i+1):n,p,s));}
        if(items.isEmpty()){toast(tr("Lisää vähintään yksi toiminto.","Add at least one action."));return;}
        items.sort((a,b)->Long.compare(b.points,a.points));
        long remaining=target,total=0,actions=0; StringBuilder out=new StringBuilder();
        for(Item it:items){if(remaining<=0)break;long need=(remaining+it.points-1)/it.points;long use=Math.min(it.stock,need);if(use>0){long pts=use*it.points;remaining-=pts;total+=pts;actions+=use;out.append(it.name).append(": ").append(fmt(use)).append(" × ").append(fmt(it.points)).append(" = ").append(fmt(pts)).append("\n");}}
        if(remaining>0){out.append("\n⚠ ").append(tr("Varasto ei riitä. Puuttuu vielä noin ","Stock is insufficient. Still short by about ")).append(fmt(remaining)).append(" ").append(tr("pistettä.","points."));}
        else {out.append("\n✓ ").append(tr("Tavoite saavutettu","Target reached")).append("\n").append(tr("Toimenpiteitä: ","Actions: ")).append(fmt(actions)).append("\n").append(tr("Pisteitä yhteensä: ","Total points: ")).append(fmt(total)).append("\n").append(tr("Ylitys: ","Overshoot: ")).append(fmt(Math.max(0,total-target)));}
        out.append("\n\n").append(tr("Huom: optimointi minimoi ensin toimenpiteiden määrää käyttäjän syöttämillä tämän päivän pistearvoilla.","Note: optimization first minimizes action count using the point values you entered for today."));
        sniperResult.setText(out.toString());
    }

    static class Item{String name;long points,stock;Item(String n,long p,long s){name=n;points=p;stock=s;}}
    private void linkButton(String text,String url){Button b=button(text);b.setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url))));root.addView(b,mp(0,0,0,8));}
    private void title(String t,String s){LinearLayout c=card();c.addView(txt(t,25,true,Color.WHITE));c.addView(txt(s,12,false,Color.rgb(205,226,238)));root.addView(c,mp(0,0,0,14));}
    private void section(String s){root.addView(txt(s,13,true,Color.rgb(119,205,255)),mp(0,8,0,7));}
    private LinearLayout card(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(dp(16),dp(14),dp(16),dp(14));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(16,48,69));g.setCornerRadius(dp(18));g.setStroke(dp(1),Color.rgb(42,86,112));l.setBackground(g);return l;}
    private TextView info(String s){TextView v=txt(s,12,false,Color.rgb(214,232,242));v.setPadding(dp(12),dp(10),dp(12),dp(10));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(11,37,55));g.setCornerRadius(dp(12));g.setStroke(dp(1),Color.rgb(34,78,102));v.setBackground(g);return v;}
    private EditText input(String hint,boolean numeric){EditText e=new EditText(this);e.setHint(hint);e.setHintTextColor(Color.rgb(140,170,188));e.setTextColor(Color.WHITE);if(numeric)e.setInputType(InputType.TYPE_CLASS_NUMBER);return e;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(20,126,196));g.setCornerRadius(dp(12));b.setBackground(g);return b;}
    private TextView txt(String s,int z,boolean bold,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(bold)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private long num(EditText e){try{return Long.parseLong(e.getText().toString().replace(" ","").trim());}catch(Exception x){return 0;}}
    private String fmt(long n){return NumberFormat.getIntegerInstance(en?Locale.US:new Locale("fi","FI")).format(n);}
    private String tr(String fi,String eng){return en?eng:fi;}
    private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
