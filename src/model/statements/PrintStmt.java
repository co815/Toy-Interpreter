package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.expressions.IExp;
import model.values.IValue;

public class PrintStmt implements IStmt {
    private final IExp exp;

    public PrintStmt(IExp _exp) {
        this.exp = _exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<IValue> out = state.getOut();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IValue value = this.exp.eval(symTable, state.getHeap());
        out.add(value);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }
}
