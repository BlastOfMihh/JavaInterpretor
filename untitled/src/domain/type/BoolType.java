package domain.type;

import domain.value.BoolValue;
import domain.value.IValue;

public class BoolType implements IType{
    public IValue newDefaultValue(){
        return new BoolValue();
    }

    @Override
    public String toString() {
        return "BoolType";
    }
}
