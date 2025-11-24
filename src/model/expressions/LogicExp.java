package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicExp implements IExp {
    private final IExp e1;
    private final IExp e2;
    private final int op;

    public LogicExp(IExp _e1, IExp _e2, int _op) {
        this.e1 = _e1;
        this.e2 = _e2;
        this.op = _op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIDictionary<Integer, IValue> heap) throws MyException {
        IValue v1 = this.e1.eval(tbl, heap);
        if(!v1.getType().equals(new BoolType())) {
            throw new MyException("First operand is not a boolean.");
        }
        IValue v2 = this.e2.eval(tbl, heap);
        if(!v2.getType().equals(new BoolType())) {
            throw new MyException("Second operand is not a boolean.");
        }
        boolean b1 = ((BoolValue) v1).getBool();
        boolean b2 = ((BoolValue) v2).getBool();
        return switch (this.op) {
            case 1 -> new BoolValue(b1 && b2);
            case 2 -> new BoolValue(b1 || b2);
            default -> throw new MyException("Invalid logic operator code: " + this.op);
        };
    }

    @Override
    public IExp deepCopy() {
        return new LogicExp(this.e1.deepCopy(), this.e2.deepCopy(), this.op);
    }

    @Override
    public String toString() {
        String opStr = switch (this.op) {
            case 1 -> "and";
            case 2 -> "or";
            default -> "?";
        };
        return "(" + this.e1.toString() + " " + opStr + " " + this.e2.toString() + ")";
    }
}
