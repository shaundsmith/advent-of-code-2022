package com.shaundsmith.aoc2022.day20;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class CircularList<R> {

    private final List<R> list;

    public CircularList(List<R> list) {

        this.list = new ArrayList<>(list);
    }

    Stream<R> stream() {

        return list.stream();
    }

    void move(int startingIndex, long endingIndex) {

        R value = this.list.get(startingIndex);
        this.list.remove(startingIndex);
        this.add(endingIndex, value);
    }

    void add(long index, R item) {

        this.list.add(getLoopedIndex(index), item);
    }

    R get(int index) {

        return this.list.get(getLoopedIndex(index));
    }

    int size() {

        return this.list.size();
    }

    ListLocation<R> getIndexOf(Predicate<R> test) {

        var location = IntStream.range(0, size())
                .filter(i -> test.test(this.list.get(i)))
                .findFirst();

        return new ListLocation<>(location.getAsInt(), list.get(location.getAsInt()));
    }

    private int getLoopedIndex(long index) {

        int loopedIndex;
        if (index < 0) {
            loopedIndex = (int) (list.size() - (Math.abs(index) % list.size()));
        } else {
            loopedIndex = (int) (index % list.size());
        }

        return loopedIndex;
    }

    record ListLocation<R>(int location, R value) {

    }
}
