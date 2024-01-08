package domain.program_state.sym_table;

import domain.my_data_structures.my_table.IMyTable;
import domain.my_data_structures.my_table.MyTable;
import domain.value.IValue;

public class SymTable extends MyTable<String, IValue> implements IMyTable<String,IValue>{
}
