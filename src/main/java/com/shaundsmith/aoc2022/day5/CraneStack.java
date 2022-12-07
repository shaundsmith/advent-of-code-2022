package com.shaundsmith.aoc2022.day5;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@AllArgsConstructor
class CraneStack {

    private final Stack<String> contents;

    List<String> liftTop(int amount, boolean moveInOrder) {

        List<String> lifted = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            lifted.add(contents.pop());
        }

        // For part 2
        if (moveInOrder) {
            Collections.reverse(lifted);
        }
        return lifted;
    }

    void addToTop(List<String> stack) {
        stack.forEach(contents::push);
    }

    String getTop() {
        return contents.isEmpty() ? null : contents.peek();
    }

    @Override
    public String toString() {
        return String.join(", ", contents);
    }

    static class Builder {

        Stack<String> contents = new Stack<>();

        Builder addTopDown(String content) {
            if (!content.isBlank()) {
                contents.add(content);
            }
            return this;
        }

        CraneStack build() {

            Collections.reverse(contents);
            return new CraneStack(contents);
        }



    }

}
