package domain.statement;

import domain.my_data_structures.my_stack.IMyStack;
import domain.my_data_structures.my_stack.MyStack;
import domain.my_data_structures.my_table.IMyTable;
import domain.my_data_structures.my_table.MyTable;
import domain.program_state.ProgramState;
import domain.type.IType;
import domain.value.IValue;
import exceptions.MyException;

public class ForkStmt implements IStmt{
    private final IStmt statement;
    public ForkStmt(IStmt statement){
        this.statement=statement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyTable<String, IValue> clonedSymTable= new MyTable<String, IValue>();
        state.getSymTable().entrySet().stream()
                .forEach(entry -> clonedSymTable.put(entry.getKey(), entry.getValue().deepCopy()));
        IMyStack<IStmt> newExecutionStack=new MyStack<IStmt>();
        newExecutionStack.push(statement);
        return new ProgramState(
                clonedSymTable, state.getHeap(), state.getOutputLog(), newExecutionStack, state.getFileTable(), state.getSemaphoreTable(), state.getLatchTable()
        );
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

