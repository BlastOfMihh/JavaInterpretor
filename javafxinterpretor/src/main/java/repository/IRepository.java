package repository;

import exceptions.MyException;

import java.util.List;
import java.util.Vector;

public interface IRepository<T> {
    public Vector<T> getAll();
    public void add(T newItem);
    public void add(List<T> newItems);
    public void setProgramList(Vector<T> newItems);
    public void setProgramList(List<T> newItems);
    public void logRepo() throws MyException ;
}
