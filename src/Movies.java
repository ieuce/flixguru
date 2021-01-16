import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Movies {
    private String _API_KEY;
    private JSONObject _MOVIE_INFO;

    private String[] _KEYS = {"original_language", "original_title", "vote_average", "vote_count", "popularity", "release_date", "adult", "overview"};
    private String[] _PRINT_KEYS = {"Original Language", "Title", "Average Vote", "Total Vote Number", "Popularity", "Release Date", "Adult Content", "Overview"};

    private Map<Integer, String> _movieID_imdbID_map = new HashMap<Integer, String>();

    public Movies(String api_key){
        _API_KEY = api_key; 
    }

    public void query(String imdb_id) {
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
        }catch (IndexOutOfBoundsException e){
            _MOVIE_INFO = null;
        }
    }

    public void displayMenu(String imdbID){
        this.query(imdbID);
        if(_MOVIE_INFO != null) {
            String MovieCardRowString;
            List<String> rows = new ArrayList<String>();
            for (int i = 0; i < _KEYS.length; i++) {
                MovieCardRowString = _PRINT_KEYS[i] + ": " + _MOVIE_INFO.get(_KEYS[i]);
                Pattern p = Pattern.compile("\\G\\s*(.{1," + CommandLineInterface._colMax + "})(?=\\s|$)", Pattern.DOTALL);
                Matcher m = p.matcher(MovieCardRowString);

                while (m.find()) {
                    rows.add(m.group(1));
                }
            }

            String movie_card_string = "\tMOVIE CARD\t";
            System.out.println(CommandLineInterface.ANSI_RED + CoolTexts.centered_logo_prettyside + CommandLineInterface.ANSI_RESET);
            System.out.println("▚".repeat(Math.round((CommandLineInterface._colMax - movie_card_string.length()) / 2)) + movie_card_string + "▚".repeat(Math.round((CommandLineInterface._colMax - movie_card_string.length()) / 2)));
            for (String text : rows) {
                System.out.println(text);
            }
            System.out.println("▚".repeat(CommandLineInterface._colMax));
            System.out.print("Rate (0-10): ");
        }else{
            CommandLineInterface.slideDown();
        }
    }

    public void set_movieId_imdbId(String linksCSV_path) {
        String line;
        try {
            BufferedReader links_df = new BufferedReader(new FileReader(linksCSV_path));
            boolean first_line = true;
            while ((line = links_df.readLine()) != null) {
                if (first_line) {
                    first_line = false;
                } else {
                    String[] metadata = line.split(",");
                    _movieID_imdbID_map.put(Integer.parseInt(metadata[0]), "tt"+metadata[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get_imdbID(int movie_id){
        return _movieID_imdbID_map.get(movie_id);
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        String data_dir = "F:\\PROJECTS\\PROCESSING\\suggest2me\\data\\ml-25m\\";
        String linksCSV_path = data_dir+"links.csv";

        Movies movie_info = new Movies("1801504cbd978f3bdb0ac97556f03dd9");
        movie_info.set_movieId_imdbId(linksCSV_path);
        int[] to_be_rated_movies10 = {2959,7153,79132,356,109487,51167,6539,89745,74458,73881};
        for(int movie_id: to_be_rated_movies10) {
            String imdbID = movie_info.get_imdbID(movie_id);
            movie_info.displayMenu(imdbID);
            int rate = input.nextInt();
        }

    }

}
