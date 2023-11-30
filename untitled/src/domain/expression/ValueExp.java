package domain.expression;

import domain.program_state.heap.Heap;
import domain.my_data_structures.my_table.IMyTable;
import domain.value.IValue;
import exceptions.MyException;

public class ValueExp implements IExpression{
    IValue value;
    public ValueExp(IValue value){
        this.value=value;
    }
    @Override
    public IValue eval(IMyTable<String, IValue> symTable, Heap heap) throws MyException {
        return value;
    }

    @Override
    public String toString() {
        return "ValueExpression{" +
                "value=" + value +
                '}';
    }
}
