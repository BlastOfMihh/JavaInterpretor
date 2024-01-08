package domain.statement;

import domain.program_state.ProgramState;
import domain.expression.IExpression;
import domain.my_data_structures.my_table.IMyTable;
import domain.type.IType;
import domain.type.RefType;
import domain.value.IValue;
import domain.value.RefValue;
import exceptions.MyException;
import domain.program_state.heap.Heap;

public class NewStatement implements IStmt{
    String varName;
    IExpression expression;
    public NewStatement(String varName, IExpression expression){
        this.expression=expression;
        this.varName=varName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Heap heap = state.getHeap();
        IMyTable<String, IValue> symTable=state.getSymTable();
        if (!(symTable.containsKey(varName)))
            throw new MyException(String.format("%s does not exist!!"+this, varName));
        if (! (symTable.get(varName) instanceof RefValue reference))
            throw new MyException(String.format("Not the same type "+this.toString(), varName));
        IValue evaluatedExpression=expression.eval(symTable, heap);
        if (! reference.getLocationType().getClass().equals(evaluatedExpression.getType().getClass())){
            throw new MyException(String.format("Inner type not equal "+this.toString(), varName));
        }
        int newAddress=heap.addEntry(evaluatedExpression.deepCopy());
        reference.setAdress(newAddress);
        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        IType variableType=typeEnvironment.get(varName);
        IType expressionType=expression.typecheck(typeEnvironment);
        if (!variableType.getClass().equals(RefType.class))
            throw new MyException("Variable type not of reftype"+this);
        if (!((RefType) variableType).getInnerType().equals(expressionType))
            throw new MyException("Inner type not equal to variable type in : "+this);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "NewStatement{" +
                "varName='" + varName + '\'' +
                ", expression=" + expression +
                '}';
    }
}
