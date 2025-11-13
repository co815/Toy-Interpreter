package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue {
    private final String value;

    public StringValue(String _value) {
        this.value = _value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "\"" + this.value + "\"";
    }
}
