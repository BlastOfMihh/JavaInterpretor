package domain.expression;
import domain.program_state.heap.Heap;
import domain.my_data_structures.my_table.IMyTable;
import domain.type.IntType;
import domain.value.*;
import exceptions.MyException;
import domain.value.IValue;
import domain.value.IntValue;

public class RelationalExpression extends BinaryExpression {

    public RelationalExpression(OperationTypes operationType, IExpression leftExp, IExpression rightExp) {
        super(operationType, leftExp, rightExp);
    }
    @Override
    public IValue eval(IMyTable<String, IValue> symTable, Heap heap) throws MyException {
        IValue leftEval= leftExp.eval(symTable, heap), rightEval= rightExp.eval(symTable, heap);
        if(!(leftEval.sameType(rightEval) && leftEval.sameType(new IntType()))){
            throw new MyException("ERROR: invalid operands");
        }
        Integer leftInt=((IntValue)leftEval).getValue(), rightInt=((IntValue)rightEval).getValue();
        Boolean result;
        try{
            switch (operationType){
                case equal:
                    result=(leftInt==rightInt);
                    break;
                case notEqual:
                    result=(leftInt!=rightInt);
                    break;
                case smaller:
                    result=(leftInt<rightInt);
                    break;
                case smallerEqual:
                    result=(leftInt<=rightInt);
                    break;
                case greater:
                    result=(leftInt>rightInt);
                    break;
                case greaterEqual:
                    result=(leftInt>=rightInt);
                    break;
                default:
                    throw new MyException(String.format("ERROR: invalid operator %s between %s and %s", operationType, leftExp.toString(), leftExp.toString()));
            }
        }catch (Exception exception){
            throw new MyException(exception.getMessage());
        }
        return new BoolValue(result);
    }
}
