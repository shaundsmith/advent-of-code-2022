package com.shaundsmith.aoc2022.day16;

import java.util.HashSet;
import java.util.LinkedList;

record TunnelPath(LinkedList<ValveLabel> path) {

    static TunnelPath from(ValveLabel startingPoint) {

        LinkedList<ValveLabel> path = new LinkedList<>();
        path.add(startingPoint);
        return new TunnelPath(path);
    }

    TunnelPath append(ValveLabel newLabel) {

        var existingPath = new LinkedList<>(path);
        existingPath.addLast(newLabel);
        return new TunnelPath(existingPath);
    }

    TunnelPath append(TunnelPath additionalPath) {

        var existingPath = new LinkedList<>(path);
        if (additionalPath.startsWith().equals(endsWith())) {
            existingPath.addAll(additionalPath.path.subList(1, additionalPath.path.size()));
        } else {
            existingPath.addAll(additionalPath.path);
        }
        return new TunnelPath(existingPath);
    }

    ValveLabel startsWith() {

        return path.getFirst();
    }

    ValveLabel endsWith() {

        return path.getLast();
    }

    boolean containsLoop() {

        return path.size() != new HashSet<>(path).size();
    }

    TunnelPath reverse() {

        var reverse = new LinkedList<ValveLabel>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reverse.add(path.get(i));
        }

        return new TunnelPath(reverse);
    }

    int getSteps() {
        return path.size() - 1;
    }
}
