package model.adt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyList<V> implements MyIList<V> {
    private final List<V> list;

    public MyList() {
        this.list = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void add(V value) {
        this.list.add(value);
    }

    @Override
    public List<V> getData() {
        return this.list;
    }

    @Override
    public String toString() {
        synchronized (list) {
            StringBuilder res = new StringBuilder("[ ");
            for (V elem : this.list) {
                res.append(elem).append(" ");
            }
            res.append("]");
            return res.toString();
        }
    }
}