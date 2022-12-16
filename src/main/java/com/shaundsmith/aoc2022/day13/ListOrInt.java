package com.shaundsmith.aoc2022.day13;

import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
class ListOrInt {

    private final Integer intValue;
    private final List<ListOrInt> listValue;

    ListOrInt(int value) {
        this.intValue = value;
        this.listValue = null;
    }

    ListOrInt(List<ListOrInt> values) {
        this.intValue = null;
        this.listValue = values;
    }

    boolean isInt() {
        return intValue != null;
    }

    Integer getIntValue() {

        return intValue;
    }

    List<ListOrInt> getListValue() {

        return listValue;
    }
}
