package com.hand33h.tulostaulu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Insets;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/** Shared five-item bottom navigation, kept visible on the primary screens. */
public final class BottomNavigation {
    public static final int HOME=0, RANKING=1, BATTLE=2, DEVELOPMENT=3, MORE=4;
    private static final int BACKGROUND=Color.rgb(12,23,38);
    private BottomNavigation() {}
    public static void show(Activity activity, View content, int selected) {
        show(activity,content,selected,null);
    }
    public static void show(Activity activity, View content, int selected, Runnable beforeNavigate) {
        SharedPreferences pref=activity.getSharedPreferences("wos_tulostaulu",Activity.MODE_PRIVATE);
        boolean en="en".equals(pref.getString("lang","fi"));
        LinearLayout shell=new LinearLayout(activity);
        shell.setOrientation(LinearLayout.VERTICAL);
        shell.setBackgroundColor(BACKGROUND);
        shell.addView(content,new LinearLayout.LayoutParams(-1,0,1f));
        LinearLayout tabs=new LinearLayout(activity);
        tabs.setOrientation(LinearLayout.HORIZONTAL);
        tabs.setBackgroundColor(Color.rgb(16,35,58));
        tabs.setPadding(dp(activity,5),dp(activity,9),dp(activity,5),dp(activity,9));
        final String[] labels=en?
            new String[]{"⌂\nHome","▥\nRanking","⚔\nBattle","⚒\nUpgrades","•••\nMore"}:
            new String[]{"⌂\nKoti","▥\nRanking","⚔\nTaistelu","⚒\nKehitys","•••\nLisää"};
        for(int i=0;i<5;i++) {
            final int section=i;
            Button b=new Button(activity);
            b.setAllCaps(false);
            b.setMinWidth(0);b.setMinimumWidth(0);b.setMinHeight(0);b.setMinimumHeight(0);
            b.setText(labels[i]);b.setTextSize(10.5f);
            b.setGravity(Gravity.CENTER);b.setIncludeFontPadding(false);
            b.setTextColor(i==selected?Color.rgb(239,192,90):Color.rgb(226,239,247));
            b.setTypeface(i==selected?Typeface.DEFAULT_BOLD:Typeface.DEFAULT);
            b.setPadding(dp(activity,1),dp(activity,7),dp(activity,1),dp(activity,7));
            GradientDrawable bg=new GradientDrawable();
            bg.setColor(i==selected?Color.rgb(29,90,130):Color.rgb(29,50,74));
            bg.setCornerRadius(dp(activity,10));
            b.setBackground(bg);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,dp(activity,52),1f);
            lp.setMargins(dp(activity,2),0,dp(activity,2),0);
            tabs.addView(b,lp);
            b.setOnClickListener(v-> {
                if (beforeNavigate!=null)beforeNavigate.run();
                if(section==MORE) { showMore(activity,en); return; }
                if(section==selected) {
                    if(content instanceof ScrollView)((ScrollView)content).smoothScrollTo(0,0);
                    return;
                }
                Class<? extends Activity> target;
                switch(section) {
                    case RANKING: target=DataSourcesActivity.class;break;
                    case BATTLE: target=BattleSimulatorActivity.class;break;
                    case DEVELOPMENT: target=CalculatorHubActivity.class;break;
                    default: target=MainActivity.class;break;
                }
                Intent intent=new Intent(activity,target);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent);
            });
        }
        shell.addView(tabs,new LinearLayout.LayoutParams(-1,-2));
        shell.setOnApplyWindowInsetsListener((v,insets)->{
            Insets bars=insets.getInsets(WindowInsets.Type.systemBars());
            shell.setPadding(0,bars.top,0,0);
            tabs.setPadding(dp(activity,5),dp(activity,9),dp(activity,5),dp(activity,9)+bars.bottom);
            return insets;
        });
        activity.setContentView(shell);
        shell.requestApplyInsets();
    }
    private static void showMore(Activity activity,boolean en) {
        String[] names=en?
            new String[]{"My account / API key","Battle reports","Import screenshots","Forum","Data library","Expert planner","WOS strategy center"}:
            new String[]{"Oma tili / API-avain","Taisteluraportit","Tuo kuvakaappaukset","Foorumi","Datakirjasto","Expert-suunnittelija","WOS-strategiakeskus"};
        Class<?>[] targets={
            MyAccountActivity.class,BattleReportAnalyzerActivity.class,ScreenshotImportActivity.class,
            ForumActivity.class,DataCatalogActivity.class,ExpertPlannerActivity.class,Wos73Activity.class
        };
        new AlertDialog.Builder(activity)
            .setTitle(en?"More WOS tools":"Lisää WOS-työkaluja")
            .setItems(names,(d,which)->activity.startActivity(new Intent(activity,targets[which])))
            .setNegativeButton(en?"Close":"Sulje",null).show();
    }
    private static int dp(Activity a,int value) {
        return (int)(value*a.getResources().getDisplayMetrics().density+0.5f);
    }
}
