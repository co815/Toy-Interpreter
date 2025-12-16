package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExp implements IExp {
    private final IExp e1;
    private final IExp e2;
    private final String op;

    public RelationalExp(IExp _e1, IExp _e2, String _op) {
        this.e1 = _e1;
        this.e2 = _e2;
        this.op = _op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> heap) throws MyException {
        IValue v1 = e1.eval(tbl, heap);
        if(!v1.getType().equals(new IntType())) {
            throw new MyException("First operand is not an integer.");
        }
        IValue v2 = e2.eval(tbl, heap);
        if(!v2.getType().equals(new IntType())) {
            throw new MyException("Second operand is not an integer.");
        }
        int n1 = ((IntValue) v1).getValue();
        int n2 = ((IntValue) v2).getValue();
        return switch (op) {
            case "<" -> new BoolValue(n1 < n2);
            case "<=" -> new BoolValue(n1 <= n2);
            case "==" -> new BoolValue(n1 == n2);
            case "!=" -> new BoolValue(n1 != n2);
            case ">" -> new BoolValue(n1 > n2);
            case ">=" -> new BoolValue(n1 >= n2);
            default -> throw new MyException("Invalid relational operator: " + this.op);
        };
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else throw new MyException("Second operand is not an integer");
        } else throw new MyException("First operand is not an integer");
    }

    @Override
    public IExp deepCopy() {
        return new RelationalExp(this.e1.deepCopy(), this.e2.deepCopy(), this.op);
    }

    @Override
    public String toString() {
        return "(" + e1.toString() + " " + op + " " + e2.toString() + ")";
    }
}
