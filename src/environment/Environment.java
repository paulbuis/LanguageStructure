package environment;

import value.FunctionInterface;
import value.Numeric;
import value.Bool;
import value.Value;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public final class Environment {
    private final Environment nonLocalEnvironment;
    private final Map<String, Value> map;

    public Environment(final List<String> names, final List<Value> values) {
        this.nonLocalEnvironment = null;
        this.map = bindParameters(names, values);
    }
    public Environment(final Environment nonLocalEnvironment, final List<String> names, final List<Value> values) {
        this.nonLocalEnvironment = nonLocalEnvironment;
        this.map = bindParameters(names, values);
    }

    private Map<String, Value> bindParameters(final List<String> names, final List<Value> values){
        Map<String, Value> map = new HashMap<>();
        for (int i=0; i<names.size(); i++) {
            map.put(names.get(i), values.get(i));
        }
        return map;
    }

    public Value lookupValue(final String name) {
        Value localValue =  map.get(name);
        if (localValue != null) {
            return localValue;
        }
        if (nonLocalEnvironment != null) {
            return nonLocalEnvironment.lookupValue(name);
        }
        throw new RuntimeException(String.format("%s, no such name", name));
    }
    public Numeric lookupNumeric(final String name) {
        return (Numeric)lookupValue(name);
    }
    public Bool lookupBool(final String name) {
        return (Bool)lookupValue(name);
    }
    public FunctionInterface lookupFunction(final String name) {
        return (FunctionInterface) lookupValue(name);
    }
}
