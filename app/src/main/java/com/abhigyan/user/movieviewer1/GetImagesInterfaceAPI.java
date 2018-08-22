package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by USER on 14-08-2018.
 */

public interface GetImagesInterfaceAPI {

    //https://api.themoviedb.org/3/movie/{movie_id}/images?api_key=<<api_key>>

    @GET("/3/movie/{movie_id}/images")
    Call<Backdrop> getBackdrops(

            @Path("movie_id") int movieid,
            @Query("api_key") String api
    );

}
