package domain.statement;

import domain.ProgramState;
import exceptions.MyException;

public interface IStmt {
    public ProgramState execute(ProgramState state) throws MyException;
}
