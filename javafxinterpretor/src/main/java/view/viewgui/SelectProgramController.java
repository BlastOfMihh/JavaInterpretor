package view.viewgui;

import controller.ProgramController;
import domain.FileDesc;
import domain.expression.*;
import domain.expression.binary_expressions.ArithExp;
import domain.expression.binary_expressions.BinaryExpression;
import domain.expression.binary_expressions.RelationalExpression;
import domain.my_data_structures.my_list.MyList;
import domain.my_data_structures.my_stack.MyStack;
import domain.my_data_structures.my_table.MyTable;
import domain.program_state.ProgramState;
import domain.program_state.heap.IHeap;
import domain.program_state.latch_table.LatchTable;
import domain.program_state.semaphore_table.ISemaphoreTable;
import domain.program_state.semaphore_table.SemaphoreTable;
import domain.statement.*;
import domain.statement.latch.AwaitStmt;
import domain.statement.latch.CountDown;
import domain.statement.latch.NewLatchStmt;
import domain.statement.switch_statement.CaseSwitch;
import domain.statement.switch_statement.SwitchStmt;
import domain.statement.sync.AquireStmt;
import domain.statement.sync.CreateSemaphoreStmt;
import domain.statement.sync.ReleaseStmt;
import domain.type.IntType;
import domain.type.RefType;
import domain.value.IValue;
import domain.value.IntValue;
import domain.value.StringValue;
import exceptions.MyException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import repository.FileRepo;
import repository.Repository;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectProgramController {
    ExecuteProgramController executeWindow;
    public void setExecuteWindow(ExecuteProgramController sm){
        executeWindow=sm;
    }
    @FXML
    public Button executeButton;
    @FXML
    public ListView<IStmt> programList;
    public static ProgramController createNewProgramController(IStmt ex) throws MyException {
        ex.typeCheck(new MyTable<>());
        Repository<ProgramState> repository = new FileRepo<ProgramState>("repolog.txt");
        ProgramController controller=new ProgramController(repository);
        IHeap heap=new IHeap();
        MyTable<String, IValue> symTable=new MyTable<String,IValue>();
        MyList<String> outputLog=new MyList<String>();
        MyStack<IStmt> executionStack=new MyStack<IStmt>();
        MyTable<String, FileDesc> fileTable=new MyTable<String,FileDesc>();
        ISemaphoreTable semaphoreTable=new SemaphoreTable();
        executionStack.push(ex);
        ProgramState program=new ProgramState(symTable,heap, outputLog, executionStack, fileTable, semaphoreTable, new LatchTable());
        controller.addProgramToExecution(program);
        return controller;
    }
    IStmt getRelationalExample(){
        IStmt RelationalExample= new CompStmt(
                new PrintStmt(new RelationalExpression(BinaryExpression.OperationTypes.equal, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(3)))),
                new PrintStmt(new RelationalExpression(BinaryExpression.OperationTypes.notEqual, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(3))))
        );
        return RelationalExample;
    }
    IStmt addNimrodExamples() {
        // int v; Ref int a; v=10;new(a,22);
        //fork(wH(a,30);v=32;print(v);print(rH(a)));
        //print(v);print(rH(a))
        IStmt ex =
                new CompStmt(
                        new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                                new NewStatement("a", new ValueExp(new StringValue())))
                                //new NewStatement("a", new ValueExp(new (7))))
                        ,
                        new CompStmt(new VarDeclStmt("b", new RefType(new IntType())),
                                new NewStatement("b", new ValueExp(new IntValue(8))))
                );
        return ex;
    }
    IStmt addThreadsExample() {
        // int v; Ref int a; v=10;new(a,22);
        //fork(wH(a,30);v=32;print(v);print(rH(a)));
        //print(v);print(rH(a))
        return new CompStmt(
                new VarDeclStmt("v", new IntType()), new CompStmt(
                new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(
                new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(
                new NewStatement("a", new ValueExp(new IntValue(22))), new CompStmt(
                new ForkStmt(new CompStmt(
                        new HeapWriteStmt("a", new ValueExp(new IntValue(30))), new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(
                        new PrintStmt(new VarExp("v")),
                        new PrintStmt(new ReadHeapExp(new VarExp("a")))
                )
                )
                )
                ), new CompStmt(
                new PrintStmt(new VarExp("v")),
                new PrintStmt(new ReadHeapExp(new VarExp("a")))
        )
        )
        )
        )
        )
        );
    }
    IStmt addSwitchStatementEx(){
        //int a; int b; int c;
        //a=1;b=2;c=5;
        //(switch(a*10)
        //(case (b*c) : print(a);print(b))
        //(case (10) : print(100);print(200))
        //(default : print(300)));
        //print(300)
        IStmt ex=new CompStmt(
                new VarDeclStmt("a", new IntType()), new CompStmt(
                new VarDeclStmt("b", new IntType()), new CompStmt(
                new VarDeclStmt("c", new IntType()), new CompStmt(
                new AssignStmt("a", new ValueExp(new IntValue(1))), new CompStmt(
                new AssignStmt("b", new ValueExp(new IntValue(2))), new CompStmt(
                new AssignStmt("c", new ValueExp(new IntValue(5))), new CompStmt(
                new SwitchStmt(new ArithExp(BinaryExpression.OperationTypes.multiplication ,
                        new VarExp("a"), new ValueExp(new IntValue(10))),
                        new ArrayList<>(Arrays.asList(
                                new CaseSwitch(
                                        new ArithExp(ArithExp.OperationTypes.multiplication, new VarExp("b"), new VarExp("c")),
                                        new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b")))
                                ),
                                new CaseSwitch(
                                        new ValueExp(new IntValue(10)),
                                        new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200))))
                                )
                        )),
                        new PrintStmt(new ValueExp(new IntValue(300)))
                ),
                new PrintStmt(new ValueExp(new IntValue(300)))
            )
        )
        )
        )
        )
        )
        );
        return ex;
    }
    IStmt addSemaphoreEx(){
        // Ref int v1; int cnt;
        // new(v1,1);createSemaphore(cnt,rH(v1));
        // fork(acquire(cnt);wh(v1,rh(v1)*10));print(rh(v1));release(cnt));
        // fork(acquire(cnt);wh(v1,rh(v1)*10));wh(v1,rh(v1)*2));print(rh(v1));release(cnt
        // ));
        // acquire(cnt);
        // print(rh(v1)-1);
        // release(cnt)
        return new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),new CompStmt(
                new VarDeclStmt("cnt", new IntType()), new CompStmt(
                new NewStatement("v1", new ValueExp(new IntValue(1))), new CompStmt(
                new CreateSemaphoreStmt("cnt", new ReadHeapExp(new VarExp("v1"))), new CompStmt(
                new ForkStmt(new CompStmt(
                        new AquireStmt("cnt"), new CompStmt(
                        new HeapWriteStmt("v1", new ArithExp(BinaryExpression.OperationTypes.multiplication, new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                        new ReleaseStmt("cnt")
                )) )), new CompStmt(
                new ForkStmt(new CompStmt(
                        new AquireStmt("cnt"), new CompStmt(
                        new HeapWriteStmt("v1", new ArithExp(BinaryExpression.OperationTypes.multiplication, new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),new CompStmt(
                        new HeapWriteStmt("v1", new ArithExp(BinaryExpression.OperationTypes.multiplication, new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(2)))),new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                        new ReleaseStmt("cnt")
                ) ) ))), new CompStmt(
                new AquireStmt("cnt"), new CompStmt(
                new PrintStmt(new ArithExp(BinaryExpression.OperationTypes.minus, new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)))),
                new ReleaseStmt("cnt")
                ))
        )
        )
        ))
        )
        );
    }
    IStmt addConditionalExamples(){
        // Ref int a; Ref int b; int v;
        // new(a,0); new(b,0);
        // wh(a,1); wh(b,2);
        // v=(rh(a)<rh(b))?100:200;
        // print(v);
        // v= ((rh(b)-2)>rh(a))?100:200;
        // print(v);
        // The final Out should be {100,200}
        return new CompStmt(
                new VarDeclStmt("a",new RefType(new IntType())), new CompStmt(
                new VarDeclStmt("b",new RefType(new IntType())), new CompStmt(
                new VarDeclStmt("v", new IntType()), new CompStmt(
                new NewStatement("a", new ValueExp(new IntValue(0))), new CompStmt(
                new NewStatement("b", new ValueExp(new IntValue(0))), new CompStmt(
                new HeapWriteStmt("a", new ValueExp(new IntValue(1))), new CompStmt(
                new HeapWriteStmt("b", new ValueExp(new IntValue(2))), new CompStmt(
                new AssignStmt("v", new ConditionalExpression(
                        new RelationalExpression(BinaryExpression.OperationTypes.smaller,
                                new ReadHeapExp(new VarExp("a")),
                                new ReadHeapExp(new VarExp("b"))),
                        new ValueExp(new IntValue(100)),
                        new ValueExp(new IntValue(200)))
                ),new CompStmt(
                new PrintStmt(new VarExp("v")), new CompStmt(
                new AssignStmt("v", new ConditionalExpression(
                        new RelationalExpression(BinaryExpression.OperationTypes.greater,
                                new ArithExp(BinaryExpression.OperationTypes.minus, new ReadHeapExp(new VarExp("b")), new ValueExp(new IntValue(2))),
                                new ReadHeapExp(new VarExp("a"))),
                        new ValueExp(new IntValue(100)),
                        new ValueExp(new IntValue(200)))
                ),
                new PrintStmt(new VarExp("v")))
        )
                )
        )))))));
    }
    IStmt addLatchExample(){
        // The following program must be hard coded in your implementation.
        // Ref int v1; Ref int v2; Ref int v3; int cnt;
        // new(v1,2); new(v2,3); new(v3,4);newLatch(cnt,rH(v2));
        // fork(wh(v1,rh(v1)*10)); print(rh(v1)); countDown(cnt);
        // fork(wh(v2,rh(v2)*10)); print(rh(v2)); countDown(cnt);
        // fork(wh(v3,rh(v3)*10)); print(rh(v3)); countDown(cnt))));
        // await(cnt);
        // print(100);
        // countDown(cnt);
        // print(100)
        return new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),new CompStmt(
                new VarDeclStmt("v2", new RefType(new IntType())),new CompStmt(
                new VarDeclStmt("v3", new RefType(new IntType())),new CompStmt(
                new VarDeclStmt("cnt", new IntType()),new CompStmt(
                new NewStatement("v1",new ValueExp(new IntValue(2))),new CompStmt(
                new NewStatement("v2",new ValueExp(new IntValue(3))),new CompStmt(
                new NewStatement("v3",new ValueExp(new IntValue(4))),new CompStmt(
                new NewLatchStmt("cnt", new ReadHeapExp(new VarExp("v2"))), new CompStmt(
                new ForkStmt( new CompStmt(
                     new HeapWriteStmt("v1", new ArithExp(BinaryExpression.OperationTypes.multiplication, new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))), new CompStmt(
                     new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                     new CountDown("cnt")
                ) )), new CompStmt(
                new ForkStmt( new CompStmt(
                        new HeapWriteStmt("v2", new ArithExp(BinaryExpression.OperationTypes.multiplication, new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))), new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                        new CountDown("cnt")
                ) )), new CompStmt(
                new ForkStmt( new CompStmt(
                        new HeapWriteStmt("v3", new ArithExp(BinaryExpression.OperationTypes.multiplication, new ReadHeapExp(new VarExp("v3")), new ValueExp(new IntValue(10)))), new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v3"))),
                        new CountDown("cnt")
                ) )), new CompStmt(
                new AwaitStmt("cnt"), new CompStmt(
                new PrintStmt(new ValueExp(new IntValue(100))),new CompStmt(
                new CountDown("cnt"),
                new PrintStmt(new ValueExp(new IntValue(100))
        )))))))))))))));
    }
    private IStmt addNop(IStmt stmt){
        return new CompStmt(stmt, new NopStmt());
    }
    private void addExamples(){
        programList.setItems(FXCollections.observableArrayList(
                addNop( getRelationalExample()),
                addNop( addNimrodExamples()),
                addNop( addThreadsExample()),
                addNop(addSwitchStatementEx()),
                addNop(addSemaphoreEx()),
                addNop(addConditionalExamples()),
                addNop(addLatchExample())
        ) );
    }
    @FXML
    public void initialize(){
        //System.out.print("Lets fight the epic battle");
        addExamples();
    }
    @FXML
    protected void onExecuteButtonClick (){
        try{
            IStmt statement = programList.getSelectionModel().getSelectedItem();
            if (statement==null) throw new MyException("Please select a program");
            executeWindow.setProgramController(createNewProgramController(statement));
            executeWindow.stateChangeRequst(StatedEPC.States.EXECUTING);
        }catch (MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
