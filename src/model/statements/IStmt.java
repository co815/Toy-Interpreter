package model.statements;

import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;
import exceptions.MyException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException;
    IStmt deepCopy();
}
