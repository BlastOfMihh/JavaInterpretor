package domain.statement.switch_statement;

import domain.expression.IExpression;
import domain.statement.IStmt;

public class CaseSwitch {
    IExpression expression;
    IStmt statement;
    public CaseSwitch(IExpression expression, IStmt statemtent){
        this.expression=expression;
        this.statement=statemtent;
    }

    public IExpression getExpression() {
        return expression;
    }
    public IStmt getStatement() {
        return statement;
    }

    @Override
    public String toString() {
        return "CaseSwitch{" +
                "expression=" + expression +
                ", statement=" + statement +
                '}';
    }
}
