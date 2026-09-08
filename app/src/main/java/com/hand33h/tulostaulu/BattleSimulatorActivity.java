package com.hand33h.tulostaulu;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
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
    private EditText aTroops,aInf,aLan,aMar,aAtk,aDef,aHp,aLeth,aHeroAtk,aHeroDef,aHeroHp,aHeroLeth;
    private EditText dTroops,dInf,dLan,dMar,dAtk,dDef,dHp,dLeth,dHeroAtk,dHeroDef,dHeroHp,dHeroLeth;
    private Spinner aPet,aPetLevel,aExpert,aAffinity,aExpertSkills,dPet,dPetLevel,dExpert,dAffinity,dExpertSkills;
    private Spinner[] aGear=new Spinner[6],dGear=new Spinner[6],aCharm=new Spinner[18],dCharm=new Spinner[18];
    private Spinner[] aHeroes=new Spinner[3],dHeroes=new Spinner[3],aHeroSkill=new Spinner[3],dHeroSkill=new Spinner[3],aExclusive=new Spinner[3],dExclusive=new Spinner[3];
    private Spinner[][] aHgQuality=new Spinner[3][4],dHgQuality=new Spinner[3][4];
    private Spinner[][] aHgEnh=new Spinner[3][4],dHgEnh=new Spinner[3][4];
    private Spinner[][] aHgMastery=new Spinner[3][4],dHgMastery=new Spinner[3][4];
    private Spinner[][] aHgAsc=new Spinner[3][4],dHgAsc=new Spinner[3][4];
    private TextView result;

    private static final String[] PETS={"None","Arctic Wolf","Cave Hyena","Musk Ox","Giant Tapir","Titan Roc","Giant Elk","Snow Leopard","Cave Lion","Snow Ape","Iron Rhino","Saber-tooth Tiger","Mammoth","Frost Gorilla","Frostscale Chameleon"};
    private static final String[] PET_LEVELS={"10","20","30","40","50","60","70","80","90","100"};
    private static final String[] EXPERTS={"None","Agnes","Cyrille","Holger","Romulus","Baldur","Fabian","Valeria","Ronne","Kathy","Gareth"};
    private static final String[] AFFINITY={"0","10","20","30","40","50","60","70","80","90","100"};
    private static final String[] EXPERT_SKILLS={"Base affinity only","Max verified combat skills"};
    private static final String[] GEAR_NAMES={"None","Green","Green ★","Blue","Blue ★","Blue ★★","Blue ★★★","Purple","Purple ★","Purple ★★","Purple ★★★","Purple T1","Purple T1 ★","Purple T1 ★★","Purple T1 ★★★","Gold","Gold ★","Gold ★★","Gold ★★★","Gold T1","Gold T1 ★","Gold T1 ★★","Gold T1 ★★★","Gold T2","Gold T2 ★","Gold T2 ★★","Gold T2 ★★★","Red","Red ★","Red ★★","Red ★★★","Red T1","Red T1 ★","Red T1 ★★","Red T1 ★★★","Red T2","Red T2 ★","Red T2 ★★","Red T2 ★★★","Red T3","Red T3 ★","Red T3 ★★","Red T3 ★★★","Red T4","Red T4 ★","Red T4 ★★","Red T4 ★★★","Red T5","Red T5 ★","Red T5 ★★","Red T5 ★★★","Red T6","Red T6 ★","Red T6 ★★","Red T6 ★★★"};
    private static final double[] GEAR_BONUS={0,9.35,12.75,17,21.25,25.50,29.75,34,36.89,39.78,42.67,45.56,48.45,51.34,54.23,56.78,59.33,61.88,64.43,66.98,69.53,72.08,74.63,77.18,79.73,82.28,85.00,89.25,93.50,97.75,102,106.25,110.50,114.75,119,123.25,127.50,131.75,136,140.25,144.50,148.75,153,161.50,170,178.50,187,195.50,204,212.50,221,229.50,238,246.50,255};
    private static final int[] GEAR_CAP={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,40,80,120,160,290,330,370,410,540,580,620,660,790,830,870,910,1050,1100,1150,1200,1340,1390,1440,1490,1630,1680,1730,1780};
    private static final String[] CHARM_LEVELS={"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"};
    private static final double[] CHARM_BONUS={0,9,12,16,19,25,30,35,40,45,50,55,64,73,82,91,100,109,118};
    private static final String[] GEAR_SLOTS={"Helmet (Lancer)","Watch (Lancer)","Jacket (Infantry)","Pants (Infantry)","Ring (Marksman)","Cane (Marksman)"};

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        ScrollView sc=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(16),dp(18),dp(16),dp(40));root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        root.addView(title("⚔️  BATTLE SIMULATOR"));
        root.addView(info("Mukana Chief Gear, 18 Charms, Pets, Experts, Hero Expedition/Exclusive Weapon data sekä nyt oikea Hero Gear -rakenne: quality, Enhancement 0–100, Mastery 0–20 ja Legendary Empowerment +0/+20/+40/+60/+80/+100 jokaiselle 4 gear-slotille. Mastery checkpointit 1–7 näytetään varmennetuilla prosenteilla. Per-piece Command-arvoa ei arvata ilman tarkkaa item-taulukkoa; Additional verified Hero/Hero Gear -kentät ovat edelleen käytössä."));
        buildSide(true);buildSide(false);
        Button sim=button("RUN SIMULATION");sim.setOnClickListener(v->simulate());root.addView(sim,margin(0,16,0,12));
        result=info("Tulokset näkyvät tässä.");root.addView(result);setContentView(sc);
    }

    private void buildSide(boolean attacker){
        root.addView(section(attacker?"ATTACKER":"DEFENDER"));
        EditText troops=field("Total troops","100000"),inf=field("Infantry %","40"),lan=field("Lancer %","30"),mar=field("Marksman %","30");
        EditText atk=field("Troops' Attack %",attacker?"444.64":"400"),def=field("Troops' Defense %",attacker?"495.23":"450"),hp=field("Troops' Health %",attacker?"118.05":"110"),leth=field("Troops' Lethality %",attacker?"115.80":"110");
        root.addView(troops);root.addView(inf);root.addView(lan);root.addView(mar);root.addView(atk);root.addView(def);root.addView(hp);root.addView(leth);
        if(attacker){aTroops=troops;aInf=inf;aLan=lan;aMar=mar;aAtk=atk;aDef=def;aHp=hp;aLeth=leth;}else{dTroops=troops;dInf=inf;dLan=lan;dMar=mar;dAtk=atk;dDef=def;dHp=hp;dLeth=leth;}

        root.addView(sub("🦸 Heroes — 3 Expedition slots"));
        Spinner[] hs=attacker?aHeroes:dHeroes,sk=attacker?aHeroSkill:dHeroSkill,ew=attacker?aExclusive:dExclusive;
        Spinner[][] q=attacker?aHgQuality:dHgQuality,en=attacker?aHgEnh:dHgEnh,ma=attacker?aHgMastery:dHgMastery,asc=attacker?aHgAsc:dHgAsc;
        for(int i=0;i<3;i++){
            root.addView(tiny("Hero "+(i+1)));hs[i]=spinner(HeroBattleData.HEROES);root.addView(hs[i]);
            root.addView(tiny("Expedition skills level (1–5)"));sk[i]=spinner(HeroBattleData.SKILL_LEVELS);sk[i].setSelection(4);root.addView(sk[i]);
            root.addView(tiny("Exclusive / Special Weapon level"));ew[i]=spinner(HeroBattleData.EXCLUSIVE_LEVELS);root.addView(ew[i]);
            for(int g=0;g<4;g++){
                root.addView(tiny("⚙ "+HeroBattleData.HERO_GEAR_SLOTS[g]));
                root.addView(tiny("Quality"));q[i][g]=spinner(HeroGearData.QUALITY);root.addView(q[i][g]);
                root.addView(tiny("Enhancement level"));en[i][g]=spinner(HeroGearData.ENHANCEMENT);root.addView(en[i][g]);
                root.addView(tiny("Mastery Forging level"));ma[i][g]=spinner(HeroGearData.MASTERY);root.addView(ma[i][g]);
                root.addView(tiny("Legendary Empowerment"));asc[i][g]=spinner(HeroGearData.ASCENSION);root.addView(asc[i][g]);
            }
        }
        root.addView(sub("Additional verified Hero / Hero Gear bonuses (%)"));
        EditText hatk=field("Additional Hero Attack bonus %","0"),hdef=field("Additional Hero Defense bonus %","0"),hhp=field("Additional Hero Health bonus %","0"),hleth=field("Additional Hero Lethality bonus %","0");
        root.addView(hatk);root.addView(hdef);root.addView(hhp);root.addView(hleth);
        if(attacker){aHeroAtk=hatk;aHeroDef=hdef;aHeroHp=hhp;aHeroLeth=hleth;}else{dHeroAtk=hatk;dHeroDef=hdef;dHeroHp=hhp;dHeroLeth=hleth;}

        root.addView(sub("🛡️ Chief Gear — 6 pieces"));Spinner[] gs=attacker?aGear:dGear;for(int i=0;i<6;i++){root.addView(tiny(GEAR_SLOTS[i]));gs[i]=spinner(GEAR_NAMES);root.addView(gs[i]);}
        root.addView(sub("💎 Chief Charms — all 18 slots"));Spinner[] cs=attacker?aCharm:dCharm;for(int piece=0;piece<6;piece++){root.addView(tiny(GEAR_SLOTS[piece]+" charms"));for(int j=0;j<3;j++){int idx=piece*3+j;cs[idx]=spinner(CHARM_LEVELS);root.addView(cs[idx]);}}
        root.addView(sub("🐾 Pet"));Spinner pet=spinner(PETS);root.addView(pet);root.addView(tiny("Pet advancement"));Spinner pl=spinner(PET_LEVELS);pl.setSelection(9);root.addView(pl);
        root.addView(sub("🎓 Dawn Academy Expert"));Spinner ex=spinner(EXPERTS);root.addView(ex);root.addView(tiny("Expert affinity"));Spinner af=spinner(AFFINITY);af.setSelection(10);root.addView(af);root.addView(tiny("Expert combat skills"));Spinner es=spinner(EXPERT_SKILLS);root.addView(es);
        if(attacker){aPet=pet;aPetLevel=pl;aExpert=ex;aAffinity=af;aExpertSkills=es;}else{dPet=pet;dPetLevel=pl;dExpert=ex;dAffinity=af;dExpertSkills=es;}
    }

    private void simulate(){
        try{
            double ai=n(aInf),al=n(aLan),am=n(aMar),di=n(dInf),dl=n(dLan),dm=n(dMar);
            if(Math.abs(ai+al+am-100)>0.2||Math.abs(di+dl+dm-100)>0.2){result.setText("⚠️ Infantry + Lancer + Marksman pitää olla 100 %.");return;}
            String gearErr=validateHeroGear(aHgQuality,aHgEnh,aHgMastery,aHgAsc,"Attacker");if(gearErr==null)gearErr=validateHeroGear(dHgQuality,dHgEnh,dHgMastery,dHgAsc,"Defender");if(gearErr!=null){result.setText("⚠️ "+gearErr);return;}
            Side A=new Side(n(aTroops),new Stats(n(aAtk),n(aDef),n(aHp),n(aLeth)),ai,al,am),D=new Side(n(dTroops),new Stats(n(dAtk),n(dDef),n(dHp),n(dLeth)),di,dl,dm);
            String aHeroNotes=applyHeroes(A,D,aHeroes,aHeroSkill,aExclusive,true)+"\n"+heroGearSummary(aHgQuality,aHgEnh,aHgMastery,aHgAsc);
            String dHeroNotes=applyHeroes(D,A,dHeroes,dHeroSkill,dExclusive,false)+"\n"+heroGearSummary(dHgQuality,dHgEnh,dHgMastery,dHgAsc);
            applyVerifiedHero(A,aHeroAtk,aHeroDef,aHeroHp,aHeroLeth);applyVerifiedHero(D,dHeroAtk,dHeroDef,dHeroHp,dHeroLeth);
            applyGearAndCharms(A,aGear,aCharm);applyGearAndCharms(D,dGear,dCharm);
            applyExpert(A,D,(String)aExpert.getSelectedItem(),Integer.parseInt((String)aAffinity.getSelectedItem()),aExpertSkills.getSelectedItemPosition()==1);
            applyExpert(D,A,(String)dExpert.getSelectedItem(),Integer.parseInt((String)dAffinity.getSelectedItem()),dExpertSkills.getSelectedItemPosition()==1);
            PetResult apr=applyPet(A.s,D.s,(String)aPet.getSelectedItem(),Integer.parseInt((String)aPetLevel.getSelectedItem()));
            PetResult dpr=applyPet(D.s,A.s,(String)dPet.getSelectedItem(),Integer.parseInt((String)dPetLevel.getSelectedItem()));A.count+=apr.squadCapacity;D.count+=dpr.squadCapacity;
            double as=armyScore(A)*counterFactor(ai,al,am,di,dl,dm),ds=armyScore(D)*counterFactor(di,dl,dm,ai,al,am),p=as/(as+ds);
            double aLoss=Math.min(100,Math.max(5,72*(ds/(as+ds)))),dLoss=Math.min(100,Math.max(5,72*(as/(as+ds))));long ar=Math.max(0,Math.round(A.count*(1-aLoss/100))),dr=Math.max(0,Math.round(D.count*(1-dLoss/100)));
            result.setText(String.format(Locale.US,"🏆 Predicted winner: %s\n\nWin estimate\nAttacker %.1f %% | Defender %.1f %%\n\nEffective weighted stats\nA: ATK %.2f DEF %.2f HP %.2f LETH %.2f DMG +%.2f%%\nD: ATK %.2f DEF %.2f HP %.2f LETH %.2f DMG +%.2f%%\n\nEffective squad size\nA: %,.0f | D: %,.0f\n\nCombat score\nA: %,.0f | D: %,.0f\n\nEstimated losses\nA: %.1f %% (~%,d left)\nD: %.1f %% (~%,d left)\n\nPet\nA: %s\nD: %s\n\nAUTO HERO DATA — ATTACKER\n%s\n\nAUTO HERO DATA — DEFENDER\n%s\n\nHero Gear progression is validated and displayed. Exact per-piece Command percentages are not invented; enter verified remaining Hero Gear stats in the Additional fields.",p>=.5?"ATTACKER":"DEFENDER",p*100,(1-p)*100,A.s.atk,A.s.def,A.s.hp,A.s.leth,A.s.damageDealt,D.s.atk,D.s.def,D.s.hp,D.s.leth,D.s.damageDealt,A.count,D.count,as,ds,aLoss,ar,dLoss,dr,apr.note,dpr.note,aHeroNotes,dHeroNotes));
        }catch(Exception e){result.setText("⚠️ Tarkista syötetyt arvot. "+e.getClass().getSimpleName());}
    }

    private String validateHeroGear(Spinner[][] q,Spinner[][] en,Spinner[][] ma,Spinner[][] asc,String side){
        for(int h=0;h<3;h++)for(int g=0;g<4;g++){
            String quality=(String)q[h][g].getSelectedItem();int e=Integer.parseInt((String)en[h][g].getSelectedItem()),m=Integer.parseInt((String)ma[h][g].getSelectedItem()),a=Integer.parseInt((String)asc[h][g].getSelectedItem());
            String err=HeroGearData.validate(quality,e,m,a);if(err!=null)return side+" Hero "+(h+1)+" "+HeroBattleData.HERO_GEAR_SLOTS[g]+": "+err;
        }return null;
    }
    private String heroGearSummary(Spinner[][] q,Spinner[][] en,Spinner[][] ma,Spinner[][] asc){
        StringBuilder s=new StringBuilder("Hero Gear:\n");boolean any=false;
        for(int h=0;h<3;h++)for(int g=0;g<4;g++){
            String quality=(String)q[h][g].getSelectedItem();if("None".equals(quality))continue;any=true;int e=Integer.parseInt((String)en[h][g].getSelectedItem()),m=Integer.parseInt((String)ma[h][g].getSelectedItem()),a=Integer.parseInt((String)asc[h][g].getSelectedItem());
            s.append("  H").append(h+1).append(' ').append(HeroBattleData.HERO_GEAR_SLOTS[g]).append(": ").append(HeroGearData.summary(quality,e,m,a)).append('\n');
        }return any?s.toString().trim():"Hero Gear: none selected";
    }

    private String applyHeroes(Side own,Side enemy,Spinner[] heroes,Spinner[] skills,Spinner[] exclusive,boolean attackerSide){
        StringBuilder notes=new StringBuilder();
        for(int i=0;i<3;i++){
            String selected=(String)heroes[i].getSelectedItem();if(selected==null||"None".equals(HeroBattleData.nameOf(selected)))continue;
            int lv=Integer.parseInt((String)skills[i].getSelectedItem()),ewLv=Integer.parseInt((String)exclusive[i].getSelectedItem());
            HeroBattleData.Effect e=HeroBattleData.directEffect(selected,lv,ewLv,!attackerSide);
            own.s.atk+=e.atk;own.s.def+=e.def;own.s.hp+=e.hp;own.s.leth+=e.leth;own.s.damageDealt+=e.damageDealt;
            enemy.s.atk-=e.enemyAtk;enemy.s.def-=e.enemyDef;enemy.s.hp-=e.enemyHp;enemy.s.leth-=e.enemyLeth;
            own.s.atk+=weightedTroop(own,e.infAtk,e.lanAtk,e.marAtk);own.s.def+=weightedTroop(own,e.infDef,e.lanDef,e.marDef);own.s.hp+=weightedTroop(own,e.infHp,e.lanHp,e.marHp);own.s.leth+=weightedTroop(own,e.infLeth,e.lanLeth,e.marLeth);
            double ew=HeroBattleData.exclusiveExpeditionLethHp(selected,ewLv);String type=HeroBattleData.troopTypeOf(selected);double share="Infantry".equals(type)?own.inf:("Lancer".equals(type)?own.lan:("Marksman".equals(type)?own.mar:0));double weightedEw=ew*share/100.0;own.s.leth+=weightedEw;own.s.hp+=weightedEw;
            notes.append("• ").append(HeroBattleData.nameOf(selected)).append(" skill Lv").append(lv).append(": ");if(e.hasVerifiedSkillData)notes.append(e.note);else notes.append("skill semantics not yet auto-scored; use Additional verified Hero fields for direct bonuses");notes.append(" | ").append(HeroBattleData.exclusiveSummary(selected,ewLv)).append('\n');
        }return notes.length()==0?"No heroes selected":notes.toString().trim();
    }

    private double weightedTroop(Side s,double inf,double lan,double mar){return(s.inf*inf+s.lan*lan+s.mar*mar)/100.0;}
    private void applyVerifiedHero(Side s,EditText a,EditText d,EditText h,EditText l){s.s.atk+=n(a);s.s.def+=n(d);s.s.hp+=n(h);s.s.leth+=n(l);}
    private void applyGearAndCharms(Side x,Spinner[] gear,Spinner[] charm){double infGear=(GEAR_BONUS[gear[2].getSelectedItemPosition()]+GEAR_BONUS[gear[3].getSelectedItemPosition()])/2.0,lanGear=(GEAR_BONUS[gear[0].getSelectedItemPosition()]+GEAR_BONUS[gear[1].getSelectedItemPosition()])/2.0,marGear=(GEAR_BONUS[gear[4].getSelectedItemPosition()]+GEAR_BONUS[gear[5].getSelectedItemPosition()])/2.0,weighted=(x.inf*infGear+x.lan*lanGear+x.mar*marGear)/100.0;x.s.atk+=weighted;x.s.def+=weighted;int cap=0;for(Spinner g:gear)cap+=GEAR_CAP[g.getSelectedItemPosition()];x.count+=cap;double infC=0,lanC=0,marC=0;for(int i=0;i<18;i++){double b=CHARM_BONUS[charm[i].getSelectedItemPosition()];if(i<6)lanC+=b;else if(i<12)infC+=b;else marC+=b;}infC/=6.0;lanC/=6.0;marC/=6.0;double c=(x.inf*infC+x.lan*lanC+x.mar*marC)/100.0;x.s.hp+=c;x.s.leth+=c;}
    private void applyExpert(Side own,Side enemy,String name,int affinity,boolean skills){double k=affinity/100.0;if("Agnes".equals(name))own.s.def+=15*k;else if("Cyrille".equals(name))own.s.atk+=15*k;else if("Holger".equals(name)){own.s.atk+=15*k;own.s.def+=15*k;}else if("Romulus".equals(name)){own.s.leth+=20*k;own.s.hp+=20*k;if(skills){own.s.atk+=10;own.s.def+=10;own.s.leth+=10;own.s.hp+=10;own.count+=10000;}}else if("Baldur".equals(name)){own.s.atk+=10*k;own.s.def+=10*k;}else if("Fabian".equals(name)){own.s.leth+=15*k;own.s.hp+=15*k;}else if("Valeria".equals(name)){own.s.leth+=20*k;own.s.hp+=20*k;if(skills){own.s.atk+=30;own.s.def+=30;own.s.leth+=30;own.s.hp+=30;}}else if("Ronne".equals(name)){own.s.atk+=15*k;own.s.def+=15*k;if(skills){own.s.atk+=30;own.s.def+=30;}}else if("Kathy".equals(name)){own.s.leth+=15*k;own.s.hp+=15*k;}else if("Gareth".equals(name)){own.s.atk+=30*k;own.s.hp+=30*k;if(skills){own.s.def+=50;own.s.hp+=50;enemy.s.leth-=5;}}}
    private PetResult applyPet(Stats own,Stats enemy,String pet,int level){int i=Math.max(0,Math.min(9,level/10-1));double[] p={2.5,3,3.5,4,5,6,7,8,9,10};if("Cave Lion".equals(pet)){own.atk+=p[i];return new PetResult(0,"ATK +"+p[i]+"%");}if("Saber-tooth Tiger".equals(pet)){own.leth+=p[i];return new PetResult(0,"Lethality +"+p[i]+"%");}if("Mammoth".equals(pet)){own.def+=p[i];return new PetResult(0,"Defense +"+p[i]+"% (range pattern; verify client stage)");}if("Frost Gorilla".equals(pet)){own.hp+=p[i];return new PetResult(0,"Health +"+p[i]+"%");}if("Frostscale Chameleon".equals(pet)){enemy.def-=p[i];return new PetResult(0,"Enemy Defense -"+p[i]+"% (range pattern; verify client stage)");}if("Snow Leopard".equals(pet)){double red=(i>=9?5:new double[]{1.5,2,2.5,3,3.5,4,4,4,4,5}[i]);enemy.leth-=red;return new PetResult(0,"Enemy Lethality -"+red+"%; intermediate late stages not claimed exact");}if("Titan Roc".equals(pet)){double[] known={1.5,2,2.5,3,3.5,4,5};int k=Math.min(6,(int)Math.floor(i*6.0/9.0));double red=known[k];enemy.hp-=red;return new PetResult(0,"Enemy Health -"+red+"%; mapped to 7 published skill stages");}if("Snow Ape".equals(pet)){double c=1500.0*(i+1);return new PetResult(c,"Squad Capacity +"+(int)c);}if("Iron Rhino".equals(pet))return new PetResult(0,"Rally Capacity bonus; not added to single-march count");return new PetResult(0,"No direct combat stat modifier");}
    private double armyScore(Side x){double a=Math.max(-95,x.s.atk),d=Math.max(-95,x.s.def),h=Math.max(-95,x.s.hp),l=Math.max(-95,x.s.leth),dmg=Math.max(-95,x.s.damageDealt);return x.count*Math.sqrt((1+a/100)*(1+l/100)*(1+d/100)*(1+h/100))*(1+dmg/100.0);}
    private double counterFactor(double inf,double lan,double mar,double oi,double ol,double om){double adv=(inf*ol+lan*om+mar*oi)/10000.0,dis=(inf*om+lan*oi+mar*ol)/10000.0;return Math.max(.80,Math.min(1.20,1+.20*(adv-dis)));}
    private static class Stats{double atk,def,hp,leth,damageDealt;Stats(double a,double d,double h,double l){atk=a;def=d;hp=h;leth=l;}}
    private static class Side{double count,inf,lan,mar;Stats s;Side(double c,Stats st,double i,double l,double m){count=c;s=st;inf=i;lan=l;mar=m;}}
    private static class PetResult{double squadCapacity;String note;PetResult(double c,String n){squadCapacity=c;note=n;}}
    private double n(EditText e){return Double.parseDouble(e.getText().toString().trim().replace(',','.'));}
    private Spinner spinner(String[] v){Spinner s=new Spinner(this);ArrayAdapter<String>a=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,v);a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);s.setAdapter(a);s.setBackgroundColor(Color.rgb(25,57,78));return s;}
    private TextView title(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.WHITE);v.setTextSize(25);v.setTypeface(Typeface.DEFAULT_BOLD);v.setPadding(0,0,0,dp(8));return v;}
    private TextView section(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.rgb(119,205,255));v.setTextSize(16);v.setTypeface(Typeface.DEFAULT_BOLD);v.setPadding(0,dp(20),0,dp(8));return v;}
    private TextView sub(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.WHITE);v.setTextSize(14);v.setTypeface(Typeface.DEFAULT_BOLD);v.setPadding(0,dp(12),0,dp(5));return v;}
    private TextView tiny(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.rgb(190,215,230));v.setTextSize(12);v.setPadding(0,dp(5),0,dp(3));return v;}
    private TextView info(String s){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.rgb(210,228,240));v.setTextSize(13);v.setPadding(dp(14),dp(14),dp(14),dp(14));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(18,48,70));g.setCornerRadius(dp(14));g.setStroke(dp(1),Color.rgb(42,86,112));v.setBackground(g);return v;}
    private EditText field(String hint,String val){EditText e=new EditText(this);e.setHint(hint);e.setText(val);e.setTextColor(Color.WHITE);e.setHintTextColor(Color.rgb(145,170,188));e.setSingleLine(true);e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);e.setPadding(dp(14),dp(11),dp(14),dp(11));GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(13,39,58));g.setCornerRadius(dp(10));g.setStroke(dp(1),Color.rgb(39,77,101));e.setBackground(g);e.setLayoutParams(margin(0,0,0,7));return e;}
    private Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(Typeface.DEFAULT_BOLD);b.setGravity(Gravity.CENTER);GradientDrawable g=new GradientDrawable();g.setColor(Color.rgb(20,126,196));g.setCornerRadius(dp(12));b.setBackground(g);return b;}
    private int dp(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}private LinearLayout.LayoutParams margin(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(dp(l),dp(t),dp(r),dp(b));return p;}
}
