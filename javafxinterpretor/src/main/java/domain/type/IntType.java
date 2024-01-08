package domain.type;

import domain.value.IValue;
import domain.value.IntValue;

public class IntType extends Type {
    public IValue newDefaultValue(){
        return new IntValue();
    }

    @Override
    public String toString() {
        return "IntType";
    }
}
