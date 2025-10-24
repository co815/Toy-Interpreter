package model;

import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.adt.MyIList;
import model.statements.IStmt;
import model.values.IValue;


public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, IValue> symTable;
    MyIList<IValue> out;

    private final IStmt originalPrg;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot, IStmt prg) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.originalPrg = prg.deepCopy();
        stk.push(prg);
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    @Override
    public String toString() {
        return "ExeStack: " + exeStack + "\n" + "SymTable: " + symTable + "\n"+"Out: " + out;
    }
}
