package domain.statement.file_statements;

import domain.FileDesc;
import domain.expression.IExpression;
import domain.program_state.heap.Heap;
import domain.my_data_structures.my_table.IMyTable;
import domain.statement.IStmt;
import domain.type.IType;
import domain.type.StringType;
import domain.value.IValue;
import domain.value.StringValue;
import exceptions.MyException;

public abstract class FileStatement implements IStmt {
    protected IExpression expression;
    protected StringValue getExpressionStringValue(IMyTable<String, FileDesc> fileTable, IMyTable<String, IValue> symTable, Heap heap) throws MyException {
        IValue expressionValue=expression.eval(symTable, heap);
        if (!expressionValue.sameType(new StringType())){
            throw new MyException(String.format("Expression %s should evaluate to string!!!", expressionValue.toString()));
        }
        StringValue expressionStringValue=(StringValue) expressionValue;
        return expressionStringValue;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        IType expressionType=expression.typecheck(typeEnvironment);
        if (!expressionType.equals(new StringType()))
            throw new MyException("Expression should evaluate to string in : "+this);
        return typeEnvironment;
    }
}
