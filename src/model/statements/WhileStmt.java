package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStmt implements IStmt {
    private final IExp exp;
    private final IStmt stmt;

    public WhileStmt(IExp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new BoolType())) {
            throw new MyException("Condition expression is not a boolean.");
        }

        BoolValue boolVal = (BoolValue) val;
        if (boolVal.getBool()) {
            MyIStack<IStmt> stack = state.getExeStack();
            stack.push(this);
            stack.push(stmt);
        }

        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "while (" + exp.toString() + ") " + stmt.toString();
    }
}
