package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MemeActivity extends Activity {
    private static final int PICK_IMAGE = 3101;
    private static final int CREATE_IMAGE = 3102;
    private static final String PREFS = "wos_tulostaulu";
    private boolean english;
    private FrameLayout preview;
    private ImageView image;
    private TextView topOverlay, bottomOverlay;
    private EditText topInput, bottomInput, searchInput;
    private Bitmap lastBitmap;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        SharedPreferences p = getSharedPreferences(PREFS, MODE_PRIVATE);
        english = "en".equals(p.getString("lang", "fi"));

        ScrollView sc = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(28, 36, 28, 48);
        root.setBackgroundColor(Color.rgb(234,244,251));
        sc.addView(root);

        root.addView(txt("🐰 WHITEOUT SURVIVAL MEME GENERATOR", 22, true, Gravity.CENTER));
        root.addView(txt(tr("Tee oma WOS-meemi kuvasta ja teksteistä.", "Create your own WOS meme from an image and captions."), 14, false, Gravity.CENTER), margins(0,8,0,16));

        root.addView(txt(tr("INTERNET-KUVAHAKU", "INTERNET IMAGE SEARCH"), 15, true, Gravity.CENTER));
        searchInput = input(tr("Hakusana, esim. Whiteout Survival", "Search, e.g. Whiteout Survival"));
        searchInput.setText("Whiteout Survival");
        root.addView(searchInput, margins(0,8,0,8));
        Button webSearch = btn(tr("HAE KUVIA INTERNETISTÄ", "SEARCH IMAGES ONLINE"), Color.rgb(75,105,123));
        root.addView(webSearch, margins(0,0,0,8));
        root.addView(txt(tr("Avaa kuvahaun selaimeen. Tallenna haluamasi kuva puhelimeen ja valitse se sitten alta.", "Opens image search in your browser. Save the image you want, then choose it below."), 12, false, Gravity.CENTER), margins(0,0,0,14));

        Button choose = btn(tr("VALITSE KUVA PUHELIMESTA", "CHOOSE IMAGE FROM PHONE"), Color.rgb(20,125,190));
        root.addView(choose);

        topInput = input(tr("Yläteksti", "Top text"));
        bottomInput = input(tr("Alateksti", "Bottom text"));
        root.addView(topInput, margins(0,12,0,8));
        root.addView(bottomInput, margins(0,0,0,12));

        preview = new FrameLayout(this);
        preview.setBackgroundColor(Color.DKGRAY);
        LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(-1, dp(420));
        root.addView(preview, pp);

        image = new ImageView(this);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setBackgroundColor(Color.BLACK);
        preview.addView(image, new FrameLayout.LayoutParams(-1,-1));

        topOverlay = overlay(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        bottomOverlay = overlay(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        preview.addView(topOverlay, overlayLp(Gravity.TOP));
        preview.addView(bottomOverlay, overlayLp(Gravity.BOTTOM));

        Button update = btn(tr("PÄIVITÄ TEKSTIT", "UPDATE TEXT"), Color.rgb(54,96,130));
        Button save = btn(tr("TALLENNA MEEMI", "SAVE MEME"), Color.rgb(48,95,130));
        root.addView(update, margins(0,12,0,8));
        root.addView(save);

        webSearch.setOnClickListener(v -> searchImages());
        choose.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            i.setType("image/*");
            i.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(i, PICK_IMAGE);
        });
        update.setOnClickListener(v -> updateText());
        save.setOnClickListener(v -> saveMeme());
        setContentView(sc);
    }

    private void searchImages() {
        try {
            String q = searchInput.getText().toString().trim();
            if (q.isEmpty()) q = "Whiteout Survival";
            String encoded = URLEncoder.encode(q, StandardCharsets.UTF_8.toString());
            Uri uri = Uri.parse("https://www.google.com/search?tbm=isch&q=" + encoded);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (Exception e) {
            toast(tr("Kuvahaku ei auennut: ", "Could not open image search: ") + e.getMessage());
        }
    }

    private void updateText() {
        topOverlay.setText(topInput.getText().toString().trim());
        bottomOverlay.setText(bottomInput.getText().toString().trim());
    }

    private void saveMeme() {
        if (preview.getWidth() <= 0 || preview.getHeight() <= 0) { toast(tr("Esikatselu ei ole valmis.", "Preview is not ready.")); return; }
        updateText();
        Bitmap bmp = Bitmap.createBitmap(preview.getWidth(), preview.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        preview.draw(c);
        lastBitmap = bmp;
        Intent i = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/png");
        i.putExtra(Intent.EXTRA_TITLE, "WOS_meme.png");
        startActivityForResult(i, CREATE_IMAGE);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;
        try {
            if (requestCode == PICK_IMAGE) {
                Uri uri = data.getData();
                if (uri != null) {
                    try { getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION); } catch (Exception ignored) {}
                    image.setImageURI(uri);
                }
            } else if (requestCode == CREATE_IMAGE && lastBitmap != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    try (OutputStream out = getContentResolver().openOutputStream(uri)) {
                        lastBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    }
                    toast(tr("Meemi tallennettu ✓", "Meme saved ✓"));
                }
            }
        } catch (Exception e) {
            toast(tr("Toiminto epäonnistui: ", "Action failed: ") + e.getMessage());
        }
    }

    private String tr(String fi, String en) { return english ? en : fi; }
    private TextView txt(String s, int size, boolean bold, int gravity) { TextView v=new TextView(this); v.setText(s); v.setTextSize(size); v.setTextColor(Color.rgb(18,43,64)); v.setGravity(gravity); if(bold)v.setTypeface(Typeface.DEFAULT_BOLD); return v; }
    private EditText input(String hint) { EditText e=new EditText(this); e.setHint(hint); e.setSingleLine(true); e.setBackgroundColor(Color.WHITE); e.setPadding(18,14,18,14); e.setTextColor(Color.rgb(18,43,64)); return e; }
    private Button btn(String s, int color) { Button b=new Button(this); b.setText(s); b.setTextColor(Color.WHITE); b.setTypeface(Typeface.DEFAULT_BOLD); b.setBackgroundColor(color); return b; }
    private TextView overlay(int gravity) { TextView t=new TextView(this); t.setGravity(gravity); t.setTextColor(Color.WHITE); t.setTextSize(28); t.setTypeface(Typeface.DEFAULT_BOLD); t.setShadowLayer(6f,2f,2f,Color.BLACK); t.setPadding(18,18,18,18); return t; }
    private FrameLayout.LayoutParams overlayLp(int gravity) { FrameLayout.LayoutParams p=new FrameLayout.LayoutParams(-1,-2); p.gravity=gravity; return p; }
    private LinearLayout.LayoutParams margins(int l,int t,int r,int b){ LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2); p.setMargins(l,t,r,b); return p; }
    private int dp(int n){ return (int)(n*getResources().getDisplayMetrics().density + 0.5f); }
    private void toast(String s){ Toast.makeText(this,s,Toast.LENGTH_LONG).show(); }
}
