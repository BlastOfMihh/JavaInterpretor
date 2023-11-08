package domain.my_stack;

public interface IMyStack<E> {
    public E pop();
    public E push(E elem);
    boolean 	empty();
}
