package domain.value;
import domain.type.IType;
import domain.type.IntType;
import exceptions.MyException;


public class IntValue extends Value<Integer>{
    public IntValue(Integer value){
        super(value);
    }
    public IntValue(){
        super(0);
    }
    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
