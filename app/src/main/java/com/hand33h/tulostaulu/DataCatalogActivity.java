package com.hand33h.tulostaulu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DataCatalogActivity extends Activity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        ScrollView sc=new ScrollView(this);
        LinearLayout root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(32,40,32,48);
        root.setBackgroundColor(Color.rgb(7,23,39));
        sc.addView(root);
        root.addView(t("🧬 WOS DATA ENGINE 7.0",25,Color.WHITE));
        root.addView(t("Laskuridata pidetään erillään OCR-havainnoista ja community-strategiasta. Season-dependent pisteitä ei käsitellä universaaleina vakioina.",13,Color.rgb(205,226,238)));
        root.addView(t(WosDataCatalog.statusText(),14,Color.rgb(119,205,255)));
        root.addView(t("T12: War Academy -data käsitellään Infantry/Lancer/Marksman-linjoina ja tarkistetaan pelin omasta preview-näkymästä ennen suuria kulutuksia.\n\nTransfer: community-ryhmät ja listaukset ovat ennuste-/community-dataa ja ne pitää vahvistaa pelin ilmoituksesta.",13,Color.WHITE));
        setContentView(sc);
    }
    private TextView t(String s,int size,int color){TextView v=new TextView(this);v.setText(s);v.setTextSize(size);v.setTextColor(color);v.setPadding(0,8,0,16);return v;}
}
