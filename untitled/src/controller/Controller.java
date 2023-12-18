package controller;

import domain.my_data_structures.my_table.IMyTable;
import domain.my_data_structures.my_table.MyTable;
import domain.program_state.ProgramState;
import domain.program_state.heap.Heap;
import domain.value.IValue;
import exceptions.MyException;
import repository.IRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository<ProgramState> repository;
    boolean displayExecution;
    ExecutorService executor;
    public Controller(IRepository<ProgramState> repository) {
        this.repository = repository;
        executor = Executors.newFixedThreadPool(2);
    }
    public void setDisplayExecution(boolean value){
        displayExecution=value;
    }
    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programs){
        return programs.stream()
                .filter(program->!program.isCompleted())
                .collect(Collectors.toList());
    }
    public List<ProgramState> cleanHeaps(List<ProgramState> programs){
        Heap newOverallHeap= new Heap();
        programs.forEach(program-> {
            newOverallHeap.setMinAvailableAdress(program.getHeap().getAvailableAdress());
            Map<Integer, IValue> newCurrHeap=program.getNewCleanedHeap();
            newOverallHeap.putAll(newCurrHeap);
        });
        programs.stream().forEach(program->program.setHeap(newOverallHeap));
        return programs;
    }
    public void completeProgramsExecution() throws MyException {
        List<ProgramState> programList=repository.getAll();
        while(programList.size()>0){
            // old way ; programList.forEach(programState -> programState.collectGarbage());
            programList=cleanHeaps(programList);
            executeOneStepForEachProgram(programList);
            programList=removeCompletedPrograms(programList);
            repository.setProgramList(programList);
            //programList=cleanHeaps(programList);
        }
        executor.shutdown();
    }
    private void executeOneStepForEachProgram(List<ProgramState> programList) throws MyException{ // allSteps method
        repository.logRepo();
        List<Callable<ProgramState>> callables=programList.stream()
                .map((ProgramState p) ->  (Callable<ProgramState>) (  ()->p.executeOneStep()  )  )
                .collect(Collectors.toList());
        // starting execution
        List<ProgramState> newPrgList;
        try{
            newPrgList = executor.invokeAll(callables) .stream()
                    .map(programStateFuture -> {
                        try {
                            return programStateFuture.get();
                        }catch(Exception exception){
                            //throw new MyException(exception.getMessage());
                            // hope nothing happens
                            System.out.print(exception.getMessage());//not good code
                            return null;
                        }
                    }).filter(p->p!=null)
                    .collect(Collectors.toList());
        }catch(Exception e){
            throw new MyException(e.getMessage());
        }
        programList.addAll(newPrgList);
        repository.setProgramList(programList);
        repository.logRepo();
    }
///    public void completeProgramExecution() throws MyException {
///        var all = repository.getAll();
///        StringBuilder output = new StringBuilder(new String());
///        Vector<ProgramState> inExecution = (Vector<ProgramState>) all.clone();
///        if(displayExecution)
///            repository.logRepo();
///        while (!inExecution.isEmpty()) {
///            for (int i = 0; i < inExecution.size(); ++i) {
///                var currentProgram = inExecution.elementAt(i);
///                if (currentProgram.isCompleted()) {
///                    output.append(currentProgram.getOutput()).append("\n");//+"\n;;;;;;;;;\n";
///                    inExecution.remove(i--);
///                } else {
///                    currentProgram.execute(); //oneStepExecution(currentProgram);
///                    currentProgram.collectGarbage();
///                    if (displayExecution){
///                        repository.logRepo();
///                    }
///                }
///            }
///           // if (displayExecution) {
///           //     repository.logRepo();
///           // }
///        }
///        System.out.print(String.format("Output:\n%sEndOuput\n\n", output.toString()));
///    }
///
    public void addProgramToExecution(ProgramState program) {
        repository.add(program);
    }
}
