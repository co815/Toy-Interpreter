package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.values.IValue;
import model.values.RefValue;

public class WriteHeapStmt implements IStmt {
    private final String varName;
    private final IExp expression;

    public WriteHeapStmt(String varName, IExp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        if (!symTable.isDefined(varName)) {
            throw new MyException("Variable " + varName + " is not defined.");
        }
        IValue varValue = symTable.getValue(varName);
        if (!(varValue instanceof RefValue)) {
            throw new MyException("Variable " + varName + " is not a reference type.");
        }
        int address = ((RefValue) varValue).getAddress();
        if (!heap.containsKey(address)) {
            throw new MyException("Address " + address + " is not allocated in the heap.");
        }
        IValue expValue = expression.eval(symTable, heap);
        IType expType = expValue.getType();
        IType locationType = ((RefValue) varValue).getLocationType();
        if (!expType.equals(locationType)) {
            throw new MyException("Expression type and reference location type do not match.");
        }
        heap.update(address, expValue);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expression.toString() + ")";
    }
}