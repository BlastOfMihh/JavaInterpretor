package domain.statement.switch_statement;

import domain.expression.ArithExp;
import domain.expression.IExpression;
import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.program_state.heap.Heap;
import domain.statement.IStmt;
import domain.type.IType;
import domain.value.IValue;
import exceptions.MyException;

import java.util.List;

public class SwitchStmt implements IStmt{
    IExpression mainExpression;
    List<CaseSwitch> casesList;
    IStmt defaultStmt;
    public SwitchStmt(IExpression mainExpression, List<CaseSwitch> casesList, IStmt defaultStmt) {
        this.mainExpression=mainExpression;
        this.casesList=casesList;
        this.defaultStmt=defaultStmt;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Heap heap = state.getHeap();
        IMyTable<String, IValue> symTable=state.getSymTable();
        IValue evaluatedMainExp=mainExpression.eval(symTable, heap);
        for (CaseSwitch caseSwitch:casesList){
            if (caseSwitch.expression.eval(symTable,heap).equals(evaluatedMainExp)){
                caseSwitch.statement.execute(state);
                return null;
            }
        }
        defaultStmt.execute(state);
        return null;
    }
    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        IType mainType=mainExpression.typecheck(typeEnvironment);
        for(CaseSwitch x:casesList){
            if (!mainType.equals(x.expression.typecheck(typeEnvironment.getShallowCopy())))
                throw new MyException(String.format("main expression not of the same type as %s in %s",x.expression, this));
            x.statement.typeCheck(typeEnvironment.getShallowCopy());
        }
        defaultStmt.typeCheck(typeEnvironment.getShallowCopy());
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "SwitchStmt{" +
                "mainExpression=" + mainExpression +
                ", casesList=" + casesList +
                ", defaultStmt=" + defaultStmt +
                '}';
    }
}
