package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    private final IExp exp;

    public CloseRFileStmt(IExp _exp) {
        this.exp = _exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue value = this.exp.eval(state.getSymTable());
        if (!value.getType().equals(new StringType())) {
            throw new MyException("File path expression is not a string.");
        }
        StringValue fileName = (StringValue) value;
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName)) {
            throw new MyException("File " + fileName.getValue() + " is not open.");
        }
        BufferedReader br = fileTable.getValue(fileName);
        try {
            br.close();
        } catch (IOException e) {
            throw new MyException("Error closing file " + fileName.getValue() + ": " + e.getMessage());
        }
        fileTable.getContent().remove(fileName);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + this.exp.toString() + ")";
    }
}