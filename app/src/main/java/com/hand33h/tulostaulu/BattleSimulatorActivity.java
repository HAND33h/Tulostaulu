package com.hand33h.tulostaulu;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class BattleSimulatorActivity extends Activity {
    private LinearLayout root;
    private EditText aTroops,aInf,aLan,aMar,aAtk,aDef,aHp,aLeth;
    private EditText dTroops,dInf,dLan,dMar,dAtk,dDef,dHp,dLeth;
    private Spinner aPet,aPetLevel,aExpert,aAffinity,dPet,dPetLevel,dExpert,dAffinity;
    private TextView result;

    private static final String[] PETS={
            "None","Arctic Wolf","Cave Hyena","Musk Ox","Giant Tapir","Titan Roc","Giant Elk","Snow Leopard",
            "Cave Lion","Snow Ape","Iron Rhino","Saber-tooth Tiger","Mammoth","Frost Gorilla","Frostscale Chameleon"
    };
    private static final String[] PET_LEVELS={"10","20","30","40","50","60","70","80","90","100"};
    private static final String[] EXPERTS={
            "None","Agnes","Cyrille","Holger","Romulus","Baldur","Fabian","Valeria","Ronne","Kathy","Gareth","Justus (upcoming)"
    };
    private static final String[] AFFINITY={"0","10","20","30","40","50","60","70","80","90","100"};

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        ScrollView sc=new ScrollView(this);
        root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(16),dp(18),dp(16),dp(40)); root.setBackgroundColor(Color.rgb(7,23,39));
        sc.addView(root);

        root.addView(title("⚔️  BATTLE SIMULATOR"));
        root.addView(info("Arvioiva WOS-taistelusimulaattori. Pet- ja Dawn Academy Expert -valinnat huomioidaan tunnetuilla taistelubonuksilla. Petin aktiivisen skillin oletetaan olevan käytössä. Event-kohtaisia Expert-skillejä ei vielä oleteta automaattisesti."));

        root.addView(section("ATTACKER"));
        aTroops=field("Total troops", "100000"); root.addView(aTroops);
        aInf=field("Infantry %", "40"); root.addView(aInf);
        aLan=field("Lancer %", "30"); root.addView(aLan);
        aMar=field("Marksman %", "30"); root.addView(aMar);
        aAtk=field("Troops' Attack %", "444.64"); root.addView(aAtk);
        aDef=field("Troops' Defense %", "495.23"); root.addView(aDef);
        aHp=field("Troops' Health %", "118.05"); root.addView(aHp);
        aLeth=field("Troops' Lethality %", "115.80"); root.addView(aLeth);
        root.addView(sub("🐾 Pet")); aPet=spinner(PETS); root.addView(aPet); root.addView(sub("Pet advancement")); aPetLevel=spinner(PET_LEVELS); aPetLevel.setSelection(9); root.addView(aPetLevel);
        root.addView(sub("🎓 Dawn Academy Expert")); aExpert=spinner(EXPERTS); root.addView(aExpert); root.addView(sub("Expert affinity")); aAffinity=spinner(AFFINITY); aAffinity.setSelection(10); root.addView(aAffinity);

        root.addView(section("DEFENDER"));
        dTroops=field("Total troops", "100000"); root.addView(dTroops);
        dInf=field("Infantry %", "40"); root.addView(dInf);
        dLan=field("Lancer %", "30"); root.addView(dLan);
        dMar=field("Marksman %", "30"); root.addView(dMar);
        dAtk=field("Troops' Attack %", "400"); root.addView(dAtk);
        dDef=field("Troops' Defense %", "450"); root.addView(dDef);
        dHp=field("Troops' Health %", "110"); root.addView(dHp);
        dLeth=field("Troops' Lethality %", "110"); root.addView(dLeth);
        root.addView(sub("🐾 Pet")); dPet=spinner(PETS); root.addView(dPet); root.addView(sub("Pet advancement")); dPetLevel=spinner(PET_LEVELS); dPetLevel.setSelection(9); root.addView(dPetLevel);
        root.addView(sub("🎓 Dawn Academy Expert")); dExpert=spinner(EXPERTS); root.addView(dExpert); root.addView(sub("Expert affinity")); dAffinity=spinner(AFFINITY); dAffinity.setSelection(10); root.addView(dAffinity);

        Button sim=button("RUN SIMULATION"); sim.setOnClickListener(v->simulate()); root.addView(sim,margin(0,14,0,12));
        result=info("Tulokset näkyvät tässä."); root.addView(result);
        setContentView(sc);
    }

    private void simulate(){
        try{
            double ai=n(aInf), al=n(aLan), am=n(aMar), di=n(dInf), dl=n(dLan), dm=n(dMar);
            if(Math.abs((ai+al+am)-100)>0.2 || Math.abs((di+dl+dm)-100)>0.2){ result.setText("⚠️ Infantry + Lancer + Marksman pitää olla yhteensä 100 % molemmilla puolilla."); return; }

            Stats as=new Stats(n(aAtk),n(aDef),n(aHp),n(aLeth));
            Stats ds=new Stats(n(dAtk),n(dDef),n(dHp),n(dLeth));
            double aCount=n(aTroops), dCount=n(dTroops);

            applyExpert(as,(String)aExpert.getSelectedItem(),Integer.parseInt((String)aAffinity.getSelectedItem()));
            applyExpert(ds,(String)dExpert.getSelectedItem(),Integer.parseInt((String)dAffinity.getSelectedItem()));

            PetResult apr=applyPet(as,ds,(String)aPet.getSelectedItem(),Integer.parseInt((String)aPetLevel.getSelectedItem()));
            PetResult dpr=applyPet(ds,as,(String)dPet.getSelectedItem(),Integer.parseInt((String)dPetLevel.getSelectedItem()));
            aCount += apr.squadCapacity;
            dCount += dpr.squadCapacity;

            double aBase=armyScore(aCount,as.atk,as.def,as.hp,as.leth);
            double dBase=armyScore(dCount,ds.atk,ds.def,ds.hp,ds.leth);
            double aCounter=counterFactor(ai,al,am,di,dl,dm);
            double dCounter=counterFactor(di,dl,dm,ai,al,am);
            double aScore=aBase*aCounter, dScore=dBase*dCounter;
            double p=aScore/(aScore+dScore);
            String winner=p>=0.5?"ATTACKER":"DEFENDER";
            double aLoss=Math.min(100, Math.max(5, 72*(dScore/(aScore+dScore))));
            double dLoss=Math.min(100, Math.max(5, 72*(aScore/(aScore+dScore))));
            long aRemain=Math.max(0,Math.round(aCount*(1-aLoss/100.0)));
            long dRemain=Math.max(0,Math.round(dCount*(1-dLoss/100.0)));

            result.setText(String.format(Locale.US,
                    "🏆 Predicted winner: %s\n\nWin estimate\nAttacker: %.1f %%\nDefender: %.1f %%\n\nEffective stats after Pet + Expert\nA: ATK %.2f | DEF %.2f | HP %.2f | LETH %.2f\nD: ATK %.2f | DEF %.2f | HP %.2f | LETH %.2f\n\nEffective squad size\nAttacker: %,.0f\nDefender: %,.0f\n\nRelative combat score\nAttacker: %,.0f\nDefender: %,.0f\n\nEstimated troop loss\nAttacker: %.1f %%  (~%,d left)\nDefender: %.1f %%  (~%,d left)\n\nPet notes\nA: %s\nD: %s\n\nCounter multiplier\nAttacker: %.3f×\nDefender: %.3f×\n\nConfidence: experimental. Calibrate with real Battle Reports.",
                    winner,p*100,(1-p)*100,as.atk,as.def,as.hp,as.leth,ds.atk,ds.def,ds.hp,ds.leth,aCount,dCount,aScore,dScore,aLoss,aRemain,dLoss,dRemain,apr.note,dpr.note,aCounter,dCounter));
        }catch(Exception e){result.setText("⚠️ Tarkista syötetyt numerot. Käytä esimerkiksi 444.64 eikä %-merkkiä.");}
    }

    private void applyExpert(Stats s,String name,int affinity){
        double scale=affinity/100.0;
        if("Agnes".equals(name)) s.def+=15*scale;
        else if("Cyrille".equals(name)) s.atk+=15*scale;
        else if("Holger".equals(name)){s.atk+=15*scale;s.def+=15*scale;}
        else if("Romulus".equals(name)){s.leth+=20*scale;s.hp+=20*scale;}
        else if("Baldur".equals(name)){s.atk+=10*scale;s.def+=10*scale;}
        else if("Fabian".equals(name)){s.leth+=15*scale;s.hp+=15*scale;}
        else if("Valeria".equals(name)){s.leth+=20*scale;s.hp+=20*scale;}
        else if("Ronne".equals(name)){s.atk+=15*scale;s.def+=15*scale;}
        else if("Kathy".equals(name)){s.leth+=15*scale;s.hp+=15*scale;}
        else if("Gareth".equals(name)){s.atk+=30*scale;s.hp+=30*scale;}
    }

    private PetResult applyPet(Stats own,Stats enemy,String pet,int level){
        int i=Math.max(0,Math.min(9,level/10-1));
        double[] pct={2.5,3,3.5,4,5,6,7,8,9,10};
        if("Cave Lion".equals(pet)){own.atk+=pct[i]; return new PetResult(0,"Feral Anthem +"+pct[i]+"% ATK");}
        if("Saber-tooth Tiger".equals(pet)){own.leth+=pct[i]; return new PetResult(0,"Apex Assault +"+pct[i]+"% Lethality");}
        if("Mammoth".equals(pet)){own.def+=pct[i]; return new PetResult(0,"Hardened Skin +"+pct[i]+"% Defense");}
        if("Frost Gorilla".equals(pet)){own.hp+=pct[i]; return new PetResult(0,"Earthbound Vigor +"+pct[i]+"% Health");}
        if("Frostscale Chameleon".equals(pet)){enemy.def-=pct[i]; return new PetResult(0,"Enemy Defense -"+pct[i]+"%");}
        if("Snow Leopard".equals(pet)){
            double[] red={1.5,2,2.5,3,3.5,4,4.25,4.5,4.75,5};
            enemy.leth-=red[i]; return new PetResult(0,"Enemy Lethality -"+red[i]+"% (march speed excluded)");
        }
        if("Titan Roc".equals(pet)){
            double[] red={1.5,2,2.5,3,3.5,4,4.25,4.5,4.75,5};
            enemy.hp-=red[i]; return new PetResult(0,"Enemy Health -"+red[i]+"%");
        }
        if("Snow Ape".equals(pet)){double cap=1500.0*(i+1); return new PetResult(cap,"Squad Capacity +"+(int)cap);}
        if("Iron Rhino".equals(pet)){double cap=60000+10000.0*i; return new PetResult(0,"Rally Capacity +"+(int)cap+" (shown only; not added to single march)");}
        if("Arctic Wolf".equals(pet)) return new PetResult(0,"Utility: stamina; no direct battle modifier");
        if("Cave Hyena".equals(pet)) return new PetResult(0,"Utility: construction; no direct battle modifier");
        if("Musk Ox".equals(pet)) return new PetResult(0,"Utility: gathering; no direct battle modifier");
        if("Giant Tapir".equals(pet)) return new PetResult(0,"Utility: pet food; no direct battle modifier");
        if("Giant Elk".equals(pet)) return new PetResult(0,"Utility: loot; no direct battle modifier");
        return new PetResult(0,"None");
    }

    private double armyScore(double troops,double atk,double def,double hp,double leth){
        atk=Math.max(-95,atk); def=Math.max(-95,def); hp=Math.max(-95,hp); leth=Math.max(-95,leth);
        double offense=(1+atk/100.0)*(1+leth/100.0);
        double survival=(1+def/100.0)*(1+hp/100.0);
        return troops*Math.sqrt(offense*survival);
    }

    private double counterFactor(double inf,double lan,double mar,double oInf,double oLan,double oMar){
        double advantage=(inf*oLan + lan*oMar + mar*oInf)/10000.0;
        double disadvantage=(inf*oMar + lan*oInf + mar*oLan)/10000.0;
        return Math.max(0.80,Math.min(1.20,1.0+0.20*(advantage-disadvantage)));
    }

    private static class Stats {double atk,def,hp,leth; Stats(double a,double d,double h,double l){atk=a;def=d;hp=h;leth=l;}}
    private static class PetResult {double squadCapacity;String note;PetResult(double s,String n){squadCapacity=s;note=n;}}
    private double n(EditText e){return Double.parseDouble(e.getText().toString().trim().replace(',','.'));}
    private TextView title(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.WHITE);v.setTextSize(25);v.setTypeface(Typeface.DEFAULT_BOLD);v.setPadding(0,0,0,dp(8));return v;}
    private TextView section(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.rgb(119,205,255));v.setTextSize(15);v.setTypeface(Typeface.DEFAULT_BOLD);v.setPadding(0,dp(18),0,dp(6));return v;}
    private TextView sub(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.rgb(190,215,230));v.setTextSize(12);v.setPadding(0,dp(4),0,dp(4));return v;}
    private TextView info(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.rgb(210,228,240));v.setTextSize(13);v.setPadding(dp(14),dp(14),dp(14),dp(14));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(18,48,70));g.setCornerRadius(dp(14));g.setStroke(dp(1),Color.rgb(42,86,112));v.setBackground(g);return v;}
    private EditText field(String hint,String value){EditText e=new EditText(this);e.setHint(hint);e.setText(value);e.setTextColor(Color.WHITE);e.setHintTextColor(Color.rgb(145,170,188));e.setTextSize(15);e.setSingleLine(true);e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);e.setPadding(dp(14),dp(12),dp(14),dp(12));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(13,39,58));g.setCornerRadius(dp(10));g.setStroke(dp(1),Color.rgb(39,77,101));e.setBackground(g);e.setLayoutParams(margin(0,0,0,8));return e;}
    private Spinner spinner(String[] values){Spinner s=new Spinner(this);ArrayAdapter<String> a=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,values){@Override public View getView(int p,View c,android.view.ViewGroup parent){TextView v=(TextView)super.getView(p,c,parent);v.setTextColor(Color.WHITE);v.setPadding(dp(12),dp(12),dp(12),dp(12));return v;}@Override public View getDropDownView(int p,View c,android.view.ViewGroup parent){TextView v=(TextView)super.getDropDownView(p,c,parent);v.setTextColor(Color.BLACK);v.setPadding(dp(12),dp(12),dp(12),dp(12));return v;}};a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);s.setAdapter(a);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(13,39,58));g.setCornerRadius(dp(10));g.setStroke(dp(1),Color.rgb(39,77,101));s.setBackground(g);s.setLayoutParams(margin(0,0,0,8));return s;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTextSize(15);b.setTypeface(Typeface.DEFAULT_BOLD);b.setGravity(Gravity.CENTER);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(20,126,196));g.setCornerRadius(dp(12));b.setBackground(g);return b;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);} private LinearLayout.LayoutParams margin(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
