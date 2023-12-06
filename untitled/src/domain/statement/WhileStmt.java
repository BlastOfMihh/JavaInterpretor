package domain.statement;


import domain.expression.IExpression;
import domain.program_state.ProgramState;
import domain.type.BoolType;
import domain.value.BoolValue;
import domain.value.IValue;
import exceptions.MyException;

public class WhileStmt implements IStmt {
    IExpression condition;
    IStmt instruction;
    public WhileStmt(IExpression condition, IStmt instruction){
        this.instruction=instruction;
        this.condition=condition;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var exeStack=state.getExecutionStack();
        // var table=state.getSymTable();
        // var output=state.getOutputLog();
        IValue conditionIValue=condition.eval(state.getSymTable(), state.getHeap());
        if (!conditionIValue.sameType(new BoolType()))
            throw new MyException(String.format("ERROR: expression %s does not evaluate to bool"));
        BoolValue conditionBoolValue=(BoolValue) conditionIValue;
        if (conditionBoolValue.getValue()){
            exeStack.push(this);
            exeStack.push(instruction);
        }
        return state;
    }

    @Override
    public String toString() {
        return "WhileStmt{" +
                "instruction=" + instruction +
                '}';
    }
}
