package domain.type;

public abstract class Type implements IType{
    @Override
    public String toString() {
        return String.format("%s",this.getClass());
    }

    @Override
    public boolean equals(IType obj) {
        return obj.getClass().equals(this.getClass());
    }
   // public IType deepCopy(){
   //     try{
   //         return this.getClass().newInstance();
   //     }catch (Exception e){
   //         return null; //should not be the case
   //     }
   // }
}
