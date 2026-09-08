package com.hand33h.tulostaulu;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class CommunityStrategyActivity extends Activity {
    private Spinner spend, goal;
    private TextView result;

    @Override protected void onCreate(Bundle b){super.onCreate(b);
        ScrollView sc=new ScrollView(this); LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(18),dp(20),dp(18),dp(40)); root.setBackgroundColor(Color.rgb(7,23,39)); sc.addView(root);
        root.addView(txt("💬 COMMUNITY STRATEGY",25,true,Color.WHITE));
        root.addView(txt("Reddit-yhteisön käytännön vinkit erillään varmennetusta pelidatasta. Valitse pelityyli ja tavoite.",13,false,Color.rgb(202,224,238)),mp(0,5,0,16));
        spend=spinner(new String[]{"F2P","Low spender","Spender / Rally leader"}); goal=spinner(new String[]{"Bear Trap – Joiner","Bear Trap – Rally Leader","SvS / PvP","Yleinen upgrade priority"});
        root.addView(txt("Pelityyli",13,true,Color.rgb(119,205,255)));root.addView(spend,mp(0,4,0,12)); root.addView(txt("Tavoite",13,true,Color.rgb(119,205,255)));root.addView(goal,mp(0,4,0,12));
        Button calc=new Button(this);calc.setText("TEE SUOSITUS");calc.setTextColor(Color.WHITE);calc.setTypeface(Typeface.DEFAULT_BOLD);GradientDrawable bg=new GradientDrawable();bg.setColor(Color.rgb(20,126,196));bg.setCornerRadius(dp(12));calc.setBackground(bg);root.addView(calc,mp(0,4,0,14));
        result=txt("",14,false,Color.WHITE); LinearLayout card=box();card.addView(result);root.addView(card);
        root.addView(txt("⚠ COMMUNITY ADVICE: nämä ovat yhteisön strategiahavaintoja, eivät virallisia WOS-sääntöjä. Pelipäivitykset voivat muuttaa optimaalista strategiaa.",11,false,Color.rgb(245,194,110)),mp(0,14,0,0));
        calc.setOnClickListener(v->recommend()); recommend(); setContentView(sc);
    }
    private void recommend(){String g=(String)goal.getSelectedItem();String s=(String)spend.getSelectedItem();String out="🎯 "+g+"\n\n";
        if(g.contains("Joiner")) out+="• Priorisoi oikea ensimmäisen slotin joiner-hero ja sen Expedition-skill.\n• Jessie, Jasser ja Seo-Yoon ovat yleisiä Bear-joiner-valintoja.\n• Vältä joiner-heroa, jonka ensimmäinen Expedition-skill ei auta Bear-vahinkoa.\n• Marksman-painotus on yleinen yhteisöstrategia, mutta pidä myös Infantry/Lancer mukana muodostelmassa.\n• Chief Gear/Charms ja hero gear eivät ole joinerin tärkein optimointikohde; rally leaderin buffit ratkaisevat paljon.";
        else if(g.contains("Leader")) out+="• Käytä vahvimpia hyökkäysbuffeja tuovia rally-heroja.\n• Panosta erityisesti Marksman lethality/attackiin, Hero Geariin, widgetteihin, Chief Geariin ja Charmeihin.\n• Tarkista ensimmäisten joinerien hero-skillit: huono joiner-buffi voi laskea koko rallyn vahinkoa.\n• Lisää rally capacity / deployment capacity kun mahdollista.";
        else if(g.contains("SvS")) out+="• Säästä isot Gear/Charm/Hero Gear/Pet/Research-päivitykset oikealle SvS Prep -päivälle.\n• Älä hajauta harvinaisia materiaaleja liian moneen puolivalmiiseen kohteeseen.\n• Priorisoi oman roolisi troop-statseja ja varmista set/threshold-hyödyt ennen materiaalien käyttöä.";
        else out+="• Tee current → target -suunnitelma ennen materiaalien käyttöä.\n• Keskity ensin päivitykseen, joka avaa selkeän skill-, set- tai power-kynnyksen.\n• Pidä Gear, Charms, Hero Gear, Pets, Research ja Fire Crystal -kulut samassa Master Plannerissa.";
        if(s.startsWith("F2P")) out+="\n\n💎 F2P: säästä universaalit/harvinaiset materiaalit selkeisiin power spikeihin; vältä puolikkaita investointeja.";
        else if(s.startsWith("Low")) out+="\n\n💎 Low spender: priorisoi pysyvät stat-parannukset ja tapahtumien milestone-arvo ennen pieniä hajapäivityksiä.";
        else out+="\n\n👑 Rally leader: omat statsit, hero gear, widgets, research, chief gear ja charms vaikuttavat eniten johtamiesi rallyjen suorituskykyyn.";
        result.setText(out);
    }
    private Spinner spinner(String[] a){Spinner s=new Spinner(this);ArrayAdapter<String>x=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,a);s.setAdapter(x);return s;}
    private LinearLayout box(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(dp(16),dp(16),dp(16),dp(16));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(18,48,70));g.setCornerRadius(dp(18));g.setStroke(dp(1),Color.rgb(42,86,112));l.setBackground(g);return l;}
    private TextView txt(String s,int z,boolean b,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(b)v.setTypeface(Typeface.DEFAULT_BOLD);return v;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private LinearLayout.LayoutParams mp(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
