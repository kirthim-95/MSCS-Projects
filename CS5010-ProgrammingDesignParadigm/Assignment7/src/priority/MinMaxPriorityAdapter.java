package priority;

import java.util.*;

class MinMaxPriorityAdapter<T> implements MinMaxPriorityQueue<T> {

    TreeMap<Integer, Queue<T>> treeMap;

    public MinMaxPriorityAdapter() {
        treeMap = new TreeMap<>();
    }

    @Override
    public void add(T val, int priority) {

        if(!treeMap.containsKey(priority)) {
            treeMap.put(priority, new LinkedList<>());
        }

        treeMap.get(priority).add(val);
    }

    @Override
    public T minPriorityItem() {

        if(treeMap.isEmpty()) {
            return null;
        }

        int minPriority = treeMap.firstKey();
        T result = treeMap.get(minPriority).poll();

        if(treeMap.get(minPriority).isEmpty()) {
            treeMap.remove(minPriority);
        }

        return result;
    }

    @Override
    public T maxPriorityItem() {

        if(treeMap.isEmpty()) {
            return null;
        }

        int maxPriority = treeMap.lastKey();
        T result = treeMap.get(maxPriority).poll();

        if(treeMap.get(maxPriority).isEmpty()) {
            treeMap.remove(maxPriority);
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Integer, Queue<T>> map: this.treeMap.entrySet()) {
            sb.append(map.getKey()).append(" : ").append(map.getValue()).append("\n");
        }
        return sb.toString();
    }
}
