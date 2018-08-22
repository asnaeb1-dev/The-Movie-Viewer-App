package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by USER on 16-08-2018.
 */

public interface MovieLanguageInterfaceAPI {

    //https://api.themoviedb.org/3/discover/movie?api_key=17d99bf38e7ffbebabfc4d9713b679a8&with_original_language=ta&sort_by=popularity.desc

    @GET("/3/discover/movie")
    Call<MovieInformation> getMoviesByLanguage(

            @Query("api_key") String api_key,
            @Query("with_original_language") String language,
            @Query("sort_by") String sortorder
    );
}
