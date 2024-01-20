package domain.statement.sync;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.program_state.heap.Heap;
import domain.program_state.semaphore_table.SemaphoreTable;
import domain.statement.IStmt;
import domain.type.IType;
import domain.type.IntType;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.MyException;

import java.util.List;

public class ReleaseStmt implements IStmt {
    String varName;
    public ReleaseStmt(String varName){
        this.varName=varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyTable<String, IValue> symTable=state.getSymTable();
        Heap heap=state.getHeap();
        var executionStack=state.getExecutionStack();
        SemaphoreTable semaphoreTable= state.getSemaphoreTable();
        if (!symTable.containsKey(varName))
            throw new MyException(String.format("RUNTIME ERROR: %s is not in the symTable in %s",varName, this));
        Integer semIndex=((IntValue)symTable.get(varName)).getValue();
        synchronized(semaphoreTable) {
            if (!semaphoreTable.containsKey(semIndex))
                throw new MyException(String.format("RUNTIME ERROR: %s is not in the semaphore table in %s", semIndex, this));
            int semMaxNo=semaphoreTable.get(semIndex).getFirst();
            List<Integer> stateIds=semaphoreTable.get(semIndex).getSecond();
            if (stateIds.contains(Integer.valueOf(state.getId())))
                stateIds.remove(Integer.valueOf(state.getId()));
            else
                throw new MyException(String.format("RUNTIME ERROR: %s does not have possesion of the semaphore %s",state.getId(), this));
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
        return "ReleaseStmt{" +
                "varName='" + varName + '\'' +
                '}';
    }
}
