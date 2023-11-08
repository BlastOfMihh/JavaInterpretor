package repository;
import java.util.Vector;

public class Repository<T> implements IRepository<T>{
    private Vector<T> items;
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
