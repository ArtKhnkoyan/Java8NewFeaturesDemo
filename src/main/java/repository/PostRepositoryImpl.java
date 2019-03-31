package repository;

import common.FuncIntForModel;
import common.FuncIntForModelList;
import common.FuncInterface;
import common.ModelNotFoundException;
import model.Post;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class PostRepositoryImpl implements DataRepository<Post, String> {
    private long count = 0;
    private static final List<Post> POSTS = new ArrayList<>();

    static {
        POSTS.add(new Post());
    }

    @Override
    public long generateId() {
        return ++count;
    }

    @Override
    public void add(Post data) {
        POSTS.add(data);
    }

    @Override
    public Optional<Post> get(String title) {
        Post post = getPostUsingFuncInt(this::getPost, title);
        return Optional.ofNullable(post);
    }

    private Post getPostUsingFuncInt(FuncIntForModel<Post, String> funcModel, String title) {
        return funcModel.get(title);
    }

    private Post getPost(String title) {
        for (int i = 1; i < POSTS.size(); i++) {
            if (POSTS.get(i).getTitle().equals(title)) {
                return POSTS.get(i);
            }
        }
        return null;
    }


    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public List<Post> getAll(User currentUser) {
        return getPostUsingFuncInt(this::getPost, currentUser);
    }

    private List<Post> getPostUsingFuncInt(FuncIntForModelList<Post, User> funcModel, User currentUser) {
        return funcModel.getList(currentUser);
    }

    private List<Post> getPost(User currentUser) {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i < POSTS.size(); i++) {
            if (POSTS.get(i).getUser().equals(currentUser)) {
                posts.add(POSTS.get(i));
            }
        }
        return posts;
    }

    @Override
    public void update(Post data, String title) {
        Optional<Post> post = get(title);
        try {
            Post p = post.orElseThrow(() -> new ModelNotFoundException(String.format("Post with %s title does not exist", title)));
            p.setTitle(data.getTitle());
            p.setContent(data.getContent());
            p.setTitle(data.getTitle());
            p.setCategory(data.getCategory());
        } catch (ModelNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(String title) throws ModelNotFoundException {
        Optional<Post> post = get(title);
        Post p = post.orElseThrow(() -> new ModelNotFoundException(String.format("Post with %s title does not exist", title)));
        POSTS.remove(p);
    }

    public void printPostsByUser(User currentUser) {
        printUsingFuncInterface(this::printBeggingFirst, currentUser);
    }

    private void printUsingFuncInterface(FuncInterface<User> funcInterface, User currentUser) {
        funcInterface.print(currentUser);
    }

    private void printBeggingFirst(User currentUser) {
        for (int i = 1; i < POSTS.size(); i++) {
            if (POSTS.get(i).getUser().equals(currentUser)) {
                System.out.println(POSTS.get(i));
            }
        }
    }

    public void printPostCategories() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        joiner.add(Post.Category.NEWS.name());
        joiner.add(Post.Category.SOCIAL.name());
        joiner.add(Post.Category.SPORT.name());
        System.out.println("categories: " + joiner.toString());
    }

    public void printPosts() {
        POSTS.stream()
                .filter(post -> post.getId() > 1)
                .forEach(System.out::println);
    }

    @Override
    public void deleteAll(User user) {
        List<Post> posts = getAll(user);
        POSTS.removeAll(posts);
    }

    @Override
    public void deleteAll() {
        POSTS.clear();
    }

    public void printPostsByCategory(Post.Category category) {
        List<Post> posts = getPostUsingFuncInt(this::getPost, category);
        posts.stream()
                .filter(post -> post.getCategory().equals(category))
                .forEach(System.out::println);
    }

    private List<Post> getPostUsingFuncInt(FuncIntForModelList<Post, Post.Category> funcModel, Post.Category category) {
        return funcModel.getList(category);
    }

    private List<Post> getPost(Post.Category category) {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i < POSTS.size(); i++) {
            if (POSTS.get(i).getCategory().equals(category)) {
                posts.add(POSTS.get(i));
            }
        }
        return posts;
    }

    public void printLastThreePosts(User currentUser) {
        List<Post> posts = getPostUsingFuncInt(this::getAll, currentUser);
        posts.stream()
                .skip(posts.size() - 3)
                .forEach(System.out::println);
    }
}
