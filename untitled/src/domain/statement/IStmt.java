package domain.statement;

import domain.program_state.ProgramState;
import exceptions.MyException;

public interface IStmt {
    public ProgramState execute(ProgramState state) throws MyException;
}
