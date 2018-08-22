package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WatchVideosActivity extends AppCompatActivity {

    private static String BASE_URL = "https://api.themoviedb.org";
    private static String APIKEY = "17d99bf38e7ffbebabfc4d9713b679a8";
    private static String YOUTUBE_API_KEY = "AIzaSyBTLY6BjIP4Onjv6UBA57e-btxwAS4ejvk";

    ArrayList<String> youtubevideokeys = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();

    ListView trailerList;
    ArrayAdapter arrayAdapter;
    LinearLayout linearLayout;
    WebView youtubeWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_videos);

        linearLayout = findViewById(R.id.linearLayout);
        trailerList = findViewById(R.id.trailerList);
        youtubeWebView = findViewById(R.id.youtubeWebView);

        Intent getIntent = getIntent();
        int movieID = getIntent.getIntExtra("id", 0);
        Log.i("id**********", String.valueOf(movieID));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YoutubeVideoInterfaceAPI getvid = retrofit.create(YoutubeVideoInterfaceAPI.class);

        Call<Videos> call = getvid.getVideos(movieID, APIKEY);
        call.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                Videos videos = response.body();
                List<Videos.Result> vid = videos.getResults();
                for (int i = 0; i < vid.size(); i++) {
                    //obtain youtube video keys and store in an arraylist
                    Videos.Result getitem = vid.get(i);
                    String videokey = getitem.getKey();
                    youtubevideokeys.add(videokey);
                    String name = getitem.getName();
                    nameList.add(name);
                }
                 arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,nameList);
                 trailerList.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("abhigyans error", t.getMessage());
            }
        });

        trailerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                youtubevideokeys.get(position);
                String url ="https://www.youtube.com/watch?v="+youtubevideokeys.get(position);
                youtubeWebView.getSettings().setJavaScriptEnabled(true);
                youtubeWebView.loadUrl(url);
            }
        });
    }
}
