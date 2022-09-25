package lexer;

import static token.Token.Name.*;


import token.Token.Name;
import token.Token;

import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

// static import
import static java.util.Map.entry;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;

import java.text.Normalizer;

public class Lexer {

    private static final Map<Name, String> regexMap = new TreeMap(Map.ofEntries(
            entry(OPEN_PAREN, "\\("),
            entry(CLOSE_PAREN, "\\)"),
            entry(COMMA, ","),
            entry(OPEN_CURLY_BRACKET, "\\{"),
            entry(CLOSE_CURLY_BRACKET, "\\}"),
            entry(COLON, ":"),
            entry(RATIONAL_LITERAL, "(0[xX][0-9a-fA-F]+)|(0[bB][01]+)|(-?[0-9]+(/[0-9]+)?)"),
            entry(REAL_LITERAL, "(-?[0-9]+(\\.[0-9]*)?)|(-?[0-9]\\.[0-9]*([eE][+-]?[0-9]+))"),
            entry(FUNCTION, "function"),
            entry(LAMBDA, "lambda"),
            entry(LET, "let"),
            entry(AND, "and"),
            entry(OR, "or"),
            entry(NOT, "not|\\u00ac"),
            entry(IF, "if"),
            entry(THEN, "then"),
            entry(ELSE, "else"),
            entry(PLUS, "\\+"),
            entry(MINUS, "-|\\u2212"),
            entry(MULTIPLY, "\\*|\\u00d7|\\u22c5"),
            entry(DIVIDE, "/|\\u00f7"),
            entry(EQUAL, "=="),
            entry(NOT_EQUAL, "!=|\\u2260"),
            entry(GREATER_THAN, ">"),
            entry(GREATER_OR_EQUAL, ">=|\\u2265"),
            entry(LESS_THAN, "<"),
            entry(LESS_OR_EQUAL, "<=|\\u2264"),
            entry(Name.IDENTIFIER, "[a-zA-Z][a-zA-Z0-9]*"),
            entry(Name.WHITE_SPACE, "(\\h|\\v)+")
    ));

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
        final Map<Name, CharSequence> matchMap = new LinkedHashMap<>();
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
        // TODO: need to handle maxMatchName == null && maxMatch.length() == 0
        return Token.makeToken(maxMatchName, maxMatch);
    }

    private CharSequence input; // not final!!!

    public Lexer(CharSequence input) {
        this.input =  Normalizer.normalize(input, Normalizer.Form.NFKD);
    }

    public List<Token> tokenize() {
        final ArrayList<Token> tokens = new ArrayList<>();
        while (!input.isEmpty()) {
            final Token nextToken = matchLongest();
            input = input.subSequence(nextToken.lexeme().length(), input.length());
            tokens.add(nextToken);
        }
        return tokens;
    }

    public static void main(String[] args) {
        String input = "+-*/:,if then else and or not 1. 2.5 2/3 7";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        for (Token token: tokens) {
            System.out.printf("%s\n", token);
        }
    }


}
