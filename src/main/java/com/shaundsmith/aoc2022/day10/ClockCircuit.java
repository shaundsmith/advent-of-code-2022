package com.shaundsmith.aoc2022.day10;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Deque;

@RequiredArgsConstructor
class ClockCircuit {

    @Getter
    private int register = 1;
    @Getter
    private int cycle = 1;

    private final Deque<Instruction> instructions;

    void tick() {
        var instruction = instructions.getFirst();
        instruction.tick();
        if (instruction.isComplete()) {
            register += instruction.getRegisterChange();
            instructions.removeFirst();
        }
        cycle++;
    }

    boolean canTick() {
        return !instructions.isEmpty();
    }



}
