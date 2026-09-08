package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.*;

public class ForumActivity extends Activity {
    private static final String PREFS = "wos_tulostaulu";
    private boolean en;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        SharedPreferences p = getSharedPreferences(PREFS, MODE_PRIVATE);
        en = "en".equals(p.getString("lang", "fi"));
        boolean anonymous = p.getBoolean("anonymous_mode", false);

        ScrollView sc = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(20), dp(18), dp(40));
        root.setBackgroundColor(Color.rgb(7, 23, 39));
        sc.addView(root);

        root.addView(text("💬  WOS FORUM", 28, true, Color.WHITE));
        root.addView(text(tr("Bunny King -yhteisö", "Bunny King community"), 14, true, Color.rgb(119,205,255)), margins(0,4,0,5));
        root.addView(text(anonymous ? tr("🕶️ Anonyymi tila käytössä", "🕶️ Anonymous mode enabled") : tr("Keskustelut, rekrytointi ja oppaat yhdessä paikassa.", "Discussions, recruitment and guides in one place."), 12, false, Color.rgb(202,224,238)), margins(0,0,0,16));

        addCategory(root, "🌐", tr("Yleinen", "General"), tr("WOS-keskustelu ja kysymykset", "WOS discussion and questions"));
        addCategory(root, "🏰", tr("State-rekrytointi", "State Recruitment"), tr("Etsi pelaajia tai uutta statea", "Find players or a new state"));
        addCategory(root, "🤝", tr("Alliance-rekrytointi", "Alliance Recruitment"), tr("Alliance-ilmoitukset ja hakijat", "Alliance posts and applicants"));
        addCategory(root, "⚔️", "SvS & Battle", tr("Strategiat, raportit ja vinkit", "Strategies, reports and tips"));
        addCategory(root, "📚", tr("Oppaat & laskurit", "Guides & Calculators"), tr("Jaa rakennus-, tutkimus- ja event-vinkkejä", "Share build, research and event tips"));
        addCategory(root, "📸", tr("Kuvat & saavutukset", "Screenshots & Achievements"), tr("Jaa kuvia ja WOS-hetkiä", "Share screenshots and WOS moments"));

        LinearLayout info = panel();
        info.setPadding(dp(14), dp(14), dp(14), dp(14));
        info.addView(text(tr("FORUM v1", "FORUM v1"), 13, true, Color.rgb(119,205,255)));
        info.addView(text(tr("Osio on nyt erotettu omaksi sovellusnäkymäkseen. Seuraava vaihe liittää viestit, vastaukset, tykkäykset, kuvat ja moderoinnin palvelimeen.", "The forum is now a standalone app section. The next stage connects posts, replies, likes, images and moderation to the backend."), 12, false, Color.WHITE), margins(0,5,0,0));
        root.addView(info, margins(0,18,0,0));

        setContentView(sc);
    }

    private void addCategory(LinearLayout root, String icon, String title, String sub) {
        Button b = new Button(this);
        b.setText(icon + "  " + title + "\n" + sub);
        b.setTextColor(Color.WHITE);
        b.setTextSize(14);
        b.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        b.setAllCaps(false);
        b.setTypeface(Typeface.DEFAULT_BOLD);
        b.setPadding(dp(16), dp(10), dp(16), dp(10));
        GradientDrawable g = new GradientDrawable();
        g.setColor(Color.rgb(18,48,70));
        g.setCornerRadius(dp(16));
        g.setStroke(dp(1), Color.rgb(52,104,136));
        b.setBackground(g);
        b.setOnClickListener(v -> Toast.makeText(this, tr("Keskusteluketjut kytketään seuraavassa vaiheessa.", "Discussion threads will be connected in the next stage."), Toast.LENGTH_SHORT).show());
        root.addView(b, margins(0,0,0,9));
    }

    private LinearLayout panel() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable g = new GradientDrawable();
        g.setColor(Color.rgb(13,48,76));
        g.setCornerRadius(dp(16));
        g.setStroke(dp(1), Color.rgb(37,78,105));
        l.setBackground(g);
        return l;
    }

    private TextView text(String s, int size, boolean bold, int color) {
        TextView v = new TextView(this);
        v.setText(s); v.setTextSize(size); v.setTextColor(color);
        if (bold) v.setTypeface(Typeface.DEFAULT_BOLD);
        return v;
    }
    private String tr(String fi, String ee) { return en ? ee : fi; }
    private int dp(int n) { return (int)(n * getResources().getDisplayMetrics().density + .5f); }
    private LinearLayout.LayoutParams margins(int l, int t, int r, int b) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.setMargins(dp(l), dp(t), dp(r), dp(b)); return p;
    }
}
