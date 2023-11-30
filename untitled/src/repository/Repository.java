package repository;
import exceptions.MyException;

import java.util.Vector;

public abstract class Repository<T> implements IRepository<T>{
    protected Vector<T> items;
    public Repository(){
        this.items=new Vector<T>();
    }
    public Repository(Vector<T> items){
        this.items=items;
    }
    public Vector<T> getAll(){
        return items;
        //return (Vector<T>) items.clone();
    }
    public void add(T newItem){
        items.add(newItem);
    }

}
