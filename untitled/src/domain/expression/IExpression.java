package domain.expression;

import domain.my_table.IMyTable;
import domain.value.IValue;
import exceptions.MyException;

public interface IExpression {
    public IValue eval(IMyTable<String, IValue> symTable) throws MyException;
}
