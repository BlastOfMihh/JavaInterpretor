package domain.statement;

import domain.FileDesc;
import domain.my_data_structures.my_list.IMyList;
import domain.my_data_structures.my_list.MyList;
import domain.my_data_structures.my_stack.IMyStack;
import domain.my_data_structures.my_stack.MyStack;
import domain.my_data_structures.my_table.IMyTable;
import domain.my_data_structures.my_table.MyTable;
import domain.program_state.ProgramState;
import domain.program_state.heap.Heap;
import domain.type.IType;
import domain.value.IValue;
import exceptions.MyException;

import java.util.Map;

public class ForkStmt implements IStmt{
    private final IStmt statement;
    public ForkStmt(IStmt statement){
        this.statement=statement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyTable<String, IValue> newSymTable= new MyTable<String, IValue>();
        //newSymTable.putAll(state.getSymTable());
        state.getSymTable().entrySet().stream()
                .forEach(entry -> newSymTable.put(entry.getKey(), entry.getValue().deepCopy()));
        Heap newHeap = state.getHeap();
        //IMyList<String> newOutputLog=new MyList<String>(); // ?
        IMyList<String> newOutputLog=state.getOutputLog();
        IMyStack<IStmt> newExecutionStack=new MyStack<IStmt>();
        newExecutionStack.push(statement);
        IMyTable<String, FileDesc > newFileTable=state.getFileTable();
        ProgramState newProgramState=new ProgramState(newSymTable, newHeap, newOutputLog, newExecutionStack, newFileTable);
        return newProgramState;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        statement.typeCheck(typeEnvironment.getShallowCopy());
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "ForkStmt{" +
                "statement=" + statement +
                '}';
    }
}

