package com.richardwardle.aoc2021.day8;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day8Part2 {

    public static void main(String[] args) {
        new Day8Part2().execute();
    }

    static String sortString(String str) {
        var chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
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
        var signalPatterns = Arrays.stream(tokens[0].split("\s"))
                .map(Day8Part2::sortString)
                .collect(Collectors.groupingBy(String::length));
        var outPutValues = Arrays.stream(tokens[1].split("\s")).toList();
        return new SignalEntry(signalPatterns, outPutValues);
    }

    private void execute() {
        var outputValueSum = readInput()
                .stream()
                .map(SignalEntry::calculateOutputValue)
                .mapToLong(Long::longValue)
                .sum();
        System.out.println(outputValueSum);
    }
}
