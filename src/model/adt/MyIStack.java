package model.adt;

import java.util.List;

public interface MyIStack<T> {
    void push(T element);
    T pop();
    boolean isEmpty();
    List<T> getReversedData();
}
