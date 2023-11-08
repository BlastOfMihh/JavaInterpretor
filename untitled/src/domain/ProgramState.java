package domain;

import domain.my_stack.IMyStack;
import domain.my_list.IMyList;
import domain.my_table.IMyTable;
import domain.statement.IStmt;
import domain.value.IValue;
import exceptions.MyException;

public class ProgramState {
    IMyTable<String, IValue> symTable;
    IMyList<String> outputLog;
    IMyStack<IStmt> executionStack;
    public ProgramState(IMyTable<String,IValue> symTable, IMyList<String> outputLog, IMyStack<IStmt> executionStack){
        this.symTable=symTable;
        this.outputLog=outputLog;
        this.executionStack=executionStack;
    }
    public IMyList<String> getOutputLog() {
        return outputLog;
    }
    public IMyStack<IStmt> getExecutionStack() {
        return executionStack;
    }
    public IMyTable<String,IValue> getSymTable() {
        return symTable;
    }
    public ProgramState execute()throws MyException {
        if (executionStack.empty()){
            throw new MyException("No more elements in the execution Stack");
        }
        IStmt topStatement=executionStack.pop();
        return topStatement.execute(this);
    }

    public boolean isCompleted() {
        return executionStack.empty();
    }

    @Override
    public String toString() {// good enough might change later
        return String.format("Symtable : %s\n--------\nOutput log : %s\n----\nExecutionStack:%s\n",
                symTable.toString(),
                outputLog.toString(),
                executionStack.toString()
        );
    }
    public String getOutput(){
        StringBuilder answer=new StringBuilder();
        for (int i=0; i<outputLog.size();++i){
            answer.append(outputLog.get(i).toString());
        }
        return answer.toString();
    }
}
