package com.abhigyan.user.movieviewer1;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
//DESIGNED BY ABHIGYAN RAHA

/**
 * Created by USER on 06-08-2018.
 */

public interface movieAPI {

     //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    @GET("/3/search/movie")
    Call <MovieInformation> getMovies(

            @Query("api_key") String apikey,
            @Query("query") String name
    );
}
