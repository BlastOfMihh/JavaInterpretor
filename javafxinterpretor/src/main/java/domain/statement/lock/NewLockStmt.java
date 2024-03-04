package domain.statement.lock;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.statement.IStmt;
import domain.type.IType;
import domain.type.IntType;
import domain.value.IntValue;
import exceptions.MyException;

public class NewLockStmt implements IStmt {
    String varName;
    public NewLockStmt(String varName){
        this.varName=varName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        synchronized (state.getLockTable()){
            state.getSymTable().put(
                    varName,
                    new IntValue(state.getLockTable().putWithAddress(-1))
            );
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
        return "NewLockStmt{" +
                "varName='" + varName + '\'' +
                '}';
    }
}
