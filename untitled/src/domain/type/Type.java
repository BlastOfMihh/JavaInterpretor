package domain.type;

public abstract class Type implements IType{
    @Override
    public String toString() {
        return String.format("%s",this.getClass());
    }
}
