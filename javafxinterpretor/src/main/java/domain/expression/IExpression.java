package domain.expression;

import domain.program_state.heap.IHeap;
import domain.my_data_structures.my_table.IMyTable;
import domain.type.IType;
import domain.value.IValue;
import exceptions.MyException;

public interface IExpression {
    public IValue eval(IMyTable<String, IValue> symTable, IHeap heap) throws MyException;
    IType typecheck(IMyTable<String, IType> typeEnvironment) throws MyException;
}
