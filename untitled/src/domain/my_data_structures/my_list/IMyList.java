package domain.my_data_structures.my_list;

public interface IMyList<E> {
    boolean add(E e);
    void add(int index, E element);
    int size();
    E get(int index);
}
