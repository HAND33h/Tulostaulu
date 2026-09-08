package com.hand33h.tulostaulu;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Locale;

public class BattleSimulatorActivity extends Activity {
    private LinearLayout root;
    private EditText aTroops,aInf,aLan,aMar,aAtk,aDef,aHp,aLeth;
    private EditText dTroops,dInf,dLan,dMar,dAtk,dDef,dHp,dLeth;
    private TextView result;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        ScrollView sc=new ScrollView(this);
        root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(16),dp(18),dp(16),dp(40)); root.setBackgroundColor(Color.rgb(7,23,39));
        sc.addView(root);

        root.addView(title("⚔️  BATTLE SIMULATOR"));
        root.addView(info("Arvioiva WOS-taistelusimulaattori. Syötä molempien osapuolten troop-määrä, jakauma ja Bonus Overview -prosentit. Malli ei väitä käyttävänsä pelin salaista serverikaavaa, vaan antaa vertailukelpoisen arvion."));

        TextView at=section("ATTACKER"); root.addView(at);
        aTroops=field("Total troops", "100000"); root.addView(aTroops);
        aInf=field("Infantry %", "40"); root.addView(aInf);
        aLan=field("Lancer %", "30"); root.addView(aLan);
        aMar=field("Marksman %", "30"); root.addView(aMar);
        aAtk=field("Troops' Attack %", "444.64"); root.addView(aAtk);
        aDef=field("Troops' Defense %", "495.23"); root.addView(aDef);
        aHp=field("Troops' Health %", "118.05"); root.addView(aHp);
        aLeth=field("Troops' Lethality %", "115.80"); root.addView(aLeth);

        TextView dt=section("DEFENDER"); root.addView(dt);
        dTroops=field("Total troops", "100000"); root.addView(dTroops);
        dInf=field("Infantry %", "40"); root.addView(dInf);
        dLan=field("Lancer %", "30"); root.addView(dLan);
        dMar=field("Marksman %", "30"); root.addView(dMar);
        dAtk=field("Troops' Attack %", "400"); root.addView(dAtk);
        dDef=field("Troops' Defense %", "450"); root.addView(dDef);
        dHp=field("Troops' Health %", "110"); root.addView(dHp);
        dLeth=field("Troops' Lethality %", "110"); root.addView(dLeth);

        Button sim=button("RUN SIMULATION"); sim.setOnClickListener(v->simulate()); root.addView(sim,margin(0,14,0,12));
        result=info("Tulokset näkyvät tässä."); root.addView(result);
        setContentView(sc);
    }

    private void simulate(){
        try{
            double ai=n(aInf), al=n(aLan), am=n(aMar), di=n(dInf), dl=n(dLan), dm=n(dMar);
            if(Math.abs((ai+al+am)-100)>0.2 || Math.abs((di+dl+dm)-100)>0.2){ result.setText("⚠️ Infantry + Lancer + Marksman pitää olla yhteensä 100 % molemmilla puolilla."); return; }
            double aCount=n(aTroops), dCount=n(dTroops);
            double aBase=armyScore(aCount,n(aAtk),n(aDef),n(aHp),n(aLeth));
            double dBase=armyScore(dCount,n(dAtk),n(dDef),n(dHp),n(dLeth));
            double aCounter=counterFactor(ai,al,am,di,dl,dm);
            double dCounter=counterFactor(di,dl,dm,ai,al,am);
            double aScore=aBase*aCounter, dScore=dBase*dCounter;
            double p=aScore/(aScore+dScore);
            String winner=p>=0.5?"ATTACKER":"DEFENDER";
            double winnerPct=Math.max(p,1-p)*100;
            double aLoss=Math.min(100, Math.max(5, 72*(dScore/(aScore+dScore))));
            double dLoss=Math.min(100, Math.max(5, 72*(aScore/(aScore+dScore))));
            long aRemain=Math.max(0,Math.round(aCount*(1-aLoss/100.0)));
            long dRemain=Math.max(0,Math.round(dCount*(1-dLoss/100.0)));
            result.setText(String.format(Locale.US,
                    "🏆 Predicted winner: %s\n\nWin estimate\nAttacker: %.1f %%\nDefender: %.1f %%\n\nRelative combat score\nAttacker: %,.0f\nDefender: %,.0f\n\nEstimated troop loss\nAttacker: %.1f %%  (~%,d left)\nDefender: %.1f %%  (~%,d left)\n\nCounter multiplier\nAttacker: %.3f×\nDefender: %.3f×\n\nConfidence: experimental / calibration needed from real Battle Reports.",
                    winner,p*100,(1-p)*100,aScore,dScore,aLoss,aRemain,dLoss,dRemain,aCounter,dCounter));
        }catch(Exception e){result.setText("⚠️ Tarkista syötetyt numerot. Käytä esimerkiksi 444.64 eikä %-merkkiä.");}
    }

    private double armyScore(double troops,double atk,double def,double hp,double leth){
        double offense=(1+atk/100.0)*(1+leth/100.0);
        double survival=(1+def/100.0)*(1+hp/100.0);
        return troops*Math.sqrt(offense*survival);
    }

    // Simple WOS-like class-counter approximation for comparative simulation.
    // Infantry pressures Lancer, Lancer pressures Marksman, Marksman pressures Infantry.
    private double counterFactor(double inf,double lan,double mar,double oInf,double oLan,double oMar){
        double advantage=(inf*oLan + lan*oMar + mar*oInf)/10000.0;
        double disadvantage=(inf*oMar + lan*oInf + mar*oLan)/10000.0;
        return Math.max(0.80,Math.min(1.20,1.0+0.20*(advantage-disadvantage)));
    }

    private double n(EditText e){return Double.parseDouble(e.getText().toString().trim().replace(',','.'));}
    private TextView title(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.WHITE);v.setTextSize(25);v.setTypeface(Typeface.DEFAULT_BOLD);v.setPadding(0,0,0,dp(8));return v;}
    private TextView section(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.rgb(119,205,255));v.setTextSize(15);v.setTypeface(Typeface.DEFAULT_BOLD);v.setPadding(0,dp(18),0,dp(6));return v;}
    private TextView info(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.rgb(210,228,240));v.setTextSize(13);v.setPadding(dp(14),dp(14),dp(14),dp(14));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(18,48,70));g.setCornerRadius(dp(14));g.setStroke(dp(1),Color.rgb(42,86,112));v.setBackground(g);return v;}
    private EditText field(String hint,String value){EditText e=new EditText(this);e.setHint(hint);e.setText(value);e.setTextColor(Color.WHITE);e.setHintTextColor(Color.rgb(145,170,188));e.setTextSize(15);e.setSingleLine(true);e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);e.setPadding(dp(14),dp(12),dp(14),dp(12));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(13,39,58));g.setCornerRadius(dp(10));g.setStroke(dp(1),Color.rgb(39,77,101));e.setBackground(g);e.setLayoutParams(margin(0,0,0,8));return e;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTextSize(15);b.setTypeface(Typeface.DEFAULT_BOLD);b.setGravity(Gravity.CENTER);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(20,126,196));g.setCornerRadius(dp(12));b.setBackground(g);return b;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);} private LinearLayout.LayoutParams margin(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
