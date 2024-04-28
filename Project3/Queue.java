public interface Queue<Item> extends Iterable<Item> {
    
    boolean isEmpty();
    int size();
    void enqueue(Item item);
    Item dequeue();
}