package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    LinearLayout flowLayout;
    LayoutInflater layoutInflater;
    private static String APIKEY="17d99bf38e7ffbebabfc4d9713b679a8";
    String BASE_URL = "https://api.themoviedb.org";
    View myView;
    String posterURL, posterpth;
    int movieID;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       flowLayout = findViewById(R.id.flowLayout);
       progressBar = findViewById(R.id.progressBar2);
       textView = findViewById(R.id.textView5);
       Intent searchIntent = getIntent();
       String query = searchIntent.getStringExtra("query");
       flowLayout.removeAllViews();
       retro(query);
    }

    public void retro(String query)
    {
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

        movieAPI MovieAPI = retrofit.create(movieAPI.class);
        Call<MovieInformation> call = MovieAPI.getMovies(APIKEY,query);
        call.enqueue(new Callback<MovieInformation>() {
            @Override
            public void onResponse(Call<MovieInformation> call, Response<MovieInformation> response) {

                MovieInformation movie = response.body();
                List<MovieInformation.Result> list = movie.getResults();
                for(int i=0;i<list.size();i++) {
                    MovieInformation.Result cinema = list.get(i);
                    String title = cinema.getTitle();
                    String releaseDate = cinema.getReleaseDate();
                    String lan = cinema.getOriginalLanguage();
                    String overview = cinema.getOverview();
                    String adult = String.valueOf(cinema.getAdult());
                    movieID = cinema.getId();
                    posterURL = cinema.getBackdropPath();
                    //passing the title, releasedate, language, overview, adultinfo, posterpath, movieid
                    showLayout(title, releaseDate, lan, overview, adult, posterpth, movieID);
                }
            }
            @Override
            public void onFailure(Call<MovieInformation> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("abhigyans error", t.getMessage());
            }
        });
    }

    public void showLayout(String title, String releaseDate, String language, String overview, String Adult, String posterpth, final int movieid) {
        layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = layoutInflater.inflate(R.layout.test_ui, flowLayout, false);

        //setting data for each layout
        TextView movieTitle = myView.findViewById(R.id.titleText);
        movieTitle.setText("Title- " + title);
        TextView languageText = myView.findViewById(R.id.languageText);
        String str = setLanguage(language);
        languageText.setText(str);
        TextView ReleaseDate = myView.findViewById(R.id.genreText);
        ReleaseDate.setText("Release Date- " + releaseDate);
        TextView overviewText = myView.findViewById(R.id.overviewText);
        overviewText.setText("Overview- " + overview);
        TextView adultText = myView.findViewById(R.id.adultInfo);
        adultText.setText("Adult- " + Adult);
        ImageView img = myView.findViewById(R.id.posterView);
        if(posterURL!=null) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + posterURL).fit().into(img);
        }
        else
        {
            img.setImageDrawable(getResources().getDrawable(R.drawable.noprofile));
            img.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        progressBar.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        //find the linear layout and set an onclick listener
        linearLayout= myView.findViewById(R.id.clickLinearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("done", "done");
                Intent completeInfoIntent = new Intent(getApplicationContext(), CompleteInformationActivity.class);
                completeInfoIntent.putExtra("movieid", movieid);
                startActivity(completeInfoIntent);
            }
        });
        flowLayout.addView(myView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }


    public String setLanguage(String lang)
    {
        switch (lang)
        {
            case "hi":
                lang = "Hindi";
                break;

            case "en":
                lang = "English";
                break;

            case "ja":
                lang = "Japanese";
                break;

            case "te":
                lang = "Telegu";
                break;

            case "ta":
                lang = "Tamil";
                break;

            case "ka":
                lang = "Kannada";
                break;

            case "bn":
                lang = "Bengali/Bangla";
                break;

            case "ko":
                lang = "Korean";
                break;

            case "ru":
            lang = "Russian";
            break;

            case "fr":
                lang = "French";
                break;

            case "or":
                lang = "Odia/Oriya";
                break;

           default:
                break;
        }
        String str = "Language- "+lang;
        return str;
    }
}

