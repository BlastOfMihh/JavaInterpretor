package domain.expression;

public abstract class BinaryExpression implements IExpression{
    static public enum OperationTypes{
        plus, minus, multiplication, division,
        or, and,
        equal, smaller, smallerEqual, greater, greaterEqual, notEqual
    }
    final OperationTypes operationType;
    IExpression leftExp, rightExp;
    public BinaryExpression( OperationTypes operationType, IExpression leftExp, IExpression rightExp){
        this.operationType=operationType;
        this.leftExp=leftExp;
        this.rightExp=rightExp;
    }

    @Override
    public String toString() {
        return String.format("Expression %s between %s and %s", operationType, leftExp.toString(), rightExp.toString());
    }
}
