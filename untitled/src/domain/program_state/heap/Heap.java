package domain.program_state.heap;

import domain.my_data_structures.my_table.MyTable;
import domain.value.IValue;

import java.util.Map;

public class Heap extends MyTable<Integer, IValue>{
    private int availableAdress;
    public Heap(){
        super();
        availableAdress=1;
    }
    public int addEntry(IValue newEntry){
        this.put(availableAdress++, newEntry);
        return availableAdress-1;
    }

    public int getAvailableAdress(){
        return availableAdress;
    }
    public void setMinAvailableAdress(int availableAdress){
        this.availableAdress=Math.max(availableAdress, this.availableAdress);
    }
}
