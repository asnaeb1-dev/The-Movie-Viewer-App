package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

/**
 * this activity shows the registered website of the movie being watched
 */


public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    String url ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        Log.i("url", url);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

}
