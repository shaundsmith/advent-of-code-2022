package com.shaundsmith.aoc2022.day10;

interface Instruction {

    void tick();

    int getRegisterChange();

    boolean isComplete();

}
