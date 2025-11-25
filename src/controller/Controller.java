package controller;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repo;

    public Controller(IRepository _repo) {
        this.repo = _repo;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        if (stack.isEmpty()) {
            throw new MyException("PrgState stack is empty.");
        }
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();
        if (prg == null) {
            throw new MyException("Repository is empty.");
        }
        repo.logPrgStateExec();
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            repo.logPrgStateExec();
            prg.getHeap().setContent(safeGarbageCollector(
                    getAddrFromSymTable(prg.getSymTable().getContent().values()),
                    prg.getHeap().getContent()));
            repo.logPrgStateExec();
        }
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddresses, Map<Integer, IValue> heap) {
        Set<Integer> reachableAddresses = new HashSet<>(symTableAddresses);
        boolean changed = true;
        while (changed) {
            changed = false;
            List<Integer> newReachable = heap.values().stream()
                    .filter(v -> v instanceof RefValue)
                    .map(v -> ((RefValue) v).getAddress())
                    .filter(address -> !reachableAddresses.contains(address) && address != 0)
                    .collect(Collectors.toList());

            if (!newReachable.isEmpty()) {
                reachableAddresses.addAll(newReachable);
                changed = true;
            }
        }
        return heap.entrySet().stream()
                .filter(e -> reachableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .collect(Collectors.toList());
    }
}
