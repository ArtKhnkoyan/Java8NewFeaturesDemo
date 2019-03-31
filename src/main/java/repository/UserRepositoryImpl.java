package repository;

import model.User;

import java.util.*;

public class UserRepositoryImpl implements DataRepository<User, String> {

    private long count = 0;
    private static final Map<String, User> USERS = new LinkedHashMap<>();

    @Override
    public long generateId() {
        return ++count;
    }

    @Override
    public void add(User data) {
        USERS.put(data.getEmail(), data);
    }

    @Override
    public Optional<User> get(String email) {
        return Optional.of(USERS.get(email));
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(USERS.values());
    }

    @Override
    public void update(User data) {
        delete(data.getEmail());
        add(data);
    }

    @Override
    public void delete(String email) {
        Optional<User> optionalUser = get(email);
        optionalUser.ifPresent(user -> USERS.remove(email));
    }

    @Override
    public void deleteAll() {
        USERS.clear();
    }

    public void printUsers() {
        USERS.values().stream()
                .filter(val -> val.getId() > 0)
                .forEach(System.out::println);
    }
}
