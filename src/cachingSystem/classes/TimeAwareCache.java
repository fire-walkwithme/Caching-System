package cachingSystem.classes;

import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Pair;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * The TimeAwareCache offers the same functionality as the LRUCache, but also stores a timestamp for
 * each element. The timestamp is updated after each get / put operation for a key. This
 * functionality allows for time based cache stale policies (e.g. removing entries that are older
 * than 1 second).
 */
public class TimeAwareCache<K, V> extends LRUCache<K, V> {

    private CacheStalePolicy<K, V> cacheStalePolicy;
    private HashMap<K, Timestamp> keyTimestamps = new HashMap<>();
    private long millis;
    /**
     * Clears all the expired entries from the cache and returns the value of a given key
     *
     * @param key the key
     * @return the value
     */

    @Override
    public V get(K key) {

        clearStaleEntries();
        return super.get(key);
    }
    /**
     * Puts in map the timestamp when an entry is added to the cache
     * Adds an entry to the cache
     *
     * @param key the key
     * @param value the value
     **/
    @Override
    public void put(K key, V value) {
        keyTimestamps.put(key, new Timestamp(System.currentTimeMillis()));
        super.put(key, value);
    }

    /**
     * Get the timestamp associated with a key, or null if the key is not stored in the cache.
     *
     * @param key the key
     * @return the timestamp, or null
     */
    public Timestamp getTimestampOfKey(K key) {

        return keyTimestamps.get(key);
    }

    /**
     * Set a cache stale policy that should remove all elements older than @millisToExpire
     * milliseconds. This is a convenience method for setting a time based policy for the cache.
     *
     * @param millisToExpire the expiration time, in milliseconds
     */
    public void setExpirePolicy(long millisToExpire) {

        millis = millisToExpire;
        super.setStalePolicy((new CacheStalePolicy<K, V>() {
            @Override
            public boolean shouldRemoveEldestEntry(Pair<K, V> entry) {
                Timestamp entryTimestamp = getTimestampOfKey(entry.getKey());
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                long interval = currentTimestamp.getTime() - entryTimestamp.getTime();
                return interval > millis;
            }
        }));
    }
}
