package domain.statement.latch;

import domain.expression.IExpression;
import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.program_state.latch_table.ILatchTable;
import domain.statement.IStmt;
import domain.type.IType;
import domain.type.IntType;
import domain.value.IntValue;
import exceptions.MyException;

public class NewLatchStmt implements IStmt {
    IExpression expression;
    String varName;
    public NewLatchStmt(String varName, IExpression expression){
        this.expression=expression;
        this.varName=varName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ILatchTable latchTable=state.getLatchTable();
        Integer evaluatedInt=((IntValue)expression.eval(state.getSymTable(), state.getHeap())).getValue();
        synchronized (latchTable){
            state.getSymTable().put(varName, new IntValue(latchTable.putWithAddress(evaluatedInt)));
        }
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        if (!expression.typecheck(typeEnvironment).equals(new IntType()))
            throw new MyException("Expression not of type INT in "+this);
        if (!typeEnvironment.containsKey(varName))
            throw new MyException("Variable not declared");
        if (!typeEnvironment.get(varName).equals(new IntType()))
            throw new MyException("Variable not of type INT in "+this);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "NewLatchStmt{" +
                "expression=" + expression +
                ", varName='" + varName + '\'' +
                '}';
    }
}
