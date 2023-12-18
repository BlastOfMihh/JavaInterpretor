package domain.statement;

import domain.program_state.ProgramState;
import domain.expression.IExpression;
import domain.type.BoolType;
import domain.value.BoolValue;
import domain.value.IValue;
import exceptions.MyException;

public class IfStmt implements IStmt {
    IStmt trueStatement, falseStatement;
    IExpression expression;

    public IfStmt(IExpression expression, IStmt trueStatement, IStmt falseStatement) {
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var exeStack = state.getExecutionStack();
        var table = state.getSymTable();
        //var output=state.getOutputLog();
        IValue expressionValue = expression.eval(table, state.getHeap());
        if (!expressionValue.sameType(new BoolType())) {
            throw new MyException("The expression does not evaluate to BOOL!");
        }
        Boolean trueValue = ((BoolValue) expressionValue).getValue();
        if (trueValue) {
            exeStack.push(trueStatement);
        } else {
            exeStack.push(falseStatement);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("if %s then %s else %s", expression.toString(), trueStatement.toString(), falseStatement.toString());
    }
}
