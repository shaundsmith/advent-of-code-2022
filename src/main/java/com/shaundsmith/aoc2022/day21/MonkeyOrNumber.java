package com.shaundsmith.aoc2022.day21;

import lombok.Getter;

@Getter
class MonkeyOrNumber {

    private final Long number;
    private final String monkey;

    MonkeyOrNumber(long number) {

        this.number = number;
        this.monkey = null;
    }

    MonkeyOrNumber(String monkey) {

        this.number = null;
        this.monkey = monkey;
    }

    boolean isMonkey() {

        return monkey != null;
    }

    boolean isHuman() {

        return monkey != null && monkey.equals("humn");
    }

}
