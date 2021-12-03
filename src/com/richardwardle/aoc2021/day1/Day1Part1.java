package com.richardwardle.aoc2021.day1;

import com.richardwardle.aoc2021.input.InputUtils;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day1Part1 {

    private final AtomicInteger result = new AtomicInteger(0);

    private void execute() throws FileNotFoundException {
        try (Scanner scanner = InputUtils.day1Data()) {
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
