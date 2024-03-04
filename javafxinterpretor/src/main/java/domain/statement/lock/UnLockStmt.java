package domain.statement.lock;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.statement.IStmt;
import domain.type.IType;
import domain.type.IntType;
import domain.value.IntValue;
import exceptions.MyException;

public class UnLockStmt implements IStmt{
    String varName;
    public UnLockStmt(String varName){
        this.varName=varName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Integer index=((IntValue)state.getSymTable().get(varName)).getValue();
        synchronized (state.getLockTable()){
            if (!state.getSymTable().get(varName).getType().equals(new IntType()))
                throw new MyException("Variable not of type INT in "+this);
            if (!state.getLockTable().containsKey(index))
                throw new MyException("Index not found in lockTable in "+this);
            if (state.getLockTable().get(index)==state.getId())
                state.getLockTable().put(index,-1);
        }
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        if (!typeEnvironment.containsKey(varName))
            throw new MyException("Variable not declared");
        if (!typeEnvironment.get(varName).equals(new IntType()))
            throw new MyException("Variable not of type INT in "+this);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "UnLockStmt{" +
                "varName='" + varName + '\'' +
                '}';
    }
}
