package dataStructures.classes;

public class DoubleLinkedList<K, V> {

    private Node<K, V> head;
    private Node<K, V> tail;


    public DoubleLinkedList() {
        head = null;
        tail = null;
    }
    /**
     * Checks if the list is empty
     *
     * @return true if the list is empty and false otherwise
     */
    public boolean isEmpty(){
        return head == null;
    }
    /**
     * Adds a node at the beggining of the list.
     *@param newNode the node to be added
     */
    public void addFirst(Node<K, V> newNode) {

        newNode.setPrevious(null);
        newNode.setNext(head);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            head.setPrevious(newNode);
            head = newNode;
        }
    }
    /**
     * Removes a node from the list.
     *@param removeNode the node to be removed
     */
    public void remove(Node<K, V> removeNode) {
        if (removeNode.getPrevious() != null) {
            removeNode.getPrevious().setNext(removeNode.getNext());
        } else {
            head = removeNode.getNext();
        }
        if (removeNode.getNext() != null) {
            removeNode.getNext().setPrevious(removeNode.getPrevious());
        } else {
            tail = removeNode.getPrevious();
        }
    }
    /**
     * Returns the MRU element from the list - the head
     *@return head of the list
     */
    public Node<K, V> getHead() {
        return head;
    }
    /**
     * Sets the head of the list
     *@param head the new head of the list
     */
    public void setHead(Node<K, V> head) {
        this.head = head;
    }
    /**
     * Returns the LRU element from the list - the tail
     *@return tail of the list
     */
    public Node<K, V> getTail() {
        return tail;
    }

}
