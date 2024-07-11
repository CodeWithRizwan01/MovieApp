package com.example.movieapi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapi.ModelClasses.PopularMovies;
import com.example.movieapi.R;

public class MovieDetailActivity extends AppCompatActivity {
     ImageView ivPoster;
     TextView tvTitle, tvOverview, tvReleaseDate, tvLanguage, tvVoteAverage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        ivPoster = findViewById(R.id.iv_movie_poster);
        tvTitle = findViewById(R.id.tv_movie_title);
        tvOverview = findViewById(R.id.tv_movie_overview);
        tvReleaseDate = findViewById(R.id.tv_movie_release_date);
        tvLanguage = findViewById(R.id.tv_movie_language);
        tvVoteAverage = findViewById(R.id.tv_movie_vote_average);

        // Retrieve movie details from intent
        PopularMovies movie = getIntent().getParcelableExtra("movie");

        // Populate views with movie details
        if (movie != null) {
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path())
                    .placeholder(R.drawable.slider2)
                    .into(ivPoster);
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            tvReleaseDate.setText(movie.getRelease_date());
            tvLanguage.setText(movie.getOriginal_language());
            // Format vote average before setting it
            String formattedVoteAverage = formatVoteAverage(movie.getVote_average());
            tvVoteAverage.setText(formattedVoteAverage);        }
    }
    public String formatVoteAverage(float voteAverage) {
        return String.format("%.1f", voteAverage);
    }
}