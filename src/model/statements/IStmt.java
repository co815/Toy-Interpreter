package model.statements;

import model.PrgState;
import exceptions.MyException;

public interface IStmt {
    public PrgState execute(PrgState state) throws MyException;
    public IStmt deepCopy();
}
