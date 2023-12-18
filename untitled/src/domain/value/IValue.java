package domain.value;

import domain.type.IType;
import exceptions.MyException;

public interface IValue extends Cloneable{
    public IType getType();
    public boolean sameType(IValue value);
    public boolean sameType(IType type);
    public IValue deepCopy();
}
