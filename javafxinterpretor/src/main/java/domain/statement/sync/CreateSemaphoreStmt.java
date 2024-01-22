package domain.statement.sync;

import domain.expression.IExpression;
import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.program_state.heap.IHeap;
import domain.program_state.semaphore_table.ISemaphoreTable;
import domain.statement.IStmt;
import domain.type.IType;
import domain.type.IntType;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.MyException;
import kotlin.Pair;

import java.util.ArrayList;

public class CreateSemaphoreStmt implements IStmt {
    String varName;
    IExpression expression;
    public CreateSemaphoreStmt(String varName, IExpression expression){
        this.varName=varName;
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyTable<String,IValue> symTable=state.getSymTable();
        IHeap heap=state.getHeap();
        ISemaphoreTable semaphoreTable= state.getSemaphoreTable();
        Integer number=((IntValue)expression.eval(symTable, heap)).getValue();
        synchronized (semaphoreTable){
            Integer address=semaphoreTable.putWithAddress(new Pair<>(number, new ArrayList<>()));
            if (symTable.containsKey(varName))
                symTable.put(varName, new IntValue(address));
            else throw new MyException(String.format("Runtime error: %s does not exist in symTable", varName));
        }
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        if (!expression.typecheck(typeEnvironment).equals(new IntType()))
            throw new MyException(String.format("Expression %s should evaluate to Int in %s",expression, this));
        if(!typeEnvironment.containsKey(varName))
            throw new MyException(String.format("RUNTIME ERROR: %s is not in the symTable in %s",varName, this));
        if (!typeEnvironment.get(varName).equals(new IntType()))
            throw new MyException(String.format("RUNTIMEE ERROR: %s is not of type int", varName));
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "CreateSemaphoreStmt{" +
                "varName='" + varName + '\'' +
                ", expression=" + expression +
                '}';
    }
}
