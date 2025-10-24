package model.adt;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private final Stack<T> tail;

    public MyStack() {
        this.tail = new Stack<>();
    }


    @Override
    public void push(T element) {
        this.tail.push(element);
    }

    @Override
    public T pop() {
        return this.tail.pop();
    }

    @Override
    public boolean isEmpty() {
        return this.tail.isEmpty();
    }

    @Override
    public String toString() {
        Stack<T> newTail = new Stack<>();
        Stack<T> copyTail = new Stack<>();
        copyTail.addAll(this.tail);
        for(T elem : copyTail) {
            newTail.push(copyTail.pop());
        }
        String result = "[ ";
        for(T elem : newTail) {
            result += elem + ", ";
        }
        return result + " ]";
    }
}
