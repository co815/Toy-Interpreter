package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyStack;
import model.adt.MyIStack;

public class ForkStmt implements IStmt {
    private final IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack = new MyStack<>();
        return new PrgState(newStack, state.getSymTable().deepCopy(), state.getOut(), state.getFileTable(), state.getHeap(), stmt);
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }
}