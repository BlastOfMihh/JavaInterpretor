package controller;

import domain.my_data_structures.my_list.IMyList;
import domain.program_state.ProgramState;
import domain.program_state.heap.IHeap;
import domain.value.IValue;
import exceptions.MyException;
import repository.IRepository;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ProgramController {
    private final IRepository<ProgramState> repository;
    IMyList<String> outputLog;
    IHeap heap;
    boolean displayExecution;
    ExecutorService executor;
    public ProgramController(IRepository<ProgramState> repository) {
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
        IHeap newOverallHeap= new IHeap();
        programs.forEach(program-> {
            newOverallHeap.setMinAvailableAdress(program.getHeap().getAvailableAdress());
            Map<Integer, IValue> newCurrHeap=program.getNewCleanedHeap();
            newOverallHeap.putAll(newCurrHeap);
        });
        programs.stream().forEach(program->program.setHeap(newOverallHeap));
        this.heap=newOverallHeap;
        return programs;
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
    public void executeOneStep() throws MyException {
        List<ProgramState> programList=repository.getAll();
        programList=cleanHeaps(programList);
        executeOneStepForEachProgram(programList);
        programList=removeCompletedPrograms(programList);
        repository.setProgramList(programList);
        // old way ; programList.forEach(programState -> programState.collectGarbage());
    }
    public void completeProgramsExecution() throws MyException {
        while(repository.getAll().size()>0){
            executeOneStep();
        }
        executor.shutdown();
    }
    public void addProgramToExecution(ProgramState program) {
        repository.add(program);
        outputLog=program.getOutputLog();
        heap=program.getHeap();
    }
    public Vector<ProgramState>getListOfPrograms (){
        return repository.getAll();
    }
    public List<String> getOutputLog(){
        return (List<String>) outputLog;
    }
    public IHeap getHeap() {
        return heap;
    }
}
