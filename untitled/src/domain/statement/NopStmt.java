package domain.statement;

import domain.program_state.ProgramState;
import exceptions.MyException;

public class NopStmt implements IStmt {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }
    @Override
    public String toString() {
        return String.format("%s", "NopStatement");
    }
}
