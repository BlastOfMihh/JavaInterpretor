package domain.statement;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.expression.IExpression;
import domain.program_state.heap.IHeap;
import domain.type.IType;
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
        IHeap heap=state.getHeap();
        synchronized (heap){
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
        }
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        IType variableType=typeEnvironment.get(referenceName);
        IType expressionType= expression.typecheck(typeEnvironment);
        if(! variableType.getClass().equals(RefType.class))
            throw new MyException("Variable is not of refftype in :"+this);
        IType innerType=((RefType) variableType).getInnerType();
        if (! innerType.equals(expressionType))
            throw new MyException("Expression is not of the same type as the variable in:"+this);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "HeapWriteStmt{" +
                "referenceName='" + referenceName + '\'' +
                ", expression=" + expression +
                '}';
    }
}
