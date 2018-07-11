package odlare.com.movieapp1.util;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import odlare.com.movieapp1.BuildConfig;

public class NetworkUtility {

    public final static String TOP_RATED_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated";
    public final static String POPULAR_BASE_URL = "https://api.themoviedb.org/3/movie/popular";
    private final static String API_KEY_PARAM = "api_key";

    public static URL buildUrl(String baseUrl) {

        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY_MOVIE)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
