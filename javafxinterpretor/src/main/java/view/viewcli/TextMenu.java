package view.viewcli;

import domain.my_data_structures.my_table.IMyTable;
import domain.my_data_structures.my_table.MyTable;

import java.util.Scanner;

public class TextMenu {
    private IMyTable<String, Command> commands;
    public TextMenu(){
        commands=new MyTable<>();
    }
    public void addCommand(Command command){
        this.commands.put(command.getKey(), command);
    }
    private void printMenu(){
        for (var command:commands.values()){
            System.out.println(command.getKey()+")"+command.getDescription());
        }
    }
    public void show(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chose an option:");
        while(true){
            printMenu();
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            try {
                Command command = commands.get(key);
                command.execute();
            }catch (Exception e ){
                System.out.println("INVALID option!!!!");
            }
        }
    }
}
