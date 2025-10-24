package model.statements;

import model.PrgState;
import exceptions.MyException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepCopy();
}
