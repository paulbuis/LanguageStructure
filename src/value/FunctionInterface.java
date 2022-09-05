package value;

import java.util.List;

import java.util.function.Function;

public sealed interface FunctionInterface extends Value, Function<List<Value>, Value>
    permits FunctionValue
{
    Value apply(List<Value> actualParameters);
}
