package domain.value;

import domain.type.IType;
import domain.type.RefType;

public class RefValue implements IValue {
    private int adress;
    private IType locationType;
    public RefValue(IType locationType, int adress){
        this.locationType=locationType;
        this.adress=adress;
    }
    public IType getLocationType(){
        return locationType;
    }
    @Override
    public IType getType() {
        return new RefType(locationType);
    }
    @Override
    public boolean sameType(IValue value) {
        return false;
    }

    @Override
    public boolean sameType(IType type) {
        return false;
    }
    public int getAdress(){
        return adress;
    }
    public void setAdress(int adress){
        this.adress=adress;
    }

    @Override
    public String toString() {
        return "RefValue{" +
                "adress=" + adress +
                ", locationType=" + locationType +
                '}';
    }
}
