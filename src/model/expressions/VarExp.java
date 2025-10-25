package model.expressions;

import model.adt.MyIDictionary;
import model.values.IValue;

public class VarExp implements IExp {
    private final String id;

    public VarExp(String _id) {
        this.id = _id;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dict) {
        return dict.getValue(this.id);
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public IExp deepCopy() {
        return new VarExp(this.id);
    }
}
