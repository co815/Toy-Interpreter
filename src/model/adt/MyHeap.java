package model.adt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap<K, V> implements MyIHeap<K, V> {
    private final Map<K, V> heap;
    private final AtomicInteger freeAddress;

    public MyHeap() {
        this.heap = new ConcurrentHashMap<>();
        this.freeAddress = new AtomicInteger(1);
    }

    @Override
    public int allocate(V value) {
        int address = freeAddress.getAndIncrement();
        heap.put((K) Integer.valueOf(address), value);
        return address;
    }

    @Override
    public void deallocate(K address) {
        heap.remove(address);
    }

    @Override
    public void update(K address, V value) {
        heap.put(address, value);
    }

    @Override
    public V get(K address) {
        return heap.get(address);
    }

    @Override
    public boolean containsKey(K address) {
        return heap.containsKey(address);
    }

    @Override
    public Map<K, V> getContent() {
        return heap;
    }

    @Override
    public void setContent(Map<K, V> content) {
        heap.clear();
        heap.putAll(content);
    }

    @Override
    public Set<K> keySet() {
        return heap.keySet();
    }

    @Override
    public String toString() {
        return "[ " + heap + " ]";
    }
}