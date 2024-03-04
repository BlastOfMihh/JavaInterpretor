package domain.statement;

import domain.expression.IExpression;
import domain.expression.VarExp;
import domain.expression.binary_expressions.ArithExp;
import domain.expression.binary_expressions.BinaryExpression;
import domain.expression.binary_expressions.RelationalExpression;
import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.ProgramState;
import domain.type.IType;
import domain.type.IntType;
import exceptions.MyException;

public class ForStmt implements IStmt{
    String itName; IExpression beginExp; IExpression endExp; IExpression incExp; IStmt instruction;
    public ForStmt(String itName, IExpression beginExp, IExpression endExp, IExpression incExp, IStmt instruction){
        this.itName=itName;
        this.endExp=endExp;
        this.incExp=incExp;
        this.instruction=instruction;
        this.beginExp=beginExp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var execStack=state.getExecutionStack();

        execStack.push(new WhileStmt(
                new RelationalExpression(
                        BinaryExpression.OperationTypes.smaller,
                        new VarExp(itName),
                        endExp
                ),
                new CompStmt(instruction, new AssignStmt(itName, incExp))
        ));
        execStack.push(new AssignStmt(itName, beginExp));
        execStack.push(new VarDeclStmt(itName, new IntType()));

        return null;
    }

    @Override
    public IMyTable<String, IType> typeCheck(IMyTable<String, IType> typeEnvironment) throws MyException {
        typeEnvironment.put(itName, new IntType());
        if (!endExp.typecheck(typeEnvironment).equals(new IntType()))
            throw new MyException("End Expression not of type INT in "+this);
        if (!incExp.typecheck(typeEnvironment).equals(new IntType()))
            throw new MyException("Increment Expression not of type INT in "+this);
        if (!beginExp.typecheck(typeEnvironment).equals(new IntType()))
            throw new MyException("Begin Expression not of type INT in "+this);
        instruction.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "ForStmt{" +
                "itName='" + itName + '\'' +
                ", beginExp=" + beginExp +
                ", endExp=" + endExp +
                ", incExp=" + incExp +
                ", instruction=" + instruction +
                '}';
    }
}
