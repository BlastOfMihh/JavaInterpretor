package view.viewgui;


import controller.ProgramController;
import domain.program_state.ProgramState;
import domain.program_state.heap.IHeap;
import domain.program_state.latch_table.ILatchTable;
import domain.value.IValue;
import exceptions.MyException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExecuteProgramController extends StatedEPC {
    public TableView<Pair<Integer, IValue>> heapTableView;
    public TableColumn<Pair<Integer, IValue>, Integer> heapAddressColumn;
    public TableColumn<Pair<Integer, IValue>, String> heapValueColumn;
    public ListView<String> outputListView;
    public ListView<String> fileListView;
    public ListView<Integer> programStatesListView;
    public ListView<String> executionStackListView;
    public TableView<Pair<String, IValue>> symbolTableView;
    public TableColumn<Pair<String, IValue>, String> symbolTableVariableNameColumn;
    public TableColumn<Pair<String, IValue>, String> symbolTableValueColumn;
    public TextField numberOfProgramStates;
    public Button executeOneStepButton;
    private ProgramController programController;
    public void setProgramController(ProgramController programController){
        this.programController=programController;
    }
    @FXML Label programStateLabel;


    static boolean showSemaphore=true;
    public VBox semaphorePane;
    public TableView<Pair<Integer, Pair<Integer, List<Integer>>>> semaphoreView;
    public TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> semaphoreIndexColumn;
    public TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> semaphoreValueColumn;
    public TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, String> semaphoreListIntColumn;


    boolean showLatchPane=false;
    public VBox latchPane;
    public TableView<Pair<Integer,Integer>> latchView;
    public TableColumn<Pair<Integer, Integer>, Integer> latchAddressColumn;
    public TableColumn<Pair<Integer, Integer>, Integer> latchValueColumn;

    @Override
    protected void enterExecuting() {
        programStateLabel.setText("Executing");
    }
    @Override
    protected void enterIdle() {
        programStateLabel.setText("Waiting for program");
        //updateGui();
    }
    @Override
    protected void enterFinished() {
        programStateLabel.setText("Finished");
    }

    public void initialize() {
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        symbolTableVariableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        symbolTableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        programStatesListView.setOnMouseClicked(mouseEvent -> updateGui());

        semaphorePane.setVisible(showSemaphore);
        semaphoreIndexColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        semaphoreValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(   p.getValue().getValue().getKey()  ).asObject());
        semaphoreListIntColumn.setCellValueFactory(
                p -> new SimpleStringProperty(
                        p.getValue().getValue().getValue().stream().map(x->x.toString()).reduce(new String(), (ac, elem)->ac+" "+elem)
                )
        );

        latchAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        latchValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(   p.getValue().getValue()  ).asObject());
        latchPane.setVisible(showLatchPane);
    }

    private void updateProgramIDsView() {
        List<ProgramState> listOfPrograms = programController.getListOfPrograms();
        List<Integer> idList = listOfPrograms.stream().map(ProgramState::getId).toList();
        programStatesListView.setItems(FXCollections.observableList(idList));
        numberOfProgramStates.setText(Integer.toString(listOfPrograms.size()));
    }
    private void updateHeap() {
        //List<Integer> dummy=new ArrayList<>();
        //dummy.add(2);
        //dummy.add(2);
        //dummy.add(2);
        //heapAddressColumn.set
        IHeap heap = new IHeap();
        if (!programController.getListOfPrograms().isEmpty())
            heap = programController.getListOfPrograms().get(0).getHeap();
            //heap = programController.getHeap();
        List<Pair<Integer, IValue>> heapTableList = new ArrayList<Pair<Integer, IValue>>();
        for (var entry : heap.entrySet())
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }
    private void updateFileTableView() {
        ArrayList<String> namesOfFiles = new ArrayList<>();
        if (!programController.getListOfPrograms().isEmpty())
            namesOfFiles = new ArrayList<String>(programController.getListOfPrograms().get(0).getFileTable().keySet());
        fileListView.setItems(FXCollections.observableArrayList(namesOfFiles));
    }
    private void updateOutputView() {
        List<String> output = new ArrayList<>();
            //output = controller.getListOfPrograms().get(0).getOutputList().getContentAsListOfStrings();
        output = (List<String>) programController.getOutputLog();
        outputListView.setItems(FXCollections.observableList(output));
        outputListView.refresh();
    }

    private ProgramState getCurrentProgram(){
        if (programController.getListOfPrograms().size() == 0)
            return null;
        int currentId = programStatesListView.getSelectionModel().getSelectedIndex();
        if (currentId == -1)
            return programController.getListOfPrograms().get(0);
        return programController.getListOfPrograms().get(Math.min(currentId, programController.getListOfPrograms().size()-1));
    }

    private void updateExecutionStackView() {
        ProgramState currentProgram = getCurrentProgram();
        List<String> executionStackAsListOfStrings = new ArrayList<>();
        if (currentProgram!=null)
            executionStackAsListOfStrings = currentProgram.getExecutionStack().stream().map(iStmt -> iStmt.toString()).collect(Collectors.toList());
        executionStackListView.setItems(FXCollections.observableList(executionStackAsListOfStrings));
        executionStackListView.refresh();
    }

    private void updateSymbolTableView() {
        ProgramState currentProgram = getCurrentProgram();
        List<Pair<String, IValue>> symbolTableList = new ArrayList<>();
        if (currentProgram!=null)
            for (Map.Entry<String, IValue> entry : currentProgram.getSymTable().entrySet())
                symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        symbolTableView.setItems(FXCollections.observableList(symbolTableList));
        symbolTableView.refresh();
    }
    protected void updateSemaphore(){
        ProgramState currentProgram = getCurrentProgram();
        List<Pair<Integer, Pair<Integer, List<Integer>>>> semTableList = new ArrayList<>();
        if (currentProgram!=null)
            for (Map.Entry<Integer, kotlin.Pair<Integer, List<Integer>>> entry : currentProgram.getSemaphoreTable().entrySet())
                semTableList.add(new Pair<>(entry.getKey(), new Pair<>(entry.getValue().getFirst(), entry.getValue().getSecond())));
        semaphoreView.setItems(FXCollections.observableList(semTableList));
        semaphoreView.refresh();
    }
    private void updateLatch() {
        ProgramState currentProgram = getCurrentProgram();
        List<Pair<Integer, Integer>> latchTableList = new ArrayList<>();
        if (currentProgram!=null)
            for (Map.Entry<Integer, Integer> entry : currentProgram.getLatchTable().entrySet())
                latchTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        latchView.setItems(FXCollections.observableList(latchTableList));
        latchView.refresh();
    }
    protected void updateGui(){
        updateExecutionStackView();
        updateSymbolTableView();
        updateOutputView();
        updateFileTableView();
        updateProgramIDsView();
        updateHeap();
        if (showSemaphore)
            updateSemaphore();
        if (showLatchPane)
            updateLatch();
    }

    @FXML
    protected void onExecuteButton(){
        if (currentState==States.EXECUTING){
            if (programController.getListOfPrograms().isEmpty())
                stateChangeRequst(States.FINISHED);
            else
                try{
                    programController.executeOneStep();
                    updateGui();
                }catch (MyException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                    stateChangeRequst(States.FINISHED);
                }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No program ready for execution", ButtonType.OK);
            alert.showAndWait();
            stateChangeRequst(States.IDLE);
        }
    }

}
