package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA
/**
 * This activity shows all the information of the movie being viewed
 * LINK- https://api.themoviedb.org/3/movie/343611?api_key={api_key} ****Query with ID (343611 is the ID)
 * YOUTUBE-API-KEY-AIzaSyBTLY6BjIP4Onjv6UBA57e-btxwAS4ejvk
 * BASE-URL-https://api.themoviedb.org
 */
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//https://api.themoviedb.org/3/movie/343611?api_key={api_key} ****Query with ID (343611 is the ID)

//AIzaSyBTLY6BjIP4Onjv6UBA57e-btxwAS4ejvk
public class CompleteInformationActivity extends AppCompatActivity {

    private static String BASE_URL = "https://api.themoviedb.org";
    private static String APIKEY = "17d99bf38e7ffbebabfc4d9713b679a8";
    /*private static String YOUTUBE_API_KEY = "AIzaSyBTLY6BjIP4Onjv6UBA57e-btxwAS4ejvk";*/
    int movieID;
    String posterpath;
    String title, rating, hpg;
    CardView cardView;
    //YouTubePlayerView youTubePlayerView;

    TextView titleinfoText,
            languageinfoText,
            ratinginfoText,
            overviewinfoText,
            adultinfoText,
            releaseinfoText,
            genreText,
            taglineText,
            statusText,
            homepageText,
            budgetText,
            revenueText;

    int data;
    Bitmap favBitmap;

    ImageView backdropImageview;
    Boolean recommendation_showing = false,
            reviewlistshowing = false;

    ScrollView recommendationScrollView;
    LinearLayout scrollviewlinearlayout;
    ListView reviewsLISTView;

    ScrollView scrollViewx;

    ProgressBar loadingProgress;
    TextView loadingText;
    LinearLayout pictureslinearlayout;
    HorizontalScrollView imgScroll;

    ParseFile file;

    LinearLayout completeInfoLinearLayout;

    // ArrayList<String> youtubevideokeys = new ArrayList<>();

