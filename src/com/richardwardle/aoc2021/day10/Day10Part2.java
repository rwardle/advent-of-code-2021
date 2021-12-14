package com.richardwardle.aoc2021.day10;

import com.richardwardle.aoc2021.input.InputUtils;

import java.util.*;

public class Day10Part2 {
    public static void main(String[] args) {
        new Day10Part2().execute();
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

    private static List<Chunk> processLine(char[] chars) {
        var chunks = new Stack<Chunk>();
        for (char c : chars) {
            var chunk = Chunk.lookup(c);
            if (Chunk.isClosing(c)) {
                var peeked = chunks.peek();
                if (peeked == chunk) {
                    chunks.pop();
                } else {
                    return Collections.emptyList();
                }
            } else {
                chunks.push(chunk);
            }
        }

        var completion = new ArrayList<Chunk>();
        while (!chunks.isEmpty()) {
            completion.add(chunks.pop());
        }

        return completion;
    }

    private static long completionScore(List<Chunk> chunks) {
        return chunks.stream()
                .map(Day10Part2::chunkValue)
                .reduce(0L, (total, score) -> total * 5L + score);
    }

    private static long chunkValue(Chunk chunk) {
        return switch (chunk) {
            case ROUND -> 1L;
            case SQUARE -> 2L;
            case BRACE -> 3L;
            case ANGLE -> 4L;
        };
    }

    private void execute() {
        var lines = readInput();
        var scores = lines.stream()
                .map(String::toCharArray)
                .map(Day10Part2::processLine)
                .filter(completion -> !completion.isEmpty())
                .map(Day10Part2::completionScore)
                .sorted()
                .toList();
        System.out.println(scores.get(scores.size() / 2));
    }
}
