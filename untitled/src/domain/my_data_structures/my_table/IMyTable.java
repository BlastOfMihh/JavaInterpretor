package domain.my_data_structures.my_table;
import java.util.Collection;

public interface IMyTable<K,V> {
    V get(Object key);
    int size();
    V put(K key, V value);
    boolean containsKey(Object key);
    V remove(Object key);

    Collection<V> values();
}
