package parser;

import java.util.List;

import token.Token.Name;
import static token.Token.Name.*;
import token.Token;

import expression.Expression;

public abstract class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = List.copyOf(tokens);
    }

    protected Token peek() {
        return tokens.get(current);
    }

    protected Token previous() {
        return tokens.get(current - 1);
    }

    protected boolean isAtEnd() {
        //return peek().name() == Name.END;
        return current == tokens.size();
    }

    protected boolean check(Name name) {
        if (isAtEnd()) {
            return false;
        }
        return peek().name() == name;
    }

    protected Token advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    protected boolean match(Name... names) {
        for (Name name : names) {
            if (check(name)) {
                advance();
                return true;
            }
        }
        return false;
    }

    public abstract Expression parse();
}
