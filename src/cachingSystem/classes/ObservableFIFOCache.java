package cachingSystem.classes;
import dataStructures.classes.Pair;


/**
 * Class that adapts the FIFOCache class to the ObservableCache abstract class.
 */
public class ObservableFIFOCache<K, V> extends ObservableCache<K, V> {

    private FIFOCache<K, V> fifoCache;


    public ObservableFIFOCache() {
        fifoCache = new FIFOCache<>();
    }
    /**
     * Puts an entry to the cache, notifies the listener and clears the eldest entry if necessary.
     * @param key the key
     * @param value the value
     */
    @Override
    public void put(K key, V value) {
        fifoCache.put(key, value);
        getListener().onPut(key, value);
        clearStaleEntries();
    }
    /**
     * Removes an entry with a given key from the cache.
     * @param key the key
     * @return the value
     */
    @Override
    public V remove(K key) {
        return fifoCache.remove(key);
    }
    /**
     * Checks if the cache is empty.
     * @return true if cache is empty and false otherwise
     */
    @Override
    public boolean isEmpty() {
        return fifoCache.isEmpty();
    }
    /**
     * Return the eldest entry from the cache. The definition for the eldest entry varies from one
     * type of cache to another.
     *
     * @return the eldest entry
     */
    @Override
    public Pair<K, V> getEldestEntry() {
        return fifoCache.getEldestEntry();
    }
    /**
     * Clear all the elements from the cache.
     */
    @Override
    public void clearAll() {
        fifoCache.clearAll();
    }
    /**
     * Get the value associated with a key, or null if the key does not exist in the cache.
     * If the key doesn't exist, notifies an onMiss to the listener and an onHit otherwise.
     *
     * @param key the key too lookup
     * @return the associated value, or null
     */
    @Override
    public V get(K key) {
        V value = fifoCache.get(key);
        if(value == null) {
            getListener().onMiss(key);

        } else {
            getListener().onHit(key);
        }
        return value;
    }
    /**
     * The cache size is defined as the number of stored key-value pairs.
     *
     * @return the cache size
     */
    @Override
    public int size() {
        return fifoCache.size();
    }
}
