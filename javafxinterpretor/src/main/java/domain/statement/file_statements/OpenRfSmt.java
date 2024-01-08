package domain.statement.file_statements;

import domain.FileDesc;
import domain.program_state.ProgramState;
import domain.expression.IExpression;
import domain.value.StringValue;
import exceptions.MyException;

import java.io.FileReader;

public class OpenRfSmt extends FileStatement{
    public OpenRfSmt(IExpression expression) {
        this.expression = expression;
    }
    public ProgramState execute(ProgramState state) throws MyException {
        //var exeStack = state.getExecutionStack();
        var symTable = state.getSymTable();
        //var output = state.getOutputLog();
        var fileTable = state.getFileTable();
        StringValue expressionStringValue=getExpressionStringValue(fileTable, symTable, state.getHeap());
        String varName=expressionStringValue.getValue();
        if (fileTable.containsKey(expressionStringValue.getValue())){
            throw new MyException(String.format("File already in the FileTable | in %s", this));
        }
        try{
            fileTable.put(expressionStringValue.getValue(),new FileDesc(new FileReader(expressionStringValue.getValue())));
        }catch (Exception e){
            throw new MyException(this.toString()+e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "OpenRfStmt{" + expression.toString() + "}";
    }
}
