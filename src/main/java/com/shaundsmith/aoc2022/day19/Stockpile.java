package com.shaundsmith.aoc2022.day19;

import java.util.HashMap;
import java.util.Map;

class Stockpile implements Cloneable {

    private final Map<Resource, Integer> resources;

    Stockpile() {

        this.resources = new HashMap<>(Map.of(
                Resource.ORE, 0,
                Resource.CLAY, 0,
                Resource.OBSIDIAN, 0,
                Resource.GEODE, 0));
    }

    void add(Resource resource, int amount) {

        resources.compute(resource, (r, existingAmount) -> existingAmount + amount);
    }

    void add(Map<Resource, Integer> expenditure) {

        expenditure.forEach((resource, amount) ->
                resources.compute(resource, (r, existingAmount) -> existingAmount + amount));
    }

    boolean canSpend(Map<Resource, Integer> expenditure) {

        return expenditure.entrySet().stream()
                .allMatch(entry -> resources.get(entry.getKey()) >= entry.getValue());
    }

    void spend(Map<Resource, Integer> expenditure) {

        if (!canSpend(expenditure)) {
            throw new RuntimeException();
        }

        expenditure.forEach((resource, amount) ->
            resources.compute(resource, (r, existingAmount) -> existingAmount - amount));
    }

    int getQuantity(Resource resource) {

        return resources.get(resource);
    }

    @Override
    protected Object clone() {

        var stockpile = new Stockpile();
        stockpile.add(stockpile.resources);

        return stockpile;
    }
}
