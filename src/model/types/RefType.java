package model.types;

import model.values.RefValue;
import model.values.IValue;

public class RefType implements IType {
    private final IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefType) {
            return inner.equals(((RefType) another).getInner());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }
}
