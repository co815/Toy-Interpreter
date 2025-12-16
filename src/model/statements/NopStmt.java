package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;

public class NopStmt implements IStmt {

    public NopStmt() {}

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public String toString() {
        return "nop";
    }
}
