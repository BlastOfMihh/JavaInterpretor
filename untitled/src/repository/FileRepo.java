package repository;

import exceptions.MyException;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;

import java.util.Vector;

public class FileRepo<T> extends Repository<T>{

    String fileName;
    public FileRepo(String fileName) throws MyException {
        super();
        this.fileName=fileName;
        try{
            FileWriter writer= new FileWriter(fileName);
            //writer.write("");
        }catch(Exception e){
            throw new MyException(e.getMessage());
        }
    }
    public void logRepo() throws MyException {
        PrintWriter writer;
        try{
            writer= new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            for (var item : items){
                writer.append(item.toString());
            }
            writer.close();
        }catch(Exception e){
            throw new MyException(e.getMessage());
        }
    }
}
