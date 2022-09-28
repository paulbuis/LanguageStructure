package token;



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
        HORIZONTAL_WHITE_SPACE,
        VERTICAL_WHITE_SPACE
    }

    private final Name _name;
    private final String _lexeme;
    private final Location _location;

    private Token(final Name name, final String lexeme, Location location) {
        this._name = name;
        this._lexeme = lexeme;
        this._location = location;
    }

    public static Token makeToken(final Name name, final String lexeme, final Location location) {
        return new Token(name, lexeme, location);
    }

    public CharSequence lexeme() {
        return this._lexeme;
    }

    public Name name() {
        return this._name;
    }

    public Location location() { return this._location; }

    @Override
    public String toString() {
        return String.format("%s, \"%s\"\n%s\n", _name, _lexeme, _location);
    }

}
