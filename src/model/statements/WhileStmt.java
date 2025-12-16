package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStmt implements IStmt {
    private final IExp expression;
    private final IStmt statement;

    public WhileStmt(IExp expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IValue condition = expression.eval(symTable, state.getHeap());
        if (!condition.getType().equals(new BoolType())) {
            throw new MyException("Condition expression is not a boolean.");
        }
        if (((BoolValue) condition).getBool()) {
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "(while (" + expression.toString() + ") " + statement.toString() + ")";
    }
}