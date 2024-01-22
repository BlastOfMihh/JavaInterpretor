package domain.statement.latch;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.program_state.latch_table.ILatchTable;
import domain.statement.IStmt;
import domain.type.IType;
import domain.type.IntType;
import domain.value.IntValue;
import exceptions.MyException;

public class CountDown implements IStmt {
    String varName;
    public CountDown(String varName){
        this.varName=varName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Integer index=((IntValue)state.getSymTable().get(varName)).getValue();
        ILatchTable latchTable=state.getLatchTable();
        synchronized(latchTable){
            if (!latchTable.containsKey(index))
                throw new MyException(String.format("Index from %s not found in letch table %s",index, latchTable));
            if (latchTable.get(index)>0)
                latchTable.put(index, latchTable.get(index)-1);
            state.getOutputLog().add(String.format("%s",state.getId()));
        }
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        if (!typeEnvironment.containsKey(varName))
            throw new MyException("Variable not declare in "+this);
        if (!typeEnvironment.get(varName).equals(new IntType()))
            throw new MyException("Variable not of type INT in "+this);
        return typeEnvironment;
    }
}
