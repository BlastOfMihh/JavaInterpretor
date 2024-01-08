package domain.statement;


import domain.expression.IExpression;
import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.type.BoolType;
import domain.type.IType;
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
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        instruction.typeCheck(typeEnvironment.getShallowCopy());
        if (! condition.typecheck(typeEnvironment).equals(new BoolType()))
            throw new MyException("The condition should be of Type Bool in:"+this);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "WhileStmt{" +
                "instruction=" + instruction +
                '}';
    }
}
