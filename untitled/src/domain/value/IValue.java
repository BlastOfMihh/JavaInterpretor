package domain.value;

import domain.type.IType;
import exceptions.MyException;

public interface IValue {
    public IType getType();
    public boolean sameType(IValue value);
    public boolean sameType(IType type);
}
