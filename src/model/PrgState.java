package model;

import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.adt.MyIList;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, IValue> symTable;
    private final MyIList<IValue> out;
    private final MyIDictionary<StringValue, BufferedReader> fileTable;
    private final IStmt originalPrg;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot, MyIDictionary<StringValue, BufferedReader> fileTbl,IStmt prg) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.fileTable = fileTbl;
        this.originalPrg = prg.deepCopy();
        this.exeStack.push(prg);
    }

    public MyIStack<IStmt> getExeStack() {
        return this.exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return this.symTable;
    }

    public MyIList<IValue> getOut() {
        return this.out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    @Override
    public String toString() {
        return "ExeStack: " + this.exeStack + "\n" + "SymTable: " + this.symTable + "\n" + "FileTable: " + this.fileTable + "Out: " + this.out + "\n";
    }
}
