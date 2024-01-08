package domain.statement;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.expression.IExpression;
import domain.my_data_structures.my_list.IMyList;
import domain.type.IType;
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
        output.add(expression.eval(table, state.getHeap()).toString()+"\n");
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException{
        expression.typecheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "PrintStatement{"+expression.toString()+"}";
    }
}
