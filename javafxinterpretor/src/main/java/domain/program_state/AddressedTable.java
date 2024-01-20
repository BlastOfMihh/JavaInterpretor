package domain.program_state;

import domain.my_data_structures.my_table.MyTable;

public class AddressedTable<T> extends MyTable<Integer, T> {
    private int availableAdress;
    public AddressedTable(){
        super();
        availableAdress=1;
    }
    public int putWithAdress(T newEntry){
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

