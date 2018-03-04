package jkvillavo.co.com.rappiandroidtest.rest;

import jkvillavo.co.com.rappiandroidtest.rest.model.moviedetail.MovieDetail;
import jkvillavo.co.com.rappiandroidtest.rest.model.movielist.MoviesResponse;
import jkvillavo.co.com.rappiandroidtest.rest.model.movievideos.ResultVideosByMovieOut;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by JkVillavo12Col on 2/03/18.
 */

public interface ApiInterface {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpComingMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieDetail> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MoviesResponse> findMoviesByQuery(@Query("query") String query, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<ResultVideosByMovieOut> getVideosByMovie(@Path("id") int id, @Query("api_key") String apiKey);

}
