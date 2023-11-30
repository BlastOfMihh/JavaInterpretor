package domain.expression;

import domain.program_state.heap.Heap;
import domain.my_data_structures.my_table.IMyTable;
import domain.type.RefType;
import domain.value.IValue;
import domain.value.RefValue;
import exceptions.MyException;

public class ReadHeapExp implements IExpression{
    IExpression expression;
    public ReadHeapExp(IExpression expression){
        this.expression=expression;
    }
    @Override
    public IValue eval(IMyTable<String, IValue> symTable, Heap heap) throws MyException {
        IValue evaluatedExpression=expression.eval(symTable, heap);
        if (! evaluatedExpression.getClass().equals(RefType.class)){
            throw new MyException(String.format("Not %s a reference value", evaluatedExpression));
        }
        var reference = (RefValue)evaluatedExpression;
        if (! heap.contains(reference.getAdress()))
            throw new MyException("Adress not found!");
        return heap.get(reference.getAdress());
    }
}
