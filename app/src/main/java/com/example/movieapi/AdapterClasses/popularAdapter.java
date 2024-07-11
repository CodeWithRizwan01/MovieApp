package com.example.movieapi.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapi.Activities.MovieDetailActivity;
import com.example.movieapi.ModelClasses.PopularMovies;
import com.example.movieapi.R;

import java.util.ArrayList;
import java.util.List;

public class popularAdapter extends RecyclerView.Adapter<popularAdapter.viewHolder> {

    Context context;
    List<PopularMovies> moviesList;
    List<PopularMovies> moviesListFull; // Copy of full list for filtering

    public popularAdapter(Context context, List<PopularMovies> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
        this.moviesListFull = new ArrayList<>(moviesList);;
    }

    @NonNull
    @Override
    public popularAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_popular,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull popularAdapter.viewHolder holder, int position) {
    PopularMovies movie = moviesList.get(position);
        holder.tvMovieName.setText(getShortenedTitle(movie.getTitle()));
        holder.tvRating.setText(formatVoteAverage(movie.getVote_average()));

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" +movie.getPoster_path())
                .placeholder(R.drawable.slider2)
                .into(holder.ivImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movie", movie);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView ivImage;
        TextView tvMovieName,tvRating;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.iv_sample);
            tvMovieName = itemView.findViewById(R.id.tv_name);
            tvRating = itemView.findViewById(R.id.tv_rating);
        }
    }
    // Method to shorten the title
    public String getShortenedTitle(String title) {
        String[] words = title.split(" ");
        if (words.length > 2) {
            return words[0] + " " + words[1] + "..";
        } else {
            return title;
        }
    }
    // Method to format the vote average
    public String formatVoteAverage(float voteAverage) {
        return String.format("%.1f", voteAverage);
    }
    // Method to filter the list based on user input

    public void filterList(String searchText) {
        moviesList.clear();
        if (searchText.isEmpty()) {
            moviesList.addAll(moviesListFull);
        } else {
            searchText = searchText.toLowerCase().trim();
            for (PopularMovies movie : moviesListFull) {
                if (movie.getTitle().toLowerCase().contains(searchText)) {
                    moviesList.add(movie);
                }
            }
        }
        notifyDataSetChanged();
    }
}
