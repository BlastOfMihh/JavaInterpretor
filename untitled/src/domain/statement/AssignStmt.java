package domain.statement;

import domain.program_state.ProgramState;
import domain.expression.IExpression;
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
            table.put(key, expression.eval(table,state.getHeap()));
        }else
            throw new MyException("AssgnmtStatement : Variable name not found!");
        return null;
    }
    public String toString(){
        return "AssigmentStatemnet " + key + "="+ expression.toString();
    }
}
