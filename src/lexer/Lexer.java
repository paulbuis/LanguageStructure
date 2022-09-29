package lexer;

import static token.Token.Name.*;
import token.Token.Name;
import token.Token;
import token.Location;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


import java.text.Normalizer;


public class Lexer {

    Location location;

    private static final LinkedHashMap<Name, String> regexMap = new LinkedHashMap<>();
    static {
        regexMap.put(OPEN_PAREN, "\\(");
        regexMap.put(CLOSE_PAREN, "\\)");
        regexMap.put(COMMA, ",");
        regexMap.put(OPEN_CURLY_BRACKET, "\\{");
        regexMap.put(CLOSE_CURLY_BRACKET, "\\}");
        regexMap.put(COLON, ":");
        regexMap.put(RATIONAL_LITERAL, "(0[xX][0-9a-fA-F]+)|(0[bB][01]+)|(-?[0-9]+(/[0-9]+)?)");
        regexMap.put(REAL_LITERAL, "(-?[0-9]+(\\.[0-9]*)?)|(-?[0-9]\\.[0-9]*([eE][+-]?[0-9]+))");
        regexMap.put(FUNCTION, "function");
        regexMap.put(LAMBDA, "lambda");
        regexMap.put(LET, "let");
        regexMap.put(AND, "and");
        regexMap.put(OR, "or");
        regexMap.put(NOT, "not|\\u00ac");
        regexMap.put(IF, "if");
        regexMap.put(THEN, "then");
        regexMap.put(ELSE, "else");
        regexMap.put(PLUS, "\\+");
        regexMap.put(MINUS, "-|\\u2212");
        regexMap.put(MULTIPLY, "\\*|\\u00d7|\\u22c5");
        regexMap.put(DIVIDE, "/|\\u00f7");
        regexMap.put(EQUAL, "==");
        regexMap.put(NOT_EQUAL, "!=|\\u2260");
        regexMap.put(GREATER_THAN, ">");
        regexMap.put(GREATER_OR_EQUAL, ">=|\\u2265");
        regexMap.put(LESS_THAN, "<");
        regexMap.put(LESS_OR_EQUAL, "<=|\\u2264");
        regexMap.put(IDENTIFIER, "[a-zA-Z][a-zA-Z0-9]*");
        regexMap.put(HORIZONTAL_WHITE_SPACE, "\\h+");
        regexMap.put(VERTICAL_WHITE_SPACE, "\\n|(\\r\\n?)");
    }

    private static final Map<Name, Pattern> patternMap = new LinkedHashMap<>();
    // static initializer
    static {
        for(Name tokenName : regexMap.keySet()) {
            final Pattern pattern = Pattern.compile(regexMap.get(tokenName));
            Lexer.patternMap.put(tokenName, pattern);
        }
    }

    private Token matchLongest()
    {
        CharSequence maxMatch = "";
        Name maxMatchName = null;
        for (Name tokenName: patternMap.keySet()) {
            final Pattern pattern = patternMap.get(tokenName);
            final Matcher matcher = pattern.matcher(input);
            if (matcher.lookingAt()) {
                final CharSequence match = input.subSequence(0, matcher.end());
                if (match.length() > maxMatch.length()) {
                    maxMatch = match;
                    maxMatchName = tokenName;
                }
            }
        }

        if (maxMatchName == null || maxMatch.length() == 0) {
            return Token.makeToken(ERROR, "", location);

        }
        // TODO: need to handle maxMatchName == null && maxMatch.length() == 0
        final String maxMatchString = maxMatch.toString();
        final Token resultToken = Token.makeToken(maxMatchName, maxMatchString, location);
        if (maxMatchName.equals(VERTICAL_WHITE_SPACE)) {
            location = location.newLine();
        } else {

            location = location.increment(maxMatchString);
        }
        return resultToken;
    }

    private CharSequence input; // not final!!!
    private final String sourceName;

    public Lexer(String sourceName, CharSequence input) {
        this.sourceName = sourceName;
        this.location = new Location(sourceName, 1, 0, 0, 0);
        this.input =  Normalizer.normalize(input, Normalizer.Form.NFKD);
    }

    public List<Token> tokenize() {
        final ArrayList<Token> tokens = new ArrayList<>();
        while (!input.isEmpty()) {
            final Token nextToken = matchLongest();
            input = input.subSequence(nextToken.lexeme().length(), input.length());
            tokens.add(nextToken);
            if (nextToken.name().equals(ERROR)) {
                System.err.printf("Illegal token at %s\n", location);
                break;
            }
        }
        return tokens;
    }

    public static void main(String[] args) {
        String input = "+-*/:,if then<=<>else!=and==or not-1.\n2.5\t2/3 -7\rxyz 0x1\r\n0b11 0";
        Lexer lexer = new Lexer("test string", input);
        List<Token> tokens = lexer.tokenize();
        for (Token token: tokens) {
            System.out.printf("%s\n", token);
        }
    }
}
