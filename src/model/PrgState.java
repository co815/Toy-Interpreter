package model;

import exceptions.MyException;
import model.adt.*;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, IValue> symTable;
    private final MyIList<IValue> out;
    private final MyIDictionary<StringValue, BufferedReader> fileTable;
    private final MyIHeap<Integer, IValue> heap;
    private final IStmt originalPrg;
    private final int id;
    private static int nextId = 1;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot, MyIDictionary<StringValue, BufferedReader> fileTbl, MyIHeap<Integer, IValue> heap, IStmt prg) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.fileTable = fileTbl;
        this.heap = heap;
        this.originalPrg = prg.deepCopy();
        this.exeStack.push(prg);
        this.id = getNextId();
    }

    private static synchronized int getNextId() {
        return nextId++;
    }

    public int getId() {
        return this.id;
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

    public MyIHeap<Integer, IValue> getHeap() {
        return this.heap;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if (exeStack.isEmpty()) {
            throw new MyException("PrgState stack is empty");
        }
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString() {
        return "Id: " + this.id + "\nExeStack: " + this.exeStack + "\n" + "SymTable: " + this.symTable + "\n" + "FileTable: " + this.fileTable + "\n" + "Heap: " + this.heap + "\n" + "Out: " + this.out + "\n";
    }
}