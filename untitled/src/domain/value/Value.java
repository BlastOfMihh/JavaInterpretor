package domain.value;

import domain.type.IType;

public abstract class Value<T> implements IValue{
    private final T value;
    public Value(T value){
        this.value=value;
    }
    public T getValue(){
        return value;
    }
    public boolean sameType(IValue value){
        return value.getClass().equals(this.getClass());
    }
    public boolean sameType(IType type){
        return sameType(type.newDefaultValue());
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj.getClass().equals(this.getClass())))
            return false;
        return ((Value<?>) obj).value.equals(value);
    }

    @Override
    public String toString() {
        return String.format("Type:%s Value:%s", this.getType().toString(), value.toString());
    }
}
