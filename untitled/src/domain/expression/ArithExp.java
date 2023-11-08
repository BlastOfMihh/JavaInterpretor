package domain.expression;

import domain.my_table.IMyTable;
import domain.type.IntType;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.MyException;

public class ArithExp extends BinaryExpression {

    public ArithExp(OperationTypes operationType, IExpression leftExp, IExpression rightExp) {
        super(operationType, leftExp, rightExp);
    }
    @Override
    public IValue eval(IMyTable<String, IValue> symTable) throws MyException {
        IValue leftEval= leftExp.eval(symTable), rightEval= rightExp.eval(symTable);
        if(!(leftEval.sameType(rightEval) && leftEval.sameType(new IntType()))){
            throw new MyException("ERROR: invalid operands");
        }
        Integer leftInt=((IntValue)leftEval).getValue(), rightInt=((IntValue)rightEval).getValue();
        Integer result;
        try{
            switch (operationType){
                case plus:
                    result=leftInt+rightInt;
                    break;
                case minus:
                    result=leftInt-rightInt;
                    break;
                case division:
                    result=leftInt/rightInt;
                    break;
                case multiplication:
                    result=leftInt*rightInt;
                    break;
                default:
                    throw new MyException(String.format("ERROR: invalid operator %s between %s and %s", operationType, leftExp.toString(), leftExp.toString()));
            }
        }catch (Exception exception){
            throw new MyException(exception.getMessage());
        }
        return new IntValue(result);
    }
}
