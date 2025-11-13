package model.adt;

import java.util.Map;

public interface MyIDictionary<K, V> {
    void put(K key, V value);
    boolean isDefined(K key);
    V getValue(K key);
    Map<K, V> getContent();
}
