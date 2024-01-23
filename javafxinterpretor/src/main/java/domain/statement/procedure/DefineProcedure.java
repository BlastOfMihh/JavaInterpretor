package domain.statement.procedure;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.statement.IStmt;
import domain.type.IType;
import exceptions.MyException;
import kotlin.Pair;

import java.util.List;

public class DefineProcedure implements IStmt {
    String funcName;
    List<String> varNames;
    IStmt functionBody;
    public DefineProcedure(String funcName, List<String> varNames, IStmt functionBody){
        this.funcName=funcName;
        this.varNames=varNames;
        this.functionBody=functionBody;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        //state.getExecutionStack().push(functionBody);
        state.getProcTable().put(funcName, new Pair<>(varNames,functionBody));
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        //functionBody.typeCheck(typeEnvironmenta);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "DefineProcedure{" +
                "funcName='" + funcName + '\'' +
                ", varNames=" + varNames +
                ", functionBody=" + functionBody +
                '}';
    }
}
