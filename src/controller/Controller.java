package controller;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
            Map<Integer, IValue> heapContent = prg.getHeap().getContent();
            List<Integer> reachable = getAddrFromSymTable(
                    prg.getSymTable().getContent().values(),
                    heapContent
            );
            heapContent.entrySet().removeIf(entry -> !reachable.contains(entry.getKey()));
            repo.logPrgStateExec();
        }
    }

    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues, Map<Integer, IValue> heap) {
        Set<Integer> reachable = symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddr())
                .collect(Collectors.toSet());
        boolean changed = true;
        while (changed) {
            changed = false;
            Set<Integer> newAddresses = new HashSet<>();
            for (Integer addr : reachable) {
                if (heap.containsKey(addr)) {
                    IValue val = heap.get(addr);
                    if (val instanceof RefValue) {
                        int nextAddr = ((RefValue) val).getAddr();
                        if (!reachable.contains(nextAddr)) {
                            newAddresses.add(nextAddr);
                        }
                    }
                }
            }
            if (!newAddresses.isEmpty()) {
                reachable.addAll(newAddresses);
                changed = true;
            }
        }
        return reachable.stream().collect(Collectors.toList());
    }
}
