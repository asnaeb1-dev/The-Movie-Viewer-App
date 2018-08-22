package com.abhigyan.user.movieviewer1;

//DESIGNED BY ABHIGYAN RAHA

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by USER on 10-08-2018.
 */

public interface completeInfoInterfaceAPI {

    //complete url- https://api.themoviedb.org/3/movie/343611?api_key=17d99bf38e7ffbebabfc4d9713b679a8

        @GET("/3/movie/{movie_id}")
        Call<Example> getMovie(

                @Path("movie_id") int id,
                @Query("api_key") String apikey
        );

}
