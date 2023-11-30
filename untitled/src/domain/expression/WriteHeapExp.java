package domain.expression;

import domain.program_state.heap.Heap;
import domain.my_data_structures.my_table.IMyTable;
import domain.type.RefType;
import domain.value.IValue;
import domain.value.RefValue;
import exceptions.MyException;

public class WriteHeapExp implements IExpression{
    IExpression expression;
    IValue referenceIValue;
    public WriteHeapExp(IValue referenceIValue, IExpression expression){
        this.expression=expression;
        this.referenceIValue=referenceIValue;
    }
    @Override
    public IValue eval(IMyTable<String, IValue> symTable, Heap heap) throws MyException {
        if (!referenceIValue.getClass().equals(RefType.class)){
            throw new MyException(String.format("Not %s a reference value", referenceIValue));
        }
        var reference = (RefValue)expression;
        if (! heap.contains(reference.getAdress()))
            throw new MyException("Adress not found!");
        IValue evaluatedExpression=expression.eval(symTable, heap);
        if (! reference.getLocationType().equals(evaluatedExpression.getType()))
            throw new MyException(String.format("Expression ins't of type %s", reference.getLocationType()));
        heap.put(reference.getAdress(), evaluatedExpression);
        return heap.get(reference.getAdress());
    }
}
