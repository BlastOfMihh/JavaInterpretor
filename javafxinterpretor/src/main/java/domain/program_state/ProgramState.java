package domain.program_state;

import domain.FileDesc;
import domain.my_data_structures.my_stack.MyStack;
import domain.program_state.garbage_collector.GarbageCollector;
import domain.program_state.heap.IHeap;
import domain.my_data_structures.my_stack.IMyStack;
import domain.my_data_structures.my_list.IMyList;
import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.latch_table.ILatchTable;
import domain.program_state.lock_table.ILockTable;
import domain.program_state.proc_table.IProcTable;
import domain.program_state.semaphore_table.ISemaphoreTable;
import domain.statement.IStmt;
import domain.value.IValue;
import exceptions.MyException;

import java.util.Map;

public class ProgramState {
    IMyTable<String, FileDesc> fileTable;
    IMyStack<IMyTable<String, IValue>> symTableStack;
    IMyList<String> outputLog;
    IMyStack<IStmt> executionStack;
    GarbageCollector garbageCollector;
    IHeap heap;
    ISemaphoreTable semaphoreTable;
    ILatchTable latchTable;
    IProcTable procTable;
    ILockTable lockTable;
    private final int id;
    static int availableId=0;

    public ProgramState(IMyTable<String,IValue> symTable, IHeap heap, IMyList<String> outputLog, IMyStack<IStmt> executionStack, IMyTable<String,FileDesc> fileTable, ISemaphoreTable semaphoreTable, ILatchTable latchTable, ILockTable lockTable, IProcTable procTable){
        this.heap=heap;
        this.symTableStack=new MyStack<>();
        this.symTableStack.push(symTable);
        this.outputLog=outputLog;
        this.executionStack=executionStack;
        this.fileTable=fileTable;
        this.garbageCollector=new GarbageCollector(heap, symTableStack);
        this.id=availableId++;
        this.semaphoreTable=semaphoreTable;
        this.latchTable=latchTable;
        this.lockTable=lockTable;
        this.procTable=procTable;
    }
    public ProgramState(IMyStack<IMyTable<String,IValue>> symTableStack, IHeap heap, IMyList<String> outputLog, IMyStack<IStmt> executionStack, IMyTable<String,FileDesc> fileTable, ISemaphoreTable semaphoreTable, ILatchTable latchTable, ILockTable lockTable, IProcTable procTable){
        this(symTableStack.getLast(), heap,outputLog,executionStack,fileTable,semaphoreTable,latchTable, lockTable, procTable);
        this.symTableStack=symTableStack;
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
        return symTableStack.getLast();
    }
    public IMyStack<IMyTable<String, IValue>> getSymTableStack(){
        return symTableStack;
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
    public IProcTable getProcTable() {
        return procTable;
    }
    public ILockTable getLockTable() { return lockTable; }

    public String getOutput(){
        StringBuilder answer=new StringBuilder();
        for (int i=0; i<outputLog.size();++i){
            answer.append(outputLog.get(i));
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
    @Override
    public String toString() {// good enough might change later
        return String.format("id %s \n *SymtableStack : %s\n *Output log : %s\n *ExecutionStack:%s\n *FileTable: %s\n *Heap: %s\n\n",
                id,
                symTableStack.toString(),
                outputLog.toString(),
                executionStack.toString(),
                fileTable.toString(),
                heap.toString()
        );
    }
}
