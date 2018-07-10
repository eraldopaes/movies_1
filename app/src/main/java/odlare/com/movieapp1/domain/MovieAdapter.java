package odlare.com.movieapp1.domain;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import odlare.com.movieapp1.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private final MovieAdapterOnClickHandler mMovieAdapterOnClickHandler;

    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    private List<Movie> movies;

    public MovieAdapter(MovieAdapterOnClickHandler movieAdapterOnClickHandler, List<Movie> movies) {
        this.mMovieAdapterOnClickHandler = movieAdapterOnClickHandler;
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public Movie getMovie(int position) {
        return this.movies.get(position);
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {

        Movie movie = movies.get(position);

        Glide.with(holder.itemView).load(IMAGE_BASE_URL + movie.getPosterPath()).into(holder.imgMoviePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(int position);
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgMoviePoster;

        MovieAdapterViewHolder(View itemView) {
            super(itemView);

            imgMoviePoster = itemView.findViewById(R.id.movie_poster_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMovieAdapterOnClickHandler.onClick(getAdapterPosition());
                }
            });

        }
    }
}
