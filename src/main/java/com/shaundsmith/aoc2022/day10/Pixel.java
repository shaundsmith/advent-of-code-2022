package com.shaundsmith.aoc2022.day10;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
class Pixel {

    private String displayValue = ".";

    void light() {
        displayValue = "#";
    }

}
