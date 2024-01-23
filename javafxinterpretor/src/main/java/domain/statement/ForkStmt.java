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
        IMyStack<IMyTable<String,IValue>> cloneSymStack=new MyStack<>();
        state.getSymTableStack().forEach(
                symTable->{
                    IMyTable<String, IValue> clonedSymTable= new MyTable<>();
                    symTable.entrySet()
                            .forEach(entry -> clonedSymTable.put(entry.getKey(), entry.getValue().deepCopy()));
                    cloneSymStack.push(clonedSymTable);
                }
        );
        IMyStack<IStmt> newExecutionStack= new MyStack<>();
        newExecutionStack.push(statement);
        return new ProgramState(
                cloneSymStack, state.getHeap(), state.getOutputLog(), newExecutionStack, state.getFileTable(), state.getSemaphoreTable(), state.getLatchTable(), state.getProcTable()
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

