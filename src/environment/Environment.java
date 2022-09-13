package environment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import value.FunctionInterface;
import value.Numeric;
import value.Bool;
import value.Value;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public final class Environment {
    private final @Nullable Environment nonLocalEnvironment;
    private final @NotNull Map<String, Value> map;

    public static final Environment emptyEnvironment = new Environment();

    private Environment() {
        map = new HashMap<>();
        nonLocalEnvironment = null;
    }

    public Environment(final List<String> names, final @NotNull List<Value> values) {
        this.nonLocalEnvironment = null;
        this.map = bindParameters(names, values);
    }
    public Environment(final Environment nonLocalEnvironment, final List<String> names, final @NotNull List<Value> values) {
        this.nonLocalEnvironment = nonLocalEnvironment;
        this.map = bindParameters(names, values);
    }

    private @NotNull Map<String, Value> bindParameters(final @Nullable List<String> names, final @NotNull List<Value> values){
        Map<String, Value> map = new HashMap<>();
        if (names != null) {
            for (int i = 0; i < names.size(); i++) {
                map.put(names.get(i), values.get(i));
            }
        }
        return map;
    }

    public @NotNull Value lookupValue(final String name) {
        Value localValue =  map.get(name);
        if (localValue != null) {
            return localValue;
        }
        if (nonLocalEnvironment != null) {
            return nonLocalEnvironment.lookupValue(name);
        }
        throw new RuntimeException(String.format("%s, no such name", name));
    }
    public @NotNull Numeric lookupNumeric(final String name) {
        return (Numeric)lookupValue(name);
    }
    public @NotNull Bool lookupBool(final String name) {
        return (Bool)lookupValue(name);
    }
    public @NotNull FunctionInterface lookupFunction(final String name) {
        return (FunctionInterface) lookupValue(name);
    }
}
