package domain.statement;

import domain.program_state.ProgramState;
import domain.expression.IExpression;
import domain.my_data_structures.my_table.IMyTable;
import domain.value.IValue;
import domain.value.RefValue;
import exceptions.MyException;
import domain.program_state.heap.Heap;

public class NewStatement implements IStmt{
    String varName;
    IExpression expression;
    NewStatement(String varName, IExpression expression){
        this.expression=expression;
        this.varName=varName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Heap heap = state.getHeap();
        IMyTable<String, IValue> symTable=state.getSymTable();
        if (!(symTable.containsKey(varName)))
            throw new MyException(String.format("%s does not exist!!", varName));
        if (! (symTable.get(varName) instanceof RefValue))
            throw new MyException(String.format("Not the same type", varName));
        int newAdress=heap.addEntry(expression.eval(symTable, heap));
        RefValue reference=(RefValue)symTable.get(varName);
        IValue evaluatedExpression=expression.eval(symTable, heap);
        if (! reference.getLocationType().equals(evaluatedExpression.getType())){
            throw new MyException(String.format("Inner type not equal", varName));
        }
        reference.setAdress(newAdress);
        return state;
    }
}
