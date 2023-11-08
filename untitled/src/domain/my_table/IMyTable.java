package domain.my_table;

public interface IMyTable<K,V> {
    V get(Object key);
    int size();
    V put(K key, V value);
    boolean containsKey(Object key);
}
