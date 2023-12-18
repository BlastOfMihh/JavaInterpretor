package domain.statement;

import domain.program_state.ProgramState;
import domain.type.IType;
import exceptions.MyException;

public class VarDeclStmt implements IStmt {
    String varName;
    IType type;
    public VarDeclStmt(String varName, IType type) {
        this.varName = varName;
        this.type = type;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        var table=state.getSymTable();
        if (!table.containsKey(varName))
            table.put(varName, type.newDefaultValue());
        else
            throw new MyException();
        return null;
    }
    public String toString(){
        return "VarDeclarationStatement:"+varName+" with type="+type.toString();
    }
}
