package repository;

import exceptions.MyException;
import model.PrgState;
import java.util.List;

public interface IRepository {
    void addPrgState(PrgState state);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> list);
    void logPrgStateExec(PrgState prgState) throws MyException;
}
