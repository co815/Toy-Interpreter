package model.adt;

import java.util.List;

public interface MyIList<V> {
    void add(V value);
    List<V> getData();
}
