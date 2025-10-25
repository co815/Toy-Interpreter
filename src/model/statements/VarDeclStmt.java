package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;
import model.values.IValue;

public class VarDeclStmt implements IStmt {
    private final String id;
    private final IType type;

    public VarDeclStmt(String _id, IType _type) {
        this.id = _id;
        this.type = _type;
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.id;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(this.id, this.type);
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if(symTable.isDefined(this.id)) {
            throw new MyException("Variable '" + this.id + "' is already declared.");
        }
        symTable.put(this.id, this.type.defaultValue());
        return state;
    }
}
