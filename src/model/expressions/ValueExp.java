package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.values.IValue;

public class ValueExp implements IExp {
    private final IValue value;

    public ValueExp(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> heap) throws MyException {
        return this.value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public IExp deepCopy() {
        return new ValueExp(this.value);
    }
}
