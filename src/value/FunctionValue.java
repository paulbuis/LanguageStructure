package value;

import java.util.List;
import expression.Expression;
import environment.Environment;

public record FunctionValue(List<String> formalParameters, Expression body, Environment definitionEnvironment)
        implements FunctionInterface {
    public Value apply(List<Value> actualParameters) {
        Environment newEnvironment = new Environment(definitionEnvironment, formalParameters, actualParameters);
        return body.eval(newEnvironment);
    }
}
