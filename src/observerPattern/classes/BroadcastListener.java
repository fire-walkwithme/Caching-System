package observerPattern.classes;

import observerPattern.interfaces.CacheListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The BroadcastListener broadcasts cache events to other listeners that have been added to it.
 */
public class BroadcastListener<K, V> implements CacheListener<K, V> {

    private List<CacheListener<K, V>> broadcastList = new ArrayList<>();
    /**
     * Add a listener to the broadcast list.
     *
     * @param listener the listener
     */
    public void addListener(CacheListener<K, V> listener) {
        broadcastList.add(listener);
    }

    @Override
    public void onHit(K key) {
        for(CacheListener<K, V> cacheListener : broadcastList) {
            cacheListener.onHit(key);
        }
    }

    @Override
    public void onMiss(K key) {
        for(CacheListener<K, V> cacheListener : broadcastList) {
            cacheListener.onMiss(key);
        }

    }

    @Override
    public void onPut(K key, V value) {
        for(CacheListener<K, V> cacheListener : broadcastList) {
            cacheListener.onPut(key, value);
        }

    }
}
