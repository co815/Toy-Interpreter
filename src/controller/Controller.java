package controller;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.statements.IStmt;
import repository.IRepository;

public class Controller {
    private final IRepository repo;

    public Controller(IRepository _repo) {
        this.repo = _repo;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        if(stack.isEmpty()) {
            throw new MyException("PrgState stack is empty.");
        }
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();
        if(prg == null) {
            throw new MyException("Repository is empty.");
        }
        while(!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            System.out.println(prg);
        }
    }
}
