package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    Boolean aboutDevIsShowing = false;
    TextView aboutDev, textView;

    LinearLayout appGuideLL;

    ImageView githubLinkIcon, emailLinkIcon, appIcon, tmdbIcon;

   String str ="Hi! Presenting the Movie Viewer application Guide.\n" +
            "\n"+
            "Movie Viewer gives the user information about any movie.\n"
            +"The user can search for desired movies or get information about the latest, popular, top-rated movies. \n"
            +"\n"
            +" FEATURES: \n"
            +"\n"
            +"a) Get information about the latest, popular, top-rated and upcoming movies. \n"
            +"\n"
            +"b) Search for any movie and get detailed description and detailed information about the movie.\n"
            +"\n"
            +"c) Save your favourite movies online and view the data whenever desired.\n"
            +"\n"
            +"d) Get multiple pictures/poster/images of the movies. \n"
            +"\n"
            +"e) Watch videos/trailers of movies.\n"
            +"\n"
            +"f) Read reviews of the movies.\n"
            +"\n"
            +"g) Get movie recommendations and decide what else to watch of the same genre.\n"
            +"\n"
            +"h) Login/register into your account and make your experience more personalized. Also, no fear of using data.\n\n"
            +"       *******DATA OBTAINED FROM:*******" +"\n\n"
            +"       This application is developed using TMDb API, but is neither endorsed nor certified by TMDb";


   String aboutDevstr = "  **DETAILS ABOUT DEV**\n"+
                        "\n"
                        +"Hi! guys. This is Abhigyan Raha.\n"
                        +"\n"
                        +"Hope you like this app.\n "
                        +"\n Contact me on the details below for any feedback regarding my app! "
                        +"\n"
                        +"\n Click of the icons below to go to the desired means of communication.";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.about_dev_menu, menu);
        MenuItem about = menu.findItem(R.id.aboutdev);
        ImageButton aboutbutton = (ImageButton) about.getActionView();
        aboutbutton.setPadding(0, 0, 20, 10);
        aboutbutton.setImageResource(R.drawable.ic_about_dev);

        if (Build.VERSION.SDK_INT >= 21) { //sdk version>=21
            aboutbutton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        } else {
            //sdk version<21
            aboutbutton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
        aboutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (aboutDevIsShowing == false) {
                    //move all the existing UI to the left

                    textView.animate().translationXBy(-2000f).setDuration(700);
                    aboutDev.animate().translationXBy(-2000f).setDuration(700);
                    githubLinkIcon.animate().translationXBy(-2000f).setDuration(700);
                    emailLinkIcon.animate().translationXBy(-2000f).setDuration(700);
                    appGuideLL.setVisibility(View.INVISIBLE);
                    aboutDev.setText(aboutDevstr);
                    aboutDevIsShowing = true;
                } else {

                    textView.animate().translationXBy(2000f).setDuration(700);
                    aboutDev.animate().translationXBy(2000f).setDuration(700);
                    githubLinkIcon.animate().translationXBy(2000f).setDuration(700);
                    emailLinkIcon.animate().translationXBy(2000f).setDuration(700);
                    appGuideLL.setVisibility(View.VISIBLE);
                    aboutDev.setText("");
                    aboutDevIsShowing = false;
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textView = findViewById(R.id.textView9);
        aboutDev = findViewById(R.id.aboutDevText);
        githubLinkIcon = findViewById(R.id.gitHubLink);
        emailLinkIcon = findViewById(R.id.gmailLink);
        appIcon = findViewById(R.id.appIcon);
        tmdbIcon = findViewById(R.id.tmdbIcon);
        appGuideLL = findViewById(R.id.appGuideLayout);
        githubLinkIcon.setTranslationX(2000f);
        emailLinkIcon.setTranslationX(2000f);
        aboutDev.setTranslationX(2000f);
        textView.setText(str);

        githubLinkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent githubIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/asnaeb1"));
                getApplicationContext().startActivity(githubIntent);
            }
        });
    }
    public  void gmailFunction(View view)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"abhigyanraha76@gmail.com"});
        emailIntent.setType("plain/text");
        getApplicationContext().startActivity(Intent.createChooser(emailIntent,
                "Send email using..."));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
