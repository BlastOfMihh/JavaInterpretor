package domain.my_data_structures.my_stack;

import java.util.List;
import java.util.Stack;

public interface IMyStack<E> extends List<E> {
    public E pop();
    public E push(E elem);
    boolean 	empty();
}
