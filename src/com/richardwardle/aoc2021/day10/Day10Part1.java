package com.richardwardle.aoc2021.day10;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.*;

public class Day10Part1 {
    public static void main(String[] args) {
        new Day10Part1().execute();
    }

    private static List<String> readInput() {
        var lines = new ArrayList<String>();
        try (Scanner scanner = InputUtils.day10Data()) {
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        }
        return lines;
    }

    private static Optional<Chunk> processLine(char[] chars) {
        var chunks = new Stack<Chunk>();
        for (char c : chars) {
            var chunk = Chunk.lookup(c);
            if (Chunk.isClosing(c)) {
                var peeked = chunks.peek();
                if (peeked == chunk) {
                    chunks.pop();
                } else {
                    return Optional.of(chunk);
                }
            } else {
                chunks.push(chunk);
            }
        }
        return Optional.empty();
    }

    private static int score(Chunk chunk) {
        return switch (chunk) {
            case ROUND -> 3;
            case SQUARE -> 57;
            case BRACE -> 1197;
            case ANGLE -> 25137;
        };
    }

    private void execute() {
        var lines = readInput();
        var score = lines.stream()
                .map(String::toCharArray)
                .map(Day10Part1::processLine)
                .mapMultiToInt((optionalChunk, mapper) -> optionalChunk
                        .ifPresent(chunk -> mapper.accept(Day10Part1.score(chunk))))
                .sum();
        System.out.println(score);
    }
}
