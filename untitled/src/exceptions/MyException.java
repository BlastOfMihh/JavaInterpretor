package exceptions;

public class MyException extends Exception{
    public MyException() {
        super("");
    }
    public MyException(String message){
        super(message);
    }
    public String toString(){
        return this.getMessage();
    }
}
