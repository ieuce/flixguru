import java.util.List;
import java.util.Scanner;

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

    public static void slideDown() {
        for (int row = 0; row < _rowMax; row++) {
            System.out.println(" ".repeat(_colMax));
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

    public static void QAMenu(String ChoiceText, int n_row_questions){
        System.out.println("▚".repeat(_colMax));
        String menuString = "\n\n"+ANSI_RED+CoolTexts.centered_logo+ANSI_RESET+"\n";
        System.out.println(menuString);
        System.out.print("\n".repeat(8-n_row_questions));
        System.out.println(ChoiceText);
        System.out.println("▚".repeat(_colMax));
        System.out.printf("Security Question ID: ");
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
        String data_dir = "F:\\PROJECTS\\PROCESSING\\suggest2me\\data\\ml-25m\\";
        String linksCSV_path = data_dir+"links.csv";

        Accounts accounts = new Accounts();

        Movies movie_info = new Movies("1801504cbd978f3bdb0ac97556f03dd9");
        movie_info.set_movieId_imdbId(linksCSV_path);

        long timeinmilis = 5000;
        long timeinmilis_short = Math.round(timeinmilis/3);

        Scanner input = new Scanner(System.in);

        WelcomeMenu(timeinmilis);

        int choice0;
        boolean looper0 = true;
        while(looper0) {
            slideDown();
            LoginRegisterMenu();
            choice0 = input.nextInt();
            switch (choice0) {
                case 1:
                    String nickname;
                    String password1, password2;
                    int security_question_id;
                    String security_question_answer;
                    int rate;
                    int[] to_be_rated_movies10 = {2959,7153,79132,356,109487,51167,6539,89745,74458,73881};

                    User user = new User();

                    // Nickname
                    boolean looper1 = true;
                    while (looper1) {
                        GetInfoMenu("Nickname");
                        nickname = input.next();
                        if(accounts.contains_nickname(nickname)){
                            slideDown();
                            WriteErrorMenu("Nickname Already Taken !!", timeinmilis_short);
                        } else{
                            user.set_nickname(nickname);
                            looper1 = false;
                        }
                        slideDown();
                    }

                    // Password
                    boolean looper3 = true;
                    while (looper3) {
                        GetInfoMenu("Password");
                        password1 = input.next();
                        slideDown();
                        GetInfoMenu("Verify password");
                        password2 = input.next();
                        if(password1.equals(password2)){
                            user.set_password(password1);
                            slideDown();
                            WriteSuccessMenu("Password verified!", timeinmilis_short);
                            looper3 = false;
                        } else{
                            slideDown();
                            WriteErrorMenu("Passwords mismatched!", timeinmilis_short);
                        }
                        slideDown();
                    }
                    user.match_credentials();

                    // Question ID
                    String qa_menu = "(1) What is your elementary school teacher's name?\n(2) What is your first pet's name?\n(3) What is the name of your favorite childhood friend?";
                    int n_questions = 3;
                    boolean looper4 = true;
                    boolean valid_id = false;
                    while (looper4){
                        QAMenu(qa_menu, n_questions);
                        security_question_id = input.nextInt();
                        for(int i=1; i<=n_questions; i++){
                            if(security_question_id == i){
                                valid_id = true;
                                looper4 = false;
                                user.set_security_question_id(security_question_id);
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
                    security_question_answer = input.next();
                    user.set_security_question_answer(security_question_answer);
                    user.match_security_qa();
                    slideDown();

                    // Ratings
                    String imdbID;
                    boolean looper5;
                    for(int movie_id: to_be_rated_movies10){
                        looper5 = true;
                        while (looper5) {
                            imdbID = movie_info.get_imdbID(movie_id);
                            movie_info.displayMenu(imdbID);
                            rate = input.nextInt();

                            if((rate<0) || (rate>10)){
                                slideDown();
                                invalidChoiceMenu(timeinmilis_short);
                            } else{
                                looper5 = false;
                                user.addMovieWRate(movie_id, rate);
                            }

                            slideDown();
                        }
                    }

                    // Register user
                    accounts.addUser(user);
                    WriteSuccessMenu("Registration complete. Please login.", timeinmilis_short);
                    slideDown();

                    break;
                case 2:
                    System.out.println("Forwarding to login menu");
                    GetInfoMenu("Username");
                    GetInfoMenu("Password");
                    looper0 = false;
                    break;
                default:
                    invalidChoiceMenu(timeinmilis_short);
                    slideDown();
                    LoginRegisterMenu();
                    break;
            }
        }


    }
}
