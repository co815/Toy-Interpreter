package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.values.IValue;

public interface IExp {
    IValue eval(MyIDictionary<String, IValue> tbl, MyIDictionary<Integer, IValue> heap) throws MyException;
    IExp deepCopy();
}
