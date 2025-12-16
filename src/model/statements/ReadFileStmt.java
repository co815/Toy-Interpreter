package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    private final IExp exp;
    private final String varName;

    public ReadFileStmt(IExp _exp, String _varName) {
        this.exp = _exp;
        this.varName = _varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (!symTable.isDefined(this.varName)) {
            throw new MyException("Variable " + this.varName + " is not defined.");
        }
        if (!symTable.getValue(this.varName).getType().equals(new IntType())) {
            throw new MyException("Variable " + this.varName + " is not of type int.");
        }
        IValue value = this.exp.eval(symTable, state.getHeap());
        if (!value.getType().equals(new StringType())) {
            throw new MyException("File path expression is not a string.");
        }
        StringValue fileName = (StringValue) value;
        if (!fileTable.isDefined(fileName)) {
            throw new MyException("File " + fileName.getValue() + " is not open.");
        }
        BufferedReader br = fileTable.getValue(fileName);
        try {
            String line = br.readLine();
            int readValue;
            if (line == null) {
                readValue = 0;
            } else if (line.trim().isEmpty()) {
                readValue = 0;
            } else {
                readValue = Integer.parseInt(line);
            }
            symTable.put(this.varName, new IntValue(readValue));
        } catch (IOException e) {
            throw new MyException("Error reading from file " + fileName.getValue() + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new MyException("File " + fileName.getValue() + " contains non-integer data.");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (!exp.typecheck(typeEnv).equals(new StringType())) {
            throw new MyException("ReadFileStmt: File path must be a string.");
        }
        if (!typeEnv.getValue(varName).equals(new IntType())) {
            throw new MyException("ReadFileStmt: Variable must be an integer.");
        }
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(this.exp.deepCopy(), this.varName);
    }

    @Override
    public String toString() {
        return "readFile(" + this.exp.toString() + ", " + this.varName + ")";
    }
}
