package dataStructures.classes;

public class Node<K, V> {
    private K key;
    private V value;
    private Node<K, V> previous;
    private Node<K, V> next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        previous = null;
        next = null;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Node<K, V> getNext() {
        return next;
    }

    public Node<K, V> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<K, V> previous) {
        this.previous = previous;
    }

    public void setNext(Node<K, V> next) {
        this.next = next;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }
}

