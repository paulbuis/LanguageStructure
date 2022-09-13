package framework;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.util.List;

import environment.Environment;
import lexer.Lexer;
import org.jetbrains.annotations.NotNull;
import token.Token;

import parser.ExpressionParser;
import parser.NumericExpressionParser;

import expression.Expression;
import value.Numeric;
import value.Value;
import evaluate.*;

// essentially same as com.craftinginterpreters.lox.Lox

public class Framework {

    private static boolean hadError = false;

    public static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.printf("[line %d] Error %s: %s", line, where, message);
        hadError = true;
    }

    private static void run(@NotNull String source) {
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.tokenize();

        for(Token token : tokens) {
            System.out.println(token);
        }

        NumericExpressionParser parser = new NumericExpressionParser(tokens);
        Expression expression = parser.parse();
        System.out.printf("expression: %s\n", expression);

        Environment environment = new Environment(List.of("x"), List.of(Numeric.makeNumericValue(3)));
        Numeric value = NumericExpressionEvaluator.eval(expression, environment);
        System.out.printf("value: %s\n", value);
    }

    private static void runFile(@NotNull String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset())); // change to UTF-8 ??
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        while(true) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            run(line);
        }
    }

    public static void main(String @NotNull [] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: java framework.Framework [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }

    }


}
