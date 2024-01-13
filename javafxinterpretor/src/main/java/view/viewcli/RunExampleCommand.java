package view.viewcli;

import controller.ProgramController;
import exceptions.MyException;

import java.util.Scanner;

public class RunExampleCommand extends Command{
    private final ProgramController controller;
    public RunExampleCommand(String key, String description, ProgramController controller){
        super(key, description);
        this.controller=controller;
    }

    @Override
    public void execute() {
        try{
            System.out.println("Do you want to seee the execution steps? (y/n)");
            Scanner readOption=new Scanner(System.in);
            String option=readOption.next();
            controller.setDisplayExecution(option.equals("y"));
            controller.completeProgramsExecution();
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
    }
}
