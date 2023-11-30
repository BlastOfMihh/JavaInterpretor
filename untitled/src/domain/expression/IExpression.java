package domain.expression;

import domain.program_state.heap.Heap;
import domain.my_data_structures.my_table.IMyTable;
import domain.value.IValue;
import exceptions.MyException;

public interface IExpression {
    public IValue eval(IMyTable<String, IValue> symTable, Heap heap) throws MyException;
}
