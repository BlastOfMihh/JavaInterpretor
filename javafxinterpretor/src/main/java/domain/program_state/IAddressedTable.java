package domain.program_state;

import domain.my_data_structures.my_table.IMyTable;

public interface IAddressedTable<T> extends IMyTable<Integer, T> {
    public int putWithAddress(T newEntry);

    public int getAvailableAdress();
    public void setMinAvailableAdress(int availableAdress);
}
