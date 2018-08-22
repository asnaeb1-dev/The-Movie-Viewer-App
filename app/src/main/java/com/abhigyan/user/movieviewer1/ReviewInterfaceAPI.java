package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by USER on 12-08-2018.
 */

public interface ReviewInterfaceAPI {

    //https://api.themoviedb.org/3/movie/{movie_id}/reviews?api_key=<<api_key>>&language=en-US&page=1

    @GET("3/movie/{movie_id}/reviews")
    Call<ReviewInformation> getReviews(

            @Path("movie_id") int movieid,
            @Query("api_key") String apikey
    );
}
