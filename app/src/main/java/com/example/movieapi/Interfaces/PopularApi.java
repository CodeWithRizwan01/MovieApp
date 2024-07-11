package com.example.movieapi.Interfaces;

import com.example.movieapi.Classes.MovieArray;
import com.example.movieapi.ModelClasses.PopularMovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PopularApi {
    @GET("movie/popular?api_key=56e296ead9743c5ef9d18ebccf926f80")
    Call <MovieArray> getMovies();
}
