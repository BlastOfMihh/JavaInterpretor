package domain.type;

import domain.value.StringValue;
import domain.value.IValue;

public class StringType extends Type {
    public IValue newDefaultValue(){
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "StringType";
    }
}
