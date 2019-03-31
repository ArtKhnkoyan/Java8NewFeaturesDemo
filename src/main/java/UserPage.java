import common.Commands;
import common.ModelNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Post;
import model.User;
import repository.DataRepository;
import repository.PostRepositoryImpl;

import java.time.LocalDateTime;
import java.util.Scanner;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPage implements Commands {
    private static final Scanner SCANNER = new Scanner(System.in);
    private PostRepositoryImpl postRepositoryImpl;
    private User currentUser;

    void goToUserPage() {
        DataRepository.printTitle("USER");
        boolean isRun = true;
        while (isRun) {
            Commands.printUserCommands();
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
                case ADD_POST:
                    addPost();
                    break;
                case UPDATE_MY_POST:
                    updatePost();
                    break;
                case DELETE_MY_POST:
                    deleteMyPost();
                    break;
                case DELETE_MY_POSTS:
                    deleteMyPosts();
                    break;
                case PRINT_LAST_THREE_POSTS:
                    postRepositoryImpl.printLastThreePosts(currentUser);
                    break;
                default:
                    System.out.println("invalid command!");
            }
        }
    }


    private void addPost() {
        postRepositoryImpl.printPostCategories();
        System.out.println("please input post's title, text, postCategory");
        String postStr = SCANNER.nextLine();
        String[] postData = postStr.split(",");
        Post post = postBuilderForSave(postData);
        postRepositoryImpl.add(post);
    }

    private void deleteMyPost() {
        postRepositoryImpl.printPostsByUser(currentUser);
        System.out.println("please select by title");
        String title = SCANNER.nextLine();
        try {
            postRepositoryImpl.delete(title);
        } catch (ModelNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteMyPosts() {
        postRepositoryImpl.deleteAll(currentUser);
    }

    private void updatePost() {
        postRepositoryImpl.printPostsByUser(currentUser);
        System.out.println("please select by title");
        String title = SCANNER.nextLine();
        postRepositoryImpl.printPostCategories();
        System.out.println("please input post's title, text, postCategory");
        String postStr = SCANNER.nextLine();
        String[] postData = postStr.split(",");
        Post updatePost = postBuilderForSet(postData);
        postRepositoryImpl.update(updatePost, title);
    }

    private Post postBuilderForSave(String[] postData) {
        return Post.builder()
                .id(postRepositoryImpl.generateId())
                .title(postData[0].trim())
                .content(postData[1].trim())
                .category(Post.Category.valueOf(postData[2].toUpperCase()))
                .createdDate(LocalDateTime.now())
                .user(currentUser)
                .build();
    }

    private Post postBuilderForSet(String[] postData) {
        return Post.builder()
                .title(postData[0].trim())
                .content(postData[1].trim())
                .category(Post.Category.valueOf(postData[2].toUpperCase().trim()))
                .build();
    }
}
