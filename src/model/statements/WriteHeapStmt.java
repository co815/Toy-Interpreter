package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.values.IValue;
import model.values.RefValue;

public class WriteHeapStmt implements IStmt {
    private final String varName;
    private final IExp exp;

    public WriteHeapStmt(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIDictionary<Integer, IValue> heap = state.getHeap();

        if (!symTable.isDefined(varName)) {
            throw new MyException("Variable " + varName + " is not defined.");
        }

        IValue val = symTable.getValue(varName);
        if (!(val instanceof RefValue)) {
            throw new MyException("Variable " + varName + " is not of RefType.");
        }

        RefValue refVal = (RefValue) val;
        int address = refVal.getAddr();

        if (!heap.isDefined(address)) {
            throw new MyException("Address " + address + " is not defined in the heap.");
        }

        IValue evaluated = exp.eval(symTable, heap);
        if (!evaluated.getType().equals(refVal.getLocationType())) {
            throw new MyException("Type mismatch: expected " + refVal.getLocationType() + " but got " + evaluated.getType());
        }

        heap.put(address, evaluated);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(varName, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp.toString() + ")";
    }
}
