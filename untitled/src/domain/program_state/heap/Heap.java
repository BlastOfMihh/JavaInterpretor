package domain.program_state.heap;

import domain.my_data_structures.my_table.MyTable;
import domain.value.IValue;

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
}
