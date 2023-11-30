package repository;

import exceptions.MyException;

import java.util.Vector;

public interface IRepository<T> {
    public Vector<T> getAll();
    public void add(T newItem);

    public void logRepo() throws MyException ;
}
