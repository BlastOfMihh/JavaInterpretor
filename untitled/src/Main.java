import domain.ProgramState;
import repository.Repository;

public class Main {
    public static void main(String[] args) {
        Repository<ProgramState> repository = new Repository<ProgramState>();
        Controller controller=new Controller(repository);
        View view=new View(controller);
        view.addExample3();
        //view.addExample1();
        view.run();
    }
}