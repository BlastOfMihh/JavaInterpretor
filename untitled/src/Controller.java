import domain.ProgramState;
import exceptions.MyException;
import repository.IRepository;

import java.util.Vector;

public class Controller {
    private IRepository<ProgramState> repository;
    boolean displayExecution;

    public Controller(IRepository<ProgramState> repository) {
        this.repository = repository;
        displayExecution=true;
    }

    // public ProgramState oneStepExecution (ProgramState programState) throws MyException{
    //     if (programState.isCompleted())
    //         throw new MyException("EXECUTION already completed for this state");
    //     return programState.execute();
    // }
    public void commpleteProgramExecution() throws MyException {
        var all = repository.getAll();
        String ouput = new String();
        Vector<ProgramState> inExecution = (Vector<ProgramState>) all.clone();
        if(displayExecution)
            for (var curr : inExecution) {
                System.out.print(curr.toString() + "\n");
            }
        boolean finished = false;
        while (!finished) {
            finished = true;
            for (int i = 0; i < inExecution.size(); ++i) {
                var currentProgram = inExecution.elementAt(i);
                if (currentProgram.isCompleted()) {
                    ouput += currentProgram.getOutput()+"\n\n";//+"\n;;;;;;;;;\n";
                    inExecution.remove(i--);
                } else {
                    finished = false;
                    currentProgram.execute(); //oneStepExecution(currentProgram);
                    if (displayExecution)
                        System.out.print(currentProgram.toString() + "\n");
                }
            }
            if (displayExecution) {
                System.out.print("\n===========\n");
            }
        }
        System.out.print(String.format("Output:\n%s\nendOuput", ouput));
    }

    public void addProgramToExecution(ProgramState program) {
        repository.add(program);
    }
}
