package view;

import controller.Controller;
import exceptions.MyException;

import java.util.Scanner;

public class RunExampleCommand extends Command{
    private final Controller controller;
    public RunExampleCommand(String key, String description, Controller controller){
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
            controller.completeProgramExecution();
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
    }
}
