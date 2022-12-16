package com.shaundsmith.aoc2022.day16;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class TunnelSystem {

    private final Map<ValveLabel, Valve> valves;
    private final Set<TunnelPath> paths;
    private final ValveLabel startingValve;

    int getMaximumPressureReduction() {

        return mapTunnels().stream()
                .mapToInt(this::score)
                .max()
                .orElseThrow();
    }

    private Set<PossibleRoute> mapTunnels() {

        Set<PossibleRoute> completedPaths = new HashSet<>();
        Set<PossibleRoute> openPaths = new HashSet<>();
        openPaths.add(new PossibleRoute(TunnelPath.from(startingValve), Collections.emptyMap(), 30));

        do {
            Set<PossibleRoute> newOpenPaths = new HashSet<>();
            for(var possibleRoute : openPaths) {
                newOpenPaths.addAll(paths.stream()
                        .filter(path -> path.startsWith().equals(possibleRoute.route().endsWith()))
                        .filter(path -> !possibleRoute.valvesClosed().containsKey(valves.get(path.endsWith())))
                        .filter(path -> possibleRoute.remainingTime() >= path.getSteps() + 1) // +1 to accommodate turning valve
                        .map(path -> appendToRoute(possibleRoute, path))
                        .collect(Collectors.toSet()));
                if (newOpenPaths.isEmpty()) {
                    completedPaths.add(possibleRoute);
                }
            }

            openPaths.clear();
            openPaths.addAll(newOpenPaths);
        } while (!openPaths.isEmpty());

        return completedPaths;
    }

    private PossibleRoute appendToRoute(PossibleRoute route, TunnelPath path) {

        int remainingTime = route.remainingTime() - (path.getSteps() + 1);

        Map<Valve, Integer> closedValves = new HashMap<>(route.valvesClosed());
        closedValves.put(valves.get(path.endsWith()), remainingTime);

        return new PossibleRoute(route.route().append(path),
                closedValves,
                remainingTime);
    }

    private int score(PossibleRoute route) {

        return route.valvesClosed().entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().flowRate() * entry.getValue())
                .sum();
    }

}
