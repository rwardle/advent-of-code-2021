package com.richardwardle.aoc2021.day1;

import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day1Part1 {

    private static final String INPUT_FILE = "day1-input.txt";
    private final AtomicInteger result = new AtomicInteger(0);

    private void execute() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(
                Objects.requireNonNull(this.getClass().getResourceAsStream(INPUT_FILE)))) {
            Optional<Integer> lastValue = Optional.empty();
            while (scanner.hasNext()) {
                Integer currentValue = Integer.valueOf(scanner.nextLine());
                lastValue.ifPresent(it -> {
                    if (it.compareTo(currentValue) < 0) {
                        result.incrementAndGet();
                    }
                });
                lastValue = Optional.of(currentValue);
            }
        }

        System.out.println("Total: " + result);
    }

    public static void main(String[] args) {
        try {
            new Day1Part1().execute();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
