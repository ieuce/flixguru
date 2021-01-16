import java.util.*;

public class CommandLineInterface {
    public static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static final int _rowMax = 20;
    public static final int _colMax = 127;

    public static void slideDown() {
        for (int row = 0; row < _rowMax; row++) {
            System.out.println(" ".repeat(_colMax));
        }
    }

    public static void invalidChoiceMenu(long time_in_milis){
        System.out.println("▚".repeat(_colMax));
        String menuString = "\n\n\n\n"+ANSI_RED+CoolTexts.invalid_choice+ANSI_RESET+"\n\n\n\n\n";
        System.out.println(menuString);
        System.out.println("▚".repeat(_colMax));

        try {
            Thread.sleep(time_in_milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void WelcomeMenu(long time_in_milis){
        System.out.println("▚".repeat(_colMax));
        String menuString = ANSI_CYAN+CoolTexts.centered_welcome_to+ANSI_RESET+"\n"+ANSI_RED+CoolTexts.centered_logo+ANSI_RESET;
        System.out.println(menuString);
        System.out.println("▚".repeat(_colMax));

        try {
            Thread.sleep(time_in_milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void LoginRegisterMenu(){
        System.out.println("▚".repeat(_colMax));
        String menuString = "\n"+ANSI_RED+CoolTexts.centered_logo+ANSI_RESET+"\n\n\n\n"+ANSI_YELLOW+CoolTexts.login_register_logo+ANSI_RESET;
        System.out.println(menuString);
        System.out.println("▚".repeat(_colMax));
        System.out.print("Choice: ");
    }

    public static void GetInfoMenu(String Text){
        System.out.println("▚".repeat(_colMax));
        String menuString = "\n\n\n\n\n\n"+ANSI_RED+CoolTexts.centered_logo+ANSI_RESET+"\n\n\n\n\n";
        System.out.println(menuString);
        System.out.println("▚".repeat(_colMax));
        System.out.printf("%s: ",Text);
    }

    public static void WriteText(String Text){
        System.out.println("▚".repeat(_colMax));
        String menuString = "\n\n\n\n\n\n"+ANSI_RED+CoolTexts.centered_logo+ANSI_RESET+"\n\n\n\n\n";
        System.out.println(menuString);
        System.out.println("▚".repeat(_colMax));
        System.out.print(Text);
    }

    public static void QAMenu(String ChoiceText, String PrintedText, int n_row_questions){
        System.out.println("▚".repeat(_colMax));
        String menuString = "\n\n"+ANSI_RED+CoolTexts.centered_logo+ANSI_RESET+"\n";
        System.out.println(menuString);
        System.out.print("\n".repeat(8-n_row_questions));
        System.out.println(ChoiceText);
        System.out.println("▚".repeat(_colMax));
        System.out.printf(PrintedText);
    }

    public static void WriteSuccessMenu(String s, long time_in_milis){
        System.out.println("▚".repeat(_colMax));
        String menuString = "\n\n\n\n\n\n"+ANSI_RED+CoolTexts.centered_logo+ANSI_RESET+"\n\n\n\n\n";
        System.out.println(menuString);
        System.out.println("▚".repeat(_colMax));
        System.out.print(ANSI_GREEN+s+ANSI_RESET);

        try {
            Thread.sleep(time_in_milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public static void WriteErrorMenu(String s, long time_in_milis){
        System.out.println("▚".repeat(_colMax));
        String menuString = "\n\n\n\n\n\n"+ANSI_RED+CoolTexts.centered_logo+ANSI_RESET+"\n\n\n\n\n";
        System.out.println(menuString);
        System.out.println("▚".repeat(_colMax));
        System.out.print(ANSI_RED+s+ANSI_RESET);

        try {
            Thread.sleep(time_in_milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public static void main(String[] args){
        final int n_components = 2;

        String data_dir = "F:\\PROJECTS\\PROCESSING\\suggest2me\\data\\ml-25m\\";
        String linksCSV_path = data_dir+"links.csv";
        String genome_tags_path = data_dir+"genome-tags.csv";
        String genome_scores_path = data_dir+"genome-scores.csv";

        Accounts accounts = new Accounts();
        User user = new User();

        Map<Integer, double[]> movieGenomeScore_map = Data.movieID_Encodings(genome_scores_path, genome_tags_path);

        Movies movie_info = new Movies("1801504cbd978f3bdb0ac97556f03dd9");
        movie_info.set_movieId_imdbId(linksCSV_path);

        long timeinmilis = 5000;
        long timeinmilis_short = Math.round(timeinmilis/3);

        String[] security_questions = {"(1) What is your elementary school teacher's name?","(2) What is your first pet's name?","(3) What is the name of your favorite childhood friend?"};
        int n_questions = security_questions.length;

        Scanner input = new Scanner(System.in);

        WelcomeMenu(timeinmilis);

        int choice0;
        boolean looper0 = true;
        boolean logged = false;
        while(looper0) {
            slideDown();
            LoginRegisterMenu();
            choice0 = input.nextInt();
            switch (choice0) {
                case 1:
                    String nickname_c1;
                    String password1_c1, password2_c1;
                    int security_question_id_c1;
                    String security_question_answer_c1;
                    int rate_c1;
                    int[] to_be_rated_movies10_c1 = {2959,7153,79132,356,109487,51167,6539,89745,74458,73881};

                    User register_user = new User();

                    // Nickname
                    boolean looper1 = true;
                    while (looper1) {
                        GetInfoMenu("Nickname");
                        nickname_c1 = input.next();
                        if(accounts.contains_nickname(nickname_c1)){
                            slideDown();
                            WriteErrorMenu("Nickname Already Taken !!", timeinmilis_short);
                        } else{
                            register_user.set_nickname(nickname_c1);
                            looper1 = false;
                        }
                        slideDown();
                    }

                    // Password
                    boolean looper3 = true;
                    while (looper3) {
                        GetInfoMenu("Password");
                        password1_c1 = input.next();
                        slideDown();
                        GetInfoMenu("Verify password");
                        password2_c1 = input.next();
                        if(password1_c1.equals(password2_c1)){
                            register_user.set_password(password1_c1);
                            slideDown();
                            WriteSuccessMenu("Password verified!", timeinmilis_short);
                            looper3 = false;
                        } else{
                            slideDown();
                            WriteErrorMenu("Passwords mismatched!", timeinmilis_short);
                        }
                        slideDown();
                    }
                    register_user.match_credentials();

                    // Question ID
                    StringBuilder qa_menu = new StringBuilder();
                    for(int i=0; i<n_questions; i++){
                        qa_menu.append(security_questions[i]);
                        if(i != n_questions-1){
                            qa_menu.append("\n");
                        }
                    }

                    boolean looper4 = true;
                    boolean valid_id = false;
                    while (looper4){
                        QAMenu(qa_menu.toString(), "Security Question ID: ", n_questions);
                        security_question_id_c1 = input.nextInt();
                        for(int i=1; i<=n_questions; i++){
                            if(security_question_id_c1 == i){
                                valid_id = true;
                                looper4 = false;
                                register_user.set_security_question_id(security_question_id_c1);
                            }
                        }
                        if(!valid_id){
                            slideDown();
                            invalidChoiceMenu(timeinmilis_short);
                        }
                        slideDown();
                    }

                    // Answer
                    GetInfoMenu("Security Question Answer");
                    security_question_answer_c1 = input.next();
                    register_user.set_security_question_answer(security_question_answer_c1);
                    register_user.match_security_qa();
                    System.out.println();

                    // Ratings
                    String imdbID;
                    boolean looper5;
                    for(int movie_id: to_be_rated_movies10_c1){
                        looper5 = true;
                        while (looper5) {
                            imdbID = movie_info.get_imdbID(movie_id);
                            movie_info.displayMenu(imdbID);
                            rate_c1 = input.nextInt();

                            if((rate_c1<0) || (rate_c1>10)){
                                slideDown();
                                invalidChoiceMenu(timeinmilis_short);
                            } else{
                                looper5 = false;
                                register_user.addMovieWRate(movie_id, rate_c1);
                            }
                        }
                        System.out.println();
                    }

                    // Register user
                    accounts.addUser(register_user);
                    WriteSuccessMenu("Registration complete. Please login.", timeinmilis_short);
                    slideDown();

                    break;
                case 2:
                    String nickname_c2 = "q";
                    String password_c2;
                    String security_question_answer_c2;

                    // Nickname
                    boolean looper6 = true;
                    while (looper6) {
                        GetInfoMenu("Nickname");
                        nickname_c2 = input.next();
                        if(accounts.contains_nickname(nickname_c2)){
                            looper6 = false;
                        } else{
                            slideDown();
                            WriteErrorMenu("Invalid Nickname", timeinmilis_short);
                        }
                        slideDown();
                    }

                    // Password
                    int wrong_pass_counter = 0;
                    boolean looper7 = true;
                    boolean security_qa = false;
                    while (looper7) {
                        if(wrong_pass_counter==3){
                            WriteErrorMenu("Too many wrong attempts. Please answer the security question.", timeinmilis_short);
                            security_qa = true;
                            looper7 = false;
                        } else {
                            GetInfoMenu("Password");
                            password_c2 = input.next();
                            if (accounts.check_password(nickname_c2, password_c2)) {
                                user = accounts.get_User(nickname_c2);
                                WriteSuccessMenu("You are logged!", timeinmilis_short);
                                looper7 = false;
                                logged = true;
                            } else {
                                wrong_pass_counter++;
                                slideDown();
                                WriteErrorMenu("Password is incorrect ! You have " + (3 - wrong_pass_counter) + " chance left", timeinmilis_short);
                            }
                            slideDown();
                        }
                    }

                    // Security QA
                    if(security_qa){
                        int q_id = accounts.get_security_questionID(nickname_c2);
                        QAMenu(security_questions[q_id-1], "Security Question Answer: ", 1);
                        security_question_answer_c2 = input.next();
                        if(accounts.check_security_qa(q_id, security_question_answer_c2)){
                            logged = true;
                            user = accounts.get_User(nickname_c2);
                            WriteSuccessMenu("You are logged", timeinmilis_short);
                        } else {
                            WriteErrorMenu("Login credentials are incorrect! Quit!", timeinmilis_short);
                        }
                    }

                    looper0 = false;
                    break;
                default:
                    invalidChoiceMenu(timeinmilis_short);
                    slideDown();
                    LoginRegisterMenu();
                    break;
            }
        }

        if(logged){
            slideDown();
            WriteText("You personal assistance flixguru is training. Please wait. This may take few minutes.");

            // Prepare PCA
            PrincipalComponentAnalysis pca = new PrincipalComponentAnalysis(n_components);
            double[][] encodings = Data.get_encodings(genome_scores_path, genome_tags_path);
            pca.fit(encodings);

            slideDown();
            WriteSuccessMenu("Training is finished.", timeinmilis);

            // Recommendation
            kNearestNeighbor knn = new kNearestNeighbor();

            LinkedHashMap<Integer, double[]> movieID_encodings = new LinkedHashMap<Integer, double[]>();
            for(int movieID: movieGenomeScore_map.keySet()){
                movieID_encodings.put(movieID, pca.transform(pca.expand_dims(movieGenomeScore_map.get(movieID), true))[0]);
            }


            int recommended_movieId;
            int choice;
            boolean looper8 = true;
            int movie_index = 10;
            int counter = 0;
            while (looper8){
                if(counter % 10 == 0){
                    // Localize User and Re-Calculate Distances
                    Map<Integer, Integer> movieid_rates = user.get_movieid_rates();
                    int[] rates              = new int[movieid_rates.keySet().size()];
                    List<double[]> movieLocs = new ArrayList<double[]>();
                    int c = 0;
                    for(int movieID: movieid_rates.keySet()){
                        rates[c++] = movieid_rates.get(movieID);
                        movieLocs.add(pca.transform(pca.expand_dims(movieGenomeScore_map.get(movieID), true))[0]);
                    }
                    user.localize(movieLocs, rates);
                    knn.fit(movieID_encodings, user.get_projected_location());
                    movie_index = movieid_rates.keySet().size();
                }

                recommended_movieId = knn.recommend(movie_index++,1)[0];
                movie_info.displayMenu(movie_info.get_imdbID(recommended_movieId));
                System.out.print("[-1 to quit]: ");
                choice = input.nextInt();
                if(choice == -1){
                    looper8 = false;
                    WriteSuccessMenu("Quit", timeinmilis_short);
                } else if((choice<0) || (choice>10)){
                    slideDown();
                    invalidChoiceMenu(timeinmilis_short);
                } else{
                    user.addMovieWRate(recommended_movieId, choice);
                }

                counter++;

                System.out.println();
            }
        }
    }
}
