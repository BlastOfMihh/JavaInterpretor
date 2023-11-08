package domain.expression;

import domain.my_table.IMyTable;
import domain.value.BoolValue;
import domain.type.BoolType;
import domain.value.IValue;
import exceptions.MyException;

public class LogicalExp extends BinaryExpression{
    public LogicalExp(BinaryExpression.OperationTypes operationType, IExpression leftExp, IExpression rightExp) {
        super(operationType, leftExp, rightExp);
    }
    @Override
    public IValue eval(IMyTable<String, IValue> symTable) throws MyException {
        IValue leftEval= leftExp.eval(symTable), rightEval= rightExp.eval(symTable);
        if(!(leftEval.sameType(rightEval) && leftEval.sameType(new BoolType()))){
            throw new MyException("ERROR: invalid operands");
        }
        Boolean leftBool=((BoolValue)leftEval).getValue(), rightBool=((BoolValue)rightEval).getValue(), result;
        switch (operationType){
            case or:
                result=leftBool || rightBool;
                break;
            case and:
                result=leftBool && rightBool;
                break;
            default:
                throw new MyException(String.format("ERROR: invalid operator %s between %s and %s", operationType, leftExp.toString(), leftExp.toString()));
        }
        return new BoolValue(result);
    }

}
