package domain.statement.file_statements;

import domain.FileDesc;
import domain.program_state.ProgramState;
import domain.expression.IExpression;
import domain.my_data_structures.my_table.IMyTable;
import domain.type.IntType;
import domain.value.IValue;
import domain.value.IntValue;
import domain.value.StringValue;
import exceptions.MyException;

public class ReadFile extends FileStatement {
    String varName;
    public ReadFile(IExpression expression, String varName) {
        this.expression = expression;
        this.varName =varName;
    }
    void checkValue(IMyTable<String, IValue> symTable) throws MyException{
        if (!symTable.containsKey(varName))
            throw new MyException(String.format("Variable %s does not exist"));
        if( ! symTable.get(varName).sameType(new IntType()))
            throw new MyException(String.format("Variable %s is not int | %s", varName, this));
    }
    public ProgramState execute(ProgramState state) throws MyException {
        //var exeStack = state.getExecutionStack();
        var symTable = state.getSymTable();
        //var output = state.getOutputLog();
        var fileTable = state.getFileTable();
        checkValue(symTable);
        StringValue expressionStringValue=getExpressionStringValue(fileTable, symTable, state.getHeap());
        FileDesc openedFile = fileTable.get(expressionStringValue.getValue());
        if (! fileTable.containsKey(expressionStringValue.getValue())){
            throw new MyException(String.format("No such file in the File Table in %s", this));
        }
        try{
            String line=openedFile.readLine();
            IntValue newInt;
            if(line.isEmpty())
                newInt=new IntValue(Integer.parseInt("0"));
            else
                newInt=new IntValue(Integer.parseInt(line));
            symTable.put(varName, newInt);
        }catch (Exception e){
            throw new MyException(this.toString()+e.getMessage());
        }
        return null;
    }
    @Override
    public String toString() {
        return "ReadFile{" + expression.toString() + "}";
    }
}
