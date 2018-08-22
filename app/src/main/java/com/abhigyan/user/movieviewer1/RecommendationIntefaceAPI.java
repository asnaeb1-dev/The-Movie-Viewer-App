package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by USER on 12-08-2018.
 */

public interface RecommendationIntefaceAPI {

    //https://api.themoviedb.org/3/movie/485942/recommendations?api_key=17d99bf38e7ffbebabfc4d9713b679a8

    @GET("3/movie/{movie_id}/recommendations")
    Call<Dates> getRecommendations(

            @Path("movie_id") int movieid,
            @Query("api_key") String apikey
    );
}
