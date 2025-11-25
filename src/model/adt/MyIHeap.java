package model.adt;

import java.util.Map;
import java.util.Set;

public interface MyIHeap<K, V> {
    int allocate(V value);
    void deallocate(K address);
    void update(K address, V value);
    V get(K address);
    boolean containsKey(K address);
    Map<K, V> getContent();
    void setContent(Map<K, V> content);
    Set<K> keySet();
}
