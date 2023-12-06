package controller;

import domain.program_state.ProgramState;
import exceptions.MyException;
import repository.IRepository;

import java.util.Vector;

public class Controller {
    private final IRepository<ProgramState> repository;
    boolean displayExecution;
    public Controller(IRepository<ProgramState> repository) {
        this.repository = repository;
    }
    public void setDisplayExecution(boolean value){
        displayExecution=value;
    }
    public void completeProgramExecution() throws MyException {
        var all = repository.getAll();
        String ouput = new String();
        Vector<ProgramState> inExecution = (Vector<ProgramState>) all.clone();
        if(displayExecution)
            repository.logRepo();
        while (!inExecution.isEmpty()) {
            for (int i = 0; i < inExecution.size(); ++i) {
                var currentProgram = inExecution.elementAt(i);
                if (currentProgram.isCompleted()) {
                    ouput += currentProgram.getOutput()+"\n";//+"\n;;;;;;;;;\n";
                    inExecution.remove(i--);
                } else {
                    currentProgram.execute(); //oneStepExecution(currentProgram);
                    currentProgram.collectGarbage();
                    if (displayExecution){
                        repository.logRepo();
                    }
                }
            }
            if (displayExecution) {
                repository.logRepo();
            }
        }
        System.out.print(String.format("Output:\n%sEndOuput\n\n", ouput));
    }

    public void addProgramToExecution(ProgramState program) {
        repository.add(program);
    }
}
