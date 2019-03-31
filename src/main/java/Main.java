import common.Commands;
import common.ModelNotFoundException;
import model.Post;
import model.User;
import repository.PostRepositoryImpl;
import repository.UserRepositoryImpl;

import java.util.Optional;
import java.util.Scanner;

public class Main implements Commands {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final PostRepositoryImpl POST = new PostRepositoryImpl();
    private static final UserRepositoryImpl USER = new UserRepositoryImpl();
    private static User admin;

    static {
        admin = User.builder()
                .name("Admin")
                .surname("Adminyan")
                .email("admin@gmail.com")
                .password("admin")
                .userType(User.UserType.ADMIN)
                .build();
    }

    public static void main(String[] args) {
        USER.add(admin);
        mainCommand();
    }

    private static void mainCommand() {
        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    login();
                    break;
                case POST_BY_CATEGORY:
                    printPostByCategory();
                    break;
                case POST_BY_USER:
                    printPostByUser();
                    break;
                default:
                    System.out.println("invalid command!!!");
            }
        }
    }

    private static void printPostByUser() {
        USER.printUsers();
        System.out.println("please select user email");
        String userEmailStr = SCANNER.nextLine();
        Optional<User> user = USER.get(userEmailStr);
        user.ifPresent(POST::printPostsByUser);
    }

    private static void printPostByCategory() {
        POST.printPosts();
        POST.printPostCategories();
        System.out.println("please select post category");
        String postCategoryStr = SCANNER.nextLine();
        Post.Category postCategory = Post.Category.valueOf(postCategoryStr.toUpperCase());
        POST.printPostsByCategory(postCategory);
    }

    private static void login() {
        System.out.println("Please input email,password");
        String userStr = SCANNER.nextLine();
        String[] userData = userStr.split(",");
        try {
            Optional<User> currentUser = USER.get(userData[0]);
            User user = currentUser.orElseThrow(() -> new ModelNotFoundException(String.format("User with %s email does not exist", userData[0])));
            if (user.getUserType() == User.UserType.ADMIN) {
                loginAsAdmin();
            } else {
                loginAsUser(user);
            }
        } catch (ModelNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loginAsUser(User currentUser) {
        new UserPage(POST, currentUser).goToUserPage();
    }

    private static void loginAsAdmin() {
        new AdminPage(POST, USER).goToAdminPage();
    }
}
