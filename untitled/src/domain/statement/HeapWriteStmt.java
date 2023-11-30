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
        if (!referenceIValue.getClass().equals(RefType.class)){
            throw new MyException(String.format("Not %s a reference value", referenceIValue));
        }
        var reference = (RefValue)heap.get(referenceIValue);
        if (! heap.contains(reference.getAdress()))
            throw new MyException("Adress not found!");
        IValue evaluatedExpression=expression.eval(symTable, heap);
        if (! reference.getLocationType().equals(evaluatedExpression.getType()))
            throw new MyException(String.format("Expression ins't of type %s", reference.getLocationType()));
        heap.put(reference.getAdress(), evaluatedExpression);
        return state;
    }
}
