package domain.type;

import domain.value.IValue;

public interface IType {
    public IValue newDefaultValue();
    public boolean equals(IType obj) ;
    //public IType deepCopy();
}
