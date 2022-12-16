package com.shaundsmith.aoc2022.day16;

import com.shaundsmith.aoc2022.FileReader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day16 {

    private static final Pattern VALVE_PATTERN = Pattern.compile("Valve (\\S+) has flow rate=(\\d+); tunnels? leads? to valves? (.+)");

    public static void main(String[] args) {

        List<String> lines = FileReader.readFile("day16/input.txt");
        Map<ValveLabel, Valve> valves = lines
                .stream()
                .map(Day16::parse)
                .collect(Collectors.toMap(Valve::label, Function.identity()));

        Set<TunnelPath> paths = PathFinder.findPaths(valves);
        Valve firstValve = parse(lines.get(0));

        var tunnelSystem = new TunnelSystem(valves, paths, firstValve.label());
        int score = tunnelSystem.getMaximumPressureReduction();

        System.out.println("Reduced pressure by " + score);
    }

    private static Valve parse(String line) {

        var matcher = VALVE_PATTERN.matcher(line);

        if (matcher.find()) {
            Set<ValveLabel> leadsTo = Arrays.stream(matcher.group(3).split(", "))
                    .map(ValveLabel::new)
                    .collect(Collectors.toSet());
            return new Valve(new ValveLabel(matcher.group(1)), Integer.parseInt(matcher.group(2)), leadsTo);
        } else {
            throw new RuntimeException();
        }
    }

}
