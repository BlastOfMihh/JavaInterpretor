package domain.my_data_structures.my_table;
import java.util.Hashtable;


public class MyTable<K,T> extends Hashtable<K,T> implements IMyTable<K,T>{

    public IMyTable<K,T> getShallowCopy(){
        MyTable<K,T> shallowCopy=new MyTable<>();
        shallowCopy.putAll(this);
        return shallowCopy;
    }
}

