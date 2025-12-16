package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
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
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return value.getType();
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
