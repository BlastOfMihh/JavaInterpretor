package domain.expression;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.heap.IHeap;
import domain.type.BoolType;
import domain.type.IType;
import domain.value.BoolValue;
import domain.value.IValue;
import exceptions.MyException;

public class ConditionalExpression implements IExpression{
    IExpression boolExp, trueExp, falseExp;
    public ConditionalExpression(IExpression boolExpression, IExpression trueExp, IExpression falseExp){
        this.boolExp = boolExpression;
        this.trueExp = trueExp;
        this.falseExp= falseExp;
    }
    @Override
    public IValue eval(IMyTable<String, IValue> symTable, IHeap heap) throws MyException {
        boolean boolValue=((BoolValue)boolExp.eval(symTable, heap)).getValue();
        if (boolValue)
            return trueExp.eval(symTable, heap);
        return falseExp.eval(symTable, heap);
    }
    @Override
    public IType typecheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        if (!boolExp.typecheck(typeEnvironment).equals(new BoolType()))
            throw new MyException("True expression does not evaluate to BOOLEAN in "+this);
        if (!trueExp.typecheck(typeEnvironment).equals(falseExp.typecheck(typeEnvironment)))
            throw new MyException(String.format("Expressions not of the same type in %s", this));
        return trueExp.typecheck(typeEnvironment);
    }
    @Override
    public String toString() {
        return "ConditionalExpression{" +
                "boolExp=" + boolExp +
                ", trueExp=" + trueExp +
                ", falseExp=" + falseExp +
                '}';
    }
}
