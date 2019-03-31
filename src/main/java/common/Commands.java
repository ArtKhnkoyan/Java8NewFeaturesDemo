package common;

public interface Commands {

    //main commands
    int EXIT = 0;
    int LOGIN = 1;
    int POST_BY_CATEGORY = 2;
    int POST_BY_USER = 3;

    //admin's commands
    int LOGOUT = 0;
    int ADD_USER = 1;
    int DELETE_USER = 2;
    int DELETE_USERS = 3;
    int DELETE_POST = 4;
    int UPDATE_USER = 5;
    int PRINT_USERS = 6;

    //user's commands
    int ADD_POST = 1;
    int UPDATE_MY_POST = 2;
    int DELETE_MY_POST = 3;
    int DELETE_MY_POSTS = 4;
    int PRINT_LAST_THREE_POSTS = 5;

    static void printMainCommands() {
        System.out.println("Please input " + EXIT + " for exit");
        System.out.println("Please input " + LOGIN + " for login");
        System.out.println("Please input " + POST_BY_CATEGORY + " for PRINT_POST_BY_CATEGORY");
        System.out.println("Please input " + POST_BY_USER + " for PRINT_POST_BY_USER");
    }

    static void printAdminCommands() {
        System.out.println("Please input " + LOGOUT + " for logout");
        System.out.println("Please input " + ADD_USER + " for ADD_USER");
        System.out.println("Please input " + DELETE_USER + " for DELETE_USER");
        System.out.println("Please input " + DELETE_USERS + " for DELETE_USERS");
        System.out.println("Please input " + DELETE_POST + " for DELETE_POST");
        System.out.println("Please input " + UPDATE_USER + " for UPDATE_USER");
        System.out.println("Please input " + PRINT_USERS + " for PRINT_USERS");
    }

    static void printUserCommands() {
        System.out.println("Please input " + LOGOUT + " for logout");
        System.out.println("Please input " + ADD_POST + " for ADD_POST");
        System.out.println("Please input " + UPDATE_MY_POST + " for UPDATE_MY_POST");
        System.out.println("Please input " + DELETE_MY_POST + " for DELETE_MY_POST");
        System.out.println("Please input " + DELETE_MY_POSTS + " for DELETE_MY_POSTS");
        System.out.println("Please input " + PRINT_LAST_THREE_POSTS + " for PRINT_LAST_THREE_POSTS");
    }
}
