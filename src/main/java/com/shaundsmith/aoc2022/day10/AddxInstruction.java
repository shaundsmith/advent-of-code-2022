package com.shaundsmith.aoc2022.day10;

class AddxInstruction implements Instruction {

    private static final int TICKS_TO_COMPLETE = 2;

    private int timesTicked = 0;
    private final int change;

    AddxInstruction(int change) {
        this.change = change;
    }

    @Override
    public void tick() {
        timesTicked++;
    }

    @Override
    public int getRegisterChange() {
        return change;
    }

    @Override
    public boolean isComplete() {
        return timesTicked == TICKS_TO_COMPLETE;
    }
}
