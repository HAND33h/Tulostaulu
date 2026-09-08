package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BattleReportAnalyzerActivity extends Activity {
    private static final int PICK=6201;
    private static final String PREFS="wos_tulostaulu", HISTORY_KEY="battle_history_v1";
    private final TextRecognizer recognizer=TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    private TextView status,result;
    private final StringBuilder merged=new StringBuilder();

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        ScrollView sc=new ScrollView(this);LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(32,40,32,48);root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        root.addView(t("⚔️ BATTLE REPORT ANALYZER 6.1",24,Color.WHITE));
        root.addView(t("Valitse yksi tai useampi saman Whiteout Survival -taisteluraportin kuvakaappaus. OCR tehdään laitteessa. Historia ja formation-yhteenveto perustuvat vain omiin havaintoihisi.",13,Color.rgb(205,226,238)));
        Button pick=new Button(this);pick.setText("VALITSE RAPORTTIKUVAT");pick.setOnClickListener(v->choose());root.addView(pick);
        Button history=new Button(this);history.setText("NÄYTÄ BATTLE HISTORY + FORMATION SUMMARY");history.setOnClickListener(v->showHistory());root.addView(history);
        Button clear=new Button(this);clear.setText("TYHJENNÄ BATTLE HISTORY");clear.setOnClickListener(v->{getSharedPreferences(PREFS,MODE_PRIVATE).edit().remove(HISTORY_KEY).apply();result.setText("Historia tyhjennetty.");});root.addView(clear);
        status=t("Valmis",14,Color.rgb(119,205,255));root.addView(status);
        result=t("",13,Color.WHITE);result.setMovementMethod(new ScrollingMovementMethod());root.addView(result);
        setContentView(sc);
        if(Intent.ACTION_SEND_MULTIPLE.equals(getIntent().getAction())){ArrayList<Uri> u=getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);if(u!=null)process(u);}else if(Intent.ACTION_SEND.equals(getIntent().getAction())){Uri u=getIntent().getParcelableExtra(Intent.EXTRA_STREAM);if(u!=null){ArrayList<Uri> a=new ArrayList<>();a.add(u);process(a);}}
    }

    private void choose(){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("image/*");i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);startActivityForResult(i,PICK);}
    @Override protected void onActivityResult(int req,int res,Intent data){super.onActivityResult(req,res,data);if(req!=PICK||res!=RESULT_OK||data==null)return;ArrayList<Uri> u=new ArrayList<>();ClipData c=data.getClipData();if(c!=null){for(int i=0;i<c.getItemCount();i++)u.add(c.getItemAt(i).getUri());}else if(data.getData()!=null)u.add(data.getData());process(u);}
    private void process(List<Uri> uris){merged.setLength(0);status.setText("Luetaan "+uris.size()+" kuvaa…");next(uris,0);}
    private void next(List<Uri> uris,int i){if(i>=uris.size()){analyze(uris.size());return;}try{InputImage im=InputImage.fromFilePath(this,uris.get(i));recognizer.process(im).addOnSuccessListener(r->{merged.append("\n--- IMAGE ").append(i+1).append(" ---\n").append(r.getText());next(uris,i+1);}).addOnFailureListener(e->next(uris,i+1));}catch(Exception e){next(uris,i+1);}}

    private void analyze(int imageCount){String s=merged.toString();String low=s.toLowerCase(Locale.ROOT);long inf=findCount(low,"infantry"),lan=findCount(low,"lancer"),mark=findCount(low,"marksman");double atk=findPercent(low,"attack"),def=findPercent(low,"defense"),leth=findPercent(low,"lethality"),health=findPercent(low,"health");String outcome=detectOutcome(low);String ratio=ratio(inf,lan,mark);
        StringBuilder out=new StringBuilder();out.append("OCR-kuvia käsitelty: ").append(imageCount).append(" ✓\n");out.append("Outcome OCR: ").append(outcome).append("\n");if(!ratio.isEmpty())out.append("Observed troop ratio: ").append(ratio).append("\n");out.append("\n");
        if(inf>0||lan>0||mark>0)out.append("Havaitut troop counts\nInfantry: ").append(inf).append("\nLancer: ").append(lan).append("\nMarksman: ").append(mark).append("\n\n");if(atk>0||def>0||leth>0||health>0)out.append("Havaitut prosentit\nAttack: ").append(atk).append("%\nDefense: ").append(def).append("%\nLethality: ").append(leth).append("%\nHealth: ").append(health).append("%\n\n");
        out.append("Huomio: OCR ja outcome-tunnistus voivat erehtyä. Formation-yhteenveto on vain omista raporteista laskettu havainto, ei virallinen WOS-taistelukaava.\n\nRAW OCR\n").append(s.length()>6000?s.substring(0,6000)+"\n…":s);String text=out.toString();result.setText(text);status.setText("Valmis ✓");SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);p.edit().putString("last_battle_analysis",text.length()>10000?text.substring(0,10000):text).apply();appendHistory(p,new Record(System.currentTimeMillis(),outcome,inf,lan,mark,atk,def,leth,health,imageCount));}

    private void appendHistory(SharedPreferences p,Record r){String old=p.getString(HISTORY_KEY,"");String entry=r.encode();String combined=entry+(old.isEmpty()?"":"\n"+old);String[] rows=combined.split("\\n");StringBuilder keep=new StringBuilder();for(int i=0;i<Math.min(rows.length,20);i++){if(i>0)keep.append('\n');keep.append(rows[i]);}p.edit().putString(HISTORY_KEY,keep.toString()).apply();}
    private void showHistory(){SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);String h=p.getString(HISTORY_KEY,"");if(h.isEmpty()){result.setText("Ei tallennettuja battle reportteja.");return;}String[] rows=h.split("\\n");StringBuilder out=new StringBuilder("BATTLE HISTORY (max 20)\n\n");double wi=0,wl=0,wm=0;int wins=0,valid=0;for(String row:rows){Record r=Record.decode(row);if(r==null)continue;valid++;out.append(new SimpleDateFormat("dd.MM HH:mm",Locale.getDefault()).format(new Date(r.time))).append(" • ").append(r.outcome).append(" • ").append(ratio(r.inf,r.lan,r.mark)).append(" • imgs ").append(r.images).append("\n");if("VICTORY".equals(r.outcome)){long sum=r.inf+r.lan+r.mark;if(sum>0){wi+=100.0*r.inf/sum;wl+=100.0*r.lan/sum;wm+=100.0*r.mark/sum;wins++;}}}
        out.append("\nOBSERVED FORMATION SUMMARY\n");if(wins>0){out.append("Average of ").append(wins).append(" OCR-detected victories:\nInfantry ").append(round1(wi/wins)).append("% • Lancer ").append(round1(wl/wins)).append("% • Marksman ").append(round1(wm/wins)).append("%\n");}else out.append("Ei vielä riittävästi OCR-tunnistettuja victory-raportteja.\n");out.append("\nTämä ei todista optimaalista formationia; se näyttää vain oman tallennetun datasi keskiarvon.");result.setText(out.toString());status.setText(valid+" raporttia historiassa");}

    private String detectOutcome(String s){if(s.contains("victory")||s.contains("won")||s.contains("win "))return"VICTORY";if(s.contains("defeat")||s.contains("lost")||s.contains("lose "))return"DEFEAT";return"UNKNOWN";}
    private String ratio(long a,long b,long c){long sum=a+b+c;if(sum<=0)return"ratio unavailable";return round1(100.0*a/sum)+"/"+round1(100.0*b/sum)+"/"+round1(100.0*c/sum);}
    private String round1(double x){return String.format(Locale.US,"%.1f",x);}
    private long findCount(String s,String key){Matcher m=Pattern.compile(key+"[^0-9]{0,30}([0-9][0-9, .]{2,})",Pattern.CASE_INSENSITIVE).matcher(s);if(m.find()){try{return Long.parseLong(m.group(1).replaceAll("[^0-9]",""));}catch(Exception ignored){}}return 0;}
    private double findPercent(String s,String key){Matcher m=Pattern.compile(key+"[^0-9]{0,20}([0-9]+(?:[.,][0-9]+)?)\\s*%",Pattern.CASE_INSENSITIVE).matcher(s);if(m.find()){try{return Double.parseDouble(m.group(1).replace(',','.'));}catch(Exception ignored){}}return 0;}
    private TextView t(String s,int z,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);v.setPadding(0,8,0,12);return v;}
    @Override protected void onDestroy(){super.onDestroy();recognizer.close();}

    private static final class Record{long time,inf,lan,mark;double atk,def,leth,health;String outcome;int images;Record(long t,String o,long i,long l,long m,double a,double d,double le,double h,int im){time=t;outcome=o;inf=i;lan=l;mark=m;atk=a;def=d;leth=le;health=h;images=im;}String encode(){return time+"|"+outcome+"|"+inf+"|"+lan+"|"+mark+"|"+atk+"|"+def+"|"+leth+"|"+health+"|"+images;}static Record decode(String s){try{String[] x=s.split("\\|",-1);return new Record(Long.parseLong(x[0]),x[1],Long.parseLong(x[2]),Long.parseLong(x[3]),Long.parseLong(x[4]),Double.parseDouble(x[5]),Double.parseDouble(x[6]),Double.parseDouble(x[7]),Double.parseDouble(x[8]),Integer.parseInt(x[9]));}catch(Exception e){return null;}}}
}
