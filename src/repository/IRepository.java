package repository;

import model.PrgState;
import java.util.List;

public interface IRepository {
    void addPrgState(PrgState state);
    PrgState getCrtPrg();
    List<PrgState> getPrgList();
}
