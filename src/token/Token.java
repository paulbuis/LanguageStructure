package token;


import org.jetbrains.annotations.NotNull;

public final class Token {
    public enum Name {
        OPEN_PAREN,
        CLOSE_PAREN,
        COMMA,
        OPEN_CURLY_BRACKET,
        CLOSE_CURLY_BRACKET,
        COLON,
        RATIONAL_LITERAL,
        REAL_LITERAL,
        FUNCTION,
        LAMBDA,
        LET,
        IF,
        THEN,
        ELSE,
        AND,
        OR,
        NOT,
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        EQUAL,
        NOT_EQUAL,
        GREATER_THAN,
        GREATER_OR_EQUAL,
        LESS_THAN,
        LESS_OR_EQUAL,
        IDENTIFIER,
        WHITE_SPACE
    }

    private final Name _name;
    private final CharSequence _lexeme;
    private Token(final Name name, final CharSequence lexeme) {
        this._name = name;
        this._lexeme = lexeme;
    }

    public static @NotNull Token makeToken(final Name name, final CharSequence lexeme) {
        return new Token(name, lexeme);
    }

    public CharSequence lexeme() {
        return this._lexeme;
    }

    public Name name() {
        return this._name;
    }

    @Override
    public String toString() {
        return String.format("%s, \"%s\"", _name, _lexeme);
    }

}
