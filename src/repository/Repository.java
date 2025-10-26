package repository;

import model.PrgState;
import java.util.List;
import java.util.ArrayList;

public class Repository implements IRepository {
    private final List<PrgState> prgStates;

    public Repository() {
        this.prgStates = new ArrayList<>();
    }

    @Override
    public void addPrgState(PrgState state) {
        this.prgStates.add(state);
    }

    @Override
    public PrgState getCrtPrg() {
        if(this.prgStates.isEmpty()) {
            return null;
        }
        return this.prgStates.get(0);
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.prgStates;
    }
}
