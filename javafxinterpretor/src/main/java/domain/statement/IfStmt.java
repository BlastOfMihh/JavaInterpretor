package domain.statement;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.expression.IExpression;
import domain.type.BoolType;
import domain.type.IType;
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
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        IType expressionType= expression.typecheck(typeEnvironment);
        if (! expressionType.equals(new BoolType()))
            throw new MyException("Expression is not boolean in "+this);
        trueStatement.typeCheck(typeEnvironment.getShallowCopy());
        falseStatement.typeCheck(typeEnvironment.getShallowCopy());
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return String.format("if %s then %s else %s", expression.toString(), trueStatement.toString(), falseStatement.toString());
    }
}
