package domain.statement;

import domain.program_state.ProgramState;
import domain.expression.IExpression;
import domain.program_state.heap.Heap;
import domain.type.RefType;
import domain.value.IValue;
import domain.value.RefValue;
import exceptions.MyException;

public class HeapWriteStmt implements IStmt{
    String referenceName;
    IExpression expression;
    public HeapWriteStmt(String referenceName, IExpression expression){
        this.referenceName=referenceName;
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Heap heap=state.getHeap();
        var symTable= state.getSymTable();
        var referenceIValue=symTable.get(referenceName);
        if (!referenceIValue.getClass().equals(RefValue.class)){
            throw new MyException(String.format("%s is NOT a reference value", referenceIValue));
        }
        var reference = (RefValue)(referenceIValue);
        if (reference == null)
            throw new MyException("reference is nullll!!! in: "+this);
        if (! heap.containsKey(reference.getAdress()))
            throw new MyException("Adress not found!");
        IValue evaluatedExpression=expression.eval(symTable, heap);
        if (! reference.getLocationType().getClass().equals(evaluatedExpression.getType().getClass()))
            throw new MyException(String.format("Expression ins't of type %s", reference.getLocationType()));
        heap.put(reference.getAdress(), evaluatedExpression);
        return state;
    }

    @Override
    public String toString() {
        return "HeapWriteStmt{" +
                "referenceName='" + referenceName + '\'' +
                ", expression=" + expression +
                '}';
    }
}
