package view.viewgui;


import controller.ProgramController;
import domain.program_state.ProgramState;
import domain.program_state.heap.Heap;
import domain.value.IValue;
import exceptions.MyException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
        System.out.print("yes epic");
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        symbolTableVariableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        symbolTableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        programStatesListView.setOnMouseClicked(mouseEvent -> updateGui());
    }

    private void populateProgramIDsView() {
        List<ProgramState> listOfPrograms = programController.getListOfPrograms();
        List<Integer> idList = listOfPrograms.stream().map(ProgramState::getId).toList();
        programStatesListView.setItems(FXCollections.observableList(idList));
        numberOfProgramStates.setText(Integer.toString(listOfPrograms.size()));
    }
    private void populateHeap() {
        //List<Integer> dummy=new ArrayList<>();
        //dummy.add(2);
        //dummy.add(2);
        //dummy.add(2);
        //heapAddressColumn.set
        Heap heap = new Heap();
        if (!programController.getListOfPrograms().isEmpty())
            heap = programController.getListOfPrograms().get(0).getHeap();
        List<Pair<Integer, IValue>> heapTableList = new ArrayList<Pair<Integer, IValue>>();
        for (var entry : heap.entrySet())
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }
    private void populateFileTableView() {
        ArrayList<String> namesOfFiles = new ArrayList<>();
        if (!programController.getListOfPrograms().isEmpty())
            namesOfFiles = new ArrayList<String>(programController.getListOfPrograms().get(0).getFileTable().keySet());
        fileListView.setItems(FXCollections.observableArrayList(namesOfFiles));
    }
    private void populateOutputView() {
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
        return programController.getListOfPrograms().get(currentId);
    }

    private void populateExecutionStackView() {
        ProgramState currentProgram = getCurrentProgram();
        List<String> executionStackAsListOfStrings = new ArrayList<>();
        if (currentProgram!=null)
            executionStackAsListOfStrings = currentProgram.getExecutionStack().stream().map(iStmt -> iStmt.toString()).collect(Collectors.toList());
        executionStackListView.setItems(FXCollections.observableList(executionStackAsListOfStrings));
        executionStackListView.refresh();
    }

    private void populateSymbolTableView() {
        ProgramState currentProgram = getCurrentProgram();
        List<Pair<String, IValue>> symbolTableList = new ArrayList<>();
        if (currentProgram!=null)
            for (Map.Entry<String, IValue> entry : currentProgram.getSymTable().entrySet())
                symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        symbolTableView.setItems(FXCollections.observableList(symbolTableList));
        symbolTableView.refresh();
    }
    protected void updateGui(){
        populateExecutionStackView();
        populateSymbolTableView();
        populateOutputView();
        populateFileTableView();
        populateProgramIDsView();
        populateHeap();
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
