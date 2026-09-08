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
    private static final String PREFS="wos_tulostaulu", HISTORY_KEY="battle_history_v2";
    private final TextRecognizer recognizer=TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    private TextView status,result;
    private final StringBuilder merged=new StringBuilder();

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        ScrollView sc=new ScrollView(this); LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(32,40,32,48); root.setBackgroundColor(Color.rgb(7,23,39)); sc.addView(root);
        root.addView(t("⚔️ BATTLE REPORT ANALYZER 7.0",24,Color.WHITE));
        root.addView(t("Multi-image OCR • casualty validation • formation history • CSV/Excel export. Data stays on device unless you explicitly share the export.",13,Color.rgb(205,226,238)));
        Button pick=new Button(this); pick.setText("VALITSE RAPORTTIKUVAT"); pick.setOnClickListener(v->choose()); root.addView(pick);
        Button history=new Button(this); history.setText("BATTLE HISTORY + FORMATION SUMMARY"); history.setOnClickListener(v->showHistory()); root.addView(history);
        Button export=new Button(this); export.setText("EXPORT BATTLE DATA (CSV / EXCEL)"); export.setOnClickListener(v->exportCsv()); root.addView(export);
        Button clear=new Button(this); clear.setText("TYHJENNÄ BATTLE HISTORY"); clear.setOnClickListener(v->{getSharedPreferences(PREFS,MODE_PRIVATE).edit().remove(HISTORY_KEY).apply();result.setText("Historia tyhjennetty.");}); root.addView(clear);
        status=t("Valmis",14,Color.rgb(119,205,255)); root.addView(status);
        result=t("",13,Color.WHITE); result.setMovementMethod(new ScrollingMovementMethod()); root.addView(result); setContentView(sc);
        if(Intent.ACTION_SEND_MULTIPLE.equals(getIntent().getAction())){ArrayList<Uri> u=getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);if(u!=null)process(u);}else if(Intent.ACTION_SEND.equals(getIntent().getAction())){Uri u=getIntent().getParcelableExtra(Intent.EXTRA_STREAM);if(u!=null){ArrayList<Uri>a=new ArrayList<>();a.add(u);process(a);}}
    }

    private void choose(){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("image/*");i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);startActivityForResult(i,PICK);}
    @Override protected void onActivityResult(int req,int res,Intent data){super.onActivityResult(req,res,data);if(req!=PICK||res!=RESULT_OK||data==null)return;ArrayList<Uri>u=new ArrayList<>();ClipData c=data.getClipData();if(c!=null){for(int i=0;i<c.getItemCount();i++)u.add(c.getItemAt(i).getUri());}else if(data.getData()!=null)u.add(data.getData());process(u);}
    private void process(List<Uri>uris){merged.setLength(0);status.setText("Luetaan "+uris.size()+" kuvaa…");next(uris,0);}
    private void next(List<Uri>uris,int i){if(i>=uris.size()){analyze(uris.size());return;}try{InputImage im=InputImage.fromFilePath(this,uris.get(i));recognizer.process(im).addOnSuccessListener(r->{merged.append("\n--- IMAGE ").append(i+1).append(" ---\n").append(r.getText());next(uris,i+1);}).addOnFailureListener(e->next(uris,i+1));}catch(Exception e){next(uris,i+1);}}

    private void analyze(int imageCount){String raw=merged.toString(),low=raw.toLowerCase(Locale.ROOT);long inf=findCount(low,"infantry"),lan=findCount(low,"lancer"),mark=findCount(low,"marksman");long troops=findCountAny(low,"troops","troop"),losses=findCountAny(low,"losses","loss"),injured=findCountAny(low,"injured","wounded"),light=findCountAny(low,"lightly injured","lightly wounded"),survivors=findCountAny(low,"survivors","survived");double atk=findPercent(low,"attack"),def=findPercent(low,"defense"),leth=findPercent(low,"lethality"),health=findPercentAny(low,"health","hp");String outcome=detectOutcome(low),role=detectRole(low),ratio=ratio(inf,lan,mark);long casualtySum=losses+injured+light+survivors;double confidence=confidence(troops,casualtySum,inf,lan,mark,outcome,imageCount);
        Record r=new Record(System.currentTimeMillis(),outcome,role,inf,lan,mark,troops,losses,injured,light,survivors,atk,def,leth,health,imageCount,confidence);
        StringBuilder out=new StringBuilder();out.append("OCR-kuvia: ").append(imageCount).append(" ✓\nOutcome: ").append(outcome).append(" • Role: ").append(role).append("\nConfidence: ").append(round1(confidence)).append("%\nFormation: ").append(ratio).append("\n\n");
        out.append("BATTLE OVERVIEW\nTroops: ").append(troops).append("\nLosses: ").append(losses).append("\nInjured: ").append(injured).append("\nLightly injured: ").append(light).append("\nSurvivors: ").append(survivors).append("\n");
        if(troops>0&&casualtySum>0)out.append("Casualty check: ").append(casualtySum).append(" / ").append(troops).append(casualtySum==troops?" ✓":" ⚠ OCR REVIEW").append("\n");
        out.append("\nTROOP TYPES\nInfantry: ").append(inf).append("\nLancer: ").append(lan).append("\nMarksman: ").append(mark).append("\n\nSTATS\nAttack: ").append(atk).append("%\nDefense: ").append(def).append("%\nLethality: ").append(leth).append("%\nHealth: ").append(health).append("%\n\n");
        out.append("Analyzer stores observed report data only. It does not claim Century Games' private damage formula.\n\nRAW OCR\n").append(raw.length()>6000?raw.substring(0,6000)+"\n…":raw);
        result.setText(out.toString());status.setText(confidence>=70?"Valmis ✓":"Valmis — tarkista OCR ⚠");SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);appendHistory(p,r);p.edit().putString("last_battle_analysis",out.toString().substring(0,Math.min(out.length(),10000))).apply();
    }

    private double confidence(long troops,long casualty,long i,long l,long m,String outcome,int images){double c=20;if(images>1)c+=10;if(!"UNKNOWN".equals(outcome))c+=15;if(i+l+m>0)c+=20;if(troops>0)c+=15;if(troops>0&&casualty>0){double err=Math.abs(troops-casualty)/(double)troops;c+=Math.max(0,20-err*100);}return Math.min(100,c);}
    private void appendHistory(SharedPreferences p,Record r){String old=p.getString(HISTORY_KEY,"");String combined=r.encode()+(old.isEmpty()?"":"\n"+old);String[]rows=combined.split("\\n");StringBuilder keep=new StringBuilder();for(int i=0;i<Math.min(rows.length,100);i++){if(i>0)keep.append('\n');keep.append(rows[i]);}p.edit().putString(HISTORY_KEY,keep.toString()).apply();}
    private void showHistory(){String h=getSharedPreferences(PREFS,MODE_PRIVATE).getString(HISTORY_KEY,"");if(h.isEmpty()){result.setText("Ei tallennettuja battle reportteja.");return;}StringBuilder out=new StringBuilder("BATTLE HISTORY (max 100)\n\n");int wins=0,valid=0;double wi=0,wl=0,wm=0;for(String row:h.split("\\n")){Record r=Record.decode(row);if(r==null)continue;valid++;out.append(new SimpleDateFormat("dd.MM HH:mm",Locale.getDefault()).format(new Date(r.time))).append(" • ").append(r.outcome).append(" • ").append(r.role).append(" • ").append(ratio(r.inf,r.lan,r.mark)).append(" • conf ").append(round1(r.confidence)).append("%\n");if("VICTORY".equals(r.outcome)&&r.inf+r.lan+r.mark>0){long s=r.inf+r.lan+r.mark;wi+=100.0*r.inf/s;wl+=100.0*r.lan/s;wm+=100.0*r.mark/s;wins++;}}
        if(wins>0)out.append("\nOBSERVED VICTORY AVERAGE\n").append(round1(wi/wins)).append(" / ").append(round1(wl/wins)).append(" / ").append(round1(wm/wins)).append(" (I/L/M)\n");out.append("\nObserved data, not an official optimal-formation claim.");result.setText(out.toString());status.setText(valid+" raporttia historiassa");}

    private void exportCsv(){String h=getSharedPreferences(PREFS,MODE_PRIVATE).getString(HISTORY_KEY,"");if(h.isEmpty()){result.setText("Ei exportoitavaa dataa.");return;}StringBuilder csv=new StringBuilder("timestamp,outcome,role,infantry,lancer,marksman,ratio,troops,losses,injured,lightly_injured,survivors,attack,defense,lethality,health,images,confidence\n");for(String row:h.split("\\n")){Record r=Record.decode(row);if(r==null)continue;csv.append(r.time).append(',').append(r.outcome).append(',').append(r.role).append(',').append(r.inf).append(',').append(r.lan).append(',').append(r.mark).append(',').append('"').append(ratio(r.inf,r.lan,r.mark)).append('"').append(',').append(r.troops).append(',').append(r.losses).append(',').append(r.injured).append(',').append(r.light).append(',').append(r.survivors).append(',').append(r.atk).append(',').append(r.def).append(',').append(r.leth).append(',').append(r.health).append(',').append(r.images).append(',').append(r.confidence).append('\n');}
        Intent send=new Intent(Intent.ACTION_SEND);send.setType("text/csv");send.putExtra(Intent.EXTRA_SUBJECT,"WOS Battle Data Export");send.putExtra(Intent.EXTRA_TEXT,"\uFEFF"+csv);startActivity(Intent.createChooser(send,"Vie battle data"));}

    private String detectOutcome(String s){if(s.contains("victory")||s.contains("won")||s.contains("win "))return"VICTORY";if(s.contains("defeat")||s.contains("lost")||s.contains("lose "))return"DEFEAT";return"UNKNOWN";}
    private String detectRole(String s){if(s.contains("attacker")||s.contains("rally leader"))return"ATTACK";if(s.contains("defender")||s.contains("garrison"))return"DEFENSE";return"UNKNOWN";}
    private String ratio(long a,long b,long c){long sum=a+b+c;if(sum<=0)return"unavailable";return round1(100.0*a/sum)+"/"+round1(100.0*b/sum)+"/"+round1(100.0*c/sum);}
    private String round1(double x){return String.format(Locale.US,"%.1f",x);}
    private long findCountAny(String s,String...keys){for(String k:keys){long v=findCount(s,k);if(v>0)return v;}return 0;}
    private long findCount(String s,String key){Matcher m=Pattern.compile(Pattern.quote(key)+"[^0-9]{0,30}([0-9][0-9, .]{1,})",Pattern.CASE_INSENSITIVE).matcher(s);if(m.find()){try{return Long.parseLong(m.group(1).replaceAll("[^0-9]",""));}catch(Exception ignored){}}return 0;}
    private double findPercentAny(String s,String...keys){for(String k:keys){double v=findPercent(s,k);if(v>0)return v;}return 0;}
    private double findPercent(String s,String key){Matcher m=Pattern.compile(Pattern.quote(key)+"[^0-9]{0,20}([0-9]+(?:[.,][0-9]+)?)\\s*%",Pattern.CASE_INSENSITIVE).matcher(s);if(m.find()){try{return Double.parseDouble(m.group(1).replace(',','.'));}catch(Exception ignored){}}return 0;}
    private TextView t(String s,int z,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);v.setPadding(0,8,0,12);return v;}
    @Override protected void onDestroy(){super.onDestroy();recognizer.close();}

    private static final class Record{long time,inf,lan,mark,troops,losses,injured,light,survivors;double atk,def,leth,health,confidence;String outcome,role;int images;Record(long t,String o,String ro,long i,long l,long m,long tr,long lo,long in,long li,long su,double a,double d,double le,double h,int im,double c){time=t;outcome=o;role=ro;inf=i;lan=l;mark=m;troops=tr;losses=lo;injured=in;light=li;survivors=su;atk=a;def=d;leth=le;health=h;images=im;confidence=c;}String encode(){return time+"|"+outcome+"|"+role+"|"+inf+"|"+lan+"|"+mark+"|"+troops+"|"+losses+"|"+injured+"|"+light+"|"+survivors+"|"+atk+"|"+def+"|"+leth+"|"+health+"|"+images+"|"+confidence;}static Record decode(String s){try{String[]x=s.split("\\|",-1);return new Record(Long.parseLong(x[0]),x[1],x[2],Long.parseLong(x[3]),Long.parseLong(x[4]),Long.parseLong(x[5]),Long.parseLong(x[6]),Long.parseLong(x[7]),Long.parseLong(x[8]),Long.parseLong(x[9]),Long.parseLong(x[10]),Double.parseDouble(x[11]),Double.parseDouble(x[12]),Double.parseDouble(x[13]),Double.parseDouble(x[14]),Integer.parseInt(x[15]),Double.parseDouble(x[16]));}catch(Exception e){return null;}}}
}
