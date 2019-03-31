package repository;

import common.ModelNotFoundException;
import model.User;

import java.util.List;
import java.util.Optional;

public interface DataRepository<T, E> {

    default long generateId(){return 1;};

    void add(T data);

    default Optional<T> get(E e1) {
        return Optional.empty();
    }

    List<T> getAll();

    default List<T> getAll(User user) {
        return null;
    }


    default void update(T data, E e) {
    }

    default void update(T data) {
    }

    void delete(E e) throws ModelNotFoundException;

    default void deleteAll(User e) {
    }

    void deleteAll();

    static void printTitle(String pageTitle) {
        System.out.println("Welcome to " + pageTitle + " page");
    }
}
