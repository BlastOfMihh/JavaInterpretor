package domain.expression;

import domain.program_state.heap.Heap;
import domain.my_data_structures.my_table.IMyTable;
import domain.type.IType;
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
        if (! evaluatedExpression.getClass().equals(RefValue.class)){
            throw new MyException(String.format("%s is NOT a reference value in this %s", evaluatedExpression, this));
        } // redundant
        var reference = (RefValue)evaluatedExpression;
        if (! heap.containsKey(reference.getAdress()))
            throw new MyException(String.format("Adress not found! in %s", this));
        return heap.get(reference.getAdress());
    }

    @Override
    public IType typecheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        IType expressionType=expression.typecheck(typeEnvironment);
        if (! expressionType.getClass().equals(RefType.class)){
            throw new MyException(String.format("%s is NOT a reference value in this %s", expressionType, this));
        }
        IType innerType = ((RefType)expressionType).getInnerType();
        return innerType;
    }

    @Override
    public String toString() {
        return "ReadHeapExp{" +
                "expression=" + expression +
                '}';
    }
}
