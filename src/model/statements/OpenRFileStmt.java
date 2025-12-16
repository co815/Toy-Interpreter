package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStmt implements IStmt {
    private final IExp exp;

    public OpenRFileStmt(IExp _exp) {
        this.exp = _exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue value = this.exp.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType())) {
            throw new MyException("Expression must be a string.");
        }
        StringValue fileName = (StringValue) value;
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (fileTable.isDefined(fileName)) {
            throw new MyException("File " + fileName.getValue() + " is already open.");
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName.getValue()));
            fileTable.put(fileName, br);
        } catch (FileNotFoundException e) {
            throw new MyException("File " + fileName.getValue() + " not found.");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (exp.typecheck(typeEnv).equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("OpenRFileStmt: Expression is not a string.");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "openRFile(" + this.exp.toString() + ")";
    }
}
