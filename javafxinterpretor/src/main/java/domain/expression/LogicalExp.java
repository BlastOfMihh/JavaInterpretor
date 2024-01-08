package domain.expression;

import domain.program_state.heap.Heap;
import domain.my_data_structures.my_table.IMyTable;
import domain.type.IType;
import domain.type.IntType;
import domain.value.BoolValue;
import domain.type.BoolType;
import domain.value.IValue;
import exceptions.MyException;

public class LogicalExp extends BinaryExpression{
    public LogicalExp(BinaryExpression.OperationTypes operationType, IExpression leftExp, IExpression rightExp) {
        super(operationType, leftExp, rightExp);
    }
    @Override
    public IValue eval(IMyTable<String, IValue> symTable, Heap heap) throws MyException {
        IValue leftEval= leftExp.eval(symTable, heap), rightEval= rightExp.eval(symTable, heap);
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

    @Override
    public IType typecheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        IType leftType=leftExp.typecheck(typeEnvironment);
        IType rightType=rightExp.typecheck(typeEnvironment);
        if (!leftType.equals(new BoolType()))
            throw new MyException("left Type not of type int in : " +this);
        if (!rightType.equals(new BoolType()))
            throw new MyException("right Type not of type int in : " +this);
        return new BoolType(); //? boool type?
    }

}
