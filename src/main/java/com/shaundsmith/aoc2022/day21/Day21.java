package com.shaundsmith.aoc2022.day21;

import com.shaundsmith.aoc2022.FileReader;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day21 {

    private static final Pattern EQUATION_PATTERN = Pattern.compile("^ (\\S{4}) (\\*|\\/|\\+|\\-) (\\S{4})$");
    private static final Pattern VALUE_PATTERN = Pattern.compile("^ (\\d+)$");

    public static void main(String[] args) {

        Map<String, Equation> equations = FileReader.readFile("day21/input.txt")
                .stream()
                .collect(Collectors.toMap(
                        line -> line.split(":")[0],
                        line -> parse(line.split(":")[1])));

        long value = resolve(equations, equations.get("root"));
        System.out.println("Root's value = " + value);


        Map<String, Equation> part2Equations = FileReader.readFile("day21/input.txt")
                .stream()
                .collect(Collectors.toMap(
                        line -> line.split(":")[0],
                        line -> parse(line.split(":")[1])));
        Equation root = part2Equations.get("root");
        part2Equations.put("root", new Equation(root.getLhs(), root.getRhs(), "="));

        boolean lhsUsesHuman = usesHuman(part2Equations, part2Equations.get(root.getLhs().getMonkey()));

        long humnValue;
        if (lhsUsesHuman) {
            long targetValue = resolve(part2Equations, part2Equations.get(root.getRhs().getMonkey()));
            humnValue = reverseHumanValue(targetValue, part2Equations, part2Equations.get(root.getLhs().getMonkey()));
        } else {
            long targetValue = resolve(part2Equations, part2Equations.get(root.getLhs().getMonkey()));
            humnValue = reverseHumanValue(targetValue, part2Equations, part2Equations.get(root.getRhs().getMonkey()));
        }
        part2Equations.put("humn", new Equation(new MonkeyOrNumber(humnValue), new MonkeyOrNumber(0), ""));
        System.out.println("Root value = " + resolve(part2Equations, part2Equations.get("root")));
        System.out.println("Humn value = " + humnValue);
    }

    private static Equation parse(String line) {

        Equation equation;

        var equationMatcher = EQUATION_PATTERN.matcher(line);
        var valueMatcher = VALUE_PATTERN.matcher(line);
        if (equationMatcher.find()) {
            equation = new Equation(
                    new MonkeyOrNumber(equationMatcher.group(1)),
                    new MonkeyOrNumber(equationMatcher.group(3)),
                    equationMatcher.group(2));
        } else if (valueMatcher.find()) {
            equation = new Equation(
                    new MonkeyOrNumber(Integer.parseInt(valueMatcher.group(1))),
                    new MonkeyOrNumber(0),
                    "");
        } else {
            throw new RuntimeException();
        }

        return equation;
    }

    private static long resolve(Map<String, Equation> equations, Equation equation) {

        long lhs;
        long rhs;
        if (equation.getLhs().isMonkey()) {
            lhs = resolve(equations, equations.get(equation.getLhs().getMonkey()));
        } else {
            lhs = equation.getLhs().getNumber();
        }
        if (equation.getRhs().isMonkey()) {
            rhs = resolve(equations, equations.get(equation.getRhs().getMonkey()));
        } else {
            rhs = equation.getRhs().getNumber();
        }

        long result;
        String operand = equation.getOperand();
        switch (operand) {
            case "/" -> result = lhs / rhs;
            case "*" -> result = lhs * rhs;
            case "+" -> result = lhs + rhs;
            case "-" -> result = lhs - rhs;
            case "=" -> result = lhs == rhs ? 1 : 0;
            default -> result = lhs;
        }

        return result;
    }

    private static boolean usesHuman(Map<String, Equation> equations, Equation equation) {

        boolean usesHuman = false;

        if (equation.getLhs().isHuman()) {
            return true;
        } else if (equation.getLhs().isMonkey()) {
            usesHuman = usesHuman(equations, equations.get(equation.getLhs().getMonkey()));
        }

        if (usesHuman) {
            return true;
        }

        if (equation.getRhs().isHuman()) {
            return true;
        } else if (equation.getRhs().isMonkey()) {
            usesHuman = usesHuman(equations, equations.get(equation.getRhs().getMonkey()));
        }

        return usesHuman;
    }

    private static long reverseHumanValue(long targetValue, Map<String, Equation> equations, Equation equation) {

        if (equation.getLhs().isHuman()) {
            long rhs = resolve(equations, equations.get(equation.getRhs().getMonkey()));
            return resolveRhsInverse(targetValue, rhs, equation.getOperand());
        } else if (equation.getRhs().isHuman()) {
            long lhs = resolve(equations, equations.get(equation.getLhs().getMonkey()));
            return resolveLhsInverse(targetValue, lhs, equation.getOperand());
        } else if (usesHuman(equations, equations.get(equation.getLhs().getMonkey()))) {
            long rhs = resolve(equations, equations.get(equation.getRhs().getMonkey()));
            return reverseHumanValue(resolveRhsInverse(targetValue, rhs, equation.getOperand()), equations, equations.get(equation.getLhs().getMonkey()));
        } else if (usesHuman(equations, equations.get(equation.getRhs().getMonkey()))) {
            long lhs = resolve(equations, equations.get(equation.getLhs().getMonkey()));
            return reverseHumanValue(resolveLhsInverse(targetValue, lhs, equation.getOperand()), equations, equations.get(equation.getRhs()
                    .getMonkey()));
        }

        return targetValue;
    }

    private static long resolveLhsInverse(long target, long lhs, String operand) {

        return switch (operand) {
            case "/" -> lhs / target;
            case "*" -> target / lhs;
            case "+" -> target - lhs;
            case "-" -> lhs - target;
            default -> target;
        };
    }

    private static long resolveRhsInverse(long target, long rhs, String operand) {

        return switch (operand) {
            case "/" -> target * rhs;
            case "*" -> target / rhs;
            case "+" -> target - rhs;
            case "-" -> target + rhs;
            default -> target;
        };
    }

}
