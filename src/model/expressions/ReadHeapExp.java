package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeapExp implements IExp {
    private final IExp expression;

    public ReadHeapExp(IExp expression) {
        this.expression = expression;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> heap) throws MyException {
        IValue expValue = expression.eval(tbl, heap);

        if (!(expValue instanceof RefValue)) {
            throw new MyException("Expression is not a reference value.");
        }

        int address = ((RefValue) expValue).getAddress();

        if (!heap.containsKey(address)) {
            throw new MyException("Address " + address + " is not allocated in the heap.");
        }

        return heap.get(address);
    }

    @Override
    public IExp deepCopy() {
        return new ReadHeapExp(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }
}
