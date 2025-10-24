package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue {
    private boolean bool;

    public BoolValue(boolean b) {
        this.bool = b;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    public boolean getBool() {
        return this.bool;
    }

    @Override
    public String toString() {
        return String.valueOf(this.bool);
    }
}
