package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue {
    private final int address;
    private final IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {
        return this.address;
    }

    public IType getLocationType() {
        return this.locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof RefValue)) {
            return false;
        }
        RefValue otherRef = (RefValue) other;
        return this.address == otherRef.getAddr() && this.locationType.equals(((RefValue) other).getLocationType());
    }

    @Override
    public int hashCode() {
        return this.address + this.locationType.hashCode();
    }

    @Override
    public String toString() {
        return "(" + this.address + ", " + this.locationType.toString() + ")";
    }
}
