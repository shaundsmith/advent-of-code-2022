package com.shaundsmith.aoc2022.day10;

class NoopInstruction implements Instruction {

    private boolean hasTicked = false;

    @Override
    public void tick() {
        hasTicked = true;
    }

    @Override
    public int getRegisterChange() {
        return 0;
    }

    @Override
    public boolean isComplete() {
        return hasTicked;
    }
}
