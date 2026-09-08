package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BattleReportAnalyzerActivity extends Activity {
    private static final int PICK=6201;
    private final TextRecognizer recognizer=TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    private TextView status,result;
    private final StringBuilder merged=new StringBuilder();

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        ScrollView sc=new ScrollView(this);LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(32,40,32,48);root.setBackgroundColor(Color.rgb(7,23,39));sc.addView(root);
        TextView title=t("⚔️ BATTLE REPORT ANALYZER 6.0",24,Color.WHITE);root.addView(title);
        root.addView(t("Valitse yksi tai useampi Whiteout Survival -taisteluraportin kuvakaappaus. OCR tehdään laitteessa. Analyysi on heuristinen ja erotetaan virallisista pelikaavoista.",13,Color.rgb(205,226,238)));
        Button pick=new Button(this);pick.setText("VALITSE RAPORTTIKUVAT");pick.setOnClickListener(v->choose());root.addView(pick);
        Button history=new Button(this);history.setText("NÄYTÄ VIIMEISIN TALLENNETTU ANALYYSI");history.setOnClickListener(v->result.setText(getSharedPreferences("wos_tulostaulu",MODE_PRIVATE).getString("last_battle_analysis","Ei tallennettua analyysiä.")));root.addView(history);
        status=t("Valmis",14,Color.rgb(119,205,255));root.addView(status);
        result=t("",13,Color.WHITE);result.setMovementMethod(new ScrollingMovementMethod());root.addView(result);
        setContentView(sc);
        if(Intent.ACTION_SEND_MULTIPLE.equals(getIntent().getAction())){ArrayList<Uri> u=getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);if(u!=null)process(u);}else if(Intent.ACTION_SEND.equals(getIntent().getAction())){Uri u=getIntent().getParcelableExtra(Intent.EXTRA_STREAM);if(u!=null){ArrayList<Uri> a=new ArrayList<>();a.add(u);process(a);}}
    }

    private void choose(){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("image/*");i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);startActivityForResult(i,PICK);}
    @Override protected void onActivityResult(int req,int res,Intent data){super.onActivityResult(req,res,data);if(req!=PICK||res!=RESULT_OK||data==null)return;ArrayList<Uri> u=new ArrayList<>();ClipData c=data.getClipData();if(c!=null){for(int i=0;i<c.getItemCount();i++)u.add(c.getItemAt(i).getUri());}else if(data.getData()!=null)u.add(data.getData());process(u);}
    private void process(List<Uri> uris){merged.setLength(0);status.setText("Luetaan "+uris.size()+" kuvaa…");next(uris,0);}
    private void next(List<Uri> uris,int i){if(i>=uris.size()){analyze();return;}try{InputImage im=InputImage.fromFilePath(this,uris.get(i));recognizer.process(im).addOnSuccessListener(r->{merged.append("\n--- IMAGE ").append(i+1).append(" ---\n").append(r.getText());next(uris,i+1);}).addOnFailureListener(e->next(uris,i+1));}catch(Exception e){next(uris,i+1);}}

    private void analyze(){String s=merged.toString();String low=s.toLowerCase(Locale.ROOT);long inf=findCount(low,"infantry"),lan=findCount(low,"lancer"),mark=findCount(low,"marksman");double atk=findPercent(low,"attack"),def=findPercent(low,"defense"),leth=findPercent(low,"lethality"),health=findPercent(low,"health");StringBuilder out=new StringBuilder();out.append("OCR-kuvia käsitelty ✓\n\n");if(inf>0||lan>0||mark>0)out.append("Havaitut troop counts\nInfantry: ").append(inf).append("\nLancer: ").append(lan).append("\nMarksman: ").append(mark).append("\n\n");if(atk>0||def>0||leth>0||health>0)out.append("Havaitut prosentit\nAttack: ").append(atk).append("%\nDefense: ").append(def).append("%\nLethality: ").append(leth).append("%\nHealth: ").append(health).append("%\n\n");out.append("Yhteisö-/heuristiikkahuomio: vertaa useita omia raportteja samalla kokoonpanolla ennen johtopäätöksiä. Sovellus ei käsittele näitä OCR-havaintoja virallisena WOS-taistelukaavana.\n\nRAW OCR\n").append(s.length()>6000?s.substring(0,6000)+"\n…":s);String text=out.toString();result.setText(text);status.setText("Valmis ✓");getSharedPreferences("wos_tulostaulu",MODE_PRIVATE).edit().putString("last_battle_analysis",text.length()>10000?text.substring(0,10000):text).apply();}
    private long findCount(String s,String key){Matcher m=Pattern.compile(key+"[^0-9]{0,30}([0-9][0-9, .]{2,})",Pattern.CASE_INSENSITIVE).matcher(s);if(m.find()){try{return Long.parseLong(m.group(1).replaceAll("[^0-9]",""));}catch(Exception ignored){}}return 0;}
    private double findPercent(String s,String key){Matcher m=Pattern.compile(key+"[^0-9]{0,20}([0-9]+(?:[.,][0-9]+)?)\\s*%",Pattern.CASE_INSENSITIVE).matcher(s);if(m.find()){try{return Double.parseDouble(m.group(1).replace(',','.'));}catch(Exception ignored){}}return 0;}
    private TextView t(String s,int z,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);v.setPadding(0,8,0,12);return v;}
    @Override protected void onDestroy(){super.onDestroy();recognizer.close();}
}
