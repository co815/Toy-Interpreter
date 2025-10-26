package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.IType;
import model.values.IValue;

public class AssignStmt implements IStmt {
    private final String id;
    private final IExp exp;

    public AssignStmt(String _id, IExp _exp) {
        this.id = _id;
        this.exp = _exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if(!symTable.isDefined(this.id)) {
            throw new MyException("Assignment error: variable '" + this.id + "' was not declared");
        }
        IValue value = this.exp.eval(symTable);
        IValue oldValue = symTable.getValue(this.id);
        IType typeId = oldValue.getType();
        if(!value.getType().equals(typeId)) {
            throw new MyException("Type mismatch in assignment to '" + this.id + "' expected " + typeId.toString() + " but got " + value.getType().toString());
        }
        symTable.put(this.id, value);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(this.id, this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }
}
