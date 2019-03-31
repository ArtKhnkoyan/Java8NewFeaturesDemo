package common;

import java.util.List;

public interface FuncIntForModelList<T, E> {
    List<T> getList(E e);
}
