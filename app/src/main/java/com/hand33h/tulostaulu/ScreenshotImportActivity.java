package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Imports Whiteout Survival ranking screenshots locally and stores verified OCR rows in the player registry. */
public class ScreenshotImportActivity extends Activity {
    private static final int PICK_IMAGES = 9101;
    private static final String PREFS="wos_tulostaulu";
    private Spinner category;
    private TextView status;
    private final TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    private PlayerDataStore playerStore;
    private String stateId="";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);
        stateId=p.getString("state","1674").trim();
        playerStore=new PlayerDataStore(this);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(36, 52, 36, 52);
        root.setBackgroundColor(Color.rgb(234,244,251));
        scroll.addView(root);

        TextView title = new TextView(this);
        title.setText("WOS RANKING SCREENSHOT IMPORT");
        title.setTextSize(22); title.setTextColor(Color.rgb(18,43,64)); title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView help = new TextView(this);
        help.setText("State #"+stateId+" • Valitse ranking-tyyppi ja yksi tai useampi Whiteout Survival -kuvakaappaus. OCR tehdään laitteessa. Jos kuvasta löytyy FID, pelaaja tallennetaan myös pysyvään state-tietokantaan.");
        help.setTextSize(15); help.setTextColor(Color.rgb(68,94,113)); help.setPadding(0,22,0,18);
        root.addView(help);

        category = new Spinner(this);
        category.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, RankingStore.SHEETS));
        root.addView(category, new LinearLayout.LayoutParams(-1,-2));

        Button choose = new Button(this);
        choose.setText("VALITSE WOS-KUVAKAAPPAUKSET");
        choose.setOnClickListener(v -> chooseImages());
        root.addView(choose, margins(0,22,0,10));

        Button openData = new Button(this);
        openData.setText("AVAA WOS DATAKESKUS");
        openData.setOnClickListener(v -> startActivity(new Intent(this, DataSourcesActivity.class)));
        root.addView(openData, new LinearLayout.LayoutParams(-1,-2));

        Button openScoreboard = new Button(this);
        openScoreboard.setText("PALAA TULOSTAULUUN / VIE EXCEL");
        openScoreboard.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        root.addView(openScoreboard, margins(0,10,0,0));

        status = new TextView(this);
        status.setText("Tuotuja rivejä tässä istunnossa: 0");
        status.setTextSize(16); status.setTextColor(Color.rgb(18,43,64)); status.setPadding(0,24,0,0);
        root.addView(status);
        setContentView(scroll);

        if (Intent.ACTION_SEND.equals(getIntent().getAction()) && getIntent().getParcelableExtra(Intent.EXTRA_STREAM) != null) {
            Uri uri = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
            processUris(java.util.Collections.singletonList(uri));
        }
    }

    private LinearLayout.LayoutParams margins(int l,int t,int r,int b){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(l,t,r,b);return p;}
    private void chooseImages(){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("image/*");i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);startActivityForResult(i,PICK_IMAGES);}

    @Override protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);if(requestCode!=PICK_IMAGES||resultCode!=RESULT_OK||data==null)return;
        List<Uri> uris=new ArrayList<>();ClipData clip=data.getClipData();if(clip!=null){for(int i=0;i<clip.getItemCount();i++)uris.add(clip.getItemAt(i).getUri());}else if(data.getData()!=null)uris.add(data.getData());processUris(uris);
    }

    private void processUris(List<Uri> uris){if(uris==null||uris.isEmpty())return;final String sheet=String.valueOf(category.getSelectedItem());status.setText("Luetaan "+uris.size()+" kuvaa…");processNext(uris,0,sheet,0,0);}

    private void processNext(List<Uri> uris,int index,String sheet,int added,int dbSaved){
        if(index>=uris.size()){status.setText("Valmis ✓  Ranking-rivejä "+added+" • pelaajarekisteriin tallennettu "+dbSaved+" • State #"+stateId);Toast.makeText(this,"WOS-rankingkuvat käsitelty",Toast.LENGTH_LONG).show();return;}
        try{
            InputImage image=InputImage.fromFilePath(this,uris.get(index));
            recognizer.process(image).addOnSuccessListener(result->{
                List<String[]> rows=parseRankingText(result.getText(),sheet);RankingStore.addRows(sheet,rows);int saved=savePlayerRows(rows,sheet);processNext(uris,index+1,sheet,added+rows.size(),dbSaved+saved);
            }).addOnFailureListener(e->processNext(uris,index+1,sheet,added,dbSaved));
        }catch(Exception e){processNext(uris,index+1,sheet,added,dbSaved);}
    }

    private int savePlayerRows(List<String[]> rows,String sheet){
        if("Alliance Power".equals(sheet))return 0;int saved=0;long now=System.currentTimeMillis();
        for(String[] row:rows){
            if(row.length<5)continue;String fid=row[2]==null?"":row[2].replaceAll("[^0-9]","");if(fid.isEmpty())continue;
            PlayerDataStore.PlayerRecord r=new PlayerDataStore.PlayerRecord();r.fid=fid;r.nickname=row[1];r.stateId=stateId;r.allianceTag=row[4];r.rank=parseInt(row[0]);r.source="Whiteout Survival screenshot OCR";r.sourceType="LEADERBOARD_OCR";r.observedAt=now;r.fetchedAt=now;r.confidence=75;r.eventType=sheet;
            long value=parseLong(row[3]);
            if("Personal Power".equals(sheet))r.personalPower=value;else if(sheet.toLowerCase(Locale.ROOT).contains("hero"))r.heroPower=value;else if(sheet.toLowerCase(Locale.ROOT).contains("pet"))r.petPower=value;else if(sheet.toLowerCase(Locale.ROOT).contains("expert"))r.expertPower=value;else if(sheet.toLowerCase(Locale.ROOT).contains("kill"))r.kills=value;
            if(playerStore.save(r))saved++;
        }return saved;
    }

    static List<String[]> parseRankingText(String text,String sheet){
        List<String[]> out=new ArrayList<>();if(text==null)return out;String[] lines=text.replace('\r','\n').split("\\n+");Pattern start=Pattern.compile("^\\s*(\\d{1,3})[.)#:-]?\\s*(.*)$");List<String> block=new ArrayList<>();int rank=-1;
        for(String raw:lines){String line=raw.trim();if(line.isEmpty())continue;Matcher m=start.matcher(line);if(m.matches()&&isRank(m.group(1))){if(rank>0)addParsedBlock(out,rank,block,sheet);rank=Integer.parseInt(m.group(1));block.clear();if(!m.group(2).trim().isEmpty())block.add(m.group(2).trim());}else if(rank>0)block.add(line);}if(rank>0)addParsedBlock(out,rank,block,sheet);return out;
    }
    private static boolean isRank(String s){try{int n=Integer.parseInt(s);return n>=1&&n<=100;}catch(Exception e){return false;}}
    private static void addParsedBlock(List<String[]> out,int rank,List<String> block,String sheet){
        if(block.isEmpty())return;String joined=String.join(" ",block).replaceAll("\\s+"," ").trim();if(joined.length()<2)return;List<String> tokens=new ArrayList<>();for(String t:joined.split(" "))if(!t.isBlank())tokens.add(t.trim());if(tokens.isEmpty())return;
        String fid="",value="",alliance="";int valueIdx=-1,fidIdx=-1;for(int i=tokens.size()-1;i>=0;i--){String clean=numeric(tokens.get(i));if(valueIdx<0&&looksValue(clean)){value=tokens.get(i);valueIdx=i;continue;}if(fidIdx<0&&clean.matches("\\d{7,12}")){fid=clean;fidIdx=i;}}
        Matcher tag=Pattern.compile("\\[([^]]{1,8})]|【([^】]{1,8})】").matcher(joined);if(tag.find())alliance=tag.group(1)!=null?tag.group(1):tag.group(2);
        if(alliance.isEmpty()&&!"Alliance Power".equals(sheet)){for(int i=tokens.size()-1;i>=0;i--){String t=tokens.get(i).replaceAll("[^A-Za-z0-9_-]","");if(i!=valueIdx&&i!=fidIdx&&t.matches("[A-Za-z][A-Za-z0-9_-]{1,7}")){alliance=t;break;}}}
        StringBuilder name=new StringBuilder();for(int i=0;i<tokens.size();i++){if(i==valueIdx||i==fidIdx)continue;String t=tokens.get(i);if(!alliance.isEmpty()&&(t.equals(alliance)||t.equals("["+alliance+"]")))continue;if(name.length()>0)name.append(' ');name.append(t);}String cleanValue=normalizeValue(value);
        if("Alliance Power".equals(sheet)){String allianceName=name.toString().trim();if(allianceName.isEmpty())allianceName=alliance;if(!allianceName.isEmpty()&&!cleanValue.isEmpty())out.add(new String[]{String.valueOf(rank),allianceName,cleanValue});}else{String player=name.toString().trim();if(!player.isEmpty()&&!cleanValue.isEmpty())out.add(new String[]{String.valueOf(rank),player,fid,cleanValue,alliance});}
    }
    private static String numeric(String s){return s==null?"":s.replaceAll("[^0-9.,KkMmBb]","");}
    private static boolean looksValue(String s){return s!=null&&(s.matches("\\d{2,}([.,]\\d+)?[KkMmBb]?")||s.matches("\\d+[KkMmBb]"));}
    private static String normalizeValue(String raw){if(raw==null)return"";String s=raw.replace(" ","").replace(",","").toUpperCase(Locale.ROOT).replaceAll("[^0-9.KMB]","");if(s.isEmpty())return"";try{double mult=1;if(s.endsWith("K")){mult=1_000;s=s.substring(0,s.length()-1);}else if(s.endsWith("M")){mult=1_000_000;s=s.substring(0,s.length()-1);}else if(s.endsWith("B")){mult=1_000_000_000;s=s.substring(0,s.length()-1);}return String.valueOf((long)(Double.parseDouble(s)*mult));}catch(Exception e){return raw.replaceAll("[^0-9]","");}}
    private static int parseInt(String s){try{return Integer.parseInt(s);}catch(Exception e){return 0;}}
    private static long parseLong(String s){try{return Long.parseLong(s);}catch(Exception e){return 0;}}
    @Override protected void onDestroy(){super.onDestroy();recognizer.close();if(playerStore!=null)playerStore.close();}
}
