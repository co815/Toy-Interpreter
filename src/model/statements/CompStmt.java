package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIStack;

public class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt second;

    public CompStmt(IStmt _first, IStmt _second) {
        this.first = _first;
        this.second = _second;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(this.second);
        stk.push(this.first);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(this.first.deepCopy(), this.second.deepCopy());
    }

    @Override
    public String toString() {
        return "( " + first.toString() + ";" + second.toString() + " )";
    }
}