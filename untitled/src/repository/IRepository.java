package repository;

import java.util.Vector;

public interface IRepository<T> {
    public Vector<T> getAll();
    public void add(T newItem);
}
