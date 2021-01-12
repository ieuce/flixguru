import java.util.List;

public class User {
    private static double _locXu;
    private static double _locYu;

    // Sum up 1D array
    private static double sum(double[] X){
        double sum = 0;

        for (double x : X) {
            sum += x;
        }

        return sum;
    }

    // Localize user in transformed movies map
    public static void localize(List<double[]> mlocs, double[] rates){
        assert mlocs.size() == rates.length : "Number of movie positions and rates should be same";

        double weighted_sumX = 0;
        double weighted_sumY = 0;

        for(int i=0; i<mlocs.size(); i++){
            weighted_sumX += mlocs.get(i)[0]*rates[i];
            weighted_sumY += mlocs.get(i)[1]*rates[i];
        }

        double total_weights = sum(rates);
        weighted_sumX = (double) weighted_sumX/total_weights;
        weighted_sumY = (double) weighted_sumY/total_weights;

        _locXu = weighted_sumX;
        _locYu = weighted_sumY;
    }



}
