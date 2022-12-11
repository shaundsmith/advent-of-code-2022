package com.shaundsmith.aoc2022.day11;

import lombok.Builder;
import lombok.Getter;

import java.util.Deque;
import java.util.function.Function;

class Monkey {

    private final Deque<Long> items;
    private final Function<Long, Long> operation;
    private final int testMonkeyTargetTrue;
    private final int testMonkeyTargetFalse;

    @Getter
    private final int throwModulus;

    @Getter
    private int itemsProcessed = 0;

    @Builder
    public Monkey(Deque<Long> items, Function<Long, Long> operation, int throwModulus, int testMonkeyTargetTrue, int testMonkeyTargetFalse) {
        this.items = items;
        this.operation = operation;
        this.throwModulus = throwModulus;
        this.testMonkeyTargetTrue = testMonkeyTargetTrue;
        this.testMonkeyTargetFalse = testMonkeyTargetFalse;
    }

    long processAndThrowItem() {

        itemsProcessed++;
        Long item = items.getFirst();
        long newWorryLevel = operation.apply(item) / 3;

        items.removeFirst();
        return newWorryLevel;
    }

    long processAndThrowItem(int worryLevelModulus) {

        itemsProcessed++;
        Long item = items.getFirst();
        long newWorryLevel = operation.apply(item) % worryLevelModulus;

        items.removeFirst();
        return newWorryLevel;
    }

    boolean hasItemsLeft() {
        return !items.isEmpty();
    }

    int getTargetMonkey(long item) {
        return item % throwModulus == 0 ? testMonkeyTargetTrue : testMonkeyTargetFalse;
    }

    void addItem(long item) {
        items.addLast(item);
    }

    @Override
    public String toString() {
        return "Monkey inspected items %d times".formatted(itemsProcessed);
    }

}
