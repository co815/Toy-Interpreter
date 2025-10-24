package model.adt;

import java.util.ArrayList;

public class MyList<V> implements MyIList<V> {
    private final ArrayList<V> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(V value) {
        this.list.add(value);
    }

    @Override
    public String toString() {
        String res = "[ ";
        for(V elem : this.list) {
            res += elem + " ";
        }
        res += "]";
        return res;
    }
}
