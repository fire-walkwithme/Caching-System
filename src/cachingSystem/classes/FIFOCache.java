package cachingSystem.classes;

import cachingSystem.FileCache;
import cachingSystem.interfaces.Cache;
import dataStructures.classes.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

public class FIFOCache<K, V> implements Cache<K, V> {


    private LinkedHashMap<K, V> cache;

    public FIFOCache() {
        cache = new LinkedHashMap<>();
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public V remove(K key) {
        return cache.remove(key);
    }

    @Override
    public void clearAll() {
        cache.clear();
    }

    @Override
    public Pair<K, V> getEldestEntry() {
        if (isEmpty()) {
            return null;
        }

        Map.Entry<K, V> eldest = cache.entrySet().iterator().next();

        return new Pair<K, V>(eldest.getKey(), eldest.getValue());
    }
}
