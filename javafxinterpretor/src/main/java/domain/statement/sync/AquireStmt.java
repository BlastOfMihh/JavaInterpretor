package domain.statement.sync;

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

import java.util.List;

public class AquireStmt implements IStmt {
    String varName;
    public AquireStmt(String varName){
        this.varName=varName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyTable<String, IValue> symTable=state.getSymTable();
        IHeap heap=state.getHeap();
        var executionStack=state.getExecutionStack();
        ISemaphoreTable semaphoreTable= state.getSemaphoreTable();
        if (!symTable.containsKey(varName))
            throw new MyException(String.format("RUNTIME ERROR: %s is not in the symTable in %s",varName, this));
        Integer index=((IntValue)symTable.get(varName)).getValue();
        synchronized(semaphoreTable){
            if (!semaphoreTable.containsKey(index))
                throw new MyException(String.format("RUNTIME ERROR: %s is not in the semaphore table in %s", index, this));
            Integer semMaxNo=semaphoreTable.get(index).getFirst();
            List<Integer> statesIds=semaphoreTable.get(index).getSecond();
            if (semMaxNo>statesIds.size()){
                if (!statesIds.contains(state.getId()))
                    statesIds.add(state.getId());
            }
            else
                executionStack.push(this);
        }
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        if(!typeEnvironment.containsKey(varName))
            throw new MyException(String.format("RUNTIME ERROR: %s is not in the symTable in %s",varName, this));
        if (!typeEnvironment.get(varName).equals(new IntType()))
            throw new MyException(String.format("RUNTIMEE ERROR: %s is not of type int", varName));
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "AquireStmt{" +
                "varName='" + varName + '\'' +
                '}';
    }
}
