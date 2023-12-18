package domain.statement.file_statements;

import domain.FileDesc;
import domain.program_state.ProgramState;
import domain.expression.IExpression;
import domain.value.StringValue;
import exceptions.MyException;

public class CloseRFile extends FileStatement {
    public CloseRFile(IExpression expression) {
        this.expression = expression;
    }
    public ProgramState execute(ProgramState state) throws MyException{
        //var exeStack = state.getExecutionStack();
        var symTable = state.getSymTable();
        //var output = state.getOutputLog();
        var fileTable = state.getFileTable();
        StringValue expressionStringValue=getExpressionStringValue(fileTable, symTable, state.getHeap());
        if (! fileTable.containsKey(expressionStringValue.getValue())){
            throw new MyException(String.format("File not found in the FileTable | in %s", this));
        }
        FileDesc openedFile = fileTable.get(expressionStringValue.getValue());
        try{
            openedFile.close();
        }catch(Exception e){
            throw new MyException(this.toString()+e.getMessage());
        }
        fileTable.remove(expressionStringValue.getValue());
        return null;
    }

    @Override
    public String toString() {
        return "CloseFile{" + expression.toString() + "}";
    }
}
