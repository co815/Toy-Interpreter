package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class NewStmt implements IStmt {
    private final String varName;
    private final IExp exp;

    public NewStmt(String varName, IExp exp) {
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
        IValue varValue = symTable.getValue(varName);
        if (!(varValue.getType() instanceof RefType)) {
            throw new MyException("Variable " + varName + " is not of RefType.");
        }
        IValue evaluated = exp.eval(symTable, heap);
        RefType refType = (RefType) varValue.getType();
        if (!evaluated.getType().equals(refType.getInner())) {
            throw new MyException("Type mismatch: expected " + refType.getInner() + " but got " + evaluated.getType());
        }
        int newAddress = 1;
        while (heap.isDefined(newAddress)) {
            newAddress++;
        }
        heap.put(newAddress, evaluated);
        symTable.put(varName, new RefValue(newAddress, evaluated.getType()));
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + exp.toString() + ")";
    }
}
