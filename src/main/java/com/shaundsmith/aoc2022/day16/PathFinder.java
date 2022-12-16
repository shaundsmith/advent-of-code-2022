package com.shaundsmith.aoc2022.day16;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
class PathFinder {

    Set<TunnelPath> findPaths(Map<ValveLabel, Valve> valves) {

        Set<TunnelPath> paths = new HashSet<>();

        for (Valve valve : valves.values()) {
            paths.addAll(mapPathsForValve(valve, valves));
        }

        return paths;
    }

    private Set<TunnelPath> mapPathsForValve(Valve valve, Map<ValveLabel, Valve> valves) {

        Set<TunnelPath> completedPaths = new HashSet<>();
        Set<TunnelPath> openPaths = new HashSet<>();
        openPaths.add(TunnelPath.from(valve.label()));

        long interestingValvesCount = valves.values()
                .stream()
                .filter(v -> v.flowRate() > 0)
                .count();

        do {

            Set<TunnelPath> newOpenPaths = new HashSet<>();
            for (var openPath : openPaths) {
                if (isComplete(openPath, valves) && !containsPath(completedPaths, openPath)) {
                    completedPaths.add(openPath);
                }
                newOpenPaths.addAll(valves.get(openPath.endsWith()).leadsTo()
                        .stream()
                        .map(openPath::append)
                        .filter(path -> !path.containsLoop())
                        .collect(Collectors.toSet()));

            }
            openPaths.clear();
            openPaths.addAll(newOpenPaths);

        } while (!openPaths.isEmpty() && completedPaths.size() != interestingValvesCount - 1);

        return completedPaths;
    }

    private boolean isComplete(TunnelPath path, Map<ValveLabel, Valve> valves) {

        return valves.get(path.endsWith()).flowRate() > 0;
    }

    private boolean containsPath(Set<TunnelPath> paths, TunnelPath targetPath) {

        return paths.stream().anyMatch(path ->
                (path.startsWith().equals(targetPath.startsWith())
                                && path.endsWith().equals(targetPath.endsWith())) ||
                        (path.startsWith().equals(targetPath.endsWith())
                                && path.endsWith().equals(targetPath.startsWith())));
    }


}
