import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String _nickname;
    private String _password;
    private int _security_question_id;
    private String _security_question_answer;

    private Map<String, String> _login_credentials = new HashMap<String, String>();
    private Map<Integer, String> _security_qa_map = new HashMap<Integer, String>();

    private Map<Integer, Integer> _movieid_rates = new HashMap<Integer, Integer>();

    private static double _locXu;
    private static double _locYu;

    public String get_nickname() {
        return _nickname;
    }
    public void set_nickname(String _nickname) {
        this._nickname = _nickname;
    }

    public String get_password() {
        return _password;
    }
    public void set_password(String _password) {
        this._password = _password;
    }

    public int get_security_question_id() {
        return _security_question_id;
    }
    public void set_security_question_id(int _security_question_id) {
        this._security_question_id = _security_question_id;
    }

    public String get_security_question_answer() {
        return _security_question_answer;
    }
    public void set_security_question_answer(String _security_question_answer) {
        this._security_question_answer = _security_question_answer;
    }

    public void match_credentials(){
        _login_credentials.put(_nickname, _password);
    }
    public Map<String, String> get_login_credentials(){
        return _login_credentials;
    }

    public void match_security_qa(){
        _security_qa_map.put(_security_question_id, _security_question_answer);
    }
    public Map<Integer, String> get_security_qa_map() {
        return _security_qa_map;
    }

    public void addMovieWRate(int movieID, int rate){
        _movieid_rates.put(movieID, rate);
    }
    public Map<Integer, Integer> get_movieid_rates() {
        return _movieid_rates;
    }

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
