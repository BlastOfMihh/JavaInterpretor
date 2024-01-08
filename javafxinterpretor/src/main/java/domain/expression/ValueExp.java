package domain.expression;

import domain.program_state.heap.Heap;
import domain.my_data_structures.my_table.IMyTable;
import domain.type.IType;
import domain.value.IValue;
import exceptions.MyException;

public class ValueExp implements IExpression{
    IValue value;
    public ValueExp(IValue value){
        this.value=value.deepCopy();
    }
    @Override
    public IValue eval(IMyTable<String, IValue> symTable, Heap heap) throws MyException {
        return value;
    }

    @Override
    public IType typecheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        return value.getType();
    }

    @Override
    public String toString() {
        return "ValueExpression{" +
                "value=" + value +
                '}';
    }
}
