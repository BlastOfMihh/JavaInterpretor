package domain.type;

import domain.value.StringValue;
import domain.value.IValue;

public class StringType implements IType{
    public IValue newDefaultValue(){
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "StringType";
    }
}
