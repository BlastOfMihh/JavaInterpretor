package view;

import controller.Controller;
import domain.FileDesc;
import domain.program_state.ProgramState;
import domain.expression.BinaryExpression;
import domain.expression.RelationalExpression;
import domain.expression.ValueExp;
import domain.expression.VarExp;
import domain.program_state.heap.Heap;
import domain.my_data_structures.my_list.MyList;
import domain.my_data_structures.my_stack.MyStack;
import domain.my_data_structures.my_table.MyTable;
import domain.statement.*;
import domain.statement.file_statements.CloseRFile;
import domain.statement.file_statements.OpenRfSmt;
import domain.statement.file_statements.ReadFile;
import domain.type.IntType;
import domain.type.StringType;
import domain.value.IValue;
import domain.value.IntValue;
import domain.value.StringValue;
import exceptions.MyException;
import repository.FileRepo;
import repository.Repository;

public class Interpreter {

    public static void addState(Controller controller, IStmt ex){
        Heap heap=new Heap();
        MyTable<String, IValue> symTable=new MyTable<String,IValue>();
        MyList<String> outputLog=new MyList<String>();
        MyStack<IStmt> executionStack=new MyStack<IStmt>();
        MyTable<String, FileDesc> fileTable=new MyTable<String,FileDesc>();
        executionStack.push(ex);
        ProgramState program=new ProgramState(symTable,heap, outputLog, executionStack, fileTable);
        controller.addProgramToExecution(program);
    }
    static protected void addFileExample(TextMenu menu) throws MyException {
        // string varf;
        // varf="test.in";
        // openRFile(varf);
        // int varc;
        // readFile(varf,varc);print(varc);
        // readFile(varf,varc);print(varc)
        // closeRFile(varf)
        IStmt ex= new CompStmt(
                new VarDeclStmt("varf", new StringType()), new CompStmt(
                new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(
                new OpenRfSmt(new VarExp("varf")), new CompStmt(

                new VarDeclStmt("varc", new IntType()), new CompStmt(
                new ReadFile(new VarExp("varf"), "varc"), new CompStmt(
                new PrintStmt(new VarExp("varc")), new CompStmt(
                new ReadFile(new VarExp("varf"), "varc"), new CompStmt(
                new PrintStmt(new VarExp("varc")),
                new CloseRFile(new VarExp("varf"))
        ) ) ) ) ) ) ));
        Repository<ProgramState> repository = new FileRepo<ProgramState>("repolog.txt");
        Controller controller=new Controller(repository);
        addState(controller, ex);
        menu.addCommand(new RunExampleCommand("1", ex.toString(), controller));
    }
    static protected void addRelationalExample(TextMenu menu) throws MyException {
        // string varf;
        // varf="test.in";
        // openRFile(varf);
        // int varc;
        // readFile(varf,varc);print(varc);
        // readFile(varf,varc);print(varc)
        // closeRFile(varf)
        IStmt ex= new CompStmt(
                new PrintStmt(new RelationalExpression(BinaryExpression.OperationTypes.equal, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(3)))),
                new PrintStmt(new RelationalExpression(BinaryExpression.OperationTypes.notEqual, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(3))))
        );

        Repository<ProgramState> repository = new FileRepo<ProgramState>("repolog.txt");
        Controller controller=new Controller(repository);
        addState(controller, ex);
        menu.addCommand(new RunExampleCommand("2", ex.toString(), controller));
    }
    static protected void addHeapExample() throws MyException{
        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        //IStmt ex
    }
    public static void main(String[] args) throws MyException {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        try {
            //addFileExample(menu);
            addRelationalExample(menu);
            //add
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
        menu.show();
    }
}
