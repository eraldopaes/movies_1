package odlare.com.movieapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import odlare.com.movieapp1.domain.MovieAdapter;

public class MovieDetailActivity extends AppCompatActivity {

    TextView txtLanguage;
    TextView txtReleasedOn;
    TextView txtVoteAverage;
    TextView txtTitle;
    TextView txtOverview;
    ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String poster = intent.getStringExtra("poster");
        String overview = intent.getStringExtra("overview");
        String release = intent.getStringExtra("release");
        double vote = intent.getDoubleExtra("vote", 0.00);
        String language = intent.getStringExtra("language");

        imgPoster = findViewById(R.id.poster);
        txtTitle = findViewById(R.id.txtTitle);
        txtLanguage = findViewById(R.id.txtLanguage);
        txtVoteAverage = findViewById(R.id.txtVoteAverage);
        txtReleasedOn = findViewById(R.id.txtReleasedOn);
        txtOverview = findViewById(R.id.txtOverview);

        txtTitle.setText(title);
        txtLanguage.setText(language);
        txtVoteAverage.setText(String.valueOf(vote));
        txtReleasedOn.setText(release);
        txtOverview.setText(overview);

        Glide.with(this).load(MovieAdapter.IMAGE_BASE_URL + poster).into(imgPoster);
    }
}
