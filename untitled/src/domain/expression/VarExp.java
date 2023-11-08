package domain.expression;

import domain.my_table.IMyTable;
import domain.value.IValue;
import exceptions.MyException;

public class VarExp implements IExpression{
    final String key;
    public VarExp(String key){
        this.key=key;
    }
    @Override
    public IValue eval(IMyTable<String, IValue> symTable) throws MyException {
        return symTable.get(key);
    }
    @Override
    public String toString() {
        return "VariableExpression{" +
                "key='" + key + '\'' +
                '}';
    }
}
