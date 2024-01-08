package domain.expression;

import domain.program_state.heap.Heap;
import domain.my_data_structures.my_table.IMyTable;
import domain.type.IType;
import domain.value.IValue;
import exceptions.MyException;

public class VarExp implements IExpression{
    final String key;
    public VarExp(String key){
        this.key=key;
    }
    @Override
    public IValue eval(IMyTable<String, IValue> symTable, Heap heap) throws MyException {

        return symTable.get(key);
    }

    @Override
    public IType typecheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment.get(key);
    }

    @Override
    public String toString() {
        return "VariableExpression{" +
                "key='" + key + '\'' +
                '}';
    }
}
