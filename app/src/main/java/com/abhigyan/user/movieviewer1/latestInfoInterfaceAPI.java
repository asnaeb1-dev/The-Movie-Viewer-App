package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by USER on 11-08-2018.
 */

//https://api.themoviedb.org/3/movie/upcoming?api_key=17d99bf38e7ffbebabfc4d9713b679a8&language=en-US&page=1
public interface latestInfoInterfaceAPI {


    @GET("/3/movie/{category}")
    Call<Dates> getLatestMovie(

            @Path("category") String category,
            @Query("api_key") String apikey
    );


}
