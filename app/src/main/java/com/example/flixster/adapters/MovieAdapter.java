package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;
import com.example.flixster.MovieDetailsActivity;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // context is used for inflating
    Context context;
    List<Movie> movies;

    //  MovieAdapter constructor
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // inflate a layout from XML and return inside the ViewHolder
    // take data from position and put into holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // populating data into item through ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // get the movie at position
        Movie movie = movies.get(position);

        // bind movie data into the ViewHolder
        holder.bind(movie);

    }

    // return total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // representation of the row (one movie)
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            // itemView's OnClickListener
            itemView.setOnClickListener(this);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;

            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            } else {
                // else default (poster image)
                imageUrl = movie.getPosterPath();
            }

//            Glide.with(context).load(imageUrl).into(ivPoster);
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? R.drawable.flicks_backdrop_placeholder : R.drawable.flicks_movie_placeholder)
                    .into(ivPoster);
        }

        @Override
        public void onClick(View v) {
            // get item position
            int position = getAdapterPosition();
            // ensure item is valid
            if (position != RecyclerView.NO_POSITION) {

                // get movie at position (only works bc class is not static)
                Movie movie = movies.get(position);

                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);

                // serialize the movie using parceler, using its short name as the key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));

                // show the activity
                context.startActivity(intent);


            }
        }
    }
}
