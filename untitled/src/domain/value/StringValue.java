package domain.value;
import domain.type.IType;
import domain.type.StringType;


public class StringValue extends Value<String>{
    public StringValue(String value){
        super(value);
    }
    public StringValue(){
        super("");
    }
    @Override
    public IType getType() {
        return new StringType();
    }
}
