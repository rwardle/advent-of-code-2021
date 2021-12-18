package com.richardwardle.aoc2021.day8;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day8Part1 {

    private static final List<Integer> UNIQUE_SEGMENT_LENGTHS = List.of(2, 3, 4, 7);

    public static void main(String[] args) {
        new Day8Part1().execute();
    }

    private static List<SignalEntry> readInput() {
        var entries = new ArrayList<SignalEntry>();

        try (Scanner scanner = InputUtils.day8Data()) {
            while (scanner.hasNext()) {
                entries.add(parseLine(scanner.nextLine()));
            }
        }

        return entries;
    }

    private static SignalEntry parseLine(String line) {
        var tokens = line.split("\s\\|\s");
        var signalPatterns = Arrays.stream(tokens[0].split("\s")).toList();
        var outPutValues = Arrays.stream(tokens[1].split("\s")).toList();
        return new SignalEntry(signalPatterns, outPutValues);
    }

    private static void outputValuesWithUniqueSegmentLength(SignalEntry entry, Consumer<Integer> mapper) {
        for (String outputValue : entry.outputValues()) {
            int length = outputValue.length();
            if (UNIQUE_SEGMENT_LENGTHS.contains(length)) {
                mapper.accept(length);
            }
        }
    }

    private void execute() {
        Map<Integer, Long> digitCount = readInput()
                .stream()
                .mapMulti(Day8Part1::outputValuesWithUniqueSegmentLength)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(digitCount.values().stream().mapToLong(count -> count).sum());
    }

    private record SignalEntry(List<String> signalPatterns, List<String> outputValues) {
    }
}
