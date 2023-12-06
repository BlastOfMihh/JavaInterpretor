package domain.program_state;

import domain.FileDesc;
import domain.program_state.garbage_collector.GarbageCollector;
import domain.program_state.heap.Heap;
import domain.my_data_structures.my_stack.IMyStack;
import domain.my_data_structures.my_list.IMyList;
import domain.my_data_structures.my_table.IMyTable;
import domain.statement.IStmt;
import domain.value.IValue;
import exceptions.MyException;

public class ProgramState {
    IMyTable<String, FileDesc> fileTable;
    IMyTable<String, IValue> symTable;
    IMyList<String> outputLog;
    IMyStack<IStmt> executionStack;
    GarbageCollector garbageCollector;
    Heap heap;
    public ProgramState(IMyTable<String,IValue> symTable, Heap heap, IMyList<String> outputLog, IMyStack<IStmt> executionStack, IMyTable<String,FileDesc> fileTable){
        this.heap=heap;
        this.symTable=symTable;
        this.outputLog=outputLog;
        this.executionStack=executionStack;
        this.fileTable=fileTable;
        this.garbageCollector=new GarbageCollector(heap, symTable);
    }
    public IMyTable<String, FileDesc> getFileTable(){
        return fileTable;
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
    public Heap getHeap(){
        return heap;
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
        //String sep=new String("\n");
        return String.format("Symtable : %s\n--------\nOutput log : %s\n----\nExecutionStack:%s\n----\nFileTable: %s\n============\nHeap: %s\n============\n\n",
                symTable.toString(),
                outputLog.toString(),
                executionStack.toString(),
                fileTable.toString(),
                heap.toString()
        );
    }
    public String getOutput(){
        StringBuilder answer=new StringBuilder();
        for (int i=0; i<outputLog.size();++i){
            answer.append(outputLog.get(i).toString());
        }
        return answer.toString();
    }

    public void collectGarbage() {
        garbageCollector.cleanHeap();
    }
}
