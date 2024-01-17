package view.viewgui;

import controller.ProgramController;
import domain.FileDesc;
import domain.expression.*;
import domain.my_data_structures.my_list.MyList;
import domain.my_data_structures.my_stack.MyStack;
import domain.my_data_structures.my_table.MyTable;
import domain.program_state.ProgramState;
import domain.program_state.heap.Heap;
import domain.statement.*;
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
import view.viewcli.RunExampleCommand;
import view.viewcli.TextMenu;

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
        Heap heap=new Heap();
        MyTable<String, IValue> symTable=new MyTable<String,IValue>();
        MyList<String> outputLog=new MyList<String>();
        MyStack<IStmt> executionStack=new MyStack<IStmt>();
        MyTable<String, FileDesc> fileTable=new MyTable<String,FileDesc>();
        executionStack.push(ex);
        ProgramState program=new ProgramState(symTable,heap, outputLog, executionStack, fileTable);
        controller.addProgramToExecution(program);
        return controller;
    }
    private IStmt getRelationalExample(){
        IStmt RelationalExample= new CompStmt(
                new PrintStmt(new RelationalExpression(BinaryExpression.OperationTypes.equal, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(3)))),
                new PrintStmt(new RelationalExpression(BinaryExpression.OperationTypes.notEqual, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(3))))
        );
        return RelationalExample;
    }


    private IStmt addNimrodExamples() {
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
        IStmt ex = new CompStmt(
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
        return ex;
    }
    private IStmt addNop(IStmt stmt){
        return new CompStmt(stmt, new NopStmt());
    }
    private void addExamples(){
        programList.setItems(FXCollections.observableArrayList(
               addNop( getRelationalExample()),
               addNop( addNimrodExamples()),
               addNop( addThreadsExample())
        ));
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
