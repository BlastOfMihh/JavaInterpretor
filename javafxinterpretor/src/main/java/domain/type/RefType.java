package domain.type;
import domain.value.IValue;
import domain.value.RefValue;

public class RefType extends Type{
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
            return innerType.equals( ((RefType) obj).innerType );
        return false;
    }
    public IType getInnerType(){
        return innerType;
    }
    @Override
    public String toString() {
        return "RefType";
    }
}

