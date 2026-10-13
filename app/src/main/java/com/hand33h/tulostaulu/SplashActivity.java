package com.hand33h.tulostaulu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/** Reliable splash: uses the bundled Bunny vector instead of corrupted JPEG. */
public class SplashActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setStatusBarColor(Color.rgb(12,23,38));
        getWindow().setNavigationBarColor(Color.rgb(12,23,38));

        FrameLayout frame=new FrameLayout(this);
        frame.setBackgroundColor(Color.rgb(12,23,38));
        ImageView image=new ImageView(this);
        image.setImageResource(R.drawable.app_icon);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setContentDescription("Bunny 👑");
        FrameLayout.LayoutParams p=new FrameLayout.LayoutParams(dp(236),dp(236),Gravity.CENTER);
        frame.addView(image,p);

        TextView brand=new TextView(this);
        brand.setText("Bunny 👑");
        brand.setTextSize(28f);
        brand.setTextColor(Color.rgb(239,192,90));
        brand.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams t=new FrameLayout.LayoutParams(-1,dp(60),Gravity.BOTTOM);
        t.bottomMargin=dp(48);
        frame.addView(brand,t);
        setContentView(frame);
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            if(isFinishing()||isDestroyed())return;
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        },1300);
    }
    private int dp(int n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}
}
