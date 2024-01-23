package domain.statement.procedure;

import domain.expression.IExpression;
import domain.my_data_structures.my_table.IMyTable;
import domain.my_data_structures.my_table.MyTable;
import domain.program_state.ProgramState;
import domain.statement.CompStmt;
import domain.statement.IStmt;
import domain.type.IType;
import domain.value.IValue;
import exceptions.MyException;

import java.util.List;

public class ExecuteProcStmt implements IStmt {
    //no typechecker
    String procName;
    List<IExpression> procArguments;
    public ExecuteProcStmt(String procName, List<IExpression> argusments){
        this.procName=procName;
        this.procArguments=argusments;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (!state.getProcTable().containsKey(procName))
            throw new MyException("Function not defined in "+this);
        var procData=state.getProcTable().get(procName);
        var procParameters=procData.getFirst();
        var procStatement=procData.getSecond();
        if (procParameters.size()!=procArguments.size())
            throw new MyException("Wrong no of arguments in "+this);
        IMyTable<String, IValue> procSymTable=new MyTable<>();
        for (int i=0; i<procParameters.size(); ++i){
            var argument = procArguments.get(i);
            var name=procParameters.get(i);
            procSymTable.put(name, argument.eval(state.getSymTable(), state.getHeap()));
        }
        state.getSymTableStack().push(procSymTable);
        state.getExecutionStack().push(
                new CompStmt(
                        procStatement,
                        new ReturnStatement()
                )
        );
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "ExecuteProc{" +
                "procName='" + procName + '\'' +
                ", procArguments=" + procArguments +
                '}';
    }
}
