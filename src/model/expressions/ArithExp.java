package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithExp implements IExp {
    private final IExp e1;
    private final IExp e2;
    private final int op;

    public ArithExp(IExp _e1, IExp _e2, int _op) {
        this.e1 = _e1;
        this.e2 = _e2;
        this.op = _op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> heap) throws MyException {
        IValue v1 = e1.eval(tbl, heap);
        if (!v1.getType().equals(new IntType())) {
            throw new MyException("First operand is not an integer.");
        }
        IValue v2 = e2.eval(tbl, heap);
        if (!v2.getType().equals(new IntType())) {
            throw new MyException("Second operand is not an integer.");
        }
        int n1 = ((IntValue) v1).getValue();
        int n2 = ((IntValue) v2).getValue();
        return switch (op) {
            case 1 -> new IntValue(n1 + n2);
            case 2 -> new IntValue(n1 - n2);
            case 3 -> new IntValue(n1 * n2);
            case 4 -> {
                if (n2 == 0) {
                    throw new MyException("Division by zero.");
                }
                yield new IntValue(n1 / n2);
            }
            default -> throw new MyException("Invalid arithmetic operator code: " + op);
        };
    }

    @Override
    public IExp deepCopy() {
        return new ArithExp(this.e1.deepCopy(), this.e2.deepCopy(), this.op);
    }

    @Override
    public String toString() {
        String opStr = switch (op) {
            case 1 -> "+";
            case 2 -> "-";
            case 3 -> "*";
            case 4 -> "/";
            default -> "?";
        };
        return "(" + e1.toString() + " " + opStr + " " + e2.toString() + ")";
    }
}
