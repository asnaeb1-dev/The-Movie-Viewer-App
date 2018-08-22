package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by USER on 17-08-2018.
 */
//https://api.themoviedb.org/3/movie/343611?api_key=17d99bf38e7ffbebabfc4d9713b679a8&append_to_response=credits
public interface CastCrewInterfaceAPI {

    @GET("/3/movie/{movie_id}")
    Call<CastandCrew> getCastCrew(

            @Path("movie_id") int movieid,
            @Query("api_key") String apikey,
            @Query("append_to_response") String response
            );
}
