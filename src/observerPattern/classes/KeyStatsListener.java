package observerPattern.classes;

import java.util.*;

import observerPattern.interfaces.CacheListener;

/**
 * The KeyStatsListener collects key-level stats for cache operations.
 *
 * @param <K>
 * @param <V>
 */
public class KeyStatsListener<K, V> implements CacheListener<K, V> {

    private Map<K, Integer> hitsMap = new HashMap<>();
    private Map<K, Integer> updatesMap = new HashMap<>();
    private Map<K, Integer> missesMap = new HashMap<>();

    /**
     * Get the number of hits for a key.
     *
     * @param key the key
     * @return number of hits
     */
    public int getKeyHits(K key) {
        return hitsMap.get(key) == null ? 0 : hitsMap.get(key);
    }

    /**
     * Get the number of misses for a key.
     *
     * @param key the key
     * @return number of misses
     */
    public int getKeyMisses(K key) {
        return missesMap.get(key) == null ? 0 : missesMap.get(key);
    }

    /**
     * Get the number of updates for a key.
     *
     * @param key the key
     * @return number of updates
     */
    public int getKeyUpdates(K key) {
        return updatesMap.get(key) == null ? 0 : updatesMap.get(key);
    }

    public List<K> sortByValue(Map<K, Integer> map, int top) {
        List<Map.Entry<K, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort( list, new Comparator<Map.Entry<K, Integer>>() {
            public int compare(Map.Entry<K, Integer> entry1, Map.Entry<K, Integer> entry2) {
                return (entry2.getValue()).compareTo(entry1.getValue());
            }
        });

        List<K> sortedKeys = new LinkedList<>();

        Map.Entry<K, Integer> entry;
        for (int i = 0; i < top; i++) {
            entry = list.get(i);
            sortedKeys.add(entry.getKey());
        }

        return sortedKeys;
    }

    /**
     * Get the @top most hit keys.
     *
     * @param top number of top keys
     * @return the list of keys
     *
     */
    public List<K> getTopHitKeys(int top) {
        return sortByValue(hitsMap, top);

    }

    /**
     * Get the @top most missed keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopMissedKeys(int top) {
        return sortByValue(missesMap, top);
    }

    /**
     * Get the @top most updated keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopUpdatedKeys(int top) {
        return sortByValue(updatesMap, top);
    }

    @Override
    public void onPut(K key, V value) {
        int noUpdates = getKeyUpdates(key);
        noUpdates++;
        updatesMap.put(key, noUpdates);
    }

    @Override
    public void onMiss(K key) {
        int noMisses = getKeyMisses(key);
        missesMap.put(key, noMisses + 1);
    }

    @Override
    public void onHit(K key) {
        int noHits = getKeyHits(key);
        noHits++;
        hitsMap.put(key, noHits);
    }
}
