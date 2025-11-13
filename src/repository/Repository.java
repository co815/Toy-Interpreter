package repository;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Repository implements IRepository {
    private final List<PrgState> prgStates;
    private final String logFilePath;

    public Repository(PrgState initialState, String logFilePath) {
        this.prgStates = new ArrayList<>();
        this.prgStates.add(initialState);
        this.logFilePath = logFilePath;
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)))) {
            logFile.print("");
        } catch (IOException e) {
            System.err.println("Could not clear log file " + logFilePath + ". " + e.getMessage());
        }
    }

    @Override
    public void addPrgState(PrgState state) {
        this.prgStates.add(state);
    }

    @Override
    public PrgState getCrtPrg() {
        if (this.prgStates.isEmpty()) {
            return null;
        }
        return this.prgStates.get(0);
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.prgStates;
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            PrgState prgState = getCrtPrg();
            if (prgState == null) {
                return;
            }
            MyIStack<IStmt> exeStack = prgState.getExeStack();
            MyIDictionary<String, IValue> symTable = prgState.getSymTable();
            MyIList<IValue> out = prgState.getOut();
            MyIDictionary<StringValue, java.io.BufferedReader> fileTable = prgState.getFileTable();
            logFile.println("ExeStack: ");
            for (IStmt stmt : exeStack.getReversedData()) {
                logFile.println(stmt.toString());
            }
            logFile.println("SymTable: ");
            for (Map.Entry<String, IValue> entry : symTable.getContent().entrySet()) {
                logFile.println(entry.getKey() + " --> " + entry.getValue().toString());
            }
            logFile.println("Out: ");
            for (IValue val : out.getData()) {
                logFile.println(val.toString());
            }
            logFile.println("FileTable: ");
            for (StringValue fileName : fileTable.getContent().keySet()) {
                logFile.println(fileName.getValue());
            }
            logFile.println("\n");
        } catch (IOException e) {
            throw new MyException("Error writing to log file: " + e.getMessage());
        }
    }
}