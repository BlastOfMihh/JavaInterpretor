package domain.statement;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.type.IType;
import exceptions.MyException;

public interface IStmt {
    public ProgramState execute(ProgramState state) throws MyException;
    IMyTable<String, IType> typeCheck(IMyTable<String,IType> typeEnvironment)throws MyException;
}
