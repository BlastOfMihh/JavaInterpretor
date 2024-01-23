package domain.my_data_structures.my_list;

import java.util.List;

public interface IMyList<E> extends List<E> {
    boolean add(E e);
    void add(int index, E element);
    int size();
    E get(int index);
}
