package com.example.mandaladrawer.parser;

import com.example.mandaladrawer.Program;
import com.example.mandaladrawer.exception.UnknownTokenException;
import com.example.mandaladrawer.instruction.PenDownInstruction;
import com.example.mandaladrawer.instruction.PenUpInstruction;
import com.example.mandaladrawer.parser.keywords.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgramData {

    private final List<ProgramKeyword> keywords;
    private final VariableScope variables;

    private List<String> tokens;
    private Program program;

    public ProgramData(List<ProgramKeyword> keywords, VariableScope variables) {
        this.keywords = keywords;
        this.variables = variables;
    }

    public Program buildProgram(String source) {
        return buildProgram(parseToTokens(source));
    }

    public Program buildProgram(List<String> tokens) {
        this.tokens = tokens;

        program = new Program();

        while (!tokens.isEmpty()) {
            parseNextToken();
        }

        return program;
    }

    private void parseNextToken() {
        String currentToken = tokens.removeFirst();

        for (ProgramKeyword keyword : keywords) {
            if (keyword.match(currentToken)) {
                keyword.parse(this);
                return;
            }
        }

        throw new UnknownTokenException("Unknown token: \"" + currentToken + "\"");
    }

    public List<ProgramKeyword> getKeywords() {
        return keywords;
    }

    public VariableScope getVariables() {
        return variables;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public Program getProgram() {
        return program;
    }

    public static String readFile(File file) {
        try {
            Scanner reader = new Scanner(file);
            StringBuilder text = new StringBuilder();

            while (reader.hasNext()) {
                text.append(reader.nextLine());
                text.append("\n");
            }

            reader.close();

            return text.toString();
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read");
            return null;
        }
    }


    public static List<String> parseToTokens(String code) {
        List<String> list = new ArrayList<>();
        StringBuilder curToken = new StringBuilder();

        // For each character, determine what to do with it
        for (int i = 0; i < code.length(); i++) {
            char currentChar = code.charAt(i);
            if (currentChar == '\n') {
                // New lines: Marks end of a token, also adds a '\n' token to the list
                if (!curToken.isEmpty()) {
                    list.add(curToken.toString());
                    curToken = new StringBuilder();
                }
                list.add("\n");
            } else if (Character.isWhitespace(currentChar)) {
                // Whitespace (except for \n): Marks end of a token
                if (!curToken.isEmpty()) {
                    list.add(curToken.toString());
                    curToken = new StringBuilder();
                }
            } else {
                // Everything else is just part of the token
                curToken.append(currentChar);
            }
        }

        // Add the final token, in case there isn't any trailing whitespace
        if (!curToken.isEmpty()) {
            list.add(curToken.toString());
        }

        return list;
    }

    public static List<Double> parseArguments(ProgramData data) {
        List<Double> arguments = new ArrayList<>();
        List<String> tokens = data.getTokens();
        List<String> relevantTokens = getUntil(tokens, "\n");

        List<String> currentExpression = new ArrayList<>();

        while (!relevantTokens.isEmpty()) {
            if (relevantTokens.getFirst().equals("|")) {
                relevantTokens.removeFirst();
                arguments.add(parseExpression(data, currentExpression));
                currentExpression.clear();
            } else {
                currentExpression.add(relevantTokens.removeFirst());
            }
        }

        arguments.add(parseExpression(data, currentExpression));

        return arguments;
    }


    public static List<Double> parseArguments(ProgramData data, String until) {
        List<Double> arguments = new ArrayList<>();
        List<String> tokens = data.getTokens();
        List<String> relevantTokens = getUntil(tokens, until);

        List<String> currentExpression = new ArrayList<>();

        while (!relevantTokens.isEmpty()) {
            if (relevantTokens.getFirst().equals("|")) {
                relevantTokens.removeFirst();
                tokens.removeFirst();
                arguments.add(parseExpression(data, currentExpression));
                currentExpression.clear();
            } else {
                tokens.removeFirst();
                currentExpression.add(relevantTokens.removeFirst());
            }
        }

        arguments.add(parseExpression(data, currentExpression));

        return arguments;
    }

    public static double parseExpression(ProgramData data) {
        return parseExpression(data, "\n");
    }

    public static double parseExpression(ProgramData data, String until) {
        List<String> expressionTokens = getUntil(data.getTokens(), until);

        ExpressionParser expressionParser = new ExpressionParser(data.getVariables(), expressionTokens);

        return expressionParser.parseExpression();
    }

    public static double parseExpression(ProgramData data, Pattern pattern) {
        List<String> expressionTokens = getUntil(data.getTokens(), pattern);

        ExpressionParser expressionParser = new ExpressionParser(data.getVariables(), expressionTokens);

        return expressionParser.parseExpression();
    }

    public static double parseExpression(ProgramData data, List<String> tokens) {
        ExpressionParser expressionParser = new ExpressionParser(data.getVariables(), tokens);

        return expressionParser.parseExpression();
    }

    public static List<String> getUntil(List<String> tokens, String until) {
        List<String> list = new ArrayList<>();

        while (!tokens.isEmpty() && !tokens.getFirst().equals(until)) {
            list.add(tokens.removeFirst());
        }

        return list;
    }

    public static List<String> getUntil(List<String> tokens, Pattern pattern) {
        List<String> list = new ArrayList<>();

        while (!tokens.isEmpty() && !pattern.matcher(tokens.getFirst()).find()) {
            list.add(tokens.removeFirst());
        }

        return list;
    }

    public static List<String> getUntil(List<String> tokens, Function<String, Boolean> until) {
        List<String> list = new ArrayList<>();

        while (!tokens.isEmpty() && !until.apply(tokens.getFirst())) {
            list.add(tokens.removeFirst());
        }

        return list;
    }

    public static boolean validateVariableName(String name) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }


    public static ProgramData getStandardData() {
        return new ProgramData(getStandardKeywords(), getStandardVariables());
    }

    public static List<ProgramKeyword> getStandardKeywords() {
        List<ProgramKeyword> list = new ArrayList<>();

        list.add(new ForwardKeyword());
        list.add(new RotateKeyword());
        list.add(new DefineVariableKeyword());
        list.add(new SetVariableKeyword());
        list.add(new RepeatKeyword());
        list.add(new PrintKeyword());
        list.add(new GoToKeyword());
        list.add(new FaceKeyword());
        list.add(new SimpleKeyword("penup", data -> data.getProgram().addInstructions(new PenUpInstruction())));
        list.add(new SimpleKeyword("pendown", data -> data.getProgram().addInstructions(new PenDownInstruction())));
        list.add(new SimpleKeyword("\n", _ -> {}));
        list.add(new EndKeyword());

        return list;
    }

    public static VariableScope getStandardVariables() {
        VariableScope scope = new VariableScope();

        scope.add(new ProgramVariable("xPos", 0));
        scope.add(new ProgramVariable("yPos", 0));
        scope.add(new ProgramVariable("heading", 0));

        return scope;
    }
}
