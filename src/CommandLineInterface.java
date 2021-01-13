import java.util.Scanner;

public class CommandLineInterface {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
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

    private static final int _rowMax = 20;
    private static final int _colMax = 127;

    public static void invalidChoiceMenu(long time_in_milis){
        System.out.println("-".repeat(_colMax));
        String menuString = "\n\n\n\n"+ANSI_RED+CoolTexts.invalid_choice+ANSI_RESET+"\n\n\n\n\n";
        System.out.println(menuString);
        System.out.println("-".repeat(_colMax));

        try {
            Thread.sleep(time_in_milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void slideDown(){
        try {
            for(int row=0; row<_rowMax; row++) {
                System.out.println(" ".repeat(_colMax));
                Thread.sleep(5);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void WelcomeMenu(long time_in_milis){
        System.out.println("-".repeat(_colMax));
        String menuString = ANSI_CYAN+CoolTexts.centered_welcome_to+ANSI_RESET+"\n"+ANSI_RED+CoolTexts.centered_logo+ANSI_RESET;
        System.out.println(menuString);
        System.out.println("-".repeat(_colMax));

        try {
            Thread.sleep(time_in_milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void LoginRegisterMenu(){
        System.out.println("-".repeat(_colMax));
        String menuString = "\n"+ANSI_RED+CoolTexts.centered_logo+ANSI_RESET+"\n\n\n\n"+ANSI_YELLOW+CoolTexts.login_register_logo+ANSI_RESET;
        System.out.println(menuString);
        System.out.println("-".repeat(_colMax));
        System.out.print("Choice: ");
    }



    public static void main(String[] args){
        long timeinmilis = 1500;
        Scanner input = new Scanner(System.in);

        WelcomeMenu(timeinmilis);
        slideDown();
        LoginRegisterMenu();

        int choice0;
        boolean looper0 = true;

        while(looper0) {
            choice0 = input.nextInt();
            switch (choice0) {
                case 1:
                    System.out.println("Forwarding to register menu");
                    looper0 = false;
                    break;
                case 2:
                    System.out.println("Forwarding to login menu");
                    looper0 = false;
                    break;
                default:
                    invalidChoiceMenu(Math.round(timeinmilis*1.25));
                    slideDown();
                    LoginRegisterMenu();
                    break;
            }
        }


    }
}
