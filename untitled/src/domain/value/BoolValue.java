package domain.value;

import domain.type.BoolType;
import domain.type.IType;

public class BoolValue extends Value<Boolean>{
    public BoolValue(Boolean value){
        super(value);
    }
    public BoolValue(){
        super(false);
    }
    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public IValue deepCopy(){
        return new BoolValue(getValue());
    }
}
