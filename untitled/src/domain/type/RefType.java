package domain.type;
import domain.value.IValue;
import domain.value.RefValue;

public class RefType implements IType{
    IType innerType;
    public RefType(IType innerType){
        this.innerType =innerType;
    }
    public IValue newDefaultValue(){
        return new RefValue(innerType, 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType)
            return innerType.equals(((RefType) obj).innerType);
        return false;
    }
    @Override
    public String toString() {
        return "RefType";
    }
}

