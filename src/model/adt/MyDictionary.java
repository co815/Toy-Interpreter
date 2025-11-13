package model.adt;

import java.util.Hashtable;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private final Hashtable<K, V> dict;

    public MyDictionary() {
        this.dict = new Hashtable<>();
    }

    @Override
    public void put(K key, V value) {
        this.dict.put(key, value);
    }

    @Override
    public boolean isDefined(K key) {
        return this.dict.containsKey(key);
    }

    @Override
    public V getValue(K key) {
        return this.dict.get(key);
    }

    @Override
    public Map<K, V> getContent() {
        return this.dict;
    }

    @Override
    public String toString() {
        return "[ " + this.dict + " ]";
    }
}
