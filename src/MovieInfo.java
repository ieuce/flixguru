import java.net.URI;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MovieInfo {
    private static String _API_KEY;
    private static JSONObject _MOVIE_INFO;

    private static String[] _KEYS = {"original_language", "original_title", "vote_average", "vote_count", "popularity", "release_date", "adult", "overview"};
    private static String[] _PRINT_KEYS = {"Original Language", "Title", "Average Vote", "Total Vote Number", "Popularity", "Release Date", "Adult Content", "Overview"};

    public MovieInfo(String api_key){
        _API_KEY = api_key; 
    }

    public static void query(String imdb_id) {
        JSONParser parser = new JSONParser();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/find/"+imdb_id+"?api_key="+_API_KEY+"&language=en-US&external_source=imdb_id"))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject response_body = (JSONObject) parser.parse(response.body().toString());
            JSONArray movie_results_arr = (JSONArray) parser.parse(response_body.get("movie_results").toString());
            JSONObject movie_info = (JSONObject) parser.parse(movie_results_arr.get(0).toString());

            _MOVIE_INFO = movie_info;

        } catch (InterruptedException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void display(){
        System.out.println("**** MOVIE CARD ****");
        for(int i=0; i<_KEYS.length; i++){
            System.out.println(_PRINT_KEYS[i] + ": " + _MOVIE_INFO.get(_KEYS[i]));
        }
        System.out.println("**** **** *****");
    }

}
