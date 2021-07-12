package com.example.tft_flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.tft_flixster.DetailActivity;
import com.example.tft_flixster.R;
import com.example.tft_flixster.databinding.ItemMovieBinding;
import com.example.tft_flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // Get the movie at the position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView ivBackdrop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            tvOverview.setMovementMethod(new ScrollingMovementMethod());
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            ivBackdrop = itemView.findViewById(R.id.ivBackdrop);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl = back drop image
                imageUrl = movie.getBackdropPath();
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.flicks_backdrop_placeholder)
                        .fitCenter()
                        .transform(new RoundedCornersTransformation(50, 0))
                        .into(ivPoster);

                if (movie.getRating() > 5) {
                    ivBackdrop.setVisibility(View.VISIBLE);
                    //Set semitransparent backdrop as background of movie item with
                    Glide.with(context).load(movie.getBackdropPath())
                            .placeholder(R.drawable.flicks_backdrop_placeholder)
                            .into(ivBackdrop);
                } else {
                    ivBackdrop.setVisibility(View.GONE);
                }
            }
            else {
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.flicks_movie_placeholder)
                        .fitCenter()
                        .transform(new RoundedCornersTransformation(30, 0))
                        .into(ivPoster);

                if (movie.getRating() > 5) {
                    ivBackdrop.setVisibility(View.VISIBLE);
                    //Set semitransparent backdrop as background of movie item with
                    Glide.with(context).load(movie.getBackdropPath())
                            .placeholder(R.drawable.flicks_backdrop_placeholder)
                            .into(ivBackdrop);
                } else {
                    ivBackdrop.setVisibility(View.GONE);
                }
            }
            // ORIGINAL - Glide.with(context).load(imageUrl).into(ivPoster);

            // 1. Register click listener on the whole row
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}
