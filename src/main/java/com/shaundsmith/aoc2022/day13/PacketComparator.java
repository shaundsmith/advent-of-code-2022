package com.shaundsmith.aoc2022.day13;

import java.util.Comparator;
import java.util.List;

class PacketComparator implements Comparator<ListOrInt> {

    static final int CORRECT_ORDER = -1;
    static final int INCORRECT_ORDER = 1;
    static final int CONTINUE = 0;

    @Override
    public int compare(ListOrInt left, ListOrInt right) {

        if (left.isInt() && right.isInt()) {
            int compareInts = compareInts(left.getIntValue(), right.getIntValue());
            if (compareInts != CONTINUE) {
                return compareInts;
            }
        } else {
            int compareLists = compareLists(listify(left), listify(right));
            if (compareLists != CONTINUE) {
                return compareLists;
            }
        }

        return CONTINUE;
    }

    private int compareInts(int left, int right) {

        int comparison;
        if (left < right) {
            comparison = CORRECT_ORDER;
        } else if (left > right) {
            comparison = INCORRECT_ORDER;
        } else {
            comparison = CONTINUE;
        }

        return comparison;
    }

    private int compareLists(ListOrInt left, ListOrInt right) {

        List<ListOrInt> leftList = left.getListValue();
        List<ListOrInt> rightList = right.getListValue();

        var leftIterator = leftList.iterator();
        var rightIterator = rightList.iterator();

        while (leftIterator.hasNext() && rightIterator.hasNext()) {
            ListOrInt leftPart = leftIterator.next();
            ListOrInt rightPart = rightIterator.next();

            int comparison = compare(leftPart, rightPart);
            if (comparison != CONTINUE) {
                return comparison;
            }
        }

        int comparison;
        if (leftIterator.hasNext() && !rightIterator.hasNext()) {
            comparison = INCORRECT_ORDER;
        } else if (!leftIterator.hasNext() && rightIterator.hasNext()) {
            comparison = CORRECT_ORDER;
        } else {
            comparison = CONTINUE;
        }

        return comparison;
    }

    private ListOrInt listify(ListOrInt value) {

        return value.isInt()
                ? new ListOrInt(List.of(new ListOrInt(value.getIntValue())))
                : value;
    }
}
