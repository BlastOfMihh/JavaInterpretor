package domain.program_state;

import domain.FileDesc;
import domain.program_state.garbage_collector.GarbageCollector;
import domain.program_state.heap.IHeap;
import domain.my_data_structures.my_stack.IMyStack;
import domain.my_data_structures.my_list.IMyList;
import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.latch_table.ILatchTable;
import domain.program_state.semaphore_table.ISemaphoreTable;
import domain.statement.IStmt;
import domain.value.IValue;
import exceptions.MyException;

import java.util.Map;

public class ProgramState {
    IMyTable<String, FileDesc> fileTable;
    IMyTable<String, IValue> symTable;
    IMyList<String> outputLog;
    IMyStack<IStmt> executionStack;
    GarbageCollector garbageCollector;
    IHeap heap;
    ISemaphoreTable semaphoreTable;
    ILatchTable latchTable;
    private int id;
    static int availableId=0;

    public ProgramState(IMyTable<String,IValue> symTable, IHeap heap, IMyList<String> outputLog, IMyStack<IStmt> executionStack, IMyTable<String,FileDesc> fileTable, ISemaphoreTable semaphoreTable, ILatchTable latchTable){
        this.heap=heap;
        this.symTable=symTable;
        this.outputLog=outputLog;
        this.executionStack=executionStack;
        this.fileTable=fileTable;
        this.garbageCollector=new GarbageCollector(heap, symTable);
        this.id=availableId++;
        this.semaphoreTable=semaphoreTable;
        this.latchTable=latchTable;
        //availableId++;
    }
    public int getId(){
        return id;
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
    public IHeap getHeap(){
        return heap;
    }
    public ISemaphoreTable getSemaphoreTable() {
        return semaphoreTable;
    }
    public ILatchTable getLatchTable() {
        return latchTable;
    }
    public ProgramState executeOneStep()throws MyException {
        if (executionStack.empty()){
            throw new MyException("No more elements in the execution Stack");
        }
        IStmt topStatement=executionStack.pop();
        return topStatement.execute(this);
    }
    public boolean isCompleted() {
        return executionStack.empty();
    }
    public String getOutput(){
        StringBuilder answer=new StringBuilder();
        for (int i=0; i<outputLog.size();++i){
            answer.append(outputLog.get(i).toString());
        }
        return answer.toString();
    }
    public Map<Integer, IValue> getNewCleanedHeap(){
        return garbageCollector.getNewCleanedHeap();
    }
    public void setHeap(IHeap heap){
        this.heap=heap;
        this.garbageCollector.setHeap(heap);
    }
    @Override
    public String toString() {// good enough might change later
        //String sep=new String("\n");
        //return String.format("id %s -> Symtable : %s\n--------\nOutput log : %s\n----\nExecutionStack:%s\n----\nFileTable: %s\n============\nHeap: %s\n============\n\n",
        return String.format("id %s \n *Symtable : %s\n *Output log : %s\n *ExecutionStack:%s\n *FileTable: %s\n *Heap: %s\n\n",
                id,
                symTable.toString(),
                outputLog.toString(),
                executionStack.toString(),
                fileTable.toString(),
                heap.toString()
        );
    }
}
