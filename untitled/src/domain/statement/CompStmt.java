package domain.statement;


import domain.ProgramState;

public class CompStmt implements IStmt {
    IStmt leftStatement, rightStatement;

    public CompStmt(IStmt left, IStmt right){
        leftStatement=left;
        rightStatement=right;
    }
    @Override
    public ProgramState execute(ProgramState state) {
        var exeStack=state.getExecutionStack();
        // var table=state.getSymTable();
        // var output=state.getOutputLog();
        exeStack.push(rightStatement);
        exeStack.push(leftStatement);
        return state;
    }
    public String toString(){
        return "("+leftStatement.toString()+","+rightStatement.toString()+")";
    }
}
