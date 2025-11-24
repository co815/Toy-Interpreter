package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeapExp implements IExp {
    private final IExp exp;

    public ReadHeapExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIDictionary<Integer, IValue> heap) throws MyException {
        IValue val = exp.eval(tbl, heap);
        if (!(val instanceof RefValue)) {
            throw new MyException("Expression is not a RefValue.");
        }

        RefValue refVal = (RefValue) val;
        int address = refVal.getAddr();

        if (!heap.isDefined(address)) {
            throw new MyException("Address " + address + " is not defined in the heap.");
        }

        return heap.getValue(address);
    }

    @Override
    public IExp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }
}
