package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by USER on 16-08-2018.
 */

public interface YearInterfaceAPI {
    //https://api.themoviedb.org/3/discover/movie?api_key=17d99bf38e7ffbebabfc4d9713b679a8&language=ta&sort_by=popularity.desc&year=2011

    @GET("/3/discover/movie")
    Call<MovieInformation> getMoviebyYear(

            @Query("api_key") String apikey,
            @Query("sort_by") String sort,
            @Query("year") int year
    );
}
