import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

public class kNearestNeighbor {
    private static boolean _fitted = false;
    private static LinkedHashMap<Integer, Double> _userMovieDistanceMap = new LinkedHashMap<Integer, Double>();

    // Calculate euclidean distance between 2 (x,y) points.
    // dX^2 + dY^2 = dD^2 (d: delta; D:distance)
    private static double euclidean_distance(double[] p1, double[] p2){
        return Math.pow(Math.pow(p1[0]-p2[0], 2) + Math.pow(p1[1]-p2[1], 2), 0.5);
    }

    public static int get_total_movie_num() {
        assert _fitted : "Firstly apply fit method -> fit(...);";
        return _userMovieDistanceMap.size();
    }

    // Calculate distances to User location in the projection map.
    public static void fit(LinkedHashMap<Integer, double[]> X, double[] UserLoc){
        TreeMap<Double, Integer> sorted_dists = new TreeMap<Double, Integer>();

        for(int key: X.keySet()){
            sorted_dists.put(euclidean_distance(X.get(key), UserLoc), key);
        }

        LinkedHashMap<Integer, Double> movDistmap = new LinkedHashMap<Integer, Double>();
        for(double distance: sorted_dists.keySet()){
            movDistmap.put(sorted_dists.get(distance), distance);
        }

        _userMovieDistanceMap = movDistmap;
        _fitted = true;
    }


    // Get v0'th to v1'th elements of Movie-Distance Map
    public static int[] recommend(int v0, int v1){
        assert _fitted : "Before recommendation, apply fit method -> fit(...);";
        assert v0 < v1 : "First index value should less than second value";
        assert v0 >= 0 : "First index value can't be zero";
        assert v1 <= _userMovieDistanceMap.size()-1 : "Max index value can't be greater than number of movie -1";

        int n_movie = v1-v0+1;
        int[] movie_ids = new int[n_movie];

        int movie_counter = 0;
        for(int movie_id: _userMovieDistanceMap.keySet()){
            movie_ids[movie_counter++] = movie_id;
            if(movie_counter == n_movie){
                break;
            }
        }

        return movie_ids;
    }

}
