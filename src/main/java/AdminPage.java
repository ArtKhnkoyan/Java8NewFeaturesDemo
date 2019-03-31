import common.Commands;
import common.ModelNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.User;
import repository.DataRepository;
import repository.PostRepositoryImpl;
import repository.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminPage implements Commands {
    private static final Scanner SCANNER = new Scanner(System.in);
    private PostRepositoryImpl postRepositoryImpl;
    private UserRepositoryImpl userRepositoryImpl;


    void goToAdminPage() {
        DataRepository.printTitle("ADMIN");
        boolean isRun = true;
        while (isRun) {
            Commands.printAdminCommands();
            int command;
            try {
                command = Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case DELETE_USER:
                    deleteUser();
                    break;
                case DELETE_USERS:
                    userRepositoryImpl.deleteAll();
                    break;
                case DELETE_POST:
                    deletePosts();
                    break;
                case UPDATE_USER:
                    updateUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                default:
                    System.out.println("invalid command!");
            }
        }
    }

    private void addUser() {
        System.out.println("please input name,surname,email,password");
        String userStr = SCANNER.nextLine();
        String[] userData = userStr.split(",");
        User user = User.builder()
                .id(userRepositoryImpl.generateId())
                .name(userData[0])
                .surname(userData[1])
                .email(userData[2])
                .password(userData[3])
                .userType(User.UserType.USER)
                .build();
        userRepositoryImpl.add(user);
    }

    private void deleteUser() {
        System.out.println("please input email");
        String email = SCANNER.nextLine();
        userRepositoryImpl.delete(email);
    }

    private void printUsers() {
        List<User> users = userRepositoryImpl.getAll();
        users.stream()
                .filter(user -> user.getId() > 0)
                .forEach(System.out::println);
    }

    private void deletePosts() {
        postRepositoryImpl.deleteAll();

    }

    private void updateUser() {
        userRepositoryImpl.printUsers();
        System.out.println("please input email");
        String email = SCANNER.nextLine();
        Optional<User> currentUser = userRepositoryImpl.get(email);
        try {
            User user = currentUser.orElseThrow(() -> new ModelNotFoundException(String.format("User with %s email does not exist", email)));
            System.out.println("please input name,surname");
            String userStr = SCANNER.nextLine();
            String[] userData = userStr.split(",");
            user.setName(userData[0]);
            user.setSurname(userData[1]);
            userRepositoryImpl.update(user);
        } catch (ModelNotFoundException e) {
            e.printStackTrace();
        }
    }
}
