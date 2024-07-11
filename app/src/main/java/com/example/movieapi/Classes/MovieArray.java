package com.example.movieapi.Classes;

import com.example.movieapi.ModelClasses.PopularMovies;

public class MovieArray {
    private PopularMovies[] results;

    public PopularMovies[] getResults() {
        return results;
    }

    public MovieArray(PopularMovies[] results) {
        this.results = results;
    }
}
