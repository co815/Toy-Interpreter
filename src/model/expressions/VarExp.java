package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.values.IValue;

public class VarExp implements IExp {
    private final String id;

    public VarExp(String _id) {
        this.id = _id;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dict) throws MyException {
        if(!dict.isDefined(this.id)) {
            throw new MyException("Variable " + this.id + " is not defined.");
        }
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
