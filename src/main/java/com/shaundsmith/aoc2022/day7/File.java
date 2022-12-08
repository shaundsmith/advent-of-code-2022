package com.shaundsmith.aoc2022.day7;

import org.apache.commons.lang3.StringUtils;

record File(String name, long size) implements FileSystemItem {

    @Override
    public String format(int indent) {
        String s = toString();
        return StringUtils.leftPad(s, s.length() + indent, " ");
    }

    @Override
    public String toString() {
        return "- %s (file, size=%d)%n".formatted(name, size);
    }
}
