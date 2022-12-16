package com.shaundsmith.aoc2022.day13;

import com.shaundsmith.aoc2022.FileReader;
import com.shaundsmith.aoc2022.Pair;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Day13 {

    private static final Comparator<ListOrInt> COMPARATOR = new PacketComparator();

    public static void main(String[] args) {

        List<String> lines = FileReader.readFile("day13/input.txt");

        List<Pair<ListOrInt>> packets = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 3) {
            packets.add(new Pair<>(parse(lines.get(i)), parse(lines.get(i + 1))));
        }

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < packets.size(); i++) {
            int comparison = COMPARATOR.compare(packets.get(i).firstItem(), packets.get(i).secondItem());
            if (comparison < 0) {
                indices.add(i + 1);
            }
        }

        System.out.println("Found matches " + indices);
        System.out.println("Sum: " + indices.stream().mapToInt(i -> i).sum());

        List<ListOrInt> individualPackets = new ArrayList<>();
        for (String line : lines) {
            if (!StringUtils.isBlank(line)) {
                individualPackets.add(parse(line));
            }
        }
        ListOrInt firstDividerPacket = new ListOrInt(List.of(new ListOrInt(2)));
        ListOrInt secondDividerPacket = new ListOrInt(List.of(new ListOrInt(6)));

        individualPackets.add(firstDividerPacket);
        individualPackets.add(secondDividerPacket);

        individualPackets.sort(new PacketComparator());
        int firstDividerIndex = individualPackets.indexOf(firstDividerPacket) + 1;
        int secondDividerIndex = individualPackets.indexOf(secondDividerPacket) + 1;
        System.out.println("Found dividers at %d and %d (Product %d)"
                .formatted(firstDividerIndex, secondDividerIndex, firstDividerIndex * secondDividerIndex));
    }

    private static ListOrInt parse(String line) {

        if (StringUtils.isNumeric(line)) {
            return new ListOrInt(Integer.parseInt(line));
        }

        List<ListOrInt> parts = new ArrayList<>();
        int bracketCount = 0;
        var sb = new StringBuilder();
        for (int i = 1; i < line.length() - 1; i++) {
            if (line.charAt(i) == '[') {
                bracketCount++;
                sb.append(line.charAt(i));
            } else if (line.charAt(i) == ']') {
                bracketCount--;
                sb.append(line.charAt(i));
            } else if (line.charAt(i) == ',' && bracketCount == 0) {
                parts.add(parse(sb.toString()));
                sb = new StringBuilder();
            } else {
                sb.append(line.charAt(i));
            }
        }
        if (!sb.isEmpty()) {
            parts.add(parse(sb.toString()));
        }

        return new ListOrInt(parts);
    }

}
