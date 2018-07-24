package cachingSystem.classes;

import dataStructures.classes.DoubleLinkedList;
import dataStructures.classes.Node;
import dataStructures.classes.Pair;

import java.util.HashMap;

/**
 * This cache is very similar to the FIFOCache, but guarantees O(1) complexity for the get, put and
 * remove operations.
 */
public class LRUCache<K, V> extends ObservableCache<K, V> {

    private DoubleLinkedList<K, V> doublyLinkedList;
    private HashMap<K, Node<K, V>> LRUCache;

    public LRUCache() {
        LRUCache = new HashMap<>();
        doublyLinkedList = new DoubleLinkedList<>();
    }

    public HashMap<K, Node<K, V>> getLRUCache() {
        return LRUCache;
    }
    /**
     * Remove a key if it exists in the cache.
     *
     * @param key the key to be removed
     * @return the value associated with the key, or null if the key was not in the cache
     */
    @Override
    public V remove(K key) {
        Node<K, V> temp = LRUCache.get(key);
        LRUCache.remove(key);
        doublyLinkedList.remove(temp);
        return temp.getValue();
    }
    /**
     * Insert a key value pair in the cache.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if(LRUCache.containsKey(key)) {
            Node<K, V> nodeToUpdate = LRUCache.get(key);
            nodeToUpdate.setValue(value);
            doublyLinkedList.remove(nodeToUpdate);
            doublyLinkedList.addFirst(nodeToUpdate);
        } else {
            Node<K, V> newNode = new Node<>(key, value);
            doublyLinkedList.addFirst(newNode);
            LRUCache.put(key, newNode);
            clearStaleEntries();
        }

        getListener().onPut(key, value);
    }
    /**
     * Get the value associated with a key, or null if the key does not exist in the cache.
     *
     * @param key the key too lookup
     * @return the associated value, or null
     */
    @Override
    public V get(K key) {
        if (LRUCache.containsKey(key)) {
            Node<K, V> temp = LRUCache.get(key);
            getListener().onHit(key);
            doublyLinkedList.remove(temp);
            doublyLinkedList.addFirst(temp);
            return temp.getValue();
        } else {
            getListener().onMiss(key);
        }
        return null;
    }
    /**
     * The cache size is defined as the number of stored key-value pairs.
     *
     * @return the cache size
     */
    @Override
    public int size() {
        return LRUCache.size();
    }
    /**
     * Clear all the elements from the cache.
     */
    @Override
    public void clearAll() {
        LRUCache.clear();
        doublyLinkedList.setHead(null);

    }
    /**
     * Return the eldest entry from the cache. The definition for the eldest entry varies from one
     * type of cache to another.
     *
     * @return the eldest entry
     */
    @Override
    public Pair<K, V> getEldestEntry() {

        if (isEmpty()) {
            return null;
        } else {
            Node<K, V> tail = doublyLinkedList.getTail();
            return new Pair<>(tail.getKey(), tail.getValue());
        }
    }
    /**
     * Tells whether or not the cache is empty.
     *
     * @return true if the cache has no keys stored, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return LRUCache.size() == 0;
    }


}
