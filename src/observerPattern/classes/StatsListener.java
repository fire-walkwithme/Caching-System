package observerPattern.classes;

import observerPattern.interfaces.CacheListener;


/**
 * The StatsListener collects hit / miss / update stats for a cache.
 *
 * @param <K>
 * @param <V>
 */
public class StatsListener<K, V> implements CacheListener<K, V> {

    private int noHits = 0;
    private int noMisses = 0;
    private int noUpdates = 0;


    /**
     * Get the number of hits for the cache.
     *
     * @return number of hits
     */
    public int getHits() {
        return noHits;

    }

    /**
     * Get the number of misses for the cache.
     *
     * @return number of misses
     */
    public int getMisses() {
        return noMisses;
    }

    /**
     * Get the number of updates (put operations) for the cache.
     *
     * @return number of updates
     */
    public int getUpdates() {
        return noUpdates;
    }

    @Override
    public void onMiss(K key) {
        noMisses++;
    }

    @Override
    public void onHit(K key) {
        noHits++;
    }

    @Override
    public void onPut(K key, V value) {
        noUpdates++;
    }
}
