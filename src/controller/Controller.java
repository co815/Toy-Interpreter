package controller;

import exceptions.MyException;
import model.PrgState;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository _repo) {
        this.repo = _repo;
    }

    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (!prgList.isEmpty()) {
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }
    private void oneStepForAllPrg(List<PrgState> prgList) throws MyException {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new MyException(e.getMessage());
        }
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });
        repo.setPrgList(prgList);
        if (!prgList.isEmpty()) {
            PrgState firstPrg = prgList.get(0);
            firstPrg.getHeap().setContent(
                    safeGarbageCollector(
                            getAllSymTableAddresses(prgList),
                            firstPrg.getHeap().getContent()
                    )
            );
        }
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
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
                    .toList();
            if (!newReachable.isEmpty()) {
                reachableAddresses.addAll(newReachable);
                changed = true;
            }
        }
        return heap.entrySet().stream()
                .filter(e -> reachableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAllSymTableAddresses(List<PrgState> prgList) {
        return prgList.stream()
                .flatMap(p -> p.getSymTable().getContent().values().stream())
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .collect(Collectors.toList());
    }

}