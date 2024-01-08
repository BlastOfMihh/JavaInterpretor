package domain.statement;


import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.type.IType;
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
        exeStack.push(rightStatement);
        exeStack.push(leftStatement);
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException{
        return rightStatement.typeCheck(leftStatement.typeCheck(typeEnvironment));
    }

    public String toString(){
        return "("+leftStatement.toString()+","+rightStatement.toString()+")";
    }
}
