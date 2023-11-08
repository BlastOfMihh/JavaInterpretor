import domain.ProgramState;
import domain.expression.*;
import domain.my_list.IMyList;
import domain.my_list.MyList;
import domain.my_stack.IMyStack;
import domain.my_stack.MyStack;
import domain.my_table.IMyTable;
import domain.my_table.MyTable;
import domain.statement.*;
import domain.type.BoolType;
import domain.type.IntType;
import domain.value.BoolValue;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.MyException;

public class View {
    private Controller controller;
    public View(Controller controller){
        this.controller=controller;
    }
    public void addState(IStmt ex){
        MyTable<String, IValue> symTable=new MyTable<String,IValue>();
        MyList<String> outputLog=new MyList<String>();
        MyStack<IStmt> executionStack=new MyStack<IStmt>();
        executionStack.push(ex);
        ProgramState program=new ProgramState(symTable, outputLog, executionStack);
        controller.addProgramToExecution(program);
    }
    public void addExample1(){
        //Example1:
        //int v; v=2;Print(v) is represented as:
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        addState(ex1);
    }
    public void addExample2(){
        ///int a;int b; a=2+3*5;b=a+1;Print(b) is represented as:
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(BinaryExpression.OperationTypes.plus,new ValueExp(new IntValue(2)),new
                                ArithExp(BinaryExpression.OperationTypes.multiplication ,new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp(BinaryExpression.OperationTypes.plus,new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        addState(ex2);
    }
    public void addExample3(){
        //Example3:
        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v) is represented as
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new NopStmt()))));
        addState(ex3);
    }
    public void run(){
        try{
            controller.commpleteProgramExecution();
        }catch (MyException e){
            System.out.print(e.toString());
        }
    }
}
