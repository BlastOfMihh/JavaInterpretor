package old_view;

import controller.Controller;
import domain.program_state.ProgramState;
import exceptions.MyException;
import repository.FileRepo;
import repository.Repository;


public class Main {
    public static void main(String[] args) throws MyException {
        if(false)
            return;
        Repository<ProgramState> repository = new FileRepo<ProgramState>("repolog.txt");
        Controller controller=new Controller(repository);
        View view=new View(controller);
        view.addFileExample();
        view.run();
    }
}