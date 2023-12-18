package repository;
import exceptions.MyException;

import java.util.List;
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
        //return items;
        return (Vector<T>) items.clone();
    }
    public void setProgramList(Vector<T> newItems){
        items= (Vector<T>) newItems.clone();
    }
    public void setProgramList(List<T> newItems){
        items=new Vector<T>();
        add(newItems);
        //newItems.forEach(item->items.add(item));
    }
    public void add(T newItem){
        items.add(newItem);
    }
    public void add(List<T> newItems){
        newItems.forEach(item -> items.add(item));
    }

}
