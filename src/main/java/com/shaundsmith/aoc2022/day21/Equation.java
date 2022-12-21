package com.shaundsmith.aoc2022.day21;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class Equation {

    private final MonkeyOrNumber lhs;
    private final MonkeyOrNumber rhs;
    private final String operand;

}
