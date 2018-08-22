package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView tmdbIcon, appicon;
    TextView appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tmdbIcon = findViewById(R.id.tmdbIconImage);
        tmdbIcon.animate().translationY(-3000f);
        appicon = findViewById(R.id.iconImage);
        appname = findViewById(R.id.appNameText);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                appicon.animate().translationXBy(-2000f).setDuration(700);
                appname.animate().translationXBy(2000f).setDuration(700);
                tmdbIcon.animate().translationYBy(4000f).setDuration(700);
            }
        },2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LatestActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        },4000);

    }
}
