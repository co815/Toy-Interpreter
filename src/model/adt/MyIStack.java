package model.adt;

public interface MyIStack<T> {
    void push(T element);
    T pop();
    boolean isEmpty();
}
