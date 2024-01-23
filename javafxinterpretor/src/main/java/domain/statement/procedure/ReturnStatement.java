package domain.statement.procedure;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.statement.IStmt;
import domain.type.IType;
import exceptions.MyException;

public class ReturnStatement implements IStmt {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getSymTableStack().pop();
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "Return;";
    }
}
