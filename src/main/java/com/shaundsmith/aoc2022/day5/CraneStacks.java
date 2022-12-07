package com.shaundsmith.aoc2022.day5;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
class CraneStacks {

    private final List<CraneStack> stacks;

    void moveStackTops(int amount, int from, int to, boolean moveInOrder) {

        List<String> lifted = stacks.get(from - 1).liftTop(amount, moveInOrder);
        stacks.get(to - 1).addToTop(lifted);
    }

    List<String> getTopOfStacks() {
        return stacks.stream()
                .map(stack -> stack.getTop())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {

        return stacks.stream()
                .map(CraneStack::toString)
                .collect(Collectors.joining("\n"));
    }

    static class Builder {

        private final Map<Integer, CraneStack.Builder> builders = new HashMap<>();

        Builder addToStack(int stack, String value) {

            builders.putIfAbsent(stack, new CraneStack.Builder());
            builders.get(stack).addTopDown(value);
            return this;
        }

        CraneStacks build() {
            var stacks = builders.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                    .map(e -> e.getValue().build())
                    .collect(Collectors.toList());

            return new CraneStacks(stacks);
        }

    }

}
