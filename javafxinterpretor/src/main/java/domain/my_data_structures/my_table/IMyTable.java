package domain.my_data_structures.my_table;
import java.util.Collection;
import java.util.Map;

public interface IMyTable<K,V> extends Map<K,V>{
    public IMyTable<K,V> getShallowCopy();
}
