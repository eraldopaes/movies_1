package odlare.com.movieapp1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import odlare.com.movieapp1.domain.Movie;
import odlare.com.movieapp1.domain.MovieAdapter;
import odlare.com.movieapp1.util.NetworkUtility;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);
        error = findViewById(R.id.connection_error);

        recyclerView = findViewById(R.id.rv_movie_list);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        recyclerView.setAdapter(movieAdapter);

        new FindMoviesByTopRated().execute(NetworkUtility.buildUrl(NetworkUtility.TOP_RATED_BASE_URL));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemSelected = item.getItemId();

        switch (itemSelected) {

            case R.id.popular_menu:
                new FindMoviesByTopRated().execute(NetworkUtility.buildUrl(NetworkUtility.POPULAR_BASE_URL));
                return true;
            case R.id.top_rated_menu:
                new FindMoviesByTopRated().execute(NetworkUtility.buildUrl(NetworkUtility.TOP_RATED_BASE_URL));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void preExecute() {
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void postExecute() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(int position) {
        Movie movie = movieAdapter.getMovie(position);

        Intent intent = new Intent(this, MovieDetailActivity.class);

        intent.putExtra("title", movie.getTitle());
        intent.putExtra("poster", movie.getPosterPath());
        intent.putExtra("overview", movie.getOverview());
        intent.putExtra("release", movie.getReleaseDate());
        intent.putExtra("vote", movie.getVoteAverage());
        intent.putExtra("language", movie.getOriginalLanguage());

        startActivity(intent);
    }

    class FindMoviesByTopRated extends AsyncTask<URL, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            error.setVisibility(View.INVISIBLE);
            preExecute();
        }

        @Override
        protected List<Movie> doInBackground(URL... urls) {

            List<Movie> movies = new ArrayList<>();

            try {

                String response = NetworkUtility.getResponseFromHttpUrl(urls[0]);
                JSONObject result = new JSONObject(response);
                JSONArray results = result.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {

                    JSONObject object = results.getJSONObject(i);

                    int[] genreIds = new int[object.getJSONArray("genre_ids").length()];

                    for (int j = 0; j < object.getJSONArray("genre_ids").length(); j++) {
                        genreIds[j] = object.getJSONArray("genre_ids").getInt(j);
                    }

                    Movie movie = Movie.builder()
                            .id(object.getLong("id"))
                            .voteCount(object.getInt("vote_count"))
                            .adult(object.getBoolean("video"))
                            .voteAverage(object.getDouble("vote_average"))
                            .title(object.getString("title"))
                            .popularity(object.getDouble("popularity"))
                            .posterPath(object.getString("poster_path"))
                            .originalLanguage(object.getString("original_language"))
                            .originalTitle(object.getString("original_title"))
                            .backdropPath(object.getString("backdrop_path"))
                            .adult(object.getBoolean("adult"))
                            .overview(object.getString("overview"))
                            .releaseDate(object.getString("release_date"))
                            .genreIds(genreIds)
                            .build();

                    movies.add(movie);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {

            if (movies == null || movies.isEmpty()) {
                progressBar.setVisibility(View.INVISIBLE);
                error.setVisibility(View.VISIBLE);
            } else {
                movieAdapter.setMovies(movies);
                postExecute();
            }
        }
    }
}
