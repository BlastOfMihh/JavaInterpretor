package domain.statement;


import domain.program_state.ProgramState;
import exceptions.MyException;

public class CompStmt implements IStmt {
    IStmt leftStatement, rightStatement;

    public CompStmt(IStmt left, IStmt right){
        leftStatement=left;
        rightStatement=right;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var exeStack=state.getExecutionStack();
        // var table=state.getSymTable();
        // var output=state.getOutputLog();
        exeStack.push(rightStatement);
        exeStack.push(leftStatement);
        return null;
    }
    public String toString(){
        return "("+leftStatement.toString()+","+rightStatement.toString()+")";
    }
}
