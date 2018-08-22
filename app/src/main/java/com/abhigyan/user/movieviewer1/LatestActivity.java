package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LatestActivity extends AppCompatActivity {

    //crucial information
    String BASE_URL = "https://api.themoviedb.org";
    private static String APIKEY="17d99bf38e7ffbebabfc4d9713b679a8";

    LinearLayout nowplayinglinearlayout,
                 popularlinearlayout,
                 topratedlinearlayout,
                 upcominglinearlayout;

    LinearLayout dataLayout,
            languageLinearLayout,
            yearLinearLayout,
            genreLinearLayout;


    ProgressBar progressBar;
    TextView loadingtextView,
            promptText;

    Boolean doubleBackToExitPressedOnce = false;

    int howManyTimesTrueShown = 0,
    howManyTimesFalseShown = 0 ;

    Spinner spinner, yearSpinner, genreSpinner;

    ArrayList<String> languageArrayList = new ArrayList<>();
    ArrayList<String> yearArrayList = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search movie...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query1) {

                Intent searchIntent = new Intent(getApplicationContext(), MainActivity.class);
                searchIntent.putExtra("query", query1);
                startActivity(searchIntent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem menuItem1 = menu.findItem(R.id.refreshButton);
        ImageButton refreshButton = (ImageButton) menuItem1.getActionView();
        refreshButton.setImageResource(R.drawable.ic_refresh_view);

        if(Build.VERSION.SDK_INT>=21) {
            refreshButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
        else
        {
            refreshButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nowplayinglinearlayout.removeAllViews();
                popularlinearlayout.removeAllViews();
                topratedlinearlayout.removeAllViews();
                upcominglinearlayout.removeAllViews();
                languageLinearLayout.removeAllViews();
                yearLinearLayout.removeAllViews();

                progressBar.setVisibility(View.VISIBLE);
                loadingtextView.setVisibility(View.VISIBLE);

                retrotoprated("top_rated");
                retronowplaying("now_playing");
                retroupcoming("upcoming");
                retropopular("popular");
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.aboutOption:

                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                return true;

            case R.id.favOption:

                if(ParseUser.getCurrentUser()!=null) {

                    Intent favouritesIntent = new Intent(getApplicationContext(), FavouritesActivity.class);
                    startActivity(favouritesIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                }
                else
                {
                    Toast.makeText(this, "Sorry! You need to Login to access Favourites!", Toast.LENGTH_SHORT).show();
                }

            case R.id.loginOption:

                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;

            case R.id.logoutOption:

               if(ParseUser.getCurrentUser()!=null) {
                   ParseUser.logOut();
                   if (ParseUser.getCurrentUser() != null) {
                       String str = "Hi!, " + ParseUser.getCurrentUser().getUsername() + ". Here are some movies you might like!";
                       promptText.setText(str);
                       promptText.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Toast.makeText(LatestActivity.this, "You have already logged In!", Toast.LENGTH_SHORT).show();
                           }
                       });
                   } else {
                       promptText.setText("Looks like you haven't Logged In. Login to use all the features of Movie Viewer!");
                       promptText.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {

                               Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                               startActivity(intent);
                               overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                               finish();
                           }
                       });
                   }
               }
               else
               {
                   Toast.makeText(this, "You are not logged in!", Toast.LENGTH_SHORT).show();
               }
                return true;

            default:
                return  false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest);

        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        nowplayinglinearlayout = findViewById(R.id.nowplayinglinearlayout);
       popularlinearlayout = findViewById(R.id.popularlinearlayout);
       topratedlinearlayout = findViewById(R.id.topratedlinearlayout);
       promptText = findViewById(R.id.promptText);
       upcominglinearlayout = findViewById(R.id.upcominglinearlayout);
       yearLinearLayout = findViewById(R.id.yearLinearLayout);
       yearSpinner = findViewById(R.id.yearSpinner);
       dataLayout = findViewById(R.id.dataLayout);
       spinner = findViewById(R.id.languageSpinner);
       languageLinearLayout = findViewById(R.id.mLanguageLinearLayout);
       dataLayout.setVisibility(View.INVISIBLE);

       progressBar = findViewById(R.id.progressBar);
       loadingtextView = findViewById(R.id.loadingText);

       checkForInternet();

       retrotoprated("top_rated");
       retronowplaying("now_playing");
       retroupcoming("upcoming");
       retropopular("popular");

        if (ParseUser.getCurrentUser() != null) {
            String str = "Hi!, "+ParseUser.getCurrentUser().getUsername()+". Here are some movies you might like!";
            promptText.setText(str);
            promptText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(LatestActivity.this, "You have already logged In!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            promptText.setText("Looks like you haven't Logged In. Login to use all the features of Movie Viewer!");
            promptText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
            });
        }
       languageArrayList.add("Select language of your choice...");
       languageArrayList.add("Hindi");
       languageArrayList.add("Bengali/Bangla");
       languageArrayList.add("Oriya");
       languageArrayList.add("Telegu");
       languageArrayList.add("Kannada");
       languageArrayList.add("Tamil");
       languageArrayList.add("French");
       languageArrayList.add("Russian");
       languageArrayList.add("Korean");
       languageArrayList.add("Japanese");

       ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,languageArrayList);
       spinner.setAdapter(arrayAdapter);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


               languageLinearLayout.removeAllViews();
               progressBar.setVisibility(View.VISIBLE);
               loadingtextView.setVisibility(View.VISIBLE);
               String lang ="";

               if(position!=0) {
                   switch (position) {
                       case 1:
                           lang = "hi";
                           break;
                       case 2:
                           lang = "bn";
                           break;
                       case 3:
                           lang = "or";
                           break;
                       case 4:
                           lang = "te";
                           break;
                       case 5:
                           lang = "ka";
                           break;
                       case 6:
                           lang = "ta";
                           break;
                       case 7:
                           lang = "fr";
                           break;
                       case 8:
                           lang = "ru";
                           break;
                       case 9:
                           lang = "ko";
                           break;
                       case 10:
                           lang = "ja";
                           break;

                       default:
                           break;
                   }
                   retroLanguage(lang);
               }
           }
           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

       yearArrayList.add("Select year of your choice...");
       for(int year=1920;year<=2018;year++)
       {
           yearArrayList.add(String.valueOf(year));
       }
       ArrayAdapter arrayAdapteryear = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, yearArrayList);
       yearSpinner.setAdapter(arrayAdapteryear);
       yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               if(position!=0) {
                   yearLinearLayout.removeAllViews();
                   String item = String.valueOf(yearSpinner.getItemAtPosition(position));
                   retroYear(Integer.parseInt(item));
               }
           }
           @Override
           public void onNothingSelected(AdapterView<?> parent) {
           }
       });

        new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                checkForInternet();
                start();
            }
        };
    }

    public void retronowplaying(String category)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        latestInfoInterfaceAPI latestInfo = retrofit.create(latestInfoInterfaceAPI.class);
        Call<Dates> call = latestInfo.getLatestMovie(category,APIKEY);
        call.enqueue(new Callback<Dates>() {
            @Override
            public void onResponse(Call<Dates> call, Response<Dates> response) {

                Dates latestMovie = response.body();
                List<Dates.Result> list = latestMovie.getResults();
                for(int i=0;i<list.size();i++) {

                    Dates.Result latestCinema = list.get(i);
                    String posterURL = latestCinema.getPosterPath();
                    int movieID = latestCinema.getId();
                    nowplayingLayout(posterURL, movieID);
                }
            }
            @Override
            public void onFailure(Call<Dates> call, Throwable t) {
                Log.e("abhigyans error", t.getMessage());
            }
        });
    }
    public void nowplayingLayout(String posterpath, final int id)
    {
        ImageView latestposterimageview = new ImageView(this);
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+posterpath).into(latestposterimageview);
        latestposterimageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        latestposterimageview.setPadding(10,0,10,0);
        nowplayinglinearlayout.addView(latestposterimageview);
        latestposterimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getCompleteInformation = new Intent(getApplicationContext(),CompleteInformationActivity.class);
                getCompleteInformation.putExtra("movieid", id);
                startActivity(getCompleteInformation);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    public void retrotoprated(String category)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        latestInfoInterfaceAPI latestInfo = retrofit.create(latestInfoInterfaceAPI.class);
        Call<Dates> call = latestInfo.getLatestMovie(category,APIKEY);
        call.enqueue(new Callback<Dates>() {
            @Override
            public void onResponse(Call<Dates> call, Response<Dates> response) {

                Dates latestMovie = response.body();
                List<Dates.Result> list = latestMovie.getResults();
                for(int i=0;i<list.size();i++) {

                    Dates.Result latestCinema = list.get(i);
                    String posterURL = latestCinema.getPosterPath();
                    int movieid = latestCinema.getId();
                    topratedlayout(posterURL,movieid);
                }
            }
            @Override
            public void onFailure(Call<Dates> call, Throwable t) {
                Log.e("abhigyans error", t.getMessage());
            }
        });
    }
    public void topratedlayout(String posterpath, final int id)
    {
        ImageView latestposterimageview = new ImageView(this);
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+posterpath).into(latestposterimageview);
        latestposterimageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        latestposterimageview.setPadding(10,0,10,0);

        topratedlinearlayout.addView(latestposterimageview);
        latestposterimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getCompleteInformation = new Intent(getApplicationContext(),CompleteInformationActivity.class);
                getCompleteInformation.putExtra("movieid", id);
                startActivity(getCompleteInformation);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

            }
        });
    }

    public void retroupcoming(String category)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        latestInfoInterfaceAPI latestInfo = retrofit.create(latestInfoInterfaceAPI.class);
        Call<Dates> call = latestInfo.getLatestMovie(category,APIKEY);
        call.enqueue(new Callback<Dates>() {
            @Override
            public void onResponse(Call<Dates> call, Response<Dates> response) {

                Dates latestMovie = response.body();
                List<Dates.Result> list = latestMovie.getResults();
                for(int i=0;i<list.size();i++) {

                    Dates.Result latestCinema = list.get(i);
                    String posterURL = latestCinema.getPosterPath();
                    int movieid = latestCinema.getId();
                    upcomingLayout(posterURL,movieid);
                }
            }
            @Override
            public void onFailure(Call<Dates> call, Throwable t) {
                Log.e("abhigyans error", t.getMessage());
            }
        });
    }
    public void upcomingLayout(String posterpath, final int id)
    {
        ImageView latestposterimageview = new ImageView(this);
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+posterpath).into(latestposterimageview);
        latestposterimageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        latestposterimageview.setPadding(10,0,10,0);

        upcominglinearlayout.addView(latestposterimageview);
        latestposterimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getCompleteInformation = new Intent(getApplicationContext(),CompleteInformationActivity.class);
                getCompleteInformation.putExtra("movieid", id);
                startActivity(getCompleteInformation);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

            }
        });
    }

    public void retropopular(String category)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        latestInfoInterfaceAPI latestInfo = retrofit.create(latestInfoInterfaceAPI.class);
        Call<Dates> call = latestInfo.getLatestMovie(category,APIKEY);
        call.enqueue(new Callback<Dates>() {
            @Override
            public void onResponse(Call<Dates> call, Response<Dates> response) {

                Dates latestMovie = response.body();
                List<Dates.Result> list = latestMovie.getResults();
                for(int i=0;i<list.size();i++) {

                    Dates.Result latestCinema = list.get(i);
                    String posterURL = latestCinema.getPosterPath();
                    int movieid = latestCinema.getId();
                    popularLayout(posterURL,movieid);
                }
            }
            @Override
            public void onFailure(Call<Dates> call, Throwable t) {
                Log.e("abhigyans error", t.getMessage());
            }
        });
    }
    public void popularLayout(String posterpath, final int id)
    {
        ImageView latestposterimageview = new ImageView(this);
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+posterpath).into(latestposterimageview);
        latestposterimageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        latestposterimageview.setPadding(10,0,10,0);

        popularlinearlayout.addView(latestposterimageview);
        loadingtextView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        dataLayout.setVisibility(View.VISIBLE);
        latestposterimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getCompleteInformation = new Intent(getApplicationContext(),CompleteInformationActivity.class);
                getCompleteInformation.putExtra("movieid", id);
                startActivity(getCompleteInformation);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

            }
        });
    }

    public void retroLanguage(String language)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieLanguageInterfaceAPI languageInfo = retrofit.create(MovieLanguageInterfaceAPI.class);
        Call<MovieInformation> call = languageInfo.getMoviesByLanguage(APIKEY, language, "popularity.desc");
        call.enqueue(new Callback<MovieInformation>() {
            @Override
            public void onResponse(Call<MovieInformation> call, Response<MovieInformation> response) {

                MovieInformation langaugeBasedMovie = response.body();
                List<MovieInformation.Result> list = langaugeBasedMovie.getResults();
                for(int i=0;i<list.size();i++) {

                    MovieInformation.Result latestlangaugeBasedCinema = list.get(i);
                    String posterURL = latestlangaugeBasedCinema.getPosterPath();
                    int movieID = latestlangaugeBasedCinema.getId();
                    languageBasedLayout(posterURL,movieID);
                }
            }
            @Override
            public void onFailure(Call<MovieInformation> call, Throwable t) {
                Log.e("abhigyans error", t.getMessage());
            }
        });
    }

    public void languageBasedLayout(String posterpath, final int movieid)
    {
        ImageView latestposterimageview = new ImageView(this);
        if(posterpath!=null) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + posterpath).into(latestposterimageview);
            latestposterimageview.setPadding(5,0,5,0);
        }
        latestposterimageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        progressBar.setVisibility(View.INVISIBLE);
        loadingtextView.setVisibility(View.INVISIBLE);
        languageLinearLayout.addView(latestposterimageview);
        latestposterimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getCompleteInformation = new Intent(getApplicationContext(),CompleteInformationActivity.class);
                getCompleteInformation.putExtra("movieid", movieid);
                startActivity(getCompleteInformation);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    public void retroYear(int year)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YearInterfaceAPI yearInfo = retrofit.create(YearInterfaceAPI.class);
        Call<MovieInformation> call = yearInfo.getMoviebyYear(APIKEY,"popularity.desc", year);
        call.enqueue(new Callback<MovieInformation>() {
            @Override
            public void onResponse(Call<MovieInformation> call, Response<MovieInformation> response) {

                MovieInformation langaugeBasedMovie = response.body();
                List<MovieInformation.Result> list = langaugeBasedMovie.getResults();
                for(int i=0;i<list.size();i++) {

                    MovieInformation.Result latestlangaugeBasedCinema = list.get(i);
                    String posterURL = latestlangaugeBasedCinema.getPosterPath();
                    int movieID = latestlangaugeBasedCinema.getId();
                   yearBasedLayout(posterURL,movieID);
                }
            }
            @Override
            public void onFailure(Call<MovieInformation> call, Throwable t) {
                Log.e("abhigyans error", t.getMessage());
            }
        });
    }
    public void yearBasedLayout(String posterpath, final int movieid)
    {
        ImageView latestposterimageview = new ImageView(this);
        if(posterpath!=null) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + posterpath).into(latestposterimageview);
            latestposterimageview.setPadding(5,0,5,0);
        }
        latestposterimageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        progressBar.setVisibility(View.INVISIBLE);
        loadingtextView.setVisibility(View.INVISIBLE);
        yearLinearLayout.addView(latestposterimageview);
        latestposterimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getCompleteInformation = new Intent(getApplicationContext(),CompleteInformationActivity.class);
                getCompleteInformation.putExtra("movieid", movieid);
                startActivity(getCompleteInformation);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    public void checkForInternet()
    {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            if(howManyTimesTrueShown<1) {
                Toast.makeText(this, "Internet Connection established", Toast.LENGTH_SHORT).show();
                howManyTimesTrueShown=1;
            }
        }
        else
        {
            if(howManyTimesFalseShown<1) {
                Toast.makeText(this, "Internet Connection failed!", Toast.LENGTH_SHORT).show();
                howManyTimesFalseShown=1;
            }
        }
    }
}
