package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStmt implements IStmt {
    private final IExp exp;
    private final IStmt thenS;
    private final IStmt elseS;

    public IfStmt(IExp _exp, IStmt _thenS, IStmt _elseS) {
        this.exp = _exp;
        this.thenS = _thenS;
        this.elseS = _elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IValue condValue = exp.eval(symTable);
        if (!condValue.getType().equals(new BoolType())) {
            throw new MyException("IF condition is not boolean.");
        }
        BoolValue boolVal = (BoolValue) condValue;
        if (boolVal.getBool()) {
            stack.push(thenS);
        } else {
            stack.push(elseS);
        }
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(this.exp.deepCopy(), this.thenS.deepCopy(), this.elseS.deepCopy());
    }

    @Override
    public String toString() {
        return "IF(" + this.exp.toString() + ") THEN(" + this.thenS.toString() + ") ELSE(" + this.elseS.toString() + ")";
    }
}
