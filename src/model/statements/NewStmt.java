package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class NewStmt implements IStmt {
    private final String varName;
    private final IExp expression;

    public NewStmt(String varName, IExp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        if (!symTable.isDefined(varName)) {
            throw new MyException("Variable " + varName + " is not defined.");
        }
        IValue varValue = symTable.getValue(varName);
        IType varType = varValue.getType();
        if (!(varType instanceof RefType)) {
            throw new MyException("Variable " + varName + " is not a reference type.");
        }
        IValue expValue = expression.eval(symTable, heap);
        IType expType = expValue.getType();
        IType innerType = ((RefType) varType).getInner();
        if (!expType.equals(innerType)) {
            throw new MyException("Expression type and reference inner type do not match.");
        }
        int newAddress = heap.allocate(expValue);
        symTable.put(varName, new RefValue(newAddress, innerType));
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.getValue(varName);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression.toString() + ")";
    }
}
