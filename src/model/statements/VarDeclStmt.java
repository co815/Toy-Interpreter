package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class VarDeclStmt implements IStmt {
    private String id;
    private IType type;

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
        if(!symTable.isDefined(this.id)) {
            if(this.type.equals(new IntType())) {
                symTable.put(this.id, new IntValue(0));
            } else {
                symTable.put(this.id, new BoolValue(false));
            }
        } else {
            throw new MyException("This variable is already declared.");
        }
        return state;
    }
}
