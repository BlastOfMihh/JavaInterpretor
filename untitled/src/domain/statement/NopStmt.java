package domain.statement;

import domain.ProgramState;
import exceptions.MyException;

public class NopStmt implements IStmt {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return state;
    }
    @Override
    public String toString() {
        return String.format("%s", "NopStatement");
    }
}
