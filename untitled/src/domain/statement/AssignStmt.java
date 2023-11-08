package domain.statement;

import domain.ProgramState;
import domain.expression.IExpression;
import domain.value.IValue;
import exceptions.MyException;

public class AssignStmt implements IStmt {
    String key;
    IExpression expression;
    public AssignStmt(String key, IExpression value){
        this.key=key;
        this.expression=value;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        //var exeStack=state.getExecutionStack();
        var table=state.getSymTable();
        //var output=state.getOutputLog();
        if (table.containsKey(key)){
            table.put(key, expression.eval(table));
        }else
            throw new MyException("AssgnmtStatement : Variable name not found!");
        return state;
    }
    public String toString(){
        return "AssigmentStatemnet " + key + "="+ expression.toString();
    }
}
