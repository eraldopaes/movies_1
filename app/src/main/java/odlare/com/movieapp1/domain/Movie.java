package odlare.com.movieapp1.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movie {

    private int voteCount;
    private long id;
    private boolean video;
    private double voteAverage;
    private String title;
    private double popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private int[] genreIds;
    private String backdropPath;
    private boolean adult;
    private String overview;
    private String releaseDate;

}