    private ArrayList<String> reviewnameArrayLIST = new ArrayList<>(),
            reviewArrayLIST = new ArrayList<>(),
            urlArrayLIST = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.auxillary_menu, menu);
        MenuItem recItem = menu.findItem(R.id.recommendations);
        ImageButton recShowHide = (ImageButton) recItem.getActionView();
        recShowHide.setPadding(0, 0, 20, 10);
        recShowHide.setImageResource(R.drawable.ic_recommendation_icon);

        if (Build.VERSION.SDK_INT >= 21) { //sdk version>=21
            recShowHide.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        } else {
            //sdk version<21
            recShowHide.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
        recShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recommendation_showing == false) {

                    //move all the existing UI to the left
                    recommendationScrollView.setVisibility(View.VISIBLE);
                    retroShowRecommendations(movieID);
                    recommendation_showing = true;
                } else {
                    recommendationScrollView.setVisibility(View.INVISIBLE);
                    recommendation_showing = false;
                }
            }
        });

        MenuItem listItem = menu.findItem((R.id.revealList));
        ImageButton reviewshowhide = (ImageButton) listItem.getActionView();
        reviewshowhide.setImageResource(R.drawable.ic_list_view_reveal);
        if (Build.VERSION.SDK_INT >= 21) { //sdk version>=21
            reviewshowhide.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        } else {
            //sdk version<21
            reviewshowhide.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
        reviewshowhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (reviewlistshowing == false) {
                    titleinfoText.animate().translationXBy(-2000f).setDuration(700);
                    languageinfoText.animate().translationXBy(-2000f).setDuration(700);
                    ratinginfoText.animate().translationXBy(-2000f).setDuration(700);
                    overviewinfoText.animate().translationXBy(-2000f).setDuration(700);
                    adultinfoText.animate().translationXBy(-2000f).setDuration(700);
                    releaseinfoText.animate().translationXBy(-2000f).setDuration(700);
                    genreText.animate().translationXBy(-2000f).setDuration(700);
                    taglineText.animate().translationXBy(-2000f).setDuration(700);
                    statusText.animate().translationXBy(-2000f).setDuration(700);
                    homepageText.animate().translationXBy(-2000f).setDuration(700);
                    budgetText.animate().translationXBy(-2000f).setDuration(700);
                    revenueText.animate().translationXBy(-2000f).setDuration(700);
                    scrollviewlinearlayout.animate().translationXBy(-2000f).setDuration(700);
                    reviewsLISTView.animate().translationXBy(-2000f).setDuration(700);
                    imgScroll.setVisibility(View.INVISIBLE);
                    scrollViewx.setVisibility(View.INVISIBLE);

                    reviewlistshowing = true;
                } else {
                    titleinfoText.animate().translationXBy(2000f).setDuration(700);
                    languageinfoText.animate().translationXBy(2000f).setDuration(700);
                    ratinginfoText.animate().translationXBy(2000f).setDuration(700);
                    overviewinfoText.animate().translationXBy(2000f).setDuration(700);
                    adultinfoText.animate().translationXBy(2000f).setDuration(700);
                    releaseinfoText.animate().translationXBy(2000f).setDuration(700);
                    genreText.animate().translationXBy(2000f).setDuration(700);
                    taglineText.animate().translationXBy(2000f).setDuration(700);
                    statusText.animate().translationXBy(2000f).setDuration(700);
                    homepageText.animate().translationXBy(2000f).setDuration(700);
                    budgetText.animate().translationXBy(2000f).setDuration(700);
                    revenueText.animate().translationXBy(2000f).setDuration(700);
                    scrollviewlinearlayout.animate().translationXBy(2000f).setDuration(700);
                    reviewsLISTView.animate().translationXBy(2000f).setDuration(700);
                    imgScroll.setVisibility(View.VISIBLE);
                    scrollViewx.setVisibility(View.VISIBLE);

                    reviewlistshowing = false;
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.favOption1:
                Intent goFavourites = new Intent(getApplicationContext(), FavouritesActivity.class);
                startActivity(goFavourites);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

                return true;

            default:
                return false;
        }
    }

    public void homePageFunction(View view) {
        if (hpg == null) {
            Log.i("check data", "entering false part");
            Toast.makeText(getApplicationContext(), "Sorry, No Links availaible", Toast.LENGTH_SHORT).show();
        } else {
            Log.i("check data", "entering true part");
            Intent webPage = new Intent(getApplicationContext(), WebViewActivity.class);
            Log.i("url1", hpg);
            webPage.putExtra("url", hpg);
            Log.i("url1", hpg);
            startActivity(webPage);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public void addFavourite(View view)
    {
        if(ParseUser.getCurrentUser()!=null) {

            completeInfoLinearLayout.setAlpha(0.3f);
            loadingProgress.setVisibility(View.VISIBLE);
            loadingText.setVisibility(View.VISIBLE);
            loadingText.setText("Adding to favourites. Please Wait...");

            ParseObject parseObject = new ParseObject("Favourites");
            parseObject.put("username", ParseUser.getCurrentUser().getUsername());
            //parseObject.put("poster", posterBM);
            parseObject.put("movieID", movieID);
            parseObject.put("title", title);
            parseObject.put("rating", rating);
            parseObject.put("posterPath",posterpath);

           /* if(favBitmap!=null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();//helps in transferring bitmap to parse server as objects
                favBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();//store in byte array after converting to byte
                file = new ParseFile("poster.png", byteArray);
                parseObject.put("poster", file);*/

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), title + " has been added to your favourites!", Toast.LENGTH_SHORT).show();
                            loadingProgress.setVisibility(View.INVISIBLE);
                            loadingText.setVisibility(View.INVISIBLE);
                            completeInfoLinearLayout.setAlpha(1f);
                        } else {
                            completeInfoLinearLayout.setAlpha(1f);
                            loadingProgress.setVisibility(View.INVISIBLE);
                            loadingText.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Something went wrong. Try again later!", Toast.LENGTH_SHORT).show();
                            loadingProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
        }
        else
        {
            Toast.makeText(this, "Sorry! You must login to use this feature!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_information);

        titleinfoText = findViewById(R.id.infotitleText);
        languageinfoText = findViewById(R.id.infoLanguageText);
        ratinginfoText = findViewById(R.id.ratingText);
        overviewinfoText = findViewById(R.id.infoOverviewText);
        adultinfoText = findViewById(R.id.infoAdultText);
        releaseinfoText = findViewById(R.id.inforeleaseDate);
        backdropImageview = findViewById(R.id.backdropimage);
        genreText = findViewById(R.id.genreText);
        loadingText = findViewById(R.id.completeInfoloadingText);
        budgetText = findViewById(R.id.budgetText);
        revenueText = findViewById(R.id.revenueText);

        completeInfoLinearLayout = findViewById(R.id.completeInfoLinearLayout);
        completeInfoLinearLayout.setVisibility(View.INVISIBLE);

        taglineText = findViewById(R.id.taglineText);
        statusText = findViewById(R.id.statusText);
        homepageText = findViewById(R.id.homepageText);
        pictureslinearlayout = findViewById(R.id.pictureslinearlayout);
        imgScroll = findViewById(R.id.imgscroll);
        cardView = findViewById(R.id.cardView);
        scrollViewx = findViewById(R.id.scrollView2);
        scrollViewx.setVisibility(View.INVISIBLE);

        recommendationScrollView = findViewById(R.id.recommendationScrollView);
        recommendationScrollView.setVisibility(View.INVISIBLE);
        scrollviewlinearlayout = findViewById(R.id.scrollviewlinearlayout);
        reviewsLISTView = findViewById(R.id.reviewList);
        reviewsLISTView.setTranslationX(2000f);
        loadingProgress = findViewById(R.id.completeInfoLoading);
        loadingProgress.setVisibility(View.VISIBLE);
        loadingText.setVisibility(View.VISIBLE);

        Intent movieIDgetter = getIntent();
        movieID = movieIDgetter.getIntExtra("movieid", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        completeInfoInterfaceAPI CompleteInfoInterfaceAPI = retrofit.create(completeInfoInterfaceAPI.class);

        Call<Example> call = CompleteInfoInterfaceAPI.getMovie(movieID, APIKEY);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                Example movie = response.body();
                title = movie.getTitle();
                titleinfoText.setText("Title- " + title);
                data = movie.getId();
                String language = movie.getOriginalLanguage();

                if (language.equals("hi")) {
                    languageinfoText.setText("Language- " + "Hindi");
                } else if (language.equals("en")) {
                    languageinfoText.setText("Language- " + "English");
                } else if (language.equals("es")) {
                    languageinfoText.setText("Language- " + "Spanish");
                } else if (language.equals("bn")) {
                    languageinfoText.setText("Language- " + "Bengali");
                } else if(language.equals("te")) {
                    languageinfoText.setText("Language- " + "Telegu");
                } else {
                    languageinfoText.setText("Language- " + language);
                }

                String tagline = movie.getTagline();
                if (tagline != null) {
                    taglineText.setText("Tagline- " + "'" + tagline + "'");
                } else {
                    taglineText.setText("Tagline- Sorry! Tagline isn't availaible!");
                }
                hpg = movie.getHomepage();
                if (hpg != null) {
                    homepageText.setText("Homepage- " + hpg);
                } else {
                    homepageText.setText("Homepage- " + "Sorry! No Data availaible");
                }
                String status = movie.getStatus();
                statusText.setText("Status- " + status);
                String budget = String.valueOf(movie.getBudget());
                if (budget.equals("0")) {
                    budgetText.setText("Budget- N/A");
                } else {
                    budgetText.setText("Budget- $" + budget);
                }
                String revenue = String.valueOf(movie.getRevenue());
                if (budget.equals("0")) {
                    revenueText.setText("Revenue- N/A");
                } else {
                    revenueText.setText("Revenue- $" + revenue);
                }
                String releaseDate = movie.getReleaseDate();
                releaseinfoText.setText("Release Date- " + releaseDate);

                String adult = String.valueOf(movie.getAdult());
                adultinfoText.setText("Adult- " +adult );

                rating = String.valueOf(movie.getVoteAverage());
                ratinginfoText.setText("Rating- " + rating);

                String overview = movie.getOverview();
                overviewinfoText.setText("Overview- " + overview);

                posterpath = movie.getPosterPath();
                if (posterpath != null) {
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + posterpath).into(backdropImageview);

                } else {
                    Toast.makeText(CompleteInformationActivity.this, "Sorry! NO POSTER AVAILABLE!", Toast.LENGTH_LONG).show();
                }
                String genreUI = "Genre- ";
                List<Example.Genre> genrelist = movie.getGenres();
                for (int i = 0; i < genrelist.size(); i++) {
                    Example.Genre genre = genrelist.get(i);
                    int genreid = genre.getId();
                    genreUI = genreUI + genre(genreid) + " ";
                }
                genreText.setText(genreUI);
                completeInfoLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("abhigyans error", t.getMessage());
            }
        });
        retroShowRecommendations(movieID);
        returnReviews(movieID);
        getMoviePictures(movieID);
        //youtubeVidoesgetter(movieID);
        //getCastInfo(movieID);


    }

    public String genre(int genreid) {
        String genre = "";
        switch (genreid) {
            case 23:
                genre = genre + "Action";
                break;
            case 12:
                genre = genre + "Adventure";
                break;
            case 16:
                genre = genre + "Animation";
                break;
            case 35:
                genre = genre + "Comedy";
                break;
            case 80:
                genre = genre + "Crime";
                break;
            case 99:
                genre = genre + "Documentary";
                break;
            case 18:
                genre = genre + "Drama";
                break;
            case 10751:
                genre = genre + "Family";
                break;
            case 14:
                genre = genre + "Fantasy";
                break;
            case 36:
                genre = genre + "History";
                break;
            case 27:
                genre = genre + "Horror";
                break;
            case 10402:
                genre = genre + "Music";
                break;
            case 9648:
                genre = genre + "Mystery";
                break;
            case 10749:
                genre = genre + "Romance";
                break;
            case 878:
                genre = genre + "Sci-fi";
                break;
            case 10770:
                genre = genre + "TV-movie";
                break;
            case 53:
                genre = genre + "Thriller";
                break;
            case 10752:
                genre = genre + "War";
                break;
            case 37:
                genre = genre + "Western";
                break;

            default:
                break;
        }
        return genre;
    }

    public void retroShowRecommendations(int movieID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecommendationIntefaceAPI recommendation = retrofit.create(RecommendationIntefaceAPI.class);
        Call<Dates> call = recommendation.getRecommendations(movieID, APIKEY);
        call.enqueue(new Callback<Dates>() {
            @Override
            public void onResponse(Call<Dates> call, Response<Dates> response) {

                Dates recommendations = response.body();
                List<Dates.Result> list = recommendations.getResults();
                for (int i = 0; i < list.size(); i++) {

                    Dates.Result recommend = list.get(i);
                    String posterURL = recommend.getPosterPath();
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setPadding(0,10,0,0);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + posterURL).into(imageView);
                    scrollviewlinearlayout.addView(imageView);
                }
            }

            @Override
            public void onFailure(Call<Dates> call, Throwable t) {
                Log.e("developer error", t.getMessage());
            }
        });
    }

    public void returnReviews(int IDMOVIE) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReviewInterfaceAPI reviewAPI = retrofit.create(ReviewInterfaceAPI.class);

        Call<ReviewInformation> call = reviewAPI.getReviews(IDMOVIE, APIKEY);
        call.enqueue(new Callback<ReviewInformation>() {
            @Override
            public void onResponse(Call<ReviewInformation> call, Response<ReviewInformation> response) {
                ReviewInformation reviews = response.body();
                List<ReviewInformation.Result> reviewList = reviews.getResults();
                for (int i = 0; i < reviewList.size(); i++) {
                    ReviewInformation.Result latestReviews = reviewList.get(i);
                    String name = latestReviews.getAuthor();
                    String content = latestReviews.getContent();
                    String URL = latestReviews.getUrl();
                    reviewnameArrayLIST.add("Author- " + name);
                    reviewArrayLIST.add(content);
                    urlArrayLIST.add("LINK- " + URL);

                }
                CustomReviewAdapter customAdapter = new CustomReviewAdapter(getApplicationContext(), reviewnameArrayLIST, reviewArrayLIST, urlArrayLIST);
                reviewsLISTView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<ReviewInformation> call, Throwable t) {

            }
        });
        loadingProgress.setVisibility(View.INVISIBLE);
        loadingText.setVisibility(View.INVISIBLE);
        scrollViewx.setVisibility(View.VISIBLE);
    }

    public void getMoviePictures(int MovieID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetImagesInterfaceAPI getImage = retrofit.create(GetImagesInterfaceAPI.class);

        Call<Backdrop> call = getImage.getBackdrops(MovieID, APIKEY);
        call.enqueue(new Callback<Backdrop>() {
            @Override
            public void onResponse(Call<Backdrop> call, Response<Backdrop> response) {

                Backdrop backdrop = response.body();
                List<Backdrop.Poster> filepaths = backdrop.getPosters();
                for (int i = 0; i < filepaths.size(); i++) {
                    Backdrop.Poster bck = filepaths.get(i);
                    String filepath = bck.getFilePath();
                    int length = bck.getHeight();
                    ImageView imageView = new ImageView(getApplicationContext());
                    if(length<=1080) {
                        Picasso.get().load("https://image.tmdb.org/t/p/w500" + filepath).into(imageView);
                        imageView.setPadding(4, 0, 4, 0);
                        pictureslinearlayout.addView(imageView);
                    }
                }
            }
            @Override
            public void onFailure(Call<Backdrop> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("abhigyans error", t.getMessage());
            }
        });
    }

    public void watchVideoFunction(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WatchVideosActivity.class);
        intent.putExtra("id",data);
        startActivity(intent);
        Log.i("data**************", String.valueOf(data));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
