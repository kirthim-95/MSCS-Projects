package priority;

public class MinMaxPriorityQueueImpl<T> implements MinMaxPriorityQueue<T> {

    MinMaxPriorityAdapter<T> adapter;

    public MinMaxPriorityQueueImpl() {
        adapter = new MinMaxPriorityAdapter<>();
    }

    @Override
    public void add(T val, int priority) {
        this.adapter.add(val, priority);
    }

    @Override
    public T minPriorityItem() {
        return adapter.minPriorityItem();
    }

    @Override
    public T maxPriorityItem() {
        return adapter.maxPriorityItem();
    }

    @Override
    public String toString() {
        return adapter.toString();
    }
}
