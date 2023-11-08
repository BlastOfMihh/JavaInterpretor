package domain.statement;

import domain.ProgramState;
import domain.expression.IExpression;
import domain.my_list.IMyList;
import exceptions.MyException;

public class PrintStmt implements IStmt {
    IExpression expression;
    public PrintStmt(IExpression expression){
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state)throws MyException {
       // var exeStack=state.getExecutionStack();
        var table=state.getSymTable();
        IMyList<String> output=state.getOutputLog();
        output.add(expression.eval(table).toString());
        return state;
    }
    @Override
    public String toString() {
        return "PrintStatement{"+expression.toString()+"}";
    }
}
